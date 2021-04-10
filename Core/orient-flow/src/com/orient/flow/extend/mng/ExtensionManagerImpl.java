package com.orient.flow.extend.mng;

import com.orient.flow.extend.ExtensionManager;
import com.orient.flow.extend.process.Extension;
import com.orient.flow.extend.process.ExtensionInfoExtractor;
import com.orient.utils.UtilFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * a default implementation of {@link ExtensionManager}
 *
 * @author Seraph
 * 2016-06-27 上午11:33
 */
class ExtensionManagerImpl implements ExtensionManager, BeanDefinitionRegistryPostProcessor, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    private static final Set<Class<?>> extensionsTypes = new HashSet<>();
    private static final Map<Class<?>, ExtensionInfoExtractor> extensionInfoExtractors = new HashMap<>();
    private static final Map<Class<?>, List<Extension>> extensionMap = new HashMap<>();

    @Override
    public List<Extension> getExtensions(Class<?> extensionType) {
        List<Extension> extensions = extensionMap.get(extensionType);
        return new ArrayList<>(extensions == null ? new ArrayList<>() : extensions);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        initialExtensionClasses();

        ExtensionBeanScanner scanner = new ExtensionBeanScanner(registry, getEnvironment(registry));
        scanner.registerFilters();
        synchronized (this){
            scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
        }
    }

    private void initialExtensionClasses(){
        Set<String> extensionClassNames = this.extensionToExtractorClsNameMap.keySet();
        for(String extensionClassName : extensionClassNames){
            String extensionExtractorClassName = this.extensionToExtractorClsNameMap.get(extensionClassName);
            try {
                Class<?> extensionClass = ClassUtils.forName(extensionClassName, ClassUtils.getDefaultClassLoader());
                Class<?> extensionExtractorClass = ClassUtils.forName(extensionExtractorClassName, ClassUtils.getDefaultClassLoader());
                if(!ExtensionInfoExtractor.class.isAssignableFrom(extensionExtractorClass)){
                    throw new IllegalArgumentException("wrong class:" + extensionExtractorClass + "not ExtensionInfoExtractor ");
                }

                extensionsTypes.add(extensionClass);

                extensionInfoExtractors.put(extensionClass, (ExtensionInfoExtractor) extensionExtractorClass.newInstance());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("wrong class:" + extensionClassName);
            } catch (InstantiationException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("wrong class:" + extensionClassName);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("wrong class:" + extensionClassName);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //this method is left intentionally blank
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() != null){
            return;
        }

        synchronized (this){
            for (String beanName : beanNames) {
                Object bean = this.applicationContext.getBean(beanName);
                Class<?> beanClass = bean.getClass();
                Class<?>[] interfaces = beanClass.getInterfaces();
                for(Class<?> inter : interfaces){
                    if(extensionsTypes.contains(inter)){
                        ExtensionInfoExtractor extractor = extensionInfoExtractors.get(inter);
                        Assert.notNull(extractor);
                        Extension extension = extractor.extractFromBean(bean, beanName);
                        List<Extension> extensionList = extensionMap.get(inter);
                        if(extensionList == null){
                            extensionList = new ArrayList<>();
                            extensionList.add(extension);
                            extensionMap.put(inter, extensionList);
                        }else{
                            extensionList.add(extension);
                        }
                    }
                }
            }
        }
    }

    private static Environment getEnvironment(BeanDefinitionRegistry registry) {
        if (registry instanceof EnvironmentCapable) {
            return ((EnvironmentCapable) registry).getEnvironment();
        }
        return new StandardEnvironment();
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void setExtensionToExtractorClsNameMap(Map<String, String> extensionToExtractorClsNameMap) {
        this.extensionToExtractorClsNameMap = extensionToExtractorClsNameMap;
    }

    private String basePackage;

    private ApplicationContext applicationContext;

    private Set<String> beanNames = UtilFactory.newHashSet();

    private Map<String, String> extensionToExtractorClsNameMap;


    private class ExtensionBeanScanner extends ClassPathBeanDefinitionScanner {

        ExtensionBeanScanner(BeanDefinitionRegistry registry, Environment environment){
            this(registry, false, environment);
        }

        ExtensionBeanScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment) {
            super(registry, useDefaultFilters, environment);
            this.registry = registry;
        }

        void registerFilters(){
            for(Class<?> type:extensionsTypes){
                addIncludeFilter(new AssignableTypeFilter(type));
            }
        }

        @Override
        public Set<BeanDefinitionHolder> doScan(String... basePackages) {
            Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

            if (beanDefinitions.isEmpty()) {
                logger.warn("No flow extensions were found in '" + Arrays.toString(basePackages) + "' package");
            } else {
                processBeanDefinitions(beanDefinitions);
            }

            return beanDefinitions;
        }

        private void processBeanDefinitions(Set<BeanDefinitionHolder> holders) {
            for(BeanDefinitionHolder holder:holders){
                ExtensionManagerImpl.this.beanNames.add(holder.getBeanName());
            }
        }

        private final BeanDefinitionRegistry registry;
    }

    public static void main(String[] agrs){

    }
}

package com.orient.flow.extend.process;

/**
 * represent an extension
 *
 * @author Seraph
 * 2016-06-28 上午8:42
 */
public class Extension<T> implements Comparable<Extension>{


    public static class Builder<T>{
        private final String beanName;
        private final Class<?> beanClass;

        private int order;
        private String displayName;
        private String description;
        private T customAnnotation;

        public Builder(String beanName, Class<?> beanClass){
            this.beanName = beanName;
            this.beanClass = beanClass;
        }

        public Builder order(int order){
            this.order = order;
            return this;
        }

        public Builder displayName(String displayName){
            this.displayName = displayName;
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder customAnnotation(T annotation){
            this.customAnnotation = annotation;
            return this;
        }

        public Extension<T> build(){
            return new Extension<>(this);
        }
    }

    private Extension(Builder<T> builder){
        this.beanName = builder.beanName;
        this.beanClass = builder.beanClass;
        this.order = builder.order;
        this.displayName = builder.displayName;
        this.description = builder.description;
        this.customAnnotation = builder.customAnnotation;
    }

    private final String beanName;
    private final Class<?> beanClass;
    private int order;
    private String displayName;
    private String description;

    private T customAnnotation;

    @Override
    public int compareTo(Extension o) {
        return order - o.getOrder();
    }

    public int getOrder() {
        return order;
    }

    public String getBeanName() {
        return beanName;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public T getCustomAnnotation() {
        return customAnnotation;
    }

    //setters
    public void setOrder(int order) {
        this.order = order;
    }

    public void setCustomAnnotation(T customAnnotation) {
        this.customAnnotation = customAnnotation;
    }
}

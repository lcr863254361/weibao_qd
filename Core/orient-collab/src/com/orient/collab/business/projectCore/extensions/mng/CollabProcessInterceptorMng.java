package com.orient.collab.business.projectCore.extensions.mng;

import com.orient.collab.business.projectCore.constant.ProcessType;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.flow.extend.ExtensionManager;
import com.orient.flow.extend.process.Extension;
import com.orient.utils.UtilFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * the mng
 *
 * @author Seraph
 *         2016-08-12 下午2:35
 */
@Component
public class CollabProcessInterceptorMng {

    public List<CollabProcessInterceptor> getInterceptors(String model, ProcessType processType){

        List<Extension> allExtensions = extensionManager.getExtensions(CollabProcessInterceptor.class);
        List<Extension> appliedExtensions = new ArrayList<>();
        for(Extension extension : allExtensions){
            CollabProcessMarker marker = (CollabProcessMarker) extension.getCustomAnnotation();
            if(marker == null){
                continue;
            }

            if(marker.processType().length>0){
                boolean processTypeApplied = false;
                for(ProcessType defProcessType : marker.processType()){
                    if(defProcessType != processType){
                        continue;
                    }
                    processTypeApplied = true;
                }

                if(!processTypeApplied){
                    continue;
                }
            }

            if(marker.models().length>0){
                boolean modelApplied = false;
                for(String defModel : marker.models()){
                    if(!defModel.equals(model)){
                        continue;
                    }
                    modelApplied = true;
                }

                if(!modelApplied){
                    continue;
                }
            }

            extension.setOrder(marker.order());
            appliedExtensions.add(extension);
        }

        Collections.sort(appliedExtensions);
        List<CollabProcessInterceptor> interceptors = UtilFactory.newArrayList();
        for(Extension extension : appliedExtensions){
            CollabProcessInterceptor listenerBean = (CollabProcessInterceptor) OrientContextLoaderListener.Appwac.getBean(extension.getBeanName());
            interceptors.add(listenerBean);
        }
        return interceptors;
    }

    @Autowired
    private ExtensionManager extensionManager;
}

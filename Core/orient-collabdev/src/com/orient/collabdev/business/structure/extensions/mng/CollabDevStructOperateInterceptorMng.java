package com.orient.collabdev.business.structure.extensions.mng;

import com.orient.collabdev.business.structure.constant.StructOperateType;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.flow.extend.ExtensionManager;
import com.orient.flow.extend.process.Extension;
import com.orient.utils.UtilFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CollabDevStructOperateInterceptorMng {

    public List<CollabDevStructInterceptor> getInterceptors(String model, StructOperateType structOperateType, ManagerStatusEnum projectStatus) {

        List<Extension> allExtensions = extensionManager.getExtensions(CollabDevStructInterceptor.class);
        List<Extension> appliedExtensions = new ArrayList<>();
        for (Extension extension : allExtensions) {
            CollabDevStructMarker marker = (CollabDevStructMarker) extension.getCustomAnnotation();
            if (marker == null) {
                continue;
            }

            if (marker.structOperateType().length > 0) {
                boolean structOperateTypeApplied = false;
                for (StructOperateType defProcessType : marker.structOperateType()) {
                    if (defProcessType != structOperateType) {
                        continue;
                    }
                    structOperateTypeApplied = true;
                }

                if (!structOperateTypeApplied) {
                    continue;
                }
            }

            if (marker.models().length > 0) {
                boolean modelApplied = false;
                for (String defModel : marker.models()) {
                    if (!defModel.equals(model)) {
                        continue;
                    }
                    modelApplied = true;
                }

                if (!modelApplied) {
                    continue;
                }
            }

            if (marker.projectStatus().length > 0) {
                boolean statusApplied = false;
                for (ManagerStatusEnum defStatus : marker.projectStatus()) {
                    if (!defStatus.equals(projectStatus)) {
                        continue;
                    }
                    statusApplied = true;
                }

                if (!statusApplied) {
                    continue;
                }
            }

            extension.setOrder(marker.order());
            appliedExtensions.add(extension);
        }

        Collections.sort(appliedExtensions);
        List<CollabDevStructInterceptor> interceptors = UtilFactory.newArrayList();
        for (Extension extension : appliedExtensions) {
            CollabDevStructInterceptor listenerBean = (CollabDevStructInterceptor) OrientContextLoaderListener.Appwac.getBean(extension.getBeanName());
            interceptors.add(listenerBean);
        }
        return interceptors;
    }

    @Autowired
    private ExtensionManager extensionManager;
}

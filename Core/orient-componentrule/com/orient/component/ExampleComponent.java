package com.orient.component;

import com.orient.component.bean.ValidateComponentBean;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/8/28 0028.
 */
@Component
public class ExampleComponent implements ComponentInterface {

    @Override
    public String validateComponent(ValidateComponentBean validateComponentBean) {
        return "";
    }

    @Override
    public String getDashBordExtClass() {
        return "OrientTdm.CollabDev.Designing.ResultSettings.ComponentData.Model.ExampleComponent";
    }

    @Override
    public String getComponentName() {
        return null;
    }
}

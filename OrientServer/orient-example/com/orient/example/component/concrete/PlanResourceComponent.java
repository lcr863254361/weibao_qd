package com.orient.example.component.concrete;

import com.orient.component.ComponentInterface;
import com.orient.component.bean.ValidateComponentBean;
import org.springframework.stereotype.Component;

/**
 * 资源规划
 *
 * @author Administrator
 * @create 2017-07-20 9:25
 */
@Component
public class PlanResourceComponent implements ComponentInterface {

    @Override
    public String validateComponent(ValidateComponentBean validateComponentBean) {

        return "";
    }

    @Override
    public String getComponentName() {
        return null;
    }

    @Override
    public String getDashBordExtClass() {
        return "OrientTdm.Example.Component.PlanTestResource";
    }
}

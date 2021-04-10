package com.orient.example.component.concrete;

import com.orient.component.ComponentInterface;
import com.orient.component.bean.ValidateComponentBean;
import org.springframework.stereotype.Component;

/**
 * 数据采集
 *
 * @author Administrator
 * @create 2017-07-20 9:25
 */
@Component
public class DAQComponent implements ComponentInterface {

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
        return "OrientTdm.Example.Component.DAQDashBord";
    }
}

package com.orient.example.component.concrete;

import com.orient.component.ComponentInterface;
import com.orient.component.bean.ValidateComponentBean;
import org.springframework.stereotype.Component;

/**
 * 生成试验报告
 *
 * @author Administrator
 * @create 2017-07-21 14:43
 */
@Component
public class GenerateDOCComponent implements ComponentInterface {

    @Override
    public String getComponentName() {
        return null;
    }

    @Override
    public String validateComponent(ValidateComponentBean validateComponentBean) {
        return "";
    }

    @Override
    public String getDashBordExtClass() {
        return "OrientTdm.Example.Component.GenerateDOCPanel";
    }
}

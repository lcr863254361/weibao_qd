package com.orient.background.business;

import com.orient.background.bean.CfQuantityVO;
import com.orient.background.bean.UnitTreeVO;
import com.orient.sysmodel.domain.quantity.CfQuantityDO;
import com.orient.sysmodel.domain.quantity.CfQuantityTemplateRelationDO;
import com.orient.sysmodel.domain.quantity.CwmSysNumberunitDO;
import com.orient.sysmodel.service.quantity.IQuantityService;
import com.orient.sysmodel.service.quantity.IQuantityTemplateRelationService;
import com.orient.sysmodel.service.quantity.IUnitService;
import com.orient.utils.BeanUtils;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class QuantityBusiness extends BaseHibernateBusiness<CfQuantityDO> {

    @Autowired
    IQuantityService quantityService;

    @Autowired
    IUnitService unitService;

    @Autowired
    IQuantityTemplateRelationService quantityTemplateRelationService;

    @Override
    public IQuantityService getBaseService() {
        return quantityService;
    }

    /**
     * -length
     * -m
     * -cm
     * -mm
     * -height
     * -kg
     * -g
     * -mg
     *
     * @return
     */
    public List<UnitTreeVO> getAllUnits() {

        List<UnitTreeVO> retVal = new ArrayList<>();
        List<CwmSysNumberunitDO> unitEntities = unitService.list();
        //init first level node
        List<String> distinctNams = unitEntities.stream().map(CwmSysNumberunitDO::getShowName).distinct().collect(Collectors.toList());
        distinctNams.forEach(category -> {
            UnitTreeVO unitTreeVO = new UnitTreeVO();
            unitTreeVO.setText(category);
            unitTreeVO.setExpanded(true);
            unitTreeVO.setLeaf(false);
            unitTreeVO.setIconCls("icon-unit-category");
            retVal.add(unitTreeVO);
        });

        unitEntities.forEach(unitEntitity -> {
            String name = unitEntitity.getShowName();
            UnitTreeVO fatherNode = retVal.stream().filter(unitTreeVO -> name.equals(unitTreeVO.getText())).findFirst().get();
            UnitTreeVO unitTreeVO = new UnitTreeVO();
            unitTreeVO.setText(unitEntitity.getUnit());
            unitTreeVO.setExpanded(false);
            unitTreeVO.setLeaf(true);
            unitTreeVO.setUnitId(unitEntitity.getId());
            unitTreeVO.setIconCls("icon-unit-leaf");
            fatherNode.getResults().add(unitTreeVO);
        });
        return retVal;
    }

    /**
     * transform unit to real data
     *
     * @param formValue
     */
    public void saveSpecial(CfQuantityVO formValue) {
        if (null != formValue.getUnitId()) {
            CwmSysNumberunitDO cwmSysNumberunitDO = unitService.getById(formValue.getUnitId());
            CfQuantityDO quantityDO = new CfQuantityDO();
            BeanUtils.copyProperties(quantityDO, formValue);
            quantityDO.setNumberunitDO(cwmSysNumberunitDO);
            super.save(quantityDO);
        }
    }

    public void updateSpecial(CfQuantityVO formValue) {
        Long id = formValue.getId();
        CfQuantityDO quantityDO = quantityService.getById(id);
        if (!quantityDO.getNumberunitDO().getId().equals(formValue.getUnitId())) {
            CwmSysNumberunitDO cwmSysNumberunitDO = unitService.getById(formValue.getUnitId());
            quantityDO.setNumberunitDO(cwmSysNumberunitDO);
        }
        quantityDO.setName(formValue.getName());
        quantityDO.setDatatype(formValue.getDatatype());
        super.update(quantityDO);
    }

    public ExtGridData<CfQuantityVO> listSpecial(Integer page, Integer limit, CfQuantityDO filter, String excludeIds) {
        ExtGridData<CfQuantityVO> retVal = new ExtGridData<>();
        Criterion criterion = null;
        if (!StringUtil.isEmpty(excludeIds)) {
            criterion = Restrictions.not(Restrictions.in("id", CommonTools.stringListToLongList(CommonTools.arrayToList(excludeIds.split(",")))));
        }
        ExtGridData<CfQuantityDO> originalData = null == criterion ? super.list(page, limit, filter) : super.list(page, limit, filter, criterion);
        BeanUtils.copyProperties(retVal, originalData);
        List<CfQuantityDO> results = originalData.getResults();
        List<CfQuantityVO> vos = new ArrayList<>();
        results.forEach(cfQuantityDO -> {
            CfQuantityVO quantityVO = new CfQuantityVO(cfQuantityDO);
            vos.add(quantityVO);
        });
        retVal.setResults(vos);
        return retVal;
    }

    public ExtGridData<CfQuantityVO> listByTemplate(Integer type, Long templateId) {
        List<CfQuantityTemplateRelationDO> exists = quantityTemplateRelationService.list(Restrictions.eq("belongTemplate.id", templateId));
        List<Long> existQuantityIds = exists.stream().map(cfQuantityTemplateRelationDO -> cfQuantityTemplateRelationDO.getBelongQuantity().getId()).collect(Collectors.toList());
        existQuantityIds.add(-1l);
        ExtGridData<CfQuantityVO> retVal = new ExtGridData<>();
        List<CfQuantityDO> quantityDOS;
        if (1 == type.intValue()) {
            quantityDOS = quantityService.list(Restrictions.in("id", existQuantityIds));
        } else {
            quantityDOS = quantityService.list(Restrictions.not(Restrictions.in("id", existQuantityIds)));
        }
        List<CfQuantityVO> vos = new ArrayList<>();
        quantityDOS.forEach(cfQuantityDO -> {
            CfQuantityVO quantityVO = new CfQuantityVO(cfQuantityDO);
            vos.add(quantityVO);
        });
        retVal.setResults(vos);
        return retVal;
    }

    public void deleteWithCascade(Long[] toDelIds) {
        List<CfQuantityDO> quantities = quantityService.getByIds(toDelIds);
        quantities.forEach(quantity -> quantityService.delete(quantity));
    }
}

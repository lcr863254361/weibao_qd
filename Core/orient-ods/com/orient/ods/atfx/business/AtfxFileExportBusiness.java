package com.orient.ods.atfx.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.ods.atfx.bean.MeasurementPathBean;
import com.orient.ods.atfx.model.*;
import com.orient.sysmodel.domain.quantity.CfQuantityInstanceDO;
import com.orient.sysmodel.domain.quantity.CwmSysNumberunitDO;
import com.orient.utils.CommonTools;
import com.orient.web.base.BaseBusiness;
import de.rechner.openatfx.util.ODSHelper;
import org.asam.ods.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by mengbin on 16/7/20.
 * Purpose:
 * Detail:
 */
@Component
public class AtfxFileExportBusiness extends BaseBusiness {

    @Autowired

    private AtfxFileTreeBusiness atfxFileTreeBusiness;


    /**
     * 输出ODS中的管理信息
     *
     * @param beanList
     * @param atfxSession
     * @return
     */
    public InstanceElement writeMeasurementPath(List<MeasurementPathBean> beanList, AtfxSession atfxSession) {

        if (beanList == null || beanList.size() == 0) {
            return null;
        }
        try {
            ODSNode root = atfxFileTreeBusiness.getRootNode(atfxSession);

            ApplicationElement envirment = atfxSession.getAoSession().getApplicationStructure().getElementByName(AtfxTagConstants.ORIENT_ELEM_AOENVIMENT);
            ApplicationRelation env_aoTest = envirment.getRelationsByBaseName(AoEnvirmentNode.Relation_AOTest)[0];
            InstanceElement envInstance = envirment.createInstance("OrientExport");


            ApplicationElement aoTest = atfxSession.getAoSession().getApplicationStructure().getElementByName(AtfxTagConstants.ORIENT_ELEM_AOTest);

            MeasurementPathBean project = beanList.get(0);
            String projName = project.getDisplayName();
            String modelId = project.getModelId();
            String dataId = project.getDataId();
            createModelColumnMetaInstance(modelId, atfxSession);
            InstanceElement aoTestInstance = aoTest.createInstance(projName);
            envInstance.createRelation(env_aoTest, aoTestInstance);
            //补齐额外属性
            suppleMentExtraAttr(modelId, dataId, aoTest, aoTestInstance, atfxSession);
            InstanceElement parentInstance = aoTestInstance;

            //如果有多层,则创建多层次AoSubTest
            if (beanList.size() > 1) {
                InstanceElement subInstance = null;
                for (int i = beanList.size() - 1; i > 0; i--) {
                    MeasurementPathBean childNode = beanList.get(i);
                    createModelColumnMetaInstance(childNode.getModelId(), atfxSession);
                    ApplicationElement aoSubTest = atfxSession.getAoSession().getApplicationStructure().getElementByName(AtfxTagConstants.ORIENT_ELEM_AOSubTest);
                    //创建树形AoSubTest结构
                    String childNodeModelId = childNode.getModelId();
                    String childNodeDataId = childNode.getDataId();
                    //创建实例
                    InstanceElement aoSubTestInstance = createSubTestInstance(childNodeModelId, childNodeDataId, childNode.getDisplayName(), atfxSession);
                    //创建关系
                    if (i != beanList.size() - 1) {
                        ApplicationRelation rel = atfxSession.getAoSession().getApplicationStructure().createRelation();
                        rel.setElem1(aoSubTestInstance.getApplicationElement());
                        rel.setElem2(subInstance.getApplicationElement());
                        rel.setBaseRelation(aoSubTest.getAllRelations()[3].getBaseRelation());
                        rel.setRelationName("relationName");
                        rel.setInverseRelationName("InverseRelationNames");
                        aoSubTestInstance.createRelation(rel, subInstance);
                    }
                    subInstance = aoSubTestInstance;
                }
            }
            return parentInstance;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 补齐模型的其他属性信息
     *
     * @param modelId         模型Id
     * @param dataId          数据ID
     * @param element         元数据描述
     * @param instanceElement 实例描述
     * @param atfxSession
     */
    private void suppleMentExtraAttr(String modelId, String dataId, ApplicationElement element, InstanceElement instanceElement, AtfxSession atfxSession) {
        IBusinessModel model = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
        List<IBusinessColumn> businessColumns = model.getAllBcCols();
        try {
            createStringAttr(element, "ORIENT_DATAID", 100);
            createStringAttr(element, "ORIENT_MODELID", 100);

            for (IBusinessColumn businessColumn : businessColumns) {
                if (canPutOds(businessColumn)) {
                    ApplicationAttribute attribute = element.createAttribute();
                    attribute.setName(businessColumn.getCol().getName());
                    attribute.setDataType(getDataType(businessColumn));
                    attribute.setLength(null != businessColumn.getCol().getMaxLength() ? businessColumn.getCol().getMaxLength().intValue() : 100);
                }
            }
            Map<String, String> data = orientSqlEngine.getBmService().createModelQuery(model).findById(dataId);
            businessModelService.dataChangeModel(orientSqlEngine, model, data, true);
            instanceElement.setValue(ODSHelper.createStringNVU("ORIENT_DATAID", dataId));
            instanceElement.setValue(ODSHelper.createStringNVU("ORIENT_MODELID", modelId));
            for (Map.Entry<String, String> entry : data.entrySet()) {
                String key = entry.getKey();
                IBusinessColumn ibc = model.getBusinessColumnBySName(key);
                String value = entry.getValue();
                NameValueUnit nameValueUnit = this.getNameValueUnit(ibc, value);
                if (nameValueUnit != null) {
                    instanceElement.setValue(nameValueUnit);
                }
            }
        } catch (AoException e) {
            e.printStackTrace();
        }

    }


    public void createStringAttr(ApplicationElement element, String attrName, int attrLength) {
        try {
            ApplicationAttribute attr = element.createAttribute();
            attr.setName(attrName);
            attr.setDataType(DataType.DT_STRING);
            attr.setLength(attrLength);
        } catch (AoException e) {
            e.printStackTrace();
        }
    }

    public boolean canPutOds(IBusinessColumn businessColumn) {
        return !businessColumn.getColType().equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Relation);
    }

    /**
     * @param childNodeModelId
     * @param childNodeDataId
     * @return 创建AoSubTest 结构 及 实例
     */
    private InstanceElement createSubTestInstance(String childNodeModelId, String childNodeDataId, String displayName, AtfxSession atfxSession) {
        ApplicationElement modelSubTest;
        BaseElement subTest;
        InstanceElement aoSubTestInstance = null;
        try {
            IBusinessModel model = businessModelService.getBusinessModelById(childNodeModelId, EnumInter.BusinessModelEnum.Table);
            subTest = atfxSession.getAoSession().getBaseStructure().getElementByType(AtfxTagConstants.BASE_ELEM_AOSubTest);
            modelSubTest = atfxSession.getAoSession().getApplicationStructure().createElement(subTest);
            modelSubTest.setName(model.getS_table_name().replaceAll("_", ""));
            aoSubTestInstance = modelSubTest.createInstance(model.getDisplay_name());
            suppleMentExtraAttr(childNodeModelId, childNodeDataId, modelSubTest, aoSubTestInstance, atfxSession);
        } catch (AoException e) {
            e.printStackTrace();
        }
        return aoSubTestInstance;
    }

    /**
     * 根据模型ID 创建模型字段描述 描述英文名与中文名映射
     *
     * @param modelId 模型ID
     */
    private void createModelColumnMetaInstance(String modelId, AtfxSession atfxSession) throws AoException {

        IBusinessModel model = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
        ApplicationElement columnMetaElement = atfxSession.getAoSession().getApplicationStructure().getElementByName(AtfxTagConstants.ORIENT_ELEM_AOModelColumnMeta);
        model.getAllBcCols().forEach(iBusinessColumn -> {
            try {
                InstanceElement aoMetaInstance = columnMetaElement.createInstance(iBusinessColumn.getCol().getName());
                aoMetaInstance.setValue(ODSHelper.createStringNVU("ModelId", modelId));
                aoMetaInstance.setValue(ODSHelper.createStringNVU("DisplayName", iBusinessColumn.getDisplay_name()));
            } catch (AoException e) {
                e.printStackTrace();
            }
        });
    }

    public void writeMeasurementAndQuantities(InstanceElement parentInstance, AtfxSession atfxSession, List<CfQuantityInstanceDO> quantityInstanceDOs, String businessModelName) {
        try {
            ApplicationRelation subTest_mesurment = parentInstance.getApplicationElement().getRelationsByBaseName(AoSubTestNode.Relation_AOSubTest_AoMeasurement)[0];

            ApplicationElement orient_measurment = atfxSession.getAoSession().getApplicationStructure().getElementByName(AtfxTagConstants.ORIENT_ELEM_AOMeasurement);
            InstanceElement measurementInstrance = orient_measurment.createInstance(businessModelName);
            parentInstance.createRelation(subTest_mesurment, measurementInstrance);
            ApplicationRelation measure_measureQuantity = orient_measurment.getRelationsByBaseName(AoMeasurementNode.Relation_AoMQuantity)[0]; //
            ApplicationElement orient_measurment_quaintity = atfxSession.getAoSession().getApplicationStructure().getElementByName(AtfxTagConstants.ORIENT_ELEM_AOMeasurementQuantity);
            //create AoSubmatrix
            ApplicationRelation aoSubMatrixRel = orient_measurment.getRelationsByBaseName(AoMeasurementNode.Relation_AoSubMatrix)[0];
            ApplicationElement subMatrixElem = aoSubMatrixRel.getElem2();
            InstanceElement matrixInstance = subMatrixElem.createInstance(businessModelName);
            matrixInstance.setValue(ODSHelper.createLongNVU("SubMatrixNoRows", 0));
            measurementInstrance.createRelation(aoSubMatrixRel, matrixInstance);
            //create unit
            List<String> unitNames = quantityInstanceDOs.stream().map(CfQuantityInstanceDO::getNumberunitDO).map(CwmSysNumberunitDO::getUnit).distinct().collect(Collectors.toList());
            Map<String, InstanceElement> unitInstanceMap = new HashMap<>(unitNames.size());
            ApplicationElement orient_unit = atfxSession.getAoSession().getApplicationStructure().getElementByName(AtfxTagConstants.ORIENT_ELEM_AOUnit);
            for (String unitName : unitNames) {
                InstanceElement unitInstance = orient_unit.createInstance(unitName);
                unitInstanceMap.put(unitName, unitInstance);
            }
            ApplicationRelation quantity_unit = orient_unit.getRelationsByBaseName("measurement_quantities")[0];
            //create quantity
            for (CfQuantityInstanceDO quantityInstanceDO : quantityInstanceDOs) {
                InstanceElement measurmentQuantity = orient_measurment_quaintity.createInstance(quantityInstanceDO.getBelongQuantity().getName());
                NameValueUnit[] nvu = new NameValueUnit[1];
                nvu[0] = ODSHelper.createEnumNVU("Datatype", getDataTypeByOrientQuantityType(quantityInstanceDO.getBelongQuantity().getDatatype()).value());
                measurmentQuantity.setValueSeq(nvu);
                measurementInstrance.createRelation(measure_measureQuantity, measurmentQuantity);
                String unit = quantityInstanceDO.getNumberunitDO().getUnit();
                InstanceElement unitInstance = unitInstanceMap.get(unit);
                unitInstance.createRelation(quantity_unit, measurmentQuantity);
            }
        } catch (AoException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出measurement
     *
     * @param parentInstance
     * @param atfxSession
     * @param businessModelmodel
     * @param datas
     * @return
     */
    public boolean writeMeasurement(InstanceElement parentInstance, AtfxSession atfxSession, IBusinessModel businessModelmodel, Map<IBusinessColumn, List<String>> datas) {

        try {
            ApplicationRelation subTest_mesurment = parentInstance.getApplicationElement().getRelationsByBaseName(AoSubTestNode.Relation_AOSubTest_AoMeasurement)[0];

            ApplicationElement orient_measurment = atfxSession.getAoSession().getApplicationStructure().getElementByName(AtfxTagConstants.ORIENT_ELEM_AOMeasurement);
            InstanceElement measurementInstrance = orient_measurment.createInstance(businessModelmodel.getDisplay_name());
            T_LONGLONG measurementId = measurementInstrance.getId();
            parentInstance.createRelation(subTest_mesurment, measurementInstrance);


            ApplicationRelation measure_measureQuantity = orient_measurment.getRelationsByBaseName(AoMeasurementNode.Relation_AoMQuantity)[0]; //
            ApplicationElement orient_measurment_quaintity = atfxSession.getAoSession().getApplicationStructure().getElementByName(AtfxTagConstants.ORIENT_ELEM_AOMeasurementQuantity);

            //create AoSubmatrix
            ApplicationRelation aoSubMatrixRel = orient_measurment.getRelationsByBaseName(AoMeasurementNode.Relation_AoSubMatrix)[0];
            ApplicationElement subMatrixElem = aoSubMatrixRel.getElem2();
            InstanceElement matrixInstance = subMatrixElem.createInstance(businessModelmodel.getDisplay_name());
            List<String> values = (List<String>) datas.values().toArray()[0];
            matrixInstance.setValue(ODSHelper.createLongNVU("SubMatrixNoRows", values.size()));
            measurementInstrance.createRelation(aoSubMatrixRel, matrixInstance);

            ApplicationRelation meq_aoLoc = orient_measurment_quaintity.getRelationsByBaseName("local_columns")[0]; //测试变量与通道的关系

            for (IBusinessColumn column : datas.keySet()) {

                //测试变量
                InstanceElement measurmentQuantity = orient_measurment_quaintity.createInstance(column.getDisplay_name());
                NameValueUnit[] nvu = new NameValueUnit[1];

                nvu[0] = ODSHelper.createEnumNVU("Datatype", getDataType(column).value());
                measurmentQuantity.setValueSeq(nvu);
                measurementInstrance.createRelation(measure_measureQuantity, measurmentQuantity);

                //
                ApplicationRelation aoLoclColumnRel = subMatrixElem.getRelationsByBaseName(AoSubMatrixNode.Relation_AOLocalColumn)[0];
                ApplicationElement localColumn = aoLoclColumnRel.getElem2();
                InstanceElement localColumnInstance = localColumn.createInstance(column.getDisplay_name());
                measurmentQuantity.createRelation(meq_aoLoc, localColumnInstance);
                matrixInstance.createRelation(aoLoclColumnRel, localColumnInstance);
                localColumnInstance.setValue(ODSHelper.createEnumNVU("SequenceRepresentation", 7));
                localColumnInstance.setValue(ODSHelper.createShortNVU("GlobalFlag", Short.valueOf("15")));


                NameValueUnit nameValueUnit = getNameValUnit(column, datas.get(column));


                atfxSession.getAoSession().setContextString("write_mode", "file");
                localColumnInstance.setValue(nameValueUnit);
                atfxSession.getAoSession().setContextString("write_mode", "database");

            }

        } catch (Exception e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;

    }


    private DataType getDataType(IBusinessColumn column) {
        if (column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Boolean
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_SingleEnum
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_MultiEnum
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_SingleTableEnum
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_MultiTableEnum
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Text
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Ods
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Hadoop
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_File
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Dynamic
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_NameValue
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_SubTable
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Simple) {
            return DataType.DT_STRING;
        } else if (column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Date
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_DateTime) {
            return DataType.DT_DATE;
        } else if (column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Integer) {
            return DataType.DT_LONG;
        } else if (column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_BigInteger
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Decimal) {
            return DataType.DT_LONGLONG;
        } else if (column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Float) {
            return DataType.DT_FLOAT;
        }
        if (column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Double) {
            return DataType.DT_DOUBLE;
        } else {
            return DataType.DT_UNKNOWN;
        }
    }

    private DataType getDataTypeByOrientQuantityType(String datatype) {
        if ("DT_LONG".equals(datatype)) {
            return DataType.DT_LONG;
        } else if ("DT_LONGLONG".equals(datatype)) {
            return DataType.DT_LONGLONG;
        } else if ("DT_FLOAT".equals(datatype)) {
            return DataType.DT_FLOAT;
        } else if ("DT_DOUBLE".equals(datatype)) {
            return DataType.DT_DOUBLE;
        }
        return DataType.DT_UNKNOWN;
    }

    public NameValueUnit getNameValueUnit(IBusinessColumn column, String value) {
        if (null == column) {
            return null;
        }
        String columnName = column.getCol().getName();
        if (column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Boolean
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_SingleEnum
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_MultiEnum
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_SingleTableEnum
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_MultiTableEnum
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Text
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Ods
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Hadoop
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_File
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Dynamic
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_NameValue
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_SubTable
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Simple) {
            // 字符串
            return ODSHelper.createStringNVU(columnName, value);
        } else if (column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Date) {
            // 日期（方便导入，以字符串存储）
            return ODSHelper.createStringNVU(columnName, value);
        } else if (column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_DateTime) {
            // 时间（方便导入，以字符串存储）
            return ODSHelper.createStringNVU(columnName, value);
        } else if (column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Integer) {
            // 数字
            if (CommonTools.isNumber(value)) {
                return ODSHelper.createLongNVU(columnName, Integer.valueOf(value));
            }
        } else if (column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_BigInteger
                || column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Decimal) {
            // 大数字
            if (CommonTools.isNumber(value)) {
                return ODSHelper.createLongLongNVU(columnName, Long.valueOf(value));
            }
        } else if (column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Float) {
            // 单精度小数
            if (CommonTools.isNumber(value)) {
                return ODSHelper.createFloatNVU(columnName, Float.valueOf(value));
            }
        }
        if (column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Double) {
            // 双精度小数
            if (CommonTools.isNumber(value)) {
                return ODSHelper.createDoubleNVU(columnName, Double.valueOf(value));
            }
        }
        return null;
    }


    private NameValueUnit getNameValUnit(IBusinessColumn column, List<String> datas) throws AoException {

        DataType dataType = getDataType(column);
        if (dataType == DataType.DT_LONGLONG) {
            T_LONGLONG data[] = new T_LONGLONG[datas.size()];
            int index = 0;
            for (String strVal : datas) {
                if (strVal.isEmpty()) {
                    strVal = "0";
                }
                data[index++] = new T_LONGLONG(0, Long.valueOf(strVal).intValue());
            }
            NameValueUnit emptyNVU = ODSHelper.createEmptyNVU("Values", dataType);
            emptyNVU.value.u.longlongSeq(data);
            return emptyNVU;
        } else if (dataType == DataType.DT_LONG) {
            int data[] = new int[datas.size()];
            int index = 0;
            for (String strVal : datas) {
                if (strVal.isEmpty()) {
                    strVal = "0";
                }
                data[index++] = Integer.valueOf(strVal).intValue();
            }
            NameValueUnit emptyNVU = ODSHelper.createEmptyNVU("Values", dataType);
            emptyNVU.value.u.longSeq(data);
            return emptyNVU;
        } else if (dataType == DataType.DT_FLOAT) {
            float data[] = new float[datas.size()];
            int index = 0;
            for (String strVal : datas) {
                if (strVal.isEmpty()) {
                    strVal = "0";
                }
                data[index++] = Float.valueOf(strVal).floatValue();
            }
            NameValueUnit emptyNVU = ODSHelper.createEmptyNVU("Values", dataType);
            emptyNVU.value.u.floatSeq(data);
            return emptyNVU;
        } else if (dataType == DataType.DT_DOUBLE) {
            double data[] = new double[datas.size()];
            int index = 0;
            for (String strVal : datas) {
                if (strVal.isEmpty()) {
                    strVal = "0";
                }
                data[index++] = Double.valueOf(strVal).doubleValue();

            }
            NameValueUnit emptyNVU = ODSHelper.createEmptyNVU("Values", dataType);
            emptyNVU.value.u.doubleSeq(data);
            return emptyNVU;
        }
        return null;
    }


}

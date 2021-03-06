package com.orient.weibao.business;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.download.bean.inform.BaseEntity;
import com.orient.download.bean.inform.CurrentStateBean;
import com.orient.download.bean.inform.InformBean;
import com.orient.edm.init.FileServerConfig;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.modeldata.business.ModelDataBusiness;
import com.orient.modeldata.util.FtpFileUtil;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.sqlengine.internal.sys.cmd.NumberUnitCmd;
import com.orient.utils.*;
import com.orient.utils.ExcelUtil.Excel;
import com.orient.utils.ExcelUtil.reader.DataEntity;
import com.orient.utils.ExcelUtil.reader.FieldEntity;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.ExtGridData;
import com.orient.web.base.ExtSorter;
import com.orient.web.util.UserContextUtil;
import com.orient.weibao.bean.DeviceInstBean;
import com.orient.weibao.bean.ImportDeviceBean;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.utils.MatrixToImageWriter;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.swetake.util.Qrcode;
import jdk.internal.org.objectweb.asm.Type;
import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import sun.awt.SunHints;
import sun.awt.image.PNGImageDecoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

import static com.orient.utils.JsonUtil.getJavaCollection;
import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2018-12-12 10:01
 */
@Service
public class SparePartsMgrBusiness extends BaseBusiness {

    @Autowired
    ModelDataBusiness modelDataBusiness;
    @Autowired
    FileServerConfig fileServerConfig;
    @Autowired
    MetaDAOFactory metaDAOFactory;
    @Autowired
    JdbcTemplate jdbcTemplate;

    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;


    /**
     * ????????????
     *
     * @param count
     * @param modelId
     * @param spareId
     * @return
     */
    public String easyAdd(String count, String modelId, String spareId, String productId) {
        String msg = "??????????????????";
        IBusinessModel ibm = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceLifeBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEVICE_LIFE_CYCLE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS, schemaId, EnumInter.BusinessModelEnum.Table);
        String tableId = deviceLifeBM.getId();
//        ibm.setReserve_filter(" AND T_SPARE_PARTS_"+schemaId+"_ID = '"+spareId+"'");
//        IBusinessModelQuery modelquery = orientSqlEngine.getBmService().createModelQuery(ibm);
//        List<Map<String,Object>> deviceInstList = modelquery.orderAsc("MAX(C_SERIAL_NUMBER_3207"+modelId+")").list();
        String sql = "select max(to_number(C_SERIAL_NUMBER_" + modelId + ")) from T_SPARE_PARTS_SHILI_" + schemaId + " where T_SPARE_PARTS_" + schemaId + "_ID = '" + spareId + "'";
        int vehicleCount = (int) metaDaoFactory.getJdbcTemplate().execute(sql, new PreparedStatementCallback() {
            @Override
            public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.execute();
                ResultSet rs = preparedStatement.getResultSet();
                rs.next();
                return rs.getInt(1);
            }
        });
        int startCount = vehicleCount;
        int countInt = Integer.parseInt(count);
        try {
//            if(deviceInstList != null && deviceInstList.size() >0){
//                startCount = Integer.parseInt(deviceInstList.get(0).get("C_SERIAL_NUMBER_"+modelId).toString());
//            }
            int deviceCount = 0;
            for (int i = 0; i < countInt; i++) {
                Map map = new HashMap<>();
                map.put("C_POSITION_" + modelId, "");
                map.put("C_LIEZHUANG_TIME_" + modelId, CommonTools.util2Sql(new Date()));
                map.put("C_DEVICE_NAME_" + modelId, spareId);
                map.put("T_SPARE_PARTS_" + schemaId + "_ID", spareId);
                map.put("C_SERIAL_NUMBER_" + modelId, ++startCount);
                map.put("C_STATE_" + modelId, "??????");
                map.put("T_PRODUCT_STRUCTURE_" + schemaId + "_ID", productId);
                map.put("C_VERSION_" + modelId, 0);
                String deviceInstId = orientSqlEngine.getBmService().insertModelData(ibm, map);
                Map lifeCycleMap = UtilFactory.newHashMap();
                lifeCycleMap.put("T_SPARE_PARTS_SHILI_" + schemaId + "_ID", deviceInstId);
                lifeCycleMap.put("C_START_TIME_" + deviceLifeBM.getId(), CommonTools.util2Sql(new Date()));
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String editTime = date.format(new Date());
                lifeCycleMap.put("C_UPDATE_TIME_" + deviceLifeBM.getId(), editTime);
                lifeCycleMap.put("C_DEVICE_STATE_" + deviceLifeBM.getId(), "??????");
                orientSqlEngine.getBmService().insertModelData(deviceLifeBM, lifeCycleMap);
                deviceCount++;
            }
            if (deviceCount > 0) {
                deviceBM.setReserve_filter("AND ID='" + spareId + "'");
                List<Map<String, Object>> spareList = orientSqlEngine.getBmService().createModelQuery(deviceBM).list();
                Map spareMap = spareList.get(0);
                String deviceVersion = CommonTools.Obj2String(spareMap.get("C_VERSION_" + deviceBM.getId()));
                deviceVersion = String.valueOf(Integer.parseInt(deviceVersion) + 1);
                spareMap.put("C_VERSION_" + deviceBM.getId(), deviceVersion);
                orientSqlEngine.getBmService().updateModelData(deviceBM, spareMap, spareId);
            }

        } catch (Exception e) {
            msg = e.toString();
        }
        return msg;
    }

    public ExtGridData<Map> getSparePartsInstData(String orientModelId, String isView, Integer page, Integer pagesize, String customerFilter,
                                                  Boolean dataChange, String sort, String spareName, String spareId, String nodeContent) {
        ExtGridData<Map> retVal = new ExtGridData<>();
        String userId = UserContextUtil.getUserId();
        EnumInter.BusinessModelEnum modelTypeEnum = "1".equals(isView) ? EnumInter.BusinessModelEnum.View : EnumInter.BusinessModelEnum.Table;
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, orientModelId, null, modelTypeEnum);
        IBusinessModel deviceModel = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS, schemaId, EnumInter.BusinessModelEnum.Table);
        if (spareId != null & !"".equals(spareId)) {
            businessModel.setReserve_filter(" AND T_SPARE_PARTS_" + schemaId + "_ID='" + spareId + "'");
        } else if (nodeContent != null & !"".equals(nodeContent)) {
            deviceModel.setReserve_filter("and c_device_name_" + deviceModel.getId() + " like '%" + nodeContent + "%'");
//            List<Map> deviceList=orientSqlEngine.getBmService().createModelQuery(deviceModel).list();
            String sql = "select id from T_SPARE_PARTS_" + schemaId + " where c_device_name_" + deviceModel.getId() + " like '%" + nodeContent + "%'";
            List<Map<String, Object>> deviceList = metaDaoFactory.getJdbcTemplate().queryForList(sql.toString());
            if (deviceList.size() > 0) {
//                Iterator iterator=deviceList.iterator();
                String id = "";
//                while (iterator.hasNext()){
//                    id+=(String)iterator.next()+",";
//                }
                for (Map map : deviceList) {
                    id += (String) map.get("ID") + ",";
                }
                id = " (" + id.substring(0, id.length() - 1) + ")";
                businessModel.clearCustomFilter();
                businessModel.setReserve_filter(" and c_device_name_" + businessModel.getId() + " in " + id);
//                " union all select from T_SPARE_PARTS_SHILI_"+schemaId+" where c_device_name_"+businessModel.getId()+" not in "+id);
//                businessModel.setReserve_filter(" and c_device_name_"+businessModel.getId()+" not in "+id);
            }
        }
        if (!org.apache.commons.lang.StringUtils.isEmpty(customerFilter)) {
            Map clazzMap = new HashMap();
            List<CustomerFilter> customerFilters = getJavaCollection(new CustomerFilter(), customerFilter, clazzMap);
            businessModel.clearAllFilter();
            businessModel.appendCustomerFilter(new CustomerFilter("T_SPARE_PARTS_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, spareId));
            customerFilters.forEach(cs -> businessModel.appendCustomerFilter(cs));
        }
        long count = orientSqlEngine.getBmService().createModelQuery(businessModel).count();
        IBusinessModelQuery businessModelQuery = orientSqlEngine.getBmService().createModelQuery(businessModel);
        if (null != page && null != pagesize) {
            int start = (page - 1) * pagesize;
            int end = page * pagesize > count ? (int) count : (page * pagesize);
            businessModelQuery.page(start, end);
        }
        if (!StringUtil.isEmpty(sort)) {
            List<ExtSorter> sorters = JsonUtil.getJavaCollection(new ExtSorter(), sort);
            sorters.forEach(loopSort -> {
                if ("ASC".equals(loopSort.getDirection())) {
                    businessModelQuery.orderAsc(loopSort.getProperty());
                } else if ("DESC".equals(loopSort.getDirection())) {
                    businessModelQuery.orderDesc(loopSort.getProperty());
                }
            });
        }
        List<Map> dataList = businessModelQuery.orderAsc("TO_NUMBER(ID)").list();
        if (spareName != null & !"".equals(spareName)) {
            for (Map map : dataList) {
                String deviceInstId = (String) map.get("ID");
                map.put("C_KEY_ID_" + orientModelId, deviceInstId);
                map.put("C_DEVICE_NAME_" + orientModelId, spareName);
            }
        } else {
            for (Map map : dataList) {
                String deviceInstId = (String) map.get("ID");
                String deviceId = CommonTools.Obj2String(map.get("C_DEVICE_NAME_" + orientModelId));
                String sql = "select c_device_name_" + deviceModel.getId() + " from T_SPARE_PARTS_" + schemaId + " where id='" + deviceId + "'";
                List<Map<String, Object>> deviceList = metaDaoFactory.getJdbcTemplate().queryForList(sql.toString());
                map.put("C_DEVICE_NAME_" + orientModelId, deviceList.get(0).get("C_DEVICE_NAME_" + deviceModel.getId()));
                map.put("C_KEY_ID_" + orientModelId, deviceInstId);
            }
        }

        if (dataChange) {
            businessModelService.dataChangeModel(orientSqlEngine, businessModel, dataList, false);
            modelDataBusiness.customDataChange(businessModel, dataList);
        }

        retVal.setResults(dataList);
        retVal.setTotalProperty(count);
        return retVal;
    }

    public void delSparePartsData(String id, String productId) {
        String deviceTableName = PropertyConstant.SPARE_PARTS;
        String deviceInstTableName = PropertyConstant.SPARE_PARTS_INST;
        String productTableName = PropertyConstant.PRODUCT_STRUCTURE;
        IBusinessModel deviceBM = businessModelService.getBusinessModelBySName(deviceTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceInstBM = businessModelService.getBusinessModelBySName(deviceInstTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel productBM = businessModelService.getBusinessModelBySName(productTableName, schemaId, EnumInter.BusinessModelEnum.Table);
//        String[] ids = id.split("\\,");
//        for (String deviceId : ids) {
//            deviceInstBM.setReserve_filter("AND "+deviceTableName+"_"+schemaId+"_ID='"+deviceId+"'");
//            List<Map<String,Object>> deviceInstList=orientSqlEngine.getBmService().createModelQuery(deviceInstBM).list();
//            if (deviceInstList.size()>0){
//                for (Map deviceInstMap:deviceInstList){
//                    String deviceInstId=CommonTools.Obj2String(deviceInstMap.get("ID"));
//                    productBM.setReserve_filter("AND ID='" + productId + "'"+
//                            " AND C_DEVICE_INST_ID_"+productBM.getId()+"='"+deviceInstId+"'");
//                    List<Map<String,Object>> productList=orientSqlEngine.getBmService().createModelQuery(productBM).list();
//                    if (productList.size()>0){
//                        Map productMap=productList.get(0);
//                        productMap.put("C_DEVICE_INST_ID_"+productBM.getId(),"");
//                        orientSqlEngine.getBmService().updateModelData(productBM,productMap,productId);
//                    }
//                }
//            }
//        }
        orientSqlEngine.getBmService().deleteCascade(deviceBM, id);
    }

    public void delSparePartsInstData(String id, String productId, String deviceId) {
        String deviceInstTableName = PropertyConstant.SPARE_PARTS_INST;
        String deviceTableName = PropertyConstant.SPARE_PARTS;
        String productTableName = PropertyConstant.PRODUCT_STRUCTURE;
        IBusinessModel deviceInstBM = businessModelService.getBusinessModelBySName(deviceInstTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceBM = businessModelService.getBusinessModelBySName(deviceTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        deviceBM.setReserve_filter("AND ID='" + deviceId + "'");
        List<Map<String, Object>> spareList = orientSqlEngine.getBmService().createModelQuery(deviceBM).list();
        Map spareMap = spareList.get(0);
        String deviceVersion = CommonTools.Obj2String(spareMap.get("C_VERSION_" + deviceBM.getId()));
        deviceVersion = String.valueOf(Integer.parseInt(deviceVersion) + 1);
        spareMap.put("C_VERSION_" + deviceBM.getId(), deviceVersion);
        orientSqlEngine.getBmService().updateModelData(deviceBM, spareMap, deviceId);
        orientSqlEngine.getBmService().deleteCascade(deviceInstBM, id);
//        }
    }

    /**
     * ???????????????????????????
     *
     * @param: id
     * @param: serialNumber
     * @param: spareName
     * @return:
     */
    public String encoderQRCode(String id, String serialNumber, String spareName) {
        //???????????????????????????????????????
        String folder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.EXPORT_ROOT);
        //????????????????????????????????????????????????
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        String fileName = spareName + "-" + serialNumber + ".png";
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
        String imgType = "png";
        //?????????????????????
        this.encoderQRCode(id, fileServerConfig.getFtpHome() + folder + finalFileName, imgType, 7);
        String name = "?????????" + spareName + "??????" + id;
        int imgSize = 67 + 12 * (7 - 1);
//        pressText(name,fileServerConfig.getFtpHome() + folder + finalFileName,5,Color.BLACK,imgSize);
        return folder + finalFileName;
    }

    /**
     * ???????????????(QRCode)??????
     *
     * @param content ????????????
     * @param imgPath ????????????
     * @param imgType ????????????
     * @param size    ???????????????
     */
    public void encoderQRCode(String content, String imgPath, String imgType, int size) {
        try {
            BufferedImage bufImg = this.qRCodeCommon(content, imgType, size);
            //???imgPath????????????????????????
            File imgFile = new File(imgPath);
            // ???????????????QRCode??????
            ImageIO.write(bufImg, imgType, imgFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ???????????????(QRCode)?????????????????????
     *
     * @param content ????????????
     * @param imgType ????????????
     * @param size    ???????????????
     * @return
     */
    private BufferedImage qRCodeCommon(String content, String imgType, int size) {
        BufferedImage bufImg = null;
        try {
            Qrcode qrcodeHandler = new Qrcode();
            // ?????????????????????????????????L(7%)???M(15%)???Q(25%)???H(30%)????????????????????????????????????????????????????????????????????????????????????
            qrcodeHandler.setQrcodeErrorCorrect('M');
            qrcodeHandler.setQrcodeEncodeMode('B');
            // ????????????????????????????????????1-40???????????????????????????????????????????????????
            qrcodeHandler.setQrcodeVersion(size);
            // ????????????????????????????????????????????????
            byte[] contentBytes = content.getBytes("utf-8");
            // ????????????
            int imgSize = 67 + 12 * (size - 1);
            bufImg = new BufferedImage(imgSize, imgSize, TYPE_INT_RGB);
            Graphics2D gs = bufImg.createGraphics();
            // ??????????????????
            gs.setBackground(Color.WHITE);
            gs.clearRect(0, 0, imgSize, imgSize);
            // ??????????????????> BLACK
            gs.setColor(Color.BLACK);
            // ???????????????????????????????????????????????????
            int pixoff = 2;
            // ????????????> ?????????
            if (contentBytes.length > 0 && contentBytes.length < 800) {
                boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
                for (int i = 0; i < codeOut.length; i++) {
                    for (int j = 0; j < codeOut.length; j++) {
                        if (codeOut[j][i]) {
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                        }
                    }
                }
            } else {
                throw new Exception("QRCode content bytes  length = " + contentBytes.length + " not in [0, 800].");
            }
            gs.dispose();
            bufImg.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufImg;
    }

    public String pressText(String pressText, String targetImg, int fontStyle, Color color, int fontSize, int width, int height, BufferedImage image, String spareName, String serialNumber, boolean isMorePrint) {
        String previewQrcodeUrl = "";
        try {
            pressText = new String(pressText.getBytes(), "utf-8");
            //??????????????????????????????
            File file = new File(targetImg);
            Image src = ImageIO.read(file);
            int imageW = src.getWidth(null);
            int imageH = src.getHeight(null);

            //???????????????????????????????????????????????????
            BufferedImage outImage = new BufferedImage(imageW, imageH + 50, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D outg = outImage.createGraphics();
            outg.setPaint(new Color(0, 0, 0, 64));
            //???????????????????????????
            outg.drawImage(image, 0, 0, imageW, imageH, null);
            outg.setColor(Color.BLACK);
            outg.setFont(new Font("??????", fontStyle, fontSize));
            //???????????????????????????
            int everyCharWidth = outg.getFontMetrics().stringWidth("???");
            //??????????????????,????????????font???????????????????????????
            int everyCharHeight = outg.getFontMetrics().getHeight();
            String pressArray[] = pressText.split("\n");
            int pressTextWidth = outg.getFontMetrics().stringWidth(pressArray[0]);
            //??????????????????????????????
//            int textSizeCount =width/everyCharWidth;
            int textSizeCount = width * pressArray[0].length() / pressTextWidth;
//            //???????????????
//            int rowSize = pressText.length()/textSizeCount+1;
            int rowSize = pressTextWidth / width + 1;
            int additionHeight = 50;
            int deviceNameAllH = pressContentSplit(pressArray[0], outg, width, imageH, additionHeight);
            //??????????????????
            BufferedImage outImage2 = new BufferedImage(imageW, imageH + 80, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D outg2 = outImage2.createGraphics();
            outg2.setPaint(new Color(0, 0, 0, 64));
            additionHeight = 30;
            //????????????????????????????????????
            outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
            outg2.setColor(Color.BLACK);
            outg2.setFont(new Font("??????", fontStyle, fontSize));
            deviceNameAllH = pressContentSplit(pressArray[1], outg2, outImage.getWidth(), deviceNameAllH, additionHeight);

            //???????????????
            BufferedImage outImage3 = new BufferedImage(imageW, imageH + 110, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D outg3 = outImage3.createGraphics();
            outg3.setPaint(new Color(0, 0, 0, 64));
            additionHeight = 30;
            //??????????????????????????????????????????????????????
            outg3.drawImage(outImage2, 0, 0, outImage2.getWidth(), outImage2.getHeight(), null);
            outg3.setColor(Color.BLACK);
            outg3.setFont(new Font("??????", fontStyle, fontSize));
            pressContentSplit(pressArray[2], outg3, outImage2.getWidth(), deviceNameAllH, additionHeight);

//            if (pressTextWidth>width){
//                //?????????????????????
//                String pressText1=pressText.substring(0,pressText.length()/2);
//                String pressText2=pressText.substring(pressText.length()/2,pressText.length());
//                int pressTextWidth1=outg.getFontMetrics().stringWidth(pressText1);
//                int pressTextWidth2=outg.getFontMetrics().stringWidth(pressText2);
//                outg.setColor(Color.WHITE);
//                outg.drawString(pressText1,200-pressTextWidth1/2,height+(outImage.getHeight()-height)/2+12);
//                BufferedImage outImage2 = new BufferedImage(imageW, imageH+85, BufferedImage.TYPE_INT_RGB);
//                Graphics outg2=outImage2.createGraphics();
//                outg2.drawImage(outImage,0,0,outImage.getWidth(),outImage.getHeight(),null);
//                outg2.setColor(Color.WHITE);
//                outg2.setFont(new Font(null,fontStyle,fontSize));
//                outg2.drawString(pressText2,200-pressTextWidth2/2,outImage.getHeight()+(outImage2.getHeight()-outImage.getHeight())/2+5);
//                outg2.dispose();
//                outImage2.flush();
//                outImage=outImage2;
//            }else{
//                //?????????
//                outg.drawString(pressText,200-pressTextWidth/2,height+(outImage.getHeight()-height)/2+12);
//            }
            //???????????????????????????????????????
            String folder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.EXPORT_ROOT);
            //????????????????????????????????????????????????
            FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);

            String fileName = spareName + "-" + serialNumber + ".jpg";
            String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
            String newQRCodePath = folder + finalFileName;
            outg3.dispose();
            outImage3.flush();
            image = outImage3;
            BufferedImage newBufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            //??????????????????
            ((Graphics2D) newBufferedImage.createGraphics()).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            ((Graphics2D) newBufferedImage.createGraphics()).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            newBufferedImage.createGraphics().drawImage(image, 0, 0, Color.white, null);
            FileOutputStream outputStream = new FileOutputStream(fileServerConfig.getFtpHome() + folder + finalFileName);
//            image.flush();
            ImageIO.write(newBufferedImage, "JPEG", outputStream);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);
            encoder.encode(image);
            outputStream.close();
            image.flush();
            System.out.println("image press success");
            //???????????????preview
            String targetPath = CommonTools.getPreviewImagePath() + File.separator + finalFileName;
            FileOperator.copyFile(fileServerConfig.getFtpHome() + newQRCodePath, targetPath);
            previewQrcodeUrl = finalFileName;
            if (isMorePrint) {
                previewQrcodeUrl = newQRCodePath;
            }
//            newQRCodePath=targetPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return previewQrcodeUrl;
    }

    public int pressContentSplit(String pressArray, Graphics2D outg, int width, int imageH, int additionHeight) {
        int pressTextHeight = 0;
        int pressTextWidth = outg.getFontMetrics().stringWidth(pressArray);
        int textSizeCount = width * pressArray.length() / pressTextWidth;
        //???????????????
        int rowSize = pressTextWidth / width + 1;
        ///????????????????????????
        int everyRowTextH = additionHeight / rowSize;
        if (rowSize == 1) {       //??????
            outg.drawString(pressArray, 0, imageH + additionHeight / 2 + 3);
            pressTextHeight = imageH + additionHeight / 2 + 3;
        } else {                        //N???
            int[] pressTextWidthArray = new int[rowSize];
            int[] pressTextHeightArray = new int[rowSize];
            String[] pressTextArray = new String[rowSize];

            for (int i = 0; i < pressTextWidthArray.length; i++) {
                //????????????
                if (i == rowSize - 1) {
                    pressTextArray[i] = pressArray.substring(textSizeCount * i, pressArray.length());
                } else {
                    pressTextArray[i] = pressArray.substring(textSizeCount * i, textSizeCount * (i + 1));
                }
                //????????????????????????
                pressTextHeightArray[i] = everyRowTextH;
                //??????drawString??????????????????x,y????????????????????????
//                int pressTextHeight;
                if (i == 0) {
                    pressTextHeight = imageH + pressTextHeightArray[i];
                } else {
                    pressTextHeight = imageH + pressTextHeightArray[i] * (i + 1);
                }
                outg.drawString(pressTextArray[i], 0, pressTextHeight);
                outg.setPaint(Color.BLACK);
                outg.drawString(pressTextArray[i], 0, pressTextHeight);
            }
        }
        return pressTextHeight;
    }


    public ExtGridData<Map> getSparePartsData(String orientModelId, String isView, Integer page, Integer pagesize, String customerFilter,
                                              Boolean dataChange, String sort, String productId, String leaf) {
        ExtGridData<Map> retVal = new ExtGridData<>();
        String userId = UserContextUtil.getUserId();
        EnumInter.BusinessModelEnum modelTypeEnum = "1".equals(isView) ? EnumInter.BusinessModelEnum.View : EnumInter.BusinessModelEnum.Table;
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, orientModelId, null, modelTypeEnum);
        IBusinessModel productModel = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
        Boolean booleaf = Boolean.valueOf(leaf);

        if (booleaf == true) {
            businessModel.setReserve_filter(" AND T_PRODUCT_STRUCTURE_" + schemaId + "_ID='" + productId + "'");
        } else {
            String allProductIds = "";
            businessModel.clearCustomFilter();
            String sql = "select * from T_PRODUCT_STRUCTURE_" + schemaId + " where id='" + productId + "'";
            List<Map<String, Object>> productList = metaDaoFactory.getJdbcTemplate().queryForList(sql.toString());
            //??????????????????????????????ID???????????????????????????????????????ID
            productModel.setReserve_filter("AND C_PID_" + productModel.getId() + " in '" + productId + "'");
            List<Map<String, Object>> productSList = orientSqlEngine.getBmService().createModelQuery(productModel).list();
            if (productSList != null && productSList.size() > 0){
                for (Map map : productSList) {
                    String allProductId = CommonTools.Obj2String(map.get("ID"));
                    allProductIds += allProductId + ",";
                }
                allProductIds = allProductIds.substring(0, allProductIds.length() - 1);
                allProductIds = allProductIds + "," + productId;
                businessModel.setReserve_filter(" and T_PRODUCT_STRUCTURE_" + schemaId + "_ID in (" + allProductIds + ")");
            }
        }
        if (!org.apache.commons.lang.StringUtils.isEmpty(customerFilter)) {
            Map clazzMap = new HashMap();
            List<CustomerFilter> customerFilters = getJavaCollection(new CustomerFilter(), customerFilter, clazzMap);
            businessModel.clearAllFilter();
            customerFilters.forEach(cs -> businessModel.appendCustomerFilter(cs));
        }
        long count = orientSqlEngine.getBmService().createModelQuery(businessModel).count();
        IBusinessModelQuery businessModelQuery = orientSqlEngine.getBmService().createModelQuery(businessModel);
        if (null != page && null != pagesize) {
            int start = (page - 1) * pagesize;
            int end = page * pagesize > count ? (int) count : (page * pagesize);
            businessModelQuery.page(start, end);
        }
        if (!StringUtil.isEmpty(sort)) {
            List<ExtSorter> sorters = JsonUtil.getJavaCollection(new ExtSorter(), sort);
            sorters.forEach(loopSort -> {
                if ("ASC".equals(loopSort.getDirection())) {
                    businessModelQuery.orderAsc(loopSort.getProperty());
                } else if ("DESC".equals(loopSort.getDirection())) {
                    businessModelQuery.orderDesc(loopSort.getProperty());
                }
            });
        }
        List<Map> dataList = businessModelQuery.orderAsc("TO_NUMBER(ID)").list();

        if (dataChange) {
            businessModelService.dataChangeModel(orientSqlEngine, businessModel, dataList, false);
            modelDataBusiness.customDataChange(businessModel, dataList);
        }

        retVal.setResults(dataList);
        retVal.setTotalProperty(count);
        return retVal;
    }

    public ExtGridData<Map> getTroubleDeviceData(String orientModelId, String isView, Integer page, Integer pagesize, Boolean dataChange, String sort, String deviceInstId, boolean isTrouble) {
        ExtGridData<Map> retVal = new ExtGridData<>();
        String userId = UserContextUtil.getUserId();
        EnumInter.BusinessModelEnum modelTypeEnum = "1".equals(isView) ? EnumInter.BusinessModelEnum.View : EnumInter.BusinessModelEnum.Table;
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, orientModelId, null, modelTypeEnum);
//        if (!org.apache.commons.lang.StringUtils.isEmpty(customerFilter)) {
//            Map clazzMap = new HashMap();
//            List<CustomerFilter> customerFilters = getJavaCollection(new CustomerFilter(), customerFilter, clazzMap);
//            customerFilters.forEach(cs -> businessModel.appendCustomerFilter(cs));
//        }
        if (!isTrouble) {
            businessModel.setReserve_filter("AND T_SPARE_PARTS_SHILI_" + schemaId + "_ID='" + deviceInstId + "'" +
                    " AND C_IS_TROUBLE_" + orientModelId + "='" + "false" + "'");
        } else {
            businessModel.setReserve_filter("AND T_SPARE_PARTS_SHILI_" + schemaId + "_ID='" + deviceInstId + "'" +
                    " AND C_IS_TROUBLE_" + orientModelId + "='" + "true" + "'");
        }
        long count = orientSqlEngine.getBmService().createModelQuery(businessModel).count();
        IBusinessModelQuery businessModelQuery = orientSqlEngine.getBmService().createModelQuery(businessModel);
        if (null != page && null != pagesize) {
            int start = (page - 1) * pagesize;
            int end = page * pagesize > count ? (int) count : (page * pagesize);
            businessModelQuery.page(start, end);
        }
        if (!StringUtil.isEmpty(sort)) {
            List<ExtSorter> sorters = JsonUtil.getJavaCollection(new ExtSorter(), sort);
            sorters.forEach(loopSort -> {
                if ("ASC".equals(loopSort.getDirection())) {
                    businessModelQuery.orderAsc(loopSort.getProperty());
                } else if ("DESC".equals(loopSort.getDirection())) {
                    businessModelQuery.orderDesc(loopSort.getProperty());
                }
            });
        }
        List<Map> dataList = businessModelQuery.list();
        for (Map troubleMap : dataList) {
            String recorderId = CommonTools.Obj2String(troubleMap.get("C_RECORDER_" + businessModel.getId()));
            if (recorderId != null && !"".equals(recorderId)) {

                StringBuilder sql = new StringBuilder();

                sql.append("select id,all_name from cwm_sys_user where id in(").append(recorderId).append(")");

                List<Map<String, Object>> userList = metaDaoFactory.getJdbcTemplate().queryForList(sql.toString());
                if (userList.size() > 0) {
                    String allName = CommonTools.Obj2String(userList.get(0).get("all_name"));
                    troubleMap.put("C_RECORDER_" + businessModel.getId(), allName);
                }
            }
        }
        if (dataChange) {
            businessModelService.dataChangeModel(orientSqlEngine, businessModel, dataList, false);
            modelDataBusiness.customDataChange(businessModel, dataList);
        }
        retVal.setResults(dataList);
        retVal.setTotalProperty(count);
        return retVal;
    }

    public ExtGridData<Map<String, Object>> getDeviceInstLifeCycle(String start, String limit, String deviceInstId) {
        List deviceLiftList = new ArrayList<>();
        int totalcount = 0;
        int limitt = Integer.parseInt(limit);
        int startt = Integer.parseInt(start);
        IBusinessModel lifeCycleBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEVICE_LIFE_CYCLE, schemaId, EnumInter.BusinessModelEnum.Table);

        lifeCycleBM.clearAllFilter();
        lifeCycleBM.setReserve_filter("and T_SPARE_PARTS_SHILI_" + schemaId + "_ID ='" + deviceInstId + "'");
//        List<Map<String, Object>> deviceInstLifeList = orientSqlEngine.getBmService().createModelQuery(lifeCycleBM).orderAsc("to_date(c_start_time_" + lifeCycleBM.getId() + ",'yyyy-mm-dd')").list();
        List<Map<String, Object>> deviceInstLifeList = orientSqlEngine.getBmService().createModelQuery(lifeCycleBM).orderAsc("to_date(c_update_time_" + lifeCycleBM.getId() + ",'yyyy-mm-dd HH24:mi:ss')").list();
        totalcount = deviceInstLifeList.size();
        int end = startt + limitt;//??????????????????
        if (deviceInstLifeList.size() < end) {
            end = deviceInstLifeList.size();
        }
        for (int i = startt; i < end; i++) {
            Map instMap = new HashMap<>();
            String lifeId = CommonTools.Obj2String(deviceInstLifeList.get(i).get("ID"));
            instMap.put("id", lifeId);
//          instMap.put("deviceName",spareName);
            String startTime = CommonTools.Obj2String(deviceInstLifeList.get(i).get("c_start_time_" + lifeCycleBM.getId()));
            instMap.put("startTime", startTime);
            String endTime = CommonTools.Obj2String(deviceInstLifeList.get(i).get("c_end_time_" + lifeCycleBM.getId()));
            instMap.put("endTime", endTime);
            String deviceInstState = CommonTools.Obj2String(deviceInstLifeList.get(i).get("c_device_state_" + lifeCycleBM.getId()));
            instMap.put("deviceState", deviceInstState);
            String deviceDesc = CommonTools.Obj2String(deviceInstLifeList.get(i).get("c_device_desc_" + lifeCycleBM.getId()));
            instMap.put("deviceDesc", deviceDesc);
            deviceLiftList.add(instMap);
        }
        return new ExtGridData<>(deviceLiftList, totalcount);
    }

    public Map getTroubleDeviceDetail(String troubleId) {
        String folderName = "voiceFileForTDevice";
        IBusinessModel troubleDeviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.TROUBLE_DEVICE_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        String modelId = troubleDeviceBM.getId();
        StringBuilder sql = new StringBuilder();
        sql.append("select c_description_" + modelId + " from T_TROUBLE_DEVICE_INS_" + schemaId + " where id=?");
        Map<String, Object> map = metaDAOFactory.getJdbcTemplate().queryForMap(sql.toString(), troubleId);
//        String detailContent=CommonTools.Obj2String(map.get("c_replace_desc_" + troubleDeviceBM.getId()));
        String detailContent = CommonTools.Obj2String(map.get("c_description_" + troubleDeviceBM.getId()));
        Map detailMap = UtilFactory.newHashMap();
        detailMap.put("detailContent", detailContent);
        String voicesql = "select * from cwm_file where DATAID='" + troubleId + "' and TABLEID='" + modelId + "' and FILETYPE='" + "amr" + "'";
        List<Map<String, Object>> fileList = jdbcTemplate.queryForList(voicesql);
        if (fileList.size() > 0) {
            String fileName = (String) fileList.get(0).get("FINALNAME");
            String fileNameMp3 = fileName.substring(0, fileName.length() - 4) + ".mp3";
//            String voiceFolderPath = getPreviewVoicePath() + File.separator + folderName;
            String imageFolderPath = fileServerConfig.getFtpHome() + File.separator + folderName;
            String targetMp3Path = imageFolderPath + File.separator + fileNameMp3;
            targetMp3Path = FileOperator.toStanderds(targetMp3Path);
            Boolean isFileExist = FileOperator.isFileExist(targetMp3Path);
            if (isFileExist) {
                String voiceUrl = "preview" + File.separator + folderName + File.separator + fileNameMp3;
                detailMap.put("voiceUrl", voiceUrl);
            }
        }
        return detailMap;
    }

    public static String getPreviewVoicePath() {
        return CommonTools.getRootPath() + File.separator + "preview";
    }

    public Map<String, Object> importDeviceData(TableEntity excelEntity, String productId) {

        IBusinessModel sparePartsBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel sparePartsInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);

        Map<String, Object> retVal = new HashMap<>();
        List<ImportDeviceBean> sparePartsList = UtilFactory.newArrayList();

        List<DataEntity> dataEntities = excelEntity.getDataEntityList();
        for (int j = 0; j < dataEntities.size(); j++) {
            ImportDeviceBean importDeviceBean = new ImportDeviceBean();
            //????????????????????????????????????????????????????????????
            List<FieldEntity> fieldEntities = dataEntities.get(j).getFieldEntityList();
            if (fieldEntities != null) {
                for (int i = 0; i < fieldEntities.size(); i++) {
                    FieldEntity fieldEntity = fieldEntities.get(i);
                    if (fieldEntity.getIsKey() == 1) {
                        continue;
                    }
                    String name = fieldEntities.get(i).getName();
                    String value = fieldEntities.get(i).getValue();
                    if ("????????????".equals(name)) {
                        if (value == null || "".equals(value)) {
                            int rowNumer = j + 2;
                            retVal.put("success", false);
                            retVal.put("msg", "???" + rowNumer + "??????" + i + "??????????????????????????????????????????????????????");
                            return retVal;
                        }
                        importDeviceBean.setName(value);
                    } else if ("??????".equals(name)) {
                        importDeviceBean.setModel(value);
                    } else if ("??????".equals(name)) {
                        importDeviceBean.setNote(value);
                    } else if ("????????????".equals(name)) {
                        importDeviceBean.setPosition(value);
                    } else if ("????????????".equals(name)) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date storeDate = null;
                        try {
                            if (value != null && !"".equals(value)) {
                                storeDate = simpleDateFormat.parse(value);
                            } else if (value == null || "".equals(value)) {
                                storeDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                            }
                            importDeviceBean.setWareHouseDate(storeDate);
                        } catch (ParseException e) {
//                            e.printStackTrace();
                            int rowNumer = j + 2;
                            retVal.put("success", false);
                            retVal.put("msg", "???" + rowNumer + "??????" + i + "???????????????????????????????????????");
                            return retVal;
                        }
                    } else if ("??????".equals(name)) {
                        if (value.contains(".")) {
                            value = value.split("\\.")[0];
                        }
                        if (value != null && !"".equals(value)) {
                            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
                            boolean isIntNumber = pattern.matcher(value).matches();
                            if (!isIntNumber) {
                                value = "0";
                            }
                            importDeviceBean.setNumber(Integer.parseInt(value));
                        }
                    } else {
                        retVal.put("success", false);
                        retVal.put("msg", "????????????,???????????????????????????");
                        return retVal;
                    }
                }
                if (!"".equals(importDeviceBean.getName())) {
                    sparePartsList.add(importDeviceBean);
                }
            }
        }
        if (sparePartsList.size() > 0) {
            for (ImportDeviceBean importDeviceBean : sparePartsList) {
                String deviceName = importDeviceBean.getName();
                String model = importDeviceBean.getModel();
                String position = importDeviceBean.getPosition();
                Date wareHouseDate = importDeviceBean.getWareHouseDate();
                String count = "" + importDeviceBean.getNumber();
                sparePartsBM.clearAllFilter();
                if (model != null && !"".equals(model)) {
                    sparePartsBM.setReserve_filter("AND C_DEVICE_NAME_" + sparePartsBM.getId() + "='" + deviceName + "'" +
                            " AND C_MODEL_" + sparePartsBM.getId() + "='" + model + "'" +
                            " AND T_PRODUCT_STRUCTURE_" + schemaId + "_ID='" + productId + "'");
                } else {
                    sparePartsBM.setReserve_filter("AND C_DEVICE_NAME_" + sparePartsBM.getId() + "='" + deviceName + "'" +
                            " AND C_MODEL_" + sparePartsBM.getId() + " is null" +
                            " AND T_PRODUCT_STRUCTURE_" + schemaId + "_ID='" + productId + "'");
                }
                List<Map> deviceList = orientSqlEngine.getBmService().createModelQuery(sparePartsBM).list();
                if (deviceList.size() > 0) {
                    String deviceId = (String) deviceList.get(0).get("ID");
                    if (count != null && !"".equals(count)) {
                        insertDeviceInst(count, sparePartsInstBM.getId(), deviceId, productId, position, wareHouseDate);
                    }
                } else {
                    Map deviceMap = UtilFactory.newHashMap();
                    deviceMap.put("C_DEVICE_NAME_" + sparePartsBM.getId(), deviceName);
                    deviceMap.put("C_MODEL_" + sparePartsBM.getId(), model);
                    deviceMap.put("C_NOTE_" + sparePartsBM.getId(), importDeviceBean.getNote());
                    deviceMap.put("T_PRODUCT_STRUCTURE_" + schemaId + "_ID", productId);
                    deviceMap.put("C_VERSION_" + sparePartsBM.getId(), 0);
                    String deviceId = orientSqlEngine.getBmService().insertModelData(sparePartsBM, deviceMap);
                    if (count != null && !"".equals(count)) {
                        insertDeviceInst(count, sparePartsInstBM.getId(), deviceId, productId, position, wareHouseDate);
                    }
                }
            }
        }
        retVal.put("success", true);
        retVal.put("msg", "???????????????");
        return retVal;
    }

    //????????????????????????
    public void insertDeviceInst(String count, String modelId, String spareId, String productId, String position, Date storeDate) {
        IBusinessModel ibm = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceLifeBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEVICE_LIFE_CYCLE, schemaId, EnumInter.BusinessModelEnum.Table);
        String tableId = deviceLifeBM.getId();
        String sql = "select max(to_number(C_SERIAL_NUMBER_" + modelId + ")) from T_SPARE_PARTS_SHILI_" + schemaId + " where T_SPARE_PARTS_" + schemaId + "_ID = '" + spareId + "'";
        int vehicleCount = (int) metaDaoFactory.getJdbcTemplate().execute(sql, new PreparedStatementCallback() {
            @Override
            public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.execute();
                ResultSet rs = preparedStatement.getResultSet();
                rs.next();
                return rs.getInt(1);
            }
        });
        int startCount = vehicleCount;
        int countInt = Integer.parseInt(count);
        for (int i = 0; i < countInt; i++) {
            Map map = new HashMap<>();
            map.put("C_POSITION_" + modelId, "");
            if (storeDate == null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    storeDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            map.put("C_LIEZHUANG_TIME_" + modelId, CommonTools.util2Sql(storeDate));
            map.put("C_DEVICE_NAME_" + modelId, spareId);
            map.put("T_SPARE_PARTS_" + schemaId + "_ID", spareId);
            map.put("C_SERIAL_NUMBER_" + modelId, ++startCount);
            map.put("C_STATE_" + modelId, "??????");
            map.put("C_POSITION_" + modelId, position);
            map.put("C_VERSION_" + modelId, 0);
            map.put("T_PRODUCT_STRUCTURE_" + schemaId + "_ID", productId);
            String deviceInstId = orientSqlEngine.getBmService().insertModelData(ibm, map);
            Map lifeCycleMap = UtilFactory.newHashMap();
            lifeCycleMap.put("T_SPARE_PARTS_SHILI_" + schemaId + "_ID", deviceInstId);
            lifeCycleMap.put("C_START_TIME_" + deviceLifeBM.getId(), CommonTools.util2Sql(storeDate));
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String editTime = date.format(new Date());
            lifeCycleMap.put("C_UPDATE_TIME_" + deviceLifeBM.getId(), editTime);
            lifeCycleMap.put("C_DEVICE_STATE_" + deviceLifeBM.getId(), "??????");
            orientSqlEngine.getBmService().insertModelData(deviceLifeBM, lifeCycleMap);
        }
    }

    public String exportDeviceData(boolean exportAll, String toExportIds, String productId) {
        IBusinessModel sparePartsBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel productBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
        Map<String, String> productMap = orientSqlEngine.getBmService().createModelQuery(productBM).findById(productId);

        sparePartsBM.setReserve_filter("AND T_PRODUCT_STRUCTURE_" + schemaId + "_ID='" + productId + "'");
        IBusinessModel sparePartsInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        Excel excel = new Excel();
        Object[] headers = new Object[]{"????????????", "??????", "??????", "??????"};
        excel.row(0).value(headers);
        final int[] i = {1};
        if (exportAll) {
            List<Map<String, Object>> deviceList = orientSqlEngine.getBmService().createModelQuery(sparePartsBM).orderAsc("to_number(ID)").list();
            if (deviceList.size() > 0) {
                for (Map map : deviceList) {
                    String deviceId = (String) map.get("ID");
                    excel.cell(i[0], 0).value(map.get("C_DEVICE_NAME_" + sparePartsBM.getId()));
                    excel.cell(i[0], 1).value(map.get("C_MODEL_" + sparePartsBM.getId()));
                    excel.cell(i[0], 2).value(map.get("C_NOTE_" + sparePartsBM.getId()));
                    sparePartsInstBM.clearAllFilter();
                    sparePartsInstBM.setReserve_filter("AND T_SPARE_PARTS_" + schemaId + "_ID='" + deviceId + "'" +
                            " AND T_PRODUCT_STRUCTURE_" + schemaId + "_ID='" + productId + "'");
                    long count = orientSqlEngine.getBmService().createModelQuery(sparePartsInstBM).count();
                    excel.cell(i[0], 3).value(count);
                    i[0]++;
                }
            }
        } else {
            sparePartsBM.clearAllFilter();
            if (!toExportIds.equals("")) {
                sparePartsBM.setReserve_filter("and id in (" + toExportIds + ")");
                List<Map<String, Object>> sparePartsList = orientSqlEngine.getBmService().createModelQuery(sparePartsBM).orderAsc("to_number(ID)").list();
                if (sparePartsList.size() > 0) {
                    for (Map map : sparePartsList) {
                        String deviceId = (String) map.get("ID");
                        excel.cell(i[0], 0).value(map.get("C_DEVICE_NAME_" + sparePartsBM.getId()));
                        excel.cell(i[0], 1).value(map.get("C_MODEL_" + sparePartsBM.getId()));
                        excel.cell(i[0], 2).value(map.get("C_NOTE_" + sparePartsBM.getId()));
                        sparePartsInstBM.clearAllFilter();
                        sparePartsInstBM.setReserve_filter("AND T_SPARE_PARTS_" + schemaId + "_ID='" + deviceId + "'" +
                                " AND T_PRODUCT_STRUCTURE_" + schemaId + "_ID='" + productId + "'");
                        List<Map> spareInstList = orientSqlEngine.getBmService().createModelQuery(sparePartsInstBM).list();
                        if (spareInstList.size() > 0) {
                            excel.cell(i[0], 3).value(spareInstList.size());
                        }
                        i[0]++;
                    }
                }
            }
        }
        for (int j = 0; j < 1; j++) {
            excel.column(j).autoWidth();
        }
        String productName = productMap.get("C_NAME_" + productBM.getId());
        String divingFolderPath = FtpFileUtil.EXPORT_ROOT + File.separator + "??????????????????";
        String folder = FtpFileUtil.getRelativeUploadPath(divingFolderPath);
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        String fileName = productName+".xls";
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
//        excel.saveExcel("consume.xls");
        excel.saveExcel(fileServerConfig.getFtpHome() + folder + finalFileName);
        return fileServerConfig.getFtpHome() + folder + finalFileName;
    }

    public ModelAndView printQRcode(HttpServletRequest request, HttpServletResponse response, String spareInstId) throws Exception {
        ModelAndView retVal = new ModelAndView();

        IBusinessModel spareInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel spareBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS, schemaId, EnumInter.BusinessModelEnum.Table);

        spareInstBM.setReserve_filter("AND ID='" + spareInstId + "'");
        List<Map> spareInstList = orientSqlEngine.getBmService().createModelQuery(spareInstBM).list();

        if (spareInstList.size() > 0) {
//            DeviceInstBean deviceInstBean = new DeviceInstBean();
            Map spareInstMap = spareInstList.get(0);
            String spareId = (String) spareInstMap.get("T_SPARE_PARTS_" + schemaId + "_ID");
            Map<String, String> spareMap = orientSqlEngine.getBmService().createModelQuery(spareBM).findById(spareId);
            String spareName = spareMap.get("C_DEVICE_NAME_" + spareBM.getId());
            Map dataMap = UtilFactory.newHashMap();
            dataMap.put("name", spareName);
            dataMap.put("id", spareInstId);
            dataMap.put("statusCode", 0);
//            retVal.addObject("id",spareInstId);
//            retVal.addObject("deviceName",spareName);
            retVal.addObject("data", dataMap);
            retVal.addObject("code", 200);

            retVal.setViewName("/print.jsp");
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
        return retVal;
    }

    public String generateQRCode(String id, String serialNumber, String spareName, String deviceModel, boolean isMorePrint) {
        String previewQrcodeUrl = "";
        //???????????????????????????????????????
        String folder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.EXPORT_ROOT);
        //????????????????????????????????????????????????
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);

        String fileName = spareName + "-" + serialNumber + ".jpg";
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            @SuppressWarnings("rawtypes")
            Map hints = new HashMap();

            //??????UTF-8??? ??????????????????
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            //??????????????????????????????????????????
            hints.put(EncodeHintType.MARGIN, 2);
            //???????????????????????????
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

            //width:??????????????????;height:??????????????????
            //?????????????????????????????????????????????????????????????????????????????????????????????
            int width = 250;
            int height = 100;
            //???????????????????????????multiFormatWriter.encode()??????????????????hints?????????????????????????????????
            BitMatrix bitMatrix = multiFormatWriter.encode(id, BarcodeFormat.QR_CODE, width, height, hints);

            //qrcFile??????????????????????????????????????????logo???????????????
            File qrcFile = new File(fileServerConfig.getFtpHome() + folder, finalFileName);
            //??????????????????
            BufferedImage image = MatrixToImageWriter.writeToFile(bitMatrix, "jpg", qrcFile);
            //?????????????????????????????????
            String text = "???????????????" + spareName + " \n?????????" + deviceModel + "-" + serialNumber + "\n???????????????" + id;
            int font = 15; //????????????
            int fontStyle = 0; //????????????
            //????????????????????????????????????????????????
            previewQrcodeUrl = pressText(text, fileServerConfig.getFtpHome() + folder + finalFileName, fontStyle, Color.BLACK, font, width, height, image, spareName, serialNumber, isMorePrint);
//             MatrixToImageWriter.pressText(text,fileServerConfig.getFtpHome() + folder + finalFileName,fontStyle, Color.BLACK,font,width,height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return previewQrcodeUrl;
    }

    public AjaxResponseData<T> batchPrintQrcode(String deviceInstIds, String spareId, String spareName, String deviceModel, boolean startAll) {
        AjaxResponseData retVal = new AjaxResponseData();
        deviceModel=CommonTools.Obj2String(deviceModel);
        IBusinessModel spareInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        List<Map<String, Object>> deviceInstList = UtilFactory.newArrayList();
        if (startAll) {
            spareInstBM.setReserve_filter("AND T_SPARE_PARTS_" + schemaId + "_ID='" + spareId + "'");
            deviceInstList = orientSqlEngine.getBmService().createModelQuery(spareInstBM).orderAsc("TO_NUMBER(ID)").list();
        } else {
            spareInstBM.clearAllFilter();
            spareInstBM.setReserve_filter("AND ID IN (" + deviceInstIds + ")");
            deviceInstList = orientSqlEngine.getBmService().createModelQuery(spareInstBM).orderAsc("TO_NUMBER(ID)").list();
        }
        if (deviceInstList.size() > 0) {
            String printText[][]=new String[deviceInstList.size()][3];
            for (int i = 0; i < deviceInstList.size(); i++) {
                Map deviceInstMap = deviceInstList.get(i);
                String deviceInstId = CommonTools.Obj2String(deviceInstMap.get("ID"));
                String serialNumber = CommonTools.Obj2String(deviceInstMap.get("C_SERIAL_NUMBER_" + spareInstBM.getId()));
                printText[i][0] = "????????????:"+spareName;
                printText[i][1]="??????:"+deviceModel+"-"+serialNumber;
                printText[i][2]="????????????:"+deviceInstId;
            }
            retVal.setSuccess(true);
            retVal.setResults(printText);
        }else{
            retVal.setSuccess(false);
            retVal.setMsg("???????????????????????????????????????");
        }
        return retVal;
    }
}

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
     * 快速新增
     *
     * @param count
     * @param modelId
     * @param spareId
     * @return
     */
    public String easyAdd(String count, String modelId, String spareId, String productId) {
        String msg = "快速新增成功";
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
                map.put("C_STATE_" + modelId, "冷备");
                map.put("T_PRODUCT_STRUCTURE_" + schemaId + "_ID", productId);
                map.put("C_VERSION_" + modelId, 0);
                String deviceInstId = orientSqlEngine.getBmService().insertModelData(ibm, map);
                Map lifeCycleMap = UtilFactory.newHashMap();
                lifeCycleMap.put("T_SPARE_PARTS_SHILI_" + schemaId + "_ID", deviceInstId);
                lifeCycleMap.put("C_START_TIME_" + deviceLifeBM.getId(), CommonTools.util2Sql(new Date()));
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String editTime = date.format(new Date());
                lifeCycleMap.put("C_UPDATE_TIME_" + deviceLifeBM.getId(), editTime);
                lifeCycleMap.put("C_DEVICE_STATE_" + deviceLifeBM.getId(), "冷备");
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
     * 生成二维码图片路径
     *
     * @param: id
     * @param: serialNumber
     * @param: spareName
     * @return:
     */
    public String encoderQRCode(String id, String serialNumber, String spareName) {
        //根据系统日期生成文件夹路径
        String folder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.EXPORT_ROOT);
        //新路径创建保存二维码图片的文件夹
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        String fileName = spareName + "-" + serialNumber + ".png";
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
        String imgType = "png";
        //生成二维码图片
        this.encoderQRCode(id, fileServerConfig.getFtpHome() + folder + finalFileName, imgType, 7);
        String name = "名称：" + spareName + "编号" + id;
        int imgSize = 67 + 12 * (7 - 1);
//        pressText(name,fileServerConfig.getFtpHome() + folder + finalFileName,5,Color.BLACK,imgSize);
        return folder + finalFileName;
    }

    /**
     * 生成二维码(QRCode)图片
     *
     * @param content 存储内容
     * @param imgPath 图片路径
     * @param imgType 图片类型
     * @param size    二维码尺寸
     */
    public void encoderQRCode(String content, String imgPath, String imgType, int size) {
        try {
            BufferedImage bufImg = this.qRCodeCommon(content, imgType, size);
            //在imgPath下生成二维码图片
            File imgFile = new File(imgPath);
            // 生成二维码QRCode图片
            ImageIO.write(bufImg, imgType, imgFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码(QRCode)图片的公共方法
     *
     * @param content 存储内容
     * @param imgType 图片类型
     * @param size    二维码尺寸
     * @return
     */
    private BufferedImage qRCodeCommon(String content, String imgType, int size) {
        BufferedImage bufImg = null;
        try {
            Qrcode qrcodeHandler = new Qrcode();
            // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
            qrcodeHandler.setQrcodeErrorCorrect('M');
            qrcodeHandler.setQrcodeEncodeMode('B');
            // 设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
            qrcodeHandler.setQrcodeVersion(size);
            // 获得内容的字节数组，设置编码格式
            byte[] contentBytes = content.getBytes("utf-8");
            // 图片尺寸
            int imgSize = 67 + 12 * (size - 1);
            bufImg = new BufferedImage(imgSize, imgSize, TYPE_INT_RGB);
            Graphics2D gs = bufImg.createGraphics();
            // 设置背景颜色
            gs.setBackground(Color.WHITE);
            gs.clearRect(0, 0, imgSize, imgSize);
            // 设定图像颜色> BLACK
            gs.setColor(Color.BLACK);
            // 设置偏移量，不设置可能导致解析出错
            int pixoff = 2;
            // 输出内容> 二维码
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
            //只有二维码生成的路径
            File file = new File(targetImg);
            Image src = ImageIO.read(file);
            int imageW = src.getWidth(null);
            int imageH = src.getHeight(null);

            //二维码图片下面加上文字，画设备名称
            BufferedImage outImage = new BufferedImage(imageW, imageH + 50, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D outg = outImage.createGraphics();
            outg.setPaint(new Color(0, 0, 0, 64));
            //画二维码到新的面板
            outg.drawImage(image, 0, 0, imageW, imageH, null);
            outg.setColor(Color.BLACK);
            outg.setFont(new Font("黑体", fontStyle, fontSize));
            //计算一个汉字的宽度
            int everyCharWidth = outg.getFontMetrics().stringWidth("测");
            //计算字符高度,注意设置font才能计算宽度和高度
            int everyCharHeight = outg.getFontMetrics().getHeight();
            String pressArray[] = pressText.split("\n");
            int pressTextWidth = outg.getFontMetrics().stringWidth(pressArray[0]);
            //计算每一行字符的个数
//            int textSizeCount =width/everyCharWidth;
            int textSizeCount = width * pressArray[0].length() / pressTextWidth;
//            //计算宽高比
//            int rowSize = pressText.length()/textSizeCount+1;
            int rowSize = pressTextWidth / width + 1;
            int additionHeight = 50;
            int deviceNameAllH = pressContentSplit(pressArray[0], outg, width, imageH, additionHeight);
            //画型号和编号
            BufferedImage outImage2 = new BufferedImage(imageW, imageH + 80, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D outg2 = outImage2.createGraphics();
            outg2.setPaint(new Color(0, 0, 0, 64));
            additionHeight = 30;
            //画二维码及名称到新的面板
            outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
            outg2.setColor(Color.BLACK);
            outg2.setFont(new Font("黑体", fontStyle, fontSize));
            deviceNameAllH = pressContentSplit(pressArray[1], outg2, outImage.getWidth(), deviceNameAllH, additionHeight);

            //画唯一标识
            BufferedImage outImage3 = new BufferedImage(imageW, imageH + 110, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D outg3 = outImage3.createGraphics();
            outg3.setPaint(new Color(0, 0, 0, 64));
            additionHeight = 30;
            //画二维码及名称及型号和编号到新的面板
            outg3.drawImage(outImage2, 0, 0, outImage2.getWidth(), outImage2.getHeight(), null);
            outg3.setColor(Color.BLACK);
            outg3.setFont(new Font("黑体", fontStyle, fontSize));
            pressContentSplit(pressArray[2], outg3, outImage2.getWidth(), deviceNameAllH, additionHeight);

//            if (pressTextWidth>width){
//                //长度过长就换行
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
//                //画文字
//                outg.drawString(pressText,200-pressTextWidth/2,height+(outImage.getHeight()-height)/2+12);
//            }
            //根据系统日期生成文件夹路径
            String folder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.EXPORT_ROOT);
            //新路径创建保存二维码图片的文件夹
            FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);

            String fileName = spareName + "-" + serialNumber + ".jpg";
            String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
            String newQRCodePath = folder + finalFileName;
            outg3.dispose();
            outImage3.flush();
            image = outImage3;
            BufferedImage newBufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            //消除画图锯齿
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
            //保存文件到preview
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
        //计算宽高比
        int rowSize = pressTextWidth / width + 1;
        ///每一行文字的高度
        int everyRowTextH = additionHeight / rowSize;
        if (rowSize == 1) {       //一行
            outg.drawString(pressArray, 0, imageH + additionHeight / 2 + 3);
            pressTextHeight = imageH + additionHeight / 2 + 3;
        } else {                        //N行
            int[] pressTextWidthArray = new int[rowSize];
            int[] pressTextHeightArray = new int[rowSize];
            String[] pressTextArray = new String[rowSize];

            for (int i = 0; i < pressTextWidthArray.length; i++) {
                //最后一行
                if (i == rowSize - 1) {
                    pressTextArray[i] = pressArray.substring(textSizeCount * i, pressArray.length());
                } else {
                    pressTextArray[i] = pressArray.substring(textSizeCount * i, textSizeCount * (i + 1));
                }
                //每一行文字的高度
                pressTextHeightArray[i] = everyRowTextH;
                //注意drawString这个函数中的x,y指代的是坐标位置
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
            //根据传进来的产品结构ID获取下面所有的产品结构主键ID
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
        int end = startt + limitt;//防止尾页越界
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
            //取出每一行的单元格数据遍历，插入到数据库
            List<FieldEntity> fieldEntities = dataEntities.get(j).getFieldEntityList();
            if (fieldEntities != null) {
                for (int i = 0; i < fieldEntities.size(); i++) {
                    FieldEntity fieldEntity = fieldEntities.get(i);
                    if (fieldEntity.getIsKey() == 1) {
                        continue;
                    }
                    String name = fieldEntities.get(i).getName();
                    String value = fieldEntities.get(i).getValue();
                    if ("装备名称".equals(name)) {
                        if (value == null || "".equals(value)) {
                            int rowNumer = j + 2;
                            retVal.put("success", false);
                            retVal.put("msg", "第" + rowNumer + "行第" + i + "列的装备名称不可为空，请修正后导入！");
                            return retVal;
                        }
                        importDeviceBean.setName(value);
                    } else if ("型号".equals(name)) {
                        importDeviceBean.setModel(value);
                    } else if ("备注".equals(name)) {
                        importDeviceBean.setNote(value);
                    } else if ("存放位置".equals(name)) {
                        importDeviceBean.setPosition(value);
                    } else if ("入库时间".equals(name)) {
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
                            retVal.put("msg", "第" + rowNumer + "行第" + i + "列的入库时间格式存在错误！");
                            return retVal;
                        }
                    } else if ("数量".equals(name)) {
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
                        retVal.put("msg", "导入失败,表头格式存在错误！");
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
        retVal.put("msg", "导入成功！");
        return retVal;
    }

    //快速插入设备实例
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
            map.put("C_STATE_" + modelId, "冷备");
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
            lifeCycleMap.put("C_DEVICE_STATE_" + deviceLifeBM.getId(), "冷备");
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
        Object[] headers = new Object[]{"装备名称", "型号", "备注", "数量"};
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
        String divingFolderPath = FtpFileUtil.EXPORT_ROOT + File.separator + "备品备件统计";
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
        //根据系统日期生成文件夹路径
        String folder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.EXPORT_ROOT);
        //新路径创建保存二维码图片的文件夹
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);

        String fileName = spareName + "-" + serialNumber + ".jpg";
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            @SuppressWarnings("rawtypes")
            Map hints = new HashMap();

            //设置UTF-8， 防止中文乱码
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            //设置二维码四周白色区域的大小
            hints.put(EncodeHintType.MARGIN, 2);
            //设置二维码的容错性
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

            //width:图片完整的宽;height:图片完整的高
            //因为要在二维码下方附上文字，所以把图片设置为长方形（高大于宽）
            int width = 250;
            int height = 100;
            //画二维码，记得调用multiFormatWriter.encode()时最后要带上hints参数，不然上面设置无效
            BitMatrix bitMatrix = multiFormatWriter.encode(id, BarcodeFormat.QR_CODE, width, height, hints);

            //qrcFile用来存放生成的二维码图片（无logo，无文字）
            File qrcFile = new File(fileServerConfig.getFtpHome() + folder, finalFileName);
            //开始画二维码
            BufferedImage image = MatrixToImageWriter.writeToFile(bitMatrix, "jpg", qrcFile);
            //附加在图片上的文字信息
            String text = "设备名称：" + spareName + " \n编号：" + deviceModel + "-" + serialNumber + "\n唯一标识：" + id;
            int font = 15; //字体大小
            int fontStyle = 0; //字体风格
            //在二维码下方添加文字（文字居中）
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
                printText[i][0] = "设备名称:"+spareName;
                printText[i][1]="编号:"+deviceModel+"-"+serialNumber;
                printText[i][2]="唯一标识:"+deviceInstId;
            }
            retVal.setSuccess(true);
            retVal.setResults(printText);
        }else{
            retVal.setSuccess(false);
            retVal.setMsg("至少选中一行数据进行打印！");
        }
        return retVal;
    }
}

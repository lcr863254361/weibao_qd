/**
 * Created by enjoy on 2016/3/16 0016.
 * Ext组件相关通用函数
 */

Ext.define("OrientTdm.ProductStructureMgr.CheckUtil.TestProcessUtil", {
    extend: 'Ext.Base',
    alternateClassName: 'TestProcessUtil',
    statics: {
        CommonHelper: {
            checkListPreview: function (checkTempId,isInst,withData,productId,isDestroyTemp) {
                if (isInst&&isDestroyTemp){
                        var centerPanel = Ext.create("OrientTdm.DestroyRepairTemplateMgr.FormTempInst.FormTempInstGrid", {
                            region: 'center',
                            isInst: isInst,
                            withData: withData,
                            productId:productId,
                            isShowProduct:true
                        });
                }else{
                    var centerPanel = Ext.create("OrientTdm.FormTemplateMgr.FormTemplatePanel.FormTemplateHeadCellGrid", {
                        region: 'center',
                        isInst: isInst,
                        withData: withData,
                        productId:productId,
                        isShowProduct:false,
                        checkTempId:checkTempId
                    });
                }

                OrientExtUtil.WindowHelper.createWindow(centerPanel, {
                    title: '预览检查表',
                    layout: "border",
                    width: 0.8 * globalWidth,
                    height: 0.8 * globalHeight,
                    buttons: [
                        {
                            text: '关闭',
                            iconCls: 'icon-close',
                            handler: function () {
                                this.up('window').close();
                            }
                        }
                    ],
                    buttonAlign: 'center'
                });
                centerPanel.getCheckListDetailPanel(checkTempId);
            }
        }
    }
})
;

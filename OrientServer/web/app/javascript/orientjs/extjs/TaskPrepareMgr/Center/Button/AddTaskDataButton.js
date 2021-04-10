/**
 * Created by enjoy on 2016/4/18 0018.
 */
Ext.define("OrientTdm.TaskPrepareMgr.Center.Button.AddTaskDataButton", {
    extend: 'OrientTdm.Common.Extend.Button.OrientButton',
    //按钮点击时触发事件
    triggerClicked: function (modelGridPanel) {
        var me = this;
        //创建新增表单面板
        //构建操作成功函数
        me.successCallBack = function (resp, extraParams) {
            if (modelGridPanel.hasListener('customRefreshGrid')) {
                //如果存在自定义刷新事件
                modelGridPanel.fireEvent('customRefreshGrid');
            } else {
                //否则调用默认刷新事件
                modelGridPanel.fireEvent('refreshGridByCustomerFilter');
            }
            modelGridPanel.fireEvent('afterCreateData', arguments);
            if(extraParams){
                var btn = extraParams[0];
                if (btn.itemId == 'saveAndClose') {
                    me.doBack();
                }
            }else{
                me.doBack();
            }
        };
        me.customPanel = me.createModelForm(modelGridPanel, me.btnDesc);
        me.callParent(arguments);
    },
    createModelForm: function (modelGridPanel, btnDesc) {
        var me = this;
        var retVal;
        if (modelGridPanel.isDestroyButton){
            var saveUrl = serviceName + '/destroyRepairMgr/saveDestroyTaskData.rdm?modelId='+modelGridPanel.modelId;
        }else{
            var saveUrl = serviceName + '/taskPrepareController/saveDivingTaskData.rdm?flowId=' + modelGridPanel.flowId +'&modelId='+modelGridPanel.modelId;
        }
        if (btnDesc && !Ext.isEmpty(btnDesc.formViewId)) {
            retVal = Ext.create('OrientTdm.BackgroundMgr.CustomForm.Common.FreemarkerForm', {
                formViewId: btnDesc.formViewId,
                modelId: modelGridPanel.modelDesc.modelId,
                buttonAlign: 'center',
                successCallback: me.successCallBack,
                saveUrl: saveUrl,
                buttons: [
                    {
                        text: '保存并关闭',
                        itemId: 'save',
                        iconCls: 'icon-save',
                        handler: function () {
                            retVal.fireEvent('saveFreemarkerForm');
                        }
                    },
                    {
                        itemId: 'back',
                        text: '关闭',
                        iconCls: 'icon-close',
                        scope: me,
                        handler: Ext.bind(me.doBack, me, [modelGridPanel], true)
                    }
                ]
            });
        } else {
            var defineString;
            if (modelGridPanel.isDestroyButton) {
                defineString="OrientTdm.DestroyTaskMgr.AddDestroyTaskPanel.AddDestroyTaskFormPanel";
            }else{
                defineString="OrientTdm.TaskPrepareMgr.Center.TabPanel.AddPanel.AddDivingTaskFormPanel";
            }
            retVal = Ext.create(defineString, {
                    title: '新增【<span style="color: red; ">' + modelGridPanel.modelDesc.text + '</span>】数据',
                    buttonAlign: 'center',
                    subFlowId:modelGridPanel.subFlowId,
                    personnelIds:modelGridPanel.personnelIds,
                    dockedItems: [{
                        xtype: 'toolbar',
                        dock: 'top',
                        items: [{
                            itemId: 'back',
                            text: '返回',
                            iconCls: 'icon-back',
                            scope: me,
                            handler: Ext.bind(me.doBack, me, [modelGridPanel], true)
                        }]
                    }],
                    buttons: [
                        {
                            itemId: 'saveAndClose',
                            text: '保存并关闭',
                            scope: me,
                            iconCls: 'icon-saveSingle',
                            handler: Ext.bind(me.doSave, me, [modelGridPanel], true)
                        },
                        {
                            itemId: 'save',
                            text: '保存',
                            scope: me,
                            iconCls: 'icon-saveSingle',
                            handler: Ext.bind(me.doSave, me, [modelGridPanel], true)
                        },
                        {
                            itemId: 'close',
                            text: '关闭',
                            iconCls: 'icon-close',
                            scope: me,
                            handler: Ext.bind(me.doBack, me, [modelGridPanel], true)
                        }
                    ],
                    successCallback: me.successCallBack,
                    bindModelName: modelGridPanel.modelDesc.dbName,
                    actionUrl: saveUrl,
                    modelDesc: modelGridPanel.modelDesc,
                    originalData: modelGridPanel.formInitData,
                    listeners:{
                        beforedestroy:function(){
                            delete me.customPanel;
                        }
                    }
                }
            )
            ;
        }
        return retVal;
    },
    doSave: function (btn, event) {
        var me = this;
        btn.up("form").fireEvent("saveOrientForm",[btn]);
    }
})
;
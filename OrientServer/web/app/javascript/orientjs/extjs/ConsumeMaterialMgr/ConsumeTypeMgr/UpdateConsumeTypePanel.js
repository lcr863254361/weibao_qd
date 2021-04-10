/**
 * Created by User on 2019/5/18.
 */
Ext.define('OrientTdm.ConsumeMaterialMgr.ConsumeTypeMgr.UpdateConsumeTypePanel', {
    extend: 'OrientTdm.Common.Extend.Form.OrientForm',
    alias: 'widget.updateConsumeTypePanel',
    actionUrl:serviceName + '/ConsumeMaterialMgr/updateConsumeTypeData.rdm',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        modelName: TDM_SERVER_CONFIG.CONSUME_MATERIAL
    },
    initComponent: function () {
        var me = this;
        me.modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        me.actionUrl = serviceName + '/ConsumeMaterialMgr/updateConsumeTypeData.rdm?modelId=' + me.modelId + '&consumeTypeId=' + me.consumeTypeId;

        // var updateUrl=serviceName+'/ConsumeMaterialMgr/updateConsumeTypeData.rdm?modelId='+modelId+'&consumeTypeId='+me.consumeTypeId;
        Ext.apply(me, {
            items: [
                {
                    xtype: 'panel',
                    layout: 'hbox',
                    bodyStyle: 'border-width:0 0 0 0;background:transparent',
                    combineErrors: true,
                    defaults: {
                        flex: 1
                    },
                    layoutConfig: {
                        pack: "start",
                        align: "stretch"
                    },

                    items: [{
                        xtype: "fieldcontainer",
                        layout: 'anchor',
                        combineErrors: true,
                        defaults: {
                            flex: 1,
                            anchor: '100%'
                        },
                        layoutConfig: {
                            pack: "start",
                            align: "stretch"
                        },
                        items: [
                            {
                                name: 'C_NAME_' + me.modelId,
                                xtype: "textfield",
                                id: 'consumeTypeNameId',
                                labelWidth: 100,
                                fieldLabel: '耗材类别名称',
                                labelAlign: 'right',
                                margin: '0 5 5 0',
                                // vtype: 'unique',
                                validataOnchange: false,
                                allowBlank: false,
                                editable: true,
                                hidden: false,
                                flex: 1
                            }
                        ]
                    }]
                }
            ],
            buttonAlign: 'center',
            buttons: [
                {
                    text: '保存并关闭',
                    iconCls: 'icon-saveAndClose',
                    handler: function () {
                        me.fireEvent("saveOrientForm", {}, true);
                    }
                },
                {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: function () {
                        me.fireEvent("saveOrientForm");
                    }
                },
                {
                    text: '关闭',
                    iconCls: 'icon-close',
                    handler: function () {
                        this.up('window').close();
                    }
                }
            ]
            // actionUrl:updateUrl
        });
        me.callParent(arguments);
    },
    customValidate: function () {
        var me = this;
        var retVal = true;
        var formData = OrientExtUtil.FormHelper.getModelData(this);
        var modelId = this.modelId;
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/modelData/validateAll.rdm', {
            formData: formData,
            modelId: modelId
        }, false, function (resp) {
            var respData = resp.decodedData;
            if (respData.results != null && respData.results.length > 0) {
                retVal = false;
                var errorMsg = Ext.Array.pluck(respData.results, "errorMsg").join('</br>');
                OrientExtUtil.Common.err(OrientLocal.prompt.error, errorMsg, function () {
                    //markInvalid
                    Ext.each(respData.results, function (error) {
                        var errColumnName = error.columnSName;
                        if (!Ext.isEmpty(errColumnName)) {
                            var field = me.down('[name=' + errColumnName + ']');
                            if (field.markInvalid) {
                                field.clearInvalid();
                                field.markInvalid(error.errorMsg);
                            }
                        }
                    });
                });
            }
        });
        return retVal;
    }

});
/**
 * Created by Seraph on 16/9/23.
 */
Ext.define("OrientTdm.Collab.common.template.TemplateCreateFormPanel", {
    extend: 'OrientTdm.Common.Extend.Form.OrientForm',
    config: {
        url:serviceName + '/template/mng/bmTemplate/create.rdm',
        baseParams : null,
        successCallback : Ext.emptyFn()
    },
    requires: [
    ],
    initComponent: function () {
        var me = this;

        Ext.apply(me, {
            bindModelName : 'COLLAB_TEMPLATE',
            items: [
                {
                    xtype: 'fieldset',
                    title: '详情',
                    collapsible: true,
                    defaults: {
                        labelWidth: 89,
                        anchor: '100%',
                        layout: {
                            type: 'vbox',
                            defaultMargins: {top: 15, right: 15, bottom: 0, left: 15}
                        }
                    },
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'name',
                            fieldLabel: '名称',
                            margin: '0 5 5 0',
                            allowBlank: false,
                            grow: true,
                            labelWidth: 100,
                            width:300
                        },{
                            xtype: 'textfield',
                            name: 'version',
                            fieldLabel: '版本',
                            margin: '0 5 5 0',
                            allowBlank: false,
                            grow: true,
                            labelWidth: 100,
                            width:300
                        }, {
                            xtype      : 'fieldcontainer',
                            fieldLabel : '是否私有',
                            defaultType: 'radiofield',
                            defaults: {
                                flex: 1
                            },
                            layout: 'hbox',
                            items: [
                                {
                                    boxLabel  : '是',
                                    name      : 'privateTemp',
                                    inputValue: true,
                                    checked : true
                                }, {
                                    boxLabel  : '否',
                                    name      : 'privateTemp',
                                    inputValue: false
                                }
                            ]
                        }]
                }
            ],
            buttons: [{
                text: '保存',
                iconCls: 'icon-saveSingle',
                handler: function () {
                    var form = this.up('form').getForm();
                    if (form.isValid()) {
                        form.submit({
                            success: function(form, action) {
                                Ext.Msg.alert("提示", '已成功导出模板');
                                me.successCallback();
                            },
                            failure: function(form, action) {
                                OrientExtUtil.Common.err('保存失败', action.result.msg);
                            }
                        });
                    }
                }
            }, {
                text: '清空',
                iconCls: 'icon-clear',
                handler: function () {
                    this.up('form').getForm().reset();
                }
            }
            ]
        });
        this.callParent(arguments);
    }
});
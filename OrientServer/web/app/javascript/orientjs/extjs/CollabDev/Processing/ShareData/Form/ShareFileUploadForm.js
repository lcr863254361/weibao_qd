/**
 * Created by ZhangSheng on 2018/08/22
 */
Ext.define('OrientTdm.CollabDev.Processing.ShareData.Form.ShareFileUploadForm', {
    extend: 'OrientTdm.Common.Extend.Form.OrientForm',
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            items: [{
                xtype: 'fieldset',
                border: 0,
                columnWidth: 1,
                collapsible: false,
                defaultType: 'textfield',
                layout: 'fit',
                items: [{
                    xtype: 'filefield',
                    fieldLabel: '选择文件',
                    allowBlank: false,
                    emptyText: '选择待上传文件',
                    buttonText: '',
                    minWidth: 200,
                    buttonConfig: {
                        iconCls: 'icon-upload'
                    }
                }]
            }
            ],
            buttonAlign: 'right',
            buttons: [{
                text: '保存',
                iconCls: 'icon-save',
                margin: '0 0 30 0',
                handler: function (btn) {
                    var form = this.up('form').getForm();
                    if (form.isValid()) {
                        var tree = Ext.ComponentQuery.query('shareFileTree')[0];
                        var currentNode = tree.getSelectionModel().getSelection()[0];
                        form.submit({
                            url: serviceName + '/shareFile/uploadShareFile.rdm?cbShareFolderId=' + currentNode.data.id,
                            waitMsg: "正在上传文件，请耐心等待",
                            success: function (fp, resp) {
                                btn.up('window').close();
                                Ext.ComponentQuery.query('shareFilePanel')[0].filterByShareFolderId(currentNode.data.id);
                                OrientExtUtil.Common.tip(OrientLocal.prompt.info, '文件上传成功');
                            }
                        })
                    }
                }
            }, {
                text: '关闭',
                iconCls: 'icon-close',
                margin: '0 5 30 10',
                handler: function (btn) {
                    btn.up('window').close();
                }
            }]
        });
        me.callParent(arguments);
    }

});
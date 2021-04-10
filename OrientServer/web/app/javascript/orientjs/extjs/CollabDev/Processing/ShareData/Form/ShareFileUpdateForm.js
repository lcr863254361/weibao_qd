/**
 * Created by enjoy on 2016/5/3 0003.
 */
Ext.define('OrientTdm.CollabDev.Processing.ShareData.Form.ShareFileUpdateForm', {
    extend: 'OrientTdm.Common.Extend.Form.OrientForm',
    alias: 'widget.shareFileUpdateForm',
    actionUrl: serviceName + '/shareFile/update.rdm',
    initComponent: function () {
        var me = this;
        var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
        Ext.apply(this, {
            items: [
                {
                    xtype: 'fieldcontainer',
                    layout: 'hbox',
                    combineErrors: true,
                    defaults: {
                        flex: 1
                    },
                    items: [
                        {
                            name: 'name',
                            xtype: 'textfield',
                            fieldLabel: '文件名称',
                            margin: '0 5 0 0',
                            afterLabelTextTpl: required,
                            allowBlank: false
                        }
                    ]
                }, {
                    xtype: 'hiddenfield',
                    name: 'id'
                }, {
                    xtype: 'hiddenfield',
                    name: 'createUser'
                }, {
                    xtype: 'hiddenfield',
                    name: 'createTime'
                }, {
                    xtype: 'hiddenfield',
                    name: 'updateUser'
                }, {
                    xtype: 'hiddenfield',
                    name: 'updateTime'
                }, {
                    xtype: 'hiddenfield',
                    name: 'remoteDesc'
                }, {
                    xtype: 'hiddenfield',
                    name: 'remoteDesc'
                }, {
                    xtype: 'hiddenfield',
                    name: 'version'
                }, {
                    xtype: 'hiddenfield',
                    name: 'fileType'
                }, {
                    xtype: 'hiddenfield',
                    name: 'fileLocation'
                }
            ],
            buttons: [
                {
                    text: '保存并关闭',
                    iconCls: 'icon-saveAndClose',
                    handler: function () {
                        me.fireEvent('saveOrientForm', {}, true);
                    }
                },
                {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: function () {
                        me.fireEvent('saveOrientForm');
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
        });
        me.callParent(arguments);
    }
});
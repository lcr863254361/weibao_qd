Ext.define('OrientTdm.CollabDev.Designing.ResultSettings.DevData.Grid.HisDevDataSettingGrid', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.hisDevDataSettingGrid',
    config: {
        beforeInitComponent: Ext.emptyFn,
        afterInitComponent: Ext.emptyFn,
        dataObjId: ''
    },
    requires: [
        'Ext.ux.statusbar.StatusBar',
        'OrientTdm.CollabDev.Designing.ResultSettings.DevData.Model.DevDataModel'
    ],
    initComponent: function () {
        var me = this;
        me.beforeInitComponent.call(me);
        var store = me.createStore.call(me);
        store.pageSize = globalPageSize || 25;
        var columns = me.createColumn.call(me);
        var toolBarItems = me.createToolBarItems.call(me);
        var toolBar = Ext.create('Ext.toolbar.Toolbar', {
            items: toolBarItems
        });
        Ext.apply(me, {
            columns: columns,
            dockedItems: [toolBar],
            store: store,
            useArrows: true,
            rootVisible: false,
            multiSelect: true
        });
        me.callParent(arguments);
    },
    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'checkchange', me.selectionchange, me);
    },
    createStore: function () {
        var me = this;
        var retVal = Ext.create('Ext.data.TreeStore', {
            model: 'OrientTdm.CollabDev.Designing.ResultSettings.DevData.Model.DevDataModel',
            autoLoad: true,
            proxy: {
                type: 'ajax',
                api: {
                    'read': serviceName + '/HisDataObj/getHisDataObj.rdm'
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'msg'
                }
            },
            listeners: {
                load: function (store, record) {
                    var treeNodes = record.childNodes;
                    Ext.each(treeNodes, function (node) {
                            node.set('checked', null);

                        }
                    )
                }
            }

        });
        return retVal;
    },
    createColumn: function () {
        var me = this;
        return [
            {
                xtype: 'treecolumn',
                header: '名称',
                flex: 1,
                sortable: true,
                dataIndex: 'dataobjectname'
            }, {
                header: '类型',
                flex: 1,
                sortable: true,
                dataIndex: 'dataTypeShowName'
            }, {
                header: '值',
                flex: 1,
                sortable: true,
                dataIndex: 'value',
                renderer: function (value, p, record) {
                    var retVal = value;
                    var extendsTypeRealName = record.get('extendsTypeRealName');
                    if ('date' == extendsTypeRealName && value) {
                        retVal = value.substr(0, value.indexOf('T'));
                    } else if ('file' == extendsTypeRealName) {
                        //json串
                        if (!Ext.isEmpty(value)) {
                            retVal = '';
                            var gridId = me.id;
                            var recordId = record.getId();
                            var templateArray = [
                                "<span class='attachement-span'>",
                                "<a target='_blank' class='attachment'  onclick='OrientExtend.FileColumnDesc.handleFile(\"#fileId#\",\"C_File\")' title='#title#'>#name#</a>",
                                '<a href="javascript:;" onclick="OrientExtend.FileColumnDesc.handleFile(\'{id}\',\'C_File\');" title="下载" class="download">',
                                '</a>',
                                '</span>'
                            ];
                            var template = templateArray.join('');
                            var fileJson = Ext.decode(value);
                            var fileSize = fileJson.length;
                            Ext.each(fileJson, function (fileDesc, index) {
                                var fileId = fileDesc.id;
                                var fileName = fileDesc.name.length > 10 ? (fileDesc.name.substr(0, 6) + '...') : fileDesc.name;
                                retVal += template.replace("#name#", fileName).replace("#title#", fileName).replace("#fileId#", fileId);
                                if (index != (fileSize - 1)) {
                                    retVal += "</br>";
                                }
                            });
                        }
                    }
                    return retVal;
                }
            }, {
                header: '单位',
                flex: 1,
                sortable: true,
                dataIndex: 'unit'
            }, {
                header: '版本',
                flex: 1,
                sortable: true,
                dataIndex: 'version'
            }, {
                header: '修改人',
                flex: 1,
                sortable: true,
                dataIndex: 'modifiedUser'
            }, {
                header: '修改时间',
                xtype: 'datecolumn',
                format: "Y-m-d H:i:s",
                flex: 1,
                sortable: true,
                dataIndex: 'modifytime'
            }
        ];
    },
    createToolBarItems: function () {
        var me = this;
        var retVal = [{
            iconCls: 'icon-back',
            text: '返回',
            itemId: 'back',
            scope: this,
            handler: me._onBackClick
        }];
        return retVal;
    },
    selectionchange: function (node, checked) {
        //递归勾选子节点
        var me = this;
        node.expand();
        node.eachChild(function (child) {
            child.set('checked', checked);
            me.fireEvent('checkchange', child, checked);
        });
    },
    _onBackClick: function () {
        var me = this;
        var layout = me.ownerCt.getLayout();
        layout.setActiveItem(0);
        var combo =  Ext.ComponentQuery.query('processingDevDataMainTabPanel')[0].down('selfDevDataPanel').down('#taskVersionSwitchCombo');
        combo.enable();
    }
});

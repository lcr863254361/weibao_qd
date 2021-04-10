Ext.define('OrientTdm.CollabDev.Processing.ResultSettings.DevData.RefData.RefDevDataGrid', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.refDevDataGrid',
    config: {
        nodeId: '',
        nodeVersion: '',
        nodeType: null,
        isGlobal: 1
    },
    requires: [
        'Ext.ux.statusbar.StatusBar',
        'OrientTdm.CollabDev.Designing.ResultSettings.DevData.Model.DevDataModel',
        'OrientTdm.Collab.Data.DevData.Common.DataTypeSelectorWin',
        'OrientTdm.Collab.Data.DevData.Common.UploadFileWin'
    ],
    initComponent: function () {
        var me = this;

        me.cellEditing = new Ext.grid.plugin.CellEditing({
            clicksToEdit: 2
        });
        var store = me.createStore.call(me);
        store.pageSize = globalPageSize || 25;
        var columns = me.createColumn.call(me);

        Ext.apply(me, {
            columns: columns,
            store: store,
            plugins: [me.cellEditing],
            useArrows: true,
            rootVisible: false,
            multiSelect: true,
            viewConfig: {
                getRowClass: me._changeRowClass
            }
        });
        me.callParent(arguments);
    },
    createStore: function () {
        var me = this;
        return Ext.create('Ext.data.TreeStore', {
            model: 'OrientTdm.CollabDev.Designing.ResultSettings.DevData.Model.DevDataModel',
            autoLoad: true,
            proxy: {
                type: 'ajax',
                api: {
                    'read': serviceName + '/dataObj/getRefDataObj.rdm',
                    'create': serviceName + '/dataObj/createDataObj.rdm',
                    'update': serviceName + '/dataObj/updateDataObj.rdm',
                    'destroy': serviceName + '/dataObj/deleteDataObj.rdm'
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'msg'
                },
                writer: {
                    type: 'json',
                    allowSingle: false,
                    root: 'data'
                },
                extraParams: {
                    nodeId: me.nodeId,
                    nodeType: me.nodeType,
                    isglobal: me.isGlobal
                }
            },
            listeners: {
                beforesync: function () {
                }
            }
        });
    },
    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'checkchange', me.selectionchange, me);
        me.mon(me, 'celldblclick', me.celldblclick, me);
    },
    celldblclick: function (view, td, cellIndex, record, tr, rowIndex) {
        return false;
    },
    createColumn: function () {
        var me = this;
        return [
            {
                header: '上游名称',
                flex: 1,
                sortable: true,
                dataIndex: 'upName'
            },
            {
                xtype: 'treecolumn',
                header: '名称',
                flex: 1,
                sortable: true,
                dataIndex: 'dataobjectname',
                editor: {
                    xtype: 'textfield'
                },
                renderer: function (value, p, record) {
                    var retVal = value;
                    if (me.isSelfData(record)) {
                        retVal = '<span style="color:red;">' + value + '</span>';
                    }
                    return retVal;
                }
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
                        retVal = Ext.isDate(value) ? Ext.Date.format(value, 'Y-m-d') : value.substr(0, value.indexOf('T'));
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
                                '&nbsp;',
                                '<a href="javascript:;" onclick="OrientExtend.FileColumnDesc.deleteGridFile(\'' + gridId + '\',\'' + recordId + '\',\'value\');" class="cancel">',
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
                dataIndex: 'version',
                renderer: function (value, column, record) {
                    var retVal = '';
                    if (record.parentNode.isRoot()) {
                        retVal = value;
                    }
                    return retVal;
                }
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
            }, {
                header: '历史版本',
                xtype: 'actioncolumn',
                width: 100,
                align: 'center',
                sortable: false,
                menuDisabled: true,
                items: [{
                    icon: 'app/images/icons/default/common/detail.png',
                    tooltip: '历史版本',
                    scope: this,
                    handler: function (grid, rowIndex, colIndex, column, event, record) {
                        var isref = record.get('isref');
                        var layout = me.ownerCt.getLayout();
                        if (isref == 8) {  //如果是复杂数据类型，显示treeGrid
                            layout.next();
                            var complexHisPanel = layout.getNext();
                            complexHisPanel.getStore().getProxy().setExtraParam('nodeId', record.get('nodeId'));
                            complexHisPanel.getStore().getProxy().setExtraParam('originalObjId', record.get('originalObjId'));
                            complexHisPanel.getStore().getProxy().setExtraParam('isglobal', record.get('isglobal'));
                            complexHisPanel.getStore().load();
                            layout.setActiveItem(2);
                        } else { //如果不是复杂类型，显示groupGrid
                            var hisPanel = layout.getNext();
                            hisPanel.getStore().getProxy().setExtraParam('nodeId', record.get('nodeId'));
                            hisPanel.getStore().getProxy().setExtraParam('originalObjId', record.get('originalObjId'));
                            hisPanel.getStore().getProxy().setExtraParam('isglobal', record.get('isglobal'));
                            hisPanel.getStore().load();
                            layout.setActiveItem(1);
                        }
                        var combo = Ext.ComponentQuery.query('processingDevDataMainTabPanel')[0].down('selfDevDataPanel').down('#taskVersionSwitchCombo');
                        combo.disable();
                    },
                    isDisabled: function (view, rowIdx, colIdx, item, record) {
                        return !record.parentNode.isRoot();
                    }
                }]
            }
        ];
    },
    createToolBarItems: function () {
        var me = this;
        var retVal = [];
        if (me.isShowLeftBar) {
            retVal.push({
                xtype: 'tbtext',
                text: '<span style="color: red">★所在列可双击编辑</span>'
            });
        } else {
            return null;
        }
        return retVal;
    },
    isSelfData: function (record) {
        var me = this;
        var retVal = false;
        if (me.mustSubmitData && record.get('dataid') == me.dataId && record.get('modelid') == me.modelId) {
            //必填项
            retVal = true;
        }
        return retVal;
    }
});
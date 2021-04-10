/**
 * 可编辑树形表格
 * Created by Administrator on 2016/7/21 0021.
 */
Ext.define('OrientTdm.CollabDev.Designing.ResultSettings.DevData.Grid.DevDataSettingGrid', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.devDataSettingGrid',
    config: {
        nodeId: '',
        nodeVersion: '',
        nodeType: null,
        isGlobal: 0,
        isShowLeftBar: true,    //是否显示左侧的按钮
        showOtherFunctionButtonType: 0,  //0显示映射和审批按钮，1显示版本修改，强制修改，工作量填写按钮 2这几个按钮都不显示,
        canCelldblclick: true,   //grid条目是否可以双击
        queryUrl: null
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
        var toolBarItems = me.createToolBarItems.call(me);
        var leftBar = me.isShowLeftBar ? me.createLeftBar.call(me) : null;
        var toolBar = toolBarItems ? Ext.create('Ext.toolbar.Toolbar', {
            items: toolBarItems
        }) : null;
        var bbar = me.createBBar.call(me);
        Ext.apply(me, {
            columns: columns,
            lbar: leftBar,
            dockedItems: toolBar,
            bbar: bbar,
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
    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'checkchange', me.selectionchange, me);
        me.mon(me, 'celldblclick', me.celldblclick, me);
        me.mon(me, 'select', me.select, me);
    },
    createStore: function () {
        var me = this;
        return Ext.create('Ext.data.TreeStore', {
            model: 'OrientTdm.CollabDev.Designing.ResultSettings.DevData.Model.DevDataModel',
            autoLoad: true,
            proxy: {
                type: 'ajax',
                api: {
                    'read': me.queryUrl == null ? serviceName + '/dataObj/getDataObj.rdm' : me.queryUrl,
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
                    //nodeVersion: me.nodeVersion,
                    isglobal: me.isGlobal
                }
            },
            listeners: {
                beforesync: function () {
                    // me.down('statusbar').showBusy();
                }
            }
        });
    },
    createColumn: function () {
        var me = this;
        var editMarker = '(★)';
        return [
            {
                xtype: 'treecolumn',
                header: '名称' + editMarker,
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
                header: '类型' + editMarker,
                flex: 1,
                sortable: true,
                dataIndex: 'dataTypeShowName',
                editor: {
                    xtype: 'textfield'
                }
            }, {
                header: '值' + editMarker,
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
                        if(isref == 8){  //如果是复杂数据类型，显示treeGrid
                            layout.next();
                            var complexHisPanel = layout.getNext();
                            complexHisPanel.getStore().getProxy().setExtraParam('nodeId', record.get('nodeId'));
                            complexHisPanel.getStore().getProxy().setExtraParam('originalObjId', record.get('originalObjId'));
                            complexHisPanel.getStore().getProxy().setExtraParam('isglobal', record.get('isglobal'));
                            complexHisPanel.getStore().load();
                            layout.setActiveItem(2);
                        }else { //如果不是复杂类型，显示groupGrid
                            var hisPanel = layout.getNext();
                            hisPanel.getStore().getProxy().setExtraParam('nodeId', record.get('nodeId'));
                            hisPanel.getStore().getProxy().setExtraParam('originalObjId', record.get('originalObjId'));
                            hisPanel.getStore().getProxy().setExtraParam('isglobal', record.get('isglobal'));
                            hisPanel.getStore().load();
                            layout.setActiveItem(1);
                        }
                        var combo =  Ext.ComponentQuery.query('processingDevDataMainTabPanel')[0].down('selfDevDataPanel').down('#taskVersionSwitchCombo');
                        combo.disable();
                    },
                    isDisabled: function (view, rowIdx, colIdx, item, record) {
                        return !record.parentNode.isRoot();
                    }
                }]
            }
        ];
    },
    createLeftBar: function () {
        var me = this;
        var retVal = [];
        switch (me.showOtherFunctionButtonType) {
            case 0:
                retVal.push(
                    {
                        cls: 'icon-collabDev-mapping ',
                        tooltip: '数据映射，可级联映射',
                        itemId: 'mapping',
                        scope: me,
                        handler: me.setMapping
                    },
                    {
                        cls: 'icon-collabDev-audit',
                        tooltip: '交付物审批设置',
                        itemId: 'approval',
                        scope: me,
                        handler: me.setApproval
                    });
                break;
            default:
                break;
        }

        retVal.push(
            {
                xtype: 'buttongroup',
                itemId: 'baseButtonGroup',
                title: '基本',
                columns: 1,
                items: [
                    {
                        iconCls: 'icon-collabDev-add',
                        tooltip: '新增',
                        itemId: 'create',
                        scope: me,
                        handler: me._onCreateClick
                    }, {
                        iconCls: 'icon-collabDev-delete',
                        tooltip: '删除',
                        itemId: 'delete',
                        scope: me,
                        handler: me._onDeleteClick
                    },
                    {
                        iconCls: 'icon-save',
                        tooltip: '保存',
                        itemId: 'save',
                        scope: me,
                        handler: me._onSaveClick
                    }, {
                        iconCls: 'icon-refresh',
                        tooltip: '刷新',
                        itemId: 'refresh',
                        scope: me,
                        //刷新按钮添加鼠标防抖动保护
                        handler: OrientExtUtil.EventHelper.debounce.call(me, me._onRefreshClick, 200)
                    }
                ]
            }
        );
        return retVal;
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
    createBBar: function () {
        /*return Ext.create("Ext.ux.statusbar.StatusBar", {
         text: '已保存',
         iconCls: 'x-status-valid'
         });*/
    },
    _onCreateClick: function () {
        var me = this;
        var childNodes = this.getRootNode().childNodes;
        var maxOrder = 1;
        Ext.each(childNodes, function (childNode) {
            var orderNumber = Number(childNode.get('ordernumber'));
            maxOrder = Math.max(orderNumber, maxOrder);
        });
        var record = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.DevData.Model.DevDataModel', {
            dataobjectname: '新增参数',
            dataTypeShowName: '文件',
            datatypeId: '5',
            leaf: true,
            isref: '1',
            extendsTypeRealName: 'file',
            isglobal: me.isGlobal,
            checked: false,
            nodeId: me.nodeId,
            nodeVersion: me.nodeVersion,
            createBy: 1
        });

        var root = me.getRootNode();
        root.insertChild(0, record);
    },
    _onDeleteClick: function () {
        var me = this;
        var selectedRecords = me.getCheckedRecords();
        Ext.each(selectedRecords, function (record) {
            record.remove();
        });
    },
    _onSaveClick: function () {
        var me = this;
        this.store.sync({
            success: function () {
                if (null == me.successCallBack) {
                    me._onRefreshClick();
                } else {
                    me.successCallBack.call(me);
                }
            }
        });
    },
    _onRefreshClick: function () {
        /* this.down('statusbar').setStatus({
         text: '已保存',
         iconCls: 'x-status-valid'
         });*/
        this.getSelectionModel().clearSelections();
        var store = this.getStore();

        var root = this.getRootNode();
        store.load({node: root});
    },
    _onMove: function (button) {
        var me = this;
        //先保存
        var store = me.getStore();
        var dirty = store.getNewRecords().length > 0 || store.getUpdatedRecords().length > 0 || store.getRemovedRecords().length > 0;
        if (dirty) {
            Ext.Msg.confirm(OrientLocal.prompt.confirm, '有未保存的数据，是否需要保存', function (btn) {
                if (btn == 'yes') {
                    me._onSaveClick();
                }
            });
        } else {
            //请求数据库 保存排序
            var records = me.getCheckedRecords(false);
            var node = records[0];
            var toChaneNode = button.text == '上移' ? node.previousSibling : node.nextSibling;
            if (toChaneNode) {
                me._swapDataObjOrderNum(node, toChaneNode);
            } else {
                OrientExtUtil.Common.tip(OrientLocal.prompt.info, button.text == '上移' ? OrientLocal.prompt.alreadyTop : OrientLocal.prompt.alreadyBottom);
            }
        }
    },
    selectionchange: function (node, checked) {
        var me = this;
        //勾选父节点 以及 兄弟节点
        var parentNode = node.parentNode;
        if (!parentNode.isRoot()) {
            parentNode.set("checked", checked);
            //勾选兄弟节点
            parentNode.eachChild(function (child) {
                child.set("checked", checked);
            });
        }
        //递归勾选子节点
        node.expand(true, function () {
            node.eachChild(function (child) {
                child.set("checked", checked);
                me.fireEvent('checkchange', child, checked);
            });
        });
    },
    celldblclick: function (view, td, cellIndex, record, tr, rowIndex) {
        var me = this;
        if (!me.canCelldblclick) {
            return false;
        }
        var belongGrid = view.up('devDataSettingGrid');
        var clickedColumn = belongGrid.columns[cellIndex];
        var clickedcolumnIndex = clickedColumn.dataIndex;
        if ('dataTypeShowName' == clickedcolumnIndex) {
            //类型选择器
            me._popDataTypeSelectorWin(record);
            return false;
        } else if ('value' == clickedcolumnIndex) {
            //如果拥有子节点 则说明为复杂类型 无法修改
            if (record.childNodes.length > 0) {
                OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.complicateUnEditable);
                return false;
            } else
                return me._switchValueEditor(record, clickedColumn);
        }
    },
    select: function (rowModel, record, index) {
        //record.set("checked", true);
        //this.fireEvent('checkchange', record, true);
    },
    _popDataTypeSelectorWin: function (record) {
        var me = this;
        //判断是否有父节点 如果有父节点 则不可修改类型
        if (record.parentNode.isRoot() == false) {
            OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.unModifyAble);
        } else if (!Ext.isEmpty(record.get('id'))) {
            OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.reAddParam);
        } else {
            //弹出选择参数类型的窗口
            Ext.create('OrientTdm.Collab.Data.DevData.Common.DataTypeSelectorWin', {
                bindRecord: record,
                afterSelected: function () {
                    //me._onSaveClick()
                }
            });
        }
    },
    _switchValueEditor: function (record, clickedColumn) {
        var me = this;
        //根据参数类型 初始化不同的值输入器
        var extendsTypeRealName = record.get('extendsTypeRealName');
        var isref = record.get('isref');
        if ('4' == String(isref)) {
            //枚举类型 动态枚举
            var dataTypeId = record.get('datatypeId');
            var enumCombobox = Ext.create('OrientTdm.Common.Extend.Form.Field.OrientComboBox', {
                editable: false,
                remoteUrl: serviceName + '/DataSubType/getDataSubType.rdm?dataTypeID=' + dataTypeId,
                displayField: 'datasubname',
                valueField: 'datasubname',
                initFirstRecord: true
            });
            clickedColumn.setEditor(enumCombobox);
        } else if ('8' == String(isref)) {
            //复杂类型 只可编辑子项
            return false;
        } else {
            if ('date' == extendsTypeRealName) {
                clickedColumn.setEditor({
                    xtype: 'datefield',
                    format: 'Y-m-d',
                    editable: false,
                    listeners: {
                        "focus": function () {
                            if (!this.readOnly) {
                                this.expand();
                            }
                        },
                        "blur": function () {
                            this.collapse();
                        },
                        "change": function (field, newValue) {

                        }
                    }
                });
            } else if ('file' == extendsTypeRealName) {
                //弹出文件窗口
                Ext.create('OrientTdm.Collab.Data.DevData.Common.UploadFileWin', {
                    bindRecord: record
                });
                return false;
            } else if ('boolean' == extendsTypeRealName) {
                //checkbox
                clickedColumn.setEditor({
                    xtype: 'checkbox'
                });
            } else if ('integer' == extendsTypeRealName) {
                clickedColumn.setEditor({
                    xtype: 'numberfield',
                    decimalPrecision: 0
                });
            } else if ('double' == extendsTypeRealName) {
                clickedColumn.setEditor({
                    xtype: 'numberfield',
                    decimalPrecision: 10
                });
            } else {
                clickedColumn.setEditor({
                    xtype: 'textfield'
                });
            }
        }
        return true;
    },
    _onPushClicked: function () {
        var me = this;
        var records = me.getCheckedRecords();
        Ext.each(records, function (record) {
            record.set('isglobal', 1);
        });
        me._refreshAllDataPanel('push');

    },
    _onUndoClicked: function () {
        var me = this;
        //只有个人数据才可以取消共享
        var records = me.getCheckedRecords();
        var errParamNames = [];
        Ext.each(records, function (record) {
            if (record.get('createBy') == 1) {
                errParamNames.push(record.get('dataobjectname'));
            }
        });
        if (errParamNames.length == 0) {
            Ext.each(records, function (record) {
                record.set('isglobal', 0);
            });
            me._refreshAllDataPanel('undo');
        } else {
            OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.onlyPrivateCanUnShare + '：【' + errParamNames.join(',') + '】不符合');
        }

    },
    _containsSonParam: function (records) {
        //判断是否包含子项
        var containsSonParam = false;
        var idArray = Ext.Array.pluck(records, 'id');
        Ext.each(records, function (record) {
            if (!record.parentNode.isRoot() && !Ext.Array.contains(idArray, record.parentNode.id)) {
                containsSonParam = true;
            }
        });
        if (containsSonParam == true) {
            OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.onlyMainParam);
        }
        return containsSonParam;
    },
    _refreshAllDataPanel: function (type) {
        var me = this;
        //刷新所有 数据面板
        var dataDashBord = me.up('devDataDashBord');
        if (dataDashBord) {
            var sharePanel = dataDashBord.down('devDataCard#shareData devDataInstance');
            var privateShare = dataDashBord.down('devDataCard#privateData devDataInstance');
            //先后保存顺序
            if ('undo' == type) {
                if (sharePanel) {
                    sharePanel._onSaveTypeChange.call(sharePanel, privateShare);
                }

            } else if ('push' == type) {
                if (privateShare) {
                    privateShare._onSaveTypeChange.call(privateShare, sharePanel);
                }

            }
        }
    },
    _swapDataObjOrderNum: function (node, toSwapNode) {
        node.setDirty();
        toSwapNode.setDirty();
        var me = this;
        var proxy = me.getStore().getProxy();
        var originalUrl = proxy.api['update'];
        proxy.api['update'] = serviceName + '/DataObj/swapDataObjOrderNum.rdm';
        me.store.sync({
            callback: function () {
                proxy.api['update'] = originalUrl;
                me._onRefreshClick();
            }
        });
    },
    _onSaveTypeChange: function (targetPanel) {
        var me = this;
        var proxy = me.getStore().getProxy();
        var originalUrl = proxy.api['update'];
        proxy.api['update'] = serviceName + '/DataObj/convertShareAbleDataObj.rdm';
        me.store.sync({
            callback: function () {
                proxy.api['update'] = originalUrl;
                me._onRefreshClick();
                targetPanel._onRefreshClick();
            }
        });
    },
    _changeRowClass: function (record, rowIndex, rowParams, store) {
        //var me = this;
        //var treeGrid = me.up('devDataInstance');
        //if (record.get('dataid') == treeGrid.dataId && record.get('modelid') == treeGrid.modelId) {
        //    //必填项
        //    return 'x-grid-record-yellow'
        //}
    },
    getCheckedRecords: function (containsSon) {
        var retVal = [];
        var me = this;
        var rootNode = me.getRootNode();
        getCheckData(rootNode);

        function getCheckData(node) {
            Ext.each(node.childNodes, function (childNode) {
                var extraFlag = containsSon == false ? childNode.parentNode.isRoot() : true;
                if (extraFlag && childNode.get('checked') == true) {
                    retVal.push(childNode);
                }
                getCheckData(childNode);
            });
        }

        return retVal;
    },
    canMove: function (button) {
        var me = this;
        //请求数据库 保存排序
        var records = me.getCheckedRecords(false);
        var node = records[0];
        if (records.length == 0) {
            OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.atleastSelectOne);
        } else if (records.length > 1) {
            OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.onlyCanSelectOne);
        } else {
            var containsSonParam = me._containsSonParam(records);
            if (containsSonParam == false) {
                var toChaneNode = button.text == '上移' ? node.previousSibling : node.nextSibling;
                if (toChaneNode) {
                    //比对dataid是否一致
                    if (node.get('dataid') == toChaneNode.get('dataid')) {
                        return true;
                    } else {
                        OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.canNotMove);
                    }
                } else {
                    OrientExtUtil.Common.tip(OrientLocal.prompt.info, button.text == '上移' ? OrientLocal.prompt.alreadyTop : OrientLocal.prompt.alreadyBottom);
                }
            }
        }
        return false;
    },
    isSelfData: function (record) {
        var me = this;
        var retVal = false;
        if (me.mustSubmitData && record.get('dataid') == me.dataId && record.get('modelid') == me.modelId) {
            //必填项
            retVal = true;
        }
        return retVal;
    },
    reloadData: function (nodeVersion) {
        var me = this;
        var store = me.getStore();
        store.proxy.extraParams.nodeVersion = nodeVersion;
        store.load();
    }
});



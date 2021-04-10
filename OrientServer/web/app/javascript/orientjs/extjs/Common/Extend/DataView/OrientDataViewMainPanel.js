/**
 * 注意，本控件是为了加载数据量少的情况下使用，优化用户体验。
 * 本面板是在DataView的基础上和原有的GirdView-API接口进行整合，修改了表现形式而已
 * 数据量过大还是要使用OrientGridView
 * Created by liuyangchao on 2019/2/19.
 */
Ext.define('OrientTdm.Common.Extend.DataView.OrientDataViewMainPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.OrientDataViewMainPanel',
    requires: [
        "OrientTdm.Common.Extend.DataView.OrientFeedView",
        'Ext.data.*',
        'Ext.util.*',
        'Ext.view.View',
        'Ext.ux.DataView.Animated',
        'Ext.XTemplate',
        'Ext.panel.Panel',
        'Ext.toolbar.*',
        'Ext.slider.Multi',
        "OrientTdm.Common.Extend.Form.OrientAddModelForm",
        "OrientTdm.Common.Extend.Form.OrientModifyModelForm",
        "OrientTdm.Common.Extend.Form.OrientDetailModelForm",
        "OrientTdm.Common.Extend.Form.OrientQueryModelForm",
        "OrientTdm.DataMgr.DataAnalysis.OnlineChartingWindow",
        "OrientTdm.Common.Util.HtmlTriggerHelper",
        "Ext.ux.DataView.DragSelector",
        "OrientTdm.ProductStructureMgr.CheckUtil.TestProcessUtil"
    ],
    id:'orientdataview702',
    loadMask: true,
    config: {
        //dataview需要的数据
        liuyangchaoTpl: '',
        dataViewkeyArray: [],
        dataViewValueArray: [],
        sColumnNameArray: [],
        dataviewconfig: [],
        mainKey: '',
        leftKey: '',
        leftKey1: '',
        rightKey: '',
        rightKey1: '',
        columnsDataView: '',
        columnsList: '',
        //--ModelGrid--//
        modelId: '',
        isView: '',
        templateId: '',
        //初始过滤条件
        customerFilter: [],
        //查询过滤条件
        queryFilter: [],
        modelDesc: {},
        queryUrl: '',
        createUrl: '',
        updateUrl: '',
        deleteUrl: '',
        dataList: null,
        loadDataFirst: true,
        formInitData: null,
        showAnalysisBtns: true,
        //--ModelGrid--//
        //前后处理器
        beforeInitComponent: Ext.emptyFn,
        afterInitComponent: Ext.emptyFn,
        actionItems: []
    },
    //视图初始化
    createToolBarItems: function () {
        var me = this;
        var retVal = [];
        //获取模型操作描述
        var btns = me.modelDesc.btns;
        Ext.each(btns, function (btn) {
            retVal.push({
                iconCls: 'icon-' + btn.code,
                text: btn.name,
                scope: me,
                btnDesc: btn,
                handler: Ext.bind(me.onGridToolBarItemClicked, me)
            });
        });
        return retVal;
    },
    createColumns: function () {
        var me = this;
        var retVal = [];
        //获取模型操作描述
        var modelDesc = me.modelDesc;
        me.columnsList = new Array();
        if (modelDesc && modelDesc.columns) {
            Ext.each(modelDesc.columns, function (column) {
                if (Ext.Array.contains(modelDesc.listColumnDesc, column.id)) {
                    retVal[Ext.Array.indexOf(modelDesc.listColumnDesc, column.id)] = OrientModelHelper.createGridColumn(column);
                    me.columnsList.push(column.id);
                }
            });
        }
        return retVal;
    },
    createStore: function () {
        var me = this;
        //获取fields
        var fields = [{
            name: 'ID'
        }];
        //获取模型操作描述
        var modelDesc = me.modelDesc;
        if (modelDesc && modelDesc.columns) {
            Ext.each(modelDesc.columns, function (column) {
                fields.push({
                    name: column.sColumnName
                });
            });
        }
        var retVal;
        if (me.dataList) {
            //直接加载数据 内存加载数据暂不支持 新增、修改、删除、查询等操作
            retVal = Ext.create('Ext.data.Store', {
                fields: fields,
                autoLoad: me.loadDataFirst,
                remoteSort: false,
                data: me.dataList,
                proxy: {
                    type: 'memory',
                    reader: {
                        type: 'json'
                    }
                }
            });
        } else {
            //从后台加载数据
            retVal = Ext.create('Ext.data.Store', {
                fields: fields,
                autoLoad: me.loadDataFirst,
                remoteSort: true,
                proxy: {
                    type: 'ajax',
                    actionMethods: {
                        create: 'POST',
                        read: 'POST',
                        update: 'POST',
                        destroy: 'POST'
                    },
                    api: {
                        "read": me.queryUrl == "" ? serviceName + "/modelData/getModelData.rdm" : me.queryUrl,
                        "create": me.createUrl == "" ? serviceName + "/modelData/saveModelData.rdm" : me.createUrl,
                        "update": me.updateUrl == "" ? serviceName + "/modelData/updateModelData.rdm" : me.updateUrl,
                        "delete": me.deleteUrl == "" ? serviceName + "/modelData/deleteModelData.rdm" : me.deleteUrl
                    },
                    extraParams: {
                        orientModelId: me.modelId,
                        isView: me.isView,
                        customerFilter: Ext.isEmpty(me.customerFilter) ? "" : Ext.encode(me.customerFilter)
                    },
                    reader: {
                        type: 'json',
                        successProperty: 'success',
                        root: 'results',
                        totalProperty: 'totalProperty',
                        idProperty: 'ID',
                        messageProperty: 'msg'
                    },
                    listeners: {}
                }
            });
        }
        return retVal;
    },
    beforeInitComponent: function () {
        //初始化面板前执行
        var me = this;
        if (me.modelDesc == null || !me.modelDesc.hasOwnProperty("modelId")) {
            if (Ext.isEmpty(me.modelId)) {
                OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.unBindModel);
            }
            var params = {
                modelId: me.modelId,
                isView: me.isView,
                templateId: me.templateId
            };
            //初始化模型描述
            OrientExtUtil.AjaxHelper.doRequest(serviceName + "/modelData/getGridModelDesc.rdm", params, false, function (response) {
                //1.获取模型字段描述 字段名称 显示名 类型...
                //2.获取模型操作描述 新增/修改/删除/查询/导入/导出...
                me.modelDesc = response.decodedData.results.orientModelDesc;
            });
        }
    },
    initDataView: function(){
        var me = this;
        //构造Keys的数组
        var detailObject = new Object();
        detailObject.sColumnName="ID";
        me.sColumnNameArray.splice(0,me.sColumnNameArray.length);
        me.dataViewkeyArray.splice(0,me.dataViewkeyArray.length);
        me.sColumnNameArray.push(detailObject.sColumnName);
        me.dataViewkeyArray.push(detailObject);
        $.each(me.modelDesc.columns, function(key,value){
            var rowObject = value;
            if(rowObject != null){
                detailObject = new Object();
                // console.log(value);
                detailObject.className = rowObject.className;
                detailObject.modelId = rowObject.modelId;
                detailObject.sModelName = rowObject.sModelName;
                detailObject.text = rowObject.text;
                if("C_Relation"==rowObject.type){
                    detailObject.name = rowObject.sColumnName+"_display";
                    detailObject.sColumnName = rowObject.sColumnName+"_display";
                }else{
                    detailObject.name = rowObject.sColumnName
                    detailObject.sColumnName = rowObject.sColumnName;
                }
                detailObject.type = 'string';
                detailObject.id = rowObject.id;
                detailObject.protype = rowObject.type;
                // detailObject.type = rowObject.type;
                me.sColumnNameArray.push(detailObject.sColumnName);
                me.dataViewkeyArray.push(detailObject);
            }
        });
        // console.log(me.dataViewkeyArray);
        Ext.define('dataViewValueModel', {
            extend: 'Ext.data.Model',
            fields: me.dataViewkeyArray
        });
        me.dataviewconfig.push(me.mainKey);
        me.dataviewconfig.push(me.leftKey);
        me.dataviewconfig.push(me.rightKey);
        console.log(me.dataViewkeyArray);
    },
    initComponent: function () {
        var me = this;
        //初始化
        me.actionItems = [];
        me.beforeInitComponent.call(me);
        //定义Store
        var store = me.createStore();
        me.store = store;
        // console.log(me.store);
        store.pageSize = me.pageSize;
        //加载数据后触发事件
        store.on('load', function (store, record) {
            // console.log(store);
           me.dataViewValueArray.splice(0,me.dataViewValueArray.length);
            $.each(store.data.items, function(key, value){
                var dataViewValue = [];
                for(var column in me.sColumnNameArray){
                    // console.log(value.data);
                    var realValue = '---';
                    if(me.sColumnNameArray[column].indexOf("_display") != -1){
                        var tempObject = Ext.JSON.decode(value.raw[me.sColumnNameArray[column]]);
                        //var tempObject = JSON.parse(value.raw[me.sColumnNameArray[column]]);
                        if(typeof tempObject == 'undefined'){
                            realValue = '---';
                        }else{
                            realValue = tempObject[0].name;
                        }
                        //console.log(realValue);
                        //var aaaa = tempObject[0];
                        //console.log(aaaa);
                    }else{
                        realValue = value.raw[me.sColumnNameArray[column]];
                        if(realValue == null){
                            realValue = '---';
                        }
                        //if(me.sColumnNameArray[column]=='C_EXCEPTION_'+me.modelId){
                        //      if(realValue=="是"){
                        //          document.getElementById('main').color="#FF0000";
                        //      }
                        //}
                    }
                    //var realValue = value.raw[me.sColumnNameArray[column]];
                    //if(realValue == null){
                    //    realValue = '---';
                    //}
                    dataViewValue.push(realValue);
                }
                me.dataViewValueArray.push(dataViewValue);
                console.log(me.dataViewValueArray);
            });
            Ext.getCmp('lyctest').store.load(Ext.create('Ext.data.ArrayStore', {
                model: 'dataViewValueModel',
                sortInfo: {
                    field    : 'sColumnName',
                    direction: 'ASC'
                },
                data: me.dataViewValueArray
            }));
        });
        //定义Columns
        var columns = me.createColumns.call(me);
        me.columnsDataView = columns;
        // console.log(columns);
        //定义contextmenu
        var toolBarItems = me.createToolBarItems.call(me);
        var contextMenu = Ext.create('Ext.menu.Menu', {
            items: toolBarItems
        });
        //增加actionColumn
        var actionColumns = me._initActionColumns();
        Ext.Array.insert(columns, 0, actionColumns);
        //定义top菜单栏
        var toolBar = toolBarItems && toolBarItems.length > 0 ? Ext.create('Ext.toolbar.Toolbar', {
            items: toolBarItems
        }) : null;
        Ext.Object.merge(me, {
            viewConfig: {
                stripeRows: true,
                listeners: {
                    itemcontextmenu: function (view, rec, node, index, e) {
                        e.stopEvent();
                        contextMenu.showAt(e.getXY());
                        return false;
                    }
                }
            },
            dockedItems: [toolBar],
            columns: columns,
            store: store,
            selModel: {
                mode: me.multiSelect ? 'MULTI' : 'SINGLE'
            },
            selType: me.selType
        });

        this.addEvents({
            //自定义刷新
            refreshGridByCustomerFilter: true,
            //根据查询条件刷新
            refreshGridByQueryFilter: true,
            //保存数据前
            afterCreateData: true,
            afterUpdateData: true,
            afterDeleteData: true,
            afterStartAuditFlow: true
        });
        this.addEvents("refreshGridByCustomerFilter", "refreshGridByQueryFilter", "refreshGridByTreeNode");
        me.initDataView.call(me);

        var storeDataView = Ext.create('Ext.data.ArrayStore', {
            model: 'dataViewValueModel',
            sortInfo: {
                field    : 'sColumnName',
                direction: 'ASC'
            },
            data: me.dataViewValueArray
        });
        var dataview = Ext.create('Ext.view.View', {
            id: 'lyctest',
            store: storeDataView,
            multiSelect: true,
            height: 310,
            trackOver: true,
            overItemCls: 'x-item-over',
            itemSelector: 'div.showmore',
            emptyText: 'No images to display',
            autoScroll : true,
            tpl  : Ext.create('Ext.XTemplate',
                '<tpl for=".">',
                '<div style="width: 15%; height: 180px; float: left; text-align:center; margin: 5px; border-radius: 10px;">',
                '<div style="height: 144px; background-color:#F0F0F0; color:#01AAED; font-size: 20px; ">',

                '<div style="height: 50%; text-align: center; display: flex; align-items: center;">',
                //'<tpl if="this.isGirl("+"[C_EXCEPTION_"+me.modelId+"])"=="是">',
                '<span id="main";style="width: 100%";color:#FF0000;">{' +
                me.mainKey +
                '}</span>',
                //'</tpl>',
                '</div>',

                '<div style="height: 50%; font-size: 12px; text-align: center; display: flex; align-items: center;">',
                '<div style="width: 49%; float:left;">',
                '<span>{' +
                me.leftKey +
                '}</span>',
                '</div>',
                '<div style="background-color:#01AAED; width: 2px; height: 60%; float:left;"></div>',
                '<div style="width: 49%; float:right;">',
                '<span>{' +
                me.rightKey +
                '}</span>',
                '</div>',
                '</div>',

                '</div>',
                '<div class="showmore" style="padding: 10px; height: 36px; background-color:#01AAED; color: #F0F0F0">',
                '<span>显示详情</span>',
                '</div>',
                '</div>',
                '</tpl>'
            ),
            listeners: {
                itemclick: {
                    fn:function(view, record, item, index, evt, eOpts){
                        var items = [];
                        $.each(me.dataViewkeyArray, function(key, value){
                            var displayFront = value.text;
                            var displayEnd = value.sColumnName;
                            var displayEndShow = record.data[displayEnd];
                            var item = {};
                            item.xtype = 'displayfield';
                            item.fieldLabel = displayFront;
                            item.value = displayEndShow;
                            console.log(value);
                            console.log(me.columnsList);
                            if (item.value == "是") {
                                document.getElementById('main').style.color="#FF0000";
                            }
                            if (Ext.Array.contains(me.columnsList, value.id)) {
                                items.push(item);
                            }
                            // item.value = '<span style="color:green;">'+displayEndShow+'</span>';
                        });
                        // console.log(items);
                        // console.log(record);
                        var panel2 = this.up('panel').up('panel').down('panel');
                        panel2.loadRecord(items);
                        var checkTempId=record.raw[0];
                        me.preview(checkTempId, true, me.productId);
                    }
                },
                //selectionchange:function(dv,nodes){
                //    var select=nodes.length;
                //    var checkTempId=nodes[0].raw[0];
                //    me.preview(checkTempId, true, me.productId);
                //}
            }
        });
        me.layout= 'border';
        me.bodyBorder= false;
        me.items= dataview;
        me.autoScroll = true;

        me.afterInitComponent.call(me);
        this.callParent(arguments);
        this.addEvents('refreshGrid', 'filterByForm', 'afterLoadData');
        // me.afterLoadData(storeDataView);
    },
    afterLoadData: function(storeDataView){
        //console.log(storeDataView);
        storeDataView.reload();
    },
    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'loadViewTplData', me.loadViewTplData, me);
        me.mon(me, 'loadViewData', me.loadViewData, me);
    },
    //删除表格数据
    onDeleteClick: function () {
        var me = this;
        if (me.getStore() && me.getStore().getProxy() && me.getStore().getProxy().api) {
            OrientExtUtil.GridHelper.deleteRecords(me, me.getStore().getProxy().api['delete'], function () {
                me.fireEvent('refreshGrid');
            });
        } else {
            OrientExtUtil.Common.err(OrientLocal.prompt.error, '未定义删除Url');
        }
    },
    onCreateClick: function (cfg) {
        if (cfg.formConfig.appendParam) {
            var extraParam = cfg.formConfig.appendParam.call(this);
            Ext.apply(cfg.formConfig, extraParam);
        }
        //弹出新增面板窗口
        var win = Ext.create('Ext.Window', Ext.apply({
            plain: true,
            height: 0.7 * globalHeight,
            width: 0.7 * globalWidth,
            layout: 'fit',
            maximizable: true,
            modal: true,
            items: [
                Ext.create(cfg.formConfig.formClassName, cfg.formConfig)
            ]
        }, cfg));
        win.show();
    },
    onUpdateClick: function (cfg) {
        var selections = this.getSelectionModel().getSelection();
        if (selections.length === 0) {
            OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.atleastSelectOne);
        } else if (selections.length > 1) {
            OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.onlyCanSelectOne);
        } else {
            if (cfg.formConfig.appendParam) {
                var extraParam = cfg.formConfig.appendParam.call(this);
                Ext.apply(cfg.formConfig, extraParam);
            }
            var updateWin = Ext.create('Ext.Window', Ext.apply({
                plain: true,
                height: 0.7 * globalHeight,
                width: 0.7 * globalWidth,
                layout: 'fit',
                maximizable: true,
                modal: true,
                items: [
                    Ext.create(cfg.formConfig.formClassName, cfg.formConfig)
                ]
            }, cfg));
            updateWin.show();
        }
    },
    _initActionColumns: function () {
        var me = this;
        var retVal = null;
        if (me.actionItems.length > 0) {
            var items = [];
            var index = 0;
            Ext.each(me.actionItems, function (actionItem) {
                items.push({
                    iconCls: actionItem.iconCls,
                    tooltip: actionItem.text,
                    handler: function (grid, rowIndex, colIndex) {
                        var record = grid.store.getAt(rowIndex);
                        me.getSelectionModel().deselectAll();
                        me.getSelectionModel().select(record, false, true);
                        var scope = actionItem.scope || actionItem;
                        actionItem.handler.apply(scope, arguments);
                    }
                });
                if (index < me.actionItems.length - 1) {
                    items.push(' ');
                }
                index++;
            });
            var width = 30 * ((items.length + items.length - 1) / 2);
            retVal = {
                xtype: 'actioncolumn',
                text: '操作',
                align: 'center',
                width: width < 50 ? 50 : width,
                items: items
            };
        }
        return null == retVal ? [] : [retVal];
    },
    onGridToolBarItemClicked: function (btn, event, eOpts) {
        var me = this;
        var btnDesc = btn.btnDesc;
        if (btnDesc.jspath) {
            Ext.require(btnDesc.jspath);
            var orientBtnInstance = Ext.create(btnDesc.jspath, {
                btnDesc: btnDesc
            });
            btn.orientBtnInstance = orientBtnInstance;
            orientBtnInstance.triggerClicked(me);
        } else {
            OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.unBindJSPath);
        }


    },
    //获取选中的记录
    getSelectedData: function () {
        var selections = this.getSelectionModel().getSelection();
        return selections;
    },
    onAddFeedClick: function(){
        var win = Ext.create('widget.orientfeedwindow', {

        });
        win.show();
    },
    loadViewTplData: function () {

    },
    loadViewData: function () {

    },

    preview: function (checkTempId, withData, productId) {
        //表单预览
        TestProcessUtil.CommonHelper.checkListPreview(checkTempId, true, withData, productId);
    }
});

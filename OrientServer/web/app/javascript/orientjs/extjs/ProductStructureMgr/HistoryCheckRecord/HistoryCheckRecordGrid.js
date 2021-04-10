/**
 * Created by User on 2019/3/13.
 */

Ext.define("OrientTdm.ProductStructureMgr.HistoryCheckRecord.HistoryCheckRecordGrid", {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.historyCheckRecordGrid',
    requires:["OrientTdm.ProductStructureMgr.CheckUtil.TestProcessUtil"],
    config:{
        pageSize: 25
    },
    initComponent: function () {
        var me = this;
        //modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        me.store = Ext.create('Ext.data.Store', {
            fields: ["id", "tableName", "recordTime", "uploader", "belongTask", "isException","deviceName"],
            remoteSort: false,
            //设置为 true 则将所有的过滤操作推迟到服务器
            remoteFilter: true,
            proxy: {
                type: 'ajax',
                url: serviceName + "/ProductStructrue/getHistoryCheckData.rdm",
                reader: {
                    type: 'json',
                    root: 'results',
                    totalProperty: 'totalProperty'
                },
                extraParams: {
                    productId: me.productId
                }
            },
            sorters: [{
                direction: 'desc'
            }],
            autoLoad: true
        });

        var gridPanel = Ext.create('Ext.grid.Panel', {
            id:'historyCheckRecordReload',
            store: me.store,
            region:'center',
            selModel: {
                selType: 'checkboxmodel'
            },
            columns: [
                {text: '表格名称', dataIndex: 'tableName', flex: 1, align: 'left|center'},
                {text: '记录时间', dataIndex: 'recordTime', flex: 1, align: 'left|center'},
                {text: '上传人', dataIndex: 'uploader', flex: 1, align: 'left|center'},
                {text: '所属任务', dataIndex: 'belongTask', flex: 1, align: 'left|center'},
                {text: '是否异常', dataIndex: 'isException', flex: 1, align: 'left|center'},
                {text: '相关设备名称', dataIndex: 'deviceName', flex: 1, align: 'left|center'}
            ],
            //bbar: [{
            //    xtype: 'pagingtoolbar',
            //    store: store,
            //    displayMsg: '显示 {0} - {1} 条，共计 {2} 条',
            //    emptyMsg: "没有数据",
            //    beforePageText: "当前页",
            //    afterPageText: "共{0}页",
            //    /* emptyMsg:"没有记录",
            //     displayInfo:true,
            //     displayMsg:"本页显示第{0}条到第{1}条的记录，一共{2}条",*/
            //    /*  displayInfo: true,*/
            //    dock: 'bottom'
            //}],
            viewConfig: {
                getRowClass: function (record, rowIndex, rowParams, store) {
                    if (record.data['isException'] == '是') {
                        return 'x-grid-record-red';
                    }
                }
            }
        });

        //store.loadPage(1);
        me.bbar = Ext.create('Ext.PagingToolbar', {
            store: me.store,
            displayInfo: true,
            displayMsg: '显示 {0} - {1} 条，共 {2} 条',
            emptyMsg: "没有数据",
            items: [
                {
                    xtype: 'tbseparator'
                },
                {
                    xtype: 'numberfield',
                    labelWidth: 40,
                    width: 100,
                    fieldLabel: '每页',
                    enableKeyEvents: true,
                    value: me.pageSize,
                    listeners: {
                        keydown: function (field, e) {
                            if (e.getKey() == Ext.EventObject.ENTER && !Ext.isEmpty(field.getValue())) {
                                var newPageSize = field.getValue();
                                var store = me.store;
                                store.pageSize = newPageSize;
                                store.loadPage(1);
                            }
                        }
                    }
                }, '条'

            ]
        });

        //增加备品备件实例查看按钮
        var columns=gridPanel.columns;
        if(Ext.isArray(columns)){
            columns.push(
                {
                    xtype:'actioncolumn',
                    text:'查看',
                    align:'center',
                    width:200,
                    items:[{
                        iconCls:'icon-detail',
                        tooltip:'表格实例查看',
                        handler:function(grid,rowIndex){
                            var data=grid.getStore().getAt(rowIndex);
                            var checkTempId=data.raw.id;
                            me.preview(checkTempId,true, me.productId);
                        }
                    }]
                })
        }
        //使用一个新的store/columns配置项进行重新配置生成grid
        gridPanel.reconfigure(gridPanel.getStore(),columns);
        Ext.apply(me, {
            layout: 'border',
            items: [gridPanel],
            modelGrid: gridPanel
        });
        me.callParent(arguments);
    },
    preview: function (checkTempId,withData,productId) {
        //表单预览
        TestProcessUtil.CommonHelper.checkListPreview(checkTempId, true, withData,productId);
    }
});
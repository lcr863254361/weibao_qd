Ext.define("OrientTdm.SparePartsMgr.DeviceInstLifeCycle", {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.deviceInstLifeCycle',
    config:{
        pageSize: 25
    },
    initComponent: function () {
        var me = this;
        //modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        me.store = Ext.create('Ext.data.Store', {
            //fields: ["id", "deviceName", "startTime", "endTime","deviceState" ,"contentDesc"],
            fields: ["id","startTime", "endTime","deviceState","deviceDesc"],

            remoteSort: false,
            //设置为 true 则将所有的过滤操作推迟到服务器
            remoteFilter: true,
            proxy: {
                type: 'ajax',
                url: serviceName + "/spareParts/getDeviceInstLifeCycle.rdm",
                reader: {
                    type: 'json',
                    root: 'results',
                    totalProperty: 'totalProperty'
                },
                extraParams: {
                    deviceInstId: me.deviceInstId
                }
            },
            sorters: [{
                direction: 'desc'
            }],
            autoLoad: true
        });

        var gridPanel = Ext.create('Ext.grid.Panel', {
            store: me.store,
            region: 'center',
            selModel: {
                selType: 'checkboxmodel'
            },
            columns: [
                //{text: '设备名称', dataIndex: 'deviceName', flex: 1, align: 'left|center'},
                {text: '开始时间', dataIndex: 'startTime', flex: 1, align: 'left|center'},
                {text: '结束时间', dataIndex: 'endTime', flex: 1, align: 'left|center'},
                {text: '设备状态', dataIndex: 'deviceState', flex: 1, align: 'left|center'},
                {text: '说明', dataIndex: 'deviceDesc', flex: 1, align: 'left|center'}
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
            //}]
        });
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
        //store.loadPage(1);
        Ext.apply(me, {
            layout: 'border',
            items: [gridPanel],
            modelGrid: gridPanel
        });
        me.callParent(arguments);
    },
});
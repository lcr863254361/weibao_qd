/**
 * Created by User on 2019/2/18.
 */
Ext.define("OrientTdm.ProductStructureMgr.ReplaceDevice.ReplaceDeviceInstGrid", {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.replaceDeviceInstGrid',
    id: 'replaceDeviceGridOwner2',
    config: {
    //    schemaId: TDM_SERVER_CONFIG.ORGANIZATION_SCHEMA_ID,
    //    modelName: TDM_SERVER_CONFIG.DUTY_LOG_MGR,
    //    templateName: TDM_SERVER_CONFIG.TPL_DUTY_LOG,
    //    dutyId: null,
    //    modelId: null
        pageSize: 25,
    },

    initComponent: function () {
        var me = this;
        //modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        me.store = Ext.create('Ext.data.Store', {
            fields: ["id", "deviceName","deviceNumber", "number", "state", "liezhuangTime", "position"],
            remoteSort: false,
            //设置为 true 则将所有的过滤操作推迟到服务器
            remoteFilter: true,
            proxy: {
                type: 'ajax',
                url: serviceName + "/CurrentTaskMgr/getDeviceInstData.rdm",
                reader: {
                    type: 'json',
                    root: 'results',
                    totalProperty: 'totalProperty'
                },
                extraParams: {
                    nodeContent: me.nodeContent,
                    productId:me.productId
                }
            },
            sorters: [{
                direction: 'desc'
            }],
            autoLoad: true
        });

        var grid = Ext.create('Ext.grid.Panel', {
            renderTo: Ext.getBody(),
            store: me.store,
            selModel: {
                selType: 'checkboxmodel',
                mode:'MULTI'
            },
            columns: [
                {text: '装备名称', dataIndex: 'deviceName', flex: 1, align: 'left|center'},
                {text: '装备编码', dataIndex: 'deviceNumber', flex: 1, align: 'left|center'},
                {text: '编号/序列号', dataIndex: 'number', flex: 1, align: 'left|center'},
                {text: '状态', dataIndex: 'state', flex: 1, align: 'left|center'},
                {text: '列装时间', dataIndex: 'liezhuangTime', flex: 1, align: 'left|center'},
                {text: '存放位置', dataIndex: 'position', flex: 1, align: 'left|center'}
            ],
            tbar: [
                //添加搜素条件
                //{
                //    xtype: 'label',
                //    text: '请输入装备名称: '
                //}, {
                //    xtype: 'textfield',
                //    id: 'keyWord'
                //},
                //{
                //    text: '搜索',
                //    handler: function () {
                //        var keyWord = Ext.getCmp("keyWord").getValue();
                //        store.proxy.extraParams.queryName = keyWord;
                //        store.proxy.extraParams.nodeContent = "";
                //        store.load();
                //    }
                //},
                {
                    xtype: 'textfield',
                    name: 'keyWord',
                    emptyText: '输入搜索词',
                    listeners: {
                        change: function (field, newValue) {
                            //store.getProxy().setExtraParam('queryName', newValue);
                            me.store.proxy.extraParams.queryName = newValue;
                            me.store.proxy.extraParams.nodeContent = "";
                            me.store.loadPage(1);
                        }
                    }
                }
            ],
            //        dock:'bottom',
            //        xtype:'pagingtoolbar',
            //        store:store,
            //        displayInfo:true,
            //        displayMsg: '显示 {0} - {1} 条，共计 {2} 条',
            //        emptyMsg:"没有数据"
            //    }
            //]
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

            viewConfig: {
                getRowClass: function (record, rowIndex, rowParams, store) {
                    if (record.data['state'] == '故障') {
                        return 'x-grid-record-red';
                    }
                }
            }
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
            layout: 'fit',
            items: [grid],
            modelGrid: grid
        });
        me.callParent(arguments);
    }
});
Ext.define('OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.FlowPost.AttendTimesStatisticsMgr', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    alternateClassName: 'attendTimesStatisticsMgr',
    config: {
        hangciId: null,
        flowPostData: null,
        pageSize: 50
    },

    initComponent: function () {
        var me = this;
        me.pageSize = 50;
        // me.store = Ext.create('Ext.data.Store', {
        //     fields: me.flowPostData.fields,
        //     remoteSort: false,
        //     //设置为 true 则将所有的过滤操作推迟到服务器
        //     remoteFilter: true,
        //     proxy: {
        //         type: 'ajax',
        //         url: serviceName + "/taskPrepareController/getFlowPostAttendTimes.rdm",
        //         reader: {
        //             type: 'json',
        //             root: 'results',
        //             totalProperty: 'totalProperty'
        //         },
        //         extraParams: {
        //             hangduanId: me.hangduanId
        //         }
        //     },
        //     sorters: [{
        //         direction: 'desc'
        //     }],
        //     autoLoad: true
        // });
        // me.bbar = Ext.create('Ext.PagingToolbar', {
        //     store: me.store,
        //     displayInfo: true,
        //     displayMsg: '显示 {0} - {1} 条，共 {2} 条',
        //     emptyMsg: "没有数据",
        //     items: [
        //         {
        //             xtype: 'tbseparator'
        //         },
        //         {
        //             xtype: 'numberfield',
        //             labelWidth: 40,
        //             width: 100,
        //             fieldLabel: '每页',
        //             enableKeyEvents: true,
        //             value: me.pageSize,
        //             listeners: {
        //                 keydown: function (field, e) {
        //                     if (e.getKey() == Ext.EventObject.ENTER && !Ext.isEmpty(field.getValue())) {
        //                         var newPageSize = field.getValue();
        //                         var store = me.store;
        //                         store.pageSize = newPageSize;
        //                         store.loadPage(1);
        //                     }
        //                 }
        //             }
        //         }, '条'
        //
        //     ]
        // });
        // Ext.apply(this, {
        //     // renderTo: document.body,
        //     height: 0.87 * globalHeight,
        //     width: 0.87 * globalWidth,
        //     autoHeight: true,
        //     autoWidth: true,
        //     layout: 'border',
        //     columnLines: true,
        //     store: me.store,
        //     columns: me.flowPostData.columns,
        //     selModel: {
        //         mode: 'MULTI'
        //     },
        //     // bodyStyle:"height:100%;width:100%",
        //     selType: "checkboxmodel"
        // });
        me.callParent(arguments);
    },

    createColumns: function () {
        var me = this;
        var retVal = me.flowPostData.columns;
        return retVal;
    },
    createStore: function () {
        var me = this;
        var retVal = Ext.create('Ext.data.Store', {
            fields: me.flowPostData.fields,
            remoteSort: false,
            //设置为 true 则将所有的过滤操作推迟到服务器
            remoteFilter: true,
            proxy: {
                type: 'ajax',
                url: serviceName + "/taskPrepareController/getFlowPostAttendTimes.rdm",
                reader: {
                    type: 'json',
                    root: 'results',
                    totalProperty: 'totalProperty'
                },
                extraParams: {
                    hangduanId: me.hangduanId
                }
            },
            sorters: [{
                direction: 'desc'
            }],
            autoLoad: true
        });
        this.store = retVal;
        return retVal;
    }
});
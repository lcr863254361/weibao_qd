Ext.define('OrientTdm.CollabDev.Processing.CompositeQuery.Result.CompositeQueryResultGrid', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    alias: 'widget.compositeQueryResultGrid',
    requires: [
        'OrientTdm.CollabDev.Processing.CompositeQuery.Model.CompositeQueryResultModel'
    ],
    selModel: {
        selType: 'rowmodel',
        mode: 'SIGNLE'
    } //设置勾选框这一列不显示
    ,
    initComponent: function () {
        var _this = this;

        _this.callParent(arguments);
    },
    createStore: function () {
        return Ext.create('Ext.data.Store', {
            autoLoad: true,
            model: 'OrientTdm.CollabDev.Processing.CompositeQuery.Model.CompositeQueryResultModel',
            proxy: {
                type: 'ajax',
                api: {
                    'read': serviceName + '/compositeQuery/getData.rdm'
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'message'
                },
                extraParams: {}
            }
        });
    },
    createColumns: function () {
        return [
            {
                header: '名称',
                flex: 1,
                sortable: true,
                dataIndex: 'name'
            }, {
                header: '最后修改人',
                flex: 1,
                sortable: true,
                dataIndex: 'lastUpdateUser'
            }, {
                header: '最后修改时间',
                flex: 1,
                sortable: true,
                dataIndex: 'lastUdpateTime'
            }, {
                header: '详细',
                xtype: 'actioncolumn',
                width: 80,
                items: [
                    {
                        iconCls: 'icon-collabDev-detail',
                        handler: function (grid, rowIndex, colIndex) {

                        }
                    }
                ]
            }
        ];
    },
    filterGrid: function (filter) {
        var _this = this;
        for (var proName in filter) {
            var proValue = filter[proName];
            _this.getStore().getProxy().setExtraParam(proName, proValue);
        }
        _this.getStore().loadPage(1);
    }
});
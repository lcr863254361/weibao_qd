Ext.define('OrientTdm.DivingStatisticsMgr.StatisticsManagerGrid', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    alias: 'widget.statisticsManagerGrid',
    requires: [
        'OrientTdm.DivingStatisticsMgr.StatisticsResultModel'
    ],
    config: {
        pageSize: globalPageSize || 25
    },
    initComponent: function () {
        var me = this;
        me.callParent(arguments);
        me.initEvents();
    },
    initEvents: function () {
        var me = this;
        me.callParent(arguments);
    },
    createStore: function () {
        var retVal = Ext.create('Ext.data.Store', {
            autoLoad: true,
            model: 'OrientTdm.DivingStatisticsMgr.StatisticsResultModel',
            proxy: {
                type: 'ajax',
                api: {
                    'read': serviceName + '/divingStatisticsMgr/queryDivingStatisticsData.rdm'
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'message'
                }
            }
        });
        this.store = retVal;
        return retVal;
    },
    createColumns: function () {
        var me = this;
        var qtipRenderer = function (value, meta, record) {
            value = value || '';
            meta.tdAttr = 'data-qtip="' + value + '"';
            return value;
        };

        return [
            {
                header: '航次',
                flex: 1,
                sortable: true,
                dataIndex: 'hangciName',
                renderer: qtipRenderer
            }, {
                header: '潜次',
                flex: 1,
                sortable: true,
                dataIndex: 'taskName',
                renderer: qtipRenderer
            }, {
                header: '下潜日期',
                flex: 1,
                sortable: true,
                dataIndex: 'divingTime',
                renderer: qtipRenderer
            },{
                header: '下潜类型',
                flex: 1,
                sortable: true,
                dataIndex: 'homeWorkContent',
                renderer: qtipRenderer
            },
            {
                header: '海区',
                flex: 1,
                sortable: true,
                dataIndex: 'seaArea',
                renderer: qtipRenderer
            },{
                header: '经度',
                flex: 1,
                sortable: true,
                dataIndex: 'longitude',
                renderer: qtipRenderer
            },
            {
                header: '纬度',
                flex: 1,
                sortable: true,
                dataIndex: 'latitude',
                renderer: qtipRenderer
            },
            {
                header: '最大深度(米)',
                flex: 1,
                sortable: true,
                dataIndex: 'depth',
                renderer: qtipRenderer
            },
            {
                header: '水中时长',
                flex: 1,
                sortable: true,
                dataIndex: 'waterHours',
                renderer: qtipRenderer
            }, {
                header: '作业时长',
                flex: 1,
                sortable: true,
                dataIndex: 'homeWorkHours',
                renderer: qtipRenderer
            },
            // {
            //     header: '左舷/主驾/右舷',
            //     flex: 1,
            //     sortable: true,
            //     dataIndex: 'zmrPersons',
            //     renderer: qtipRenderer
            // },
            {
                header: '左舷',
                flex: 1,
                sortable: true,
                dataIndex: 'zuoxianPerson',
                renderer: qtipRenderer
            },{
                header: '主驾',
                flex: 1,
                sortable: true,
                dataIndex: 'mainDriverPerson',
                renderer: qtipRenderer
            },{
                header: '右舷',
                flex: 1,
                sortable: true,
                dataIndex: 'youxianPerson',
                renderer: qtipRenderer
            },{
                header: '左舷单位',
                flex: 1,
                sortable: true,
                dataIndex: 'zxCompany',
                renderer: qtipRenderer
            }, {
                header: '右舷单位',
                flex: 1,
                sortable: true,
                dataIndex: 'company',
                renderer: qtipRenderer
            },{
                header: '采样情况',
                flex: 1,
                sortable: true,
                dataIndex: 'sampleSituation',
                renderer: qtipRenderer
            }, {
                header: '人员体重和',
                flex: 1,
                sortable: true,
                dataIndex: 'personTotalWeight',
                renderer: qtipRenderer
            }
        ];
    },
    createFooBar: function () {
        var me = this;
        return {
            xtype: 'pagingtoolbar',
            store: me.store,   // same store GridPanel is using
            dock: 'bottom',
            displayInfo: true,
            items: [
                {
                    xtype: 'tbseparator'
                },
                {
                    xtype: 'numberfield',
                    labelWidth: 30,
                    width: 100,
                    fieldLabel: '每页',
                    enableKeyEvents: true,
                    value: me.pageSize,
                    listeners: {
                        keydown: function (field, e) {
                            if (e.getKey() == Ext.EventObject.ENTER && !Ext.isEmpty(field.getValue())) {
                                var newPageSize = field.getValue();
                                var store = me.getStore();
                                store.pageSize = newPageSize;
                                store.loadPage(1);
                            }
                        }
                    }
                }, '条'

            ]
        };
    },
    getSelectedData: function () {
        var selections = this.getSelectionModel().getSelection();
        // if (selections.length === 0) {
        //     OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.atleastSelectOne);
        // }else{
        var toExportIds = OrientExtUtil.GridHelper.getSelectRecordIds(this);
        var exportAll = false;
        if (selections.length === 0) {
            Ext.MessageBox.confirm('提示', '是否导出所有统计信息？', function (btn) {
                if (btn == 'yes') {
                    exportAll = true;
                    window.location.href = serviceName + '/divingStatisticsMgr/exportDivingData.rdm?exportAll=' + exportAll;
                }
            });
        } else {
            window.location.href = serviceName + '/divingStatisticsMgr/exportDivingData.rdm?exportAll=' + exportAll + '&toExportIds=' + toExportIds;
        }
    }
    // }
});

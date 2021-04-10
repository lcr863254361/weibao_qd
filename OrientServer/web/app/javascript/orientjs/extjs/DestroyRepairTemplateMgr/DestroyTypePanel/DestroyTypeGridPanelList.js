
Ext.define('OrientTdm.DestroyRepairTemplateMgr.DestroyTypePanel.DestroyTypeGridPanelList', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    alias: 'widget.destroyTypeGridPanelList',
    requires: [
        'OrientTdm.DestroyRepairTemplateMgr.model.DestroyTypeGridModel'
    ],
    config: {
        dirId: ''
    },
    beforeInitComponent: Ext.emptyFn,
    afterInitComponent: Ext.emptyFn,
    usePage: false,
    initComponent: function () {
        var me = this;
        me.callParent(arguments);
    },
    //视图初始化
    createToolBarItems: function () {
        return null;
    },
    createColumns: function () {
        return [
            {
                header: '拆解模板名称',
                flex: 1,
                // width: 500,
                sortable: true,
                dataIndex: 'name',
                filter: {
                    type: 'string'
                }
            }
            // {
            //     header: '项目负责人',
            //     width: 100,
            //     sortable: true,
            //     dataIndex: 'principal',
            //     filter: {
            //         type: 'string'
            //     }
            // }, {
            //     header: '计划开始时间',
            //     flex: 1,
            //     sortable: true,
            //     dataIndex: 'plannedStartDate',
            //     filter: {
            //         type: 'string'
            //     }
            // }, {
            //     header: '计划结束时间',
            //     flex: 1,
            //     sortable: true,
            //     dataIndex: 'plannedEndDate',
            //     filter: {
            //         type: 'string'
            //     }
            // }
        ];
    },
    createStore: function () {
        var me = this;
        var retVal = Ext.create('Ext.data.Store', {
            model: 'OrientTdm.DestroyRepairTemplateMgr.model.DestroyTypeGridModel',
            autoLoad: true,
            proxy: {
                type: 'ajax',
                api: {
                    'read': serviceName + '/destroyRepairMgr/getDestroyTypeList.rdm'
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'msg'
                },
                extraParams: {
                    dirId: me.dirId
                }
            }
        });
        this.store = retVal;
        return retVal;
    }
});
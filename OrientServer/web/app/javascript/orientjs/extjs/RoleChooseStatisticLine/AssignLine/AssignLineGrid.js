Ext.define("OrientTdm.RoleChooseStatisticLine.AssignLine.AssignLineGrid", {
    extend: 'OrientTdm.SysMgr.RoleMgr.Common.AssignGrid',
    alias: 'widget.assignSchemaGrid',
    //查询url
    queryUrl: serviceName + '/divingStatisticsMgr/getStatisticsLineList.rdm',
    //保存url
    saveUrl: serviceName + '/divingStatisticsMgr/saveAssignLine.rdm',
    initComponent: function () {
        var me = this;
        Ext.apply(me, {});
        this.callParent(arguments);
    },
    createGridColumns: function () {
        return [
            {
                header: '名称',
                flex: 1,
                sortable: true,
                dataIndex: 'name'
            }
        ];
    },
    // createGridToolBar: function () {
    //     return [];
    // },
    createGridToolBar: function () {
        var items = [{
            xtype: 'triggerfield',
            triggerCls: 'x-form-clear-trigger',
            onTriggerClick: function () {
                this.setValue('');
                this.up('grid').getStore().clearFilter();
            },
            name: 'filterField',
            fieldLabel: '关键词',
            emptyText: '输入搜索词',
            labelWidth: 60,
            listeners: {
                change: function (field, newValue) {
                    if (Ext.isEmpty(newValue)) {
                        this.up('grid').getStore().clearFilter();
                    } else {
                        this.up('grid').getStore().filterBy(function (record) {
                            if (record.get('name').indexOf(newValue) != -1 || record.get('name').indexOf(newValue) != -1) {
                                return true;
                            }
                            return false;
                        });
                    }
                }
            }
        }];
        var retVal = Ext.create('Ext.toolbar.Toolbar', {
            items: items
        });
        return retVal;
    }
});
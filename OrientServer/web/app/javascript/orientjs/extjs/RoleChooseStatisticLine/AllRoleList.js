Ext.define('OrientTdm.RoleChooseStatisticLine.AllRoleList', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    alias: 'widget.RoleList',
    requires: [
        'OrientTdm.SysMgr.RoleMgr.Model.RoleListExtModel',
        'OrientTdm.SysMgr.RoleMgr.RoleSearchForm'
    ],
    beforeInitComponent: Ext.emptyFn,
    afterInitComponent: Ext.emptyFn,
    itemdblclickListener: function (grid, record, item, index, e, eOpts) {
        var southPanel = this.up('RoleMain').southPanel;
        southPanel.fireEvent('showRoleDetail', record.data.id);
    },
    //视图初始化
    createToolBarItems: function () {

        var me = this;
        var retVal = [{
            iconCls: 'icon-query',
            text: '查询',
            disabled: false,
            itemId: 'search',
            scope: this,
            handler: this.onSearchClick
        }, {
            iconCls: 'icon-queryAll',
            text: '显示全部',
            disabled: false,
            itemId: 'searchAll',
            scope: this,
            handler: this.onSearchAllClick
        }, '->', {
            xtype: 'tbtext',
            text: '<span style="color: red">*双击行即可分配列权限</span>'
        }];
        return retVal;
    },
    createColumns: function () {
        return [
            {
                header: '角色名称',
                flex: 2,
                sortable: true,
                dataIndex: 'name',
                filter: {
                    type: 'string'
                }
            }, {
                header: '备注',
                flex: 2,
                sortable: true,
                dataIndex: 'memo',
                filter: {
                    type: 'string'
                }
            }
        ];
    },
    createStore: function () {
        var retVal = Ext.create('Ext.data.Store', {
            model: 'OrientTdm.SysMgr.RoleMgr.Model.RoleListExtModel',
            autoLoad: true,
            proxy: {
                type: 'ajax',
                api: {
                    'read': serviceName + '/role/list.rdm'
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    root: 'results',
                    messageProperty: 'msg'
                },
                writer: {
                    type: 'json',
                    writeAllFields: false,
                    root: 'data'
                },
                listeners: {
                    exception: function (proxy, response, operation) {
                        Ext.MessageBox.show({
                            title: '读写数据异常',
                            msg: operation.getError(),
                            icon: Ext.MessageBox.ERROR,
                            buttons: Ext.Msg.OK
                        });
                    }
                }
            },
            listeners: {
                write: function (proxy, operation) {

                }
            }
        });
        this.store = retVal;
        return retVal;
    },
    initComponent: function () {
        this.on('itemdblclick', this.itemdblclickListener, this);
        this.callParent(arguments);
    },
    onSearchClick: function () {
        var me = this;
        //弹出新增面板窗口
        var createWin = Ext.create('Ext.Window', {
            title: '查询角色',
            plain: true,
            height: 200,
            width: 0.3 * globalWidth,
            layout: 'fit',
            maximizable: true,
            modal: true,
            items: [Ext.create(OrientTdm.SysMgr.RoleMgr.RoleSearchForm, {
                bindModelName: 'CWM_SYS_ROLE',
                successCallback: function (data) {
                    me.fireEvent('loadData', data);
                    createWin.close();
                }
            })]
        });
        createWin.show();
    },
    onSearchAllClick: function () {
        var me = this;
        me.fireEvent('refreshGrid');
        if(this.ownerCt.southPanel){
            this.ownerCt.southPanel.collapse('bottom');
        }
        if(this.ownerCt.eastPanel){
            this.ownerCt.eastPanel.collapse('right');
        }
    }
});
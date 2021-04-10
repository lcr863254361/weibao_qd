/**
 * Created by enjoy on 2016/5/26 0026.
 * 左右分配Grid基类
 */
Ext.define("OrientTdm.SysMgr.RoleMgr.Common.AssignGrid", {
    extend: 'Ext.grid.Panel',
    alias: 'widget.assignGrid',
    frame: true,
    loadMask: true,
    requires: [
        "Ext.ux.grid.FiltersFeature"
    ],
    features: [{
        ftype: 'filters',
        encode: false,
        local: false
    }],
    config: {
        roleId: "",
        //已分配
        assigned: true,
        //查询url
        queryUrl: '',
        //保存URL
        saveUrl: '',
        idProperty: 'id',
        //前后处理器
        beforeInitComponent: Ext.emptyFn,
        afterInitComponent: Ext.emptyFn

    },
    initComponent: function () {
        var me = this;
        //创建columns
        var columns = me.createGridColumns();
        //创建store
        var store = me.createGridStore(columns);
        //绑定
        me.store = store;
        //创建菜单栏
        var toolBar = me.createGridToolBar();
        //创建fooBar
        var fooBar = me.createGridFoolBar();
        Ext.apply(me, {
            layout: 'fit',
            flex: 1,
            multiSelect: true,
            selType: "checkboxmodel",
            stripeRow: true,
            margins: '2',
            dockedItems: [toolBar, fooBar],
            columns: columns,
            store: store
        });
        this.callParent(arguments);
    },
    initEvents: function () {
        var me = this;
        me.callParent();
    },
    createGridColumns: function () {
        throw("子类必须实现该方法");
    },
    createGridToolBar: function () {
        throw("子类必须实现该方法");
    },
    createGridFoolBar: function () {
        //默认展现分页工具栏 可在后处理函数中动态扩展
        var me = this;
        return {
            xtype: 'pagingtoolbar',
            store: me.store,   // same store GridPanel is using
            dock: 'bottom',
            displayInfo: true
        };
    },
    createGridStore: function (columns) {
        var me = this;
        if (Ext.isEmpty(me.queryUrl)) {
            throw("未传入查询url");
        }
        var fields = [me.idProperty];
        Ext.each(columns, function (column) {
            fields.push(column.dataIndex);
        });
        return Ext.create("Ext.data.Store", {
            fields: fields,
            autoLoad: true,
            proxy: {
                type: 'ajax',
                api: {
                    "read": me.queryUrl
                },
                extraParams: {
                    roleId: me.roleId,
                    assigned: me.assigned
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'msg'
                }
            }
        });
    }
});

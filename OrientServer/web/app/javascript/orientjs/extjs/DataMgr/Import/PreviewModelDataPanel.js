/**
 * Created by Administrator on 2016/7/8 0008.
 */
Ext.define('OrientTdm.DataMgr.Import.PreviewModelDataPanel', {
    alias: 'widget.previewModelDataPanel',
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    autoScroll: true,
    requires: [],
    initComponent: function () {
        var me = this;
        me.callParent(arguments);
        me.addEvents('preiveiwData');
    },
    initEvents: function () {
        var me = this;
        me.callParent();
    },
    _loadFileData: function (result) {
        var me = this;
        //导入设置
        var importConfig = result.config;
        //创建columns
        var columns = result.columns;
        //初始化数据
        var gridData = result.data;
        //不展现唯一标识
        //columns = Ext.Array.erase(columns, 0, 1);
        //加载映射面板
        var eastPanel = me.ownerCt.eastPanel;
        if (eastPanel) {
            var copyColumns = Ext.decode(Ext.encode(columns));
            eastPanel.fireEvent('loadMapping', copyColumns);
            eastPanel.importConfig = importConfig;
        }
        me.configData = gridData;
        me.configColumns = columns;
        //创建store
        var store = Ext.create('Ext.data.Store', {
            fields: Ext.Array.pluck(columns, 'dataIndex'),
            data: gridData
        });
        //创建表格
        me._createGrid(store, columns);
    },
    _createGrid: function (store, columns) {
        var me = this;
        if (me.down('grid')) {
            me.down('grid').reconfigure(store, columns);
        } else {
            var cellEditing = Ext.create("Ext.grid.plugin.CellEditing", {
                clicksToEdit: 2
            });
            var toolBar = Ext.create("Ext.toolbar.Toolbar", {
                /*items: [
                    {
                        iconCls: 'icon-create',
                        text: '新增',
                        scope: me,
                        handler: me._addExtraData
                    },
                    {
                        iconCls: 'icon-delete',
                        text: '删除',
                        scope: me,
                        handler: me._removeData
                    },
                    {
                        iconCls: 'icon-refresh',
                        text: '重置',
                        scope: me,
                        handler: me._resetData
                    }
                ]*/
            });
            me.add(Ext.create('Ext.grid.Panel', {
                frame: true,
                loadMask: true,
                columns: columns,
                store: store,
                selModel: {
                    mode: 'MULTI'
                },
                selType: "checkboxmodel",
                plugins: [cellEditing],
                dockedItems: [toolBar],
                //绑定其他属性
                cellEditing: cellEditing
            }));
        }
    },
    _addExtraData: function () {
        var me = this;
        var grid = me.down("grid");
        var store = grid.getStore();
        var rec = {};
        Ext.each(me.configColumns, function (column) {
            rec[column.dataIndex] = "";
        });
        store.insert(0, rec);
        grid.cellEditing.startEditByPosition({
            row: 0,
            column: 0
        });
    },
    _removeData: function () {
        var me = this;
        var grid = me.down("grid");
        var store = grid.getStore();
        store.remove(OrientExtUtil.GridHelper.getSelectedRecord(grid));
    },
    _resetData: function () {
        var me = this;
        var grid = me.down("grid");
        var store = grid.getStore();
        store.removeAll();
        store.add(me.configData);
    }
});
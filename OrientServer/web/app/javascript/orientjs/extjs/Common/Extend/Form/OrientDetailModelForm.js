/**
 * Created by enjoy on 2016/4/19 0019.
 */
Ext.define('OrientTdm.Common.Extend.Form.OrientDetailModelForm', {
    extend: 'OrientTdm.Common.Extend.Form.OrientForm',
    alias: 'widget.orientDetailModelForm',
    alternateClassName: 'OrientExtend.OrientDetailModelForm',
    config: {
        beforeInitForm: Ext.emptyFn,
        afterInitForm: Ext.emptyFn,
        rowNum: 2,//默认两列
        modelDesc: null,//模型描述,
        canCollapseForm: true,
        createDefaultGroup: true
    },
    initComponent: function () {
        var me = this;
        if (Ext.isEmpty(me.modelDesc)) {
            throw("未传入模型定义");
        }
        //初始化前处理
        me.beforeInitForm.call(me);
        //获取新增描述
        var columnDescs = me.modelDesc.columns;
        var detailColumnIds = me.modelDesc.detailColumnDesc;
        var formColumnDescs = [];
        Ext.each(columnDescs, function (column) {
            if (Ext.Array.contains(detailColumnIds, column.id)) {
                formColumnDescs[Ext.Array.indexOf(detailColumnIds, column.id)] = Ext.clone(column);
            }
        });
        me._convertToDetailColumn(formColumnDescs);
        //构建 增加默认ID属性
        var items = [{
            xtype: 'hiddenfield',
            name: 'ID'
        }];
        var lastFieldContainer = null;
        var loopIndex = 0;
        var gridColumns = [];
        Ext.each(formColumnDescs, function (column, index) {
            column.editAble = false;
            if (column.controlType < 17) {
                var columnItem = {
                    xtype: column.className,
                    margin: '0 5 0 5',
                    columnDesc: column
                };
                if (loopIndex % me.rowNum == 0) {
                    lastFieldContainer = {
                        xtype: 'fieldcontainer',
                        layout: 'hbox',
                        combineErrors: true,
                        defaults: {
                            flex: 1,
                            labelAlign: 'right'
                        },
                        items: []
                    };
                    items.push(lastFieldContainer);
                }
                lastFieldContainer.items.push(columnItem);
                loopIndex++;
            } else {
                gridColumns.push(column);//检查字段
            }
        });
        var mainItems = [];
        if (me.createDefaultGroup == true) {
            var defaultFiledSet = Ext.create('Ext.form.FieldSet', {
                collapsible: '默认分组',
                title: me.formTitle,
                items: items
            });
            mainItems.push(defaultFiledSet);
        } else
            mainItems = items;

        var gridItems = OrientExtUtil.FormHelper.createGridByColumns(gridColumns, true);
        var finalItems = Ext.Array.merge(mainItems, gridItems);
        //表格单独展现
        Ext.apply(me, {
            items: finalItems
        });
        me.callParent(arguments);
        //初始化后处理
        me.afterInitForm.call(me);
    },
    /**
     * 如果普通字段存在选择器，则从前端转化为选择器字段
     * @param columnDescs
     * @private
     */
    _convertToDetailColumn: function (columnDescs) {
        Ext.each(columnDescs, function (column) {
            if (column.className == 'SimpleColumnDesc' && !Ext.isEmpty(column.selector)) {
                column.className = column.className + 'ForSelector';
            }
        });
    }
});
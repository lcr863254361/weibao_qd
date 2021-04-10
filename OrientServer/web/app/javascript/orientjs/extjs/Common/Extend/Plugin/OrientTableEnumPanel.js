/**
 * Created by enjoy on 2016/4/2 0002
 */
Ext.define('OrientTdm.Common.Extend.Plugin.OrientTableEnumPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.tableEnumPanel',
    layout: 'border',
    config: {
        modelId: '',
        isMulti: false,
        selectedValue: []
    },
    initComponent: function () {
        var me = this;
        var selectedCustomerFilter = me.createCustomerFilter(CustomerFilter.prototype.SqlOperation.In);
        var unSelectedCustomerFilter = me.createCustomerFilter(CustomerFilter.prototype.SqlOperation.NotIn);
        var keepDictionart = ['详细', '查询', '查询全部'];
        var selectedModelGrid = Ext.create("OrientTdm.Common.Extend.Grid.OrientModelGrid", {
            title: '<span style="color: red; ">已选数据</span>',
            modelId: me.modelId,
            selectType: 'selected',
            autoScroll: true,
            customerFilter: selectedCustomerFilter,
            afterInitComponent: function () {
                //增加按钮
                var toolBar = this.dockedItems[0];
                //只保留详细、查询、查询全部三个按钮
                var toRemoveItems = [];
                Ext.each(toolBar.items.items, function (btn) {
                    if (!Ext.Array.contains(keepDictionart, btn.text)) {
                        toRemoveItems.push(btn);
                    }
                });
                Ext.each(toRemoveItems, function (toRemoveItem) {
                    toolBar.remove(toRemoveItem);
                });
                toolBar.add('->', {
                    xtype: 'tbtext',
                    style: 'color:red',
                    text: '双击条目即可还原到未选数据',
                    iconCls: 'x-status-error'
                });
                /* toolBar.add({
                 iconCls: 'icon-remove',
                 text: '移除',
                 itemId: 'grid-remove',
                 scope: this,
                 handler: me.removeSelectedData
                 });*/
            },
            listeners: {
                'itemdblclick': function (grid, record) {
                    me.removeSelectedDataToLeft(grid);
                }
            }
        });
        var unSelectedModelGrid = Ext.create("OrientTdm.Common.Extend.Grid.OrientModelGrid", {
            title: '<span style="color: blue; ">未选数据</span>',
            modelId: me.modelId,
            selectType: 'unselected',
            autoScroll: true,
            customerFilter: unSelectedCustomerFilter,
            afterInitComponent: function () {
                //增加按钮
                var toolBar = this.dockedItems[0];
                var toRemoveItems = [];
                Ext.each(toolBar.items.items, function (btn) {
                    if (!Ext.Array.contains(keepDictionart, btn.text)) {
                        toRemoveItems.push(btn);
                    }
                });
                Ext.each(toRemoveItems, function (toRemoveItem) {
                    toolBar.remove(toRemoveItem);
                });

                toolBar.add('->',{
                    xtype: 'tbtext',
                    style: 'color:red',
                    text: '双击条目即可添加到已选数据',
                    iconCls: 'x-status-error'
                });

                /*  toolBar.add({
                 iconCls: 'icon-select',
                 text: '选中',
                 itemId: 'grid-select',
                 scope: this,
                 handler: me.addSelectedData
                 });*/
            },
            listeners: {
                'itemdblclick': function (grid, record) {
                    me.addSelectedDataToRight(grid);
                }
            }
        });

        var leftRespPanel = Ext.create("OrientTdm.Common.Extend.Panel.OrientPanel", {});
        var leftContent = Ext.create("OrientTdm.Common.Extend.Panel.OrientPanel", {
            width: '50%',
            layout: 'card',
            region: 'west',
            items: [unSelectedModelGrid, leftRespPanel]
        });

        var rightRespPanel = Ext.create("OrientTdm.Common.Extend.Panel.OrientPanel", {});
        var rightContent = Ext.create("OrientTdm.Common.Extend.Panel.OrientPanel", {
            layout: 'card',
            width: '50%',
            region: 'center',
            items: [selectedModelGrid, rightRespPanel]
        });

        Ext.apply(me, {
            layout: 'border',
            items: [leftContent, rightContent]
        });
        me.callParent(arguments);
    },
    removeSelectedData: function () {
        var me = this;
        //获取父panel
        var fatherPanel = me.up('tableEnumPanel');
        var selections = this.getSelectionModel().getSelection();
        if (selections.length === 0) {
            OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.atleastSelectOne);
        } else {
            Ext.each(selections, function (item) {
                Ext.Array.remove(fatherPanel.selectedValue, item.getId());
            });
            //空值处理
            fatherPanel.selectedValue = Ext.isEmpty(fatherPanel.selectedValue) ? ["-1"] : fatherPanel.selectedValue;
            //刷新左右表格
            fatherPanel.doRefreshPanel();
        }
    },
    addSelectedData: function () {
        var me = this;
        //获取父panel
        var fatherPanel = me.up('tableEnumPanel');
        //校验是否可以添加
        var canAdd = fatherPanel.canAdd();
        if (canAdd == 1) {
            var selections = this.getSelectionModel().getSelection();
            if (selections.length === 0) {
                OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.atleastSelectOne);
            } else {
                Ext.each(selections, function (item) {
                    Ext.Array.include(fatherPanel.selectedValue, item.getId());
                });
                fatherPanel.doRefreshPanel();
            }
        } else if (canAdd == -1) {
            OrientExtUtil.Common.err(OrientLocal.prompt.error, "请先删除已经选中的数据，再重新添加新的数据");
        } else if (canAdd == -2) {
            OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.onlyCanSelectOne);
        }
    },
    doRefreshPanel: function () {
        var me = this;
        //刷新左右表格
        var selectedGrid = me.down("orientModelGrid[selectType=selected]");
        var unSelectedGrid = me.down("orientModelGrid[selectType=unselected]");
        var selectedCustomerFilter = me.createCustomerFilter("In");
        var unSelectedCustomerFilter = me.createCustomerFilter("NotIn");
        selectedGrid.fireEvent("refreshGridByCustomerFilter", selectedCustomerFilter);
        unSelectedGrid.fireEvent("refreshGridByCustomerFilter", unSelectedCustomerFilter);
    },
    createCustomerFilter: function (operation) {
        var me = this;
        //空值处理
        me.selectedValue = Ext.isEmpty(me.selectedValue) ? ["-1"] : me.selectedValue;
        return [new CustomerFilter("ID", operation, "", me.selectedValue.toString())];
    },
    getSelectedData: function () {
        //获取选中的数据
        var me = this;
        var selectedGrid = me.down("orientModelGrid[selectType=selected]");
        var proxy = selectedGrid.getStore().getProxy();
        var url = proxy.api.read;
        var params = proxy.extraParams;
        var selectedData = [];
        OrientExtUtil.AjaxHelper.doRequest(url, params, false, function (resp) {
            var data = resp.decodedData;
            selectedData = data.results;
        });
        return selectedData;
    },
    canAdd: function () {
        var retVal = 1;
        //校验是否可以添加选中的数据
        var me = this;
        //如果只能单选
        if (me.isMulti == false) {
            var selectedGrid = me.down("orientModelGrid[selectType=selected]");
            var unSelectedGrid = me.down("orientModelGrid[selectType=unselected]");
            //如果已经存在选中的数据
            if (selectedGrid.getStore().getCount() > 0) {
                retVal = -1;
            } else if (unSelectedGrid.getSelectionModel().getSelection().length > 1) {
                retVal = -2;
            }
        }
        return retVal;
    },
    addSelectedDataToRight: function (grid) {
        var me = this;
        //校验是否可以添加
        var canAdd = me.canAdd();
        if (canAdd == 1) {
            var selections = grid.getSelectionModel().getSelection();
            if (selections.length === 0) {
                OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.atleastSelectOne);
            } else {
                Ext.each(selections, function (item) {
                    Ext.Array.include(me.selectedValue, item.getId());
                });
                me.doRefreshPanel();
            }
        } else if (canAdd == -1) {
            OrientExtUtil.Common.err(OrientLocal.prompt.error, "请先删除已经选中的数据，再重新添加新的数据");
        } else if (canAdd == -2) {
            OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.onlyCanSelectOne);
        }
    },
    removeSelectedDataToLeft: function (grid) {
        var me = this;
        var selections = grid.getSelectionModel().getSelection();
        if (selections.length === 0) {
            OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.atleastSelectOne);
        } else {
            Ext.each(selections, function (item) {
                Ext.Array.remove(me.selectedValue, item.getId());
            });
            //空值处理
            me.selectedValue = Ext.isEmpty(me.selectedValue) ? ["-1"] : me.selectedValue;
            //刷新左右表格
            me.doRefreshPanel();
        }
    }
});
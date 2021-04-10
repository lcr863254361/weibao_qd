/**
 * Created by FZH  on 2016/10/24.
 */
Ext.define('OrientTdm.Test.TestGrid', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientModelGrid',
    alias: 'widget.TestGrid',
    config: {

    },
    requires: [
        'OrientTdm.TestResourceMgr.GeneralEquipmentMgr..ChooseDevicePanel'
    ],
    initComponent: function () {
        var me = this;
        this.callParent(arguments);
    },
    afterInitComponent: function () {
        var me = this;
        var toolbar = me.dockedItems[0];

        toolbar.add({
            text: '选择设备',
            handler: me._doAddDeviceClicked,
            iconCls: "icon-create",
            scope: me
        });
    },
    afterRender: function() {
        var me = this;
        this.callParent(arguments);

        var treeNode = me.bindNode;
        var tbomModels = treeNode.raw.tBomModels;
    },
    _doAddDeviceClicked: function (toolbar) {
        var me = this;

        var mainPanel = Ext.create('OrientTdm.TestResourceMgr.GeneralEquipmentMgr.ChooseDevicePanel', {
            showSelected: false,
            selectedValue: '',
            customFilters: [TestResourceUtil.getDeviceStateFilter("可用")],
            multiSelect: true,
            saveAction: function(selectedIds, selectedRecords, selectedInfoMap) {
                var me = this;
                me.up('window').close();
                OrientExtUtil.Common.tip('提示', "已选设备：" + Ext.encode(selectedIds));
                TestResourceUtil.setDeviceState(selectedIds, "占用");
            }
        });

        OrientExtUtil.WindowHelper.createWindow(mainPanel, {
            title: '选择设备',
            layout: "fit",
            width: 0.8 * globalWidth,
            height: 0.8 * globalHeight,
            buttonAlign: 'center'
        });
    }
});
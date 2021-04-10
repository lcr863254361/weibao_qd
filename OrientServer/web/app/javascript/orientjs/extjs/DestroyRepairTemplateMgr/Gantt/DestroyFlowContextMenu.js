//extended context menu, color picker added
Ext.define('OrientTdm.DestroyRepairTemplateMgr.Gantt.DestroyFlowContextMenu', {
    extend: 'Gnt.plugin.TaskContextMenu',
    createMenuItems: function () {
        return [{
            text: '新增流程',
            iconCls: 'icon-add',
            itemId: 'add',
            scope: this,
            handler: this.customAddTaskBelowAction,
        }]
    },
    customAddTaskBelowAction: function () {
        this.addTaskBelow(this.copyTask(this.rec))
    },
    configureMenuItems: function () {
        this.callParent(arguments);

        var rec = this.rec;

        // there can be no record when clicked on the empty space in the schedule
        if (!rec) return;
    }
});

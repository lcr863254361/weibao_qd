
Ext.define("OrientTdm.DestroyRepairTemplateMgr.util.GanttControlledPanelHelper", {
    extend: 'Ext.Base',
    alternateClassName: 'GanttControlledPanelHelper',
    requires:[
      'OrientTdm.Collab.common.TaskHandler.TaskHandlerPanel'
    ],
    statics: {
        getPanelByTitle: function (title, params, customParams) {
            var panel = null;

            var config = this.getConfigParams(title, params, customParams);

              if (title == '甘特图') {
                panel = Ext.create("OrientTdm.DestroyRepairTemplateMgr.Gantt.DestroyGanttPanel", Ext.apply({}, config));
            }
            return panel;

        },
        getConfigParams: function (title, params, customParams) {
            var retV = params;
            if (!Ext.isEmpty(customParams)) {
                if (!Ext.isEmpty(customParams[title])) {
                    retV = Ext.apply(retV, customParams[title]);
                }
            }

            return retV;
        }
    }
});
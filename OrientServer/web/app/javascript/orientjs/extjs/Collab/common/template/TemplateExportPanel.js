/**
 * Created by Seraph on 16/9/23.
 */
Ext.define('OrientTdm.Collab.common.template.TemplateExportPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    config : {
        personal : true,
        baseParams : null,
        successCallback : Ext.emptyFn(),
        mainTab : null
    },
    initComponent: function () {
        var me = this;
        var centerPanel = Ext.create("OrientTdm.Collab.common.template.TemplateCreateFormPanel", {
            region: 'center',
            baseParams : me.baseParams,
            successCallback : me.successCallback,
            bodyStyle:{background:'#ffffff'}
        });
        Ext.apply(this, {
            layout : 'border',
            items : [centerPanel],
            centerPanel : centerPanel
        });
        this.callParent(arguments);
    }
});
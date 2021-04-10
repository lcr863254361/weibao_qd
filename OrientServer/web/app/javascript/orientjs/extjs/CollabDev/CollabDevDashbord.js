Ext.define('OrientTdm.CollabDev.CollabDevDashbord', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.collabDevDashbord',
    requires: [
        'OrientTdm.CollabDev.Common.Navigation.NavigationPanel',
        'OrientTdm.CollabDev.Common.Content.CollabDevCenterPanel',
        'OrientTdm.CollabDev.Tools.CollabDevModelCRUDHelper'
    ],
    initComponent: function () {
        var _this = this;
        var centerPanel = Ext.create('OrientTdm.CollabDev.Common.Content.CollabDevCenterPanel', {
            region: 'center',
            padding: '0 0 0 5'
        });
        //Tbom
        var navigationPanel = Ext.create('OrientTdm.CollabDev.Common.Navigation.NavigationPanel', {
            width: 280,
            minWidth: 280,
            maxWidth: 400,
            region: 'west'
        });
        Ext.apply(_this, {
            layout: 'border',
            items: [centerPanel, navigationPanel]
        });
        _this.callParent(arguments);
    }
});
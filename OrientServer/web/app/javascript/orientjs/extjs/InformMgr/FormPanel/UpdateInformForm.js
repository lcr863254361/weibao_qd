Ext.define('OrientTdm.InformMgr.FormPanel.UpdateInformForm', {
    extend: 'OrientTdm.Common.Extend.Form.OrientForm',
    alias: 'widget.updateInformForm',
    config: {
    },

    initComponent: function () {
        var me = this;

        Ext.apply(this, {
            layout: 'fit',
            //items: [
            //    {   id:'form',
            //        xtype: 'panel',
            //        layout: 'hbox',
            //        bodyStyle: 'border-width:0 0 0 0; background:transparent',
            //        combineErrors: true,
            //        defaults: {
            //            flex: 1,
            //            anchor:'100%'
            //        },
            //        layoutConfig: {
            //            pack: "start",
            //            align: "stretch"
            //        },
                    items: [
                        {
                            xtype: 'textarea',
                            readOnly:false,
                            id:'informContentId',
                            //height:0.6 * globalHeight,
                            //width: 0.6 * globalWidth,
                            value: me.informContent
                        }
                    ]
            //    }
            //]
        });

        this.callParent(arguments);
    }
});
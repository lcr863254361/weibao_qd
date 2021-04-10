/**
 * Created by liuyangchao on 2019/2/22.
 */
Ext.define('OrientTdm.Common.Extend.DataView.OrientInfoPanel', {
    extend: 'Ext.panel.Panel',
    alias : 'widget.orientInfoPanel',
    static :{
        ownClick: function(){
            alert("hahaha!");
        }
    },
    id: 'orient-dataview-info',

    width: 150,
    minWidth: 150,

    tpl: [
        '<div class="details" >',
        '<tpl for=".">',
        '<div class="details-info">',
            '<div class="dataview-block">',
                '<span class="dataview-front-label">{fieldLabel}：</span>',
                '<span class="dataview-front-end" >{value}</span>',
            '</div>',
        '</div>',
        '</tpl>',
        '</div>',
        {
            click:function (instanceId) {
                var instanceId=data.raw.ID;
                Ext.create('Ext.Window', {
                        plain: true,
                        title: '预览',
                        height: globalHeight * 0.9,
                        width: globalWidth * 0.9,
                        layout: 'fit',
                        maximizable: true,
                        modal: true,
                        autoScroll: true,
                        items: [
                            Ext.create('OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel', {
                                instanceId: instanceId
                            })
                        ]
                    }
                ).show();
            }
        }
    ],


    afterRender: function(){
        this.callParent();
        if (!Ext.isWebKit) {
            this.el.on('click', function(){
                alert('The Sencha Touch examples are intended to work on WebKit browsers. They may not display correctly in other browsers.');
            }, this, {delegate: 'a'});
        }
    },

    /**
     * Loads a given image record into the panel. Animates the newly-updated panel in from the left over 250ms.
     */
    loadRecord: function(image) {
        this.body.hide();
        this.tpl.overwrite(this.body, image);
        this.body.slideIn('l', {
            duration: 250
        });
    },

    clear: function(){
        this.body.update('');
    }
});
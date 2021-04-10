/**
 * Created by User on 2019/5/27.
 */
Ext.define('OrientTdm.InformMgr.InformManagerDashboard', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.informManagerDashboard',
    initComponent: function () {
        var me = this;
        me.checkGrp = Ext.create('Ext.form.FieldContainer', {
            region: 'north',
            bodyStyle: 'border-width:0 0 0 0; background:transparent;',
            // float:left
            xtype: 'fieldcontainer',
            labelWidth: 75,
            fieldLabel: '<span style="font-size: 15px;font-family: 黑体">' + '当前状态' + '</span>',
            defaultType: 'radiofield',
            margin: '8 12 0 0',
            layout: {
                type: 'hbox',
                align: 'middle',
                pack: 'start'
            },
            labelAlign: 'right',
            items: [{
                boxLabel: '<span style="font-size: 15px;font-family: 黑体">' + '航渡' + '</span>',
                name: 'state',
                inputValue: '航渡',
                padding: '0 10 0 0',
                id: 'c1',
                listeners: {
                    'focus': function () {
                        me.singleCheck(0);
                    }
                }
            }, {
                boxLabel: '<span style="font-size: 15px;font-family: 黑体">' + '下潜作业' + '</span>',
                name: 'state',
                inputValue: '下潜作业',
                padding: '0 10 0 0',
                id: 'c2',
                listeners: {
                    'focus': function () {
                        me.singleCheck(1);
                    }
                }
            }, {

                boxLabel: '<span style="font-size: 15px;font-family: 黑体">' + '备潜' + '</span>',
                name: 'state',
                inputValue: '备潜',
                padding: '0 10 0 0',
                id: 'c3',
                listeners: {
                    'focus': function () {
                        me.singleCheck(2);
                    }
                }
            }, {
                boxLabel: '<span style="font-size: 15px;font-family: 黑体">' + '常规作业' + '</span>',
                name: 'state',
                inputValue: '常规作业',
                padding: '0 10 0 0',
                id: 'c4',
                listeners: {
                    'focus': function () {
                        me.singleCheck(3);
                    }
                }
            }]
        });

        //var northPanel=Ext.create('Ext.form.Panel', {
        //    region: 'north',
        //    bodyStyle: 'border-width:0 0 0 0; background:transparent',
        //    margin:'8 12 0 0',
        //    layout:{
        //        type:'hbox',
        //        align:'middle',
        //        pack:'start'
        //    }
        //});
        //northPanel.add(
        //    me.checkGrp
        //);

        var gridPanel = Ext.create('OrientTdm.InformMgr.InformManagerGrid', {
            region: 'center',
            padding: '0005',
        });

        Ext.apply(me, {
            layout: 'border',
            items: [me.checkGrp, gridPanel],
            //activeItem: 0
        });
        me.callParent(arguments);
        //初始化界面时单选按钮显示已经打勾的项
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/informMgr/getInformState.rdm', {},
            false, function (response) {
                if (response.decodedData.success) {
                    var stateName = response.decodedData.results;
                    var checkGrp = me.items.items[0];
                    for (var i = 0; i < checkGrp.items.length; i++) {
                        var inputValue = checkGrp.items.items[i].inputValue;
                        if (inputValue == stateName) {
                            var id = me.checkGrp.items.items[i].id;
                            var check = Ext.getCmp(id);
                            check.setValue(true);
                        }
                    }
                }
            });
    },
    singleCheck: function (index) {
        var me = this;
        var checkGrp = me.items.items[0];
        var id = checkGrp.items.items[index].id;
        var check = Ext.getCmp(id);
        check.setValue(true);
        var checkStateName = checkGrp.items.items[index].inputValue;
        if (check.getValue()) {
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/informMgr/saveInformState.rdm', {
                    checkStateName: checkStateName
                },
                false, function (response) {
                });
        }
    }
});
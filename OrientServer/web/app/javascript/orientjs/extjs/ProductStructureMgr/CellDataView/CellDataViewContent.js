/**
 * Cell 卡片式布局主内容
 * Created by liuyangchao on 2019/3/11.
 */
Ext.define('OrientTdm.ProductStructureMgr.CellDataView.CellDataViewContent', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.CellDataViewContent',
    config:{
        productId: '',
        deviceContent: '',
        deviceInstId: '',
        nodeContent: '',
        testStore: ''
    },
    requires: [
        'Ext.data.*',
        'Ext.util.*',
        'Ext.view.View',
        'Ext.ux.DataView.Animated',
        'Ext.ux.data.PagingMemoryProxy',
        'Ext.XTemplate',
        'Ext.panel.Panel',
        'Ext.toolbar.*',
        'Ext.slider.Multi',
        "Ext.ux.DataView.DragSelector"
    ],

    initComponent: function () {
        var me = this;
        me.initStore(me.productId, me.deviceInstId);
        Ext.define('Mobile', {
            extend: 'Ext.data.Model',
            fields: me.columnData
        });
        // var store;
        me.testStore = Ext.create('Ext.data.Store', {
            model: 'Mobile',
            sortInfo: {
                field: 'name',
                direction: 'ASC'
            },
            pageSize: 25,
            autoLoad: true,
            proxy: new Ext.ux.data.PagingMemoryProxy({
                fields: me.columnData,
                data: me.dataviewData,
                reader:{
                    type: 'json'
                },
                enablePaging: true
            })
        });
        var pagingToolbar = new Ext.PagingToolbar({store:me.testStore, displayInfo:true});

        var canterPanel = new Ext.create('OrientTdm.ProductStructureMgr.CellDataView.CellDataViewMain', {
            collapsible: false,
            region: 'center',
            margins: '5 0 0 0',
            columnWidth: 0.7,
            store: me.testStore,
            test: me.testStore,
            productId: me.productId,
            deviceInstId: me.deviceInstId,
            columnData: me.columnData,
            bbar: pagingToolbar,
            id:'cellDataViewReload',
        });

        var winNavigation = new Ext.create('OrientTdm.Common.Extend.DataView.OrientInfoPanel', {
            title: '属性详情',
            region: 'east',
            collapsible: true,
            autoScroll : true,
            anchor: '30%',
            columnWidth: 0.3,
            minWidth: 300,
            bodyPadding: 5
        });
        Ext.apply(me, {
            layout: 'border',
            items: [winNavigation, canterPanel],
            centerPanel: canterPanel,
            rightPanel: winNavigation,
            dockedItems: [{
                xtype: 'toolbar',
                items: [
                    {
                        xtype: 'tbtext',
                        id:'refDeviceId',
                        text: '<span style="color: black" ><b>当前关联设备：</b></span>' + '<span style="color: blue" >' + me.deviceContent + '</span>'
                    }, '', {
                        xtype: 'button',
                        iconCls: 'icon-back',
                        text: '更换设备',
                        handler: function () {
                            //弹出新增面板窗口
                            var createWin = Ext.create('Ext.Window', {
                                title: '备件列表',
                                plain: true,
                                height: 0.5 * globalHeight,
                                width: 0.7 * globalWidth,
                                layout: 'fit',
                                maximizable: true,
                                modal: true,
                                items: [Ext.create('OrientTdm.ProductStructureMgr.ReplaceDevice.ReplaceDeviceInstGrid', {
                                    nodeContent: me.nodeContent,
                                    productId:me.productId,
                                    successCallback: function (resp, callBackArguments) {
                                        if (callBackArguments) {
                                            createWin.close();
                                        }
                                    }
                                })],
                                buttonAlign: 'center',
                                buttons: [
                                    {
                                        text: '保存',
                                        iconCls: 'icon-save',
                                        //scope: me,
                                        handler: function(){
                                            var buttonSave=this;
                                            var replaceGrid = Ext.getCmp("replaceDeviceGridOwner2").items.items[0];
                                            if (OrientExtUtil.GridHelper.getSelectedRecord(replaceGrid).length == 0) {
                                                OrientExtUtil.Common.err(OrientLocal.prompt.error, '未选中任何记录');
                                                return;
                                            }
                                            //else if (OrientExtUtil.GridHelper.getSelectedRecord(replaceGrid).length > 1) {
                                            //    OrientExtUtil.Common.err(OrientLocal.prompt.error, '只能选择一条备品备件');
                                            //    return;
                                            //}
                                            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/ProductStructrue/replaceDeviceInstData.rdm', {
                                                    productId: me.productId,
                                                    newDeviceInstId: OrientExtUtil.GridHelper.getSelectRecordIds(replaceGrid),
                                                },
                                                false, function (response) {
                                                    if (response.decodedData.success) {
                                                        // console.log( Ext.getCmp('cellDataViewReload'));
                                                        //Ext.getCmp('cellDataViewReload').store.reload();
                                                        // console.log( Ext.getCmp('historyCheckRecordReload'));
                                                        Ext.getCmp('historyCheckRecordReload').store.reload();
                                                        console.log(buttonSave);
                                                        //Ext.Msg.alert("提示", response.decodedData.msg);
                                                        console.log(buttonSave.up("window"));
                                                        buttonSave.up("window").items.items[0].items.items[0].store.reload();

                                                        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/ProductStructrue/getCurrentRefDevice.rdm', {
                                                                productId: me.productId
                                                            },
                                                            false, function (response) {
                                                                me.deviceContent = response.decodedData.results.select;
                                                                // console.log(toolbar);
                                                                //更新toolbar中tbtext中的文本
                                                                Ext.getCmp('refDeviceId').setText('<span style="color: black" ><b>当前关联设备：</b></span>' + '<span style="color: blue" >' + me.deviceContent + '</span>');
                                                            });
                                                        //更新卡片布局
                                                        // me.items.items[1].store.reload();
                                                        me.initStore(me.productId, OrientExtUtil.GridHelper.getSelectRecordIds(replaceGrid));

                                                        me.testStore.model = 'Mobile';
                                                        me.testStore.proxy.fields = me.columnData;
                                                        me.testStore.proxy.data = me.dataviewData;
                                                        me.testStore.load();
                                                        me.items.items[1].doLayout();

                                                        buttonSave.up("window").close();

                                                    } else {
                                                        Ext.Msg.alert("提示", response.decodedData.msg);
                                                        //buttonSave.up("window").close();
                                                    }
                                                });

                                        }
                                    },
                                    {
                                        text: '关闭',
                                        iconCls: 'icon-close',
                                        handler: function () {
                                            this.up('window').close();
                                            //Ext.getCmp("currentCheckRecordOwner1").fireEvent("refreshGrid");
                                        }
                                    }
                                ]
                            });
                            createWin.show();
                        }
                    }
                ]
            }]
        });
        me.callParent(arguments);
    },
    //初始化Column+Store的数据
    initStore: function (productId, deviceInstId) {
        var me = this;
        if (!Ext.isEmpty(productId)) {
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/ProductStructrue/queryCellDataView.rdm', {
                    productId: productId,
                    deviceInstId: deviceInstId
                },
                false, function (response) {
                    console.log(response);
                    me.columnData = response.decodedData.results.column;
                    // me.dataviewData = response.decodedData.results.data;
                    me.dataviewData = response.decodedData.results.newData;
                })
        }
    },

});

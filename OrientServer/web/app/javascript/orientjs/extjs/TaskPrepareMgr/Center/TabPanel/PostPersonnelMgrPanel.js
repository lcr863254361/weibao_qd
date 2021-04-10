/**
 * Created by User on 2019/2/18.
 */
Ext.define("OrientTdm.TaskPrepareMgr.Center.TabPanel.PostPersonnelMgrPanel", {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.postPersonnelMgrPanel',
    id: 'postPersonnelMgrPanel2',
    config: {
        pageSize: 25
    },
    initComponent: function () {
        var me = this;
        var attendKeyId = me.attendKeyId;
        //modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        me.store = Ext.create('Ext.data.Store', {
            fields: ["id", "postName", "postType"],
            remoteSort: false,
            //设置为 true 则将所有的过滤操作推迟到服务器
            remoteFilter: true,
            proxy: {
                type: 'ajax',
                url: serviceName + "/taskPrepareController/queryAttendPersonData.rdm?postIds=" + me.postIds,
                reader: {
                    type: 'json',
                    root: 'results',
                    totalProperty: 'totalProperty'
                },
                extraParams: {}
            },
            sorters: [{
                direction: 'desc'
            }],
            autoLoad: true
        });
        me.store.on("load", this.loadChoosePost, this);
        var allSelectedRecords = [];
        me.grid = Ext.create('Ext.grid.Panel', {
            renderTo: Ext.getBody(),
            store: me.store,
            selModel: {
                selType: 'checkboxmodel',
                pruneRemoved: false,
                mode: 'MULTI',
                listeners: {
                    select: function (me, record, index, opts) {
                    //     if (allSelectedRecords.length == 0) {
                    //      allSelectedRecords.push(record);
                    // } else {
                    //      Ext.each(allSelectedRecords, function (selected) {
                    //            if (allSelectedRecords.indexOf(record) == -1) {
                    //                 allSelectedRecords.push(record);
                    //         }
                    //          });
                    //    }
                        if (allSelectedRecords.length == 0) {
                            allSelectedRecords.push(record);
                        } else {
                            var count=0;
                            Ext.each(allSelectedRecords, function (selected) {
                                if (selected.get("id")==record.raw['id']) {
                                    count++;
                                }
                            });
                            if (count==0){
                              allSelectedRecords.push(record);
                            }
                        }
                    },
                    deselect: function (me, record, index, opts) {
                        // allSelectedRecords=Ext.Array.filter(allSelectedRecords,function (item) {
                        //     return item.get("ID")!=record.get("ID");
                        // });
                        if (index != -1) {
                            Ext.getCmp('postPersonnelMgrPanel2').remove(record, allSelectedRecords);
                        }
                    }
                }
            },
            columns: [
                {text: '岗位名称', dataIndex: 'postName', flex: 1, align: 'left|center'},
                {text: '岗位类型', dataIndex: 'postType', flex: 1, align: 'left|center'}
            ],
            tbar: [
                //添加搜素条件
                //{
                //    xtype: 'label',
                //    text: '请输入岗位名称: '
                //},
                {
                    xtype: 'textfield',
                    name: 'keyWord',
                    emptyText: '输入搜索词',
                    listeners: {
                        change: function (field, newValue) {
                            //store.getProxy().setExtraParam('queryName', newValue);
                            me.store.proxy.extraParams.queryName = newValue;
                            me.store.loadPage(1);
                        }
                    }
                },
                //{
                //    text: '搜索',
                //    handler: function () {
                //        var keyWord = Ext.getCmp("keyWord").getValue();
                //        store.proxy.extraParams.queryName = keyWord;
                //        store.load();
                //    }
                //},
                //{
                //    text:'清空',
                //    handler:function(){
                //        var keyWord=Ext.getCmp("keyWord").setValue("");
                //        store.proxy.extraParams.queryName = "";
                //        store.load();
                //    }
                //},
                //{
                //    text: '添加岗位',
                //    handler:function(){
                //        if (OrientExtUtil.GridHelper.getSelectedRecord(me.grid).length == 0) {
                //            OrientExtUtil.Common.err(OrientLocal.prompt.error, '未选中任何记录');
                //            return;
                //        }
                //        var select=OrientExtUtil.GridHelper.getSelectRecordIds(me.grid);
                //        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/addPostData.rdm', {
                //                selectId: OrientExtUtil.GridHelper.getSelectRecordIds(me.grid),
                //                taskId:me.taskId,
                //                attendKeyId:attendKeyId
                //            },
                //            false, function (response) {
                //                if (response.decodedData.success) {
                //                    //me.up('setsMgrStageTabPanel').next("panel[region=south]").child(xtype = 'grid').refreshGrid();
                //                    Ext.Msg.alert("提示", response.decodedData.msg);
                //                }else{
                //                    Ext.Msg.alert("提示", response.decodedData.msg);
                //                }
                //                //console.log(
                //                //    Ext.getCmp('bindCheckTableTempGrid_own_1')
                //                //);
                //                //console.log(Ext.ComponentQuery.query('bindCheckTableTempGrid')[0].modelGrid);
                //                //Ext.ComponentQuery.query('bindCheckTableTempGrid')[0].modelGrid.store.reload(); //刷新绑定面板
                //                Ext.getCmp('selectPostPanel').store.reload();    //刷新绑定面板
                //                me.up('window').close();  //关闭窗口
                //            })
                //    }
                //}
            ],
            //        dock:'bottom',
            //        xtype:'pagingtoolbar',
            //        store:store,
            //        displayInfo:true,
            //        displayMsg: '显示 {0} - {1} 条，共计 {2} 条',
            //        emptyMsg:"没有数据"
            //    }
            //]
            //bbar: [{
            //    xtype: 'pagingtoolbar',
            //    store: store,
            //    displayMsg: '显示 {0} - {1} 条，共计 {2} 条',
            //    emptyMsg: "没有数据",
            //    beforePageText: "当前页",
            //    afterPageText: "共{0}页",
            //    /* emptyMsg:"没有记录",
            //     displayInfo:true,
            //     displayMsg:"本页显示第{0}条到第{1}条的记录，一共{2}条",*/
            //    /*  displayInfo: true,*/
            //    dock: 'bottom'
            //}]
        });
        me.allSelectedRecords = allSelectedRecords;

        me.bbar = Ext.create('Ext.PagingToolbar', {
            store: me.store,
            displayInfo: true,
            displayMsg: '显示 {0} - {1} 条，共 {2} 条',
            emptyMsg: "没有数据",
            items: [
                {
                    xtype: 'tbseparator'
                },
                {
                    xtype: 'numberfield',
                    labelWidth: 40,
                    width: 100,
                    fieldLabel: '每页',
                    enableKeyEvents: true,
                    value: me.pageSize,
                    listeners: {
                        keydown: function (field, e) {
                            if (e.getKey() == Ext.EventObject.ENTER && !Ext.isEmpty(field.getValue())) {
                                var newPageSize = field.getValue();
                                var store = me.store;
                                store.pageSize = newPageSize;
                                store.loadPage(1);
                            }
                        }
                    }
                }, '条'

            ]
        });

        //store.loadPage(1);
        // me.store.load({
        //     callback:function(records,options,success){
        //         var arr=[];
        //         for(var i=0;i<records.length;i++){
        //             if(records[i].raw["checked"]){
        //                 arr.push(records[i]);
        //             }
        //         }
        //         me.grid.getSelectionModel().select(arr);
        //     }
        // });
        Ext.apply(me, {
            layout: 'fit',
            items: [me.grid],
            modelGrid: me.grid
        });
        me.callParent(arguments);
    },
    //解决分页加载选择的岗位
    loadChoosePost: function (records, options, success) {
        var me = this;
        var arr = [];
        var count=0;
        for (var i = 0; i < options.length; i++) {
            if (options[i].raw["checked"]) {
                arr.push(options[i]);
                if (me.allSelectedRecords.length == 0) {
                    me.allSelectedRecords.push(options[i]);
                } else {
                    Ext.each(me.allSelectedRecords, function (selected) {
                        // if (me.allSelectedRecords.indexOf(options[i]) == -1) {
                        //     me.allSelectedRecords.push(options[i]);
                        // }
                        if (selected.get("id")==options[i].raw['id']) {
                            count++;
                        }
                    });
                    if (count==0){
                        me.allSelectedRecords.push(options[i]);
                    }
                }
            }else{
                Ext.each(me.allSelectedRecords, function (selected) {
                    if (selected.get("id")==options[i].raw['id']) {
                        options[i].raw['checked']=true;
                        arr.push(options[i]);
                    }
                });
            }
        }
        me.grid.getSelectionModel().select(arr, true, true);
    },
    indexof: function (val, allSelectedRecords) {
        for (var i = 0; i < allSelectedRecords.length; i++) {
            if (allSelectedRecords[i].raw['id']== val.get("id")) {
                return i;
            }
        }
        return -1;
    },
    remove: function (val, allSelectedRecords) {
        var index = this.indexof(val, allSelectedRecords);
        if (index != -1) {
            allSelectedRecords.splice(index, 1);
        }
    }
});
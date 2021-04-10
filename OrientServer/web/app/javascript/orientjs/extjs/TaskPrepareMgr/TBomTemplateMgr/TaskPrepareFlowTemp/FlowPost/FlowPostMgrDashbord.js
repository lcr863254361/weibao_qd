Ext.define('OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.FlowPost.FlowPostMgrDashbord', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    alternateClassName: 'FlowPostMgrDashbord',
    config: {
        hangciId: null,
        flowPostData: null,
        pageSize: 25
    },
    requires: [
        'Ext.selection.CellModel',
        'Ext.grid.*',
        'Ext.data.*',
        'Ext.util.*',
        'Ext.form.*',
        'Ext.ux.data.PagingMemoryProxy',
        'OrientTdm.Collab.Data.DevData.Common.UploadFileWin'
    ],

    createToolBarItems: function () {
        var me = this;
        var retVal = [{
            iconCls: 'icon-export',
            text: '发布流动岗位',
            itemId: 'submit',
            scope: this,
            handler: me.publishFlowPost
        }];
        return retVal;
    },

    initData: function () {
        var me = this;
        Ext.each(me.flowPostData.columns, function (column) {
            if (column.meType === '流动') {
                var postId = column.dataIndex;
                column.align = 'center';
                column.renderer = function (value, cellmeta, record, rowIndex, columnIndex, store) {
                    // return "<span style='margin-right: 10px'><a href='#'>查看</a></span>";
                    var taskId = record.raw['taskId'];
                    var taskState = record.raw['taskState'];
                    var attendPersonList = record.raw['attendPersonList'];
                    var attendPersonIds;
                    var attendPersonNames;
                    if (attendPersonList.length > 0) {
                        for (var i = 0; i < attendPersonList.length; i++) {
                            var queryPostId = attendPersonList[i].postId;
                            if (postId === queryPostId) {
                                attendPersonIds = attendPersonList[i].attendPersonIds;
                                attendPersonNames = attendPersonList[i].attendPersonNames;
                                if (attendPersonNames == null || attendPersonNames == '无' || attendPersonNames == '') {
                                    attendPersonNames = '未设置';
                                }
                                break;
                            }
                        }
                    }
                    if (taskState == '已结束') {
                        return "<span style='color:black'>" + attendPersonNames + " </span>";
                    } else {
                        return "<a href='#' style='color:blue' onclick=\"showWinPersonName('" + postId + "','" + attendPersonIds + "','" + taskId + "')\">" + attendPersonNames + "</a>";
                    }
                }
            }
        })

        showWinPersonName = function (postId, attendPersonIds, taskId) {
            //弹出新增面板窗口
            var createWin = Ext.create('Ext.Window', {
                title: '选择参与人员',
                id: 'attendWindow',
                plain: true,
                height: 0.5 * globalHeight,
                width: 0.5 * globalWidth,
                layout: 'fit',
                maximizable: true,
                modal: true,
                items: [Ext.create('OrientTdm.TaskPrepareMgr.Center.TabPanel.AddPanel.PostSelectPersonGrid', {
                    postId: postId,
                    taskId: taskId,
                    buttonShow: true,
                    selectPersonIds: attendPersonIds,
                    successCallback: function (resp, callBackArguments) {
                        me.fireEvent("refreshGrid");
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
                        handler: function () {
                            var postGridPanel = Ext.getCmp("postSelectPersonGridOwner").items.items[0];
                            console.log(postGridPanel);
                            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/selectPersonData.rdm', {
                                    selectPersonId: OrientExtUtil.GridHelper.getSelectRecordIds(postGridPanel),
                                    taskId: taskId,
                                    postId: postId,
                                    beforeSelectPersonIds: attendPersonIds,
                                    signPersonLogo: false
                                },
                                false, function (response) {
                                    if (response.decodedData.success) {
                                        Ext.getCmp('attendWindow').close();
                                        me.store.reload();    //刷新绑定面板
                                    } else {
                                        Ext.Msg.alert("提示", response.decodedData.msg);
                                    }
                                });
                        }
                    },
                    {
                        text: '关闭',
                        iconCls: 'icon-close',
                        handler: function () {
                            this.up('window').close();
                        }
                    }
                ]

            });
            createWin.show();
        };
    },
    initComponent: function () {
        var me = this;
        me.initData();
        // me.store = Ext.create('Ext.data.Store', {
        //     fields: me.flowPostData.fields,
        //     remoteSort: false,
        //     //设置为 true 则将所有的过滤操作推迟到服务器
        //     remoteFilter: true,
        //     proxy: {
        //         type: 'ajax',
        //         url: serviceName + "/taskPrepareController/getDivingTaskFlowPost.rdm",
        //         reader: {
        //             type: 'json',
        //             root: 'results',
        //             totalProperty: 'totalProperty'
        //         },
        //         extraParams: {
        //             hangduanId: me.
        //         }
        //     },
        //     sorters: [{
        //         direction: 'desc'
        //     }],
        //     autoLoad: true
        // });
        // me.bbar = Ext.create('Ext.PagingToolbar', {
        //     store: me.store,
        //     displayInfo: true,
        //     displayMsg: '显示 {0} - {1} 条，共 {2} 条',
        //     emptyMsg: "没有数据",
        //     items: [
        //         {
        //             xtype: 'tbseparator'
        //         },
        //         {
        //             xtype: 'numberfield',
        //             labelWidth: 40,
        //             width: 100,
        //             fieldLabel: '每页',
        //             enableKeyEvents: true,
        //             value: me.pageSize,
        //             listeners: {
        //                 keydown: function (field, e) {
        //                     if (e.getKey() == Ext.EventObject.ENTER && !Ext.isEmpty(field.getValue())) {
        //                         var newPageSize = field.getValue();
        //                         var store = me.store;
        //                         store.pageSize = newPageSize;
        //                         store.loadPage(1);
        //                     }
        //                 }
        //             }
        //         }, '条'
        //
        //     ]
        // });
        // Ext.apply(this, {
        //     // renderTo: document.body,
        //     height: 0.87 * globalHeight,
        //     width: 0.87 * globalWidth,
        //     layout:'fit',
        //     columnLines: true,
        //     plugins: [this.cellEditing],
        //     store: me.store,
        //     columns: me.flowPostData.columns,
        //     selModel: {
        //         mode: 'MULTI'
        //     },
        //     bodyStyle:"height:100%;width:100%",
        //     selType: "checkboxmodel"
        // });

        me.callParent();
    },
    createColumns: function () {
        var me = this;
        var retVal = me.flowPostData.columns;
        return retVal;
    },
    createStore: function () {
        var me = this;
        var retVal = Ext.create('Ext.data.Store', {
            fields: me.flowPostData.fields,
            remoteSort: false,
            //设置为 true 则将所有的过滤操作推迟到服务器
            remoteFilter: true,
            proxy: {
                type: 'ajax',
                url: serviceName + "/taskPrepareController/getDivingTaskFlowPost.rdm",
                reader: {
                    type: 'json',
                    root: 'results',
                    totalProperty: 'totalProperty'
                },
                extraParams: {
                    hangduanId: me.hangduanId
                }
            },
            sorters: [{
                direction: 'desc'
            }],
            autoLoad: true
        });
        this.store = retVal;
        return retVal;
    },
    publishFlowPost: function () {
        var me = this;
        var hangduanId=me.hangduanId;
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/publishFlowPost.rdm', {
            hangduanId: hangduanId
        }, false, function (resp) {
            var result = resp.decodedData;
            if (result.success){
                // OrientExtUtil.Common.tip('成功',result.msg);
            }
        });
    }
});
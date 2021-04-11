Ext.define('OrientTdm.TaskPrepareMgr.MxGraphEditor.MxGraphEditorPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.mxGraphEditorPanel',
    requires: [],
    config: {
        taskId: '',
        taskName: ''
    },
    initComponent: function () {
        var me = this;
        var isHistoryTaskSearch=me.isHistoryTaskSearch;
        var taskName = me.taskName;
        var sid = new Date().getTime();
        me.mainActionsId = "mainActions" + sid;
        me.selectActionsId = "selectActions" + sid;
        me.graphPanelId = "graphPanel" + sid;
        me.topToolId = "topTool" + sid;

        var backgroundUrl = "app/javascript/orientjs/extjs/TaskPrepareMgr/MxGraphEditor/Images/window.gif";
        me.toolbarId = 'toolbar';
        me.graphId = 'graph';
        me.xmlPath = 'app/javascript/orientjs/extjs/TaskPrepareMgr/MxGraphEditor/Config/diagrameditor.xml';

        //当前任务状态显示
        if (me.isStatusSearch) {
            me.toolbarId = 'toolbar_search';
            me.graphId = 'graph_search';
            me.xmlPath = 'app/javascript/orientjs/extjs/TaskPrepareMgr/MxGraphEditor/Config/diagrameditor_search.xml';
        }
        if (me.taskEndState == '已结束') {
            me.toolbarId = 'toolbar';
            me.graphId = 'graph';
            me.xmlPath = 'app/javascript/orientjs/extjs/TaskPrepareMgr/MxGraphEditor/Config/diagrameditor.xml';
            me.closeButton = true;
        }
        var belongHangciHangduan='无当前任务！';
        if (!Ext.isEmpty(me.taskId)) {
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CurrentTaskMgr/getHangciHangduanData.rdm', {
                    taskId: me.taskId
                },
                false, function (response) {

                    belongHangciHangduan = response.decodedData.results + " " + taskName;
                    //me.up('setsMgrStageTabPanel').next("panel[region=south]").child(xtype = 'grid').refreshGrid();
                })
        }

        Ext.apply(me, {
            layout: 'border',
            tbar: ['',
                {
                    xtype: 'button',
                    iconCls: 'icon-startproject',
                    text: '任务启动',
                    hidden: me.isStatusSearch || me.closeButton,
                    scope: me,
                    //handler: me.issueTask()
                    handler: function () {
                        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/divingTaskBegin.rdm', {
                            taskId: me.taskId
                        }, false, function (resp) {
                            if (resp.decodedData.success) {
                                //me.up('setsMgrStageTabPanel').next("panel[region=south]").child(xtype = 'grid').refreshGrid();
                                // Ext.Msg.alert("提示", "成功启动下潜任务！");
                                OrientExtUtil.Common.tip('成功', "成功启动下潜任务！");
                            } else {
                                Ext.Msg.alert("提示", resp.decodedData.msg);
                            }
                        });
                    }
                },
                {
                    xtype: 'button',
                    iconCls: 'icon-back',
                    text: '返回上一页',
                    hidden: me.isStatusSearch,
                    scope: me,
                    handler: function () {
                        //console.log(Ext.getCmp('taskPrepareDashboardOwner1'));  //获取上层组件的上层组件
                        //var cardPanel=Ext.getCmp('taskPrepareDashboardOwner1');
                        //var gridPanel=Ext.create("OrientTdm.TaskPrepareMgr.Center.TaskMgrDataShowRegion", {
                        //    region: 'center',
                        //    padding: '0 0 0 5',
                        //    isStatusSearch:false
                        //});
                        //cardPanel.add(gridPanel);
                        //cardPanel.navigation(cardPanel,'next');
                        // var centerPanel = Ext.getCmp("taskPrepareCenterPanel");
                        var centerPanel=me.ownerCt.ownerCt.ownerCt.centerPanel;
                        centerPanel.items.each(function (item, index) {
                            centerPanel.remove(item);
                        });
                        //设置技术安全表模板面板
                        var divingTaskMgrGrid = Ext.create("OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.DivingTaskMgrGrid", {
                            region: 'center',
                            //workItemTempId: node.raw.idList[0]
                            flowId: me.flowId,
                            isHistoryTaskSearch:isHistoryTaskSearch
                        });
                        centerPanel.add({
                            title: "下潜任务管理",
                            //iconCls: "icon-preview",
                            layout: 'border',
                            items: [divingTaskMgrGrid]
                        });
                        var personnelWeightGrid = Ext.create("OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.PersonnelWeightGrid", {
                            region: 'center',
                            flowId:me.flowId
                        });
                        centerPanel.add({
                            title: "航段信息管理",
                            //iconCls: "icon-preview",
                            layout: 'border',
                            items: [personnelWeightGrid]
                        });
                        var params = {
                            hangduanId:me.flowId
                        };
                        var flowPostData;
                        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/getFlowPostHead.rdm', params, false, function (resp) {
                            flowPostData = resp.decodedData.results;
                        });
                        var flowPostMgrGrid = Ext.create('OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.FlowPost.FlowPostMgrDashbord', {
                            hangduanId: me.flowId,
                            flowPostData: flowPostData,
                            region: 'center'
                        });
                        centerPanel.add({
                            title: "流动岗位管理",
                            //iconCls: "icon-preview",
                            layout: 'border',
                            items: [flowPostMgrGrid]
                        });
                        var params = {
                            hangduanId:  me.flowId
                        };
                        var flowPostData;
                        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/getAttendTimesFlowPostHead.rdm', params, false, function (resp) {
                            flowPostData = resp.decodedData.results;
                        });
                        var attendTimesStatisticsMgr = Ext.create('OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.FlowPost.AttendTimesStatisticsMgr', {
                            hangduanId:  me.flowId,
                            flowPostData: flowPostData,
                            region: 'center'
                        });
                        centerPanel.add({
                            title: "参与次数统计",
                            //iconCls: "icon-preview",
                            layout: 'border',
                            items: [attendTimesStatisticsMgr],
                            listeners: {
                                'beforeshow': function () {
                                    attendTimesStatisticsMgr.store.load();
                                }
                            }
                        });
                        centerPanel.setActiveTab(0);
                    }
                }, {
                    xtype: 'button',
                    iconCls: 'icon-closeFlow',
                    text: '任务结束',
                    hidden: me.isStatusSearch || me.closeButton,
                    scope: me,
                    //handler: me.issueTask()
                    handler: function () {
                        Ext.Msg.confirm("友情提示", "是否确认结束",
                            function (btn) {
                                if (btn == 'yes') {
                                    OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/divingTaskEnd.rdm', {
                                        taskId: me.taskId
                                    }, false, function (resp) {
                                        if (resp.decodedData.success) {
                                            // Ext.Msg.alert("提示", resp.decodedData.msg);
                                        } else {
                                            Ext.Msg.alert("提示", resp.decodedData.msg);
                                        }
                                    });
                                }
                            });
                    }
                }, {
                    xtype: 'tbtext',
                    text: '<span style="color: black" >' + belongHangciHangduan + '</span>',
                    hidden: !me.isStatusSearch
                }, '  ', {
                    xtype: 'button',
                    text: '未完成',
                    iconCls: 'icon-orange',
                    hidden: !me.isStatusSearch
                }
                // , {
                //     xtype: 'button',
                //     text: '未通过',
                //     iconCls: 'icon-red',
                //     hidden: !me.isStatusSearch
                // }
                , {
                    xtype: 'button',
                    text: '已通过',
                    iconCls: 'icon-green',
                    hidden: !me.isStatusSearch
                },{
                    xtype: 'button',
                    iconCls: 'icon-export',
                    text: '导出所有表格word',
                    scope: me,
                    handler:function () {
                        // console.log('xawdw')
                        // console.log($("[data-recordid]"))
                        // let instanceIds=[]
                        // for(var i=0;i<$("#centerTabPanelWater [data-recordid]").length;i++){
                        //     instanceIds.push($("#centerTabPanelWater [data-recordid]")[i].getAttribute('data-recordid'))
                        // }
                        // console.log(instanceIds)
                        var params={
                            taskId:me.taskId
                        };
                        // if (instanceIds.length==0){
                        //     Ext.Msg.alert('提示','没有表格！');
                        //     return;
                        // }
                        Ext.getBody().mask("请稍后...", "x-mask-loading");
                        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CurrentTaskMgr/getCheckTableCaseHtmlList.rdm', params, true, function (response) {
                            $("#myWord").remove();
                            $("body").append('<div id="myWord" style="display: none" >'+response.decodedData+'</div>')
                            setTimeout(()=>{
                                $("#myWord").wordExport3('表格信息')
                                Ext.getBody().unmask();
                            },500)
                        });
                    }
                },{
                    xtype: 'button',
                    iconCls: 'icon-import',
                    text: '检查表绑定流程',
                    scope: me,
                    handler:function () {
                        var win = Ext.create('Ext.Window', {
                            title: '检查表绑定流程',
                            plain: true,
                            height: 110,
                            width: '40%',
                            layout: 'fit',
                            maximizable: true,
                            modal: true,
                            items: [{
                                xtype: 'form',
                                bodyPadding: 10,
                                layout: 'anchor',
                                defaults: {
                                    anchor: '100%',
                                    labelAlign: 'left',
                                    msgTarget: 'side',
                                    labelWidth: 90
                                },
                                items: [{
                                    xtype: 'filefield',
                                    buttonText: '',
                                    fieldLabel: '检查表绑定流程(.xlsx)',
                                    buttonConfig: {
                                        iconCls: 'icon-upload'
                                    },
                                    listeners: {
                                        'change': function (fb, v) {
                                            if (v.substr(v.length - 3) != "xls" && v.substr(v.length - 4) != "xlsx") {
                                                OrientExtUtil.Common.info('提示', '请选择Excel文件');
                                                return;
                                            }
                                        }
                                    }
                                }]
                            }],
                            buttons: [{
                                text: '关联',
                                handler: function () {
                                    var form = win.down("form").getForm();
                                    if (form.isValid()) {
                                        form.submit({
                                            url: serviceName + '/taskPrepareController/easybindFlowData.rdm?taskId=' + me.taskId,
                                            waitMsg: '绑定中...',
                                            success: function (form, action) {
                                                OrientExtUtil.Common.info('成功', action.result.msg, function () {
                                                    win.close();
                                                });
                                            },
                                            failure: function (form, action) {
                                                OrientExtUtil.Common.info('失败', action.result.msg, function () {
                                                    win.close();
                                                });
                                            }
                                        });
                                    }
                                }
                            }]
                        });
                        win.show();
                    }
                }],
            items: [
                   {
                   region: 'north',
                   height: 25,
                   border: false,
                   // hidden: me.isStatusSearch,
                   html: '<div id="' + me.topToolId + '">' +
                   '<div id="' + me.mainActionsId + '" style="width:100%;padding-top:8px;padding-left:24px;padding-bottom:4px;display: inline"></div>' +
                   '<div id="' + me.selectActionsId + '" style="float:right;padding-top: 2px;padding-right:4px;display: inline"></div>' +
                   '</div>'
                },
                {
                    html: '<table>' +
                    '<tr>' +
                    '<td id="' + me.toolbarId + '" style="width:16px;background: url(' + backgroundUrl + ');" valign="top"></td>' +
                    '<td id="' + me.graphPanelId + '" valign="top" class="dataFlow">' +
                    '<div id="' + me.graphId + '" style="position:relative;width:100%;height:auto;overflow:scroll;cursor:default;"></div>' +
                    '</td>' +
                    '</tr>' +
                    '</table>',
                    region: 'center',
                    border: false,
                    autoScroll: true
                }],
            listeners: {
                afterrender: me._initDiag,
                scope: me
            }
        });
        me.callParent(arguments);
    },
    //issueTask: function () {
    //    var me = this;
    //   //下发到当前任务
    //        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/divingTaskBegin.rdm', {
    //            taskId: me.taskId
    //        }, false, function (resp) {
    //            if (resp.decodedData.success) {
    //                //me.up('setsMgrStageTabPanel').next("panel[region=south]").child(xtype = 'grid').refreshGrid();
    //                Ext.Msg.alert("提示", "下潜任务成功启动！");
    //            }
    //        });
    //},
    _initDiag: function () {
        var me = this;
        var taskId = me.taskId;

        if (!mxClient.isBrowserSupported) {
            mxUtils.error('Browser is not supported!', 200, false);
        }
        if (mxClient.IS_IE) {
            me.resizer = new mxDivResizer(container);
        }
        //加载配置文件
        var config = mxUtils.load(me.xmlPath).getDocumentElement();
        var editor = new mxEditor(config);
        me.editor = editor;

        //set the save path and parameters
        editor.urlPost = "taskPrepareController/saveMxGraphXml.rdm?taskId=" + taskId;
        //绑定快捷操作
        // var keyHandler = new mxDefaultKeyHandler(editor);
        // keyHandler.bindAction(46, 'delete');
        // keyHandler.bindAction(65, 'selectAll', 1);
        // keyHandler.bindAction(90, 'undo', 1);
        // keyHandler.bindAction(89, 'redo', 1);
        // keyHandler.bindAction(88, 'cut', 1);
        // keyHandler.bindAction(67, 'copy', 1);
        // keyHandler.bindAction(86, 'paste', 1);

        // Crisp rendering in SVG except for connectors, actors, cylinder, ellipses
        mxShape.prototype.crisp = true;
        mxActor.prototype.crisp = false;
        mxCylinder.prototype.crisp = false;
        mxEllipse.prototype.crisp = false;
        mxDoubleEllipse.prototype.crisp = false;
        mxConnector.prototype.crisp = false;

        // Enables guides
        mxGraphHandler.prototype.guidesEnabled = true;

        // Alt disables guides
        mxGuide.prototype.isEnabledForEvent = function (evt) {
            return !mxEvent.isAltDown(evt);
        };

        // Enables snapping waypoints to terminals
        mxEdgeHandler.prototype.snapToTerminals = true;

        // Defines an icon for creating new connections in the connection handler.
        // This will automatically disable the highlighting of the source vertex.
        if (me.isStatusSearch) {
        editor.graph.setTooltips(false);
        }
        else {
           if (me.taskEndState == '已结束') {
               editor.graph.setTooltips(false);
               editor.graph.setConnectable(false);
               // Clones the source if new connection has no target
               editor.graph.connectionHandler.setCreateTarget(false);
           } else {
               mxConnectionHandler.prototype.connectImage = new mxImage('app/javascript/orientjs/extjs/TaskPrepareMgr/MxGraphEditor/Images/connector.gif', 16, 16);
               editor.graph.setConnectable(true);
               // Clones the source if new connection has no target
               editor.graph.connectionHandler.setCreateTarget(true);
               editor.graph.setTooltips(false);
           }
        }

        // Displays information about the session
        // in the status bar
        editor.addListener(mxEvent.SESSION, function (editor, evt) {
            var session = evt.getProperty('session');

            if (session.connected) {
                var tstamp = new Date().toLocaleString();
                editor.setStatus(tstamp + ':' +
                    ' ' + session.sent + ' bytes sent, ' +
                    ' ' + session.received + ' bytes received');
            } else {
                editor.setStatus('Not connected');
            }
        });

        //add save callback function
        editor.addListener(mxEvent.SAVE, function (editor, evt) {
            //为了给后台保存数据的时间
            OrientExtUtil.Common.mask('提交中，请稍后...');
            setTimeout(function () {
                OrientExtUtil.Common.unmask();
                Ext.Msg.alert("提示", "保存成功！");
            }, 500);
        });

        editor.graph.addListener(mxEvent.REMOVE_CELLS, function (editor, evt) {
            if (editor.getSelectionCell() !== undefined) {
                var nodeId = editor.getSelectionCell().id;
                OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/deleteCellMxgraph.rdm', {
                    nodeId: nodeId,
                    flowTempTypeId:'',
                    taskId:taskId
                }, false, function (resp) {
                });
            }
        });

        //cell change event
        editor.graph.getSelectionModel().addListener(mxEvent.CHANGE, function (sender, evt) {

            console.log(me.ownerCt.items.items[1].items);
            var nodeFilePanel = me.ownerCt.items;

            if (me.isStatusSearch) {

                var nodeId = "";
                var nodeText = "未选择";
                var showCheckTablePanel = Ext.create('OrientTdm.TaskPrepareMgr.Center.TabPanel.CheckTableMgrPanel', {
                    region: 'center',
                    padding: '0 0 0 0',
                    taskId: me.taskId
                });
                if (sender.cells.length !== 1) {
                    //return;
                    showCheckTablePanel.refreshCheckfilePanelByNode(nodeId, nodeText);
                } else {
                    nodeId = sender.cells[0].id;
                    nodeText = sender.cells[0].value.getAttribute('label');
                    if (sender.cells[0].isVertex() !== 1 && sender.cells[0].isVertex() !== 2) {
                        return;
                    }
                    //if(nodeText=='下潜作业'){
                    if (sender.cells[0].isVertex() == 2) {
                        if (me.isStatusSearch) {
                            //var centerPanel = Ext.getCmp('taskDesignPanel');
                            //centerPanel.items.each(function (item, index) {
                            //    centerPanel.remove(item);
                            //});
                            var centerPanel = Ext.getCmp("centerTabPanelWater");
                            centerPanel.removeAll();
                            var waterDownRecordGridId = Ext.getCmp("waterDownRecordGridOwner");
                            var waterRecordGrid = Ext.getCmp("waterRecordGrid");
                            if (!waterDownRecordGridId & !waterRecordGrid) {
                                var waterDownRecordGrid = Ext.create('OrientTdm.CurrentTaskMgr.WaterDownRecordGrid', {
                                    region: 'center',
                                    padding: '0 0 0 0',
                                    id: 'waterDownRecordGrid1',
                                    taskId: me.taskId
                                });
                                var waterRecordGrid = Ext.create('OrientTdm.CurrentTaskMgr.WaterRecordGrid', {
                                    region: 'center',
                                    padding: '0 0 0 0',
                                    taskId: me.taskId
                                });
                                centerPanel.add(
                                    {
                                        title: '水面记录单',
                                        //iconCls: record.raw['iconCls'],
                                        layout: 'border',
                                        items: [waterRecordGrid],
                                        waterRecordGrid: waterRecordGrid
                                    },
                                    {
                                        title: '水下记录单',
                                        //iconCls: record.raw['iconCls'],
                                        layout: 'border',
                                        items: [waterDownRecordGrid],
                                        waterDownRecordGrid: waterDownRecordGrid
                                    });
                                centerPanel.setActiveTab(waterDownRecordGrid);
                                centerPanel.setActiveTab(waterRecordGrid);
                                Ext.getCmp("waterRecordGrid").refreshWaterPanelByNode(nodeId, nodeText);
                            }
                        }
                    } else {
                        if (sender.cells[0].isVertex() !== 1 && sender.cells[0].isVertex() !== 2) {
                            return;
                        }
                        var centerPanel = Ext.getCmp("centerTabPanelWater");
                        centerPanel.removeAll();

                        centerPanel.add({
                            layout: 'border',
                            title: '检查表格',
                            //iconCls: treeNode.get('iconCls'),
                            items: [
                                showCheckTablePanel
                            ],
                            showCheckTablePanel: showCheckTablePanel
                        });
                        centerPanel.setActiveTab(0);
                        showCheckTablePanel.refreshCheckfilePanelByNode(nodeId, nodeText);
                    }
                }
            } else {
                console.log(nodeFilePanel.items[1].getActiveTab());
                var nodeId = "";
                var nodeText = "未选择";
                if (sender.cells.length !== 1) {
                    //Consider the delete and the multiselect operation
                    if (nodeFilePanel.items[1].getActiveTab().title === '参与岗位') {
                        var toolbar = nodeFilePanel.items[1].items.items[1].attendPostMgrPanel.child(xtype = 'grid').getDockedItems('toolbar[dock="top"]')[0];
                        if (!Ext.isEmpty(toolbar)) {
                            toolbar.setDisabled(true);
                        }
                        console.log(Ext.getCmp("SetsMgrStageTabPanelOwner"));
                        Ext.getCmp("SetsMgrStageTabPanelOwner").refreshPostfilePanelByNode(nodeId, nodeText);
                    } else if (nodeFilePanel.items[1].getActiveTab().title === '检查表格') {
                        var toolbar = nodeFilePanel.items[1].items.items[0].checkTableMgrPanel.child(xtype = 'grid').getDockedItems('toolbar[dock="top"]')[0];
                        // if (!Ext.isEmpty(toolbar)) {
                        //     toolbar.setDisabled(true);
                        // }
                        Ext.getCmp("SetsMgrStageTabPanelOwner").refreshCheckfilePanelByNode(nodeId, nodeText);
                    }

                } else {
                    if (sender.cells[0].isVertex() !== 1 && sender.cells[0].isVertex() !== 2) {
                        return;
                    }
                    nodeId = sender.cells[0].id;
                    nodeText = sender.cells[0].value.getAttribute('label');

                    if (nodeFilePanel.items[1].getActiveTab().title === '参与岗位') {
                        var toolbar = nodeFilePanel.items[1].items.items[1].attendPostMgrPanel.child(xtype = 'grid').getDockedItems('toolbar[dock="top"]')[0];
                        if (me.taskEndState == '已结束') {
                            toolbar.setDisabled(true);
                        } else {
                            if (!Ext.isEmpty(toolbar)) {
                                toolbar.setDisabled(false);
                            }
                        }
                        console.log(Ext.getCmp("addPost_own_1"));
                        Ext.getCmp("addPost_own_1").refreshPostfilePanelByNode(nodeId, nodeText);
                    } else if (nodeFilePanel.items[1].getActiveTab().title === '检查表格') {
                        var toolbar = nodeFilePanel.items[1].items.items[0].checkTableMgrPanel.child(xtype = 'grid').getDockedItems('toolbar[dock="top"]')[0];
                        if (me.taskEndState == '已结束') {
                            toolbar.setDisabled(true);
                        } else {
                            if (!Ext.isEmpty(toolbar)) {
                                toolbar.setDisabled(false);
                            }
                        }
                        Ext.getCmp("SetsMgrStageTabPanelOwner").refreshCheckfilePanelByNode(nodeId, nodeText);
                    }
                }
            }
        });

        // Changes the zoom on mouseWheel events
        // if(!me.isStatusSearch) {
        //         //    mxEvent.addMouseWheelListener(function (evt, up) {
        //         //        //if (!mxEvent.isConsumed(evt)) {
        //         //        if (up) {
        //         //            editor.execute('zoomIn');
        //         //        } else {
        //         //            editor.execute('zoomOut');
        //         //        }
        //         //        //    mxEvent.consume(evt);
        //         //        //}
        //         //    });
        //         // }
        if (me.isStatusSearch) {

        } else {
           // Create select actions in page
           var node = document.getElementById(me.mainActionsId);
           var buttons = ['save', 'delete', 'undo'];//, 'undo', 'redo', 'print', 'cut', 'copy', 'paste'];

           for (var i = 0; i < buttons.length; i++) {
               var button = document.createElement('button');
               mxUtils.write(button, mxResources.get(buttons[i]));

               var factory = function (name) {
                   return function () {
                       editor.execute(name);
                   };
               };

               mxEvent.addListener(button, 'click', factory(buttons[i]));
               node.appendChild(button);
           }

           var autoLayout = document.createElement('button');
           mxUtils.write(autoLayout, '自动布局');
           mxEvent.addListener(autoLayout, 'click', function () {
               me._autolayout();
           });
           node.appendChild(autoLayout);

           //选择工具
           var selectActions = document.getElementById(me.selectActionsId);
           mxUtils.write(selectActions, '选择: ');
           mxUtils.linkAction(selectActions, '全部', editor, 'selectAll');
           mxUtils.write(selectActions, ', ');
           mxUtils.linkAction(selectActions, '全部取消', editor, 'selectNone');
           mxUtils.write(selectActions, ', ');
           mxUtils.linkAction(selectActions, '单元', editor, 'selectVertices');
           mxUtils.write(selectActions, ', ');
           mxUtils.linkAction(selectActions, '连线', editor, 'selectEdges');
        }
        if (!me.isStatusSearch) {
           if (me.taskEndState == '已结束') {

           } else {
               //添加键盘delete事件
               document.onkeydown = function (event) {
                   var e = event || window.event || arguments.callee.caller.arguments[0];
                   if (e && e.keyCode == 46) {
                       editor.execute('delete');
                   }
               }
           }
        }
        //增加管理员权限控制
        // var isSuperAdmin = false;
        // OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/isSuperAdmin.rdm', {}, false, function (resp) {
        //    isSuperAdmin = resp.decodedData.results;
        //    if (isSuperAdmin) {
        //        //定制，增加锁定和解除锁定按钮
        //        var lock = document.createElement('button');
        //        mxUtils.write(lock, '锁定');
        //        mxEvent.addListener(lock, 'click', function () {
        //            me._lockOperation(0);
        //        });
        //        node.appendChild(lock);
        //
        //        var unLock = document.createElement('button');
        //        mxUtils.write(unLock, '解锁');
        //        mxEvent.addListener(unLock, 'click', function () {
        //            me._lockOperation(1);
        //        });
        //        node.appendChild(unLock);
        //    }
        // });

        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/getXmlStr.rdm', {
            taskId: taskId
        }, false, function (resp) {
            var xmlString = resp.decodedData.results;
            if (!Ext.isEmpty(xmlString)) {
                var doc = mxUtils.parseXml(xmlString);
                var node = doc.documentElement;
                editor.readGraphModel(node);
            }
        });

        me.canEdit = true;
        if (me.isStatusSearch) {
            me.canEdit = false;
           me._switchEdit();
        }
        if (me.taskEndState == '已结束') {
            me.canEdit = false;
           me._switchEdit();
        }
        me._switchEdit();
        if (me.isStatusSearch) {
            me._changeNodeColor();
            me._addBackgroundImage();
        }
        me.addDeleteOverlay();
        //me.graphCenterShow();

        //    var bounds=me.editor.graph.getGraphBounds();
        //    var margin=10;
        //me.editor.graph.container.style.overflow="hidden";
        //me.editor.graph.view.setTranslate(
        //        -bounds.x -(bounds.width-me.editor.graph.container.clientWidth)/2
        //       -bounds.y-(bounds.height-me.editor.graph.container.clientHeight)/2
        //    );
        //    while((bounds.width+margin*2)>me.editor.graph.container.clientWidth||(bounds.height+margin*2)>me.editor.graph.container.clientHeight){
        //        me.editor.graph.zoomOut();
        //        bounds=me.editor.graph.getGraphBounds();
        //    }
        //me.editor.graph.container.style.overflow="auto";

    },

    _autolayout: function () {
        var me = this;
        var graph = me.editor.graph;
        //pay attention to the orientation
        var layout = new mxHierarchicalLayout(graph, mxConstants.DIRECTION_WEST);
        layout.resizeParent = true;
        graph.getModel().beginUpdate();
        try {
            var parent = graph.getDefaultParent();
            layout.execute(parent);
        }
        finally {
            graph.getModel().endUpdate();
            //graph.fit();
        }
    },
    _changeNodeColor: function () {
        //根据状态改变颜色
        var me = this;
        var graph = me.editor.graph;
        var cells = graph.getChildVertices(graph.getDefaultParent());
        var model = graph.getModel();
        var picUrl = "app/javascript/orientjs/extjs/TaskPrepareMgr/MxGraphEditor/Images/lingxing.gif";
        var cellNodeIdList = [];
        Ext.each(cells, function (cell) {
            cellNodeIdList.push(cell.id);
        });

        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CurrentTaskMgr/checkStageNodeStatus.rdm', {
            taskId: me.taskId,
            cellNodeId: cellNodeIdList
        }, false, function (resp) {
            var ret = Ext.decode(resp.responseText).results;
            model.beginUpdate();
            Ext.each(cells, function (cell, index) {
                //var style='';
                var color = "#eac74c"; //未完成
                if (ret[index] === '已通过') {
                    color = "#80c342"; //已完成
                } else if (ret[index] === '未通过') {
                    color = "#ED0607";
                }
                if(cell.vertex===2){
                    style ="rhombus";
                    //graph.setCellStyles(mxConstants.STYLE_IMAGE,"image;image="+picUrl,[cell]);
                }else if(cell.vertex===1){
                    style = "rounded;";
                }
                //   var style = "rounded;";
                style += mxConstants.STYLE_STROKECOLOR + "=" + color + ";";
                style += mxConstants.STYLE_FILLCOLOR + "=" + color;
                model.setStyle(cell, style);

                //if (cell.isVertex() === 2) {
                //
                //    var style = model.getStyle(cell);
                //    if (style.indexOf(mxConstants.STYLE_DELETABLE) !== -1) {
                //        style = style.slice(0, 45);
                //    }
                //    style += ";";
                //    //style += mxConstants.STYLE_DELETABLE + "=" + 1 + ";";
                //    //标志是否可以删除
                //    style += mxConstants.STYLE_EDITABLE + "=" + 0;
                //    model.setStyle(cell, style);
                //        me.editor.graph.addCellOverlay(cell, new mxCellOverlay(new mxImage('app/javascript/orientjs/extjs/TaskPrepareMgr/MxGraphEditor/Images/lock.png', 16, 16), '已锁定'));
                //}
                //model.endUpdate();
            });
            model.endUpdate();
            //graph.getModel().endUpdate();
        });
    },

    _addBackgroundImage:function(){

    },

    _lockOperation: function (operationType) {
        //避开对线进行操作
        //锁定样式变为overlay
        var me = this;
        var alertMsg = ["锁定成功！", "解锁成功！"];
        var cells = me.editor.graph.getSelectionCells();
        var model = me.editor.graph.getModel();
        var nodeId = [];
        Ext.each(cells, function (cell) {
            model.beginUpdate();
            nodeId.push(cell.id);
            if (cell.isVertex() === 1) {
                var style = model.getStyle(cell);
                if (style.indexOf(mxConstants.STYLE_DELETABLE) !== -1) {
                    style = style.slice(0, 45);
                }
                style += ";";
                style += mxConstants.STYLE_DELETABLE + "=" + operationType + ";";
                //标志是否可以删除
                style += mxConstants.STYLE_EDITABLE + "=" + operationType;
                model.setStyle(cell, style);
                if (operationType === 0) {
                    me.editor.graph.removeCellOverlays(cell);
                    me.editor.graph.addCellOverlay(cell, new mxCellOverlay(new mxImage('app/javascript/orientjs/extjs/TaskPrepareMgr/MxGraphEditor/Images/lock.png', 16, 16), '已锁定'));
                } else {
                    me.editor.graph.removeCellOverlays(cell);
                }
            }
            model.endUpdate();
        });
        //操作数据库，修改nodefile是否是关键节点
        Ext.Msg.alert('提示', alertMsg[operationType]);
    },
    addDeleteOverlay: function () {
        var me = this;
        var graph = me.editor.graph;
        var cells = graph.getChildVertices(graph.getDefaultParent());
        var model = graph.getModel();
        Ext.each(cells, function (cell) {
            model.beginUpdate();
            if (!graph.isCellDeletable(cell)) {
                graph.addCellOverlay(cell, new mxCellOverlay(new mxImage('app/javascript/orientjs/extjs/TaskPrepareMgr/MxGraphEditor/Images/lock.png', 16, 16), '已锁定'));
            } else {
                graph.removeCellOverlays(cell);
            }
            model.endUpdate();
        });
    },
    _switchEdit: function () {
        /**
         *设置图形不可以拖动
         *设置图形不可以改变大小
         *设置不可以新增 修改和删除
         */
        var me = this;
        //切换浏览模式
        // me.canEdit = true;
        // me.editor.graph.setCellsLocked(true);
        //new mxCellTracker(me.editor.graph);
        //me.editor.graph.getSelectionCell();
        //me.editor.graph.isCellSelected(true);
        //me.editor.graph.createHandler();
        //me.editor.graph.setEnabled(true);
        //me.editor.graph.setPanning(true);
        //me.editor.graph.setTooltips(false);
        //me.editor.graph.panningHandler.useLeftButtonForPanning=false;
        //  me.editor.graph.setConnectable(false);
        //  me.editor.graph.setCellsEditable(false);
        //  me.editor.graph.setEnabled(false);
        //隐藏图标和按钮
        //   $("#" + me.toolbarId).hide();
        // $("#" + me.topToolId).hide();
        // $("#" + me.graphPanelId).removeClass("dataFlow");
        //切换浏览模式
        if (me.canEdit) {
            //me.canEdit = false;
            //me.editor.graph.setCellsLocked(true);
            //$("#" + me.toolbarId).hide();
            //$("#" + me.topToolId).hide();
            //$("#" + me.graphPanelId).removeClass("dataFlow");
            me.canEdit = true;
            me.editor.graph.setCellsLocked(false);
            $("#" + me.toolbarId).show();
            $("#" + me.topToolId).show();
            $("#" + me.graphPanelId).addClass("dataFlow");
        } else {
            me.canEdit = false;
            me.editor.graph.setCellsLocked(true);
            $("#" + me.toolbarId).hide();
            $("#" + me.topToolId).hide();
            $("#" + me.graphPanelId).removeClass("dataFlow");
        }
    },

    graphCenterShow: function () {
        /**
         *设置图形居中显示
         */
        var me = this;
        var graph = me.editor.graph;
        //graph.center(true,true,0.5,0.5);
        var sc=graph.getView().getScale();
        graph.zoomTo(Math.round(sc/2));
        //new mxCellTracker(me.editor.graph);
        //me.editor.graph.getSelectionCell();
        //me.editor.graph.isCellSelected(true);
        //me.editor.graph.createHandler();
        //me.editor.graph.setEnabled(true);
        //me.editor.graph.setPanning(true);
        //me.editor.graph.setTooltips(false);
        //me.editor.graph.panningHandler.useLeftButtonForPanning=false;
        //  me.editor.graph.setConnectable(false);
        //  me.editor.graph.setCellsEditable(false);
        //  me.editor.graph.setEnabled(false);
    }
});

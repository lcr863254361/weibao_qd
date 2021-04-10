Ext.define('OrientTdm.PlatformTemplateMgr.MxGraphEditor.MxGraphEditorPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.pMxGraphEditorPanel',
    requires: [],
    initComponent: function () {
        var me = this;
        var sid = new Date().getTime();
        me.mainActionsId = "mainActions" + sid;
        me.selectActionsId = "selectActions" + sid;
        me.graphPanelId = "graphPanel" + sid;
        me.topToolId = "topTool" + sid;

        var backgroundUrl = "app/javascript/orientjs/extjs/TaskPrepareMgr/MxGraphEditor/Images/window.gif";
        me.toolbarId = 'toolbar_platformTemplate';
        me.graphId = 'graph_platformTemplate';
        me.xmlPath = 'app/javascript/orientjs/extjs/PlatformTemplateMgr/MxGraphEditor/Config/diagrameditor.xml';
        Ext.apply(me, {
            layout: 'border',
            items: [{
                region: 'north',
                height: 25,
                border: false,
                html: '<div id="' + me.topToolId + '">' +
                    '<div id="' + me.mainActionsId + '" style="width:100%;padding-top:8px;padding-left:24px;padding-bottom:4px;display: inline"></div>' +
                    '<div id="' + me.selectActionsId + '" style="float:right;padding-top: 2px;padding-right:4px;display: inline"></div>' +
                    '</div>'
            }, {
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
    _initDiag: function () {
        var me = this;
        //var platformId = me.platformId;

        if (!mxClient.isBrowserSupported) {
            mxUtils.error('Browser is not supported!', 200, false);
        }
        if (mxClient.IS_IE) {
            me.resizer = new mxDivResizer(container);
        }
        var config = mxUtils.load(me.xmlPath).getDocumentElement();
        var editor = new mxEditor(config);
        me.editor = editor;
        //set the save path and parameters
        editor.urlPost = "taskPrepareController/saveMxGraphXml.rdm?flowTempTypeId=" + me.flowTempTypeId;

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
        mxConnectionHandler.prototype.connectImage = new mxImage('app/javascript/orientjs/extjs/TaskPrepareMgr/MxGraphEditor/Images/connector.gif', 16, 16);

        editor.graph.setConnectable(true);

        // Clones the source if new connection has no target
        editor.graph.connectionHandler.setCreateTarget(true);
        editor.graph.setTooltips(false);

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
            Ext.Msg.alert("提示", "保存成功！");
        });

        //editor.graph.addListener(mxEvent.REMOVE_CELLS, function (editor, evt) {
        //    if (editor.getSelectionCell() !== undefined) {
        //        //if (!editor.isCellDeletable(editor.getSelectionCell())) {
        //        //    Ext.Msg.alert("提示", "该节点已经锁定，无法删除！");
        //        //}
        //
        //    }
        //});

        editor.graph.addListener(mxEvent.REMOVE_CELLS, function (editor, evt) {
            if (editor.getSelectionCell() !== undefined) {
                //if (!editor.isCellDeletable(editor.getSelectionCell())) {
                //    Ext.Msg.alert("提示", "该节点已经锁定，无法删除！");
                //}
                var nodeId = editor.getSelectionCell().id;
                //Ext.Msg.confirm("友情提示", "是否确认删除",
                //    function (btn) {
                //        if (btn == 'yes') {
                OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/deleteCellMxgraph.rdm', {
                    nodeId: nodeId,
                    //platformId: platformId
                    flowTempTypeId:me.flowTempTypeId
                }, false, function (resp) {
                });
                //}
                //});
            }
        });

        //cell change event
        editor.graph.getSelectionModel().addListener(mxEvent.CHANGE, function (sender, evt) {
            //auto save
            // editor.execute('save');
            var nodeFilePanel = me.ownerCt.items;
            var nodeId = "";
            var nodeText = "未选择";
            var getAcitveTabTitle;
            var items;
            if (nodeFilePanel.items.length==3){
                getAcitveTabTitle=nodeFilePanel.items[2].getActiveTab().title;
                items=nodeFilePanel.items[2];
            } else{
                getAcitveTabTitle=nodeFilePanel.items[1].getActiveTab().title;
                items=nodeFilePanel.items[1];
            }
            if (sender.cells.length !== 1) {
                //Consider the delete and the multiselect operation
                if (getAcitveTabTitle=== '参与岗位') {
                    var toolbar =items.items.items[0].attendPostMgrPanel.child(xtype = 'grid').getDockedItems('toolbar[dock="top"]')[0];
                    if (!Ext.isEmpty(toolbar)) {
                        toolbar.setDisabled(true);
                    }
                    Ext.getCmp("addPost_owner_1").refreshPostfilePanelByNode(nodeId, nodeText);
                } else if (getAcitveTabTitle=== '检查表格') {
                    var toolbar = items.items.items[1].checkTableMgrPanel.child(xtype = 'grid').getDockedItems('toolbar[dock="top"]')[0];
                    if (!Ext.isEmpty(toolbar)) {
                        toolbar.setDisabled(true);
                    }
                    Ext.getCmp("bindCheckTableTempGrid_2").refreshCheckfilePanelByNode(nodeId, nodeText);
                }

            } else {
                if (sender.cells[0].isVertex() !== 1 && sender.cells[0].isVertex() !== 2) {
                    return;
                }
                nodeId = sender.cells[0].id;
                nodeText = sender.cells[0].value.getAttribute('label');

                if (getAcitveTabTitle === '参与岗位') {
                    var toolbar = items.items.items[0].attendPostMgrPanel.child(xtype = 'grid').getDockedItems('toolbar[dock="top"]')[0];
                    if (!Ext.isEmpty(toolbar)) {
                        toolbar.setDisabled(false);
                    }
                    console.log(Ext.getCmp("addPost_owner_1"));
                    Ext.getCmp("addPost_owner_1").refreshPostfilePanelByNode(nodeId, nodeText);
                } else if (getAcitveTabTitle === '检查表格') {
                    var toolbar = items.items.items[1].checkTableMgrPanel.child(xtype = 'grid').getDockedItems('toolbar[dock="top"]')[0];
                    if (!Ext.isEmpty(toolbar)) {
                        toolbar.setDisabled(false);
                    }
                    Ext.getCmp("bindCheckTableTempGrid_2").refreshCheckfilePanelByNode(nodeId, nodeText);
                }

            }
        });

        //Changes the zoom on mouseWheel events
        //mxEvent.addMouseWheelListener(function (evt, up) {
        //    //if (!mxEvent.isConsumed(evt)) {
        //        if (up) {
        //            editor.execute('zoomIn');
        //        } else {
        //            editor.execute('zoomOut');
        //        }
        //    //    mxEvent.consume(evt);
        //    //}
        //});

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
        //定制，增加锁定和解除锁定按钮
        //var lock = document.createElement('button');
        //mxUtils.write(lock, '锁定');
        //mxEvent.addListener(lock, 'click', function () {
        //    me._lockOperation(0);
        //});
        //node.appendChild(lock);
        //
        //var unLock = document.createElement('button');
        //mxUtils.write(unLock, '解锁');
        //mxEvent.addListener(unLock, 'click', function () {
        //    me._lockOperation(1);
        //});
        //node.appendChild(unLock);

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

        if (!me.isStatusSearch) {
            //添加键盘delete事件
            document.onkeydown = function (event) {
                var e = event || window.event || arguments.callee.caller.arguments[0];
                if (e && e.keyCode == 46) {
                    editor.execute('delete');
                }
            }
        }

        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/getXmlStr.rdm', {
            flowTempTypeId: me.flowTempTypeId
        }, false, function (resp) {
            var xmlString = resp.decodedData.results;
            if (!Ext.isEmpty(xmlString)) {
                var doc = mxUtils.parseXml(xmlString);
                var node = doc.documentElement;
                editor.readGraphModel(node);
            }
        });

        me.addDeleteOverlay();
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
    _lockOperation: function (operationType) {
        //避开对线进行操作
        //锁定样式变为overlay
        var me = this;
        var alertMsg = ["锁定成功！", "解锁成功！"];
        var cells = me.editor.graph.getSelectionCells();
        var model = me.editor.graph.getModel();
        Ext.each(cells, function (cell) {
            model.beginUpdate();
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
    }
});

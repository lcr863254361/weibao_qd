Ext.define('OrientTdm.CollabDev.Common.Content.Pedigree.MXClientHelper', {
    extend: 'Ext.Base',
    alternateClassName: 'MXClientHelper',
    statics: {
        /**
         * 初始化画布部分 如果需要获取graph的引用，可采用
         * MXClientHelper.initDrawRegion.call 或者 MXClientHelper.initDrawRegion.apply的方式进行调用
         */
        initDrawRegion: function (containerId, editAble, dirtySettingFn) {
            editAble = editAble || false;
            if (!mxClient.isBrowserSupported()) {
                mxUtils.error('浏览器不支持MXGraph！', 200, false);
            }
            else {
                var container = document.getElementById(containerId);
                //全局设置
                mxRectangleShape.prototype.crisp = true;
                var graph = new mxGraph(container);
                this.graph = graph;
                if (mxClient.IS_IE) {
                    var resizer = new mxDivResizer(container);
                    resizer.getDocumentHeight = function () {
                        //return Ext.getCmp("mainModelAncestryCard").getHeight() - 28;
                    };
                    resizer.getDocumentWidth = function () {
                        //return Ext.getCmp("mainModelAncestryCard").getWidth();
                    };
                    //resizer.resize();
                    graph.resizer = resizer;
                }
                graph.setEnabled(true);
                //整体移动
                graph.setPanning(true);
                graph.panningHandler.useLeftButtonForPanning = true;
                //graph.setTooltips(true);
                graph.htmlLabels = true;
                graph.setAllowDanglingEdges(false);
                graph.setMultigraph(false);
                graph.setAllowLoops(false);
                graph.setConnectable(editAble);
                graph.setCellsEditable(false);
                graph.setCellsResizable(false);
                //设置节点不可折叠
                graph.isCellFoldable = function (cell, collapse) {
                    return false;
                };
                //鼠标经过时高亮
                var highlight = new mxCellTracker(graph, '#00D800');
                //节点样式
                var style = graph.stylesheet.getDefaultVertexStyle();
                style[mxConstants.STYLE_SHAPE] = 'label';
                style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_CENTER;
                style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
                style[mxConstants.STYLE_FONTCOLOR] = '#333333';//'#1d258f';
                style[mxConstants.STYLE_FONTFAMILY] = '微软雅黑';//'Verdana';
                style[mxConstants.STYLE_FONTSIZE] = '12';
                style[mxConstants.STYLE_FONTSTYLE] = '1';
                style[mxConstants.STYLE_SHADOW] = '1';
                style[mxConstants.STYLE_ROUNDED] = '1';
                //线样式
                style = graph.stylesheet.getDefaultEdgeStyle();
                style[mxConstants.STYLE_ROUNDED] = true;//圆角连线
                style[mxConstants.STYLE_LABEL_BACKGROUNDCOLOR] = '#FFFFFF';
                style[mxConstants.STYLE_FONTCOLOR] = 'gray';
                style[mxConstants.STYLE_EDGE] = mxEdgeStyle.TopToBottom;
                style[mxConstants.STYLE_STROKEWIDTH] = 2;
                style[mxConstants.STYLE_EXIT_X] = 0.5; // center
                style[mxConstants.STYLE_EXIT_Y] = 1.0; // bottom
                style[mxConstants.STYLE_EXIT_PERIMETER] = 0; // disabled
                style[mxConstants.STYLE_ENTRY_X] = 0.5; // center
                style[mxConstants.STYLE_ENTRY_Y] = 0; // top
                style[mxConstants.STYLE_ENTRY_PERIMETER] = 0; // disabled
                style[mxConstants.STYLE_STROKECOLOR] = '#333333';
                //选中时高亮
                var modelGetStyle = graph.model.getStyle;
                graph.model.getStyle = function (cell) {
                    if (cell != null) {
                        var style = modelGetStyle.apply(this, arguments);
                        if (cell.isEdge() && style) {
                            if (cell.customHighlight) {
                                style = style + ';strokeColor=#C00000';
                            } else
                                style = style.replace(';strokeColor=#C00000', '');
                            return style;
                        } else if (cell.isVertex() && style) {
                            if (cell.customHighlight) {
                                style = style + ';fontColor=#C00000';
                            } else
                                style = style.replace(';fontColor=#C00000', '');
                            return style;
                        }
                    }
                    return null;
                };
                mxEvent.disableContextMenu(container);
                //弹出菜单
                graph.panningHandler.factoryMethod = function (menu, cell, evt) {
                    return MXClientHelper.createPopupMenu(graph, menu, cell, evt);
                };

                //图像布局
                var layout = new mxHierarchicalLayout(graph);
                layout.resizeParent = true;
                graph.layout = layout;

                //event
                graph.addListener(mxEvent.CLICK, MXClientHelper.mxGraphCellClicked);
                //remove
                var keyHandler = new mxKeyHandler(graph);
                keyHandler.bindKey(46, function (evt) {
                    if (graph.isEnabled() && graph.getSelectionCell() && graph.getSelectionCell().isEdge()) {
                        graph.removeCells();
                    }
                });

                // Installs automatic validation (use editor.validation = true
                // if you are using an mxEditor instance)
                var _this = this;
                var listener = function (sender, evt) {
                    graph.validateGraph();
                    if (dirtySettingFn) {
                        dirtySettingFn.call(_this);
                    }
                };

                graph.getModel().addListener(mxEvent.CHANGE, listener);
            }
        },
        createPopupMenu: function (graph, menu, cell, evt) {
            if (cell != null && cell.isEdge() == true) {
                menu.addItem('删除', serviceName + '/app/images/mxgraph/clear.png', function () {
                    graph.removeCells([cell]);
                });
            }
        },
        mxGraphCellClicked: function (sender, evt) {
            var model = sender.getModel();
            var cell = evt.getProperty('cell');
            model.beginUpdate();
            if (cell != null && cell.isVertex()) {
                for (var cellId in model.cells) {
                    var curCell = model.getCell(cellId);
                    curCell.customHighlight = false;
                    sender.removeCellOverlays(curCell);
                }
                var overlays = sender.getCellOverlays(cell);
                var overlay = new mxCellOverlay(
                    new mxImage(serviceName + '/app/images/mxgraph/check.png', 16, 16),
                    'Overlay tooltip');
                //增加选中标记
                if (overlays == null) {
                    sender.addCellOverlay(cell, overlay);
                }
                else {
                    sender.removeCellOverlays(cell);
                }
                //高亮
                if (cell.isVertex()) {
                    var edgeCounts = cell.getEdgeCount();
                    for (var index = 0; index < edgeCounts; index++) {
                        var edge = cell.getEdgeAt(index);
                        MXClientHelper.customHighlight(edge);
                        if (edge.target == cell) {
                            //sender.addCellOverlay(edge, overlay);
                        }
                    }
                }
                evt.consume();
            }
            model.endUpdate();
            sender.refresh();
        },
        customHighlight: function (edge) {
            var source = edge.source;
            var target = edge.target;
            if (!edge.customHighlight) {
                source.customHighlight = true;
                target.customHighlight = true;
                edge.customHighlight = true;
            }
        },
        /**
         * 清空画布 绘制 节点及连线信息
         * @param graph
         */
        doDraw: function (graph, nodes, relations, isShowColor) {
            var SIZE = {x: 160, y: 90};
            var parent = graph.getDefaultParent();
            graph.getModel().beginUpdate();
            try {
                //remove all
                var existsCells = [];
                var model = graph.getModel();
                for (var cellId in model.cells) {
                    var curCell = model.getCell(cellId);
                    if (curCell.isVertex()) {
                        existsCells.push(curCell);
                    }
                }
                graph.removeCells(existsCells, true);
                //add new
                var cells = {};
                for (var i = 0; i < nodes.length; i++) {
                    if (!isShowColor) {
                        nodes[i].techStatus = null;
                    }
                    var techStatus = nodes[i].techStatus;
                    var vertexText = MXClientHelper.generateVertexText(nodes[i]);
                    var v = graph.insertVertex(parent, nodes[i].id, vertexText, 0, 0, SIZE.x, SIZE.y, 'fillColor=' + MXClientHelper.getColorByStatus(techStatus));
                    v.dbDesc = nodes[i];
                    cells[nodes[i].id] = v;
                }

                var connected = {};
                for (var i = 0; i < relations.length; i++) {
                    var srcId = relations[i].srcId;
                    var destId = relations[i].destId;
                    if (cells[srcId] && cells[destId]) {
                        graph.insertEdge(parent, null, '', cells[srcId], cells[destId]);
                        connected[srcId] = true;
                        connected[destId] = true;
                    }
                }

                //对孤立节点进行处理
                var unconnected = [];
                for (var id in cells) {
                    if (!connected[id]) {
                        unconnected.push(cells[id]);
                    }
                }
                var unconroot = graph.insertVertex(parent, 'unconroot', '', 0, 0, 0, 0, 'fillColor=white');
                for (var i = 0; i < unconnected.length; i++) {
                    var e = graph.insertEdge(parent, null, '', unconroot, unconnected[i]);
                    e.visible = false;
                }
                graph.layout.execute(parent);
                //keep layoput and remove unconroot
                graph.removeCells([unconroot], true);
            }
            finally {
                graph.getModel().endUpdate();
                //自适应
                if (mxClient.IS_IE) {
                    graph.resizer.resize();
                }
                //自适应
                //graph.fit();
                //var sc = graph.getView().getScale();
                //graph.zoomTo(Math.round(sc/2));
            }
        },
        getColorByStatus: function (status) {
            if (!status) {
                return "white";
            }
            else if (status == "最新") {
                return "#9ECA89";
            }
            else if (status == "待更新") {
                return "#FFD47C";
            }
            else if (status == "已过时") {
                return "#C1C1C1";
            }
        },
        generateVertexText: function (node) {
            var retVal =
                "<P style='line-height: 180%'>" +
                node.name + "<br/>" +
                "(当前" + node.version + (node.tips ? "," + node.tips : "") + ")，" + node.updateUser + "<br/>" +
                node.updateTime + "<br/>" +
                "<img src=\"" + serviceName;
            if (node.techStatus == "最新") {
                retVal += "/app/images/mxgraph/status_newest.png\" width=\"73\" height=\"20\">";
                if (node.approveTempId) {
                    retVal += "（已审批）</P>";
                } else {
                    retVal += "（未经审批）</P>";
                }
            } else if (node.techStatus == "待更新") {
                retVal += "/app/images/mxgraph/status_toUpdate.png\" width=\"73\" height=\"20\"></P>";
            } else if (node.techStatus == "已过时") {
                retVal += "/app/images/mxgraph/status_outdated.png\" width=\"73\" height=\"20\"></P>";
            } else {
                //retVal += "/app/images/mxgraph/status_default.png\" width=\"73\" height=\"20\"></P>";
            }
            return retVal;
        },
        doAutoLayout: function (graph) {
            graph.getModel().beginUpdate();
            try {
                var parent = graph.getDefaultParent();
                graph.layout.execute(parent);
            }
            finally {
                graph.getModel().endUpdate();

            }
        },
        getRelations: function (graph) {
            var relations = [];
            var model = graph.getModel();
            for (var cellId in model.cells) {
                var curCell = model.getCell(cellId);
                if (curCell.isEdge() && curCell.source && curCell.target) {
                    var relation = {
                        srcId: curCell.source.id,
                        destId: curCell.target.id
                    };
                    relations.push(relation);
                }
            }
            return relations;
        }
    }
})
;
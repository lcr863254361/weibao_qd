/**
 * Created by User on 2020/11/30.
 */
Ext.define('OrientTdm.ProductStructMgr.StructDataShowRegion', {
    alias: 'widget.dataShowRegion',
    extend: 'OrientTdm.Common.Extend.Panel.OrientTabPanel',
    requires: [
        "OrientTdm.Common.Extend.Grid.OrientModelGrid"
    ],
    config: {
        belongFunctionId: ''
    },
    initComponent: function () {
        var me = this;
        Ext.apply(me,
            {
                items: [
                    {
                        title: '简介',
                        iconCls: 'icon-basicInfo',
                        //html: '<h1>数据管理...此处可也添加HTML，介绍功能点主要用途</h1>'
                        html: '<iframe width="100%" height="100%" marginwidth="0" framespacing="0" marginheight="0" frameborder="0" src = "' + serviceName +
                            '/app/views/introduction/DataManage.jsp?"></iframe>'
                    }
                ],
                activeItem: 0
            }
        );
        me.callParent(arguments);
        me.addEvents("initModelDataByNode", 'initModelDataBySchemaNode');
    },
    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'initModelDataByNode', me.initModelDataByNode, me);
        me.mon(me, 'initModelDataBySchemaNode', me._initModelDataBySchemaNode, me);
    },
    _initModelDataBySchemaNode: function (treeNode) {
        var modelId = treeNode.raw.modelId;
        var me = this;
        me.items.each(function (item, index) {
            if (0 != index) {
                me.remove(item);
            }
        });
        var initParams = {
            modelId: modelId,
            isView: 0,
            region: 'center'
        };
        var modelGrid = Ext.create("OrientTdm.Common.Extend.Grid.OrientModelGrid", initParams);
        me.add({
            layout: 'border',
            title: treeNode.get('text'),
            iconCls: treeNode.get('iconCls'),
            items: [
                modelGrid, {
                    xtype: 'orientPanel',
                    region: 'south',
                    collapsible: true,
                    collapseMode: 'mini',
                    header: false,
                    collapsed: true,
                    layout: 'fit',
                    listeners: {
                        beforeshow: function () {

                        }
                    }
                }
            ]
        });
        me.setActiveTab(1);
    },
    initModelDataByNode: function (treeNode) {
        var me = this;
        var tbomModels = treeNode.raw.tBomModels;
        me.items.each(function (item, index) {
            if (0 != index) {
                me.remove(item);
            }
        });
        if (tbomModels.length == 0) {
            //保留首页
        } else {
            //保留首页
            Ext.each(tbomModels, function (tbomMode) {
                var initParams = {
                    modelId: tbomMode.modelId,
                    isView: tbomMode.type,
                    customerFilter: [tbomMode.defaultFilter],
                    templateId: tbomMode.templateId,
                    bindNode: treeNode,
                    region: 'center',
                    usePage: tbomMode.usePage,
                    pageSize: tbomMode.pageSize,
                    isOnlyShow: me.isOnlyShow
                };
                var modelGrid;
                if (!Ext.isEmpty(tbomMode.templateJS)) {
                    modelGrid = Ext.create(tbomMode.templateJS, initParams);
                } else {
                    modelGrid = Ext.create("OrientTdm.Common.Extend.Grid.OrientModelGrid", initParams);
                }
                if ((tbomMode.modelName === '产品结构系统' && treeNode.raw.level === 1) || (tbomMode.modelName === '产品结构设备' && treeNode.raw.level === 2)
                    || (tbomMode.modelName === '产品结构设备' && treeNode.raw.level === 3) || (tbomMode.modelName === '产品结构零部件' && treeNode.raw.level === 4)
                    || (tbomMode.modelName === '产品结构设备实例' && treeNode.raw.level === 4) || (tbomMode.modelName === '产品结构零部件' && treeNode.raw.level === 5)) {
                    //获取型号Id
                    if (me.isOnlyShow) {
                        if (!Ext.isEmpty(tbomMode.templateJS)) {
                            if (tbomMode.modelName !== '产品结构设备实例' && (treeNode.raw.level !== 4||treeNode.raw.level !== 5)) {
                                //隐藏产品配置的按钮
                                modelGrid.modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0].hide();
                                modelGrid.modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[1].hide();
                                modelGrid.modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[4].hide();
                                // modelGrid.modelGrid.getDockedItems()[0].hidden = true;
                            }
                        } else {
                            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0].hide();
                            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[1].hide();
                            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[4].hide();
                            // modelGrid.getDockedItems()[0].hidden = true;
                        }
                    } else {
                        //产品结构中隐藏产品结构设备实例
                        if (tbomMode.modelName === '产品结构设备实例' && treeNode.raw.level === 4) {
                            return;
                        }
                    }
                }
                //刷新时 同时刷新右侧树
                modelGrid.on("customRefreshGrid", function (useQueryFilter) {
                    if (me.ownerCt.westPanel) {
                        me.ownerCt.westPanel.fireEvent("refreshCurrentNode", useQueryFilter);
                    }
                });


                //tree信息和grid匹配以便新增时使用
                var treeInfo = treeNode.raw;
                if (treeInfo.nodeType != "static_node") {
                    var val = treeInfo.idList[0];
                    var modelId = treeInfo.modelId;
                    var modelDesc = modelGrid.modelDesc;
                    if (typeof modelDesc != "undefined" && modelDesc) {
                        var modelCols = modelDesc.columns;
                        var refCnt = 0;
                        for (var i = 0; i < modelCols.length; i++) {
                            if (modelCols[i].type != "C_Relation") {
                                continue;
                            }
                            var refModelId = modelCols[i].refModelId;
                            if (refModelId == modelId) {
                                var colName = modelCols[i].sColumnName;
                                var refValue = {
                                    name: treeInfo.text,
                                    id: val
                                };
                                var oriData = {};
                                oriData[colName + '_display'] = Ext.encode([refValue]);
                                oriData[colName] = val;
                                modelGrid.formInitData = oriData;
                                refCnt++;
                            }
                        }
                        if (refCnt > 1) {
                            modelGrid.formInitData = null;
                        }
                    }
                    else {
                        //没有实时加载modelDesc时
                    }
                }

                me.add({
                    layout: 'border',
                    title: tbomMode.modelName,
                    iconCls: treeNode.get('iconCls'),
                    items: [
                        modelGrid, {
                            xtype: 'orientPanel',
                            region: 'south',
                            collapsible: true,
                            collapseMode: 'mini',
                            header: false,
                            collapsed: true,
                            layout: 'fit',
                            listeners: {
                                beforeshow: function () {

                                }
                            }
                        }
                    ]
                });
            });
            me.setActiveTab(1);
        }
    }
});
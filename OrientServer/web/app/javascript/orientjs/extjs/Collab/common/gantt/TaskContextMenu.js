//extended context menu, color picker added
Ext.define('OrientTdm.Collab.common.gantt.TaskContextMenu', {
    extend: 'Gnt.plugin.TaskContextMenu',
    createMenuItems: function () {
        return [{
            handler: this.deleteTask,
            requiresTask: true,
            itemId: 'deleteTask',
            text: '删除'
        },
            {
                handler: this.toggleMilestone,
                requiresTask: true,
                itemId: 'toggleMilestone',
                text: '转化为里程碑'
            },
            {
                text: '新增',
                itemId: 'add',
                menu: {
                    plain: true,
                    defaults: {
                        scope: this
                    },
                    items: [
                        {
                            handler: this.customAddTaskBelowAction,
                            text: '增加工作包'
                        },
                        {
                            handler: this.addMilestone,
                            requiresTask: true,
                            text: '增加里程碑'
                        },
                        {
                            handler: function () {
                                this.addSubWorkPackage(this.rec);
                            },
                            requiresTask: true,
                            text: '增加子工作包'
                        },
                        {
                            handler: this.addSuccessor,
                            requiresTask: true,
                            text: '增加前驱'
                        },
                        {
                            handler: this.addPredecessor,
                            requiresTask: true,
                            text: '增加后继'
                        }]
                }
            },
            {
                text: '删除依赖关系',
                requiresTask: true,
                itemId: 'deleteDependencyMenu',
                isDependenciesMenu: true,
                menu: {
                    plain: true,
                    listeners: {
                        beforeshow: this.populateDependencyMenu,
                        mouseover: this.onDependencyMouseOver,
                        mouseleave: this.onDependencyMouseOut,
                        scope: this
                    }
                }
            }]
    },
    customAddTaskBelowAction: function () {
        var treePanel = Ext.ComponentQuery.query('navigationPanel')[0];
        var currentNode = treePanel.getCurrentNode();
        this.addWorkPackage(TDM_SERVER_CONFIG.PLAN, currentNode.getId());
    },
    configureMenuItems: function () {
        this.callParent(arguments);
        var rec = this.rec;
        // there can be no record when clicked on the empty space in the schedule
        if (!rec) return;
    },
    addWorkPackage: function (modelName, parentNodeId) {
        var _this = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(modelName, TDM_SERVER_CONFIG.COLLAB_SCHEMA_ID);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, TDM_SERVER_CONFIG.TPL_VIEWBOARD_PLAN);
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/collabNavigation/getGridModelDesc.rdm', {
            modelName: modelName,
            templateId: templateId
        }, true, function (response) {
            var modelDesc = response.decodedData.results.orientModelDesc;
            var createForm = Ext.create('OrientTdm.Common.Extend.Form.OrientAddModelForm', {
                buttonAlign: 'center',
                buttons: [
                    {
                        itemId: 'save',
                        text: '保存',
                        scope: _this,
                        iconCls: 'icon-save',
                        handler: function (btn) {
                            btn.up('form').fireEvent('saveOrientForm', {
                                modelId: modelDesc.modelId,
                                parentNodeId: parentNodeId
                            });
                        }
                    },
                    {
                        itemId: 'back',
                        text: '取消',
                        scope: _this,
                        iconCls: 'icon-close',
                        handler: function (btn) {
                            btn.up('window').close();
                        }
                    }
                ],
                successCallback: function () {
                    //关闭窗口
                    this.up('window').close();
                    //刷新gantt图
                    var ganttPanel = Ext.ComponentQuery.query('designPlanGanttPanel')[0];
                    ganttPanel.taskStore.load();
                    ganttPanel.dependencyStore.load();
                    ganttPanel.fireEvent('cascade');
                },
                actionUrl: serviceName + '/modelData/saveModelData.rdm',
                modelDesc: modelDesc,
                afterInitForm: function () {
                    var createForm = this;
                    Ext.each(this.modelDesc.columns, function (column) {
                        if (column.className == 'DateColumnDesc') {
                            var sColumnName = column.sColumnName;
                            //找到页面元素中对应日期选择的field
                            var field = createForm.down('DateColumnDesc[name=' + sColumnName + ']');
                            if (field) {
                                field.vtype = 'daterange';
                                if (sColumnName.indexOf('START') != -1) {
                                    //选中开始时间后，在结束时间的选择事件中，添加置灰的日期校验
                                    field.addListener('select', function () {
                                        var endField = createForm.down('DateColumnDesc[name=' + sColumnName.replace(/START/, 'END') + ']');
                                        var startDate = field.value;
                                        endField.setMinValue(startDate);
                                    });
                                } else if (sColumnName.indexOf('END') != -1) {
                                    //选中结束时间后，在开始时间的选择事件中，添加置灰的日期校验
                                    field.addListener('select', function () {
                                        var startField = createForm.down('DateColumnDesc[name=' + sColumnName.replace(/END/, 'START') + ']');
                                        var endDate = field.value;
                                        startField.setMaxValue(endDate);
                                    });
                                }
                            }
                        }
                    });
                }
            });

            new Ext.Window({
                title: '新增【<span style="color: red; ">' +modelDesc.text + '</span>】数据',
                width: 0.4 * globalWidth,
                height: 0.8 * globalHeight,
                layout: 'fit',
                autoShow: true,
                items: [
                    createForm
                ],
                listeners: {
                    'beforeshow': function (win) {
                        var items = createForm.items.items[0].items.length;
                        if (items < 3) {
                            win.setHeight(items * 210);
                        }
                        else if (items >= 3 && items <= 7) {
                            win.setHeight(items * 90);
                        }
                        else {
                            win.setHeight(items * 90);
                        }
                    }
                }
            });
        });
    },
    addSubWorkPackage: function (workPackage) {
        var _this = this;
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/designPlanGantt/getPlanNodeId.rdm', {planId: workPackage.getId()}, false, function (resp) {
            _this.addWorkPackage(TDM_SERVER_CONFIG.PLAN, resp.decodedData.results);
        });
    }
});

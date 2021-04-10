Ext.define("OrientTdm.CollabDev.Tools.CollabDevModelCRUDHelper", {
    extend: 'Ext.Base',
    alternateClassName: 'CollabDevModelCRUDHelper',
    statics: {
        createByModelName: function (modelName, currentNode) {
            var _this = this;
            var currentNodeId = currentNode.getId();
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/collabNavigation/getGridModelDesc.rdm', {modelName: modelName}, true, function (response) {
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
                                    parentNodeId: currentNodeId
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
                        this.up('window').close();
                        _this.refreshNode(currentNodeId, false);
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
                                    }
                                    else if (sColumnName.indexOf('END') != -1) {
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
        updateByModelNameNode: function (modelName, currentNode) {
            var _this = this;
            var pNodeId = currentNode.parentNode.getId();
            var dataId = currentNode.getData().bmDataId;
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/collabNavigation/getGridModelDescAndData.rdm', {
                modelName: modelName,
                dataId: dataId
            }, true, function (response) {
                var modelDesc = response.decodedData.results.orientModelDesc;
                var originalData = response.decodedData.results.modelData;
                var updateForm = Ext.create("OrientTdm.Common.Extend.Form.OrientModifyModelForm", {
                    buttonAlign: 'center',
                    buttons: [
                        {
                            itemId: 'save',
                            text: '保存',
                            scope: _this,
                            iconCls: 'icon-saveSingle',
                            handler: function (btn) {
                                btn.up('form').fireEvent('saveOrientForm', {
                                    modelId: modelDesc.modelId
                                });
                            }
                        },
                        {
                            itemId: 'back',
                            text: '关闭',
                            iconCls: 'icon-back',
                            scope: _this,
                            handler: function (btn) {
                                btn.up('window').close();
                            }
                        }
                    ],
                    successCallback: function () {
                        this.up('window').close();
                        _this.refreshNode(pNodeId, false);
                    },
                    actionUrl: serviceName + '/modelData/updateModelData.rdm',
                    modelDesc: modelDesc,
                    originalData: originalData
                });
                new Ext.Window({
                    title: '修改【<span style="color: red; ">' +modelDesc.text + '</span>】数据',
                    width: 0.4 * globalWidth,
                    height: 0.8 * globalHeight,
                    layout: 'fit',
                    autoShow: true,
                    items: [
                        updateForm
                    ],
                    listeners: {
                        'beforeshow': function (win) {
                            var items = updateForm.items.items[0].items.length - 1;
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
        deleteByModelNameNode: function (modelName, currentNode) {
            var _this = this;
            var pNodeId = currentNode.parentNode.getId();
            var dataId = currentNode.getData().bmDataId;
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/collabNavigation/deleteNode.rdm', {
                modelName: modelName,
                toDelIds: dataId
            }, true, function () {
                _this.refreshNode(pNodeId, false);
            });
        },
        getModelNameByType: function (type) {
            if ('dir' === type) {
                return TDM_SERVER_CONFIG.DIRECTORY;
            } else if ('prj' == type) {
                return TDM_SERVER_CONFIG.PROJECT;
            } else if ('plan' == type) {
                return TDM_SERVER_CONFIG.PLAN;
            } else if ('task' == type) {
                return TDM_SERVER_CONFIG.TASK;
            }
        }
    }
})
;
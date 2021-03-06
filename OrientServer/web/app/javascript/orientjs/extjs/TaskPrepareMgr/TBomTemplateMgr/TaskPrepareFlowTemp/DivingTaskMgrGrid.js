if (typeof jQuery !== "undefined" && typeof saveAs !== "undefined") {
    (function ($) {
        $.fn.wordExport3 = function (fileName) {
            fileName = typeof fileName !== 'undefined' ? fileName : "jQuery-Word-Export";
            var static = {
                mhtml: {
                    top: "Mime-Version: 1.0\nContent-Base: " + location.href + "\nContent-Type: Multipart/related; boundary=\"NEXT.ITEM-BOUNDARY\";type=\"text/html\"\n\n--NEXT.ITEM-BOUNDARY\nContent-Type: text/html; charset=\"utf-8\"\nContent-Location: " + location.href + "\n\n<!DOCTYPE html>\n<html>\n_html_</html>",
                    head: "<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n<style>\n_styles_\n</style>\n</head>\n",
                    body: "<body>_body_</body>"
                }
            };
            var options = {
                maxWidth: 624
            };
            // Clone selected element before manipulating it
            var markup = $(this).clone();

            // Remove hidden elements from the output
            markup.each(function () {
                var self = $(this);
                if (self.is(':hidden'))
                    self.remove();
            });

            // Embed all images using Data URLs
            var images = Array();
            var img = markup.find('img');
            for (var i = 0; i < img.length; i++) {
                // Calculate dimensions of output image
                var w = Math.min(img[i].width, options.maxWidth);
                var h = img[i].height * (w / img[i].width);
                // Create canvas for converting image to data URL
                var canvas = document.createElement("CANVAS");
                canvas.width = w;
                canvas.height = h;
                // Draw image to canvas
                var context = canvas.getContext('2d');
                context.drawImage(img[i], 0, 0, w, h);
                // Get data URL encoding of image
                var uri = canvas.toDataURL("image/png");
                $(img[i]).attr("src", img[i].src);
                img[i].width = w;
                img[i].height = h;
                // Save encoded image to array
                images[i] = {
                    type: uri.substring(uri.indexOf(":") + 1, uri.indexOf(";")),
                    encoding: uri.substring(uri.indexOf(";") + 1, uri.indexOf(",")),
                    location: $(img[i]).attr("src"),
                    data: uri.substring(uri.indexOf(",") + 1)
                };
            }

            // Prepare bottom of mhtml file with image data
            var mhtmlBottom = "\n";
            for (var i = 0; i < images.length; i++) {
                mhtmlBottom += "--NEXT.ITEM-BOUNDARY\n";
                mhtmlBottom += "Content-Location: " + images[i].location + "\n";
                mhtmlBottom += "Content-Type: " + images[i].type + "\n";
                mhtmlBottom += "Content-Transfer-Encoding: " + images[i].encoding + "\n\n";
                mhtmlBottom += images[i].data + "\n\n";
            }
            mhtmlBottom += "--NEXT.ITEM-BOUNDARY--";

            //TODO: load css from included stylesheet
            var styles = "";

            // Aggregate parts of the file together
            var fileContent = static.mhtml.top.replace("_html_", static.mhtml.head.replace("_styles_", styles) + static.mhtml.body.replace("_body_", markup.html())) + mhtmlBottom;

            // Create a Blob with the file contents
            var blob = new Blob([fileContent], {
                type: "application/msword;charset=utf-8"
            });
            saveAs(blob, fileName + ".doc");
        };
    })(jQuery);
} else {
    if (typeof jQuery === "undefined") {
        console.error("jQuery Word Export: missing dependency (jQuery)");
    }
    if (typeof saveAs === "undefined") {
        console.error("jQuery Word Export: missing dependency (FileSaver.js)");
    }
}

/**
 * Created by User on 2019/1/28.
 */

Ext.define('OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.DivingTaskMgrGrid', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.divingTaskMgrGrid',

    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_DIVING_TASK,
        modelName: TDM_SERVER_CONFIG.DIVING_TASK
    },

    initComponent: function () {
        var me = this;
        var schemaId = me.schemaId;
        var isHistoryTaskSearch = me.isHistoryTaskSearch;
        var hangduanTimeData = [];
        var params = {
            hangduanId: me.flowId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/getHangduanTime.rdm', params, false, function (resp) {
            hangduanTimeData = resp.decodedData.results
        });

        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var isScientist = me.isScientist;
        var customerFilter = new CustomerFilter('T_HANGDUAN_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', me.flowId);
        var customerFilters = [];
        customerFilters.push(customerFilter);
        if (me.isHistoryTaskSearch) {
            var customerFilterTask = new CustomerFilter('C_STATE_' + modelId, CustomerFilter.prototype.SqlOperation.Equal, '', "?????????");
            customerFilters.push(customerFilterTask);
        }
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter: customerFilters,
            flowId: me.flowId,
            //createUrl: serviceName + "/taskPrepareController/saveDivingTaskData.rdm?flowId=" + me.flowId,
            queryUrl: serviceName + "/taskPrepareController/getDivingTaskData.rdm",
            updateUrl: serviceName + "/taskPrepareController/updateDivingTaskData.rdm",
            afterInitComponent: function () {
                var me = this;
                //??????
                // this.getStore().sort([{
                //     "property": "C_TASK_NAME_" + modelId,
                //     "direction": "ASC"
                // }]);
                var toolbar = me.dockedItems[0];
                var addButton = toolbar.child('[text=??????]');
                if (addButton) {
                    Ext.Function.interceptAfter(addButton, 'handler', function (button) {
                        //????????????????????????????????????????????????
                        var customPanel = button.orientBtnInstance.customPanel;
                        if (customPanel) {
                            var planStartTimeField = customPanel.down('[name=C_PLAN_START_TIME_' + modelId + ']');
                            var planEndTimeField = customPanel.down('[name=C_END_TIME_' + modelId + ']');
                            if (hangduanTimeData.length = 2) {
                                planStartTimeField.setMinValue(hangduanTimeData[0]);
                                planStartTimeField.setMaxValue(hangduanTimeData[1]);
                                planEndTimeField.setMinValue(hangduanTimeData[0]);
                                planEndTimeField.setMaxValue(hangduanTimeData[1]);
                            }
                        }
                    });
                }
                var modifyButton = toolbar.child('[text=??????]');
                if (modifyButton) {
                    Ext.Function.interceptAfter(modifyButton, 'handler', function (button) {
                        //????????????????????????????????????????????????
                        var customPanel = button.orientBtnInstance.customPanel;
                        if (customPanel) {
                            var planStartTimeField = customPanel.down('[name=C_PLAN_START_TIME_' + modelId + ']');
                            var planEndTimeField = customPanel.down('[name=C_END_TIME_' + modelId + ']');
                            if (hangduanTimeData.length = 2) {
                                planStartTimeField.setMinValue(hangduanTimeData[0]);
                                planStartTimeField.setMaxValue(hangduanTimeData[1]);
                                planEndTimeField.setMinValue(hangduanTimeData[0]);
                                planEndTimeField.setMaxValue(hangduanTimeData[1]);
                            }
                        }
                    });
                }
                toolbar.add({
                        iconCls: 'icon-create',
                        text: '????????????',
                        handler: function () {
                            var selections = modelGrid.getSelectionModel().getSelection();
                            if (selections.length === 0) {
                                OrientExtUtil.Common.tip(OrientLocal.prompt.info, "???????????????????????????????????????");
                            } else if (selections.length > 1) {
                                OrientExtUtil.Common.tip(OrientLocal.prompt.info, "???????????????????????????????????????");
                            } else {
                                // Ext.Msg.wait('??????','??????????????????????????????...');
                                Ext.getBody().mask("??????????????????????????????...", "x-mask-loading");
                                Ext.Ajax.request({
                                    url: serviceName + '/taskPrepareController/easyDivingTask.rdm',
                                    params: {
                                        taskId: selections[0].raw.ID,
                                        hangduanId: me.flowId
                                    },
                                    success: function (response) {
                                        // Ext.Msg.hide();
                                        Ext.getBody().unmask();
                                        var gridPanel = me.up("panel");
                                        gridPanel.down("panel").store.reload();
                                    }
                                });
                            }
                        }
                    }, {
                        iconCls: 'icon-export',
                        text: '?????????????????????',
                        handler: function () {
                            // Ext.Msg.wait('??????', '??????????????????????????????...');
                            Ext.getBody().mask("??????????????????????????????...", "x-mask-loading");
                            Ext.Ajax.request({
                                url: serviceName + '/accountingForm/generateBalanceHtml.rdm',
                                params: {
                                    hangduanId: me.flowId
                                },
                                success: function (response) {
                                    $("#exportBalanceWord").remove();
                                    $("body").append('<div id="exportBalanceWord" style="display: none" >' + response.decodedData + '</div>')
                                    setTimeout(() => {
                                        $("#exportBalanceWord").wordExport3('?????????????????????')
                                        Ext.getBody().unmask();
                                    }, 500)
                                }
                            });
                        }
                    }, {
                        iconCls: 'icon-import',
                        text: '???????????????????????????',
                        handler: function () {
                            var selections = modelGrid.getSelectionModel().getSelection();
                            if (selections.length === 0) {
                                OrientExtUtil.Common.tip(OrientLocal.prompt.info, "???????????????????????????????????????");
                            } else if (selections.length > 1) {
                                OrientExtUtil.Common.tip(OrientLocal.prompt.info, "???????????????????????????????????????");
                            } else {
                                var divingTaskId=selections[0].raw.ID;
                                var win = Ext.create('Ext.Window', {
                                    title: '???????????????????????????',
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
                                            fieldLabel: '???????????????????????????(.zip)',
                                            buttonConfig: {
                                                iconCls: 'icon-upload'
                                            },
                                            listeners: {
                                                'change': function (fb, v) {
                                                    if (v.substr(v.length - 3) != "zip" && v.substr(v.length - 3) != "xls" && v.substr(v.length - 4) != "xlsx") {
                                                        OrientExtUtil.Common.info('??????', '?????????zip????????????');
                                                        return;
                                                    }
                                                }
                                            }
                                        }]
                                    }],
                                    buttons: [{
                                        text: '??????',
                                        handler: function () {
                                            var form = win.down("form").getForm();
                                            if (form.isValid()) {
                                                form.submit({
                                                    //???????????????????????????
                                                    url: serviceName + '/taskPrepareController/importCheckTableResultData.rdm?divingTaskId=' + divingTaskId,
                                                    waitMsg: '????????????...',
                                                    success: function (form, action) {
                                                        OrientExtUtil.Common.info('??????', action.result.msg, function () {
                                                            win.close();
                                                        });
                                                    },
                                                    failure: function (form, action) {
                                                        OrientExtUtil.Common.info('??????', action.result.msg, function () {
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
                        }
                    }
                );
                //toolbar.add({
                //    iconCls: 'icon-delete',
                //    text: '????????????',
                //    handler: function () {
                //        if (!OrientExtUtil.GridHelper.hasSelected(modelGrid)) {
                //            return;
                //        }
                //        var selectRecords = OrientExtUtil.GridHelper.getSelectedRecord(modelGrid);
                //        var ids = [];
                //        Ext.each(selectRecords, function (s) {
                //            ids.push(s.data.id);
                //        });
                //        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/deleteDivingTaskData.rdm', {
                //            id: ids.toString()
                //        }, false, function (resp) {
                //            var ret = Ext.decode(resp.responseText);
                //            if (ret.success) {
                //                modelGrid.fireEvent('refreshGrid');
                //                Ext.Msg.alert("??????", "???????????????");
                //                Ext.getCmp("tBomTemplateTreeOwner1").doRefresh();
                //            }
                //        })
                //    },
                //    scope: me
                //});
                if (!isHistoryTaskSearch) {
                    me.viewConfig.getRowClass = function (record, rowIndex, rowParams, store) {
                        if (record.data['C_STATE_' + modelId] == '?????????') {
                            return 'x-grid-record-grey';
                        } else if (record.data['C_STATE_' + modelId] == '?????????') {
                            return 'x-grid-record-orange';
                        } else if (record.data['C_STATE_' + modelId] == '?????????') {
                            return 'x-grid-record-green';
                        }
                    }
                }
            }
        });
        //??????Grid??? ?????????????????????
        modelGrid.on("customRefreshGrid", function (useQueryFilter) {
            var westPanel = me.ownerCt.ownerCt.ownerCt.westPanel;
            if (westPanel) {
                westPanel.fireEvent("refreshCurrentNode", useQueryFilter);
            }
        });
        var columns = modelGrid.columns;
        if (Ext.isArray(columns)) {
            var recordTaskIds = [];
            columns.push(
                {
                    xtype: 'actioncolumn',
                    text: '????????????',
                    align: 'center',
                    width: 200,
                    // id: 'actionColumn',
                    items: [{
                        iconCls: 'icon-NORMAL',
                        tooltip: '????????????',
                        handler: function (grid, rowIndex) {
                            var data = grid.getStore().getAt(rowIndex);
                            me.taskId = data.raw.ID;
                            me.taskName = data.raw['C_TASK_NAME_' + modelId];
                            me.taskEndState = data.raw['C_STATE_' + modelId];
                            me.seaArea = data.raw['C_SEA_AREA_' + modelId];
                            me.weidu = data.raw['C_WEIDU_' + modelId];
                            me.jingdu = data.raw['C_JINGDU_' + modelId];
                            me.planDivingDepth = data.raw['C_PLAN_DIVING_DEPTH_' + modelId];
                            // if (me.taskEndState !== '?????????') {
                            //
                            //     //Ext.getCmp('actionColumn').setTooltip('????????????');
                            //
                            //     //????????????????????????
                            //     var createWin = Ext.create('Ext.Window', {
                            //         title: '????????????????????????',
                            //         plain: true,
                            //         height: 0.5 * globalHeight,
                            //         width: 0.5 * globalWidth,
                            //         layout: 'fit',
                            //         maximizable: true,
                            //         modal: true,
                            //         id: 'selectPostWindow',
                            //         items: [Ext.create('OrientTdm.TaskPrepareMgr.Center.TabPanel.PostPersonMgrPanel', {
                            //             taskId: me.taskId,
                            //             id: 'postPersonMgrPanelowner',
                            //             taskEndState: me.taskEndState
                            //         })],
                            //         buttonAlign: 'center',
                            //         buttons: [
                            //             {
                            //                 text: '??????',
                            //                 iconCls: 'icon-close',
                            //                 handler: function () {
                            //                     this.up('window').close();
                            //                 }
                            //             }
                            //         ]
                            //     });
                            //     createWin.show();
                            // }

                            console.log(me.taskId);
                            //var centerPanel = grid.ownerCt.up("taskPrepareManagerDashboard").centerPanel;
                            // var centerPanel = Ext.getCmp("taskPrepareCenterPanel");
                            var centerPanel = me.ownerCt.ownerCt.ownerCt.centerPanel;
                            centerPanel.items.each(function (item, index) {
                                centerPanel.remove(item);
                            });
                            var panel = Ext.create('OrientTdm.TaskPrepareMgr.MxGraphEditor.MxGraphEditorPanel', {
                                region: 'center',
                                taskId: me.taskId,
                                //?????????????????? ????????????
                                isStatusSearch: me.isStatusSearch,
                                flowId: me.flowId,
                                taskEndState: me.taskEndState,
                                isHistoryTaskSearch: isHistoryTaskSearch
                                //treeNode: treeNode
                            });
                            var teststageTabPanel = Ext.create('OrientTdm.TaskPrepareMgr.Center.TabPanel.SetsMgrStageTabPanel', {
                                layout: 'fit',
                                height: '45%',
                                region: 'south',
                                animCollapse: true,
                                collapsible: true,
                                isStatusSearch: me.isStatusSearch,
                                taskId: me.taskId,
                                taskEndState: me.taskEndState
                                //nodeId: me.nodeId,
                                //nodeText: me.nodeText
                            });
                            centerPanel.add({
                                title: '????????????',
                                //iconCls: record.raw['iconCls'],
                                layout: 'border',
                                items: [panel, teststageTabPanel],
                                panel: panel,
                                stageTabPanel: teststageTabPanel
                            });
                            // var taskSkillPanel = Ext.create('OrientTdm.ProductStructureMgr.SkillDocumentMgr', {
                            //     // title: '????????????',
                            //     region: 'center',
                            //     taskId: me.taskId,
                            //     productId: "",
                            //     hangduanId: me.flowId,
                            //     flowId: '',
                            //     taskEndState: me.taskEndState
                            // });
                            // centerPanel.add(
                            //     {
                            //         title: '????????????',
                            //         layout: 'border',
                            //         items: [taskSkillPanel],
                            //         taskSkillPanel: taskSkillPanel
                            //     }
                            // );

                            var divingPlanTablePanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                                region: 'center',
                                taskId: me.taskId,
                                productId: "",
                                hangduanId: me.flowId,
                                flowId: '',
                                taskEndState: me.taskEndState,
                                items: [{
                                    layout: "fit",
                                    html: '<iframe  id="planTableIframe" frameborder="0"  style="margin-left: 0px;" src="' + 'accountingForm/getDivingPlanTableData.rdm?taskId=' + me.taskId + '&hangduanId=' + me.flowId
                                        + '"></iframe>'
                                }]
                            });

                            centerPanel.add(
                                {
                                    title: '???????????????',
                                    layout: 'border',
                                    items: [divingPlanTablePanel],
                                    divingPlanPanel: divingPlanTablePanel,
                                    listeners: {
                                        'beforeshow': function () {
                                            if (Ext.get('planTableIframe')) {
                                                window.onresize = function () {
                                                    changeFrameHeight();
                                                };
                                                changeFrameHeight();

                                                function changeFrameHeight() {
                                                    var cwin = document.getElementById('planTableIframe');
                                                    cwin.width = document.documentElement.clientWidth - 250;
                                                    cwin.height = document.documentElement.clientHeight - 140;
                                                    divingPlanTablePanel.setHeight(cwin.height);
                                                    divingPlanTablePanel.setWidth(cwin.width);
                                                }
                                            }
                                            //??????iframe??????
                                            window.parent.document.getElementById('planTableIframe').contentWindow.document.location.reload();
                                        }
                                    }
                                }
                            );

                            var divingDeviceTablePanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                                region: 'center',
                                taskId: me.taskId,
                                productId: "",
                                hangduanId: me.flowId,
                                flowId: '',
                                taskEndState: me.taskEndState,
                                items: [{
                                    layout: "fit",
                                    // html:'<iframe  frameborder="0" width="800" height="800" style="margin-left: 300px" src="'+'app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/divingDeviceWeight.jsp?hangduanId='+me.flowId+ '&taskId=' + me.taskId
                                    //     +'"></iframe>'
                                    // }]

                                    html: '<iframe  id="divingDeviceIframe" frameborder="0" width="50%" height="80%" style="margin-left: 0px" src="' + 'accountingForm/getDivingDeviceData.rdm?taskId=' + me.taskId + '&hangduanId=' + me.flowId
                                        + '"></iframe>'
                                }]
                            });
                            centerPanel.add(
                                {
                                    title: '???????????????',
                                    layout: 'border',
                                    items: [divingDeviceTablePanel],
                                    divingDeviceTablePanel: divingDeviceTablePanel,
                                    listeners: {
                                        'beforeshow': function () {
                                            if (Ext.get('divingDeviceIframe')) {
                                                window.onresize = function () {
                                                    changeFrameHeight();
                                                };
                                                changeFrameHeight();

                                                function changeFrameHeight() {
                                                    var cwin = document.getElementById('divingDeviceIframe');
                                                    cwin.width = document.documentElement.clientWidth - 250;
                                                    cwin.height = document.documentElement.clientHeight - 140;
                                                    divingDeviceTablePanel.setHeight(cwin.height);
                                                    divingDeviceTablePanel.setWidth(cwin.width);
                                                }
                                            }
                                            //??????iframe??????
                                            window.parent.document.getElementById('divingDeviceIframe').contentWindow.document.location.reload();
                                        }
                                    }
                                }
                            );

                            // var currentOutTemplate = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                            //     region: 'east',
                            //     items: [{
                            //         layout: "fit",
                            //         // html:'<iframe  frameborder="0" width="800" height="800" style="margin-left: 0px" marginwidth="0" src="'+'app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/currentOutTemplate.jsp?hangduanId='+me.flowId+ '&taskId=' + me.taskId
                            //         //     + '&seaArea=' + me.seaArea + '&jingdu=' + me.jingdu+ '&weidu=' + me.weidu+'&planDivingDepth='+me. planDivingDepth
                            //         //     +'"></iframe>'
                            //         html: '<iframe  id="currentOutTemplateIframe" frameborder="0" width="800" height="100%" style="margin-left: 0px "marginwidth="0"  src="' + 'accountingForm/getOutTemplateTable.rdm?taskId=' + me.taskId + '&taskName=' + me.taskName + '&hangduanId=' + me.flowId
                            //             + '"></iframe>'
                            //     }]
                            // });
                            //
                            // var beforeOutTemplate = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                            //     layout: 'absolute',
                            //     region: 'west',
                            //     split: true,
                            //     items: [{
                            //         layout: "fit",
                            //         // html:'<iframe  frameborder="0" width="800" height="800" style="margin-left: 0px" src="'+'app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/preOutTemplate.jsp?hangduanId='+me.flowId+ '&taskId=' + me.taskId
                            //         //     + '&seaArea=' + me.seaArea + '&jingdu=' + me.jingdu+ '&weidu=' + me.weidu+'&planDivingDepth='+me. planDivingDepth
                            //         //     +'"></iframe>'
                            //         html: '<iframe  id="preOutTemplateIframe" frameborder="0" width="800" height="100%" style="margin-left: 0px " src="' + 'accountingForm/getDivingTypeData.rdm?hangduanId=' + me.flowId
                            //             + '"></iframe>'
                            //     }]
                            // });
                            //
                            // centerPanel.add({
                            //     title: '??????????????????',
                            //     // iconCls: record.raw['iconCls'],
                            //     layout: 'border',
                            //     items: [beforeOutTemplate, currentOutTemplate],
                            //     listeners: {
                            //         'beforeshow': function () {
                            //             if (Ext.get('currentOutTemplateIframe') || Ext.get('preOutTemplateIframe')) {
                            //                 window.onresize = function () {
                            //                     changeFrameHeight();
                            //                 };
                            //                 changeFrameHeight();
                            //
                            //                 function changeFrameHeight() {
                            //                     var cwin = document.getElementById('currentOutTemplateIframe');
                            //                     cwin.height = document.documentElement.clientHeight - 140;
                            //                     cwin.width = document.documentElement.clientWidth / 2 - 100;
                            //                     currentOutTemplate.setHeight(cwin.height);
                            //                     currentOutTemplate.setWidth(cwin.width);
                            //                     var preCwin = document.getElementById('preOutTemplateIframe');
                            //                     preCwin.height = document.documentElement.clientHeight - 140;
                            //                     preCwin.width = document.documentElement.clientWidth / 2 - 150;
                            //                     beforeOutTemplate.setHeight(preCwin.height);
                            //                     beforeOutTemplate.setWidth(preCwin.width);
                            //                 }
                            //             }
                            //             window.parent.document.getElementById('currentOutTemplateIframe').contentWindow.document.location.reload();
                            //             window.parent.document.getElementById('preOutTemplateIframe').contentWindow.document.location.reload();
                            //         }
                            //     }
                            // });

                            var currentOutTemplateGridPanel = Ext.create('OrientTdm.TaskPrepareMgr.Accounting.CurrentOutTemplateGridPanel', {
                                region: 'center',
                                taskId: me.taskId,
                                taskName: me.taskName,
                                hangduanId: me.flowId
                            });

                            centerPanel.add(
                                {
                                    title: '??????????????????',
                                    layout: 'border',
                                    items: [currentOutTemplateGridPanel],
                                    currentOutTemplateGridPanel: currentOutTemplateGridPanel,
                                    listeners: {
                                        'beforeshow': function () {
                                            currentOutTemplateGridPanel.down('grid').store.load();
                                        }
                                    }
                                }
                            );

                            var divingReportPanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                                region: 'center',
                                split: true,
                                items: [{
                                    layout: "fit",
                                    // html:'<iframe  id="divingReportIframe" frameborder="0" width="950" height="800" style="margin-left: 300px" src="'+'app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/divingReport.jsp?hangduanId='+me.flowId+ '&taskId=' + me.taskId
                                    //     + '&seaArea=' + me.seaArea + '&jingdu=' + me.jingdu+ '&weidu=' + me.weidu+'&planDivingDepth='+me. planDivingDepth
                                    //     +'"></iframe>'
                                    html: '<iframe  id="divingReportIframe" frameborder="0" width="100%" height="100%" style="margin-left: 0px" src="' + 'accountingForm/getDivingReportData.rdm?taskId=' + me.taskId + '&taskName=' + me.taskName
                                        + '"></iframe>'
                                }]
                            });
                            // centerPanel.add({
                            //     title: '????????????',
                            //     layout: 'border',
                            //     items: [divingReportPanel],
                            //     listeners: {
                            //         'beforeshow': function () {
                            //             if (Ext.get('divingReportIframe')) {
                            //                 window.onresize = function () {
                            //                     changeFrameHeight();
                            //                 };
                            //                 changeFrameHeight();
                            //
                            //                 function changeFrameHeight() {
                            //                     var cwin = document.getElementById('divingReportIframe');
                            //                     cwin.width = document.documentElement.clientWidth - 250;
                            //                     cwin.height = document.documentElement.clientHeight - 148;
                            //                     divingReportPanel.setHeight(cwin.height);
                            //                     divingReportPanel.setHeight(cwin.width);
                            //                     // $('#divingReportWord').css("width", cwin.width + "px");
                            //                     // $('#divingReportWord').css("height", cwin.height + "px");
                            //                 }
                            //             }
                            //             //??????iframe??????
                            //             window.parent.document.getElementById('divingReportIframe').contentWindow.document.location.reload();
                            //         }
                            //     }
                            // });


                            var chartPanel = Ext.create('OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.ChartLinePanel', {
                                region: 'center',
                                height: 800,
                                voyageId: data.raw.ID,
                                layout: 'border',
                                id: 'chartLinePanel',
                                tbar: [
                                    {
                                        xtype: 'button',
                                        text: '????????????',
                                        icon: 'app/images/icons/default/common/attachment.png',
                                        handler: function () {
                                            me.createTemplate(data.raw.ID)
                                        }
                                    }
                                ]
                            });
                            centerPanel.add({
                                title: "??????",
                                layout: 'border',
                                items: [chartPanel]
                            });
                            centerPanel.setActiveTab(0);

                            //modelGrid = Ext.create('Ext.panel.Panel', {
                            //    title: '????????????',
                            //    region: 'center',
                            //    layout: 'border',
                            //    items: [me.stageTabPanel, panel],
                            //    panel: panel,
                            //    stageTabPanel: me.stageTabPanel
                            //});
                            //centerPanel.navigation(centerPanel, 'next');
                            //centerPanel.setActiveTab(1);
                        }
                    }]
                },
                // {
                //     xtype: 'actioncolumn',
                //     text: '????????????',
                //     align: 'center',
                //     width: 200,
                //     items: [{
                //         iconCls: 'icon-detail',
                //         tooltip: '????????????',
                //         handler: function (grid, rowIndex) {
                //             var data = grid.getStore().getAt(rowIndex);
                //             var taskId = data.raw.ID;
                //             me.taskEndState = data.raw['C_STATE_' + modelId];
                //             var centerPanel = Ext.getCmp("taskPrepareCenterPanel");
                //             centerPanel.items.each(function (item, index) {
                //                 centerPanel.remove(item);
                //             });
                //             var taskSkillPanel = Ext.create('OrientTdm.ProductStructureMgr.SkillDocumentMgr', {
                //                 // title: '????????????',
                //                 region: 'center',
                //                 taskId: taskId,
                //                 productId: "",
                //                 hangduanId: me.flowId,
                //                 flowId:'',
                //                 taskEndState: me.taskEndState
                //             });
                //             // var taskSkillTabPanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientTabPanel', {
                //             //     layout: 'fit',
                //             //     height: '45%',
                //             //     region: 'south',
                //             //     animCollapse: false,
                //             //     collapsible: false,
                //             //     header: false,
                //             //     items: [taskSkillPanel]
                //             // });
                //             centerPanel.add(
                //                 {
                //                     title: '????????????',
                //                     //iconCls: record.raw['iconCls'],
                //                     layout: 'border',
                //                     items: [taskSkillPanel],
                //                     taskSkillPanel: taskSkillPanel
                //                 }
                //                 //         taskSkillTabPanel
                //             );
                //             centerPanel.setActiveTab(0);
                //         }
                //     }
                //     ]
                // }
                {
                    xtype: 'gridcolumn',
                    text: '??????',
                    align: 'center',
                    width: 200,
                    hidden: isHistoryTaskSearch,
                    renderer: function (value, metaData, record) {
                        var taskId = record.raw.ID;
                        // if (!Ext.Array.contains(recordTaskIds, taskId)) {

                        if (record.data['C_STATE_' + modelId] == '?????????') {
                            metaData.tdAttr = 'data-qtip="????????????"';
                            metaData.tdStyle = "align:center;";
                            Ext.defer(function () {
                                Ext.widget('button', {
                                    renderTo: taskId,
                                    // dock: 'top',
                                    style: 'margin:0 0 0 0',
                                    // ui: 'footer',
                                    // items: [
                                    //     {
                                    //         xtype: 'button',
                                    iconCls: 'icon-startproject',
                                    // text: '????????????',
                                    height: 'auto',
                                    width: 'auto',
                                    handler: function () {
                                        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/divingTaskBegin.rdm', {
                                            taskId: taskId
                                        }, false, function (resp) {
                                            if (resp.decodedData.success) {
                                                OrientExtUtil.Common.tip('??????', "???????????????????????????");
                                                //??????grid
                                                modelGrid.store.load();
                                            } else {
                                                Ext.Msg.alert("??????", resp.decodedData.msg);
                                            }
                                        });
                                    }
                                    //     }
                                    // ]
                                });
                            }, 50);
                            return Ext.String.format('<div id="{0}"></div>', taskId);
                        } else if (record.data['C_STATE_' + modelId] == '?????????' || record.data['C_STATE_' + modelId] == '?????????') {
                            metaData.tdAttr = 'data-qtip="????????????"';
                            metaData.tdStyle = "align:center;";
                            Ext.defer(function () {
                                Ext.widget('button', {
                                    renderTo: taskId,
                                    // dock: 'top',
                                    style: 'margin:0 0 0 0',
                                    // ui: 'footer',
                                    // items: [
                                    //     {
                                    //         xtype: 'button',
                                    iconCls: 'icon-closeFlow',
                                    // text: '????????????',
                                    height: 'auto',
                                    width: 'auto',
                                    handler: function () {
                                        Ext.Msg.confirm("????????????", "??????????????????",
                                            function (btn) {
                                                if (btn == 'yes') {
                                                    OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/divingTaskEnd.rdm', {
                                                        taskId: taskId
                                                    }, false, function (resp) {
                                                        if (resp.decodedData.success) {
                                                            //??????grid
                                                            modelGrid.store.load();
                                                        } else {
                                                            Ext.Msg.alert("??????", resp.decodedData.msg);
                                                        }
                                                    });
                                                }
                                            });
                                    }
                                    //     }
                                    // ]
                                });
                            }, 50);
                            return Ext.String.format('<div id="{0}"></div>', taskId);
                        }
                        // }
                    }
                }
            )
        }
//??????????????????store/columns?????????????????????????????????grid
        modelGrid.reconfigure(modelGrid.getStore(), columns);
        if (isHistoryTaskSearch) {
            console.log(modelGrid.getDockedItems('toolbar[dock="top"]')[0]);
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0].setVisible(false);
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[1].setVisible(false);
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[4].setVisible(false);
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[5].setVisible(false);
            // modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[6].setVisible(false);
        }
        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    },
    createTemplate: function (refId) {
        console.log(refId)
        var me = this;
        var win = Ext.create('Ext.Window', {
            title: '??????????????????',
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
                    fieldLabel: '????????????(.txt)',
                    buttonConfig: {
                        iconCls: 'icon-upload'
                    }
                }]
            }],
            buttons: [{
                text: '??????',
                handler: function () {
                    var form = win.down("form").getForm();
                    if (form.isValid()) {
                        form.submit({
                            url: serviceName + '/analysis/uploadChartData.rdm?voyageId=' + refId,

                            waitMsg: '?????????????????????',
                            success: function (form, action) {
                                OrientExtUtil.Common.info('??????', action.result.msg, function () {
                                    win.close();
                                    me.fireEvent("refreshTree", false);
                                });
                            },
                            failure: function (form, action) {
                                //alert("?????????????????????")
                                OrientExtUtil.Common.info('??????', action.result.msg, function () {
                                    win.close();
                                    //me.fireEvent("refreshTree",false);
                                });
                            }
                        });
                    }
                }
            }]
        });
        win.show();
    }
})
;
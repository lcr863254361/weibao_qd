if (typeof jQuery !== "undefined" && typeof saveAs !== "undefined") {
    (function($) {
        $.fn.wordExport3 = function(fileName) {
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
            markup.each(function() {
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

Ext.define('OrientTdm.TaskPrepareMgr.Center.TabPanel.SetsMgrStageTabPanel', {
    alias: 'widget.setsMgrStageTabPanel',
    extend: 'OrientTdm.Common.Extend.Panel.OrientTabPanel',
    id:'SetsMgrStageTabPanelOwner',
    requires: [
        "OrientTdm.TaskPrepareMgr.MxGraphEditor.MxGraphEditorPanel"
    ],
    initComponent: function () {
        var me = this;
        me.callParent(arguments);
        me.addEvents("initModelDataByNode", 'initModelDataBySchemaNode');
    },

    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'initModelDataByNode', me.initModelDataByNode, me);
        me.mon(me, 'initModelDataBySchemaNode', me._initModelDataBySchemaNode, me);
    },
    beforeRender: function () {
        var me = this;
        var taskId=me.taskId;
        var attendPostMgrPanel = Ext.create("OrientTdm.TaskPrepareMgr.Center.TabPanel.AttendPostMgrPanel", {
            region: 'center',
            padding: '0 0 0 0',
            readModel:false,
            //nodeId: me.nodeId,
            //nodeText :me.nodeText
            taskId:taskId,
            taskEndState:me.taskEndState
        });
        var checkTableMgrPanel = Ext.create("OrientTdm.TaskPrepareMgr.Center.TabPanel.CheckTableMgrPanel", {
            region: 'center',
            padding: '0 0 0 0',
            //nodeId: me.nodeId,
            //nodeText :me.nodeText
            taskId:taskId,
            taskEndState:me.taskEndState,
            // id: 'centerTabPanelWater_1',
            // tbar: [
            //     { xtype: 'button', text: '导出所有表格word' ,iconCls:'icon-export',handler:function(){
            //             let instanceIds=[]
            //             for(var i=0;i<$("#centerTabPanelWater_1 [data-recordid]").length;i++){
            //                 instanceIds.push($("#centerTabPanelWater_1 [data-recordid]")[i].getAttribute('data-recordid'))
            //             }
            //             console.log(instanceIds)
            //             var params={
            //                 taskId:taskId
            //             };
            //             if (instanceIds.length==0){
            //                 Ext.Msg.alert('提示','当前流程节点下没有表格，暂不支持导出！');
            //                 return;
            //             }
            //             Ext.getBody().mask("请稍后...", "x-mask-loading");
            //             OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CurrentTaskMgr/getCheckTableCaseHtmlList.rdm', params, true, function (response) {
            //                 $("#myWord").remove();
            //                 $("body").append('<div id="myWord" style="display: none" >'+response.decodedData+'</div>')
            //
            //                 setTimeout(()=>{
            //                     $("#myWord").wordExport3('表格信息');
            //                     Ext.getBody().unmask();
            //                 },500)
            //             });
            //         }}]
        });

        var postPersonMgrPanel = Ext.create("OrientTdm.TaskPrepareMgr.Center.TabPanel.PostPersonMgrPanel", {
            region: 'center',
            padding: '0 0 0 0',
            taskId:taskId,
            taskEndState:me.taskEndState
        });

        me.add({
            layout: 'border',
            title: '检查表格',
            //iconCls: treeNode.get('iconCls'),
            items: [
                checkTableMgrPanel
            ],
            checkTableMgrPanel:checkTableMgrPanel,
            listeners: {
                'beforeshow':function (t,n) {
                    console.log(t.ownerCt.ownerCt.panel);
                    var toolbar =t.ownerCt.ownerCt.stageTabPanel.getActiveTab().items.items[0].child(xtype = 'grid').getDockedItems('toolbar[dock="top"]')[0];
                        if (!Ext.isEmpty(t.ownerCt.ownerCt.panel.nodeId)&&me.taskEndState!=="已结束") {
                            toolbar.setDisabled(false);
                        } else{
                        toolbar.setDisabled(true);
                    }
                    t.ownerCt.ownerCt.stageTabPanel.refreshCheckfilePanelByNode(t.ownerCt.ownerCt.panel.nodeId,t.ownerCt.ownerCt.panel.nodeText);
                }
            }
        },{
            layout: 'border',
            title: '参与岗位',
            //iconCls: treeNode.get('iconCls'),
            items: [
                attendPostMgrPanel
            ],
            attendPostMgrPanel:attendPostMgrPanel,
            listeners: {
                'beforeshow':function (t,n) {
                    console.log(t.ownerCt.ownerCt.panel);
                    var toolbar =t.ownerCt.ownerCt.stageTabPanel.getActiveTab().items.items[0].child(xtype = 'grid').getDockedItems('toolbar[dock="top"]')[0];
                        if (!Ext.isEmpty(t.ownerCt.ownerCt.panel.nodeId)&&me.taskEndState!=="已结束") {
                            toolbar.setDisabled(false);
                        } else{
                        toolbar.setDisabled(true);
                    }
                    // t.ownerCt.ownerCt.stageTabPanel.getActiveTab().items.items[0].refreshPostfilePanelByNode(t.ownerCt.ownerCt.stageTabPanel.nodeId,t.ownerCt.ownerCt.stageTabPanel.nodeText);
                    t.ownerCt.ownerCt.stageTabPanel.refreshPostfilePanelByNode(t.ownerCt.ownerCt.panel.nodeId,t.ownerCt.ownerCt.panel.nodeText);
                }
            }
        },{
            layout: 'border',
            title: '参与人员',
            //iconCls: treeNode.get('iconCls'),
            items: [
                postPersonMgrPanel
            ],
            postPersonMgrPanel:postPersonMgrPanel
        });
        me.setActiveTab(0);
    },

    refreshPostfilePanelByNode: function (nodeId, nodeText) {
        var me = this;
        me.nodeId = nodeId;
        me.nodeText = nodeText;

        this.ownerCt.panel.nodeId=this.nodeId;
        this.ownerCt.panel.nodeText=this.nodeText;

        console.log(me.items.items[0].items.items[0].items.items[0].items.items[0]);

        var postFilter = me.addCustomerFilterPost();
        me.items.items[1].items.items[0].items.items[0].items.items[0].getStore().getProxy().setExtraParam('customerFilter', Ext.encode([postFilter]));
        me.items.items[1].items.items[0].items.items[0].items.items[0].getStore().getProxy().setExtraParam('nodeId', me.nodeId);
        me.items.items[1].items.items[0].items.items[0].items.items[0].getStore().getProxy().setExtraParam('nodeText', me.nodeText);
        me.items.items[1].items.items[0].items.items[0].items.items[0].getStore().load();

    },
    refreshCheckfilePanelByNode: function (nodeId, nodeText) {
        var me = this;
        me.nodeId = nodeId;
        me.nodeText = nodeText;

        console.log(this);
        //为了tab页切换过滤检查表数据或岗位数据，此时把nodeId和nodeText放入到teststageTabPanel中
        this.ownerCt.panel.nodeId=this.nodeId;
        this.ownerCt.panel.nodeText=this.nodeText;

        console.log(me.items.items[1].items.items[0].items.items[0].items.items[0]);

        //根据节点和任务id
        var checkFilter = me.addCustomerFilterTable();
        me.items.items[0].items.items[0].items.items[0].items.items[0].getStore().getProxy().setExtraParam('customerFilter', Ext.encode([checkFilter]));
        me.items.items[0].items.items[0].items.items[0].items.items[0].getStore().getProxy().setExtraParam('nodeId', me.nodeId);
        me.items.items[0].items.items[0].items.items[0].items.items[0].getStore().getProxy().setExtraParam('nodeText', me.nodeText);
        me.items.items[0].items.items[0].items.items[0].items.items[0].getStore().load();
    },
    addCustomerFilterPost: function () {
        var me = this;

        var refPostTableName = TDM_SERVER_CONFIG.REF_POST_NODE;
        var schemaId = TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID;
        var modelId = OrientExtUtil.ModelHelper.getModelId(refPostTableName, schemaId);
        var filter = {};
        filter.expressType = "ID";
        filter.modelName = refPostTableName;

        filter.idQueryCondition = {};
        filter.idQueryCondition.params = [me.taskId];
        refPostTableName = refPostTableName + "_" + schemaId;
        filter.idQueryCondition.sql = "SELECT ID FROM " + refPostTableName + " WHERE T_DIVING_TASK_" + schemaId + "_ID=?";
        if (Ext.isEmpty(me.nodeId)) {
            //展示所有
            return filter;
        } else {
            var postSql = "(C_NODE_ID_" + modelId + "='" + me.nodeId + "')";
            filter.idQueryCondition.sql += "AND (" + postSql;
            filter.idQueryCondition.sql += ")";
            return filter;
        }
    },
    addCustomerFilterTable: function () {
        var me = this;

        var refPostTableName = TDM_SERVER_CONFIG.CHECK_TEMP_INST;
        var schemaId = TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID;
        var modelId = OrientExtUtil.ModelHelper.getModelId(refPostTableName, schemaId);
        var filter = {};
        filter.expressType = "ID";
        filter.modelName = refPostTableName;

        filter.idQueryCondition = {};
        filter.idQueryCondition.params = [me.taskId];
        refPostTableName = refPostTableName + "_" + schemaId;
        filter.idQueryCondition.sql = "SELECT ID FROM " + refPostTableName + " WHERE T_DIVING_TASK_" + schemaId + "_ID=?";
        if (Ext.isEmpty(me.nodeId)) {
            //展示所有
            return filter;
        } else {
            var postSql = "(C_NODE_ID_" + modelId + "='" + me.nodeId + "')";
            filter.idQueryCondition.sql += "AND (" + postSql;
            filter.idQueryCondition.sql += ")";
            return filter;
        }
    }
});
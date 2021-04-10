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


/**
 * Created by User on 2019/2/13.
 */
Ext.define('OrientTdm.CurrentTaskMgr.CurrentTaskManagerDashboard', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.currentTaskManagerDashboard',
    initComponent: function () {
        var me = this;

        //var leftPanel=Ext.create('OrientTdm.CurrentTaskMgr.DivingTaskTree',{
        //    collapsible: false,
        //    width:240,
        //    minWidth:240,
        //    maxWidth:350,
        //    region:'west',
        //    isStatusSearch: true,
        //});

        var taskId='';
        var taskName='';
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CurrentTaskMgr/getDivingTaskTreeNodeId.rdm', {}, false, function (response) {
            taskId = response.decodedData.results.id;
            taskName = response.decodedData.results.text;
        });

        var mxGraphEditorPanel = Ext.create('OrientTdm.TaskPrepareMgr.MxGraphEditor.MxGraphEditorPanel', {
            region: 'center',
            taskId: taskId,
            taskName: taskName,
            //颜色表示进度 无工具栏
            isStatusSearch: true
        });


        if (!Ext.isEmpty(taskId)) {
            var centerTabPanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientTabPanel', {
                layout: 'fit',
                height: '45%',
                region: 'south',
                animCollapse: true,
                collapsible: true,
                header: true,
                id: 'centerTabPanelWater',
                // tbar: [
                //     { xtype: 'button', text: '导出所有表格word' ,iconCls:'icon-export',handler:function(){
                //             console.log('xawdw')
                //             console.log($("[data-recordid]"))
                //             let instanceIds=[]
                //             for(var i=0;i<$("#centerTabPanelWater [data-recordid]").length;i++){
                //                 instanceIds.push($("#centerTabPanelWater [data-recordid]")[i].getAttribute('data-recordid'))
                //             }
                //             console.log(instanceIds)
                //             // var params={
                //             //     instanceIds:instanceIds
                //             // }
                //             var params={
                //                 taskId:taskId
                //             };
                //             if (instanceIds.length==0){
                //                 Ext.Msg.alert('提示','没有表格！');
                //                 return;
                //             }
                //             Ext.getBody().mask("请稍后...", "x-mask-loading");
                //             OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CurrentTaskMgr/getCheckTableCaseHtmlList.rdm', params, true, function (response) {
                //                 $("#myWord").remove();
                //                 $("body").append('<div id="myWord" style="display: none" >'+response.decodedData+'</div>')
                //                 setTimeout(()=>{
                //                     $("#myWord").wordExport3('表格信息')
                //                     Ext.getBody().unmask();
                //                 },500)
                //             });
                //         }}]
            });
            var showCheckTablePanel = Ext.create('OrientTdm.CurrentTaskMgr.ShowCheckTablePanel', {
                //layout: 'fit',
                //height: '38%',
                //region: 'south',
                //animCollapse: true,
                //collapsible: true,
                //header: true,
                region: 'center',
                padding: '0 0 0 0',
                taskId: taskId
            });
            centerTabPanel.add({
                layout: 'border',
                title: '检查表格',
                //iconCls: treeNode.get('iconCls'),
                items: [
                    showCheckTablePanel
                ],
                showCheckTablePanel: showCheckTablePanel
            });
            centerTabPanel.setActiveTab(0);

            Ext.apply(this, {
                layout: 'border',
                items: [mxGraphEditorPanel, centerTabPanel],
                mxGraphEditorPanel: mxGraphEditorPanel,
                centerTabPanel: centerTabPanel
            });

        } else {
            Ext.apply(this, {
                layout: 'border',
                items: [mxGraphEditorPanel],
                mxGraphEditorPanel: mxGraphEditorPanel
            });
        }
        me.callParent(arguments);
    }
});
if (typeof jQuery !== "undefined" && typeof saveAs !== "undefined") {
    (function ($) {
        $.fn.wordExport2 = function (fileName) {
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

Ext.define('OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.checkTableCaseHtmlPanel',
    config: {
        instanceId: null
    },
    statics: {
        showPictures: function (picIds) {
            var me = this;
            var globalWidth = Ext.getBody().getViewSize().width;
            var globalHeight = Ext.getBody().getViewSize().height;
            if (!Ext.isEmpty(picIds)) {
                var filePanel = {
                    layout: 'fit',
                    html: '<iframe id="imageViewFrame" width="100%" height="100%" marginwidth="0" framespacing="0" marginheight="0" frameborder="0" src = ""></iframe>',
                    listeners: {
                        render: function () {
                            me.reconfigImagePlugin(picIds);
                        }
                    }
                };
                OrientExtUtil.WindowHelper.createWindow(filePanel, {
                    title: '图片查看',
                    iconCls: 'icon-picture',
                    height: 0.5 * globalHeight,
                    width: 0.5 * globalWidth,
                    buttons: [{
                        text: '关闭',
                        iconCls: 'icon-close',
                        handler: function () {
                            this.up('window').close();
                        }
                    }],
                    buttonAlign: 'center'
                });
            }
        },
        reconfigImagePlugin: function (picIds) {
            var me = this;
            // Ext.query("#imageViewFrame")[0].src = serviceName + '/app/views/file/imageViewByFancybox.jsp?' + 'modelId=' + me.modelId + '&dataId=' + me.dataId + '&fileGroupId=' + me.imageFileGroupId;
            // Ext.query("#imageViewFrame")[0].src = serviceName + '/app/views/file/imageViewByFancybox.jsp?' + 'modelId=3216&dataId=' + picIds + '&fileGroupId=-4';
            Ext.query("#imageViewFrame")[0].src = serviceName + '/app/views/file/localPicturePreview.jsp?' + 'modelId=3216&dataId=' + picIds + '&fileGroupId=-4';
        },
        showCellTroubleRecord: function (cellInstId, isShowByInform) {
            var me = this;
            if (!Ext.isEmpty(cellInstId)) {
                OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CurrentTaskMgr/getTroubleIdByCellId.rdm', {
                    cellInstId: cellInstId
                }, false, function (resp) {
                    var troubleId = JSON.parse(resp.responseText).results;
                    var globalWidth = Ext.getBody().getViewSize().width;
                    var globalHeight = Ext.getBody().getViewSize().height;
                    if (!Ext.isEmpty(troubleId)) {
                        if (!isShowByInform) {
                            var troubleDeviceDetail = Ext.create('OrientTdm.SparePartsMgr.TroubleDeviceMgr.TroubleDeviceDetail', {
                                troubleId: troubleId
                            });
                            //在当前任务中展示
                            OrientExtUtil.WindowHelper.createWindow(troubleDeviceDetail, {
                                title: '故障详情',
                                iconCls: 'icon-detail',
                                height: 0.5 * globalHeight,
                                width: 0.5 * globalWidth
                            });
                        } else {
                            //在通知3中展示
                            var cellTroubleDetailPanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                                region: 'center',
                                troubleId: troubleId,
                                items: [{
                                    layout: "fit",
                                    html: '<iframe  id="troubleDetailIframe" frameborder="0" width="100%" height="100%" style="margin-left: 0px;" src="' + '../informMgr/informGetTroubleCellDetail.rdm?troubleId=' + troubleId
                                        + '"></iframe>'
                                }]
                            });
                            var win = Ext.create('Ext.window.Window', {
                                title: '故障详情',
                                titleAlign: 'center',
                                iconCls: 'icon-detail',
                                height: 0.5 * globalHeight,
                                width: 0.5 * globalWidth,
                                layout: 'fit',
                                maximizable: true,
                                modal: true,
                                constrain: true,   //限制窗口不超出浏览器边界
                                listeners: {
                                    'afterrender': function () {
                                        window.onresize = function () {
                                            if (Ext.getCmp("troubleDetailIframe") == undefined) {
                                                return;
                                            }
                                            changeFrameHeight(cellTroubleDetailPanel, win);
                                        };
                                        changeFrameHeight(cellTroubleDetailPanel, win);
                                    },
                                },
                                buttons: [
                                    {
                                        text: '关闭',
                                        iconCls: 'icon-close',
                                        handler: function () {
                                            this.up('window').close();
                                        }
                                    }
                                ],
                                buttonAlign: 'center',
                                items: [cellTroubleDetailPanel]
                            });
                            win.show();
                        }
                    }
                });
            }
        }
    },
    initComponent: function () {
        var me = this;
        var tableHtml = '';

        var params = {
            instanceId: me.instanceId
        };

        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CurrentTaskMgr/getCheckTableCaseHtml.rdm', params, false, function (response) {
            tableHtml = response.decodedData;
        });

        Ext.apply(me, {
            autoScroll: true,
            tbar: [
                {
                    xtype: 'button', text: '导出word', iconCls: 'icon-export', handler: function () {
                        var title = $("#title").text()
                        console.log("title:" + title)
                        $("#myDiv").wordExport2(title)

                    }
                },
                // { xtype: 'button', text: '打印' ,icon:'app/images/icons/default/common/attachment.png',handler:function(){
                //         $("#myDiv").jqprint()
                //
                //     }},
                {
                    xtype: 'button',
                    text: '导出pdf',
                    icon: 'app/images/icons/default/common/attachment.png',
                    handler: function () {
                        window.open(serviceName + "/CurrentTaskMgr/exportPdf.rdm?instanceId=" + me.instanceId);
                    }
                }
            ],
            items: [
                {
                    xtype: 'fieldset',
                    border: false,
                    title: '',
                    defaults: {anchor: '100%'},
                    collapsible: false,
                    layout: 'anchor',
                    html: tableHtml
                }
            ]
        });
        me.callParent(arguments);

    },
});

function changeFrameHeight(cellTroubleDetailPanel, win) {
    win.center();
    var cwin = document.getElementById('troubleDetailIframe');
    cwin.width = win.getWidth();
    cwin.height = win.getHeight() - 38;
    cellTroubleDetailPanel.setHeight(cwin.height);
    cellTroubleDetailPanel.setWidth(cwin.width);
}



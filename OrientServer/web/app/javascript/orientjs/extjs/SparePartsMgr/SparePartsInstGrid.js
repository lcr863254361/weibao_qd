/**
 * Created by User on 2019/3/9.
 */
Ext.define('OrientTdm.SparePartsMgr.SparePartsInstGrid', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.SparePartsInstGrid',
    id: 'sparePartsInstOwner',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_SPARE_PARTS_INST,
        modelName: TDM_SERVER_CONFIG.SPARE_PARTS_INST,
        spareId: null,
        spareName: null,
        deviceModel: null
    },
    initComponent: function () {
        var me = this;
        var productId = me.productId;
        var leaf = me.leaf;
        var backPageSize = me.devicePageSize;
        var backPage = me.currentPage;
        var spareName = me.spareName;
        var deviceModel = me.deviceModel;
        var spareId = me.spareId;
        var isCarryTool=me.isCarryTool;
        var isCabinOutOrIn=me.isCabinOutOrIn;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);

        //var customerFilter=new CustomerFilter('T_SPARE_PARTS_'+me.schemaId+'_ID',CustomerFilter.prototype.SqlOperation.Equal,'',me.spareId);
        // var customerFilter=new CustomerFilter('T_SPARE_PARTS_'+me.schemaId+'_ID',CustomerFilter.prototype.SqlOperation.Equal,'',me.spareId);
        // var insSpareId = me.spareId;
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            title: '查看【<span style="color: blue; ">' + me.spareName + '</span>】台账',
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            //customerFilter:[customerFilter],
            createUrl: serviceName + '/spareParts/saveSparePartsInstData.rdm?spareId=' + me.spareId + "&productId=" + me.productId,
            // queryUrl: serviceName + '/spareParts/querySparePartsInstData.rdm?spareName=' + me.spareName + "&spareId=" + me.spareId + "&productId=" + me.productId,
            queryUrl: serviceName + '/spareParts/querySparePartsInstData.rdm',
            updateUrl: serviceName + '/spareParts/updateSparePartsInstData.rdm?spareId=' + me.spareId,
            afterInitComponent: function () {
                var me = this;
                me.viewConfig.getRowClass = function (record, rowIndex, rowParams, store) {
                    if (record.data['C_STATE_' + modelId] == '故障') {
                        return 'x-grid-record-red';
                    }
                };
                me.getStore().getProxy().setExtraParam('spareName', spareName);
                me.getStore().getProxy().setExtraParam('spareId', spareId);
                me.getStore().getProxy().setExtraParam('productId', productId);
                var toolbar = me.dockedItems[0];
                toolbar.add({
                    iconCls: 'icon-create',
                    text: '快速新增',
                    handler: function () {
                        // me.initEasyAddWin();
                        var form = Ext.create('Ext.form.Panel', {
                            plain: true,
                            border: 0,
                            bodyPadding: 5,
                            fieldDefaults: {
                                labelWidth: '50%',
                                anchor: '100%'
                            },
                            layout: {
                                type: 'vbox',
                                align: 'stretch'  // Child items are stretched to full width
                            },
                            items: [{
                                xtype: 'numberfield',
                                fieldLabel: '请输入新增设备数量',
                                name: 'count'
                            }],
                            buttons: [{
                                text: '确定',
                                handler: function () {
                                    var localWin = this;
                                    var content = form.getForm().getValues();
                                    var gridPanel = me.up("panel");
                                    // var window1 = me.up("window");
                                    // console.log(content);
                                    // console.log(me.up("panel"));
                                    Ext.Ajax.request({
                                        url: serviceName + '/spareParts/easyAdd.rdm',
                                        params: {
                                            count: content.count,
                                            modelId: me.modelId,
                                            spareId: gridPanel.spareId,
                                            //spareName: gridPanel.spareName,
                                            productId: gridPanel.productId
                                        },
                                        success: function (response) {
                                            gridPanel.down("panel").store.reload();
                                            localWin.up("window").close();
                                        }
                                    });
                                }
                            }, {
                                text: '取消',
                                handler: function () {
                                    this.up('window').close();
                                }
                            }]
                        });
                        var win = Ext.create('Ext.window.Window', {
                            title: '快速新增',
                            height: '15%',
                            width: '20%',
                            layout: 'fit',
                            maximizable: true,
                            modal: true,
                            items: [form]
                        });
                        win.show();
                    },
                    scope: me
                }, {
                    xtype: 'button',
                    iconCls: 'icon-delete',
                    text: '级联删除',
                    scope: this,
                    handler: function () {
                        if (!OrientExtUtil.GridHelper.hasSelected(modelGrid)) {
                            return;
                        }
                        var selectRecords = OrientExtUtil.GridHelper.getSelectedRecord(modelGrid);
                        var deviceId = selectRecords[0].data['T_SPARE_PARTS_480_ID'];
                        var ids = [];
                        Ext.each(selectRecords, function (s) {
                            ids.push(s.data.id);
                        });
                        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/spareParts/delSparePartsInstData.rdm', {
                            id: ids.toString(),
                            productId: me.productId,
                            deviceId: deviceId
                        }, false, function (resp) {
                            var ret = Ext.decode(resp.responseText);
                            if (ret.success) {
                                modelGrid.fireEvent('refreshGrid');
                                //Ext.Msg.alert("提示", "删除成功！")
                            }
                        })
                    }
                }, {
                    iconCls: 'icon-print',
                    text: '批量打印二维码',

                    handler: function () {
                        var grid = modelGrid;
                        var selections = grid.getSelectionModel().getSelection();
                        var deviceInstIds = OrientExtUtil.GridHelper.getSelectRecordIds(grid);
                        var startAll = false;
                        var win = Ext.create('Ext.window.Window', {
                            title:'打印二维码',
                            id:"qrcodeWindow",
                            height: 0.33 * globalHeight,
                            width: 0.3 * globalWidth,
                            layout: 'fit',
                            maximizable: true,
                            modal: true,
                            items: [
                                Ext.create('Ext.panel.Panel', {
                                html: '<div id="batchQrcode" align="center" style="width:562px; height:140px;">' +
                                    '</div>' +
                                    '<div id="batchQrcodeText" align="center" style="width:562px; height:140px;">' +
                                    '</div>' +
                                    '<div id="batchQrcodeTextImage" align="center" style="width:370px; height:285px;">' +
                                    '</div>' +
                                    '<iframe id="batchPrintf" src="" width="0" height="0" frameborder="0"></iframe>',
                            })
                            ]
                        });
                        win.show();
                        if (selections.length === 0) {
                            Ext.MessageBox.confirm('提示', '是否启动打印所有二维码？', function (btn) {
                                if (btn == 'yes') {
                                    startAll = true;
                                    OrientExtUtil.AjaxHelper.doRequest(serviceName + '/spareParts/batchPrintQrcode.rdm', {
                                        deviceInstIds: deviceInstIds,
                                        spareId: spareId,
                                        spareName: spareName,
                                        deviceModel: deviceModel,
                                        startAll: true
                                    }, false, function (resp) {
                                        if (resp.decodedData.success) {
                                            var printContent = resp.decodedData.results;
                                            Ext.getCmp('sparePartsInstOwner').batchPrintQrcodeText(printContent);
                                            // win.close();
                                            // Ext.Msg.alert("提示", "打印任务结束！");
                                        } else {
                                            Ext.Msg.alert("提示", resp.decodedData.msg);
                                        }
                                    });
                                }
                            });
                        } else {
                            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/spareParts/batchPrintQrcode.rdm', {
                                deviceInstIds: deviceInstIds,
                                spareName: spareName,
                                deviceModel: deviceModel,
                                startAll: false
                            }, false, function (resp) {
                                if (resp.decodedData.success) {
                                    var printContent = resp.decodedData.results;
                                    Ext.getCmp('sparePartsInstOwner').batchPrintQrcodeText(printContent);
                                    // win.close();
                                    // Ext.Msg.alert("提示", "打印任务结束！");
                                } else {
                                    Ext.Msg.alert("提示", resp.decodedData.msg);
                                }
                            });
                        }
                    }
                }, {
                    iconCls: 'icon-back',
                    text: '返回上一页',
                    handler: function () {
                        //console.log(modelGrid);
                        //console.log(me.ownerCt.up('sparePartsManagerDashboard'));  //获取上层组件的上层组件
                        var cardPanel = Ext.getCmp("spareCenterPanel");
                        //移除所有面板
                        cardPanel.items.each(function (item, index) {
                            cardPanel.remove(item);
                        });
                        var gridPanel = Ext.create('OrientTdm.SparePartsMgr.SparePartsManagerGrid', {
                            productId: productId,
                            leaf: leaf,
                            isCarryTool:isCarryTool,
                            isCabinOutOrIn:isCabinOutOrIn,
                            //pageSize:backPageSize,
                            //page:backPage,
                            afterInitComponent: function () {
                                var store = Ext.getCmp('sparePartsMgrOwner').getStore();
                                store.pageSize = backPageSize;
                                store.loadPage(backPage);
                            }
                        });
                        cardPanel.add(gridPanel);
                        //var store = gridPanel.modelGrid.getStore();
                        //store.pageSize = backPageSize;
                        //store.loadPage(backPage);
                        //cardPanel.navigation(cardPanel, 'next');
                    },
                    scope: me
                });
            }
        });

        var columns = modelGrid.columns;
        if (Ext.isArray(columns)) {
            columns.push({
                xtype: 'actioncolumn',
                text: '生成二维码',
                align: 'center',
                width: 250,
                items: [{
                    icon: serviceName + '/app/images/icons/default/common/lcr_QRCode.png',
                    tooltip: '生成二维码',
                    handler: function (grid, rowIndex) {
                        var data = grid.store.getAt(rowIndex);
                        var id = data.raw.ID;
                        var spareName = data.raw['C_DEVICE_NAME_' + modelId];
                        var serialNumber = data.raw['C_SERIAL_NUMBER_' + modelId];
                        //弹出新增面板窗口
                        var createWin = Ext.create('Ext.Window', {
                            title: '查看【<span style="color: blue; ">' + spareName + '</span>】二维码',
                            plain: true,
                            height: 0.33 * globalHeight,
                            width: 0.3 * globalWidth,
                            layout: 'fit',
                            maximizable: true,
                            modal: true,
                            items: [Ext.create('Ext.panel.Panel', {
                                listeners: {
                                    render: function () {
                                        var me = this;
                                        // me.qrcode = $('#canvas_sham').qrcode({
                                        //     // render: "div",
                                        //     text: id,
                                        //     width:100,
                                        //     height:100,
                                        //     background: "#ffffff",
                                        //     foreground: "#000000",
                                        //     correctLevel: 0,
                                        //     // margin: "8px"
                                        // });
                                        var canvas = document.getElementById('canvas_sham');
                                        QRCode.toCanvas(canvas, id, {
                                            width: 140,
                                            height: 140,
                                            margin: 1
                                        }, function (error) {
                                            if (error) {
                                                console.log("success!");
                                            }
                                        });
                                        // $('#qrcode').children('canvas').width = 100;
                                        // $('#qrcode').children('canvas').height = 100;
                                        // console.log($('#qrcode').children('canvas').width());
                                        // console.log($('#qrcode').children('canvas').height());
                                        // var qrcode=new QRCode(document.getElementById('qrcode'),{
                                        //     width:50,
                                        //     height:50
                                        // });

                                        //  var canvas=me.qrcode.find('canvas').get(0);
                                        //  Ext.getCmp('sparePartsInstOwner').addQrcodeText(me.qrcode,1000,120,20,"#fff","#a50054")
                                        // var  = document.getElementById('');
                                        // var qrcode_title = document.getElementById('qrcode_title');
                                        // var qrcode_desc = document.getElementById('qrcode_desc');
                                        // me.canvas_sham = document.getElementById('canvas_sham');
                                        if (Ext.isEmpty(deviceModel)) {
                                            deviceModel = "";
                                        }
                                        if (Ext.isEmpty(serialNumber)) {
                                            serialNumber = "";
                                        }
                                        var textArray = [];
                                        textArray[0] = "设备名称：" + spareName;

                                        textArray[1] = "编号：" + deviceModel + "-" + serialNumber;
                                        textArray[2] = "唯一标识：" + id;
                                        var lineHeight = 0;
                                        for (var i = 0; i < textArray.length; i++) {
                                            var result = Ext.getCmp('sparePartsInstOwner').breakLinesForCanvas(
                                                textArray[i],
                                                370, ' bold 25px 黑体');
                                            console.log(result);

                                            lineHeight = 25 + lineHeight;
                                            var canvas_sham1 = document.getElementById('canvas_sham1');
                                            var context = canvas_sham1.getContext('2d');
                                            //消除锯齿
                                            context.mozImageSmoothingEnabled = false;
                                            context.webkitImageSmoothingEnabled = false;
                                            context.msImageSmoothingEnabled = false;
                                            context.imageSmoothingEnabled = false;
                                            context.font = "bold 25px 黑体";
                                            // context.textAlign='center';
                                            var rowHeight;
                                            result.forEach(function (line, index) {
                                                rowHeight = lineHeight * index + lineHeight;
                                                context.fillText(line, 0, lineHeight * index + lineHeight);
                                            });
                                            lineHeight = rowHeight;
                                        }


                                        // var canvas_sham = document.getElementById('canvas_sham');
                                        // var ctx = canvas_sham.getContext("2d");
                                        // var content="编号："+serialNumber;
                                        // var canvasWidth=800;
                                        // var canvasHeight=100;
                                        // Ext.getCmp('sparePartsInstOwner').canvasQrcode(ctx,content,canvasWidth,canvasHeight);
                                        // var content="唯一标识："+id;
                                        // var canvasWidth=845;
                                        // var canvasHeight=150;
                                        // Ext.getCmp('sparePartsInstOwner').canvasQrcode(ctx,content,canvasWidth,canvasHeight);

                                        // ctx.setFillStyle('#666') // 文字颜色

                                        // QRCode.toDataURL(
                                        //     id, {
                                        //         errorCorrectionLevel: "H"
                                        //     }, function f(err, url) {
                                        //         .src = url;
                                        //         // 画二维码里的说明性文字
                                        //         html2canvas(qrcode_desc, {
                                        //             scale: 1
                                        //         }).then(function f1(canvas) {
                                        //             qrcode_desc.style.display = "none";
                                        //             //将convas生成的二维码变为图片并插入qrcode_title标签
                                        //             qrcode_title.src = canvas.toDataURL("image/png");
                                        //             // 在canvas里进行拼接
                                        //             var ctx = canvas_sham.getContext("2d");
                                        //             ctx.rect(0, 0, 400, 400);
                                        //             ctx.fillStyle = "#fff";
                                        //             ctx.fill();
                                        //             setTimeout(function () {
                                        //                 //获取图片
                                        //                 ctx.drawImage(, 150, 0, 200, 200);
                                        //                 //获取字体
                                        //                 ctx.drawImage(qrcode_title, 180, 180, 120, 10);
                                        //                 canvas_sham.style.display = "none";
                                        //                 canvas_sham.style.font="18px '微软雅黑'";
                                        //                 canvas_sham.align="center";
                                        //                 .src = canvas_sham.toDataURL();
                                        //                 .style.display = "inline-block";
                                        //             }, 1000);
                                        //             qrcode_title.style.display = "none";
                                        //         });
                                        //     }
                                        // );
                                    }
                                },
                                html: '<div id="qrcode" align="center" style="width:562px; height:140px;">' +
                                    '<canvas width="140" height="140" class="canvas" id="canvas_sham"></canvas>' +
                                    '</div>' +
                                    '<div id="qrcodeText" align="center" style="width:562px; height:140px;">' +
                                    '<canvas width="370" height="110"  class="canvas" id="canvas_sham1"></canvas>' +
                                    '</div>' +
                                    '<div id="qrcodeTextImage" align="center" style="width:370px; height:285px;">' +
                                    '<canvas width="370" height="285"  class="canvas" id="canvas_sham2"></canvas>' + '<!--startprint-->' +
                                    '<img class="qrcode_Image" id="qrcode_Image">' +
                                    '<!--endprint-->' +
                                    '</div>' +
                                    '<iframe id="printf" src="" width="0" height="0" frameborder="0"></iframe>'
                                // '<img class="" id="" alt="二维码图片" display="none">' +
                                // '<img class="qrcode_title" id="qrcode_title" alt="二维码描述文字" display="none">' +
                                // '<span class="qrcode_desc" id="qrcode_desc" display="none">欢迎来到鸿基梦</span>' +

                            })],

                            buttonAlign: 'center',
                            buttons: [
                                {
                                    text: '打印',
                                    iconCls: 'icon-print',
                                    handler: function () {
                                        // OrientExtUtil.AjaxHelper.doRequest(serviceName + '/spareParts/printQRCodeImage.rdm', {
                                        //     path: resp.decodedData.results[1]
                                        // }, true, function (resp) {
                                        //
                                        // })
                                        // $('#qrcodeImage').empty();

                                        var img = document.getElementById("qrcode_Image");
                                        //显示图片
                                        // img.style.display = "block";
                                        var qrcodeCanvas = $('#canvas_sham.canvas').get(0);
                                        var qrcodeTextCanvas = $('#canvas_sham1.canvas').get(0);
                                        var canvas_sham2 = document.getElementById('canvas_sham2');
                                        // canvas_sham2.width=740;
                                        // canvas_sham2.height=560;
                                        // var qrcodeTextCanvas=$('#canvas_sham1').getContext("2d");
                                        var ctx = canvas_sham2.getContext("2d");
                                        // var getPixekRatio=function(ctx){
                                        //     var backingStore=context.backingStorePixelRatio||
                                        //         context.webkitBackingStorePixelRatio||
                                        //         context.mozBackingStorePixelRatio||
                                        //         context.msBackingStorePix
                                        // }
                                        // var width=canvas_sham2.width;
                                        // var height=canvas_sham2.height;
                                        // canvas_sham2.style.width=width+"px";
                                        // canvas_sham2.style.height=height+"px";
                                        // canvas_sham2.height=height*2;
                                        // canvas_sham2.width=width*2;
                                        // ctx.scale(2,2);
                                        //消除锯齿
                                        ctx.mozImageSmoothingEnabled = false;
                                        ctx.webkitImageSmoothingEnabled = false;
                                        ctx.msImageSmoothingEnabled = false;
                                        ctx.imageSmoothingEnabled = false;
                                        ctx.font = "bold 25px 黑体";
                                        ctx.drawImage(qrcodeCanvas, 90, 15);
                                        ctx.drawImage(qrcodeTextCanvas, 0, 160);
                                        // canvas.margin = "8px";
                                        img.src = canvas_sham2.toDataURL("image/jpg");
                                        // $('#qrcode_Image').jqprint({
                                        //     debug: false,
                                        //     importCSS: true,
                                        //     printContainer: true,
                                        //     operaSupport: false
                                        // })
                                        // //不显示图片
                                        // var iframe=document.createElement('IFRAME');
                                        // var doc=null;
                                        // document.body.appendChild(iframe);
                                        // doc=iframe.contentWindow.document;
                                        // doc.write($('#qrcode_Image'));
                                        // iframe.contentWindow.print();
                                        // img.style.display = "none";
                                        var printImage = document.getElementById("qrcode_Image");
                                        var bdhtml = window.document.body.innerHTML;
                                        img.style.display = "none";
                                        ctx.clearRect(0, 0, canvas_sham2.width, canvas_sham2.height);
                                        var olderHtml = bdhtml;
                                        var sprnstr = "<!--startprint-->";
                                        var eprnstr = "<!--endprint-->";
                                        var prnhtml = bdhtml.substr(bdhtml.indexOf(sprnstr) + 17);
                                        prnhtml = prnhtml.substring(0, prnhtml.indexOf(eprnstr));
                                        var f = document.getElementById("printf");
                                        f.contentDocument.write(prnhtml);
                                        f.contentDocument.close();
                                        // f.contentWindow.print();
                                        // window.document.body.innerHTML=prnhtml;
                                        setTimeout(function () {
                                            f.contentWindow.print();
                                        }, 500);
                                        // window.document.body.innerHTML=olderHtml;
                                        // return false;
                                    }
                                },
                                {
                                    text: '下载',
                                    iconCls: 'icon-download',
                                    handler: function () {
                                        var qrcodeCanvas = $('#canvas_sham.canvas').get(0);
                                        var qrcodeTextCanvas = $('#canvas_sham1.canvas').get(0);
                                        var canvas_sham2 = document.getElementById('canvas_sham2');
                                        // var qrcodeTextCanvas=$('#canvas_sham1').getContext("2d");
                                        var ctx = canvas_sham2.getContext("2d");
                                        ctx.drawImage(qrcodeCanvas, 90, 15);
                                        ctx.drawImage(qrcodeTextCanvas, 0, 160);
                                        var alink = document.createElement("a");
                                        alink.href = canvas_sham2.toDataURL('image/jpg');
                                        alink.download = "二维码";
                                        alink.click();
                                    }
                                },
                                {
                                    text: '关闭',
                                    iconCls: 'icon-close',
                                    handler: function () {
                                        this.up('window').close();
                                    }
                                }
                            ]
                        });
                        createWin.show();
                        // $('#qrcode').qrcode({
                        //     render: "canvas",
                        //     text: id,
                        //     width: 161,
                        //     height: 161,
                        //     background: "#ffffff",
                        //     foreground: "#000000",
                        //     correctLevel: 0
                        // });

                        // })
                    }
                }]
            }, {
                xtype: 'actioncolumn',
                text: '故障记录',
                align: 'center',
                width: 250,
                items: [{
                    iconCls: 'icon-detail',
                    tooltip: '故障记录',
                    handler: function (grid, rowIndex) {
                        var data = grid.store.getAt(rowIndex);
                        var deviceInstId = data.raw.ID;
                        var troubleDeviceGrid = Ext.create('OrientTdm.SparePartsMgr.TroubleDeviceMgr.TroubleDeviceGrid', {
                            deviceInstId: deviceInstId,
                            spareId: me.spareId,
                            spareName: me.spareName,
                            productId: me.productId,
                            leaf: me.leaf
                        });
                        var cardPanel = Ext.getCmp("spareCenterPanel");
                        //移除所有面板
                        cardPanel.items.each(function (item, index) {
                            cardPanel.remove(item);
                        });
                        cardPanel.add(troubleDeviceGrid);
                        cardPanel.navigation(cardPanel, 'next');
                    }
                }]
            }, {
                xtype: 'actioncolumn',
                text: '入所检验',
                align: 'center',
                width: 250,
                items: [{
                    iconCls: 'icon-detail',
                    tooltip: '入所检验',
                    handler: function (grid, rowIndex) {
                        var data = grid.store.getAt(rowIndex);
                        var deviceInstId = data.raw.ID;
                        var troubleDeviceGrid = Ext.create('OrientTdm.SparePartsMgr.EnterResearchTestMgr.EnterResearchTestGrid', {
                            deviceInstId: deviceInstId,
                            spareId: me.spareId,
                            spareName: me.spareName,
                            productId: me.productId,
                            leaf: me.leaf
                        });
                        var cardPanel = Ext.getCmp("spareCenterPanel");
                        //移除所有面板
                        cardPanel.items.each(function (item, index) {
                            cardPanel.remove(item);
                        });
                        cardPanel.add(troubleDeviceGrid);
                        cardPanel.navigation(cardPanel, 'next');
                    }
                }]
            }, {
                xtype: 'actioncolumn',
                text: '寿命全周期',
                align: 'center',
                width: 250,
                items: [{
                    iconCls: 'icon-detail',
                    tooltip: '寿命全周期',
                    handler: function (grid, rowIndex) {
                        var data = grid.store.getAt(rowIndex);
                        var deviceInstId = data.raw.ID;
                        var deviceName = data.raw['C_DEVICE_NAME_' + modelId];
                        var numberId = data.raw['C_SERIAL_NUMBER_' + modelId];
                        var deviceInstName = deviceName + '-' + numberId;
                        //var troubleDeviceGrid = Ext.create('OrientTdm.SparePartsMgr.DeviceInstLifeCycle', {
                        //    deviceInstId: deviceInstId,
                        //    spareId: me.spareId,
                        //    spareName: me.spareName,
                        //    productId: me.productId,
                        //    leaf: me.leaf
                        //});
                        //var cardPanel = Ext.getCmp("spareCenterPanel");
                        ////移除所有面板
                        //cardPanel.items.each(function (item, index) {
                        //    cardPanel.remove(item);
                        //});
                        //cardPanel.add(troubleDeviceGrid);
                        //cardPanel.navigation(cardPanel, 'next');

                        //弹出新增面板窗口
                        var createWin = Ext.create('Ext.Window', {
                            title: '查看【<span style="color: blue; ">' + deviceInstName + '</span>】寿命周期',
                            plain: true,
                            height: 0.5 * globalHeight,
                            width: 0.5 * globalWidth,
                            layout: 'fit',
                            maximizable: true,
                            modal: true,
                            items: [Ext.create('OrientTdm.SparePartsMgr.DeviceInstLifeCycle', {
                                deviceInstId: deviceInstId,
                                deviceInstName: deviceInstName,
                                spareId: me.spareId,
                                spareName: me.spareName,
                                productId: me.productId,
                                leaf: me.leaf
                            })],
                            buttonAlign: 'center',
                            buttons: [
                                {
                                    text: '关闭',
                                    iconCls: 'icon-close',
                                    handler: function () {
                                        this.up('window').close();
                                    }
                                }
                            ]
                        });
                        createWin.show();
                    }
                }]
            })
        }
        modelGrid.reconfigure(modelGrid.getStore(), columns);

        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    },
    addQrcodeText: function (qrcode, canvasSize, fontSize, padding, divBackground, fontColor) {
        var me = this;
        var arr = [];
        arr.push('法律噶及阿娇噶吉拉拉');
        arr.push('发酵噶巨额荣发机构');
        var canvases = qrcode.find('canvas').get(0);
        var fontSizeH = fontSize + padding * 3;  //文字高度
        var rectW = 0;
        var width = 0;
        var upTextX = 400 - canvases.width;
        var upTextY = canvases.height;
        var ctx = canvases.getContext("2d");
        width = ctx.measureText(arr[0][1]).width;
        ctx.fillRect(upTextX, upTextY, " ", fontSizeH);
        ctx.font = fontSize + "px Calibri";
        ctx.fillStyle = fontColor;
        ctx.fillText(arr[0], upTextX, upTextY);
    },

    fillText: function (textArray) {
        var lineHeight = 0;
        for (var i = 0; i < textArray.length; i++) {
            var result = Ext.getCmp('sparePartsInstOwner').breakLinesForCanvas(
                textArray[i],
                250, '16px 黑体 加粗');
            console.log(result);

            lineHeight = 20 + lineHeight;
            var context = document.getElementById('canvas_sham1').getContext('2d');
            context.font = "16px 黑体";
            // context.textAlign='center';
            var rowHeight;
            result.forEach(function (line, index) {
                rowHeight = lineHeight * index + lineHeight;
                context.fillText(line, 3, lineHeight * index + lineHeight);
            });
            lineHeight = rowHeight;
        }
        return lineHeight;
    },

    //寻找切换断点
    findBreakPoint: function (text, width, context) {
        var min = 0;
        var max = text.length - 1;

        while (min <= max) {
            var middle = Math.floor((min + max) / 2);
            var middleWidth = context.measureText(text.substr(0, middle)).width;
            var oneCharWiderThanMiddleWidth = context.measureText(text.substr(0, middle + 1)).width;
            if (middleWidth <= width && oneCharWiderThanMiddleWidth > width) {
                return middle;
            }
            if (middleWidth < width) {
                min = middle + 1;
            } else {
                max = middle - 1;
            }
        }

        return -1;
    },

    breakLinesForCanvas: function (text, width, font, j) {
        var me = this;
        var canvas = document.getElementById('canvas_sham1');
        if (typeof j != 'undefined') {
            canvas = document.getElementById('batchCanvas_sham1' + j.toString());
        }
        var context = canvas.getContext('2d');
        var result = [];
        var breakPoint = 0;

        if (font) {
            context.font = font;
        }

        while ((breakPoint = me.findBreakPoint(text, width, context)) !== -1) {
            result.push(text.substr(0, breakPoint));
            text = text.substr(breakPoint);
        }

        if (text) {
            result.push(text);
        }
        return result;
    },
    canvasQrcode: function (ctx, content, canvasWidth, canvasHeight) {
        var me = this;
        //文字颜色
        ctx.fillStyle = "#666";
        //水平对齐
        ctx.textAlign = "center";
        ctx.fontSize = "20";
        // ctx.setTextAlign('center') //水平对齐
        // ctx.setFontSize(18);//字体大小

        // var canvasWidth = 800; //画布宽度
        // var canvasHeight = 500; //画布宽度
        //一行画布可绘制多少文字
        var textareaWidth = Math.ceil(canvasWidth / 18); //画布宽度除以字号
        var text = [];//存放切割后的内容
        // var content="编号："+serialNumber+"唯一标识："+id;
        while (content.length > 0) {
            text.push(content.substr(0, textareaWidth))
            content = content.substr(textareaWidth, content.length)
        }

        //设置文本垂直居中
        for (var i = 0; i < text.length; i++) {
            var h = 0;
            switch (i + 1) {
                case 1:
                    h = (canvasHeight / 100) * (i + 1 * 22) + (i * 20);
                    break;
                case 2:
                    h = (canvasHeight / 100) * (i + 1 * 20) + (i * 20);
                    break;
                case 3:
                    h = (canvasHeight / 100) * (i + 1 * 17) + (i * 20);
                    break;
            }
            ctx.fillText(text[i], canvasWidth / 2, h)
        }
    },

    batchPrintQrcodeText: function (printContent) {
        var dataBase64="";
        for (var j = 0; j < printContent.length; j++) {
            var lineHeight = 0;
            // var canvas_sham = document.getElementById('canvas_sham');
            // var ctxsham = canvas_sham.getContext("2d");
            // ctxsham.clearRect(0,0,canvas_sham.width,canvas_sham.height);
            document.getElementById('batchQrcode').innerHTML="";
            document.getElementById('batchQrcodeText').innerHTML="";

            $("#batchQrcode").append("<canvas width=\"140\" height=\"140\" class=\"canvas\"></canvas>");
            var batchQrcodeCanvas = $('#batchQrcode').children('canvas')[0];
            batchQrcodeCanvas.setAttribute("id", "batchCanvas_sham" + j);
            $("#batchQrcodeText").append("<canvas width=\"370\" height=\"110\"  class=\"canvas\"></canvas>");
            var batchQrcodeTextnvas = $('#batchQrcodeText').children('canvas')[0];
            batchQrcodeTextnvas.setAttribute("id", "batchCanvas_sham1" + j);
            var canvas;
            for (var i = 0; i < printContent[j].length; i++) {

                if (printContent[j][i].indexOf("唯一标识") != -1) {
                    var biaoshiContent = printContent[j][i];
                    var deviceInstId = biaoshiContent.slice(5, biaoshiContent.length);
                     canvas = document.getElementById('batchCanvas_sham' + j.toString());
                    QRCode.toCanvas(canvas, deviceInstId, {
                        width: 140,
                        height: 140,
                        margin: 1
                    }, function (error) {
                        if (error) {
                            console.log("success!");
                        }
                    });
                }
                var result = Ext.getCmp('sparePartsInstOwner').breakLinesForCanvas(
                    printContent[j][i],
                    370, ' bold 25px 黑体', j);
                console.log(result);
                lineHeight = 25 + lineHeight;
                var batchCanvas_sham1 = document.getElementById('batchCanvas_sham1' + j.toString());
                var context = batchCanvas_sham1.getContext('2d');
                //消除锯齿
                context.mozImageSmoothingEnabled = false;
                context.webkitImageSmoothingEnabled = false;
                context.msImageSmoothingEnabled = false;
                context.imageSmoothingEnabled = false;
                context.font = "bold 25px 黑体";
                // context.textAlign='center';
                var rowHeight;
                result.forEach(function (line, index) {
                    rowHeight = lineHeight * index + lineHeight;
                    context.fillText(line, 0, lineHeight * index + lineHeight);
                });
                lineHeight = rowHeight;
            }
            document.getElementById('batchQrcodeTextImage').innerHTML="";
            $("#batchQrcodeTextImage").append("<canvas width=\"370\" height=\"285\"  class=\"canvas\"></canvas>"+"<!--startprint"+j+"-->"+
                "<img class=\"batchQrcode_Image\">"+"<!--endprint"+j+"-->");
            var batchQrcodeTextImageCanvas = $('#batchQrcodeTextImage').children('canvas')[0];
            batchQrcodeTextImageCanvas.setAttribute("id", "batchCanvas_sham2" + j);
            var batchQrcodeTextImage = $('#batchQrcodeTextImage').children('img')[0];
            batchQrcodeTextImage.setAttribute("id", "batchQrcode_Image" + j);

            //打印图片
            var img = document.getElementById("batchQrcode_Image" + j.toString());
            //显示图片
            var qrcodeCanvas = $('#batchCanvas_sham' + j.toString() + '.canvas').get(0);
            var qrcodeTextCanvas = $('#batchCanvas_sham1' + j.toString() + '.canvas').get(0);
            var canvas_sham2 = document.getElementById('batchCanvas_sham2' + j.toString());
            var ctx = canvas_sham2.getContext("2d");
            //消除锯齿
            ctx.mozImageSmoothingEnabled = false;
            ctx.webkitImageSmoothingEnabled = false;
            ctx.msImageSmoothingEnabled = false;
            ctx.imageSmoothingEnabled = false;
            ctx.font = "bold 25px 黑体";
            ctx.drawImage(qrcodeCanvas, 90, 15);
            ctx.drawImage(qrcodeTextCanvas, 0, 160);
            img.src = canvas_sham2.toDataURL("image/jpg");
            var bdhtml = window.document.body.innerHTML;
            img.style.display = "none";
            //清除canvas画的图
            ctx.clearRect(0, 0, canvas_sham2.width, canvas_sham2.height);
            var qrcodeCtx=canvas.getContext("2d");
            qrcodeCtx.clearRect(0, 0, canvas.width, canvas.height);
            context.clearRect(0, 0, batchCanvas_sham1.width, batchCanvas_sham1.height);
            var sprnstr = "<!--startprint"+j+"-->";
            var eprnstr = "<!--endprint"+j+"-->";
             var prnhtml = bdhtml.substr(bdhtml.indexOf(sprnstr) + 18);
            prnhtml = prnhtml.substring(0, prnhtml.indexOf(eprnstr));
           dataBase64=dataBase64+prnhtml;
           img.src="";
        }
        var f = document.getElementById("batchPrintf");
        f.contentDocument.write(dataBase64);
        setTimeout(function () {
            f.contentWindow.print();
        }, 500);
        f.contentDocument.close();
    }
});
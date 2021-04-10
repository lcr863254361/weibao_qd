/**
 * Created by User on 2018/12/13.
 */
Ext.define('OrientTdm.FormTemplateMgr.FormTemplatePanel.FormTemplateHeadCellGrid', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    alias: 'widget.formTemplateHeadCellGrid',
    statics: {
        showPictures: function (cellId) {
            var me = this;
            if (!Ext.isEmpty(cellId)) {
                var filePanel = {
                    layout: 'fit',
                    html: '<iframe id="imageViewFrame" width="100%" height="100%" marginwidth="0" framespacing="0" marginheight="0" frameborder="0" src = ""></iframe>',
                    listeners: {
                        render: function () {
                            me.reconfigImagePlugin(cellId);
                        }
                    }
                };
                OrientExtUtil.WindowHelper.createWindow(filePanel, {
                    title: '图片查看',
                    iconCls: 'icon-picture',
                    height: 0.8 * globalHeight,
                    width: 0.8 * globalWidth,
                    maximized: true,
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
        reconfigImagePlugin: function (cellId) {
            var schemaId = TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID;
            var modelId = OrientExtUtil.ModelHelper.getModelId("T_CHECK_CELL_INST", schemaId);
            Ext.query("#imageViewFrame")[0].src = serviceName + '/app/views/file/localPicturePreview.jsp?' + 'modelId=' + modelId + '&dataId=' + cellId + '&fileGroupId=-4';
        }
    },
    initComponent: function () {
        var me = this;
        me.usePage = false;
        me.getCheckListDetailPanel(me.checkTempId);
        // Ext.apply(me, {
        //     layout: 'border',
        //     items: []
        // });
        me.callParent(arguments);
    },
    getCheckListDetailPanel: function (checkTempId) {
        var me = this;

        var schemaId = TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID;
        var modelId = OrientExtUtil.ModelHelper.getModelId("T_CHECK_CELL_INST", schemaId);

        var column = [];
        var fields = [];
        me.checkTempId = checkTempId;

        // var isHasPhoto;
        //提前判断拍照项是否存在图片
        // if(me.isInst){
        //     OrientExtUtil.AjaxHelper.doRequest(serviceName + '/formTemplate/getTemplateCellIds.rdm', {
        //         checkTempId: me.checkTempId,
        //         modelId:modelId
        //     }, false, function (resp) {
        //         if(resp.decodedData.success){
        //            isHasPhoto=resp.decodedData.results;
        //         }
        //     })
        // }
        var productMap = '';
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/formTemplate/getChooseProductNode.rdm', {
            checkTempId: checkTempId
        }, false, function (response) {
            productMap = response.decodedData.results
        });

        //得到表头信息
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/formTemplate/getTemplateHeadersById.rdm', {
            checkTempId: me.checkTempId,
            isInst: me.isInst
        }, false, function (resp) {
            //得到表头信息
            var retVal = resp.decodedData.results;
            for (var i = retVal.length - 1; i >= 0; i--) {
                var colArr = retVal[i].split('[+]');
                var col = {
                    header: colArr[0],
                    dataIndex: colArr[1],
                    // sortable: false,
                    flex: 1,
                    width: 'auto',
                    listeners: {
                        mouseover: function () {
                            $(".image_stack").delegate('img', 'mouseenter', function () { //when user hover mouse on image with div id=stackphotos
                                if ($(this).hasClass('stackphotos')) { //
                                    var $parent = $(this).parent();
                                    $parent.find('img#midPhoto').addClass('rotateLeft');
                                    $parent.find('img#topPhoto').addClass('rotateMid');
                                    $parent.find('img#bottomPhoto').css("left", "90px"); // reposition the first and last image
                                    $parent.find('img#midPhoto').css("left", "30px");
                                }
                            }).delegate('img', 'mouseleave', function () { // when user removes cursor from the image stack
                                $('img#bottomPhoto').removeClass('rotateRight'); // remove the css class that was previously added to make it to its original position
                                $('img#midPhoto').removeClass('rotateLeft');
                                $('img#topPhoto').removeClass('rotateMid');
                                $('img#bottomPhoto').css("left", ""); // remove the css property 'left' value from the dom
                                $('img#midPhoto').css("left", "");
                            });
                        }
                    }
                };
                if (col.header.indexOf('#') === 0) {
                    col.header = col.header.substring(1);
                    col.renderer = function (v, p, record) {
                        if (Ext.isEmpty(v)) {
                            return "";
                        }
                        if (v.indexOf('对勾') !== -1) {
                            if (v.indexOf('是') !== -1) {
                                return '<input style="vertical-align:middle" type="checkbox" onclick="return false;" checked/>是&nbsp;&nbsp;<input style="vertical-align: middle" type="checkbox" onclick="return false;"/>否';
                            } else if (v.indexOf('否') !== -1) {
                                return '<input style="vertical-align:middle" type="checkbox" onclick="return false;"/>是&nbsp;&nbsp;<input style="vertical-align: middle" type="checkbox" onclick="return false;" checked/>否';
                            } else {
                                return '<input style="vertical-align:middle" type="checkbox" onclick="return false;"/>是&nbsp;&nbsp;<input style="vertical-align: middle" type="checkbox" onclick="return false;"/>否';
                            }
                        }
                        if (v.indexOf('是否无') !== -1) {
                            if (v.indexOf('是') !== -1 && v.length == 1) {
                                return '<input style="vertical-align:middle" type="checkbox" onclick="return false;" checked/>是&nbsp;&nbsp;<input style="vertical-align: middle" type="checkbox" onclick="return false;"/>否&nbsp;&nbsp;<input style="vertical-align: middle" type="checkbox" onclick="return false;"/>无';
                            } else if (v.indexOf('否') !== -1 && v.length == 1) {
                                return '<input style="vertical-align:middle" type="checkbox" onclick="return false;"/>是&nbsp;&nbsp;<input style="vertical-align: middle" type="checkbox" onclick="return false;" checked/>否&nbsp;&nbsp;<input style="vertical-align: middle" type="checkbox" onclick="return false;"/>无';
                            } else if (v.indexOf('无') !== -1 && v.length == 1) {
                                return '<input style="vertical-align:middle" type="checkbox" onclick="return false;"/>是&nbsp;&nbsp;<input style="vertical-align: middle" type="checkbox" onclick="return false;" checked/>否&nbsp;&nbsp;<input style="vertical-align: middle" type="checkbox" onclick="return false;"/>无';
                            } else {
                                return '<input style="vertical-align:middle" type="checkbox" onclick="return false;"/>是&nbsp;&nbsp;<input style="vertical-align: middle" type="checkbox" onclick="return false;"/>否&nbsp;&nbsp;<input style="vertical-align: middle" type="checkbox" onclick="return false;"/>无';
                            }
                        } else if (v.indexOf('填写') !== -1) {
                            if (v.split('::').length === 3) {
                                //有值的情况下直接返回数值
                                return v.split("::")[2];
                            } else {
                                // var width = col.width;
                                // return '<input type="text" readonly="readonly" style="width: ' + width + 'px"/>';
                                return '<input type="text" readonly="readonly"/>';
                            }
                        } else if (v.indexOf('数字') !== -1) {
                            if (v.split('::').length === 3) {
                                //有值的情况下直接返回数值
                                return v.split("::")[2];
                            } else {
                                // var width = col.width;
                                return '<input type="text" readonly="readonly"/>';
                            }
                        } else if (v.indexOf('时间') !== -1) {
                            if (v.split('::').length === 3) {
                                //有值的情况下直接返回数值
                                return v.split("::")[2];
                            } else {
                                // var width = col.width;
                                return '<input type="text" readonly="readonly"/>';
                            }
                        } else if (v.indexOf('经纬度') !== -1) {
                            if (v.split('::').length === 3) {
                                //有值的情况下直接返回数值
                                return v.split("::")[2];
                            } else {
                                // var width = col.width;
                                return '<input type="text" readonly="readonly"/>';
                            }
                        } else if (v.indexOf('拍照') !== -1 || v.indexOf('签署') !== -1) {
                            if (v.indexOf('::') !== -1) {
                                v = v.split("::")[1];
                            } else {
                                v = "";
                            }
                            // if(!Ext.isEmpty(v)&&me.isInst){
                            //     var isHasPhotoResult=isHasPhoto[v];
                            //     if(isHasPhotoResult==='true'){
                            //         var htmlStr = '<div class="image_stack" onclick="OrientTdm.FormTemplateMgr.FormTemplatePanel.FormTemplateHeadCellGrid.showPictures(\'' + v + '\')">' +
                            //             '<img id="bottomPhoto" class="stackphotos" src="app/images/rotate/bottom.jpg">' +
                            //             '<img id="midPhoto" class="stackphotos" src="app/images/rotate/mid.jpg">' +
                            //             '<img id="topPhoto" class="stackphotos" src="app/images/rotate/top.jpg">' +
                            //             '</div>';
                            //         return htmlStr;
                            //     }else{
                            //         var noPhotoHtml = '<span> </span>';
                            //         return noPhotoHtml;
                            //     }
                            // }else{
                            //     var htmlStr = '<div class="image_stack" onclick="OrientTdm.FormTemplateMgr.FormTemplatePanel.FormTemplateHeadCellGrid.showPictures(\'' + v + '\')">' +
                            //         '<img id="bottomPhoto" class="stackphotos" src="app/images/rotate/bottom.jpg">' +
                            //         '<img id="midPhoto" class="stackphotos" src="app/images/rotate/mid.jpg">' +
                            //         '<img id="topPhoto" class="stackphotos" src="app/images/rotate/top.jpg">' +
                            //         '</div>';
                            //     return htmlStr;
                            // }
                            var htmlStr = '<div class="image_stack" onclick="OrientTdm.FormTemplateMgr.FormTemplatePanel.FormTemplateHeadCellGrid.showPictures(\'' + v + '\')">' +
                                '<img id="bottomPhoto" class="stackphotos" src="app/images/rotate/bottom.jpg">' +
                                '<img id="midPhoto" class="stackphotos" src="app/images/rotate/mid.jpg">' +
                                '<img id="topPhoto" class="stackphotos" src="app/images/rotate/top.jpg">' +
                                '</div>';
                            return htmlStr;
                        } else if (v.indexOf('故障') !== -1) {
                            if (v.indexOf('::') !== -1) {
                                v = v.split("::")[1];
                            } else {
                                v = "";
                            }
                            var htmlStr = '<div class="image_stack">' +
                                '<img style="width: 20px;height: 20px" src="app/images/mediaImage/trouble.png">' +
                                '</div>';
                            return htmlStr;
                        } else {
                            return "";
                        }
                    }
                } else {
                    col.renderer = function (v, meta, record) {
                        if (v.indexOf('::') !== -1 && (v.split("::")[0] != 'null')) {
                            meta.tdAttr = 'data-qtip="' + v.split("::")[0] + '"';
                            return v.split("::")[0];
                        } else if (v.indexOf('::') !== -1 && (v.split("::")[0] == 'null')) {
                            return "";
                        } else {
                            return "";
                        }
                    }
                }
                column.push(col);
                fields.push(colArr[1]);
            }
            if (me.isShowProduct) {
                column.push(
                    {
                        text: '已选的产品结构',
                        align: 'center',
                        draggable: true,
                        resizable: true,
                        flex: 1,
                        width: 'auto',
                        // sortable: false,
                        // menuDisabled: true,
                        renderer: function (value, cellmeta, record, rowIndex, columnIndex, store) {
                            var rowNumber = rowIndex + 1;
                            // var productName;
                            // OrientExtUtil.AjaxHelper.doRequest(serviceName + '/formTemplate/getChooseProductTree.rdm', {
                            //     rowNumber: rowNumber,
                            //     checkTempId:checkTempId
                            // }, false, function (response) {
                            //     productName=response.decodedData.results
                            // });
                            // return productName;
                            cellmeta.style = 'overflow:visible;white-space:normal;';
                            cellmeta.tdAttr = 'data-qtip="' + productMap[rowNumber] + '"';
                            return productMap[rowNumber];
                        }
                    }, {
                        xtype: 'actioncolumn',
                        text: '关联产品结构',
                        align: 'center',
                        width: '100px',
                        flex: 1,
                        draggable: true,
                        resizable: true,
                        items: [{
                            iconCls: 'icon-detail',
                            tooltip: '产品结构树',
                            handler: function (grid, rowIndex) {
                                me.grid = grid;
                                me.rowIndex = rowIndex;
                                me.rowNumber = rowIndex + 1;
                                //var data = grid.getStore().getAt(rowIndex);
                                var createWin = Ext.create('Ext.Window', {
                                    title: '产品结构树',
                                    plain: true,
                                    height: 0.5 * globalHeight,
                                    width: 0.5 * globalWidth,
                                    layout: 'fit',
                                    maximizable: true,
                                    modal: true,

                                    items: [Ext.create('OrientTdm.FormTemplateMgr.ProductStructureTree.ProductStructureTree', {
                                        rowNumber: me.rowNumber,
                                        checkTempId: checkTempId,
                                        isInst: me.isInst
                                    })],
                                    buttonAlign: 'center',
                                    buttons: [
                                        {
                                            text: '保存',
                                            iconCls: 'icon-save',
                                            //scope: me,
                                            handler: function () {
                                                var tree = createWin.down('panel');
                                                var nodes = OrientExtUtil.TreeHelper.getSelectNodes(tree)[0];
                                                console.log(OrientExtUtil.TreeHelper.getSelectNodeIds(tree).length);
                                                var level = nodes.raw['level'];
                                                if (level == 2 || level == 3 || level == 4) {
                                                    OrientExtUtil.Common.err(OrientLocal.prompt.error, '只能选择零件或零件子节点');
                                                    return;
                                                }
                                                // if(tree.getChecked().length==0){
                                                //     OrientExtUtil.Common.err(OrientLocal.prompt.error, '未选择任何零件或零件子节点');
                                                //     return;
                                                // }
                                                if (tree.getChecked().length > 1) {
                                                    OrientExtUtil.Common.err(OrientLocal.prompt.error, '只能选择一个零件或零件子节点');
                                                    return;
                                                }
                                                var treeId;
                                                if (tree.getChecked().length == 0) {
                                                    treeId = '';
                                                } else {
                                                    treeId = tree.getChecked()[0].raw['id'];
                                                }
                                                OrientExtUtil.AjaxHelper.doRequest(serviceName + '/formTemplate/saveChooseProductTree.rdm', {
                                                    treeId: treeId,
                                                    rowNumber: me.rowNumber,
                                                    checkTempId: checkTempId
                                                }, false, function (response) {
                                                    if (response.decodedData.success) {
                                                        //Ext.Msg.alert("提示", response.decodedData.msg);
                                                        // OrientExtUtil.AjaxHelper.doRequest(serviceName + '/formTemplate/getChooseProductTree.rdm', {
                                                        //     rowNumber:  me.rowNumber,
                                                        //     checkTempId:checkTempId
                                                        // }, false, function (response) {
                                                        //     me.productName=response.decodedData.results;
                                                        //     var record=me.grid.getStore().getAt(me.rowIndex);
                                                        //     record.set("已选的产品结构",me.productName);
                                                        //     record.commit();
                                                        // });
                                                        if (tree.getChecked().length == 0) {
                                                            productMap[me.rowNumber] = '未选择';
                                                        } else {
                                                            productMap[me.rowNumber] = tree.getChecked()[0].raw['text'];
                                                        }
                                                        var record = me.grid.getStore().getAt(me.rowIndex);
                                                        //单独刷新某一行
                                                        record.commit();
                                                        createWin.close();

                                                    } else {
                                                        Ext.Msg.alert("提示", response.decodedData.msg);
                                                    }
                                                });

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
                                createWin.down('panel').expandAll();
                                createWin.show();
                            }
                        }
                        ]
                    }
                )
            }
            me.column = column;
            me.field = fields;
        });

        // var contentData = [];
        // OrientExtUtil.AjaxHelper.doRequest(serviceName + '/formTemplate/getTemplateCellData.rdm', {
        //     checkTempId: me.checkTempId,
        //     isInst: me.isInst,
        //     withData: Ext.isEmpty(me.withData) ? false : me.withData,
        //     productId:Ext.isEmpty(me.productId)? "":me.productId
        // }, false, function (resp) {
        //     contentData = resp.decodedData.results;
        // });
        //
        // var store = Ext.create("Ext.data.Store", {
        //     fields: fields,
        //     data: contentData
        // });
        //
        // var contentPanel = Ext.create("Ext.grid.Panel", {
        //     region: 'center',
        //     columns: column,
        //     store: store,
        //     viewConfig:{
        //         enableTextSelection:'true',
        //         //getRowClass:function(record,rowIndex,rowParams,store){
        //         //    if(record.data.indexOf(me.productId) !== -1){
        //         //        return 'x-grid-record-red';
        //         //    }
        //         //}
        //     },
        //     forceFit: true
        // });
        // me.removeAll();
        // me.add(contentPanel);
    },

    createColumns: function () {
        var me = this;
        var retVal = me.column;
        return retVal;
    },

    createStore: function () {
        var me = this;
        var retVal = Ext.create('Ext.data.Store', {
            fields: me.field,
            remoteSort: false,
            //设置为 true 则将所有的过滤操作推迟到服务器
            remoteFilter: true,
            proxy: {
                type: 'ajax',
                url: serviceName + "/formTemplate/getTemplateCellData.rdm",
                reader: {
                    type: 'json',
                    root: 'results',
                    totalProperty: 'totalProperty'
                },
                extraParams: {
                    checkTempId: me.checkTempId,
                    isInst: me.isInst,
                    withData: Ext.isEmpty(me.withData) ? false : me.withData,
                    productId: Ext.isEmpty(me.productId) ? "" : me.productId
                }
            },
            sorters: [{
                direction: 'desc'
            }],
            autoLoad: true
        });
        this.store = retVal;
        return retVal;
    },
    //_saveChoose: function (rowNumber,checkTempId) {
    //
    //}
});
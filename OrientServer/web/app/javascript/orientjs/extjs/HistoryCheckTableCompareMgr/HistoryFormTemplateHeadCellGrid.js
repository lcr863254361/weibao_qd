/**
 * Created by User on 2020/12/14.
 */
Ext.define('OrientTdm.HistoryCheckTableCompareMgr.HistoryFormTemplateHeadCellGrid', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    alias: 'widget.historyFormTemplateHeadCellGrid',
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
        },
        showHistoryCheckInstData: function (cellId, checkTempName) {
            var me = this;
            if (!Ext.isEmpty(cellId)) {
                var params = {
                    cellId: cellId.split('::')[1],
                    // headerName:headerName
                };
                var checkInstHeader;
                OrientExtUtil.AjaxHelper.doRequest(serviceName + '/formTemplate/getHistoryCheckInstHead.rdm', params, false, function (resp) {
                    checkInstHeader = resp.decodedData.results;
                });
                var modelGrid = Ext.create("OrientTdm.HistoryCheckTableCompareMgr.HistoryCheckInstShowGridPanel", {
                    region: 'center',
                    cellId: cellId.split('::')[1],
                    checkInstHeader: checkInstHeader,
                    title: '历史检查项数据',
                    checkTempName: checkTempName
                });
                var queryPanel = Ext.create("OrientTdm.HistoryCheckTableCompareMgr.HistoryCheckInstFilterPanel", {
                    region: 'north',
                    title: '查询',
                    height: 120,
                    collapsible: true,
                    bodyStyle: {background: '#ffffff'},
                    cellId: cellId.split('::')[1],
                    columns: JSON.stringify(checkInstHeader.columns),
                    checkTempName: checkTempName
                });
                var mainPanel = Ext.create("OrientTdm.Common.Extend.Panel.OrientPanel", {
                    layout: "border",
                    // layout: 'fit',
                    padding: '0 0 0 0',
                    items: [modelGrid, queryPanel],
                    centerPanel: modelGrid,
                    northPanel: queryPanel
                });
                OrientExtUtil.WindowHelper.createWindow(mainPanel, {
                    title: '查看历史检查数据',
                    iconCls: 'icon-picture',
                    height: 0.8 * globalHeight,
                    width: 0.8 * globalWidth,
                    // maximized: true,
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
        }
    },
    initComponent: function () {
        var me = this;
        me.usePage = false;
        me.getCheckListDetailPanel(me.checkTempId, me.checkTempName);
        me.callParent(arguments);
    },
    getCheckListDetailPanel: function (checkTempId, checkTempName) {
        var me = this;

        var schemaId = TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID;
        var modelId = OrientExtUtil.ModelHelper.getModelId("T_CHECK_CELL_INST", schemaId);

        var column = [];
        var fields = [];
        me.checkTempId = checkTempId;
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
                                // var headerName="#"+p.column.text;
                                // return '<input style="vertical-align:middle" type="checkbox" onclick="return false;"/>是&nbsp;&nbsp;&nbsp;<input style="vertical-align: middle" type="checkbox" onclick="return false;"/>否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" readonly="readonly" value="查看" style="width:50px;color:blue;font-size: medium" onclick="OrientTdm.HistoryCheckTableCompareMgr.HistoryFormTemplateHeadCellGrid.showHistoryCheckInstData(\'' + v +"','"+headerName+"','"+checkTempName+'\')">';
                                return '<input style="vertical-align:middle" type="checkbox" onclick="return false;"/>是&nbsp;&nbsp;&nbsp;<input style="vertical-align: middle" type="checkbox" onclick="return false;"/>否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" readonly="readonly" value="查看" style="width:50px;color:blue;font-size: medium" onclick="OrientTdm.HistoryCheckTableCompareMgr.HistoryFormTemplateHeadCellGrid.showHistoryCheckInstData(\'' + v + "','" + checkTempName + '\')">';
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
                                var headerName = "#" + p.column.text;
                                return '<input style="vertical-align:middle" type="checkbox" onclick="return false;"/>是&nbsp;&nbsp;<input style="vertical-align: middle" type="checkbox" onclick="return false;"/>否&nbsp;&nbsp;<input style="vertical-align: middle" type="checkbox" onclick="return false;"/>无&nbsp;&nbsp;<input type="button" readonly="readonly" value="查看" style="width:50px;color:blue;font-size: medium" onclick="OrientTdm.HistoryCheckTableCompareMgr.HistoryFormTemplateHeadCellGrid.showHistoryCheckInstData(\'' + v + "','" + checkTempName + '\')">';
                            }
                        } else if (v.indexOf('填写') !== -1) {
                            if (v.split('::').length === 3) {
                                //有值的情况下直接返回数值
                                return v.split("::")[2];
                            } else {
                                var headerName = "#" + p.column.text;
                                var width = col.header.length + 100;
                                // return '<input type="text" readonly="readonly" style="width: ' + width + 'px"/>';
                                return '<input type="text" readonly="readonly" style="width:' + width + 'px"/>&nbsp;&nbsp;<input type="button" readonly="readonly" value="查看" style="width:50px;color:blue;font-size: medium" onclick="OrientTdm.HistoryCheckTableCompareMgr.HistoryFormTemplateHeadCellGrid.showHistoryCheckInstData(\'' + v + "','" + checkTempName + '\')">';
                            }
                        } else if (v.indexOf('数字') !== -1) {
                            if (v.split('::').length === 3) {
                                //有值的情况下直接返回数值
                                return v.split("::")[2];
                            } else {
                                var headerName = "#" + p.column.text;
                                var width = col.header.length + 100;
                                return '<input type="text" readonly="readonly" style="width:' + width + 'px"/>&nbsp;&nbsp;<input type="button" readonly="readonly" value="查看" style="width:50px;color:blue;font-size: medium" onclick="OrientTdm.HistoryCheckTableCompareMgr.HistoryFormTemplateHeadCellGrid.showHistoryCheckInstData(\'' + v + "','" + checkTempName + '\')">';
                            }
                        } else if (v.indexOf('时间') !== -1) {
                            if (v.split('::').length === 3) {
                                //有值的情况下直接返回数值
                                return v.split("::")[2];
                            } else {
                                var headerName = "#" + p.column.text;
                                var width = col.header.length + 100;
                                return '<input type="text" readonly="readonly" style="width:' + width + 'px" />&nbsp;&nbsp;<input type="button" readonly="readonly" value="查看" style="width:50px;color:blue;font-size: medium" onclick="OrientTdm.HistoryCheckTableCompareMgr.HistoryFormTemplateHeadCellGrid.showHistoryCheckInstData(\'' + v + "','" + checkTempName + '\')">';
                            }
                        } else if (v.indexOf('经纬度') !== -1) {
                            if (v.split('::').length === 3) {
                                //有值的情况下直接返回数值
                                return v.split("::")[2];
                            } else {
                                var headerName = "#" + p.column.text;
                                var width = col.header.length + 100;
                                return '<input type="text" readonly="readonly" style="width:' + width + 'px"/>&nbsp;&nbsp;<input type="button" readonly="readonly" value="查看" style="width:50px;color:blue;font-size: medium" onclick="OrientTdm.HistoryCheckTableCompareMgr.HistoryFormTemplateHeadCellGrid.showHistoryCheckInstData(\'' + v + "','" + checkTempName + '\')">';
                            }
                        } else if (v.indexOf('拍照') !== -1 || v.indexOf('签署') !== -1) {
                            if (v.indexOf('::') !== -1) {
                                v = v.split("::")[1];
                            } else {
                                v = "";
                            }
                            var htmlStr = '<div class="image_stack" onclick="OrientTdm.HistoryCheckTableCompareMgr.HistoryFormTemplateHeadCellGrid.showPictures(\'' + v + '\')">' +
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
            me.column = column;
            me.field = fields;
        });
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
    /*afterInitComponent: function () {
        var me = this;
        var columns = me.columns;
        Ext.each(columns, function (column) {
            if (column.header == '记录编号') {
                column.hidden = true;
                return false;
            }
        });
        var colLength=columns.length;
        //合并单元格
        var store = me.getStore();
        store.on('load', function () {

            var gridView = document.getElementById(me.getView().getId() + '-body');
            if (gridView == null) {
                return;
            }

            // 2.获取Grid的所有tr
            var trArray = [];
            if (me.layout.type == 'table') { // 若是table部署方式，获取的tr方式如下
                trArray = gridView.childNodes;
            } else {
                trArray = gridView.getElementsByTagName('tr');
            }
            // 1)遍历grid的列，从第2个开始
            var colIndexArray = [];
            (function(){
                for (var i=colLength;i>0;i--){
                    colIndexArray.push(i);
                }
                //数组取反
                colIndexArray.reverse();
            })();
            for (var i = 0, colArrayLength = colIndexArray.length; i < colArrayLength; i++) {
                var colIndex = colIndexArray[i];
                var lastTr = trArray[0]; // 合并tr，默认为第一行数据
                // 2)遍历grid的tr，从第二个数据行开始
                for (var j = 1, trLength = trArray.length; j < trLength; j++) {
                    var thisTr = trArray[j];
                    // 3)2个tr的td内容一样
                    if (lastTr.childNodes[colIndex].innerText == thisTr.childNodes[colIndex].innerText) {
                        // 4)若前面的td未合并，后面的td都不进行合并操作
                        if (i > 0 && thisTr.childNodes[colIndexArray[i - 1]].style.display != 'none') {
                            lastTr = thisTr;
                            continue;
                        } else {
                            // 5)符合条件合并td
                            if (lastTr.childNodes[colIndex].hasAttribute('rowspan')) {
                                var rowspan = lastTr.childNodes[colIndex].getAttribute('rowspan') - 0;
                                rowspan++;
                                lastTr.childNodes[colIndex].setAttribute('rowspan', rowspan);
                            } else {
                                lastTr.childNodes[colIndex].setAttribute('rowspan', '2');
                            }
                            // lastTr.childNodes[colIndex].style['text-align'] = 'center';; // 水平居中
                            lastTr.childNodes[colIndex].style['vertical-align'] = 'middle';
                            ; // 纵向居中
                            thisTr.childNodes[colIndex].style.display = 'none'; // 当前行隐藏
                        }
                    } else {
                        // 5)2个tr的td内容不一样
                        lastTr = thisTr;
                    }
                }
            }
        })
    }
    */
});
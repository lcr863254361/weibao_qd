/**
 * Created by User on 2021/2/23.
 */
Ext.define('OrientTdm.DataAnalysis.HistoryNumberAnalysisMgr.HistoryNumbeFormTemplateGrid', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    alias: 'widget.historyNumbeFormTemplateGrid',
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
        testClick :function (url) {
            Ext.define('WeatherPoint', {
                extend: 'Ext.data.Model',
                fields: ['times','number', 'date']
            });
            var store1 = Ext.create('Ext.data.Store', {
                model: 'WeatherPoint',
                proxy: {
                    type: 'ajax',
                    url: url,
                    reader: {
                        type: 'json',
                        root: 'results'
                    }
                },
                autoLoad: true
                /*    data: [
                        { temperature: 58, date: new Date(2011, 1, 1, 8) },
                        { temperature: 63, date: new Date(2011, 1, 2, 9) },
                        { temperature: 73, date: new Date(2011, 1, 3, 10) },
                        { temperature: 78, date: new Date(2011, 1, 4, 11) },
                        { temperature: 81, date: new Date(2011, 1, 5, 12) }
                    ],*/

            });
            var chart1 = Ext.create('Ext.chart.Chart', {
                renderTo: Ext.getBody(),
                width: 1200,
                height: 500,
                store: store1,
                axes: [
                    {
                        title: '数值',
                        type: 'Numeric',
                        position: 'left',
                        fields: ['number']
                    },
                    {
                        title: '上传时间',
                        type: 'Category',
                        position: 'bottom',
                        fields: ['date'],
                        label:{
                            rotate:{
                                degrees: -60
                            }
                        }

                    }
                ],
                series: [
                    {
                        type: 'line',
                        xField: 'times',
                        yField: 'number',
                        tips: {
                            trackMouse: true,
                            width: 80,
                            height: 40,
                            renderer: function(storeItem, item) {
                                this.setTitle(storeItem.get('number'));
                                this.update(storeItem.get('date'));
                            }
                        }

                    }
                ],
                theme: 'Green'
            });
            Ext.create("Ext.window.Window",{
                title: '折线图窗口',
                width:1200,
                height:500,
                layout:'fit',
                constrain:true,    //限制不超出浏览器
                modal:true,
                constrainHeader:true,//
                renderTo: Ext.getBody(),
                items:chart1
            }).show();
        }
    },
    initComponent: function () {
        var me = this;
        me.usePage = false;
        me.getCheckListDetailPanel(me.checkTempId,me.checkTempName);
        me.callParent(arguments);
    },
    getCheckListDetailPanel: function (checkTempId,checkTempName) {
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
                                // return '<input type="text" readonly="readonly" style="width: ' + width + 'px"/>';
                                return '<input type="text" readonly="readonly"/>';
                            }
                        } else if (v.indexOf('数字') !== -1) {
                            if (v.split('::').length === 3) {
                                //有值的情况下直接返回数值
                                return v.split("::")[2];
                            } else {
                                // var headerName="#"+p.column.text;
                                // var width = col.header.length+100;
                                // return '<input type="text" readonly="readonly" style="width:'+ width + 'px"/>&nbsp;&nbsp;<input type="button" readonly="readonly" value="查看" style="width:50px;color:blue;font-size: medium" onclick="OrientTdm.HistoryCheckTableCompareMgr.HistoryFormTemplateHeadCellGrid.showHistoryCheckInstData(\'' + v +"','"+checkTempName+ '\')">';
                                var cellId = v.split("::")[1];
                                var url = serviceName+"/analysis/generateChart.rdm?cellId="+cellId
                                return "<a href='#'  onclick=\"OrientTdm.DataAnalysis.HistoryNumberAnalysisMgr.HistoryNumbeFormTemplateGrid.testClick(\'"+url+"\'); return false;\">查看折线图</a>"
                            }
                        } else if (v.indexOf('时间') !== -1) {
                            if (v.split('::').length === 3) {
                                //有值的情况下直接返回数值
                                return v.split("::")[2];
                            } else {
                                return '<input type="text" readonly="readonly"/>';
                            }
                        } else if (v.indexOf('经纬度') !== -1) {
                            if (v.split('::').length === 3) {
                                //有值的情况下直接返回数值
                                return v.split("::")[2];
                            } else {
                                return '<input type="text" readonly="readonly"/>';
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
});
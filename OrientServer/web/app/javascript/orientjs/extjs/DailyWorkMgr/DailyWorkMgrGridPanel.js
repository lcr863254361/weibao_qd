Ext.define('OrientTdm.DailyWorkMgr.DailyWorkMgrGridPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.dailyWorkMgrGridPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_DAILY_WORK,
        modelName: TDM_SERVER_CONFIG.DAILY_WORK,
    },

    initComponent: function () {
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            afterInitComponent: me.afterInit,
        });
        //增加附件按钮
        var columns = modelGrid.columns;
        //增加actionColumn
        var actionColumns = me._initActionColumns();
        Ext.Array.insert(columns, columns.length, actionColumns);
        //使用一个新的store/columns配置项进行重新配置生成grid
        modelGrid.reconfigure(modelGrid.getStore(), columns);
        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    },
    afterInit: function () {
        var me = this;
        //排序
        this.getStore().sort([{
            "property": "C_WORK_DATE_" + me.modelId,
            "direction": "ASC"
        },{
            "property": "C_STRUCT_SYSTEM_" + me.modelId,
            "direction": "ASC"
        }]);
        var columns = me.columns;
        // Ext.each(columns, function (column) {
        //     if (column.header == '记录编号') {
        //         column.hidden = true;
        //         return false;
        //     }
        // });
        //隐藏按钮
        // var buttons = me.dockedItems[0].items.items;
        // Ext.each(buttons, function (btn) {
        //     btn.hide();
        // })

        //合并单元格
        // /**
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
             // 1)遍历grid的tr，从第二个数据行开始
             var colIndexArray = [1, 2, 3, 4, 5, 6, 7];
             // var colIndexArray = [1];
             if (trArray.length != 0){
                 for (var i = 0, colArrayLength = colIndexArray.length; i < colArrayLength; i++) {
                     var colIndex = colIndexArray[i];
                     var lastTr = trArray[0]; // 合并tr，默认为第一行数据
                     lastTr.childNodes[colIndex].style['border-right'] = '#000000 1px solid';
                     lastTr.childNodes[colIndex].style['border-top'] = '#000000 1px solid';
                     lastTr.childNodes[colIndex].style['border-left'] = '#000000 1px solid';
                     lastTr.childNodes[colIndex].style['border-bottom'] = '#000000 1px solid';
                     // 2)遍历grid的tr，从第二个数据行开始
                     for (var j = 1, trLength = trArray.length; j < trLength; j++) {
                         var thisTr = trArray[j];
                         // 3)2个tr的td内容一样
                         if (lastTr.childNodes[colIndex].innerText == thisTr.childNodes[colIndex].innerText) {
                             // 4)若前面的td未合并，后面的td都不进行合并操作
                             if (i > 0 && thisTr.childNodes[colIndexArray[i - 1]].style.display != 'none') {
                                 thisTr.childNodes[colIndex].style['border-right'] = '#000000 1px solid';
                                 thisTr.childNodes[colIndex].style['border-top'] = '#000000 1px solid';
                                 thisTr.childNodes[colIndex].style['border-left'] = '#000000 1px solid';
                                 thisTr.childNodes[colIndex].style['border-bottom'] = '#000000 1px solid';
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
                                 lastTr.childNodes[colIndex].style['border-right'] = '#000000 1px solid';
                                 lastTr.childNodes[colIndex].style['border-top'] = '#000000 1px solid';
                                 lastTr.childNodes[colIndex].style['border-left'] = '#000000 1px solid';
                                 lastTr.childNodes[colIndex].style['border-bottom'] = '#000000 1px solid';
                                 lastTr.childNodes[colIndex].style['background-color'] = 'fafaf';
                                 thisTr.childNodes[colIndex].style.display = 'none'; // 当前行隐藏
                             }
                         } else {
                             // 5)2个tr的td内容不一样
                             thisTr.childNodes[colIndex].style['border-right'] = '#000000 1px solid';
                             thisTr.childNodes[colIndex].style['border-top'] = '#000000 1px solid';
                             thisTr.childNodes[colIndex].style['border-left'] = '#000000 1px solid';
                             thisTr.childNodes[colIndex].style['border-bottom'] = '#000000 1px solid';
                             lastTr = thisTr;
                         }
                     }
                 }
         }
        })
         // **/
         },


        _initActionColumns: function () {
            var me = this;
            var width = 0.1 * globalWidth;
            var retVal = {
                xtype: 'actioncolumn',
                text: '附件',
                align: 'center',
                width: width,
                items: [{
                    iconCls: 'icon-detail',
                    tooltip: '附件',
                    handler: function (grid, rowIndex) {
                        var data = grid.getStore().getAt(rowIndex);
                        var dailyWorkId = data.raw.ID;
                        var dailyWorkAttachPanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                            region: 'center',
                            items: [{
                                layout: "fit",
                                html: '<iframe  id="dailyWorkAttachIframe" frameborder="0" width="80%" height="80%" style="margin-left: 0px;" src="' + 'dailyWork/dailyWorkAttachDetail.rdm?dailyWorkId=' + dailyWorkId
                                    + '"></iframe>'
                            }]
                        });
                        var win = Ext.create('Ext.Window', {
                                plain: true,
                                id: 'dailyWorkAttachWin',
                                title: '附件详情',
                                height: 0.75 * globalHeight,
                                width: 0.75 * globalWidth,
                                layout: 'fit',
                                maximizable: true,
                                modal: true,
                                autoScroll: true,
                                constrain: true,   //限制窗口不超出浏览器边界
                                listeners: {
                                    'afterrender': function () {
                                        window.onresize = function () {
                                            if (Ext.getCmp("dailyWorkAttachWin") == undefined) {
                                                return;
                                            }
                                            changeFrameHeight(dailyWorkAttachPanel, win);
                                        };
                                        changeFrameHeight(dailyWorkAttachPanel, win);
                                    },
                                },
                                items: [
                                    dailyWorkAttachPanel
                                ]
                            }
                        )
                        win.show();

                    }
                }]
            };
            return null == retVal ? [] : [retVal];
        }
    ,
    });

function changeFrameHeight(dailyWorkAttachPanel, win) {
    win.center();
    var cwin = document.getElementById('dailyWorkAttachIframe');
    cwin.width = win.getWidth();
    cwin.height = win.getHeight() - 38;
    dailyWorkAttachPanel.setHeight(cwin.height);
    dailyWorkAttachPanel.setWidth(cwin.width);
}
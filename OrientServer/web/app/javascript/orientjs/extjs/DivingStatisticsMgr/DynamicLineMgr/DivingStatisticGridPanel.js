Ext.define('OrientTdm.DivingStatisticsMgr.DynamicLineMgr.DivingStatisticGridPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.divingStatisticGridPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_DIVING_STATISTICS,
        modelName: TDM_SERVER_CONFIG.DIVING_STATISTICS,
    },
    requires: [
        "OrientTdm.DivingStatisticsMgr.DynamicLineMgr.DynamicLineUpdateDashBord"
    ],
    initComponent: function () {
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            queryUrl:serviceName + '/divingStatisticsMgr/getDivingStatisticGridData.rdm',
            afterInitComponent: function () {
                //构建完表格后 定制操作
                var toolbar = this.dockedItems[0];
                toolbar.add(
                    {
                        iconCls: 'icon-update',
                        text: '动态修改显示列',
                        disabled: false,
                        itemId: 'update',
                        scope: me,
                        handler: me.updateShowLine
                    },{
                        iconCls: 'icon-export',
                        text: '导出潜次统计信息',
                        disabled: false,
                        itemId: 'export',
                        scope: me,
                        handler: me.exportDivingStatisticsData
                    });
                // var selectButton = this.dockedItems[0].down('button[text=查询]');
                // if (selectButton) {
                //     selectButton.handler = Ext.bind(me.filterStatisticData, me, [this], true);
                // }
            },
            refreshGridByQueryFilter: function () {
                var me = this;
                var customerFilter = me.getCustomerFilter();
                var queryFilter = me.getQueryFilter();
                if (queryFilter.length>0){
                    var newQueryFilter=[];
                    Ext.each(queryFilter,function (everyFilter,index) {
                        if (everyFilter.filterName=='DIVINGDENTRYTIME_'+modelId||everyFilter.filterName=='HATCHOPENTIME_'+modelId){
                            var filterValue=everyFilter.filterValue;
                            var valueArr = filterValue.split("<!=!>");
                            if (valueArr.length == 2) {
                                var startDateTime = valueArr[0];
                                var endDateTime = valueArr[1];
                                if (!Ext.isEmpty(startDateTime)){
                                    var startDateTimeArray=startDateTime.split(" ");
                                    var startTime=startDateTimeArray[1];
                                    var hour = startTime.split(':')[0];
                                    var min = startTime.split(':')[1];
                                    var sec = startTime.split(':')[2];
                                    var s = Number(hour * 3600) + Number(min * 60) + Number(sec);
                                    var ms=s * 1000;
                                    startDateTime=ms;
                                }
                                if (!Ext.isEmpty(endDateTime)){
                                    var endDateTimeArray=endDateTime.split(" ");
                                    var endTime=endDateTimeArray[1];
                                    var hour = endTime.split(':')[0];
                                    var min = endTime.split(':')[1];
                                    var sec = endTime.split(':')[2];
                                    var s = Number(hour * 3600) + Number(min * 60) + Number(sec);
                                    var ms=s * 1000;
                                    endDateTime=ms;
                                }
                                if (!Ext.isEmpty(startDateTime) || !Ext.isEmpty(endDateTime)) {
                                    everyFilter.filterValue=startDateTime+"<!=!>"+endDateTime;
                                    newQueryFilter.push(everyFilter);
                                    //数组中这样删除是错的
                                    // queryFilter.splice(index,1);
                                    // queryFilter.push(everyFilter);
                                }
                            }
                        }else{
                            newQueryFilter.push(everyFilter);
                        }
                    });
                }
                var combineFilter = Ext.isEmpty(customerFilter) ? newQueryFilter : Ext.Array.merge(customerFilter, newQueryFilter);
                //从表格对象中获取自定义过滤条件
                var gridStore = me.getStore();
                var proxy = gridStore.getProxy();
                proxy.setExtraParam("customerFilter", Ext.isEmpty(combineFilter) ? "" : Ext.encode(combineFilter));
                me.getSelectionModel().clearSelections();
                var lastOptions = gridStore.lastOptions;
                lastOptions.page=1;
                gridStore.reload(lastOptions);
            },
        });

        var columns = modelGrid.columns;
        Ext.each(columns, function (column) {
            if (column.dataIndex === 'C_WATER_TIME_LONG_'+modelId||column.dataIndex === 'C_WORK_TIME_LONG_'+modelId||column.dataIndex ==='DIVINGDOUTWATERTIME_'+modelId
                ||column.dataIndex ===Ext.util.Format.uppercase('bufangCommandTime')+ '_'+modelId||column.dataIndex ===Ext.util.Format.uppercase('personComeinCabinT')+ '_'+modelId
                ||column.dataIndex ===Ext.util.Format.uppercase('hatchCloseTime')+ '_'+modelId||column.dataIndex ===Ext.util.Format.uppercase('ballastRemoveTime')+ '_'+modelId
                ||column.dataIndex ===Ext.util.Format.uppercase('divingDEntryTime')+ '_'+modelId ||column.dataIndex ===Ext.util.Format.uppercase('startFillWaterTime')+ '_'+modelId
                ||column.dataIndex ===Ext.util.Format.uppercase('startWorkTime')+ '_'+modelId ||column.dataIndex ===Ext.util.Format.uppercase('endWorkTime')+ '_'+modelId
                ||column.dataIndex ===Ext.util.Format.uppercase('floatWaterTime')+ '_'+modelId ||column.dataIndex ===Ext.util.Format.uppercase('recoverDeckTime')+ '_'+modelId
                ||column.dataIndex ===Ext.util.Format.uppercase('hatchOpenTime')+ '_'+modelId ||column.dataIndex ===Ext.util.Format.uppercase('personOutCabinTime')+ '_'+modelId
                ||column.dataIndex ===Ext.util.Format.uppercase('onceBufangboatTime')+ '_'+modelId ||column.dataIndex ===Ext.util.Format.uppercase('onceRecoverboatTime')+ '_'+modelId
                ||column.dataIndex ===Ext.util.Format.uppercase('twiceBufangboatTime')+ '_'+modelId ||column.dataIndex ===Ext.util.Format.uppercase('twiceRecoverboatTime')+ '_'+modelId
            ) {
                column.renderer = function (value, cellmeta, record, rowIndex, columnIndex, store) {
                    var retVal = '无';
                    if (!Ext.isEmpty(value)&&value!='无') {
                        var h=parseInt(value/(60*60*1000));
                        var min=parseInt((value-h*(60*60*1000))/(60*1000));
                        var s=parseInt((value-h*(60*60*1000)-(min*60*1000))/1000);
                        if (min==0){
                            min='00';
                        }else if (min<10){
                            min='0'+min;
                        }
                        if (s==0){
                            s='00';
                        }else if (s<10){
                            s='0'+s;
                        }
                        retVal=h+":"+min+":"+s;
                    }
                    return retVal;
                }
            }
        });
        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    },
    updateShowLine: function () {
        var me = this;
        var filter=new Object();
        filter.name=me.templateName;
        var originalData;
        $.ajax({
            url: serviceName + '/modelGridView/list.rdm?page=1&limit=1',
            type: "post",
            async: false,
            data: filter,
            success: function (resp) {
                originalData=Ext.decode(resp).results[0];
            }
        });
        // OrientExtUtil.AjaxHelper.doRequest(serviceName + '/modelGridView/list.rdm', {
        //     page:1,
        //     limit:1,
        //     filter: filter
        // }, false, function (resp) {
        //     var data = resp.decodedData.results;
        // });
        var updateConfig = {
            title: '修改数据模板',
            formConfig: {
                formClassName: "OrientTdm.DivingStatisticsMgr.DynamicLineMgr.DynamicLineUpdateDashBord",
                appendParam: function () {
                    return {
                        bindModelName: "FREEMARK_TEMPLATE",
                        originalData:originalData,
                        successCallback: function () {
                            me.fireEvent("refreshGrid");
                            this.up("window").close();
                        }
                    }
                }
            },
            buttons: [
                {
                    iconCls: 'icon-save',
                    text: '保存',
                    handler: function () {
                        this.up("window").down("modelGridUpdateDashBord").doSave(
                            function () {
                                var centerPanel=me.ownerCt;
                                centerPanel.items.each(function (item, index) {
                                    centerPanel.remove(item);
                                });
                                var divingStatisticGridPanel = Ext.create('OrientTdm.DivingStatisticsMgr.DynamicLineMgr.DivingStatisticGridPanel', {
                                    region: 'center'
                                });
                                centerPanel.add(divingStatisticGridPanel);
                                //刷新grid
                                // me.down('grid').store.load();
                                // me.fireEvent("refreshGrid");
                                this.up("window").close();
                            }
                        );
                    }
                },
                {
                    iconCls: 'icon-close',
                    text: '关闭',
                    handler: function () {
                        this.up("window").close();
                    }
                }
            ]
        };
        if (updateConfig.formConfig.appendParam) {
            var extraParam = updateConfig.formConfig.appendParam.call(this);
            Ext.apply(updateConfig.formConfig, extraParam);
        }
        var updateWin = Ext.create('Ext.Window', Ext.apply({
            plain: true,
            height: 0.7 * globalHeight,
            width: 0.7 * globalWidth,
            layout: 'fit',
            maximizable: true,
            modal: true,
            items: [
                Ext.create(updateConfig.formConfig.formClassName, updateConfig.formConfig)
            ]
        }, updateConfig));
        updateWin.show();
    },
    exportDivingStatisticsData: function () {
        var me = this;
        var grid = me.modelGrid;
        var selections = grid.getSelectionModel().getSelection();
        var toExportIds = OrientExtUtil.GridHelper.getSelectRecordIds(grid);
        var exportAll = false;
        if (selections.length === 0) {
            Ext.MessageBox.confirm('提示', '是否导出所有潜次统计信息？', function (btn) {
                if (btn == 'yes') {
                    exportAll = true;
                    window.location.href = serviceName + '/divingStatisticsMgr/exportDivingStatisticsData.rdm?exportAll=' + exportAll + '&toExportIds=' + toExportIds;
                }
            });
        } else {
            window.location.href = serviceName + '/divingStatisticsMgr/exportDivingStatisticsData.rdm?exportAll=' + exportAll + '&toExportIds=' + toExportIds;
        }
    },
    filterStatisticData: function (btn, event, modelGridPanel) {
        var me = this;
    }
});
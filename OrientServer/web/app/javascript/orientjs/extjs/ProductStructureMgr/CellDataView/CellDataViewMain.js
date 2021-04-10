/**
 * Cell 卡片式布局聚合面板
 * Created by liuyangchao on 2019/3/11.
 */
/**
 * Created by User on 2019/1/21.
 */

Ext.define('OrientTdm.ProductStructureMgr.CellDataView.CellDataViewMain', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.CellDataViewMain',
    requires: [],
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_CURRENT_CHECK_RECORD,
        modelName: TDM_SERVER_CONFIG.CHECK_TEMP_INST,
        columnData: '',
        dataviewData: '',
        productId: '',
        deviceInstId: '',
        test: ''
    },

    initComponent: function () {
        var me = this;
        var pngPath = serviceName+'/app/images/cards/card-detail.png';

        var dataview = Ext.create('Ext.view.View', {
            id: 'cardmain1',
            deferInitialRefresh: false,
            store: me.test,
            multiSelect: true,
            trackOver: true,
            overItemCls: 'x-item-over',
            itemSelector: 'div.dataview-normal',
            emptyText: '暂无数据',
            autoScroll : true,
            tpl  : Ext.create('Ext.XTemplate',
                    '<tpl for=".">',
                    '<div class="dataview-normal">',

                    '<div class="{[this.isExp(values.isException)]}" onclick="alert("ddd")">',
                        '<div style="width:80%; margin-left: 15px; color: #F0F0F0; font-size: 15px; float: left; text-align: left">',
                            '<span>{mainContent}</span>',
                        '</div>',
                        // '<div class="dataview-detail">',
                        // '</div>',
                    '</div>',

                    '<div class="{[this.isContent(values.isException)]}">',
                        '<div style="height: 50%; width:100%; font-size: 12px; display: flex; text-align: center; align-items: center; font-weight:bold; color: #F0F0F0;">',
                        '<div style="width: 50%; float:left;">',
                        '<span>{leftContent}</span>',
                        '</div>',
                        '<div style="width: 50%; float:right;">',
                        '<span>{rightContent}</span>',
                        '</div>',
                        '</div>',

                        '<div style="height: 50%; width:100%; font-size: 12px; display: flex; text-align: center; align-items: center; font-weight:bold; color: #F0F0F0;">',
                        '<div style="width: 50%; float:left;">',
                        '<span>{checker}</span>',
                        '</div>',
                        '<div style="width: 50%; float:right;">',
                        '<span>{uploadDate}</span>',
                        '</div>',
                        '</div>',
                    '</div>',

                    '</div>',
                    '</tpl>',
                    {
                        isExp:function(isException){
                            console.log(isException);
                            if(isException === '是'){
                                return "dataview-header-error";
                            }else{
                                return "dataview-header ";
                            }
                        },
                        isContent:function(isException){
                            console.log(isException);
                            if(isException === '是'){
                                return "dataview-content-error";
                            }else{
                                return "dataview-content";
                            }
                        },
                    }
                ),
            listeners: {
                itemclick: {
                    fn:function(view, record, item, index, evt, eOpts){
                        var items = [];
                        var count = 0;
                        var instanceId = '';
                        $.each(record.data, function(key, value){
                            var displayFront = me.columnData[count++].description;
                            var displayFront_name = me.columnData[--count].name;
                            count++;
                            var displayEnd = value;
                            var item = {};
                            item.xtype = 'displayfield';
                            item.fieldLabel = displayFront;
                            item.value = displayEnd;
                            items.push(item);
                            if(displayFront_name == 'checkInstId'){
                                instanceId = displayEnd;
                            }
                        });
                        var panel2 = this.up('panel').up('panel').down('panel');
                        panel2.loadRecord(items);

                        // var instanceId=data.raw.ID;
                        Ext.create('Ext.Window', {
                            plain: true,
                            title: '预览',
                            height: globalHeight * 0.9,
                            width: globalWidth * 0.9,
                            layout: 'fit',
                            maximizable: true,
                            modal: true,
                            autoScroll: true,
                            items: [
                                Ext.create('OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel', {
                                    instanceId: instanceId
                                })
                            ]}).show();

                    }
                },
            }
        });

        Ext.apply(me, {
            layout: 'border',
            items: [dataview],
            centerPanel: dataview
        });
        me.callParent(arguments);
    },
    //初始化Column+Store的数据
    // initStore: function () {
    //     var me = this;
    //     if (!Ext.isEmpty(me.productId)) {
    //         OrientExtUtil.AjaxHelper.doRequest(serviceName + '/ProductStructrue/queryCellDataView.rdm', {
    //                 productId: me.productId,
    //                 deviceInstId: me.deviceInstId
    //             },
    //             false, function (response) {
    //                 console.log(response);
    //                 me.columnData = response.decodedData.results.column;
    //                 me.dataviewData = response.decodedData.results.data;
    //             })
    //     }
    // },


    // tpl  : Ext.create('Ext.XTemplate',
    //     '<tpl for=".">',
    //     '<div class="{[this.isExp(values.isException)]}">',
    //     '<div style="height: 144px; background-color:#F0F0F0; font-size: 20px; ">',
    //
    //     '<div style="height: 50%; text-align: center; display: flex; align-items: center;">',
    //     //'<tpl if="this.isGirl("+"[C_EXCEPTION_"+me.modelId+"])"=="是">',
    //     '<span id="main"; style="margin-right: auto; margin-left: auto;">{mainContent}</span>',
    //     //'</tpl>',
    //     '</div>',
    //
    //     '<div style="height: 25%; font-size: 12px; text-align: center; display: flex; align-items: center;">',
    //     '<div style="width: 49%; float:left;">',
    //     '<span>{leftContent}</span>',
    //     '</div>',
    //     '<div style="background-color:#01AAED; width: 2px; height: 60%; float:left;"></div>',
    //     '<div style="width: 49%; float:right;">',
    //     '<span>{rightContent}</span>',
    //     '</div>',
    //     '</div>',
    //
    //     '<div style="height: 25%; font-size: 12px; text-align: center; display: flex; align-items: center;">',
    //     '<div style="width: 49%; float:left;">',
    //     '<span>{checker}</span>',
    //     '</div>',
    //     '<div style="background-color:#01AAED; width: 2px; height: 60%; float:left;"></div>',
    //     '<div style="width: 49%; float:right;">',
    //     '<span>{uploadDate}</span>',
    //     '</div>',
    //     '</div>',
    //
    //     '</div>',
    //     '<div class="showmore" style="padding: 10px; height: 36px; background-color:#01AAED; color: #F0F0F0">',
    //     '<span>显示详情</span>',
    //     '</div>',
    //     '</div>',
    //     '</tpl>',
    //     {
    //         isExp:function(isException){
    //             console.log(isException);
    //             if(isException === '是'){
    //                 return "dataview-error";
    //             }else{
    //                 return "dataview-normal";
    //             }
    //         }
    //     }
    // ),
});

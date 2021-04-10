/**
 * Created by LiuYangChao on 2019/2/16 .
 */
Ext.define('OrientTdm.Common.Extend.DataView.OrientDataViewPolyPanel', {
    extend: 'Ext.panel.Panel',
    alternateClassName: 'OrientExtend.Panel',
    alias: 'widget.OrientDataViewPolyPanel',
    requires: [
        "OrientTdm.Common.Extend.DataView.OrientFeedView",
        "OrientTdm.Common.Extend.DataView.OrientDataViewMainPanel"
    ],
    loadMask: true,
    config: {
        mainContent: '',
        leftContent: '',
        RightContent: '',
        mainKey: '',
        leftKey: '',
        rightKey: '',
        leftKey1: '',
        rightKey1: '',
        //--ModelGrid--//
        isView: '',
        //初始过滤条件
        customerFilter: [],
        //查询过滤条件
        queryFilter: [],
        modelDesc: {},
        queryUrl: '',
        createUrl: '',
        updateUrl: '',
        deleteUrl: '',
        dataList: null,
        loadDataFirst: true,
        formInitData: null,
        showAnalysisBtns: true,
        //--ModelGrid--//
        //前后处理器
        beforeInitComponent: Ext.emptyFn,
        afterInitComponent: Ext.emptyFn,
        actionItems: [],
        productId:''
    },
    initComponent: function () {
        var me = this;
        me.beforeInitComponent.call(me);
        //Border布局自动绑定引用
        if (me.layout == 'border') {
            Ext.each(me.items, function (item) {
                if (item) {
                    region = item.region;
                    me[region + 'PanelComponent'] = item;
                }
            });
        };
        var winMain = new Ext.create('OrientTdm.Common.Extend.DataView.OrientDataViewMainPanel', {
            id: 'cardmain',
            //title: 'Main Content',
            collapsible: false,
            region: 'center',
            margins: '5 0 0 0',
            columnWidth: 0.7,
            modelId:me.modelId,
            templateId:me.templateId,
            mainKey:me.mainKey,
            leftKey:me.leftKey,
            rightKey:me.rightKey,
            leftKey1:me.leftKey1,
            rightKey1:me.rightKey1,
            isView:0,
            queryUrl:me.queryUrl,
            productId:me.productId
        });
        var footPanel = new Ext.create('Ext.panel.Panel', {
            title: 'Footer',
            region: 'south',
            collapsible: true,
            height: 150,
            minHeight: 75,
            maxHeight: 250,
            html: 'Foot Panel'
        });
        var winNavigation = new Ext.create('OrientTdm.Common.Extend.DataView.OrientInfoPanel', {
            title: '属性详情',
            region: 'east',
            collapsible: true,
            autoScroll : true,
            anchor: '30%',
            columnWidth: 0.3,
            minWidth: 300,
            bodyPadding: 5,
            // html: 'hahaha'
        });
        // var winNavigation = new Ext.create('OrientTdm.Common.Extend.DataView.OrientFeedView', {
        //     title: '属性详情',
        //     region: 'east',
        //     collapsible: true,
        //     autoScroll : true,
        //     anchor: '30%',
        //     bodyPadding: 5,
        //     // html: 'hahaha'
        // });
        this.layout= 'border';
        this.bodyBorder= false;
        this.items= [winNavigation, winMain];
        this.callParent(arguments);
        me.afterInitComponent.call(me);
    },
    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'loadViewTplData', me.loadViewTplData, me);
        me.mon(me, 'loadViewData', me.loadViewData, me);
    },
    onAddFeedClick: function(){
        var win = Ext.create('widget.orientfeedwindow', {

        });
        win.show();
    },
    loadViewTplData: function () {

    },
    loadViewData: function () {

    }
});

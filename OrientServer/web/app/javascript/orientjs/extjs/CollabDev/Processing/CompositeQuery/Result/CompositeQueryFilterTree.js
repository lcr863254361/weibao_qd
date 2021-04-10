Ext.define('OrientTdm.CollabDev.Processing.CompositeQuery.Result.CompositeQueryFilterTree', {
    extend: 'OrientTdm.Common.Extend.Tree.OrientTree',
    alias: 'widget.compositeQueryFilterTree',
    requires: [],
    initComponent: function () {
        var _this = this;

        _this.callParent(arguments);
    },
    initEvents: function () {
        var _this = this;
        _this.callParent();
        _this.mon(_this, 'select', _this.itemClickListener, _this);
    },
    createStore: function () {
        return Ext.create("Ext.data.TreeStore", {
            root: {
                text: '根',
                id: '-1',
                expanded: true,
                children: [
                    {text: '项目', leaf: true, iconCls: 'icon-collabDev-project'},
                    {text: '计划', leaf: true, iconCls: 'icon-collabDev-plan '},
                    {text: '数据包', leaf: true, iconCls: 'icon-collabDev-task'},
                    {text: '研发数据', leaf: true, iconCls: 'icon-collabDev-data '},
                    {text: '文件', leaf: true, iconCls: 'icon-collabDev-file'}
                ]
            },
            listeners: {
                beforeLoad: function (store, operation) {
                }
            }
        });
    },
    itemClickListener: function (tree, record, item) {
        switch (record.data.text) {
            case '项目':
                break;
            case '计划':
                break;
            case '数据包':
                break;
            case '研发数据':
                break;
            case '文件':
                break;
            default:
                break;
        }
    }
});
/**
 * Created by User on 2019/1/12.
 */
Ext.define('OrientTdm.SparePartsMgr.ProductStructureTree', {
    extend: 'OrientTdm.Common.Extend.Tree.OrientTree',
    alias: 'widget.productStructureTree',
    rootVisible: false,

    initComponent: function () {
        var me = this;
        me.callParent(arguments);//调用父类的initComponent方法，并为其传参数
    },

    createStore: function () {
        var me = this;
        var retVal;
        retVal = Ext.create('Ext.data.TreeStore', {
            autoSync: true,
            proxy: {
                type: 'ajax',
                url: serviceName + '/ProductStructrue/getProductTreeNodes.rdm',
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'msg'
                }
            },
            root: {
                text: '深海勇士号产品结构',
                qtip: '深海勇士号产品结构',
                id: '-1',
                iconCls: 'icon-function',
                icon: 'app/images/function/数据建模.png',
                type: 'root',
                expanded: true
            },
            listeners: {
                beforeLoad: function (store, operation) {  //在一个新数据对象请求发出前触发此事件. 如果beforeload的处理函数返回'false', 数据请求将被取消.
                    var node = operation.node;
                    if (node.isRoot()) {
                        store.getProxy().setExtraParam('id', '-1');
                        store.getProxy().setExtraParam('type', "root");
                        store.getProxy().setExtraParam('level', '0');
                        store.getProxy().setExtraParam('version', true);
                    } else {
                        //截断父节点信息 防止嵌套层次太深
                        if (node.raw.parentNode) {
                            node.raw.parentNode = null;
                        }
                        store.getProxy().setExtraParam('id', node.raw.dataId);
                        store.getProxy().setExtraParam('type', node.raw.type);
                        store.getProxy().setExtraParam('level', node.raw.level);
                        store.getProxy().setExtraParam('version', true);
                    }
                }
            },
            sorters: [{
                sorterFn: function (node1, node2) {
                    if (node2.raw.position > node1.raw.position) {
                        return -1;
                    } else if (node2.raw.position < node1.raw.position) {
                        return 1;
                    } else
                        return 0;
                }
            }]
        });

        return retVal;
    },

    createToolBarItems: function () {
        var me = this;
        var retVal = [
            {
                xtype: 'trigger',
                triggerCls: 'x-form-clear-trigger',
                onTriggerClick: function () {
                    this.setValue('');
                    me.clearFilter();
                },
                emptyText: '快速搜索(只能搜索已展开节点)',
                width: "95%",
                enableKeyEvents: true,
                listeners: {
                    keyup: function (field, e) {
                        if (Ext.EventObject.ESC == e.getKey()) {
                            field.onTriggerClick();
                        } else {
                            me.filterByText(this.getRawValue(), "text");
                        }
                    }
                }
            }
        ];
        return retVal;
    },

    //此处在前台页面流程管理以及它的孩子显示出来之后，点击它们才会触发此事件
    itemClickListener: function (tree, record, item) {
        var me = this;
        if (me.ownerCt.centerPanel) {

            switch (record.raw.type) {
                case 'root':
                    me.initCenterPanel2(record);     //点击子流程调用子流程方法，初始化中间面板
                    break;
                default :
                    break;
            }
        }
    },

    initCenterPanel2: function (record) {
        var me = this;
        var centerPanel = me.up('sparePartsManagerDashboard').centerPanel;
        if (record.raw['level'] == 1 || record.raw['level'] == 2) {
            //移除所有面板
            centerPanel.items.each(function (item, index) {
                centerPanel.remove(item);
            });
            var gridPanel;
            if (record.raw['leaf'] && record.raw['text'] != '携带的作业工具' && record.raw['text'] != '舱内携带的作业工具') {
                gridPanel = Ext.create('OrientTdm.SparePartsMgr.SparePartsManagerGrid', {
                    region: 'center',
                    productId: record.raw['id'],
                    leaf: record.raw['leaf'],
                    isCarryTool: false
                });
            }
            // if (record.raw['leaf'] && record.raw['text'] == '携带的作业工具') {
            //     gridPanel = Ext.create('OrientTdm.SparePartsMgr.SparePartsManagerGrid', {
            //         region: 'center',
            //         productId: record.raw['id'],
            //         leaf: record.raw['leaf'],
            //         isCarryTool: true,
            //         isShowLWH:true,
            //         isCabinOutOrIn: 'cabinOut'
            //     });
            // }
            // if (record.raw['leaf'] && record.raw['text'] == '舱内携带的作业工具') {
            //     gridPanel = Ext.create('OrientTdm.SparePartsMgr.SparePartsManagerGrid', {
            //         region: 'center',
            //         productId: record.raw['id'],
            //         leaf: record.raw['leaf'],
            //         isCarryTool: true,
            //         isShowLWH:false,
            //         isCabinOutOrIn: 'cabinIn'
            //     });
            // }
            centerPanel.add({
                //title: '备品备件',
                //iconCls: record.raw['iconCls'],
                layout: 'border',
                items: [gridPanel]
            });
        } else if (record.raw['level'] == 3 || record.raw['leaf'] || !record.raw['leaf']) {
            //移除所有面板
            centerPanel.items.each(function (item, index) {
                centerPanel.remove(item);
            });
            var gridPanel = Ext.create('OrientTdm.SparePartsMgr.SparePartsManagerGrid', {
                region: 'center',
                productId: record.raw['id'],
                leaf: record.raw['leaf'],
                isCarryTool: false,
                isCabinOutOrIn:''
            });
            centerPanel.add({
                //title: '备品备件',
                //iconCls: record.raw['iconCls'],
                layout: 'border',
                items: [gridPanel]
            });
        }
    }
});
/**
 * Created by User on 2019/1/12.
 */
Ext.define('OrientTdm.ProductStructureMgr.ProductStructureTree', {
    extend: 'OrientTdm.Common.Extend.Tree.OrientTree',
    alias: 'widget.productStructureTree',
    rootVisible: false,
    id: 'addproductTree',
    initComponent: function () {
        var me = this;
        me.callParent(arguments);
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
                text: 'root',
                qtip: 'root',
                id: '-1',
                iconCls: 'icon-function',
                icon: 'app/images/function/数据建模.png',
                type: 'root',
                expanded: true,
                level: '0'
            },
            listeners: {
                beforeLoad: function (store, operation) {  //在一个新数据对象请求发出前触发此事件. 如果beforeload的处理函数返回'false', 数据请求将被取消.
                    var node = operation.node;
                    if (node.isRoot()) {
                        store.getProxy().setExtraParam('id', '-1');
                        store.getProxy().setExtraParam('type', "root");
                        store.getProxy().setExtraParam('level', '0');
                        store.getProxy().setExtraParam('version', false);
                    }
                    else {
                        //截断父节点信息 防止嵌套层次太深
                        if (node.raw.parentNode) {
                            node.raw.parentNode = null;
                        }
                        store.getProxy().setExtraParam('id', node.raw.dataId);
                        store.getProxy().setExtraParam('type', node.raw.type);
                        store.getProxy().setExtraParam('level', node.raw.level);
                        store.getProxy().setExtraParam('version', false);
                    }
                    //node.expand();
                },
                //鼠标右击事件
                itemcontextmenu: function (view, record, item, index, e, eOpts) {
                    var destroyFlowId = record.data['Id'];
                    //禁用浏览器的右键响应事件
                    e.preventDefault();
                    e.stopEvent();
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
            }],
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
                width: "66%",
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
            },
            {
                iconCls: 'icon-upload',
                text: '<span style="color: blue; ">' + '上传' + '</span>',
                name: 'create',
                tooltip: '从Excel导入',
                disabled: false,
                handler: Ext.bind(me.createTemplate, me)
            }, {
                iconCls: 'icon-export',
                text: '<span style="color: blue; ">' + '导出' + '</span>',
                name: 'export',
                tooltip: '导出所有产品结构树节点',
                disabled: false,
                handler: Ext.bind(me.exportAllProduct, me)
            }
            //    ,'',{
            //    iconCls:'icon-update',
            //    text:'更新',
            //    name:'update',
            //    tooltip:'从Excel导入',
            //    disabled:false,
            //    handler: Ext.bind(me.updateTemplate, me)
            //}
        ];
        return retVal;
    },

    createTemplate: function () {
        var me = this;
        var win = Ext.create('Ext.Window', {
            title: '新增产品结构树',
            plain: true,
            height: 110,
            width: '40%',
            layout: 'fit',
            maximizable: true,
            modal: true,
            items: [{
                xtype: 'form',
                bodyPadding: 10,
                layout: 'anchor',
                defaults: {
                    anchor: '100%',
                    labelAlign: 'left',
                    msgTarget: 'side',
                    labelWidth: 100
                },
                items: [{
                    xtype: 'filefield',
                    buttonText: '',
                    fieldLabel: '导入产品结构树(.xls或xlsx)',
                    buttonConfig: {
                        iconCls: 'icon-upload'
                    },
                    listeners: {
                        'change': function (fb, v) {
                            if (v.substr(v.length - 3) != "xls" && v.substr(v.length - 4) != "xlsx") {
                                OrientExtUtil.Common.info('提示', '请选择Excel文件!');
                                return;
                            }
                        }
                    }
                }]
            }],
            buttons: [{
                text: '导入',
                handler: function () {
                    var form = win.down("form").getForm();
                    if (form.isValid()) {
                        form.submit({
                            //导入新的产品结构树
                            url: serviceName + '/ProductStructrue/importProductTree.rdm',
                            waitMsg: '导入产品结构树...',
                            success: function (form, action) {
                                OrientExtUtil.Common.info('成功', action.result.msg, function () {
                                    win.close();
                                    //me.dockedItems.items[1].items.items[0].setDisabled(true);
                                    //me.fireEvent("refreshTree",false);
                                    //last modified
                                    //var treepanel = Ext.getCmp("addproductTree");
                                    //treepanel.expandAll();
                                    //treepanel.refreshTree(false);

                                    //new modified
                                    var treepanel = Ext.getCmp("addproductTree");
                                    var store = treepanel.getStore();
                                    //var rootNode = store.getRootNode();

                                    store.load({
                                        //node: rootNode,
                                        callback: function (records) {
                                            //rootNode.appendChild(records);	//添加子节点
                                            //rootNode.set('leaf',false);
                                            //rootNode.expand();
                                            treepanel.expandAll();
                                        }
                                    });
                                });
                            },
                            failure: function (form, action) {
                                //alert("模板导入失败！")
                                // OrientExtUtil.Common.info('失败',action.result.msg,function(){
                                //     win.close();
                                //     //me.fireEvent("refreshTree",false);
                                // });
                                switch (action.failureType) {
                                    case Ext.form.action.Action.CLIENT_INVALID:
                                        OrientExtUtil.Common.err('失败', '文件路径存在错误');
                                        break;
                                    case Ext.form.action.Action.CONNECT_FAILURE:
                                        OrientExtUtil.Common.err('失败', '无法连接服务器');
                                        break;
                                    case Ext.form.action.Action.SERVER_INVALID:
                                        OrientExtUtil.Common.err('失败', action.result.msg);
                                }
                            }
                        });
                    }
                }
            }]
        });
        win.show();
    },

    updateTemplate: function () {
        var me = this;
        var win = Ext.create('Ext.Window', {
            title: '新增产品结构树',
            plain: true,
            height: 100,
            width: '40%',
            layout: 'fit',
            maximizable: true,
            modal: true,
            items: [{
                xtype: 'form',
                bodyPadding: 10,
                layout: 'anchor',
                defaults: {
                    anchor: '100%',
                    labelAlign: 'left',
                    msgTarget: 'side',
                    labelWidth: 90
                },
                items: [{
                    xtype: 'filefield',
                    buttonText: '',
                    fieldLabel: '更新产品结构树',
                    buttonConfig: {
                        iconCls: 'icon-upload'
                    },
                    listeners: {
                        'change': function (fb, v) {
                            if (v.substr(v.length - 3) != "xls" && v.substr(v.length - 4) != "xlsx") {
                                OrientExtUtil.Common.info('提示', '请选择Excel文件!');
                                return;
                            }
                        }
                    }
                }]
            }],
            buttons: [{
                text: '更新',
                handler: function () {
                    var form = win.down("form").getForm();
                    if (form.isValid()) {
                        form.submit({
                            //导入新的检查单模板
                            url: serviceName + '/ProductStructrue/updateProductTree.rdm',
                            waitMsg: '更新产品结构树...',
                            success: function (form, action) {
                                OrientExtUtil.Common.info('成功', action.result.msg, function () {
                                    win.close();
                                    me.fireEvent("refreshTree", false);
                                });
                            },
                            failure: function (form, action) {
                                //alert("模板导入失败！")
                                OrientExtUtil.Common.info('失败', action.result.msg, function () {
                                    win.close();
                                    //me.fireEvent("refreshTree",false);
                                });
                            }
                        });
                    }
                }
            }]
        });
        win.show();
    },

    exportAllProduct:function(){
        Ext.Msg.confirm("友情提示", "是否导出所有的产品结构树节点",
            function (btn) {
                if (btn == 'yes') {
                    window.location.href = serviceName + "/ProductStructrue/exportAllProductNodes.rdm";
                }
            }
        )
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
        var centerPanel = me.up('productStructureDashboard').centerPanel;
        //移除所有面板
        centerPanel.items.each(function (item, index) {
            centerPanel.remove(item);
        });

        var productTreeGridPanel = Ext.create('OrientTdm.ProductStructureMgr.ProductTreeNodeMgr.ProductTreeManagerGrid', {
            region: 'center',
            productId: record.raw['id'],
            leaf: record.raw['leaf'],
            level: record.raw['level']
        });

        if (record.raw['level'] == 1) {
            centerPanel.add({
                title: '分系统管理',
                iconCls: record.raw['iconCls'],
                layout: 'border',
                items: [productTreeGridPanel]
            });
        }else if (record.raw['level'] == 2) {
            centerPanel.add({
                title: '部件管理',
                iconCls: record.raw['iconCls'],
                layout: 'border',
                items: [productTreeGridPanel]
            });
        } else if (record.raw['level'] == 3) {
            centerPanel.add({
                title: '零件管理',
                iconCls: record.raw['iconCls'],
                layout: 'border',
                items: [productTreeGridPanel]
            });
        } else if (record.raw['level'] == 4 ||(record.raw['level']!=1&&record.raw['level']!=2&&record.raw['level']!=3)) {

            var skillDocumentMgr = Ext.create('OrientTdm.ProductStructureMgr.SkillDocumentMgr', {
                region: 'center',
                productId: record.raw['id'],
                isProductStructure: true
            });

            var currentCheckRecordMgr = Ext.create('OrientTdm.ProductStructureMgr.CurrentCheckRecordMgr', {
                region: 'center',
                productId: record.raw['id'],
                nodeContent: record.raw.text
            });

            var historyCheckRecordMgr = Ext.create('OrientTdm.ProductStructureMgr.HistoryCheckRecord.HistoryCheckRecordGrid', {
                region: 'center',
                productId: record.raw['id']
            });

            var checkInstDataMgr = Ext.create('OrientTdm.ProductStructureMgr.ShowCheckTablePanel', {
                region: 'center',
                productId: record.raw['id']
            });

            centerPanel.add({
                title: '零件管理',
                iconCls: record.raw['iconCls'],
                layout: 'border',
                items: [productTreeGridPanel]
            });

            centerPanel.add({
                title: '当前检查记录',
                iconCls: record.raw['iconCls'],
                layout: 'border',
                items: [currentCheckRecordMgr]
            });

            centerPanel.add({
                title: '历史检查记录',
                iconCls: record.raw['iconCls'],
                layout: 'border',
                items: [historyCheckRecordMgr]
            });

            centerPanel.add({
                title: '技术文档',
                iconCls: record.raw['iconCls'],
                layout: 'border',
                items: [skillDocumentMgr]
            });

            centerPanel.add({
                title: '检查表管理',
                iconCls: record.raw['iconCls'],
                layout: 'border',
                items: [checkInstDataMgr]
            });
            centerPanel.setActiveTab(0);
        }
    }
    //itemcontextmenu:function(view,record,item,index,e){
    //    //禁用浏览器右键的相应事件
    //    e.preventDefault();
    //    e.stopEvent();
    //
    //    var contextMenu = Ext.create('Ext.menu.Menu', {
    //        //控制右键菜单位置
    //        float:true,
    //        items:[{
    //            text:"添加",
    //            iconCls:'icon-create',
    //            handler:function(){
    //                this.up("menu").hide();
    //                alert("点击的节点ID是："+record.raw.id+",文字是："+record.raw.text+",父id是："+record.raw.cpid)
    //                var schemaId=TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID;
    //                //弹出新增面板窗口
    //                var createWin = Ext.create('Ext.Window', {
    //                    title: '新增产品部件',
    //                    plain: true,
    //                    height: 0.5 * globalHeight,
    //                    width: 0.7 * globalWidth,
    //                    layout: 'fit',
    //                    maximizable: true,
    //                    modal: true,
    //                    items: [Ext.create('OrientTdm.ProductStructureMgr.TreeNodeChange.AddTreeNodePanel', {
    //                        bindModelName: "T_PRODUCT_STRUCTURE_"+schemaId,
    //                        parentId:record.raw.id
    //                    })]
    //                });
    //                createWin.show();
    //            }
    //        },{
    //            text:"修改",
    //            iconCls:'icon-update',
    //            handler:function(){
    //                alert("点击的节点ID是："+record.raw.id+",文字是："+record.raw.text+",父id是："+record.raw.cpid)
    //            }
    //        },{
    //            text:"删除",
    //            iconCls:'icon-delete',
    //            handler:function(){
    //                this.up("menu").hide();
    //                alert("点击的节点ID是："+record.raw.id+",文字是："+record.raw.text+",父id是："+record.raw.cpid)
    //            }
    //        }]
    //    });
    //    //让右键菜单跟随鼠标位置
    //    contextMenu.showAt(e.getXY());
    //}
});
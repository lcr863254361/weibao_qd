/**
 * Created by User on 2019/1/12.
 */
Ext.define('OrientTdm.FormTemplateMgr.ProductStructureTree.ProductStructureTree',{
    extend:'OrientTdm.Common.Extend.Tree.OrientTree',
    alias:'widget.productStructureTree',
    rootVisible:false,
    config: {
        itemClickCheckchange: Ext.emptyFn,
        rowNumber:'',
        checkTempId:'',
        isInst: ''
    },
    initComponent:function(){
        var me=this;

        me.callParent(arguments);
        me.on("checkchange", me.itemClickCheckchange, me);
    },

    createStore:function(){
        var me=this;
        var checkTableInstId=me.checkTableInstId;
        var retVal;
        retVal=Ext.create('Ext.data.TreeStore',{
            autoSync:true,
            proxy:{
                type:'ajax',
                url:serviceName+'/formTemplate/getProductTreeNodes.rdm',
                reader:{
                    type:'json',
                    successProperty:'success',
                    totalProperty:'totalProperty',
                    root:'results',
                    messageProperty:'msg'
                }
            },
            root:{
                text:'root',
                qtip:'root',
                id:'-1',
                iconCls:'icon-function',
                icon:'app/images/function/数据建模.png',
                type:'root',
                expanded:true,
                checked:false,
                checkModel:'single',
                level:'1'
            },
            listeners: {
                beforeLoad: function (store, operation) {  //在一个新数据对象请求发出前触发此事件. 如果beforeload的处理函数返回'false', 数据请求将被取消.
                    var node = operation.node;
                    if (node.isRoot()) {
                        store.getProxy().setExtraParam('id', '-1');
                        store.getProxy().setExtraParam('type', "root");
                        store.getProxy().setExtraParam('level','1');
                        store.getProxy().setExtraParam('version', true);
                        store.getProxy().setExtraParam('checkTableInstId', checkTableInstId);
                        store.getProxy().setExtraParam('rowNumber',me. rowNumber);
                        store.getProxy().setExtraParam('checkTempId', me.checkTempId);
                        store.getProxy().setExtraParam('isInst', me.isInst);
                    } else {
                        //截断父节点信息 防止嵌套层次太深
                        if (node.raw.parentNode) {
                            node.raw.parentNode = null;
                        }
                        store.getProxy().setExtraParam('id', node.raw.dataId);
                        store.getProxy().setExtraParam('type', node.raw.type);
                        store.getProxy().setExtraParam('checked', true);
                        store.getProxy().setExtraParam('level', node.raw.level);
                        store.getProxy().setExtraParam('version', true);
                        store.getProxy().setExtraParam('checkTableInstId', checkTableInstId);
                        store.getProxy().setExtraParam('rowNumber',me. rowNumber);
                        store.getProxy().setExtraParam('checkTempId', me.checkTempId);
                        store.getProxy().setExtraParam('isInst', me.isInst);
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

    createToolBarItems:function(){
        var me=this;
        var retVal=[
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

    ////此处在前台页面流程管理以及它的孩子显示出来之后，点击它们才会触发此事件
    itemClickListener: function (tree, record, item) {
        var checkedNodes=tree.getChecked();
        var level=record.raw['level'];

        for(var i=0;i<checkedNodes.length;i++){
            var nodes=checkedNodes[i];
            if(record.raw['id']!= nodes.raw['id']&&level==true);
            nodes.set("checked",false)
        }
    }
});
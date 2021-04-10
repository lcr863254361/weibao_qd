/**
 * Created by User on 2019/2/13.
 */
Ext.define('OrientTdm.CurrentTaskMgr.DivingTaskTree',{
    extend:'OrientTdm.Common.Extend.Tree.OrientTree',
    alias:'widget.divingTaskTree',
    rootVisible:true,

    initComponent:function(){
        var me=this;
        me.callParent(arguments);
    },

    createStore:function(){
        var me=this;
        var retVal;
        retVal=Ext.create('Ext.data.TreeStore',{
            autoSync:true,
            proxy:{
                type:'ajax',
                url:serviceName+'/CurrentTaskMgr/getDivingTaskTreeNodes.rdm',
                reader:{
                    type:'json',
                    successProperty:'success',
                    totalProperty:'totalProperty',
                    root:'results',
                    messageProperty:'msg'
                }
            },
            root:{
                text:'当前任务',
                qtip:'当前任务',
                id:'-1',
                iconCls:'icon-function',
                icon:'app/images/function/数据建模.png',
                type:'root',
                expanded:true
            },
            listeners:{
                beforeLoad:function(store,operation){
                    var node=operation.node;
                    if(node.isRoot()){
                        store.getProxy().setExtraParam('id','-1');
                        store.getProxy().setExtraParam('type',"root");
                    }else{
                        store.getProxy().setExtraParam('id',node.raw.dataId);
                        store.getProxy().setExtraParam('type',node.raw.type);
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
        var centerPanel = me.up('currentTaskManagerDashboard').centerPanel;
        //移除所有面板
        centerPanel.items.each(function (item, index) {
            centerPanel.remove(item);
        });
        if(record.raw['leaf']){
            var panel = Ext.create('OrientTdm.TaskPrepareMgr.MxGraphEditor.MxGraphEditorPanel', {
                region: 'center',
                taskId: record.raw['id'],
                //颜色表示进度 无工具栏
                isStatusSearch: me.isStatusSearch,
                //treeNode: treeNode
            });
            var teststageTabPanel = Ext.create('OrientTdm.CurrentTaskMgr.ShowCheckTablePanel', {
                layout: 'fit',
                height: '38%',
                region: 'south',
                animCollapse: true,
                collapsible: true,
                header: true,
                taskId: record.raw['id'],
                //nodeId: me.nodeId,
                //nodeText: me.nodeText
            });
            centerPanel.add({
                //title: '节点设计',
                //iconCls: record.raw['iconCls'],
                layout: 'border',
                items: [panel, teststageTabPanel],
                panel: panel,
                stageTabPanel: teststageTabPanel
            });
        }
    }
});
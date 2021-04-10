/**
 * Created by Administrator on 2016/8/24 0024.
 */
Ext.define('OrientTdm.Collab.common.auditFlow.ChooseAuditAssignGraphPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.chooseAuditAssignGraphPanel',
    requires: [
        'OrientTdm.FlowCommon.flowDiagram.flowDiagram',
        'OrientTdm.FlowCommon.flowDiagram.dataObtainer.collabFlowDataObtainer'
    ],
    padding: '0 0 5 0',
    layout: 'fit',
    config: {
        bindId: '',
        pdId: '',
        beforeInitComponent: Ext.emptyFn,
        afterInitComponent: Ext.emptyFn
    },
    initComponent: function () {
        var me = this;
        me.beforeInitComponent.call(me);
        var sid = new Date().getTime();
        me.sid = sid;
        Ext.apply(me, {
            items: [
                {
                    tbar: [
                        '->', '<span style="color:red;">双击节点即可设置人员</span>'
                    ],
                    listeners: {
                        afterrender: function (panel) {
                            me._doLoadFlowDiagram(panel);
                        }
                    },
                    height: 410,
                    html: '<div id="curflowContainer_' + sid + '" style="z-index:1;position:relative;overflow:hidden;top:0px;right:0px;width:100%;height:100%;border-style:none;border-color:lightgray;"></div>'
                }
            ]
        });
        me.callParent(arguments);
        me.afterInitComponent.call(me);
    },
    initEvents: function () {
        var me = this;
        me.callParent();
    },
    _doLoadFlowDiagram: function (panel) {
        var me = this;
        me.flowDiagram = Ext.create('OrientTdm.FlowCommon.flowDiagram.flowDiagram');
        var flowDataObtainer = Ext.create('OrientTdm.FlowCommon.flowDiagram.dataObtainer.collabFlowDataObtainer');
        me.flowDiagram.init(document.getElementById('curflowContainer_' + me.sid), flowDataObtainer, {pdId: me.pdId});
        me.flowDiagram.addListener(mxEvent.DOUBLE_CLICK, Ext.bind(me._dblclickNode, me));
        me.flowDiagram.showRealName({pdId: me.pdId});
    },
    _setAssign: function () {
        var me = this;
        //设置人员
        var selectedTaskInfo = me.flowDiagram.getCurSelNodeAttr();
        me._openSelectUserWin();
    },
    _openSelectUserWin: function (selectedTaskInfo) {
        var me = this;
        if (Ext.isEmpty(selectedTaskInfo)) {
            Ext.Msg.alert('提示', '未选择待设置节点');
            return;
        } else {
            var selectedValue = selectedTaskInfo['taskAssignerIds'].join(',');
            var item = Ext.create('OrientTdm.Common.Extend.Form.Selector.ChooseUserPanel', {
                selectedValue: selectedValue,
                multiSelect: true,
                saveAction: function (selectedValue, callBack) {
                    if (selectedValue.length > 0) {
                        selectedTaskInfo['taskAssignerIds'] = Ext.Array.pluck(selectedValue, 'id');
                        selectedTaskInfo['taskAssignerNames'] = Ext.Array.pluck(selectedValue, 'userName');
                        selectedTaskInfo['taskAssignerDisplayNames'] = Ext.Array.pluck(selectedValue, 'name');
                        //更新面板
                        me.flowDiagram.updateNodeAssigner(selectedTaskInfo['taskAssignerDisplayNames'].join(','));
                        callBack.call(this);
                    } else {
                        OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.atleastSelectOne);
                    }
                }
            });
            OrientExtUtil.WindowHelper.createWindow(item, {
                title: '设置人员'
            });
        }
    },
    getAssignInfos: function () {
        //获取执行人信息
        var me = this;
        var retVal = [];
        var cells = me.flowDiagram.getAllCells();
        Ext.each(cells, function (cell) {
            if (cell.flowAttrs.taskAssignerNames && cell.flowAttrs.taskAssignerNames.length > 0) {
                retVal.push({
                    taskName: cell.id,
                    assign_username: cell.flowAttrs.taskAssignerNames.join(',')
                });
            }
        });
        return retVal;
    },
    _dblclickNode: function (sender, evt) {
        var cell = evt.getProperty('cell');
        if (cell && cell.isVertex()) {
            if (cell.style == 'task' || cell.style == 'custom') {
                this._openSelectUserWin(cell.flowAttrs);
            }
        }
    }
});
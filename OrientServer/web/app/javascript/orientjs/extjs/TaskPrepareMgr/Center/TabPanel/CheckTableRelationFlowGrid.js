/**
 * Created by User on 2021/3/30.
 */
Ext.define("OrientTdm.TaskPrepareMgr.Center.TabPanel.CheckTableRelationFlowGrid", {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.checkTableRelationFlowGrid',
    initComponent: function () {
        var me = this;
        var comboboxData=me.getFlowNodeInfo(me.taskId);
        me.store = Ext.create('Ext.data.Store', {
            fields: ["id", "flowName"],
            remoteSort: false,
            //设置为 true 则将所有的过滤操作推迟到服务器
            remoteFilter: true,
            data:comboboxData,
            sorters: [{
                direction: 'desc'
            }],
            autoLoad: true
        });
        me.grid = Ext.create('Ext.grid.Panel', {
            renderTo: Ext.getBody(),
            store: me.store,
            selModel: {
                selType: 'checkboxmodel',
                pruneRemoved: false,
                mode: 'MULTI'
            },
            columns: [
                {text: '流程名称', dataIndex: 'flowName', flex: 1, align: 'left|center'}
            ],
        });

        Ext.apply(me, {
            layout: 'fit',
            items: [me.grid],
            modelGrid: me.grid
        });
        me.callParent(arguments);
    },
    getFlowNodeInfo: function (divingTaskId) {
        var me = this;
        var data = [];

        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/getXmlStr.rdm', {
            taskId: divingTaskId
        }, false, function (resp) {
            var xmlString = resp.decodedData.results;
            if (!Ext.isEmpty(xmlString)) {
                var config = mxUtils.load('app/javascript/orientjs/extjs/SetsMgr/MxGraphEditor/Config/diagrameditor4decode.xml').getDocumentElement();
                var editor = new mxEditor(config);
                var doc = mxUtils.parseXml(xmlString);
                var node = doc.documentElement;
                editor.readGraphModel(node);
                var cells = editor.graph.getChildVertices(editor.graph.getDefaultParent());
                Ext.each(cells, function (cell) {
                    var comboboxData = {};
                    comboboxData.id = cell.id;
                    comboboxData.flowName = cell.value.getAttribute('label');
                    data.push(comboboxData);
                })
            }
        });
        return data;
    }
});
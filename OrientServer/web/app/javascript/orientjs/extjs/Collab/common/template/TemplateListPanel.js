/**
 * Created by Seraph on 16/9/22.
 */
Ext.define('OrientTdm.Collab.common.template.TemplateListPanel', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    requires: [
        "OrientTdm.Collab.common.template.model.TemplateListModel"
    ],
    config : {
        personal : true,
        schemaId : null,
        modelName : null,
        mainTab : null
    },
    initComponent: function () {
        var me = this;

        this.callParent(arguments);

        me.initEvents();
        me.on("itemclick", me.itemClickListener, me);
        this.addEvents("filterByFilter");
    },
    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'filterByFilter', me.filterByFilter, me);
    },
    createStore: function () {
        var me = this;
        var readUrl;
        if(me.personal){
            readUrl = serviceName + "/templateInfo/bmTemplates/my.rdm"
        }else{
            readUrl = serviceName + "/templateInfo/bmTemplates.rdm"
        }

        var retVal = Ext.create("Ext.data.Store", {
            autoLoad: true,
            model: 'OrientTdm.Collab.common.template.model.TemplateListModel',
            proxy: {
                type: 'ajax',
                api: {
                    "read": readUrl
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'msg'
                }
            },
            listeners: {
                beforeLoad: function (store, operation) {
                    store.getProxy().setExtraParam('modelName', me.modelName);
                    store.getProxy().setExtraParam('schemaId', me.schemaId);
                }
            }
        });

        this.store = retVal;
        return retVal;
    },
    createToolBarItems: function () {
        var me = this;

        var retVal = [];

        return retVal;
    },
    createColumns: function () {
        var me = this;

        var columns = [
            {
                header: '??????',
                flex: 1,
                sortable: true,
                dataIndex: 'name'
            },
            {
                header: '??????',
                width: 100,
                sortable: true,
                dataIndex: 'version'
            },
            {
                header: '?????????',
                width: 100,
                sortable: true,
                dataIndex: 'createUserDisplayName'
            },
            {
                header: '?????????',
                width: 100,
                sortable: true,
                dataIndex: 'auditUserDisplayName'
            },
            {
                header: '????????????',
                width: 100,
                sortable: true,
                dataIndex: 'createTimeValue'
            }
        ];

        columns.push({
            header: '????????????',
            width: 100,
            sortable: true,
            dataIndex: 'privateTemp',
            renderer: function (value, p, record) {
                if(value){
                    return '???';
                }else{
                    return '???';
                }
            }
        });


        columns.unshift({
            xtype:'actioncolumn',
            header: '??????',
            width: 60,
            dataIndex: 'id',
            items: [{
                iconCls: 'icon-preview',
                tooltip: '??????',
                handler: function(grid, rowIndex, colIndex, item, e, record) {

                    var oldPanel = me.mainTab.queryById("collabTemplatePreviewPanel");
                    if (!Ext.isEmpty(oldPanel)) {
                        me.mainTab.remove(oldPanel);
                    }

                    var thePanel = Ext.create('OrientTdm.Collab.common.template.TemplatePreviewPanel', {
                        title: '????????????"' + record.data.name + '"',
                        closable: true,
                        templateId: record.data.id
                    });
                    me.mainTab.add(thePanel);
                    me.mainTab.setActiveTab(thePanel);

                }
            }, ' ', {
                iconCls: 'icon-delete',
                tooltip: '??????',
                handler: function(grid, rowIndex, colIndex, item, e, record) {

                    var reqParams = {
                        id : record.data.id
                    };
                    OrientExtUtil.AjaxHelper.doRequest(serviceName + '/template/mng/delete.rdm', reqParams, false, function (response) {
                        var respJson = response.decodedData;
                        if(respJson.success){
                            grid.getStore().reload();
                        }
                    });
                },
                isDisabled : function (view, rowIndex, colIndex, item, record) {
                    if(me.personal){
                        if(record.data.privateTemp){
                            return false;
                        }else{
                            return true;
                        }
                    }else{
                        return false;
                    }
                }
            }]
        });

        return columns;
    },
    filterByFilter: function (filter) {
        for (var proName in filter) {

            var proValue = filter[proName];
            this.getStore().getProxy().setExtraParam(proName, proValue);
        }
        this.getStore().loadPage(1);
    }
});
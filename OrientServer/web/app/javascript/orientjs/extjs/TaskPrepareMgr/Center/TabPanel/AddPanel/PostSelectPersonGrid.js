/**
 * Created by User on 2019/4/29.
 */
Ext.define("OrientTdm.TaskPrepareMgr.Center.TabPanel.AddPanel.PostSelectPersonGrid", {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.postSelectPersonGrid',
    id: 'postSelectPersonGridOwner',
    config: {
        pageSize: 25
    },
    initComponent: function () {
        var me = this;
        var attendKeyId = me.attendKeyId;
        //modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        me.store = Ext.create('Ext.data.Store', {
            fields: ["id", "personName", "deptName"],
            remoteSort: false,
            //设置为 true 则将所有的过滤操作推迟到服务器
            remoteFilter: true,
            proxy: {
                type: 'ajax',
                url: serviceName + "/taskPrepareController/querySelectPersonData.rdm?selectPersonIds=" + me.selectPersonIds + "&taskId=" + me.taskId,
                reader: {
                    type: 'json',
                    root: 'results',
                    totalProperty: 'totalProperty'
                },
                extraParams: {}
            },
            sorters: [{
                direction: 'desc'
            }],
            autoLoad: true
        });
        me.store.on("load", this.loadChoosePerson, this);
        var allSelectedRecords = [];
        me.grid = Ext.create('Ext.grid.Panel', {
            renderTo: Ext.getBody(),
            store: me.store,
            selModel: {
                selType: 'checkboxmodel',
                pruneRemoved: false,
                mode: 'MULTI',
                listeners: {
                    select: function (me, record, index, opts) {
                        if (allSelectedRecords.length == 0) {
                            allSelectedRecords.push(record);
                        } else {
                            var count = 0;
                            Ext.each(allSelectedRecords, function (selected) {
                                if (selected.get("id") == record.raw['id']) {
                                    count++;
                                }
                            });
                            if (count == 0) {
                                allSelectedRecords.push(record);
                            }
                        }
                    },
                    deselect: function (me, record, index, opts) {
                        if (index != -1) {
                            Ext.getCmp('postSelectPersonGridOwner').remove(record, allSelectedRecords);
                        }
                    }
                }
            },
            columns: [
                {text: '人员名称', dataIndex: 'personName', flex: 1, align: 'left|center'},
                {text: '部门', dataIndex: 'deptName', flex: 1, align: 'left|center'}
            ],
            tbar: [
                //添加搜素条件
                //{
                //    xtype: 'label',
                //    text: '请输入岗位名称: '
                //},
                {
                    xtype: 'textfield',
                    name: 'keyWord',
                    emptyText: '输入搜索词',
                    listeners: {
                        change: function (field, newValue) {
                            //store.getProxy().setExtraParam('queryName', newValue);
                            me.store.proxy.extraParams.userName = newValue;
                            me.store.loadPage(1);
                        }
                    }
                }
            ]
        });
        me.allSelectedRecords = allSelectedRecords;
        me.bbar = Ext.create('Ext.PagingToolbar', {
            store: me.store,
            displayInfo: true,
            displayMsg: '显示 {0} - {1} 条，共 {2} 条',
            emptyMsg: "没有数据",
            items: [
                {
                    xtype: 'tbseparator'
                },
                {
                    xtype: 'numberfield',
                    labelWidth: 40,
                    width: 100,
                    fieldLabel: '每页',
                    enableKeyEvents: true,
                    value: me.pageSize,
                    listeners: {
                        keydown: function (field, e) {
                            if (e.getKey() == Ext.EventObject.ENTER && !Ext.isEmpty(field.getValue())) {
                                var newPageSize = field.getValue();
                                var store = me.store;
                                store.pageSize = newPageSize;
                                store.loadPage(1);
                            }
                        }
                    }
                }, '条'

            ]
        });

        //store.loadPage(1);
        Ext.apply(me, {
            layout: 'fit',
            items: [me.grid],
            modelGrid: me.grid
        });
        me.callParent(arguments);
    },
    loadChoosePerson: function (records, options, success) {
        var me = this;
        var arr = [];
        var count = 0;
        for (var i = 0; i < options.length; i++) {
            if (options[i].raw["checked"]) {
                arr.push(options[i]);
                if (me.allSelectedRecords.length == 0) {
                    me.allSelectedRecords.push(options[i]);
                } else {
                    Ext.each(me.allSelectedRecords, function (selected) {
                        if (selected.get("id") == options[i].raw['id']) {
                            count++;
                        }
                    });
                    if (count == 0) {
                        me.allSelectedRecords.push(options[i]);
                    }
                }
            } else {
                Ext.each(me.allSelectedRecords, function (selected) {
                    if (selected.get("id") == options[i].raw['id']) {
                        options[i].raw['checked'] = true;
                        arr.push(options[i]);
                    }
                });
            }
        }
        me.grid.getSelectionModel().select(arr);
    },
    indexof: function (val, allSelectedRecords) {
        for (var i = 0; i < allSelectedRecords.length; i++) {
            if (allSelectedRecords[i].raw['id'] == val.get("id")) {
                return i;
            }
        }
        return -1;
    },
    remove: function (val, allSelectedRecords) {
        var index = this.indexof(val, allSelectedRecords);
        if (index != -1) {
            allSelectedRecords.splice(index, 1);
        }
    }
});
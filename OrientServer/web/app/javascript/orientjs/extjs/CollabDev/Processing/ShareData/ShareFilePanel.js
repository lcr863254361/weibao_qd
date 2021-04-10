/**
 * Created by ZhangSheng on 2018/8/20 1407.
 */
Ext.define('OrientTdm.CollabDev.Processing.ShareData.ShareFilePanel', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    alias: 'widget.shareFilePanel',
    requires: [
        'OrientTdm.CollabDev.Processing.Model.ShareFilePanelExtModel',
        'OrientTdm.CollabDev.Processing.ShareData.Form.ShareFileUploadForm',
        'OrientTdm.CollabDev.Processing.ShareData.Form.ShareFileUpdateForm'
    ],
    config: {
        cbShareFolderId: ''
    },
    beforeInitComponent: Ext.emptyFn,
    afterInitComponent: Ext.emptyFn,
    initComponent: function () {
        var me = this;
        me.callParent(arguments);
        this.addEvents('filterByShareFolderId');
    },
    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'filterByShareFolderId', me.filterByShareFolderId, me);
    },
    //视图初始化
    createToolBarItems: function () {
        var me = this;
        var addConfig = {
            title: '新增数据内容',
            height: 0.2 * globalHeight,
            width: 0.4 * globalWidth,
            formConfig: {
                formClassName: 'OrientTdm.CollabDev.Processing.ShareData.Form.ShareFileUploadForm',
                appendParam: function () {
                    return {
                        bindModelName: TDM_SERVER_CONFIG.SHARE_FILE,
                        cbShareFolderId: me.getCbShareFolderId(),
                        successCallback: function (resp, callBackArguments) {
                            me.fireEvent("refreshGrid");
                            if (callBackArguments) {
                                this.up("window").close();
                            }
                        }
                    }
                }
            }
        };

        var updateConfig = {
            title: '修改数据内容',
            height: 0.3 * globalHeight,
            width: 0.5 * globalWidth,
            formConfig: {
                formClassName: 'OrientTdm.CollabDev.Processing.ShareData.Form.ShareFileUpdateForm',
                appendParam: function () {
                    return {
                        bindModelName: TDM_SERVER_CONFIG.SHARE_FILE,
                        originalData: this.getSelectedData()[0],
                        successCallback: function () {
                            me.fireEvent("refreshGrid");
                            this.up("window").close();
                        }
                    }
                }
            }
        };

        var retVal = [{
            iconCls: 'icon-create',
            text: '新增',
            itemId: 'create',
            scope: me,
            handler: Ext.bind(me.onUploadClick, me, [addConfig], false)
        }, {
            iconCls: 'icon-update',
            text: '修改',
            itemId: 'update',
            scope: me,
            handler: Ext.bind(me.onUpdateClick, me, [updateConfig], false)
        }, {
            iconCls: 'icon-delete',
            text: '批量删除',
            disabled: false,
            itemId: 'delete',
            scope: me,
            handler: me.onDeleteClick
        }];
        return retVal;
    },
    createColumns: function () {
        return [
            {
                header: '名称',
                flex: 1,
                sortable: true,
                dataIndex: 'name'
            },
            {
                header: '类型',
                flex: 1,
                //width: 150,
                sortable: true,
                dataIndex: 'fileType'
            },
            {
                header: '版本',
                flex: 1,
                sortable: true,
                dataIndex: 'version'
            },
            {
                header: '上次修改时间',
                xtype: 'datecolumn',
                flex: 1,
                sortable: true,
                format: "Y-m-d H:i:s",
                dataIndex: 'updateTime'
            },
            {
                header: '操作',
                xtype: 'actioncolumn',
                //flex: 1,
                ////操作column固定宽度，宽度比icon宽度略宽
                width: 80,
                align: 'center',
                sortable: false,
                menuDisabled: true,
                items: [{
                    icon: 'app/images/icons/default/common/detail.png',
                    tooltip: '下载',
                    scope: this,
                    handler: function (grid, rowIndex, colIndex, column, event, record) {
                        var shareFileId = record.get("id");
                        window.location.href = serviceName + '/shareFile/downloadShareFile.rdm?shareFileId=' + shareFileId;
                    }
                }]
            }
        ];
    },
    createStore: function () {
        var me = this;
        var retVal = Ext.create('Ext.data.Store', {
            model: 'OrientTdm.CollabDev.Processing.Model.ShareFilePanelExtModel',
            proxy: {
                type: 'ajax',
                //extraParams: {
                //    cbShareFolderId: me.cbShareFolderId
                //},
                api: {
                    "read": serviceName + '/shareFile/list.rdm',
                    "create": serviceName + '/shareFile/create.rdm',
                    "update": serviceName + '/shareFile/update.rdm',
                    "delete": serviceName + '/shareFile/delete.rdm'
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'msg'
                }
            }
        });
        this.store = retVal;
        return retVal;
    },
    filterByShareFolderId: function (cbShareFolderId) {
        this.setCbShareFolderId(cbShareFolderId);
        this.getStore().getProxy().setExtraParam("cbShareFolderId", cbShareFolderId);
        this.getStore().load({
            page: 1
        });
    },
    onUploadClick: function (cfg) {
        var me = this;
        //点击新增按钮时，判断左侧共享文件夹是否选中，选中文件夹节点，才能上传数据文件
        var shareFileTree = me.up('shareFileDashBord').down('shareFileTree');
        if (shareFileTree) {
            var selection = shareFileTree.getSelectionModel().getSelection();
            if (selection.length == 0) {
                OrientExtUtil.Common.err(OrientLocal.prompt.error, "请先选中共享文件夹");
                return;
            }
            else {
                me.onCreateClick(cfg);
            }
        }
    }
});
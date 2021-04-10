/**
 * Created by ZhangSheng on 2018/8/20 1506.
 */
Ext.define('OrientTdm.CollabDev.Processing.ShareData.ShareFileDashBord', {
    extend: 'OrientTdm.CollabDev.Common.BaseVersionPanel',
    alias: 'widget.shareFileDashBord',
    requires: [
        'OrientTdm.CollabDev.Processing.ShareData.ShareFileTree',
        'OrientTdm.CollabDev.Processing.ShareData.ShareFilePanel'
    ],
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            iconCls: 'icon-collabDev-tabShare',
            title: '数据共享区',
            layout: 'border',
            items: [
                {
                    xtype: 'container',
                    title: '占位面板'
                }
            ]
            //items: [listPanel, shareFileTree],
            //items: [listPanel],
            //westPanel: shareFileTree,
            //centerPanel: listPanel
        });
        me.callParent(arguments);
    },
    initEvents: function () {
        var _this = this;
        _this.mon(_this, 'refreshByNewNode', _this.refreshByNewNode, _this);
        _this.callParent();
    },
    refreshByNewNode: function () {
        var _this = this;
        if (_this.getInited() == false) {
            _this.setInited(true);
            _this.removeAll();
            //
            //创建中间面板
            var listPanel = Ext.create('OrientTdm.CollabDev.Processing.ShareData.ShareFilePanel', {
                region: 'center',
                padding: '0 0 0 0',
                title: '数据内容'
            });
            //左侧共享文件夹tree
            var shareFileTree = Ext.create('OrientTdm.CollabDev.Processing.ShareData.ShareFileTree', {
                width: 290,
                minWidth: 290,
                maxWidth: 290,
                region: 'west',
                collabNodeId: _this.nodeId
            });

            _this.add([listPanel, shareFileTree]);
            _this.centerPanel = listPanel;
            _this.westPanel = shareFileTree;
        } else {
            //先清空共享文件表格
            var centerPanel = _this.centerPanel;
            centerPanel.filterByShareFolderId('0');
            //再刷新共享文件夹树
            var westPanel = _this.westPanel;
            westPanel.refreshShareFolderTree(_this.nodeId);
        }
    }
});
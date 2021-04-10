Ext.define('OrientTdm.CollabDev.Common.Navigation.BasePrjTreePanel', {
    extend: 'OrientTdm.Common.Extend.Tree.OrientTree',
    alias: 'widget.basePrjTreePanel',
    config: {
        lastClickNode: null
    },
    requires: [],
    initComponent: function () {
        var _this = this;
        Ext.apply(_this, {
            displayField: 'name'
        });
        _this.callParent(arguments);
    },
    _containerclick: function () {
        //点击空白区域 选中 根节点
        this.getSelectionModel().select(this.getRootNode(), false, true);
    },
    _itemClickListener: function (tree, node) {
        var _this = this;
        //get center panel
        var centerPanel = _this.up('collabDevDashbord').down('collabDevCenterPanel');
        //fire events
        if (_this.getLastClickNode() != node) {
            _this.setLastClickNode(node);
            centerPanel.fireEvent('switchnode', node);
        }
    },
    refreshNode: function (nodeId, refreshParent) {
        var me = this;
        var rootNode = this.getRootNode();
        var currentNode;
        if (nodeId === '-1') {
            currentNode = rootNode;
        } else {
            currentNode = rootNode.findChild('id', nodeId, true) || rootNode;
        }
        var toRefreshNode = currentNode;
        if (refreshParent && currentNode.isRoot() == false) {
            toRefreshNode = currentNode.parentNode;
        }
        this.store.load({
            node: toRefreshNode,
            callback: function (records) {
                Ext.each(records, function (record) {
                    if (record.get('expanded') == true) {
                        record.set('expanded', false);
                        record.expand();
                    }
                });
                me.getSelectionModel().select(currentNode);
            }
        });
    }
});
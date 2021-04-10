/**
 * Created by Administrator on 2018/7/27 0027.
 * 中间元素面板基类，通用操作统一在基类控制
 *
 * 由于relayEvent需要容器在实例化的基础上方能生效，故tab面板采用非延迟加载，子面板采用代理模式，初始化时采用空白面板占位，在首次激活面板时加载真实内容
 */
Ext.define('OrientTdm.CollabDev.Common.BaseVersionPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.baseVersionPanel',
    config: {
        nodeId: '',
        nodeVersion: '',
        nodeName: '',
        bmDataId: '',
        type: '', //节点类型
        status: '',//节点的运行状态
        needRefresh: true,
        inited: false
    },
    requires: [],
    initComponent: function () {
        var _this = this;
        Ext.apply(_this, {
            listeners: {
                activate: function () {

                }
            }
        });
        _this.addEvents(
            /**
             * @event refreshByNewNode
             * Fires when the project tree click a diffrent node
             * @param {Ext.data.NodeInterface} clickd node
             */
            'refreshByNewNode'
        );
        _this.callParent(arguments);
    },
    initEvents: function () {
        var _this = this;
        _this.mon(_this, 'switchnode', _this._fourceRefresh, _this);
        _this.callParent();
    },
    _fourceRefresh: function (node) {
        var nodeId = node.getId();
        var nodeVersion = node.get('version');
        var nodeName = node.get('name');
        var bmDataId = node.get('bmDataId');
        var type = node.get('type');
        var status = node.get('status');
        this.setNodeId(nodeId);
        this.setNodeVersion(nodeVersion);
        this.setNodeName(nodeName);
        this.setBmDataId(bmDataId);
        this.setType(type);
        this.setStatus(status);
        //judge if current panel is active
        var belongTab = this.up('orientTabPanel');
        if (belongTab && belongTab.getActiveTab() == this) {
            this.fireEvent('refreshByNewNode');
        } else
            this.setNeedRefresh(true);
    }
});
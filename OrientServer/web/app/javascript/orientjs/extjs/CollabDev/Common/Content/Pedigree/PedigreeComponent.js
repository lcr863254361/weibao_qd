/**
 *谱系通用组件
 */
Ext.define('OrientTdm.CollabDev.Common.Content.Pedigree.PedigreeComponent', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.pedigreeComponent',
    config: {
        /**
         * 是否展现进度信息
         */
        statusAble: true,
        /**
         * 是否可以编辑
         */
        editAble: false,
        /**
         * 展现类型：children:子节点谱系；brother:兄弟谱系
         */
        startType: 'children',
        /**
         *
         */
        startNodeId: null,
        /**
         * if startNodeVersion is null then query the latest nodes and relations
         */
        startNodeVersion: null,
        /**
         * 是否需要保存
         */
        dirty: false,
        /**
         * 是否隐藏toolbar
         */
        hideButton: false,
        /**
         * 控制后台请求的同步异步
         */
        isAsync: true,
        /**
         * 设计阶段的谱系设置面板，工作项不显示颜色
         */
        isShowColor: true,
        /**
         * 是否初始化画布
         */
        isInit: true
    },
    requires: [
        'OrientTdm.CollabDev.Common.Content.Pedigree.MXClientHelper'
    ],
    initComponent: function () {
        var _this = this;
        //此处传入的mxBoardid需保证其唯一性，采用_this.id标记不同的mxBoard实例，否则会导致同时有多个mxBoard实例时，谱系图加载不出来的问题
        //fixedby TeddyJohnson 2018.9.19
        Ext.apply(_this, {
            bodyStyle: 'padding:5px',
            html: '<div id="mxBoard-' + _this.id + '" style="position:absolute; overflow:hidden; left:20px; top:10px;' +
            ' right:0; bottom:0; z-index:100;"></div>' +
            ' <div id="content" style="padding:4px; z-index:101;"></div>',
            listeners: {
                afterrender: function () {
                    if (_this.isInit) {
                        _this._doInit();
                    }
                }
            },
            tbar: {
                items: [
                    {
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: _this._saveRelation,
                        scope: _this,
                        hidden:_this.hideButton
                    },
                    {
                        text: '自动布局',
                        iconCls: 'icon-autoLayout',
                        handler: _this._autoLayout,
                        scope: _this,
                        hidden:_this.hideButton
                    },
                    {
                        text: '放大',
                        iconCls: 'icon-zoomIn',
                        handler: _this._zoomIn,
                        scope: _this
                    },
                    {
                        text: '缩小',
                        iconCls: 'icon-zoomOut',
                        handler: _this._zoomOut,
                        scope: _this
                    }, '->'
                ]
            }
        });

        _this.addEvents(
            /**
             * 根据新的节点id及版本 刷新谱系面板
             */
            'doRefresh'
        );
        _this.callParent(arguments);
    },
    initEvents: function () {
        var _this = this;
        _this.mon(_this, 'doRefresh', _this.doRefresh, _this);
        _this.callParent();
    },
    _zoomIn: function () {
        var _this = this;
        var graph = _this.graph;
        graph.zoomIn();
    },
    _zoomOut: function () {
        var _this = this;
        var graph = _this.graph;
        graph.zoomOut();
    },
    doRefresh: function (nodeId, nodeVersion) {
        var _this = this;
        if (!_this.isInit) {
            MXClientHelper.initDrawRegion.call(_this, 'mxBoard-' + _this.id, _this.getEditAble(), function () {
                _this.isInit = true;
            });
        }
        _this.setStartNodeId(nodeId);
        _this.setStartNodeVersion(nodeVersion);
        _this._doDraw();
    },
    doClear: function () {
        var _this = this;
        var graph = _this.graph;
        MXClientHelper.doClear(graph);
    },
    _doInit: function () {
        var _this = this;
        MXClientHelper.initDrawRegion.call(_this, 'mxBoard-' + _this.id, _this.getEditAble(), function () {
            if (_this.getDirty() === false) {
                _this.setDirty(true);
                _this.down('toolbar').add('<span style="color: red;">已修改数据谱系，请及时保存</span>');
            }
        });
        //加载内容
        _this._doDraw();
    },
    _doDraw: function () {
        var _this = this;
        var graph = _this.graph;
        var param = {
            nodeId: _this.getStartNodeId(),
            nodeVersion: _this.getStartNodeVersion(),
            queryType: _this.getStartType(),
            queryStatus: _this.getStatusAble()
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/pedigree/mxClient.rdm', param, _this.isAsync, function (resp) {
            var respJson = resp.decodedData;
            if (respJson.success) {
                MXClientHelper.doDraw(graph, resp.decodedData.results.nodes, resp.decodedData.results.relations, _this.isShowColor);
            }
        });
    },
    _saveRelation: function () {
        var _this = this;
        var toolbar = _this.down(toolbar);
        if (_this.getDirty()) {
            //save to DB
            var graph = _this.graph;
            var relations = MXClientHelper.getRelations(graph);
            var params = {
                nodeId: _this.getStartNodeId(),
                nodeVersion: _this.getStartNodeVersion()
            };

            Ext.Ajax.request({
                url: serviceName + "/pedigree/relations.rdm",
                method: 'POST',
                params: params,
                jsonData: relations,
                headers: {
                    "Content-Type": "application/json;charset=UTF-8"
                },
                success: function (response, options) {
                    if (response.decodedData.results) {
                        var tips = toolbar.items.getAt(toolbar.items.length - 1);
                        toolbar.remove(tips);
                        _this.setDirty(false);
                    }
                }
            });
        }

    },
    _autoLayout: function () {
        var _this = this;
        var graph = _this.graph;
        MXClientHelper.doAutoLayout(graph);
    }
});
Ext.define('OrientTdm.CollabDev.Designing.ResultSettings.DevData.Model.DevDataModel', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'datatypeId',
        'dataobjectname',
        'isref',
        'dimension',
        'value',
        'parentdataobjectid',
        'ordernumber',
        'subtypeid',
        'subtypeparentid',
        'fileid',
        'createUser',
        'modifiedUser',
        'modifytime',
        'version',
        'unit',
        'description',
        'isglobal',
        'nodeId',
        'nodeVersion',
        'createBy',
        //额外字段
        'dataTypeShowName',
        //所属基础类型'string','file',...
        'extendsTypeRealName',
        'originalObjId',
        //上游节点的名字
        'upName'
    ]
});
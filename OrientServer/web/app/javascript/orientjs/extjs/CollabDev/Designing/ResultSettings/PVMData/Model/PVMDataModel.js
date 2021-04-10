/**
 * 检查模板前端模型
 */
Ext.define('OrientTdm.CollabDev.Designing.ResultSettings.PVMData.PVMDataModel', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'name',
        'nodeId',
        'checkmodelid',
        'checktablestatus',
        'signroles',
        'signnames',
        'uploaduser',
        'uploadtime',
        'html',
        'remark',
        //前台包装
        'checkmodelid_display'
    ]
});
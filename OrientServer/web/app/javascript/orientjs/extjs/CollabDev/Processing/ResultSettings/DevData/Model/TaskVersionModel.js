/**
 *任务版本model
 */
Ext.define('OrientTdm.CollabDev.Processing.ResultSettings.DevData.Model.TaskVersionModel', {
    extend: 'Ext.data.Model',
    fields: [
        'version',
        'updateUser',
        'updateTime',
        'history',
        'taskNodeId',
        {
            name: 'selectText', convert: seltext
        }
    ]
});

function seltext(v, record) {
    return record.data.version + '-' + record.data.updateUser + '-' + record.data.updateTime
}
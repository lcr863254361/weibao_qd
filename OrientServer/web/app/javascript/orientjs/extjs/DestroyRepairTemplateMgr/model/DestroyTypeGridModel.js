
Ext.define('OrientTdm.DestroyRepairTemplateMgr.model.DestroyTypeGridModel', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'name',
        'plannedEndDate',
        'plannedStartDate',
        'principal',
        'actualStartDate',
        'actualEndDate'
    ]
});
Ext.define('OrientTdm.DestroyRepairTemplateMgr.Gantt.FlowGanttFields', {
    extend: 'Gnt.model.Task',
    nameField : 'name',
    startDateField : 'startDate',
    endDateField : 'endDate',
    // percentDoneField : 'progress',
    fields: [
        {name: 'Id', type: 'string'},
        {name: 'name',  type: 'string'},
        {name: 'startDate',type : 'date', dateFormat : 'Y-m-d' },
        {name: 'endDate', type : 'date', dateFormat : 'Y-m-d' },
        {name: 'taskDutor',type: 'string'},
        {name: 'taskType', type: 'string'},
        {name: 'leaf',  type: 'bool'},
        {name: 'parentId', type: 'string'},
        {name: 'PercentDone', type : 'string'}
    ]
});
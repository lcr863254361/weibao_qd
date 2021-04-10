Ext.define('OrientTdm.DivingStatisticsMgr.StatisticsResultModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'hangciName', mapping: 'hangciName', type: 'string'},
        {name: 'divingTime', mapping: 'divingTime', type: 'string'},
        {name: 'taskName', mapping: 'taskName', type: 'string'},
        {name: 'seaArea', mapping: 'seaArea', type: 'string'},
        {name: 'longitude', mapping: 'longitude', type: 'string'},
        {name: 'latitude', mapping: 'latitude', type: 'string'},
        {name: 'depth', mapping: 'depth', type: 'string'},
        {name: 'waterHours', mapping: 'waterHours', type: 'string'},
        {name: 'homeWorkHours', mapping: 'homeWorkHours', type: 'string'},
        // {name: 'zmrPersons', mapping: 'zmrPersons', type: 'string'},
        {name: 'zuoxianPerson', mapping: 'zuoxianPerson', type: 'string'},
        {name: 'mainDriverPerson', mapping: 'mainDriverPerson', type: 'string'},
        {name: 'youxianPerson', mapping: 'youxianPerson', type: 'string'},
        {name: 'zxCompany', mapping: 'zxCompany', type: 'string'},
        {name: 'company', mapping: 'company', type: 'string'},
        {name: 'homeWorkContent', mapping: 'homeWorkContent', type: 'string'},
        {name: 'sampleSituation', mapping: 'sampleSituation', type: 'string'},
        {name: 'personTotalWeight', mapping: 'personTotalWeight', type: 'string'}
    ]
});
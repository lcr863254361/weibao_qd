Ext.define('OrientTdm.CollabDev.Processing.ViewBoard.Plan.WheelIterationModel', {
    extend: 'Ext.data.Model',
    fields: [
        'wheelNumber',
        'startVersion',
        'endVersion',
        {
            name: 'selectText', convert: seltext
        }
    ]
});

function seltext(v, record) {
    return '第' + record.data.wheelNumber + '轮迭代'
}
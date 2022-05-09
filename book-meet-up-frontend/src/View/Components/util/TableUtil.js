
export default function getEntityColumns(fields, includeId) {
    const entityColumns = fields.map(field => {
        return {
            Header: field.label,
            accessor: field.name
        }
    })
    if (includeId) {
        entityColumns.unshift({
            Header: 'ID',
            accessor: 'id',
        })
    }
    return entityColumns
}

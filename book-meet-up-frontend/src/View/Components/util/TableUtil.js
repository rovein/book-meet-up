import Moment from "moment";

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

export const sortById = (current, next) => {
    return current.id - next.id
}

export const sortByDate = (current, next) => {
    const first = Moment(current.date + 'T' + current.time);
    const second = Moment(next.date + 'T' + next.time);
    if (second > first) return 1;
    else if (first > second) return -1;
    else return 0;
}

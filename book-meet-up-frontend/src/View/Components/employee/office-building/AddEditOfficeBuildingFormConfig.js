export const FIELDS = [
    {
        label: 'FCity',
        inputType: 'text',
        name: 'city',
        pattern: /^([А-Яа-яё]+)|([A-Za-z]+)$/i,
        error: 'ECity'
    },
    {
        label: 'FStreet',
        inputType: 'text',
        name: 'street',
        pattern: /^([А-Яа-яё]+)|([A-Za-z]+)$/i,
        error: 'EStreet'
    },
    {
        label: 'FHouse',
        inputType: 'text',
        name: 'house',
        pattern: /^([0-9]+)|([0-9А-Яа-я]+)|([0-9A-Za-z]+)$/i,
        error: 'EHouse'
    },
    {
        label: 'Name',
        inputType: 'text',
        name: 'name',
        pattern: /^([0-9]+)|([0-9А-Яа-я]+)|([0-9A-Za-z]+)$/i,
        error: 'EName'
    }
]

export const ADD_FORM_NAME = {
    header: 'AddOfficeBuilding',
    submitButton: 'Add'
}

export const EDIT_FORM_NAME = {
    header: 'EditOfficeBuilding',
    submitButton: 'Edit'
}

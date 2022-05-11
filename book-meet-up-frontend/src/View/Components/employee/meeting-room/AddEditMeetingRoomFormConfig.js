export const FIELDS = [
    {
        label: 'FMeetingRoomNumber',
        inputType: 'text',
        name: 'type',
        pattern: /^([А-Яа-яё0-9]+)|([A-Za-z0-9]+)$/i,
        error: 'EMeetingRoomNumber'
    },
    {
        label: 'FMeetingRoomFloor',
        inputType: 'number',
        name: 'amount',
        pattern: /^-?\d+\.?\d*$/,
        error: 'EMeetingRoomFloor'
    },
    {
        label: 'FMeetingRoomInfo',
        inputType: 'text',
        name: 'type',
        pattern: /^([А-Яа-яё0-9]+)|([A-Za-z0-9]+)$/i,
        error: 'EMeetingRoomInfo'
    }
]

export const ADD_FORM_NAME = {
    header: 'AddMeetingRoom',
    submitButton: 'Add'
}

export const EDIT_FORM_NAME = {
    header: 'EditMeetingRoom',
    submitButton: 'Edit'
}

import React from "react";

export const formatEmployeeData = employee => {
    const activatedAccount = "accIsActive"
    const activatedAccountStyle = "text-success"
    const blockedAccount = "accIsBlocked"
    const blockedAccountStyle = "text-danger"

    employee.isLockedTranslate = employee.isLocked ? blockedAccount : activatedAccount
    employee.lockStatusStyle = employee.isLocked ? blockedAccountStyle : activatedAccountStyle
    employee.displayTitle = <div>{employee.firstName}<br/>{employee.lastName}</div>

    return employee
}

export const formatOfficeBuildingData = officeBuilding => {
    officeBuilding.displayTitle = officeBuilding.name
    return officeBuilding
}

export const formatMeetingRoomsData = meetingRoom => {
    meetingRoom.displayTitle = "№ " + meetingRoom.number + ' (' + meetingRoom.floor + ')';
    return meetingRoom;
}

export const sortById = data => {
    return data.sort((current, next) => {
        return current.id - next.id
    })
}

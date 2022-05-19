import React from "react";
import Moment from "moment";
import {BOOKING_STATUSES, getBookingDurations} from "./Constants";

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
    meetingRoom.displayTitle = "№ " + meetingRoom.number;
    return meetingRoom;
}

export const formatBookingData = booking => {
    const dateTime = booking.date + 'T' + booking.time
    booking.displayTitle = Moment(dateTime).format('DD.MM.YYYY HH:mm')

    const duration = getBookingDurations().find(duration => duration.value === booking.duration)
    booking.durationTranslate = duration ? duration.translateKey : booking.duration + " min."

    const bookingStatus = BOOKING_STATUSES.find(status => status.value === booking.status)
    booking.statusTranslate = bookingStatus.translateKey
    booking.statusStyle = 'text-bold ' + bookingStatus.style

    booking.finishTime = Moment(dateTime).add(booking.duration, 'minutes').format('HH:mm')

    return booking;
}

export const sortById = data => {
    return data.sort((current, next) => {
        return current.id - next.id
    })
}

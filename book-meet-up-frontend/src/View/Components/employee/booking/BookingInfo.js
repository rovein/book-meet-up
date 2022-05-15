import {withTranslation} from "react-i18next";
import React, {useEffect, useState} from "react";
import {
    getCurrentOfficeBuilding,
    getCurrentEmployee,
    getCurrentMeetingRoom
} from "../../util/LocalStorageUtils";
import {useParams} from "react-router-dom";
import BookingsByMeetingRoomInfoBlock from "./by-meeting-room/BookingsByMeetingRoomInfoBlock";
import BookingsByEmployeeInfoBlock from "./by-employee/BookingsByEmployeeInfoBlock";
import StoragesByMeetingRoomTable from "./by-meeting-room/BookingsByMeetingRoomTable";
import StoragesByEmployeeTable from "./by-employee/BookingsByEmployeeTable";

function BookingInfo() {
    const {getBy, id} = useParams();
    const [officeBuilding, setOfficeBuilding] = useState({})
    const [meetingRoom, setMeetingRoom] = useState({})
    const [employee, setEmployee] = useState({})

    const isStoragesByMeetingRoom = () => {
        return getBy === 'by-meeting-room';
    }

    const isStoragesByEmployee = () => {
        return getBy === 'by-employee';
    }

    useEffect(() => {
        if (isStoragesByMeetingRoom()) {
            setOfficeBuilding(getCurrentOfficeBuilding())
            setMeetingRoom(getCurrentMeetingRoom())
        } else if (isStoragesByEmployee()) {
            setEmployee(getCurrentEmployee())
        }
    }, [])

    return (
        <div>
            {isStoragesByMeetingRoom() && <BookingsByMeetingRoomInfoBlock meetingRoom={meetingRoom} officeBuilding={officeBuilding}/>}
            {isStoragesByEmployee() && <BookingsByEmployeeInfoBlock employee={employee}/>}
            <div>
                {isStoragesByMeetingRoom() && <StoragesByMeetingRoomTable id={id}/>}
                {isStoragesByEmployee() && <StoragesByEmployeeTable id={id}/>}
            </div>
        </div>
    )
}

export default withTranslation()(BookingInfo)

import React, {useEffect, useState} from "react";
import {withTranslation} from "react-i18next";
import axios from "../../util/ApiUtil";
import DefaultLoader from "../../ui/Loader";
import DataTableComponent from "../../ui/DataTable";
import getEntityColumns from "../../util/TableUtil";
import {FIELDS} from "./AddEditMeetingRoomFormConfig";
import {
    getCurrentOfficeBuildingId,
    setCurrentMeetingRoom,
    setCurrentMeetingRoomId,
    setEditMeetingRoomId
} from "../../util/LocalStorageUtils";

function MeetingRoomsTable() {
    const [data, setData] = useState([])
    const [isLoaded, setIsLoaded] = useState(false)

    useEffect(() => {
        axios.get(`/office-buildings/${getCurrentOfficeBuildingId()}/meeting-rooms`)
            .then(result => {
                const data = result.data;
                setData(data)
                setIsLoaded(true)
            })
    }, [])

    const columns = React.useMemo(() => getEntityColumns(FIELDS, false), [])

    function goToBookingsPage(id) {
        setCurrentMeetingRoomId(id)
        setCurrentMeetingRoom(data.find(meetingRoom => meetingRoom.id === id));
        window.location.href = `./bookings/by-meeting-room/${id}`;
    }

    function editEntity(id) {
        setEditMeetingRoomId(id);
        window.location.href = "./edit-meeting-room";
    }

    const operations = [
        {
            "name": "ToBookings",
            "onClick": goToBookingsPage,
            "className": "btn",
            "onClickPassParameter": "id"
        },
        {
            "name": "Edit",
            "onClick": editEntity,
            "className": "btn",
            "onClickPassParameter": "id"
        },
        {
            "name": "Delete",
            "className": "btn",
            "onClickPassParameter": "id",
            "url": "office-buildings/meeting-rooms/{id}",
        }
    ]

    if (!isLoaded) return <DefaultLoader height={325} width={325}/>;
    return <DataTableComponent displayData={data} displayColumns={columns} operations={operations}/>
}

export default withTranslation()(MeetingRoomsTable);

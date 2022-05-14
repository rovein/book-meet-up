import React, {useEffect, useState} from "react";
import {useTranslation, withTranslation} from "react-i18next";
import axios from "../../util/ApiUtil";
import DefaultLoader from "../../ui/Loader";
import DataTableComponent from "../../ui/DataTable";
import {
    getCurrentOfficeBuildingId,
    getCurrentUserRole,
    setCurrentMeetingRoom,
    setCurrentMeetingRoomId,
    setEditMeetingRoomId
} from "../../util/LocalStorageUtils";
import {ADMIN} from "../../util/Constants";
import {formatMeetingRoomsData} from "../../util/DataFormattingUtil";
import {confirmAlert} from "react-confirm-alert";

function MeetingRoomsTable() {
    const [data, setData] = useState([])
    const [isLoaded, setIsLoaded] = useState(false)

    useEffect(() => {
        axios.get(`/office-buildings/${getCurrentOfficeBuildingId()}/meeting-rooms`)
            .then(result => {
                const data = result.data;
                setData(data.map(formatMeetingRoomsData))
                setIsLoaded(true)
            })
    }, [])

    const columns = React.useMemo(() => [], [])

    const {t} = useTranslation();

    function showMeetingRoomInfo(info) {
        confirmAlert({
            title: t("Info"),
            message: info,
            buttons: [
                {
                    label: t("Ok")
                }
            ],
            closeOnEscape: true,
            closeOnClickOutside: true,
        });
    }

    function goToBookingsPage(id) {
        setCurrentMeetingRoomId(id)
        setCurrentMeetingRoom(data.find(meetingRoom => meetingRoom.id === id));
        window.location.href = `./bookings/by-meeting-room/${id}`;
    }

    function editEntity(id) {
        setEditMeetingRoomId(id);
        window.location.href = "./edit-meeting-room";
    }

    const operations = [{
        "name": "Info",
        "onClick": showMeetingRoomInfo,
        "className": "btn btn-info",
        "onClickPassParameter": "info"
    }]

    if (getCurrentUserRole() === ADMIN) {
        operations.push({
                "name": "Bookings",
                "onClick": goToBookingsPage,
                "className": "btn",
                "onClickPassParameter": "id"
            },
            {
                "name": "Edit",
                "onClick": editEntity,
                "className": "btn btn-danger-warning",
                "onClickPassParameter": "id"
            },
            {
                "name": "Delete",
                "className": "btn btn-danger",
                "onClickPassParameter": "id",
                "url": "office-buildings/meeting-rooms/{id}",
            });
    }

    if (!isLoaded) return <DefaultLoader height={325} width={325}/>;
    return <DataTableComponent displayData={data} displayColumns={columns} operations={operations}
                               tableName={"MeetingRooms"} addEntityUrl={"./add-meeting-room"}/>
}

export default withTranslation()(MeetingRoomsTable);

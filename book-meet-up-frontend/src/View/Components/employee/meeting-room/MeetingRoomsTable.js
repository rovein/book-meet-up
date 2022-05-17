import React, {useEffect, useState} from "react";
import {useTranslation, withTranslation} from "react-i18next";
import axios from "../../util/ApiUtil";
import DefaultLoader from "../../ui/Loader";
import DataTableComponent from "../../ui/DataTable";
import {
    getCurrentUserRole,
    setCurrentMeetingRoom,
    setEditMeetingRoomId
} from "../../util/LocalStorageUtils";
import {ADMIN} from "../../util/Constants";
import {formatMeetingRoomsData} from "../../util/DataFormattingUtil";
import {showMeetingRoomInfo} from "../../util/AlertUtil";
import {sortById} from "../../util/TableUtil";

function MeetingRoomsTable({retrieveUrl, additionalOperations}) {
    const [data, setData] = useState([])
    const [isLoaded, setIsLoaded] = useState(false)

    useEffect(() => {
        axios.get(retrieveUrl)
            .then(result => {
                const data = result.data;
                setData(data.map(formatMeetingRoomsData))
                setIsLoaded(true)
            })
    }, [])

    const columns = React.useMemo(() => [{
        Header: "FMeetingRoomFloor",
        accessor: 'floor'
    }], [])

    const {t} = useTranslation();

    function goToBookingsPage(id) {
        setCurrentMeetingRoom(data.find(meetingRoom => meetingRoom.id === id));
        window.location.href = `./bookings/by-meeting-room/${id}`;
    }

    function editEntity(id) {
        setEditMeetingRoomId(id);
        window.location.href = "./edit-meeting-room";
    }

    const operations = [{
        "name": "Info",
        "onClick": showMeetingRoomInfo(t),
        "className": "btn btn-info",
        "onClickPassParameter": "info"
    }]

    if (additionalOperations) {
        additionalOperations.forEach(operation => operations.push(operation))
    }

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
                               tableName={"MeetingRooms"} addEntityUrl={"./add-meeting-room"} sorter={sortById}/>
}

export default withTranslation()(MeetingRoomsTable);

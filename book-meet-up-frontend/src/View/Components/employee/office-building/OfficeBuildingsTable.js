import React, {useEffect, useState} from "react";
import {withTranslation} from "react-i18next";
import axios from "../../util/ApiUtil";
import DefaultLoader from "../../ui/Loader";
import DataTableComponent from "../../ui/DataTable";
import {FIELDS} from "./AddEditOfficeBuildingFormConfig";
import getEntityColumns from "../../util/TableUtil";
import {setCurrentOfficeBuilding, setCurrentOfficeBuildingId, setEditOfficeBuildingId} from "../../util/LocalStorageUtils";

function OfficeBuildingsTable() {
    const [data, setData] = useState([])
    const [isLoaded, setIsLoaded] = useState(false)

    useEffect(() => {
        axios.get(`/office-buildings`)
            .then(result => {
                const data = result.data
                setData(data)
                setIsLoaded(true)
            })
    }, [])

    const columns = React.useMemo(() => getEntityColumns(FIELDS, false), [])

    function editEntity(id) {
        setEditOfficeBuildingId(id);
        window.location.href = "./edit-office-building";
    }

    function goToMeetingRoomsPage(id) {
        setCurrentOfficeBuildingId(id);
        setCurrentOfficeBuilding(data.find(officeBuildingId => officeBuildingId.id === id));
        window.location.href = "./office-building-info";
    }

    const operations = [
        {
            "name": "ToMeetingRooms",
            "onClick": goToMeetingRoomsPage,
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
            "url": "/office-buildings/{id}",
        }
    ]

    if (!isLoaded) return <DefaultLoader height={325} width={325}/>;
    return <DataTableComponent displayData={data} displayColumns={columns} operations={operations} />
}

export default withTranslation()(OfficeBuildingsTable);

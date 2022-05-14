import React, {useEffect, useState} from "react";
import {withTranslation} from "react-i18next";
import axios from "../../util/ApiUtil";
import DefaultLoader from "../../ui/Loader";
import DataTableComponent from "../../ui/DataTable";
import {FIELDS} from "./AddEditOfficeBuildingFormConfig";
import getEntityColumns from "../../util/TableUtil";
import {
    getCurrentUserRole,
    setCurrentOfficeBuilding,
    setCurrentOfficeBuildingId,
    setEditOfficeBuildingId
} from "../../util/LocalStorageUtils";
import {ADMIN} from "../../util/Constants";
import {formatOfficeBuildingData} from "../../util/DataFormattingUtil";

function OfficeBuildingsTable() {
    const [data, setData] = useState([])
    const [isLoaded, setIsLoaded] = useState(false)

    useEffect(() => {
        axios.get(`/office-buildings`)
            .then(result => {
                const data = result.data
                setData(data.map(formatOfficeBuildingData))
                setIsLoaded(true)
            })
    }, [])

    const columns = React.useMemo(() => {
        const columns = getEntityColumns(FIELDS, false)
        const nameIndex = columns.findIndex(column => column.accessor === 'name');
        columns[nameIndex].isHidden = true;
        return columns;
    }, [])

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
        }
    ]

    if (getCurrentUserRole() === ADMIN) {
        operations.push({
                "name": "Edit",
                "onClick": editEntity,
                "className": "btn btn-danger-warning",
                "onClickPassParameter": "id"
            },
            {
                "name": "Delete",
                "className": "btn btn-danger",
                "onClickPassParameter": "id",
                "url": "office-buildings/{id}",
            });
    }

    if (!isLoaded) return <DefaultLoader height={425} width={425}/>;
    return <DataTableComponent displayData={data} displayColumns={columns} operations={operations}
                               tableName={"OfficeBuildings"} addEntityUrl={"./add-office-building"}/>
}

export default withTranslation()(OfficeBuildingsTable);

import React, {useEffect, useState} from "react";
import axios from "../../util/ApiUtil";
import DefaultLoader from "../../ui/Loader";
import DataTableComponent from "../../ui/DataTable";
import {withTranslation} from "react-i18next";
import {getCurrentUserRole} from "../../util/LocalStorageUtils";
import {ADMIN} from "../../util/Constants";
import {formatBookingData} from "../../util/DataFormattingUtil";
import {sortByDate} from "../../util/TableUtil";

function BookingsTable({retrieveBookingsUrl, columns}) {
    const [data, setData] = useState([])
    const [isLoaded, setIsLoaded] = useState(false)

    useEffect(() => {
        axios.get(retrieveBookingsUrl)
            .then(result => {
                setData(result.data.map(formatBookingData))
                setIsLoaded(true)
            })
    }, [])

    const operations = [
        {
            "name": "Invite",
            "onClick": id => alert("Send invitation for booking " + id),
            "className": "btn btn-info",
            "onClickPassParameter": "id"
        }
    ]

    if (getCurrentUserRole() === ADMIN) {
        operations.push({
            "name": "Delete",
            "className": "btn btn-danger",
            "onClickPassParameter": "id",
            "url": "bookings/{id}",
        })
    }

    if (!isLoaded) return <DefaultLoader height={425} width={425}/>;
    return <DataTableComponent displayData={data} displayColumns={columns} tableName={"Bookings"}
                               operations={operations} addEntityUrl={'/create-storage'} sorter={sortByDate}/>
}

export default withTranslation()(BookingsTable);

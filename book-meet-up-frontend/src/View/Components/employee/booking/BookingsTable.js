import React, {useEffect, useState} from "react";
import axios from "../../util/ApiUtil";
import DefaultLoader from "../../ui/Loader";
import DataTableComponent from "../../ui/DataTable";
import {useTranslation, withTranslation} from "react-i18next";
import {getCurrentUserRole} from "../../util/LocalStorageUtils";
import {ADMIN, EMPLOYEE} from "../../util/Constants";
import {formatBookingData} from "../../util/DataFormattingUtil";
import {sortByDate} from "../../util/TableUtil";
import {confirmSendingInvitation} from "../../util/AlertUtil";

function BookingsTable({retrieveBookingsUrl, columns}) {
    const [data, setData] = useState([])
    const [isLoaded, setIsLoaded] = useState(false)
    const {t} = useTranslation()

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
            "onClick": confirmSendingInvitation(t),
            "className": "btn btn-info",
            "onClickPassParameter": "id"
        }
    ]

    const isCancelButtonDisabled = booking => booking.status === t("Canceled") || booking.status === t("Finished")

    if (getCurrentUserRole() === EMPLOYEE) {
        operations.push({
            "name": "CancelMeeting",
            "className": "btn btn-danger-warning",
            "onClickPassParameter": "id",
            "url": "bookings/cancel/{id}",
            "disabledCondition": isCancelButtonDisabled
        })
    }

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

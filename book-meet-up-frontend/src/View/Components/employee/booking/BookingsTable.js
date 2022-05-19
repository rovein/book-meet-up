import React, {useEffect, useState} from "react";
import axios from "../../util/ApiUtil";
import DefaultLoader from "../../ui/Loader";
import DataTableComponent from "../../ui/DataTable";
import {useTranslation, withTranslation} from "react-i18next";
import {getCurrentUserRole} from "../../util/LocalStorageUtils";
import {ADMIN} from "../../util/Constants";
import {formatBookingData} from "../../util/DataFormattingUtil";
import {sortByDate} from "../../util/TableUtil";
import {confirmAlert} from "react-confirm-alert";

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

    const confirmSendingInvitation = id => {
        const alertResult = (title, message) => {
            confirmAlert({
                title: t(title),
                message: t(message),
                buttons: [
                    {
                        label: t("Ok")
                    }
                ],
                closeOnEscape: true,
                closeOnClickOutside: true,
            });
        }
        confirmAlert({
            customUI: ({onClose}) => {
                return (
                    <div id="react-confirm-alert">
                        <div className="react-confirm-alert-overlay undefined">
                            <div className="react-confirm-alert">
                                <div className="react-confirm-alert-body" style={{width: "600px"}}>
                                    <h1>{t("SendInvitationHeader")}</h1>
                                    {t("SendInvitationText")}
                                    <br/>
                                    <textarea id='invitation-input' rows={3} style={{width: "100%"}}/>
                                    <div className="react-confirm-alert-button-group">
                                        <button onClick={() => {
                                            const input = document.getElementById('invitation-input').value;
                                            const emails = input.split(',').map(email => email.trim());
                                            onClose();
                                            axios.post(`bookings/${id}/send-invitation`, emails)
                                                .then(
                                                    _ => alertResult("Success", "InvitationWasSent"),
                                                    _ => alertResult("EError", "ErrorResponse")
                                                );
                                        }}>{t("ToInvite")}
                                        </button>
                                        <button onClick={onClose}>{t("GetBack")}</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                );
            }
        });
    }

    const operations = [
        {
            "name": "Invite",
            "onClick": confirmSendingInvitation,
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

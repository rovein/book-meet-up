import {confirmAlert} from "react-confirm-alert";
import axios from "./ApiUtil";
import React from "react";

export const showMeetingRoomInfo = t => {
    return info => {
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
}

export const confirmSendingInvitation = (t) => {
    return id => {
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
}

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

const alertResult = (title, message, t, confirmCallback) => {
    confirmAlert({
        title: t(title),
        message: t(message),
        buttons: [
            {
                label: t("Ok"),
                onClick: () => {
                    if (confirmCallback) confirmCallback();
                }
            }
        ],
        closeOnEscape: true,
        closeOnClickOutside: true,
    });
}

export const confirmSendingInvitation = (t) => {
    return id => {
        showConfirmDialogWithInput(t, id, "SendInvitationHeader", "SendInvitationText", "ToInvite",
            'bookings/{id}/send-invitation', "InvitationWasSent")
    }
}

export const confirmSendingCancelNotification = (t) => {
    return (id, confirmCallback) => {
        showConfirmDialogWithInput(t, id, "MeetingWillBeCanceled", "SendCancelNotificationText", "CancelMeeting",
            'bookings/{id}/send-cancel-notification', "CancelNotificationWasSent", confirmCallback)
    }
}

const showConfirmDialogWithInput = (t, id, header, text, actionButtonText, actionUrl, successMessage, confirmCallback) => {
    confirmAlert({
        customUI: ({onClose}) => {
            return (
                <div id="react-confirm-alert">
                    <div className="react-confirm-alert-overlay undefined">
                        <div className="react-confirm-alert">
                            <div className="react-confirm-alert-body" style={{width: "600px"}}>
                                <h1>{t(header)}</h1>
                                {t(text)}
                                <br/>
                                <textarea id='invitation-input' rows={3} style={{width: "100%"}}/>
                                <div className="react-confirm-alert-button-group">
                                    <button onClick={() => {
                                        const input = document.getElementById('invitation-input').value;
                                        const emails = input.split(',').map(email => email.trim());
                                        onClose();
                                        axios.post(actionUrl.replace("{id}", id), emails)
                                            .then(
                                                _ => alertResult("Success", successMessage, t, confirmCallback),
                                                _ => alertResult("EError", "ErrorResponse", t, confirmCallback)
                                            );
                                    }}>{t(actionButtonText)}
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

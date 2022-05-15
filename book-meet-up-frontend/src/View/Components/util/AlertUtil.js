import {confirmAlert} from "react-confirm-alert";

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

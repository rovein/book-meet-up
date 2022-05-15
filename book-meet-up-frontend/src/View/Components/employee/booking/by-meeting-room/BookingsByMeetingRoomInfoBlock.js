import React from "react";
import {useTranslation} from "react-i18next";
import {showMeetingRoomInfo} from "../../../util/AlertUtil";

function BookingsByMeetingRoomInfoBlock({officeBuilding, meetingRoom}) {
    const {t} = useTranslation();
    return <div className="profile_back">
        <p className={'entityName'}>{t("OfficeBuilding") + ' ' + officeBuilding.name}</p>
        <p className={'entityName'}>{t("MeetingRoom") + ' № ' + meetingRoom.number}</p>

        <p>{t("FCity")}: {officeBuilding.city}</p>
        <p>{t("FMeetingRoomFloor")}: {meetingRoom.floor}</p>

        <p>{t("FStreet")}: {officeBuilding.street}</p>
        <p style={{textDecoration: "underline"}}
           onClick={() => showMeetingRoomInfo(t)(meetingRoom.info)}>{t("Info")}</p>

        <p>{t("FHouse")}: {officeBuilding.house}</p>
    </div>
}

export default BookingsByMeetingRoomInfoBlock;

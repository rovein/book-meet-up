import {useTranslation, withTranslation} from "react-i18next";
import React, {useEffect, useState} from "react";
import MeetingRoomsTable from "../meeting-room/MeetingRoomsTable";
import {getCurrentOfficeBuilding, getCurrentEmployee} from "../../util/LocalStorageUtils";

function OfficeBuildingInfo() {
    const [user, setUser] = useState({})
    const [officeBuilding, setOfficeBuilding] = useState({})

    useEffect(() => {
        setUser(getCurrentEmployee());
        setOfficeBuilding(getCurrentOfficeBuilding())
    }, [])

    const {t} = useTranslation();
    return (
        <div>
            <div className="w3-light-grey w3-text-black w3-border w3-border-black profile_back">
                <p className={'entityName'}>{user.name}</p>
                <p className={'entityName'}>{t("OfficeBuilding") + ' № ' + officeBuilding.id}</p>
                <p>{t("Email")}: {user.email}</p>
                <p>{t("FCity")}: {officeBuilding.city}</p>
                <p>{t("Phone")}: {user.phoneNumber}</p>
                <p>{t("FStreet")}: {officeBuilding.street}</p>
                <p></p>
                <p>{t("FHouse")}: {officeBuilding.house}</p>
            </div>
            <div>
                <MeetingRoomsTable/>
            </div>
        </div>
    )
}

export default withTranslation()(OfficeBuildingInfo)

import {useTranslation, withTranslation} from "react-i18next";
import React, {useEffect, useState} from "react";
import MeetingRoomsTable from "../meeting-room/MeetingRoomsTable";
import {
    getCurrentOfficeBuilding,
    getCurrentEmployee,
    getCurrentUserRole,
    getCurrentAdmin
} from "../../util/LocalStorageUtils";
import {ADMIN, EMPLOYEE} from "../../util/Constants";

function OfficeBuildingInfo() {
    const [user, setUser] = useState({})
    const [officeBuilding, setOfficeBuilding] = useState({})
    const currentUserRole = getCurrentUserRole();

    useEffect(() => {
        setUser(isAdmin() ? getCurrentAdmin : getCurrentEmployee());
        setOfficeBuilding(getCurrentOfficeBuilding());
    }, [])

    const isAdmin = () => currentUserRole === ADMIN;
    const isEmployee = () => currentUserRole === EMPLOYEE;

    const {t} = useTranslation();
    return (
        <div>
            <div className="profile_back">
                {isEmployee() && <p className={'entityName'}>{user.firstName + ' ' + user.lastName}</p>}
                {isAdmin() && <p className={'entityName'}>{t("Admin")}</p>}
                <p className={'entityName'}>{t("OfficeBuilding") + ' ' + officeBuilding.name}</p>

                {isEmployee() && <p>{t("Email")}: {user.email}</p>}
                {isAdmin() && <p>{user.firstName + ' ' + user.lastName}</p>}
                <p>{t("FCity")}: {officeBuilding.city}</p>

                {isEmployee() && <p>{t("Phone")}: {user.phoneNumber}</p>}
                {isAdmin() && <p>{t("Email")}: {user.email}</p>}
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

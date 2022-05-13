import React, {useEffect, useState} from 'react'
import {useTranslation, withTranslation} from 'react-i18next';

import axios from "../util/ApiUtil";
import {
    checkToken,
    getCurrentEmployee,
    getCurrentUserEmail,
    setCurrentEmployee
} from "../util/LocalStorageUtils";
import MeetingRoomsTable from "./meeting-room/MeetingRoomsTable";
import OfficeBuildingsTable from "./office-building/OfficeBuildingsTable";

function Profile() {
    checkToken();

    const [employee, setEmployee] = useState({})

    const checkCachedEmployee = () => {
        const cachedEmployee = getCurrentEmployee();
        if (cachedEmployee != null) {
            setEmployee(cachedEmployee);
            return true;
        }
        return false;
    }

    useEffect(() => {
        if (checkCachedEmployee()) return;
        axios.get(`/employees/${getCurrentUserEmail()}`).then(result => {
                setEmployee(result.data);
                setCurrentEmployee(result.data);
            }
        )
    }, [])

    const {t} = useTranslation();
    return (
        <div className="profile">
            <div className="profile_back">
                <p className={'entityName'}>{employee.name}</p>
                <p></p>
                <p>{t("Email")}: {employee.email}</p>
                <p></p>
                <p>{t("Phone")}: {employee.phoneNumber}</p>
                <p></p>
                <p></p>
            </div>
            <div>
                <OfficeBuildingsTable/>
            </div>
        </div>
    )
}

export default withTranslation()(Profile);

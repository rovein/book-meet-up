import React, {useEffect, useState} from 'react'
import {withTranslation} from 'react-i18next';
import {
    getAdminProfileShownTable,
    removeEditUserEmail,
    removeEditUserRole, setCurrentAdmin,
} from "../util/LocalStorageUtils";
import OfficeBuildingsTable from "../employee/office-building/OfficeBuildingsTable";
import {EMPLOYEES, OFFICE_BUILDINGS} from "../util/Constants";
import AdminEmployeesTable from "./AdminEmployeesTable";
import AdminProfilePanel from "./AdminProfilePanel";

function Profile() {

    const [shownTable, setShownTable] = useState(OFFICE_BUILDINGS);
    const [admin, setAdmin] = useState({})

    useEffect(() => {
        const shownTable = getAdminProfileShownTable();
        if (shownTable) setShownTable(getAdminProfileShownTable());

        const admin = {
            firstName: "Rick",
            lastName: "Sanchez",
            email: "admin@gmail.com"
        };
        setCurrentAdmin(admin);
        setAdmin(admin);
    }, [])

    removeEditUserEmail();
    removeEditUserRole();

    return (
        <div>
            <AdminProfilePanel setShownTable={setShownTable} currentAdmin={admin}/>
            <div id="rooms_container">
                {shownTable === OFFICE_BUILDINGS && <OfficeBuildingsTable/>}
                {shownTable === EMPLOYEES && <AdminEmployeesTable/>}
            </div>
        </div>
    );

}

export default withTranslation()(Profile);

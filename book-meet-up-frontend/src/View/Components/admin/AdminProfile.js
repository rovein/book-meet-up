import React, {useEffect, useState} from 'react'
import {useTranslation, withTranslation} from 'react-i18next';
import {
    getAdminProfileShownTable,
    removeEditUserEmail,
    removeEditUserRole, setAdminProfileShownTable,
} from "../util/LocalStorageUtils";
import Button from "../ui/Button";
import OfficeBuildingsTable from "../employee/office-building/OfficeBuildingsTable";
import {EMPLOYEES, OFFICE_BUILDINGS} from "../util/Constants";
import AdminEmployeesTable from "./AdminEmployeesTable";

function Profile() {

    const [shownTable, setShownTable] = useState(OFFICE_BUILDINGS);

    useEffect(() => {
        const shownTable = getAdminProfileShownTable();
        if (shownTable) setShownTable(getAdminProfileShownTable());
    }, [])

    removeEditUserEmail();
    removeEditUserRole();

    const {t} = useTranslation();
    return (
        <div>
            <div className="profile_back">
                <p></p>
                <Button
                    text={t("CreateAcc")}
                    disabled={false}
                    onClick={() => {
                        window.location.href = "/signup";
                    }}
                />
                <p id="cName">{t("Admin")}</p>
                <Button
                    text={t("OfficeBuildings")}
                    disabled={false}
                    onClick={() => {
                        setShownTable(OFFICE_BUILDINGS)
                        setAdminProfileShownTable(OFFICE_BUILDINGS)
                    }}
                />
                <p></p>
                <Button
                    text={t("Employees")}
                    disabled={false}
                    onClick={() => {
                        setShownTable(EMPLOYEES)
                        setAdminProfileShownTable(EMPLOYEES)
                    }}
                />
            </div>

            <div id="rooms_container">
                {shownTable === OFFICE_BUILDINGS && <OfficeBuildingsTable/>}
                {shownTable === EMPLOYEES && <AdminEmployeesTable/>}
            </div>
        </div>
    );

}

export default withTranslation()(Profile);

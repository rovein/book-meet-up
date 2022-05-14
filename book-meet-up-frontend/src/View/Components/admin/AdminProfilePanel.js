import {useTranslation, withTranslation} from "react-i18next";
import Button from "../ui/Button";
import {EMPLOYEES, OFFICE_BUILDINGS} from "../util/Constants";
import {getCurrentAdmin, setAdminProfileShownTable} from "../util/LocalStorageUtils";
import React from "react";

function AdminProfilePanel({setShownTable, currentAdmin}) {
    const {t} = useTranslation();
    return (
            <div className="profile_back">
                <p id="cName">{t("Admin")} {currentAdmin.firstName} {currentAdmin.lastName}</p>
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
    );
}

export default withTranslation()(AdminProfilePanel);

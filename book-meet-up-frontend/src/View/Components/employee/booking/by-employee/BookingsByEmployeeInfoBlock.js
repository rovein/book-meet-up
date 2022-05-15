import React from "react";
import {useTranslation} from "react-i18next";

function BookingsByEmployeeInfoBlock({employee}) {
    const {t} = useTranslation();
    return <div className="profile_back">
        <p className={'entityName'}>{employee.firstName + ' ' + employee.lastName}</p>
        <p></p>

        <p>{t("Email")}: {employee.email}</p>
        <p></p>

        <p>{t("Phone")}: {employee.phoneNumber}</p>
        <p></p>
    </div>
}

export default BookingsByEmployeeInfoBlock;

import Header from "../../auth/HeaderAuth";
import React from "react";
import WarehouseInfo from "../../employee/office-building/OfficeBuildingInfo";

function OfficeBuildingInfoPage() {
    return (
        <div className="profile">
            <Header/>
            <WarehouseInfo/>
        </div>
    )
}

export default OfficeBuildingInfoPage;

import Header from "../../auth/HeaderAuth";
import React from "react";
import OfficeBuildingInfo from "../../employee/office-building/OfficeBuildingInfo";

function OfficeBuildingInfoPage() {
    return (
        <div className="profile">
            <Header/>
            <OfficeBuildingInfo/>
        </div>
    )
}

export default OfficeBuildingInfoPage;

import React from 'react'
import Header from '../../auth/HeaderAuth'
import EditOfficeBuildingForm from "../../employee/office-building/EditOfficeBuildingForm";

function EditOfficeBuilding() {
    return (
        <div className="signIn">
            <Header/>
            <EditOfficeBuildingForm/>
        </div>
    )
}

export default EditOfficeBuilding;

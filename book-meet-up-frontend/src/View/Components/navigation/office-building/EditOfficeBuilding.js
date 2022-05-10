import React from 'react'
import Header from '../../auth/HeaderAuth'
import EditOfficeBuildingForm from "../../employee/office-building/EditOfficeBuildingForm";

function EditOfficeBuilding() {
    return (
        <div className="signIn">
            <Header/>
            <div className="container">
                <EditOfficeBuildingForm/>
            </div>
        </div>
    )
}

export default EditOfficeBuilding;

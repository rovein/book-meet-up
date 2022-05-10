import React from 'react'
import Header from '../../auth/HeaderAuth'
import AddOfficeBuildingForm from "../../employee/office-building/AddOfficeBuildingForm";

function AddOfficeBuilding() {
    return (
        <div className="signIn">
            <Header/>
            <AddOfficeBuildingForm/>
        </div>
    )
}

export default AddOfficeBuilding;

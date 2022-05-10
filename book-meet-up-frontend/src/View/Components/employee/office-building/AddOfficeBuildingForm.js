import React from 'react'
import axios from "../../util/ApiUtil";
import AddEditEntityForm from "../../ui/AddEditEntityForm";
import {ADD_FORM_NAME, FIELDS} from "./AddEditOfficeBuildingFormConfig";

function AddOfficeBuildingForm() {
    const requestPayload = {
        function: axios.post,
        url: `/office-buildings`,
        body: {},
        entityId: 'editOfficeBuildingId',
        redirectUrl: './profile'
    }

    return <div className="container">
        <AddEditEntityForm requestPayload={requestPayload} fields={FIELDS} formName={ADD_FORM_NAME}/>
    </div>
}

export default AddOfficeBuildingForm

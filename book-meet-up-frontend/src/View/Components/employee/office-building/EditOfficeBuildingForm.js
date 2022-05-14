import React, {useEffect, useState} from 'react'
import axios from "../../util/ApiUtil";
import AddEditEntityForm from "../../ui/AddEditEntityForm";
import {EDIT_FORM_NAME, FIELDS} from "./AddEditOfficeBuildingFormConfig";
import DefaultLoader from "../../ui/Loader";
import {getEditOfficeBuildingId} from "../../util/LocalStorageUtils";

function EditOfficeBuildingForm() {
    const [isLoaded, setIsLoaded] = useState(false)
    const [body, setBody] = useState({})

    useEffect(() => {
        axios.get(`/office-buildings/${getEditOfficeBuildingId()}`)
            .then(result => {
                    setBody(result.data)
                    setIsLoaded(true);
                }
            )
    }, [])

    if (!isLoaded) return <DefaultLoader height={400} width={425} isCentered={true}/>;
    return <div className="container">
        <AddEditEntityForm requestPayload={{
            function: axios.put,
            url: `/office-buildings`,
            entityId: 'editOfficeBuildingId',
            redirectUrl: './profile',
            body
        }} fields={FIELDS} formName={EDIT_FORM_NAME}/>
    </div>
}

export default EditOfficeBuildingForm

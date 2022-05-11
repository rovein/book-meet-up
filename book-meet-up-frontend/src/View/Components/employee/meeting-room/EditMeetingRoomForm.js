import React, {useEffect, useState} from 'react'
import {withTranslation} from 'react-i18next'
import axios from "../../util/ApiUtil";
import DefaultLoader from "../../ui/Loader";
import AddEditEntityForm from "../../ui/AddEditEntityForm";
import {EDIT_FORM_NAME, FIELDS} from "./AddEditMeetingRoomFormConfig";
import {getCurrentOfficeBuildingId, getEditMeetingRoomId} from "../../util/LocalStorageUtils";

function EditMeetingRoomForm() {
    const [isLoaded, setIsLoaded] = useState(false)
    const [body, setBody] = useState({})

    useEffect(() => {
        axios.get(`/office-buildings/meeting-rooms/${getEditMeetingRoomId()}`)
            .then(result => {
                    const data = result.data;
                    setBody(data);
                    setIsLoaded(true);
                }
            )
    }, [])

    if (!isLoaded) return <DefaultLoader height={400} width={425} isCentered={false}/>;
    return <div className="container">
        <AddEditEntityForm requestPayload={{
            function: axios.put,
            url: `/office-buildings/${getCurrentOfficeBuildingId()}/meeting-rooms`,
            entityId: 'placementId',
            redirectUrl: './office-building-info',
            body
        }} fields={FIELDS} formName={EDIT_FORM_NAME}/>
    </div>
}

export default withTranslation()(EditMeetingRoomForm);

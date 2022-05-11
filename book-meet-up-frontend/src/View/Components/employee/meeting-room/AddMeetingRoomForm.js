import React from 'react'
import { withTranslation } from 'react-i18next'
import axios from "../../util/ApiUtil";
import AddEditEntityForm from "../../ui/AddEditEntityForm";
import {ADD_FORM_NAME, FIELDS} from "./AddEditMeetingRoomFormConfig";
import {EDIT_MEETING_ROOM_ID, getCurrentOfficeBuildingId} from "../../util/LocalStorageUtils";

function AddMeetingRoomForm() {
    const requestPayload = {
        function: axios.post,
        url: `/office-buildings/${getCurrentOfficeBuildingId()}/meeting-rooms`,
        body: {},
        entityId: EDIT_MEETING_ROOM_ID,
        redirectUrl: './office-building-info'
    }

    return <div className="container">
        <AddEditEntityForm requestPayload={requestPayload} fields={FIELDS} formName={ADD_FORM_NAME}/>
    </div>
}

export default withTranslation() (AddMeetingRoomForm);

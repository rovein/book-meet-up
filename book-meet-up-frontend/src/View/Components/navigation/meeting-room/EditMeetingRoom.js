import React from 'react'
import Header from '../../auth/HeaderAuth'
import EditPlacementForm from '../../employee/meeting-room/EditMeetingRoomForm'

function EditMeetingRoom() {
    return (
        <div className="signIn">
            <Header/>
            <div className="container">
                <EditPlacementForm/>
            </div>
        </div>
    )
}

export default EditMeetingRoom;

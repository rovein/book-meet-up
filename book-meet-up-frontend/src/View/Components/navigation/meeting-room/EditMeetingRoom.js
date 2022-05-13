import React from 'react'
import Header from '../../auth/HeaderAuth'
import EditMeetingRoomForm from '../../employee/meeting-room/EditMeetingRoomForm'

function EditMeetingRoom() {
    return (
        <div className="signIn">
            <Header/>
            <div className="container">
                <EditMeetingRoomForm/>
            </div>
        </div>
    )
}

export default EditMeetingRoom;

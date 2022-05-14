import React from 'react'
import Header from '../../auth/HeaderAuth'
import AddMeetingRoomForm from '../../employee/meeting-room/AddMeetingRoomForm'

function AddMeetingRoom() {
    return (
        <div className="signIn">
            <Header/>
            <AddMeetingRoomForm/>
        </div>
    )
}

export default AddMeetingRoom;

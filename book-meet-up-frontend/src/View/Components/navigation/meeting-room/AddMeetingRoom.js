import React from 'react'
import Header from '../../auth/HeaderAuth'
import AddMeetingRoomForm from '../../employee/meeting-room/AddMeetingRoomForm'

function AddMeetingRoom() {
    return (
        <div className="signIn">
            <Header/>
            <div className="container">
                <AddMeetingRoomForm/>
            </div>
        </div>
    )
}

export default AddMeetingRoom;

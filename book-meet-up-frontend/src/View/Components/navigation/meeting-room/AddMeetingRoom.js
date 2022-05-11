import React from 'react'
import Header from '../../auth/HeaderAuth'
import AddPlacementForm from '../../employee/meeting-room/AddMeetingRoomForm'

function AddMeetingRoom() {
    return (
        <div className="signIn">
            <Header/>
            <div className="container">
                <AddPlacementForm/>
            </div>
        </div>
    )
}

export default AddMeetingRoom;

import React from 'react'
import Header from '../../auth/HeaderAuth'
import EditForm from '../../auth/EditUserProfileForm'

function Edit() {
    return (
        <div className="signIn">
            <Header/>
            <div className="container">
                <EditForm/>
            </div>
        </div>
    )
}

export default Edit;

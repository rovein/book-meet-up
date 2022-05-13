import React from 'react'
import Header from '../../auth/HeaderAuth'
import EmployeeProfile from '../../employee/EmployeeProfile'
import AdminProfile from '../../admin/AdminProfile'
import {ADMIN, EMPLOYEE} from "../../util/Constants";
import {checkToken, getCurrentUserRole} from "../../util/LocalStorageUtils";

function Profile() {
    checkToken();
    return (
        <div className="profile">
            <Header/>
            {getCurrentUserRole() === EMPLOYEE ? <EmployeeProfile/>
                : getCurrentUserRole() === ADMIN ? <AdminProfile/> : <></>}
        </div>
    )

}

export default Profile;

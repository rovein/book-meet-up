import React from 'react'
import Header from '../../ui/Header'
import {getCurrentLanguage, getCurrentUserRole, setCurrentLanguage} from "../../util/LocalStorageUtils";
import SignUpForm from "../../auth/SignUpForm";
import HeaderAuth from "../../auth/HeaderAuth";
import {ADMIN} from "../../util/Constants";

function SignUp() {
    const language = getCurrentLanguage();
    setCurrentLanguage(language);
    return (
        <div className="profile">
            {getCurrentUserRole() === ADMIN ? <HeaderAuth/> : <Header/>}
            <div className="container">
                <SignUpForm/>
            </div>
        </div>
    )
}

export default SignUp;

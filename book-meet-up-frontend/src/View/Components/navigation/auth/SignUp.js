import React from 'react'
import Header from '../../ui/Header'
import {getCurrentLanguage, setCurrentLanguage} from "../../util/LocalStorageUtils";
import SignUpForm from "../../auth/SignUpForm";

function SignUp() {
    const language = getCurrentLanguage();
    setCurrentLanguage(language);
    return (
        <div className="profile">
            <Header/>
            <div className="container">
                <SignUpForm/>
            </div>
        </div>
    )
}

export default SignUp;

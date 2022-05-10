import React from 'react'
import Header from '../../ui/Header'
import SignUpTabBar from '../../auth/SignUpTabBar';
import {clearLocalStorage, getCurrentLanguage, setCurrentLanguage} from "../../util/LocalStorageUtils";

function SignUp() {
    const language = getCurrentLanguage();
    clearLocalStorage();
    setCurrentLanguage(language);
    return (
        <div className="signIn">
            <Header/>
            <div className="container">
                <SignUpTabBar/>
            </div>
        </div>
    )
}

export default SignUp;

import React, {useState} from 'react'
import {useTranslation, withTranslation} from 'react-i18next';
import {ADMIN, EMPLOYEE} from "../util/Constants";
import {
    checkToken, clearLocalStorage, getCurrentEmployeeId, getCurrentLanguage, getCurrentUserRole, setCurrentLanguage
} from "../util/LocalStorageUtils";
import {determineInitialLanguage, onLanguageHandle} from "../util/LocalizationUtil";

function Header(props) {

    checkToken()

    const [language, setLanguage] = useState(determineInitialLanguage());

    const signOut = _ => {
        const language = getCurrentLanguage();
        clearLocalStorage();
        setCurrentLanguage(language)
        window.location.href = '/';
    }

    const {t} = useTranslation();
    return (<div className="header">
            <nav>
                <ul className="nav_links">
                    <li><input type="button" id="locale" value={language}
                               onClick={() => onLanguageHandle(language, setLanguage, props.i18n)}/></li>
                    <li><a href="/profile">{t("Profile")}</a></li>
                    {getCurrentUserRole() === EMPLOYEE && <>
                        <li><a href="/edit">{t('EditP')}</a></li>
                        <li><a href={`/bookings/by-employee/${getCurrentEmployeeId()}`}>{t('MyBookings')}</a></li>
                        <li><a href="/create-booking">{t('CreateBooking')}</a></li>
                    </>}
                    {getCurrentUserRole() === ADMIN && <>
                    </>}
                    <li><a onClick={signOut} id="SO">{t("Signout")}</a></li>
                </ul>
            </nav>
        </div>)
}

export default withTranslation()(Header);

import React, {useState} from 'react'
import {useTranslation, withTranslation} from 'react-i18next'
import {determineInitialLanguage, onLanguageHandle} from "../util/LocalizationUtil";

function Header(props) {

    const [language, setLanguage] = useState(determineInitialLanguage());

    const {t} = useTranslation();
    return (
        <div className="header">
            <nav>
                <ul className="nav_links">
                    <li><input type="button" id="locale" value={language}
                               onClick={() => onLanguageHandle(language, setLanguage, props.i18n)}/></li>
                    <li><a href="/login">{t("Login")}</a></li>
                    <li><a href="/signup">{t("Signup")}</a></li>
                </ul>
            </nav>
        </div>
    )
}

export default withTranslation()(Header);

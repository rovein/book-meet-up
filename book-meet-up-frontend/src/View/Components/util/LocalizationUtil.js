import {getCurrentLanguage, setCurrentLanguage} from "./LocalStorageUtils";

export const determineInitialLanguage = () => {
    if (getCurrentLanguage() !== "UA") {
        setCurrentLanguage("EN");
        return ("EN");
    } else {
        return (getCurrentLanguage());
    }
}

export const onLanguageHandle = (currentLanguage, stateChanger, i18n) => {
    let newLang = currentLanguage === 'EN' ? 'UA' : 'EN';
    stateChanger(newLang);
    i18n.changeLanguage(newLang);
}

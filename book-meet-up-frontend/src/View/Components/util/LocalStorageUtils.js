import jwt_decode from "jwt-decode";

export const TOKEN = "token";
export const CURRENT_USER_EMAIL = 'currentUserEmail';
export const CURRENT_USER_ROLE = "currentUserRole";
export const CURRENT_LANGUAGE = "i18nextLng";

export const EDIT_USER_EMAIL = "editUserEmail";
export const EDIT_USER_ROLE = "editUserRole";

export const CURRENT_EMPLOYEE = "currentEmployee";
export const CURRENT_ADMIN = "currentAdmin";

export const CURRENT_EMPLOYEE_ID = "currentEmployeeId";
export const EDIT_OFFICE_BUILDING_ID = "editOfficeBuildingId";
export const EDIT_MEETING_ROOM_ID = "editMeetingRoomId";

export const CURRENT_OFFICE_BUILDING_ID = "currentOfficeBuildingId";
export const CURRENT_OFFICE_BUILDING = "currentOfficeBuilding"
export const CURRENT_MEETING_ROOM = "currentMeetingRoom";


export const ADMIN_PROFILE_SHOWN_TABLE = "adminProfileShownTable";


export const clearLocalStorage = () => localStorage.clear()

export const getItem = key => localStorage.getItem(key)

export const getObject = key => JSON.parse(getItem(key))

export const setItem = (key, value) => localStorage.setItem(key, value)

export const setObject = (key, value) => setItem(key, JSON.stringify(value))

export const removeItem = key => localStorage.removeItem(key)


export const checkToken = () => {
    if (getItem(TOKEN) == null) window.location.href = './'
}
export const getToken = () => getItem(TOKEN)
export const setToken = value => setItem(TOKEN, value)
export const setTokenValues = jwtToken => {
    const decoded = jwt_decode(jwtToken)
    setCurrentUserEmail(decoded.email);
    setCurrentUserRole(decoded.role);
}

export const getCurrentUserEmail = () => getItem(CURRENT_USER_EMAIL)
export const setCurrentUserEmail = value => setItem(CURRENT_USER_EMAIL, value)

export const getCurrentUserRole = () => getItem(CURRENT_USER_ROLE)
export const setCurrentUserRole = value => setItem(CURRENT_USER_ROLE, value)

export const getCurrentLanguage = () => getItem(CURRENT_LANGUAGE)
export const setCurrentLanguage = value => setItem(CURRENT_LANGUAGE, value)

export const getEditUserEmail = () => getItem(EDIT_USER_EMAIL)
export const setEditUserEmail = value => setItem(EDIT_USER_EMAIL, value)
export const removeEditUserEmail = () => removeItem(EDIT_USER_EMAIL)

export const getEditUserRole = () => getItem(EDIT_USER_ROLE)
export const setEditUserRole = value => setItem(EDIT_USER_ROLE, value)
export const removeEditUserRole = () => removeItem(EDIT_USER_ROLE)

export const getCurrentEmployee = () => getObject(CURRENT_EMPLOYEE)
export const setCurrentEmployee = value => setObject(CURRENT_EMPLOYEE, value)

export const getCurrentEmployeeId = () => getItem(CURRENT_EMPLOYEE_ID)
export const setCurrentEmployeeId = value => setItem(CURRENT_EMPLOYEE_ID, value)

export const getCurrentAdmin = () => getObject(CURRENT_ADMIN)
export const setCurrentAdmin = value => setObject(CURRENT_ADMIN, value)

export const getEditOfficeBuildingId = () => getItem(EDIT_OFFICE_BUILDING_ID)
export const setEditOfficeBuildingId = value => setItem(EDIT_OFFICE_BUILDING_ID, value)

export const getCurrentOfficeBuildingId = () => getItem(CURRENT_OFFICE_BUILDING_ID)
export const setCurrentOfficeBuildingId = value => setItem(CURRENT_OFFICE_BUILDING_ID, value)

export const getCurrentOfficeBuilding = () => getObject(CURRENT_OFFICE_BUILDING)
export const setCurrentOfficeBuilding = value => setObject(CURRENT_OFFICE_BUILDING, value)

export const getEditMeetingRoomId = () => getItem(EDIT_MEETING_ROOM_ID)
export const setEditMeetingRoomId = value => setItem(EDIT_MEETING_ROOM_ID, value)

export const getCurrentMeetingRoom = () => getObject(CURRENT_MEETING_ROOM)
export const setCurrentMeetingRoom = value => setObject(CURRENT_MEETING_ROOM, value)

export const getAdminProfileShownTable = () => getItem(ADMIN_PROFILE_SHOWN_TABLE)
export const setAdminProfileShownTable = value => setItem(ADMIN_PROFILE_SHOWN_TABLE, value)

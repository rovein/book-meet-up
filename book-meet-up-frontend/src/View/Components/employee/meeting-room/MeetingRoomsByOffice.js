import {getCurrentOfficeBuildingId} from "../../util/LocalStorageUtils";
import MeetingRoomsTable from "./MeetingRoomsTable";
import {withTranslation} from "react-i18next";

function MeetingRoomsByOffice() {
    const retrieveUrl = `/office-buildings/${getCurrentOfficeBuildingId()}/meeting-rooms`;
    return <MeetingRoomsTable retrieveUrl={retrieveUrl}/>
}

export default withTranslation()(MeetingRoomsByOffice);

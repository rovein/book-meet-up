import {withTranslation} from "react-i18next";
import MeetingRoomsTable from "./MeetingRoomsTable";

function MeetingRoomsAvailableForBooking({retrieveUrl, additionalOperations}) {
    return <MeetingRoomsTable retrieveUrl={retrieveUrl} additionalOperations={additionalOperations}/>
}

export default withTranslation()(MeetingRoomsAvailableForBooking);

import React from "react";
import {withTranslation} from "react-i18next";
import BookingsTable from "../BookingsTable";
import {COLUMNS} from "../BookingsTableConfig";

function BookingsByMeetingRoomTable({id}) {
    const columns = React.useMemo(() => COLUMNS, [])
    return <BookingsTable retrieveBookingsUrl={`/bookings/meeting-room/${id}`} columns={columns}/>
}

export default withTranslation()(BookingsByMeetingRoomTable);

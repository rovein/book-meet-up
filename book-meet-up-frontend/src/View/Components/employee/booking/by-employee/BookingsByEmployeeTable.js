import React from "react";
import {withTranslation} from "react-i18next";
import BookingsTable from "../BookingsTable";
import {COLUMNS} from "../BookingsTableConfig";

function BookingsByEmployeeTable({id}) {
    const columns = React.useMemo(() => COLUMNS, [])
    return <BookingsTable retrieveBookingsUrl={`/bookings/employee/${id}`} columns={columns}/>
}

export default withTranslation()(BookingsByEmployeeTable);

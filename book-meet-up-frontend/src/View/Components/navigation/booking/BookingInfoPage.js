import Header from "../../auth/HeaderAuth";
import React from "react";
import BookingInfo from "../../employee/booking/BookingInfo";

function BookingInfoPage() {
    return (
        <div className="profile">
            <Header/>
            <BookingInfo/>
        </div>
    )
}

export default BookingInfoPage;

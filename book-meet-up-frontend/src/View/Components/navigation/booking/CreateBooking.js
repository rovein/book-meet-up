import Header from "../../auth/HeaderAuth";
import React from "react";
import CreateBookingForm from "../../employee/booking/CreateBookingForm";

function CreateBooking() {
    return (
        <div className="signIn">
            <Header/>
            <div className="container">
                <CreateBookingForm/>
            </div>
        </div>
    )
}

export default CreateBooking;

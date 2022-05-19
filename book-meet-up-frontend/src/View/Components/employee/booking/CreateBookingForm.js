import React, {useEffect, useRef, useState} from 'react'
import {useTranslation, withTranslation} from 'react-i18next'
import 'react-dropdown/style.css';
import "react-datepicker/dist/react-datepicker.css";
import axios from "../../util/ApiUtil";
import DefaultLoader from "../../ui/Loader";
import _, {parseInt} from "lodash";
import {useForm} from "react-hook-form";
import {sortById} from "../../util/DataFormattingUtil";
import DatePicker from "react-datepicker"
import "react-datepicker/dist/react-datepicker.css";
import {getCurrentEmployeeId} from "../../util/LocalStorageUtils";
import Moment from "moment";
import MeetingRoomsAvailableForBooking from "../meeting-room/MeetingRoomsAvailableForBooking";
import Select from 'react-select';
import {getBookingDurations} from "../../util/Constants";

function CreateBookingForm() {
    const [officeBuildingOptions, setOfficeBuildingOptions] = useState([])
    const [selectedOfficeBuildingId, setSelectedOfficeBuildingId] = useState(0)
    const [selectedDate, setSelectedDate] = useState(new Date().setHours(9, 0))
    const [selectedMeetingRoomId, setSelectedMeetingRoom] = useState(0)
    const [selectedMeetingRoomNumber, setSelectedMeetingRoomNumber] = useState(0)
    const [selectedDuration, setSelectedDuration] = useState(0)
    const [retrieveMeetingRoomsUrl, setRetrieveBookingsUrl] = useState('')

    const [showMeetingRooms, setShowMeetingRooms] = useState(false)
    const [isLoaded, setIsLoaded] = useState(false)
    const [error, setError] = useState("");
    const {handleSubmit, formState: {errors}} = useForm()
    const {t} = useTranslation()
    const durationOptions = getBookingDurations(t)

    const submitButtonRef = useRef(null);

    useEffect(() => {
        axios.get(`/office-buildings/`)
            .then(result => {
                const data = sortById(result.data)
                const options = data.map(officeBuilding => ({
                    value: officeBuilding.id,
                    label: officeBuilding.name
                }));
                setOfficeBuildingOptions(options)
            });
    }, [])

    useEffect(() => {
        if (_.isEmpty(officeBuildingOptions)) return;
        setIsLoaded(true);
    }, [officeBuildingOptions])

    const handleError = _ => {
        setError("ErrorResponse")
        setIsLoaded(true)
    }

    useEffect(() => {
        if (selectedOfficeBuildingId === 0 || selectedDuration === 0) return
        setShowMeetingRooms(false)
        const meetingRoomsUrl = `/office-buildings/${selectedOfficeBuildingId}/meeting-rooms/available-for-booking`
        const dateTime = Moment(selectedDate).format("yyyy-MM-DDTHH:mm")
        setRetrieveBookingsUrl(`${meetingRoomsUrl}?dateTime=${dateTime}&duration=${selectedDuration}`)
        setShowMeetingRooms(true)
    }, [selectedOfficeBuildingId, selectedDate, selectedDuration])

    useEffect(async () => {
        if (selectedMeetingRoomId === 0) return
        submitButtonRef.current.disabled = false
        const meetingRoom = await (await axios.get(`/office-buildings/meeting-rooms/${selectedMeetingRoomId}`)).data
        setSelectedMeetingRoomNumber(meetingRoom.number)
    }, [selectedMeetingRoomId])

    const onSubmit = data => {
        if (!_.isEmpty(errors)) return;
        setIsLoaded(false);
        data.officeBuildingId = selectedOfficeBuildingId;
        data.employeeId = getCurrentEmployeeId();
        data.meetingRoomId = selectedMeetingRoomId;
        data.date = Moment(selectedDate).format("yyyy-MM-DD")
        data.time = Moment(selectedDate).format("HH:mm")
        data.duration = selectedDuration;
        axios.post('/bookings', data)
            .then(result => {
                const createdStorage = result.data
                if (createdStorage) window.location.href = `./bookings/by-employee/${createdStorage.employeeId}`;
            })
            .catch(handleError)
    }

    const filterPassedTime = time => {
        const currentDate = new Date();
        const selectedDate = new Date(time);
        const lowerDateRange = new Date(time)
        const upperDateRange = new Date(time)

        lowerDateRange.setHours(9, 0)
        upperDateRange.setHours(19, 0)

        return currentDate.getTime() < selectedDate.getTime() &&
            (selectedDate.getTime() >= lowerDateRange.getTime() && selectedDate.getTime() <= upperDateRange);
    }

    const selectMeetingRoom = id => {
        setSelectedMeetingRoom(id)
    }

    const operations = [{
        "name": "Select",
        "onClick": selectMeetingRoom,
        "className": "btn btn-info",
        "onClickPassParameter": "id"
    }
    ]

    if (!isLoaded) return <DefaultLoader height={400} width={425} isCentered={false}/>;
    return (
        <>
            <div className="signUpForm entity-form-border">
                <form className="signUpContainer" onSubmit={handleSubmit(onSubmit)}>
                    <h1 className="w3-center">{t("CreateBooking")}</h1>

                    <div className="sized-font w3-center w3-text-red">
                        {error && <p>{t(error)}</p>}
                    </div>

                    {selectedMeetingRoomNumber !== 0 ?
                        <h2 className="w3-center">{t("SelectedMeetingRoom")}: {selectedMeetingRoomNumber}</h2>
                    :  <h2 className="w3-center">{t("PassBookingSteps")}</h2>}

                    <div>
                        <Select placeholder={t("ChooseOffice")} options={officeBuildingOptions} onChange={e => {
                            setSelectedOfficeBuildingId(parseInt(e.value))
                            delete errors.officeBuildingId
                        }} />
                        {errors["officeBuildingId"] && <small className="w3-text-red">{t("ErrorChooseOffice")}</small>}
                    </div>

                    <DatePicker
                        selected={selectedDate}
                        showTimeSelect
                        filterTime={filterPassedTime}
                        dateFormat="dd.MM.yyyy HH:mm"
                        onChange={setSelectedDate}
                    />

                    <div>
                        <div>
                            <Select placeholder={t("ChooseDuration")} options={durationOptions} onChange={e => {
                                setSelectedDuration(parseInt(e.value))
                                delete errors.duration
                            }}/>
                            {errors["duration"] &&
                                <small className="w3-text-red">{t("ErrorChooseDuration")}</small>}
                        </div>
                    </div>

                    <br/>
                    <input ref={submitButtonRef} disabled={true} className="btn" value={t('Create')} type="submit"/>
                </form>
            </div>

            {showMeetingRooms &&
                <MeetingRoomsAvailableForBooking key={retrieveMeetingRoomsUrl} retrieveUrl={retrieveMeetingRoomsUrl}
                                                 additionalOperations={operations}/>}
        </>
    );
}

export default withTranslation()(CreateBookingForm);

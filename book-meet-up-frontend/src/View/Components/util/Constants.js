export const INPUT_STYLE_CLASSES = "input";
export const SERVER_URL = "http://localhost:8181";
export const DELAY = 1000;
export const ADMIN = "ADMIN";
export const EMPLOYEE = "EMPLOYEE";
export const OFFICE_BUILDINGS = "OFFICE_BUILDINGS";
export const EMPLOYEES = "EMPLOYEES";

export const getBookingDurations = t => {
    const durations = [
        {
            value: 15,
            translateKey: "FifteenMinutes",
        },
        {
            value: 30,
            translateKey: "HalfAnHour"
        },
        {
            value: 60,
            translateKey: "OneHour"
        },
        {
            value: 90,
            translateKey: "OneHourAndHalf"
        },
        {
            value: 120,
            translateKey: "TwoHours"
        },
        {
            value: 240,
            translateKey: "FourHours"
        },
        {
            value: 360,
            translateKey: "SixHours"
        },
        {
            value: 480,
            translateKey: "EightHours"
        }
    ]

    return durations.map(duration => {
        if (t) duration.label = t(duration.translateKey);
        return duration
    })
}

export const CREATED = "CREATED";
export const IN_PROGRESS = "IN_PROGRESS";
export const CANCELED = "CANCELED";
export const FINISHED = "FINISHED";

export const BOOKING_STATUSES = [
    {
        value: CREATED,
        translateKey: "Created",
        style: "text-info"
    },
    {
        value: IN_PROGRESS,
        translateKey: "InProgress",
        style: "text-warning"
    },
    {
        value: CANCELED,
        translateKey: "Canceled",
        style: "text-danger"
    },
    {
        value: FINISHED,
        translateKey: "Finished",
        style: "text-success"
    }
]

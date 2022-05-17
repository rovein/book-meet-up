export const INPUT_STYLE_CLASSES = "input";
export const SERVER_URL = "http://localhost:8181";
export const DELAY = 1000;
export const ADMIN = "ADMIN";
export const EMPLOYEE = "EMPLOYEE";
export const OFFICE_BUILDINGS = "OFFICE_BUILDINGS";
export const EMPLOYEES = "EMPLOYEES";

export const getBookingDurations = t => [
    {
        value: 15,
        label: t("FifteenMinutes")
    },
    {
        value: 30,
        label: t("HalfAnHour")
    },
    {
        value: 60,
        label: t("OneHour")
    },
    {
        value: 90,
        label: t("OneHourAndHalf")
    },
    {
        value: 120,
        label: t("TwoHours")
    },
    {
        value: 240,
        label: t("FourHours")
    },
    {
        value: 360,
        label: t("SixHours")
    },
    {
        value: 480,
        label: t("EightHours")
    }
]

export const CREATED = "CREATED";
export const IN_PROGRESS = "IN_PROGRESS";
export const CANCELED = "CANCELED";
export const FINISHED = "FINISHED";

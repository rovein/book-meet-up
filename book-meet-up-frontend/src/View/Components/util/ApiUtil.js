import axios from 'axios';
import {SERVER_URL} from "./Constants";
import delay from "./DelayUtil";
import {getToken} from "./LocalStorageUtils";

const instance = axios.create({
    baseURL: SERVER_URL
})
instance.interceptors.request.use(async config => {
    await delay();
    console.log("REQUEST")
    console.log(config)
    const token = getToken();
    if (token != null) {
        config.headers.common.Authorization = `Bearer ${token}`;
        config.headers['Accept'] = "application/json";
        config.headers['Content-Type'] = "application/json";
    }
    return config;
}, error => Promise.reject(error));

instance.interceptors.response.use(config => {
    console.log("RESPONSE")
    console.log(config)
    return config;
}, error => Promise.reject(error))

export default instance;

export const authInstance = axios.create({
    baseURL: SERVER_URL
});

authInstance.interceptors.request.use(async (request) => {
    await delay();
    return request;
});

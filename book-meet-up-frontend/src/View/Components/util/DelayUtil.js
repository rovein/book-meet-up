import {DELAY} from "./Constants";

export default function delay() {
    return new Promise((resolve) => setTimeout(resolve, DELAY));
}

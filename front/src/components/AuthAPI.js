import axios from "axios";
import {HOST} from "../config.js";

export const registerToken = (token) => {
    localStorage.setItem("token", token);
}

export const isAuthorized = () => {
    return Boolean(localStorage.getItem("token"));
}

export const authAPI = axios.create(
    {
        baseURL: HOST,
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    }
);


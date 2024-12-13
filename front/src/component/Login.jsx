import React from "react";
import InputComponent from "./InputComponent.jsx";
import {HOST} from "../config.js";
import axios from "axios";

const Login = () => {

    const handleSubmit = () => {
        axios.post(`${HOST}/api/v1/auth/login`,
        )
            .then((response) => {
                console.log(response);
            })
            .catch((error) => {
                console.log(error);
            });
    }
    console.log("rerender")
    return (
        <form onSubmit={handleSubmit} className="login">
            <InputComponent
                placeholder="Enter your email"
                type="text"
                name="email"
            />
            <InputComponent
                placeholder="Enter your password"
                type="password"
                name="password"
            />
            <div className="card">
                <button onClick={handleSubmit}>Login</button>
            </div>
        </form>
    )
}
export default Login;
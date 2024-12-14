import "../styles/login.css";
import React, {useEffect} from "react";
import Input from "./Input.jsx";
import {HOST} from "../config.js";
import axios from "axios";

const Login = () => {
    const [email, setEmail] = React.useState("");
    const [password, setPassword] = React.useState("");

    //
    // const handleSubmit = () => {
    //     axios.post(`${HOST}/api/v1/auth/login`,
    //     )
    //         .then((response) => {
    //             console.log(response);
    //         })
    //         .catch((error) => {
    //             console.log(error);
    //         });
    // }
    useEffect(() => {
        console.log(`email: ${email}, password: ${password}`);
    }, [email, password])
    return (
        <div className={"Login"}>
            <h3>Login 🔑</h3>
            <form className={"LoginWrapper"}>
                <Input
                    value={email}
                    placeholder={"Enter your email"}
                    onChange={(e) => setEmail(e.target.value)}
                    type={"text"}
                />
                <Input
                    value={password}
                    placeholder={"Enter your password"}
                    onChange={(e) => setPassword(e.target.value)}
                    type={"password"}
                />
                <div className={"ButtonWrapper"}>
                    <button>Login</button>
                </div>
            </form>
        </div>
    )
}
export default Login;
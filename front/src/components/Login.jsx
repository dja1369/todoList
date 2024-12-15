import "../styles/login.css";
import React, {useEffect} from "react";
import Input from "./Input.jsx";
import {HOST} from "../config.js";
import axios from "axios";
import {registerToken} from "./AuthAPI.js";
import {useNavigate} from "react-router-dom";

const Login = () => {
    const [email, setEmail] = React.useState("");
    const [password, setPassword] = React.useState("");

    const navigator = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post(`${HOST}/api/v1/auth/login`,
            {email: email, password: password}
        )
            .then((response) => {
                registerToken(response.data.result);
                navigator(`/`);
            })
            .catch((error) => {
                console.log(error.response.data.message);
            });
    }
    useEffect(() => {
    }, [email, password])
    return (
        <div className={"Login"}>
            <h3>Login 🔑</h3>
            <form className={"LoginWrapper"} onSubmit={handleSubmit}>
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
                    <button type={"submit"}>Login</button>
                </div>
            </form>
        </div>
    )
}
export default Login;
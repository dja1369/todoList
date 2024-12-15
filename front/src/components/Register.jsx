import "../styles/Register.css";
import Input from "./Input.jsx";
import React, {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {HOST} from "../config.js";

const Register = () => {
    const [email, setEmail] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [nickName, setNickName] = React.useState("");

    const navigator = useNavigate();
    //
    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post(`${HOST}/api/v1/registers/register`,
            {email: email, password: password, nickName: nickName}
        )
            .then((response) => {
                navigator(`/login`);
            })
            .catch((error) => {
                console.log(error);
            });
    }
    useEffect(() => {
    },[email, password, nickName])
    return (
        <div className={"Register"}>
            <h3>Register 🙋</h3>
            <form className={"RegisterWrapper"} onSubmit={handleSubmit}>
                <Input
                    value={nickName}
                    placeholder={"Enter your nickname"}
                    onChange={(e) => setNickName(e.target.value)}
                    type={"text"}
                />
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
                    <button type={"submit"}>Register</button>
                </div>
            </form>
        </div>
    );
}
export default Register;
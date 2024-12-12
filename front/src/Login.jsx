import React, {useState} from "react";
import InputComponent from "./InputComponent.jsx";

const Login = (props) => {
    const [state, setState] = useState({
        username: "", password: ""
    });
    const handleChange = (e) => {
        const {name, value} = e.target;
        setState((prevState) => ({
            ...prevState,
            [name]: value
        }));
    }
    return (<>
        <form>
            <InputComponent
                placeholder="plz Enter your email"
                value={state.username}
                name="email"
                type="text"
                onChange={handleChange}
            />
            <InputComponent
                placeholder="plz Enter your password"
                value={state.password}
                type="password"
                name="password"
                onChange={handleChange}
            />
        </form>
    </>)
}
export default Login;
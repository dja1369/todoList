import "../styles/Register.css";
import Input from "./Input.jsx";
import React, {useEffect} from "react";

const Register = () => {
    const [email, setEmail] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [nickName, setNickName] = React.useState("");

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
        console.log(`email: ${email}, password: ${password}, nickName: ${nickName}`);
    },[email, password, nickName])
    return (
        <div className={"Register"}>
            <h3>Register 🙋</h3>
            <form className={"RegisterWrapper"}>
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
                    <button>Register</button>
                </div>
            </form>
        </div>
    );
}
export default Register;
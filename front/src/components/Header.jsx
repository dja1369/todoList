import "../styles/Header.css";
import {BrowserRouter, NavLink, Route, Routes} from "react-router-dom";
import Login from "./Login.jsx";
import Todo from "./Todo.jsx";
import Register from "./Register.jsx";

const Header = () => {
    return (
        <BrowserRouter>
            <header className={"Header"}>
                <h3>Unknown 님</h3>
                <h3 className={"Date"}>{`Today: ${new Date().toDateString()}`}</h3>
                <nav className={"Nav"}>
                    <NavLink to="/">할일 목록</NavLink>
                    <NavLink to="/login">로그인</NavLink>
                    <NavLink to="/register">회원 가입</NavLink>
                </nav>
            </header>
                <Routes>
                    <Route path="/" element={<Todo/>}/>
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/register" element={<Register/>}/>
                </Routes>
        </BrowserRouter>
    );
}

export default Header;
import "../styles/todo-editor.css";
import {useEffect, useState} from "react";
import Input from "./Input.jsx";
import {authAPI} from "./AuthAPI.js";


const TodoEditor = ({addTodo}) => {
    // const [title, setTitle] = useState("");
    const [content, setContent] = useState("");

    const onSubmit = () => {
        if (content === "") {
            return;
        }
        api();
        setContent("");
        // setTitle("");
    }

    const api = () => {
        authAPI.post(`/api/v1/todos/todo`, {
            title: content
        })
            .then((response) => {
                addTodo(response.data.result);
            })
            .catch((error) => {
                console.log(error);
            });
    }

    useEffect(() => {
    }, [content])

    return (
        <div className={"TodoEditor"}>
            <h4>Create New Todo ✏️</h4>
            <div className={"EditorWrapper"}>
                <Input value={content} onChange={(e) => {
                    setContent(e.target.value)
                }} placeholder={`Enter Your TODO`}/>
                {/*<Input value={content} required={false}*/}
                {/*       onChange={(e) => {*/}
                {/*           setContent(e.target.value)*/}
                {/*       }} placeholder={`Enter Your Todo`}/>*/}
                {/*<input value={content} onChange={onHandleChange}*/}
                {/*       placeholder={`Enter Your Todo`}/>*/}
                <button type={"submit"} onClick={onSubmit}>Add</button>
            </div>
        </div>
    )

}

export default TodoEditor;
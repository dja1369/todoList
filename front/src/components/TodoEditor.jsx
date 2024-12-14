import "../styles/todo-editor.css";
import {useEffect, useState} from "react";
import Input from "./Input.jsx";


const TodoEditor = ({addTodo}) => {
    const [content, setContent] = useState("");

    const onSubmit = () => {
        addTodo(content);
        setContent("");
    }
    useEffect(() => {
    }, [content])

    return (
        <div className={"TodoEditor"}>
            <h4>Create New Todo ✏️</h4>
            <div className={"EditorWrapper"}>
                <Input value={content} onChange={(e) => {setContent(e.target.value)} } placeholder={`Enter Your Todo`}/>
                {/*<input value={content} onChange={onHandleChange}*/}
                {/*       placeholder={`Enter Your Todo`}/>*/}
                <button type={"submit"} onClick={onSubmit}>Add</button>
            </div>
        </div>
    )

}

export default TodoEditor;
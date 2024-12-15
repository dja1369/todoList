import "../styles/todo-item.css";
import {useState} from "react";
import {authAPI} from "./AuthAPI.js";

const TodoItem = ({id, content, createdAt, getTodoApi}) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [newContent, setNewContent] = useState("");
    const [isChecked, setIsChecked] = useState(false);

    const handleModify = () => {
        setIsModalOpen(true);
    };

    const handleUpdateTodo = () => {
        authAPI.patch(`/api/v1/todos/todo/${id}`, {
            title: newContent,
            status: isChecked ? "DONE" : "TODO"
        })
            .then(() => {
                getTodoApi();
                setNewContent("");
            })
            .catch((error) => {
                console.log(error);
            });
        setIsModalOpen(false);
    };

    return (
        <div className={"TodoItem"}>
            <span className={"title-col"}>{content}</span>
            <span> {new Date(createdAt).toLocaleString('ko-KR', {
                year: '2-digit',
                month: '2-digit',
                day: '2-digit',
                hour: '2-digit',
                minute: '2-digit'
            })}</span>
            <div className={"btn-col"}>
                <button onClick={handleModify}>Modify</button>
            </div>
            {isModalOpen && (
                <div className={"TodoItem"}>
                    <div className={"modal"}>
                        <h4>Edit Todo</h4>
                        <input type="checkbox" onChange={(e) => {
                            setIsChecked(e.target.checked)
                        }}/>
                        <input className={"search-bar"}
                               type="text"
                               value={newContent}
                               onChange={(e) => setNewContent(e.target.value)}
                        />
                        <div className={"BtnWrapper"}>
                            <button onClick={handleUpdateTodo}>Save</button>
                            <button onClick={() => setIsModalOpen(false)}>Cancel</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};
export default TodoItem;
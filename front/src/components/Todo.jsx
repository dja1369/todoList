import TodoEditor from "./TodoEditor.jsx";
import TodoList from "./TodoList.jsx";
import {useEffect, useState} from "react";
import {authAPI, isAuthorized} from "./AuthAPI.js";

const Todo = () => {
    const [todoList, setTodoList] = useState([]);

    const handleAddTodo = () => {
        if(isAuthorized()){
            getTodoApi();
        }
    }

    const getTodoApi = () => {
        authAPI.get(`/api/v1/todos`)
            .then((response) => {
                    setTodoList(response.data.result);
                }
            ).then((error) => {
            console.log(error);
        });
    }

    useEffect(() => {
        handleAddTodo();
    }, [])

    return (
        <>
            <TodoEditor addTodo={handleAddTodo}/>
            <TodoList todoList={todoList} getTodoApi={getTodoApi}/>
        </>
    )
}
export default Todo;
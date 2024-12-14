import TodoEditor from "./TodoEditor.jsx";
import TodoList from "./TodoList.jsx";
import {useEffect, useState} from "react";

const Todo = () => {
    const [todoList, setTodoList] = useState([]);

    const handleAddTodo = (content) => {
        if (!content) {
            return;
        }
        setTodoList([...todoList, content]);
    }
    useEffect(() => {
        console.log(todoList);
    }, [todoList])

    return (
        <>
            <TodoEditor addTodo={handleAddTodo}/>
            <TodoList todoList={todoList}/>
        </>
    )
}
export default Todo;
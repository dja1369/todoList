import "../styles/todo-list.css";
import TodoItem from "./TodoItem.jsx";

const TodoList = ({todoList}) => {
    console.log(todoList);
    return (
        <div className={"TodoList"}>
            <h4>Todo List</h4>
            <input className={"search-bar"} type="text" placeholder="Search todo"/>
            <div className={"item-wrapper"}>
                {todoList.length === 0 ? (
                    <h2>No todos available</h2>
                ) : (
                    todoList.map((item) => (
                        <TodoItem content={item}/>
                    ))
                )}
            </div>
        </div>
    )
}

export default TodoList;
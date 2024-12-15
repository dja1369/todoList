import "../styles/todo-list.css";
import TodoItem from "./TodoItem.jsx";

const TodoList = ({todoList, getTodoApi}) => {
    const notCompletedTodoList = todoList.filter((item) => item.status !== "DONE");
    const sortedTodoList = [...notCompletedTodoList].sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));

    return (
        <div className={"TodoList"}>
            <h4>Todo List</h4>
            <input className={"search-bar"} type="text" placeholder="Search todo"/>
            <div className={"item-wrapper"}>
                {sortedTodoList.length === 0 ? (
                    <h2>No todos available</h2>
                ) : (
                    sortedTodoList.map((item) => (
                        <TodoItem
                            id={item.id}
                            content={item.title}
                            createdAt={item.createdAt}
                            getTodoApi={getTodoApi}
                        />
                    ))
                )}
            </div>
        </div>
    )
}

export default TodoList;
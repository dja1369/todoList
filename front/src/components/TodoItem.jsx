import "../styles/todo-item.css";

const TodoItem = ({content}) => {
    return (
        <div className={"TodoItem"}>
            <div className={"checkbox-col"}>
                <input type="checkbox"/>
            </div>
            <span className={"title-col"}>{content}</span>
            <span> {new Date().toLocaleDateString()}</span>
            <div className={"btn-col"}>
                <button>Delete</button>
            </div>
        </div>
    );
}
export default TodoItem;
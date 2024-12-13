import React from "react";

const InputComponent = ({placeholder, type, name}) => {
    return (
        <input
            placeholder={placeholder}
            type={type}
            name={name}
            required
        />
    )
}


export default InputComponent;
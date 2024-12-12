import React from "react";

const InputComponent = ({placeholder, value, type, name, onChange}) => {
    return (
        <>
            <input
                placeholder={placeholder}
                value={value}
                type={type}
                name={name}
                onChange={onChange}
                required
            />
        </>
    )
}

export default InputComponent;
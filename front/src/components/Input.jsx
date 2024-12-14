const Input = ({value, placeholder, type, onClick, onChange,require=true}) => {
    return (
        <input
            value={value}
            placeholder={placeholder}
            type={type}
            required={require}
            onClick={onClick}
            onChange={onChange}
        />
    )
}


export default Input;
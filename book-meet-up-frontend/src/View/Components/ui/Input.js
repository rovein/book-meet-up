import React from 'react'

function Input(props) {
    return (
        <input
            disabled={props.disabled ? props.disabled : false}
            className={props.className ? props.className : 'input'}
            type={props.type}
            placeholder={props.placeholder}
            value={props.value}
            onChange={(e) => props.onChange(e.target.value)}/>
    )
}

export default Input;

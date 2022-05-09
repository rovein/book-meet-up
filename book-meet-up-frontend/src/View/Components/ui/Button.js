import React from 'react'

function Button(props) {
    return (
        <button
            style={props.style}
            className={props.className ? props.className : 'btn'}
            type="submit"
            disabled={props.disabled}
            onClick={() => props.onClick ? props.onClick() : () => null}>
            {props.text}
        </button>
    )
}

export default Button;

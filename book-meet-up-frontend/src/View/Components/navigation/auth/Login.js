import React from 'react'
import Header from '../../ui/Header'
import LoginForm from '../../auth/LoginForm'

function Login() {
    return (
        <div className="signIn">
            <Header/>
            <div className="container">
                <LoginForm/>
            </div>
        </div>
    )
}

export default Login;

import React, {useState} from 'react'
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {MDBContainer, MDBInput} from "mdb-react-ui-kit";

function ResetPassword() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [passwordRepeat, setPasswordRepeat] = useState('')
    const [error, setError] = useState('')

    const history = useNavigate()

    const resetPassword = async () => {
        if (password !== passwordRepeat) {
            setError("Passwords are different")
            return
        }
        try {
            const response = await axios.post('http://localhost:8080/user/reset-password', {email, password})
            localStorage.setItem('user', JSON.stringify(response.data))
            history('/signin')
        } catch (error) {
            if (error.response && error.response.data) {
                const errorMessages = error.response.data
                setError(
                    Object.entries(errorMessages)
                        .map(([field, message]) => `${field}: ${message}`)
                        .join(', ')
                )
            } else {
                setError('Failed to reset password, please try again.')
            }
        }
    }

    return (
        <div className="d-flex justify-content-center align-items-center vh-100">
            <div className="border rounded-lg p-4" style={{width: '500px', height: 'auto'}}>
                <MDBContainer className="p-3">
                    <h2 className="mb-4 text-center">Reset Password Page</h2>
                    <MDBInput
                        wrapperClass='mb-4'
                        placeholder='Email address'
                        id='email'
                        value={email}
                        type='email'
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <MDBInput
                        wrapperClass='mb-4'
                        placeholder='Password'
                        id='password'
                        type='password'
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <MDBInput
                        wrapperClass='mb-4'
                        placeholder='Repeat password'
                        id='password-repeat'
                        type='password'
                        value={passwordRepeat}
                        onChange={(e) => setPasswordRepeat(e.target.value)}
                    />
                    {error && <p className="text-danger">{error}</p>}
                    <button
                        className="mb-4 d-block btn-primary"
                        style={{height: '50px', width: '100%'}}
                        onClick={resetPassword}
                    >
                        Reset Password
                    </button>
                    <div className="text-center">
                        <p>Already have account? <a href="/signin">Sign in</a></p>
                        <p>Not a member? <a href="/signup">Register</a></p>
                    </div>
                </MDBContainer>
            </div>
        </div>
    )
}

export default ResetPassword

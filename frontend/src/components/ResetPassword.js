import React, {useState} from 'react'
import axios from "axios"
import {Link} from "react-router-dom"
import {MDBContainer, MDBInput} from "mdb-react-ui-kit"

function ResetPassword() {

    const BACKEND = process.env.REACT_APP_BACKEND_URL

    const [email, setEmail] = useState('')
    const [error, setError] = useState('')

    const [emailSent, setEmailSent] = useState(false)

    const resetPassword = async () => {
        try {
            await axios.post(`${BACKEND}/signin/reset-password`,
                {email}
            )
            setEmailSent(true)
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
                    {emailSent ? (
                        <div className="text-center">
                            <h2 className="mb-4 text-center">Password reset</h2>
                            <p>We send password reset link to your email address, check your inbox</p>
                            <p>Already Register? <Link to="/signin">Login</Link></p>
                        </div>
                    ) : (
                        <>
                            <h2 className="mb-4 text-center">Reset Password Page</h2>
                            <MDBInput
                                wrapperClass='mb-4'
                                placeholder='Email address'
                                id='email'
                                value={email}
                                type='email'
                                required
                                onChange={(e) => setEmail(e.target.value)}
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
                                <p>Already have account? <Link to="/signin">Sign in</Link></p>
                                <p>Not a member? <Link to="/signup">Register</Link></p>
                            </div>
                        </>
                    )}
                </MDBContainer>
            </div>
        </div>
    )
}

export default ResetPassword

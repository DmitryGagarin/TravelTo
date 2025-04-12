import {Link, useLocation, useNavigate} from "react-router-dom"
import React, {useState} from "react"
import axios from "axios"
import {MDBContainer, MDBInput} from "mdb-react-ui-kit"

function ResetPasswordCompletion() {
    const location = useLocation()
    const queryParams = new URLSearchParams(location.search)

    const email = queryParams.get('email')
    const token = queryParams.get('token')

    const [passwordFirst, setPasswordFirst] = useState('')
    const [passwordSecond, setPasswordSecond] = useState('')
    const [error, setError] = useState('')

    const navigate = useNavigate()

    const handleResetPassword = async () => {
        if (passwordFirst !== passwordSecond) {
            setError("Passwords do not match.")
            return
        }

        try {
            await axios.post(`http://localhost:8080/signin/reset-password-completion`, {
                email,
                token,
                password: passwordFirst
            })

            navigate("/signin")
        } catch (error) {
            if (error.response && error.response.data) {
                const errorMessages = error.response.data
                setError(
                    Object.entries(errorMessages)
                        .map(([field, message]) => `${field}: ${message}`)
                        .join(', ')
                )
            } else {
                setError('Failed to reset password. Please try again.')
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
                        placeholder='Password'
                        id='passwordFirst'
                        value={passwordFirst}
                        type='password'
                        required
                        onChange={(e) => setPasswordFirst(e.target.value)}
                    />
                    <MDBInput
                        wrapperClass='mb-4'
                        placeholder='Repeat Password'
                        id='passwordSecond'
                        value={passwordSecond}
                        type='password'
                        required
                        onChange={(e) => setPasswordSecond(e.target.value)}
                    />
                    {error && <p className="text-danger">{error}</p>}
                    <button
                        className="mb-4 d-block btn-primary"
                        style={{height: '50px', width: '100%'}}
                        onClick={handleResetPassword}
                    >
                        Reset Password
                    </button>
                    <div className="text-center">
                        <p>Already have an account? <Link to="/signin">Sign in</Link></p>
                        <p>Not a member? <Link to="/signup">Register</Link></p>
                    </div>
                </MDBContainer>
            </div>
        </div>
    )
}

export default ResetPasswordCompletion

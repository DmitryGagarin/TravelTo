import React, {useState} from 'react'
import axios from 'axios'
import {Link, useNavigate} from 'react-router-dom'
import {MDBContainer, MDBInput} from 'mdb-react-ui-kit'

function LoginPage() {
    const BACKEND = process.env.REACT_APP_BACKEND_URL

    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')
    const history = useNavigate()

    const handleLogin = async () => {
        try {
            if (!email || !password) {
                setError('Please enter both email and password.')
                return
            }

            const response = await axios.post(`${BACKEND}/signin`, { email, password }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            })

            localStorage.setItem('user', JSON.stringify(response.data))

            history('/attractions')
        } catch (error) {
            if (error.response && error.response.data) {
                const errorMessages = error.response.data
                setError(
                    Object.entries(errorMessages)
                        .map(([field, message]) => `${field}: ${message}`)
                        .join(', ')
                )
            } else {
                setError('Registration failed, please try again.')
            }
        }
    }

    return (
        <div className="d-flex justify-content-center align-items-center vh-100">
            <div className="border rounded-lg p-4" style={{ width: '500px', height: 'auto' }}>
                <MDBContainer className="p-3">
                    <h2 className="mb-4 text-center">Login Page</h2>
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
                    {error && <p className="text-danger">{error}</p>}
                    <button className="mb-4 d-block btn-primary" style={{ height: '50px', width: '100%' }} onClick={handleLogin}>Sign in</button>
                    <div className="text-center">
                        <p>Not a member? <Link to="/signup">Register</Link></p>
                    </div>
                    <div className="text-center">
                        <p>Forget password? <Link to="/reset-password">Reset Password</Link></p>
                    </div>
                </MDBContainer>
            </div>
        </div>
    )
}

export default LoginPage

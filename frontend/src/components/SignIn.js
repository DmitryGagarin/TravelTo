import React, { useState } from 'react'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
import { MDBContainer, MDBInput } from 'mdb-react-ui-kit'

function LoginPage() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')
    const history = useNavigate()

    // Function to refresh the access token using the refresh token
    const refreshAccessToken = async () => {
        const refreshToken = JSON.parse(localStorage.getItem('user'))?.refreshToken
        if (!refreshToken) {
            throw new Error('No refresh token available')
        }

        try {
            const email = JSON.parse(localStorage.getItem('user'))?.email
            // const password = JSON.parse(localStorage.getItem('user'))?.password
            console.log(JSON.parse(localStorage.getItem('user')))
            console.log(password)
            const response = await axios.post(
                'http://localhost:8080/signin/refresh', {
                    refreshToken,
                    email,
                    password
                })
            const newAccessToken = response.data.accessToken  // New access token returned from backend
            localStorage.setItem('user', JSON.stringify({ token: newAccessToken, refreshToken }))
            return newAccessToken
        } catch (error) {
            console.error('Error refreshing token:', error)
            throw new Error('Session expired, please login again')
        }
    }

    // Function to attempt login with the current or refreshed token
    const attemptLogin = async (token) => {
        try {
            const response = await axios.post('http://localhost:8080/signin', { email, password }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            })

            // Assuming the response contains a new access token and refresh token
            const newAccessToken = response.data.accessToken
            const refreshToken = response.data.refreshToken

            // Save tokens in localStorage
            localStorage.setItem('user', JSON.stringify({ token: newAccessToken, refreshToken }))

            // Navigate to home after successful login
            history('/home')
        } catch (error) {
            console.log('Login attempt failed:', error)
            setError('Invalid email or password.')
        }
    }

    // Function to handle login logic, including refreshing token if expired
    const handleLogin = async () => {
        try {
            if (!email || !password) {
                setError('Please enter both email and password.')
                return
            }

            const user = JSON.parse(localStorage.getItem('user'))
            const accessToken = user?.accessToken

            // Send login request
            const response = await axios.post('http://localhost:8080/signin', { email, password }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${accessToken}`
                }
            })

            // Save user token after login
            const newAccessToken = response.data.accessToken  // New access token returned from login
            const refreshToken = response.data.refreshToken  // New refresh token returned from login
            localStorage.setItem('user', JSON.stringify({ token: newAccessToken, refreshToken }))

            // Navigate to home after successful login
            history('/home')
        } catch (error) {
            if (error.response && error.response.status === 401) {
                // Handle token expiry error and try refreshing the token
                try {
                    const newAccessToken = await refreshAccessToken()
                    await attemptLogin(newAccessToken)  // Retry login with new token
                } catch (refreshError) {
                    setError(refreshError.message || 'Session expired. Please login again.')
                }
            } else {
                setError(error.message || 'Invalid email or password.')
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
                    {error && <p className="text-danger">{error}</p>} {/* Render error message if exists */}
                    <button className="mb-4 d-block btn-primary" style={{ height: '50px', width: '100%' }} onClick={handleLogin}>Sign in</button>
                    <div className="text-center">
                        <p>Not a member? <a href="/signup">Register</a></p>
                    </div>
                </MDBContainer>
            </div>
        </div>
    )
}

export default LoginPage

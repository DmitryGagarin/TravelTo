import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import {
    MDBContainer,
    MDBInput
} from 'mdb-react-ui-kit';

function SignUpStepOne() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState('');
    const history = useNavigate();

    const handleSignup = async () => {

        try {
            const response = await axios.post('http://localhost:8080/signup', {
                email,
                password
            }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            localStorage.setItem('user', JSON.stringify(response.data))
            history('/signup/name');
        } catch (error) {
            if (error.response && error.response.data) {
                const errorMessages = error.response.data;
                setError(
                    Object.entries(errorMessages)
                        .map(([field, message]) => `${field}: ${message}`)
                        .join(', ')
                );
            } else {
                console.error('Signup failed:', error.message);
                setError('Signup failed, please try again.');
            }
        }
    };

    return (
        <div className="d-flex justify-content-center align-items-center vh-100">
            <div className="border rounded-lg p-4" style={{width: '600px', height: 'auto'}}>
                <MDBContainer className="p-3">
                    <h2 className="mb-4 text-center">Sign Up Page</h2>
                    {error && <p className="text-danger">{error}</p>}
                    <MDBInput
                        wrapperClass='mb-3'
                        placeholder='Email Address'
                        id='email'
                        value={email}
                        type='email'
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <MDBInput
                        wrapperClass='mb-3'
                        placeholder='Password'
                        id='password'
                        type='password'
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <MDBInput
                        wrapperClass='mb-3'
                        placeholder='Confirm Password'
                        id='confirmPassword'
                        type='password'
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                    />
                    <button className="mb-4 d-block mx-auto fixed-action-btn btn-primary"
                            style={{height: '40px', width: '100%'}}
                            onClick={handleSignup}>Sign Up
                    </button>

                    <div className="text-center">
                        <p>Already Register? <a href="/signin">Login</a></p>
                    </div>

                </MDBContainer>
            </div>
        </div>
    );
}

export default SignUpStepOne;

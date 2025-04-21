import React, { useEffect, useState } from "react"
import { useLocation, useNavigate } from "react-router-dom"
import { MDBContainer, MDBSpinner, MDBBtn } from "mdb-react-ui-kit"
import axios from "axios"

function VerificationCompleted() {
    const BACKEND = process.env.REACT_APP_BACKEND_URL
    const location = useLocation()
    const queryParams = new URLSearchParams(location.search)
    const navigate = useNavigate()

    const email = queryParams.get('email')
    const token = queryParams.get('token')

    const [verificationState, setVerificationState] = useState({
        loading: true,
        success: false,
        error: null
    })

    useEffect(() => {
        const verifyAccount = async () => {
            try {
                const response = await axios.get(
                    `${BACKEND}/signin/verification-completed`,
                    {
                        params: { email, token }
                    }
                )

                // Handle raw boolean response (true/false)
                const isVerified = response.data

                setVerificationState({
                    loading: false,
                    success: isVerified,
                    error: isVerified ? null : "Invalid verification token or expired link"
                })

                if (isVerified) {
                    setTimeout(() => navigate('/signin'), 2000)
                }
            } catch (error) {
                console.error("Verification error:", error)
                setVerificationState({
                    loading: false,
                    success: false,
                    error: error.response?.data?.message ||
                        "Verification service unavailable. Please try again later."
                })
            }
        }

        if (email && token) {
            verifyAccount()
        } else {
            setVerificationState({
                loading: false,
                success: false,
                error: "Missing verification parameters in the URL"
            })
        }
    }, [email, token, navigate, BACKEND])

    return (
        <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
            <div className="border rounded-lg p-4 shadow-sm bg-white" style={{ width: '600px' }}>
                <MDBContainer className="p-3 text-center">
                    {verificationState.loading ? (
                        <div className="py-4">
                            <MDBSpinner grow size="lg" className="mx-2" color="primary" />
                            <h4 className="mt-3">Verifying your account...</h4>
                        </div>
                    ) : verificationState.success ? (
                        <div className="py-3">
                            <div className="text-success mb-3">
                                <i className="fas fa-check-circle fa-4x"></i>
                                <h2 className="mt-3">Account Verified!</h2>
                            </div>
                            <p className="text-muted">You'll be redirected to login shortly...</p>
                        </div>
                    ) : (
                        <div className="py-3">
                            <div className="text-danger mb-3">
                                <i className="fas fa-times-circle fa-4x"></i>
                                <h2 className="mt-3">Verification Failed</h2>
                            </div>
                            <p className="text-muted mb-4">{verificationState.error}</p>
                            <div className="d-flex justify-content-center gap-3">
                                <MDBBtn
                                    color="primary"
                                    onClick={() => navigate('/signin')}
                                >
                                    Go to Login
                                </MDBBtn>
                                <MDBBtn
                                    color="secondary"
                                    onClick={() => window.location.reload()}
                                >
                                    Try Again
                                </MDBBtn>
                            </div>
                        </div>
                    )}
                </MDBContainer>
            </div>
        </div>
    )
}

export default VerificationCompleted
import React, {useEffect, useState} from "react"
import {useLocation, useNavigate} from "react-router-dom"
import {MDBContainer} from "mdb-react-ui-kit"
import axios from "axios"

function VerificationCompleted() {
    const BACKEND = process.env.REACT_APP_BACKEND_URL

    const location = useLocation()
    const queryParams = new URLSearchParams(location.search)

    const email = queryParams.get('email')
    const token = queryParams.get('token')

    const navigate = useNavigate()

    const [isVerifiedCorrectly, setIsVerifiedCorrectly] = useState(false)

    useEffect(() => {
        const checkAccountVerification = async () => {
            try {
                const encodedEmail = encodeURIComponent(email)
                const encodedToken = encodeURIComponent(token)

                const response = await axios.get(
                    `${BACKEND}/signin/verification-completed?email=${encodedEmail}&token=${encodedToken}`
                )

                if (response.data.success) {
                    setIsVerifiedCorrectly(true)
                    navigate('/signin')
                } else {
                    setIsVerifiedCorrectly(false)
                }
            } catch (error) {
                console.error("Error verifying account:", error)
                setIsVerifiedCorrectly(false)
            }
        }

        if (email && token) {
            checkAccountVerification()
        } else {
            setIsVerifiedCorrectly(false)
        }
    }, [email, token, navigate])

    return (
        <div className="d-flex justify-content-center align-items-center vh-100">
            <div className="border rounded-lg p-4" style={{ width: '600px', height: 'auto' }}>
                <MDBContainer className="p-3">
                    {isVerifiedCorrectly ? (
                        <div>
                            <h2>Account successfully verified!</h2>
                        </div>
                    ) : (
                        <div>
                            <h2>Can't verify account. Please check the link or try again.</h2>
                        </div>
                    )}
                </MDBContainer>
            </div>
        </div>
    )
}

export default VerificationCompleted

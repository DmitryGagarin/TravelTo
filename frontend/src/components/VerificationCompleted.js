import React, { useEffect, useState } from "react"
import {useLocation, useNavigate} from "react-router-dom"
import { MDBContainer } from "mdb-react-ui-kit"
import axios from "axios"

function VerificationCompleted() {
    const location = useLocation()
    const queryParams = new URLSearchParams(location.search)

    const email = queryParams.get('email')
    const token = queryParams.get('token')

    const history = useNavigate()

    console.log(email)
    console.log(token)

    const [isVerifiedCorrectly, setIsVerifiedCorrectly] = useState(false)

    useEffect(() => {
        const checkAccountVerification = async () => {
            try {
                const encodedEmail = encodeURIComponent(email)
                const encodedToken = encodeURIComponent(token)

                const response = await axios.get(
                    `http://localhost:8080/signin/verification-completed?email=${encodedEmail}&token=${encodedToken}`
                )
                setIsVerifiedCorrectly(response.data)
                history('/signin')
            } catch (error) {
                console.log(error)
            }
        }

        if (email && token) {
            checkAccountVerification()
        }
    }, [email, token])

    return (
        <div className="d-flex justify-content-center align-items-center vh-100">
            <div className="border rounded-lg p-4" style={{ width: '600px', height: 'auto' }}>
                <MDBContainer className="p-3">
                    {isVerifiedCorrectly ? (
                        <div>
                            <h2> Account successfully verified </h2>
                        </div>
                    ) : (
                        <div>
                            <h2>Can't verify account</h2>
                        </div>
                    )}
                </MDBContainer>
            </div>
        </div>
    )
}

export default VerificationCompleted

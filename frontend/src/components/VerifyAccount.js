import {MDBContainer} from "mdb-react-ui-kit"
import React, {useEffect} from "react"
import {Link, useParams} from "react-router-dom";
import axios from "axios";

function VerifyAccount() {

    const {email} = useParams('')

    useEffect(() => {
        const sendVerificationEmail = async () => {
            try {
                await axios.post(`http://localhost:8080/signin/verify-account/${email}`)
            } catch (error) {
                console.log(error)
            }
        }
        sendVerificationEmail()
    }, [email]);

    return (
        <div className="d-flex justify-content-center align-items-center vh-100">
            <div className="border rounded-lg p-4" style={{width: '600px', height: 'auto'}}>
                <MDBContainer className="p-3">
                    <div className="text-center">
                        <h2 className="mb-4 text-center">Account verification</h2>
                        <p>We send verification to your email address, check your inbox</p>
                        <p>Already Register? <Link to="/signin">Login</Link></p>
                    </div>
                </MDBContainer>
            </div>
        </div>
    )
}

export default VerifyAccount
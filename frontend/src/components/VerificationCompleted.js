import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { MDBContainer } from "mdb-react-ui-kit";
import axios from "axios";

function VerificationCompleted() {
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);

    const email = queryParams.get('email');
    const token = queryParams.get('token');

    const navigate = useNavigate();  // Updated for consistency

    console.log("Email:", email);
    console.log("Token:", token);

    const [isVerifiedCorrectly, setIsVerifiedCorrectly] = useState(false);

    useEffect(() => {
        const checkAccountVerification = async () => {
            try {
                // Make sure email and token are present
                if (!email || !token) {
                    console.log("Missing email or token");
                    return;
                }

                const encodedEmail = encodeURIComponent(email);
                const encodedToken = encodeURIComponent(token);

                const response = await axios.get(
                    `http://localhost:8080/signin/verification-completed?email=${encodedEmail}&token=${encodedToken}`
                );

                if (response.data.success) {  // Assuming the response includes a 'success' key
                    setIsVerifiedCorrectly(true);
                    navigate('/signin');  // Redirect after successful verification
                } else {
                    setIsVerifiedCorrectly(false);  // Handle unsuccessful verification
                }
            } catch (error) {
                console.error("Error verifying account:", error);
                setIsVerifiedCorrectly(false);  // Handle errors gracefully
            }
        };

        // If both email and token are available, proceed with the verification check
        if (email && token) {
            checkAccountVerification();
        } else {
            setIsVerifiedCorrectly(false);  // If email or token is missing, show failure
        }
    }, [email, token, navigate]);

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
    );
}

export default VerificationCompleted;

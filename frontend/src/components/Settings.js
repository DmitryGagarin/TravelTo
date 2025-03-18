import React from 'react'
import {useNavigate} from "react-router-dom"

function Settings() {

    const history = useNavigate()

    const handleAccount = () => {
        history('/settings/account')
    }

    const handleBusinessOwner = () => {
        history('/settings/business')
    }

    const handleMyBusiness = () => {
        history('/settings/my')
    }

    const handleHome = () => {
        history('/home')
    }

    const handleLogout = () => {
        history('/'); // Redirect to login page
    };

    return (
        <div>
            <div className="text-center">
                <button type="button" className="btn btn-primary mt-3" onClick={handleAccount}>Account</button>
            </div>
            <div className="text-center">
                <button type="button" className="btn btn-primary mt-3" onClick={handleMyBusiness}>My business</button>
            </div>
            <div className="text-center">
                <button type="button" className="btn btn-primary mt-3" onClick={handleBusinessOwner}>Are you a business owner?</button>
            </div>
            <div className="text-center">
                <button type="button" className="btn btn-primary mt-3" onClick={handleHome}>Home</button>
            </div>
            <div className="text-center">
                <button type="button" className="btn btn-primary mt-3" onClick={handleLogout}>Logout</button>
            </div>
        </div>
    )
}

export default Settings
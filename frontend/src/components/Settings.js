import React from 'react'
import {useNavigate} from "react-router-dom"
import Header from "./Header";

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
            <Header/>
            <div className="settings">
                <div className="text-center">
                    <button type="button" className="btn btn-primary mt-3 settings-button"
                            onClick={handleAccount}>Account
                    </button>
                </div>
                <div className="text-center">
                    <button type="button" className="btn btn-primary mt-3 settings-button" onClick={handleMyBusiness}>My
                        business
                    </button>
                </div>
                <div className="text-center">
                    <button type="button" className="btn btn-primary mt-3 settings-button"
                            onClick={handleBusinessOwner}>Are you a business owner?
                    </button>
                </div>
                <div className="text-center">
                    <button type="button" className="btn btn-primary mt-3 settings-button" onClick={handleHome}>Home
                    </button>
                </div>
                <div className="text-center">
                    <button type="button" className="btn btn-primary mt-3 settings-button"
                            onClick={handleLogout}>Logout
                    </button>
                </div>
            </div>
        </div>
    )
}

export default Settings
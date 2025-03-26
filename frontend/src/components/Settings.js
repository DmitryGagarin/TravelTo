import React from 'react'
import {useNavigate} from "react-router-dom"
import Header from "./Header"

function Settings() {

    const isAdmin = JSON.parse(localStorage.getItem('user')).data.role === "ADMIN_USER"

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

    const handleLogout = () => {
        history('/')
    }

    const handleAdminModeration = () => {
        history('admin/moderation/on_moderation')
    }

    return (
        <div>
            <Header/>
            {isAdmin ? (
                <div className="settings">
                    <div className="text-center">
                        <button type="button" className="btn btn-primary mt-3 settings-button"
                                onClick={handleAccount}>Account
                        </button>
                    </div>
                    <div className="text-center">
                        <button type="button" className="btn btn-primary mt-3 settings-button"
                                onClick={handleMyBusiness}>My
                            business
                        </button>
                    </div>
                    <div className="text-center">
                        <button type="button" className="btn btn-primary mt-3 settings-button"
                                onClick={handleBusinessOwner}>Are you a business owner?
                        </button>
                    </div>
                    <div className="text-center">
                        <button type="button" className="btn btn-primary mt-3 settings-button"
                                onClick={handleLogout}>Logout
                        </button>
                    </div>
                    <div className="text-center">
                        <button type="button" className="btn btn-primary mt-3 settings-button"
                                onClick={handleAdminModeration}>Moderation
                        </button>
                    </div>
                </div>
            ) : (
                <div className="settings">
                    <div className="text-center">
                        <button type="button" className="btn btn-primary mt-3 settings-button"
                                onClick={handleAccount}>Account
                        </button>
                    </div>
                    <div className="text-center">
                        <button type="button" className="btn btn-primary mt-3 settings-button"
                                onClick={handleMyBusiness}>My
                            business
                        </button>
                    </div>
                    <div className="text-center">
                        <button type="button" className="btn btn-primary mt-3 settings-button"
                                onClick={handleBusinessOwner}>Are you a business owner?
                        </button>
                    </div>
                    <div className="text-center">
                        <button type="button" className="btn btn-primary mt-3 settings-button"
                                onClick={handleLogout}>Logout
                        </button>
                    </div>
                </div>
            )}
        </div>
    )
}


export default Settings
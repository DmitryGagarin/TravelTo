import React, {useEffect, useState} from 'react'
import {useNavigate} from "react-router-dom"
import Header from "./Header"
import axios from "axios"

function Settings() {
    const BACKEND = process.env.REACT_APP_BACKEND_URL

    const [isAdmin, setIsAdmin] = useState(false)
    const [isOwner, setIsOwner] = useState(false)

    const authUser = JSON.parse(localStorage.getItem('user'))

    const history = useNavigate()

    useEffect(() => {
        const getUser = async () => {
            try {
                const response = await axios.get(`${BACKEND}/user/get`, {
                    headers: {
                        'Authorization': `Bearer ${authUser.accessToken}`
                    }
                })

                const roles = response.data.role.map(role => role.authority)
                setIsOwner(roles.includes("ROLE_OWNER"))
                setIsAdmin(roles.includes("ROLE_ADMIN"))

            } catch (error) {
                console.error(error)
            }
        }

        getUser()
    }, [authUser.accessToken])

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
        history('/signin')
    }

    const handleAdminModeration = () => {
        history('/settings/admin/moderation/on_moderation')
    }

    return (
        <div>
            <Header />
            <div className="settings">
                <div className="text-center">
                    <button type="button" className="btn btn-primary mt-3 settings-button"
                            onClick={handleAccount}>Account
                    </button>
                </div>
                {isOwner && (
                    <div className="text-center">
                        <button type="button" className="btn btn-primary mt-3 settings-button"
                                onClick={handleMyBusiness}>My Business
                        </button>
                    </div>
                )}
                <div className="text-center">
                    <button type="button" className="btn btn-primary mt-3 settings-button"
                            onClick={handleBusinessOwner}>Are you a business owner?
                    </button>
                </div>
                {isAdmin && (
                    <div className="text-center">
                        <button type="button" className="btn btn-primary mt-3 settings-button"
                                onClick={handleAdminModeration}>Moderation
                        </button>
                    </div>
                )}
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

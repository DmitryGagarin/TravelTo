import React, { useEffect, useState } from 'react'
import { useNavigate } from "react-router-dom"
import Header from "./Header"
import axios from "axios";

function Settings() {

    const [isAdmin, setIsAdmin] = useState(false)
    const [isOwner, setIsOwner] = useState(false)
    const [response, setResponse] = useState([])

    const authUser = JSON.parse(localStorage.getItem('user'))

    const history = useNavigate()

    useEffect(() => {
        const getUser = async () => {
            try {
                const response = await axios.get('http://localhost:8080/user/get', {
                    headers: {
                        'Authorization': `Bearer ${authUser.accessToken}`
                    }
                })

                setResponse(response.data)
                const roles = response.data.role.map(role => role.authority);
                setIsOwner(roles.includes("ROLE_ADMIN"))
                setIsAdmin(roles.includes("ROLE_OWNER"))

            } catch (error) {
                console.error(error)
            }
        }

        getUser()
    }, [authUser.accessToken])

    // useEffect(() => {
    //     console.log(response)
    // }, [response]);

    // console.log("isAdmin", isAdmin)
    // console.log("isOwner", isOwner)
    // console.log("response", response.data)

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
        history('admin/moderation/on_moderation')
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

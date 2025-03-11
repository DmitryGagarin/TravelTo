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

    const handleHome = () => {
        history('/home')
    }

    return (
        <div>
            <div className="text-center">
                <button type="button" className="btn btn-primary mt-3" onClick={handleAccount}>Account</button>
            </div>
            <div className="text-center">
                <button type="button" className="btn btn-primary mt-3" onClick={handleBusinessOwner}>Are you a business owner?</button>
            </div>
            <div className="text-center">
                <button type="button" className="btn btn-primary mt-3" onClick={handleHome}>Home</button>
            </div>
        </div>
    )
}

export default Settings
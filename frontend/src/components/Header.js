import React, {useEffect, useState} from 'react'
import {useNavigate} from 'react-router-dom'
import {FaCog, FaHeart, FaUserAlt} from 'react-icons/fa'
import axios from "axios";

function Header() {

    const [userName, setUserName] = useState('')
    const authUser = JSON.parse(localStorage.getItem('user'))


    useEffect(() => {
        const getUser = async () => {
            try {
                const response = await axios.get('http://localhost:8080/user/get', {
                    headers: {
                        'Authorization': `Bearer ${authUser.accessToken}`
                    }
                })
                setUserName(response.data.name)
            } catch (error) {
                console.error(error)
            }
        }

        getUser()
    }, [authUser.accessToken])

        const navigate = useNavigate()

        const handleAccountClick = () => {
            navigate('/settings/account')
        }

        const handleSettingsClick = () => {
            navigate('/settings')
        }

        const handleLiked = () => {
            navigate('/likes')
        }

        const handleAttractionsClick = () => {
            navigate('/attractions')
        }

        const handleHomeClick = () => {
            navigate('/home')
        }

        return (
            <header className="d-flex justify-content-between align-items-center p-3 shadow-sm"
                    style={{position: 'fixed', top: 0, left: 0, right: 0, zIndex: 1000, backgroundColor: 'white'}}>
                <button onClick={handleAttractionsClick} className="btn btn-light me-3">
                    Attractions
                </button>
                <button onClick={handleHomeClick} className="btn btn-light me-3">
                    Home
                </button>
                <div className="d-flex align-items-center">
                    <p className="mb-0 me-3">
                        Hello, {userName}!
                    </p>
                    <button onClick={handleAccountClick} className="btn btn-light me-3">
                        <FaUserAlt size={24}/>
                    </button>
                    <button onClick={handleSettingsClick} className="btn btn-light">
                        <FaCog size={24}/>
                    </button>
                    <button onClick={handleLiked} className="btn btn-light">
                        <FaHeart size={24}/>
                    </button>
                </div>
            </header>
        )
    }

    export default Header

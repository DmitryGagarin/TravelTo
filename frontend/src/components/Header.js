import React, {useState} from 'react'
import {useNavigate} from 'react-router-dom'
import {FaCog, FaHeart, FaUserAlt} from 'react-icons/fa'

function Header() {
    const [user, setUser] = useState('')
    const navigate = useNavigate();

    const handleAccountClick = () => {
        navigate('/settings/account'); // Redirect to the account page
    };

    const handleSettingsClick = () => {
        navigate('/settings'); // Redirect to the settings page
    };

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
                    Hello, {JSON.parse(localStorage.getItem('user')).data.name}!
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
    );
}

export default Header;

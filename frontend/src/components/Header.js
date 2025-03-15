import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import { FaUserAlt, FaCog } from 'react-icons/fa'; // Importing icons for Account and Settings

function Header() {
    const [user, setUser] = useState('')
    const navigate = useNavigate();

    const handleAccountClick = () => {
        navigate('/settings/account'); // Redirect to the account page
    };

    const handleSettingsClick = () => {
        navigate('/settings'); // Redirect to the settings page
    };

    const handleAttractionsClick = () => {
        navigate('/attractions')
    }

    const handleHomeClick = () => {
        navigate('/home')
    }

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user'))
        setUser(user)
    }, []);

    return (
        <header className="d-flex justify-content-between align-items-center p-3 shadow-sm" style={{ position: 'fixed', top: 0, left: 0, right: 0, zIndex: 1000, backgroundColor: 'white' }}>
            <div></div> {/* Empty div to maintain balance on the left side */}
            <button onClick={handleAttractionsClick} className="btn btn-light me-3">
                Attractions
            </button>
            <button onClick={handleHomeClick} className="btn btn-light me-3">
                Home
            </button>
            <div className="d-flex align-items-center">
                {user && <p className="mb-0 me-3">Hello, {user.name}!</p>} {/* Display user email */}
                <button onClick={handleAccountClick} className="btn btn-light me-3">
                    <FaUserAlt size={24} />
                </button>
                <button onClick={handleSettingsClick} className="btn btn-light">
                    <FaCog size={24} />
                </button>
            </div>
        </header>
    );
}

export default Header;

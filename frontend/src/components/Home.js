import React, {useEffect, useState} from 'react';
import { useNavigate} from 'react-router-dom';
import {hover} from "@testing-library/user-event/dist/hover";

function Home({ email }) {
    const [user, setUser] = useState(null)
    const history = useNavigate();

    useEffect(() => {
        const storedUser = localStorage.getItem('user')
        if (storedUser) {
            setUser(JSON.parse(storedUser))
        }
    }, []);

    const handleLogout = () => {
        history('/');
    };

    const handleAccount = () => {
        history('/settings/profile')
    }

    return (
        <div className="d-flex justify-content-center align-items-center vh-100">
            <div className="border rounded-lg p-4" style={{width: '500px', height: '400px'}}>
                <h2 className="mb-4 text-center">Welcome to Dashboard</h2>
                <p className="mb-4 text-center">Hello, {user?.username}!</p>
                <p className="text-center">You are logged in successfully.</p>
                <div className="text-center">
                    <button type="button" className="btn btn-primary mt-3" onClick={handleLogout}>Logout</button>
                </div>
                <div className="text-center">
                    <button type="button" className="btn btn-primary mt-3" onClick={handleAccount}>Account</button>
                </div>
            </div>
        </div>
    );
}

export default Home;

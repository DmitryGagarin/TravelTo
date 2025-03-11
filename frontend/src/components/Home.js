import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from "axios";

function Home() {
    const [user, setUser] = useState(null);
    const [attractions, setAttractions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const history = useNavigate();

    // Set the user state based on the stored user in localStorage
    useEffect(() => {
        const storedUser = localStorage.getItem('user');
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        } else {
            history('/'); // If there's no user, redirect to login page
        }
    }, [history]);

    // Fetch attractions only once
    useEffect(() => {
        const fetchAttractions = async () => {
            try {
                setLoading(true);
                const token = JSON.parse(localStorage.getItem('user'))?.token;
                if (!token) {
                    setError("No token found. Please log in again.");
                    return;
                }
                const response = await axios.get("http://localhost:8080/attraction", {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    },
                });
                setAttractions(response.data.content); // Assuming response.data.content is the list
            } catch (err) {
                setError(err.message || "An error occurred while fetching attractions");
            } finally {
                setLoading(false);
            }
        };

        if (user) { // Only fetch attractions if the user exists
            fetchAttractions();
        }
    }, [user]); // Dependency on user to avoid unnecessary fetches

    // Handle loading state
    if (loading) {
        return <div>Loading...</div>;
    }

    // Handle error state
    if (error) {
        return <div>Error: {error}</div>;
    }

    const handleLogout = () => {
        history('/'); // Redirect to login page
    };

    const handleSettings = () => {
        history('/settings'); // Redirect to settings page
    };

    return (
        <div className="d-flex justify-content-center align-items-center vh-100">
            <div className="border rounded-lg p-4" style={{ width: '500px', height: '400px' }}>
                <h2 className="mb-4 text-center">Welcome to Dashboard</h2>
                <p className="mb-4 text-center">Hello, {user?.username}!</p>
                <p className="text-center">You are logged in successfully.</p>
                <div className="text-center">
                    <button type="button" className="btn btn-primary mt-3" onClick={handleLogout}>Logout</button>
                </div>
                <div className="text-center">
                    <button type="button" className="btn btn-primary mt-3" onClick={handleSettings}>Settings</button>
                </div>
            </div>
            <div>
                <h1>Attractions</h1>
                <ul>
                    {attractions.map((attraction) => (
                        <li key={attraction.id}>{attraction.name}</li> // Adjust based on actual property names
                    ))}
                </ul>
            </div>
        </div>
    );
}

export default Home;

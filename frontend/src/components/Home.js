import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import Header from './Header'; // Import the Header component

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

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div>
            <Header />
            <div className="attractions-container">
                <h1>Attractions</h1>
                <div className="cards-container">
                    {attractions.map((attraction) => (
                        <div key={attraction.id} className="attraction-card">
                            <div className="image-container">
                                <img
                                    src={attraction.image}
                                    alt={attraction.name}
                                    className="card-image"
                                />
                                <div className="attraction-type">{attraction.type}</div>
                            </div>
                            <div className="rating">
                                Rating: {attraction.rating} {/* Assuming you want to show the rating */}
                            </div>
                            <div className="opening-time">
                                <p>Opening Time: {attraction.openTime}</p>
                                <p>Closing Time: {attraction.closeTime}</p>
                            </div>
                            <div className="contact-info">
                                <p>
                                    Website:{" "}
                                    <a
                                        href={attraction.website}
                                        target="_blank"
                                        rel="noopener noreferrer"
                                    >
                                        Visit
                                    </a>
                                </p>
                                <p>Phone: {attraction.phone}</p>
                            </div>
                            <div className="name-description">
                                <h5>{attraction.name}</h5>
                                <p>{attraction.description}</p>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );

}

export default Home;

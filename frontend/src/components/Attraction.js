import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import axios from "axios";
import Header from './Header';
import {MDBInput} from "mdb-react-ui-kit"; // Import the Header component

function Attraction() {
    const [attractions, setAttractions] = useState([]);
    const [filter, setFilter] = useState('');
    const [error, setError] = useState(null);
    const history = useNavigate();

    // Fetch attractions
    useEffect(() => {
        const fetchAttractions = async () => {
            try {
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
                setAttractions(response.data._embedded.attractionModelList); // Attractions list
            } catch (err) {
                setError(err.message || "An error occurred while fetching attractions");
            }
        };

        fetchAttractions();
    }, []);

    const handleSearch = () => {

    }

    return (
        <div>
            <Header />
            <div className="main-container">
                {/* Attraction Cards Container */}
                <div className="attractions-container">
                    <div className="cards-container">
                        {attractions.map((attraction) => (
                            <div key={attraction.id} className="attraction-card">
                                <div className="image-container">
                                    <img
                                        src={`data:image/png;base64,${attraction.image}`}
                                        alt={attraction.name}
                                        className="card-image"
                                    />
                                    <div className="attraction-type">{attraction.type}</div>
                                </div>
                                <div className="rating">
                                    Rating: {attraction.rating}
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
                                <div className="opening-time">
                                    <p>Opening Time: {attraction.openTime}</p>
                                    <p>Closing Time: {attraction.closeTime}</p>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>

                {/* Search Bar Container */}
                <div className="filter-container">
                    <MDBInput
                        type="text"
                        id="filterInput"
                        onKeyUp={handleSearch}
                        placeholder="Search"
                    />
                    <ul id="list">
                        {attractions.map((attraction) => (
                            <li key={attraction.id}>
                                <a>
                                    {attraction.name}
                                </a>
                            </li>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    );
}

export default Attraction;

import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import Settings from "./Settings";

function MyAttractions() {
    const [attractions, setAttractions] = useState([]);
    const [attractionName, setAttractionName] = useState(null); // State to store the attraction to be deleted

    const token = JSON.parse(localStorage.getItem('user'))?.token;

    // Fetch attractions on initial load
    useEffect(() => {
        const fetchAttractions = async () => {
            try {
                const response = await axios.get("http://localhost:8080/attraction/my", {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    },
                });
                setAttractions(response.data._embedded.attractionModelList);
            } catch (err) {
                console.error("Error fetching attractions:", err);
            }
        };
        fetchAttractions();
    }, [token]);

    console.log(attractionName)
    const handleDelete = async (name) => {
        try {
            // Debugging log to check if this function is called
            console.log(`Attempting to delete attraction: ${name}`);

            // Delete the attraction with the specified name
            await axios.post(`http://localhost:8080/attraction/delete/${name}`, {}, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            });

            // After deletion, filter out the deleted attraction from the list
            // setAttractions((prevAttractions) =>
            //     prevAttractions.filter(attraction => attraction.name !== name)
            // );
        } catch (err) {
            console.error("Error deleting attraction:", err);
        }
    };

    // Handle deletion when attractionName changes
    useEffect(() => {
        console.log("attractionName effect triggered:", attractionName); // Check if effect is triggered
        if (attractionName) {
            const confirmation = window.confirm("Are you sure you want to delete this attraction?");
            if (confirmation) {
                handleDelete(attractionName);
            } else {
                console.log("User canceled deletion.");
            }
            setAttractionName(null); // Reset attractionName after the confirmation
        }
    }, [attractionName, token]); // Ensure it re-runs if attractionName or token changes

    return (
        <div className="my-attractions-main-container">
            <div className="my-attractions-container">
                <div className="cards-container">
                    {attractions.map((attraction) => (
                        <div key={attraction.id} className="attraction-card my-attraction-card">
                            <div className="image-container">
                                <img
                                    src={`data:image/png;base64,${attraction.images[0]}`}
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
                            <div className="learn-more">
                                <button className="learn-more-button">
                                    <Link to={`/attraction/${attraction.name}`}>Learn More</Link>
                                </button>
                            </div>
                            <div className="name-description">
                                <h5>{attraction.name}</h5>
                                <p>{attraction.description}</p>
                            </div>
                            <div className="opening-time">
                                <p>Opening Time: {attraction.openTime}</p>
                                <p>Closing Time: {attraction.closeTime}</p>
                            </div>
                            <div className="delete-button">
                                <button onClick={() => {
                                    console.log(`Delete button clicked for ${attraction.name}`); // Check if button is clicked
                                    setAttractionName(attraction.name);
                                }}>Delete</button>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
            <Settings/>
        </div>
    );
}

export default MyAttractions;

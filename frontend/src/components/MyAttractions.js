import React, { useEffect, useState } from "react"
import axios from "axios"
import { Link } from "react-router-dom"
import Settings from "./Settings"

function MyAttractions() {
    const [attractions, setAttractions] = useState([])
    const [attractionName, setAttractionName] = useState(null) 

    const token = JSON.parse(localStorage.getItem('user')).accessToken

    useEffect(() => {
        const fetchAttractions = async () => {
            try {
                const response = await axios.get("http://localhost:8080/attraction/my", {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    },
                })
                setAttractions(response.data._embedded.attractionModelList)
            } catch (error) {
                if (error.response.status === 401) {
                    window.location.href = "http://localhost:3000/signin";
                }
                console.error("Error fetching attractions:", error)
            }
        }
        fetchAttractions()
    }, [token])

    const handleDelete = async (name) => {
        try {
            await axios.post(`http://localhost:8080/attraction/delete/${name}`, {}, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            })
            window.location.reload();
        } catch (error) {
            if (error.response.status === 401) {
                window.location.href = "http://localhost:3000/signin";
            }
            console.error("Error deleting attraction:", error)
        }
    }

    useEffect(() => {
        if (attractionName) {
            const confirmation = window.confirm("Are you sure you want to delete this attraction?")
            if (confirmation) {
                handleDelete(attractionName)
            }
            setAttractionName(null)
        }
    }, [attractionName, token])

    const getAttractionStatusStyle = (status) => {
        switch (status.toLowerCase()) {
            case 'PUBLISHED':
                return { color: 'green' }
            case 'ON_MODERATION':
                return { color: 'red' }
            default:
                return { backgroundColor: 'lightgray', color: 'black' }
        }
    }

    return (
        <div className="my-attractions-main-container">
            <div className="my-attractions-container">
                <div className="cards-container">
                    {attractions.map((attraction) => (
                        <div key={attraction.name} className="attraction-card my-attraction-card">
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
                            <div className="edit">
                                <button className="edit-button">
                                    <Link to={`/attraction/edit/${attraction.name}`}>Edit</Link>
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
                                    setAttractionName(attraction.name)
                                }}>Delete</button>
                            </div>
                            <div className="status" style={getAttractionStatusStyle(attraction.status)}>
                                <h5>{attraction.status}</h5>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
            <Settings/>
        </div>
    )
}

export default MyAttractions

import React, {useEffect, useState} from "react"
import axios from "axios"
import {Link} from "react-router-dom"
import Settings from "../Settings"
import {getImageFormat} from "../../utils/ImageUtils"
import {getAttractionStatusStyle, renderStars} from "../../utils/StyleUtils"

function MyAttractions() {
    const BACKEND = process.env.REACT_APP_BACKEND_URL
    const FRONTEND = process.env.REACT_APP_FRONTEND_URL

    const [attractions, setAttractions] = useState([])
    const [attractionName, setAttractionName] = useState(null) 

    const token = JSON.parse(localStorage.getItem('user')).accessToken

    useEffect(() => {
        const fetchAttractions = async () => {
            try {
                const response = await axios.get(`${BACKEND}/attraction/my`, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    },
                })
                setAttractions(response.data._embedded.attractionModelList)
            } catch (error) {
                if (error.response.status === 401) {
                    window.location.href = `${FRONTEND}/signin`
                }
                console.error("Error fetching attractions:", error)
            }
        }
        fetchAttractions()
    }, [token])

    const handleDelete = async (name) => {
        try {
            await axios.post(`${BACKEND}/attraction/delete/${name}`, {}, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            })
            window.location.reload()
        } catch (error) {
            if (error.response.status === 401) {
                window.location.href = `${FRONTEND}/signin`
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

    return (
        <div className="my-attractions-main-container">
            <div className="my-attractions-container">
                <div className="cards-container">
                    {attractions.map((attraction) => (
                        <div key={attraction.name} className="attraction-card my-attraction-card">
                            <div className="image-container">
                                <img
                                    src={`data:image/${getImageFormat(attraction.imagesFormats[0])};base64,${attraction.images[0]}`}
                                    alt={attraction.name}
                                    className="card-image"
                                />
                                <div className="attraction-type">{attraction.type}</div>
                            </div>
                            <div className="contact-info">
                                <p>
                                    Website:{" "}
                                    <a
                                        href={attraction.website}
                                        target="_blank"
                                        rel="noopener noreferrer"
                                    >
                                        {attraction.website}
                                    </a>
                                </p>
                                <p>Phone: {attraction.phone}</p>
                            </div>
                            <div className="rating">
                                {renderStars(attraction.rating)}
                            </div>
                            <div className="edit">
                                <button className="edit-button btn btn-info">
                                    <Link to={`/attraction/edit/${attraction.name}`}>Edit</Link>
                                </button>
                            </div>
                            <div className="learn-more learn-more-my">
                                <button className="learn-more-button btn btn-success">
                                    <Link to={`/attraction/${attraction.name}`}>Learn More</Link>
                                </button>
                            </div>
                            <div className="name-description">
                                <h5>{attraction.name}</h5>
                                <p>{attraction.description}</p>
                            </div>
                            <div className="opening-time">
                                <p>From: {attraction.openTime}</p>
                                <p>To: {attraction.closeTime}</p>
                            </div>
                            <div className="delete-button ">
                                <button
                                    onClick={() => setAttractionName(attraction.name)}
                                    className={"btn btn-danger mt-3"}
                                >
                                    Delete
                                </button>
                            </div>
                            <div className="status" style={getAttractionStatusStyle(attraction.status)}>
                                <div className="attraction-status">
                                    {attraction.status === 'published' ? (
                                        <div className="attraction-status text-success">
                                            Published
                                        </div>
                                    ) : (
                                        <div className="attraction-status text-warning">
                                            On Moderation
                                        </div>
                                    )
                                    }
                                </div>
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

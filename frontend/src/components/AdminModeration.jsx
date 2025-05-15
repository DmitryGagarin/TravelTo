import React, {useEffect, useState} from 'react'
import {getAttractionCardStyle} from "../utils/StyleUtils"
import axios from "axios"
import Settings from "./Settings"
import {getImageFormat} from "../utils/ImageUtils"
import {Link} from "react-router-dom"

// TODO: настройки улетают
const AdminModeration = () => {
    const GRAFANA_URL = process.env.REACT_APP_GRAFANA_URL
    const BACKEND = process.env.REACT_APP_BACKEND_URL
    const FRONTEND = process.env.REACT_APP_FRONTEND_URL
    const TOKEN = JSON.parse(localStorage.getItem('user'))?.accessToken

    const [attractions, setAttractions] = useState([])
    const [attractionStatus, setAttractionStatus] = useState('on_moderation')
    const [attractionName, setAttractionName] = useState('')
    const [currentImageIndex, setCurrentImageIndex] = useState(0)

    const [onModerationPage, setOnModerationPage] = useState(true)

    useEffect(() => {
        const fetchAttractions = async () => {
            try {
                const response = await axios.get(`${BACKEND}/admin/moderation/${attractionStatus}`,
                    {
                        headers: {
                            'Authorization': `Bearer ${TOKEN}`
                        }
                    })
                setAttractions(response?.data?._embedded?.attractionModelList || [])
            } catch (error) {
                if (error.response && error.response.status === 401) {
                    window.location.href = `${FRONTEND}/signin`
                }
            }
        }
        fetchAttractions()
    }, [attractionStatus])

    useEffect(() => {
        const applyModeration = async () => {
            try {
                await axios.post(`${BACKEND}/admin/apply-moderation/${attractionName}`, {},
                    {
                        headers: {
                            'Authorization': `Bearer ${JSON.parse(localStorage.getItem('user')).accessToken}`,
                        }
                    })
            } catch (error) {
                if (error.response && error.response.status === 401) {
                    window.location.href = `${FRONTEND}/signin`
                } else {
                    console.log(error.response?.status)
                }
            }
            setAttractionName('')
        }

        if (attractionName) {
            applyModeration()
        }
    }, [attractionName])

    const deleteAttraction = async (name) => {
        try {
            await axios.post(`${BACKEND}/attraction/delete/${name}`, {},
                {
                    headers: {
                        'Authorization': `Bearer ${TOKEN}`
                    }
                })
        } catch (error) {
            alert("Impossible to delete attraction")
        }
    }

    const handleNextImage = (index, images) => {
        if (images && images.length > 0) {
            return (index + 1) % images.length
        }
        return index
    }

    const handlePrevImage = (index, images) => {
        if (images && images.length > 0) {
            return (index - 1 + images.length) % images.length
        }
        return index
    }

    return (
        <div>
            <div className="attractions-main-container">
                <div className="attractions-container">
                    {(attractions.length === 0 || !attractions) && (
                        <div>
                            <h2>Nothing to moderate</h2>
                        </div>
                    )}
                    <div className="cards-container">
                        {attractions.map((attraction) => {
                            const images = attraction.images || []
                            return (
                                <div key={attraction.name} className="attraction-card">
                                    <div className="image-container">
                                        {images.length > 0 ? (
                                            <img
                                                src={`data:image/${getImageFormat(attraction.imagesFormats[currentImageIndex])};base64,${images[currentImageIndex]}`}
                                                alt={attraction.name}
                                                className="card-image"
                                            />
                                        ) : (
                                            <p>No images available</p>
                                        )}
                                        <div className="image-navigation">
                                            {/* Left Arrow Button */}
                                            <button
                                                className="image-nav-button left"
                                                onClick={() => setCurrentImageIndex(handlePrevImage(currentImageIndex, images))}
                                            >
                                                ←
                                            </button>

                                            {/* Right Arrow Button */}
                                            <button
                                                className="image-nav-button right"
                                                onClick={() => setCurrentImageIndex(handleNextImage(currentImageIndex, images))}
                                            >
                                                →
                                            </button>
                                        </div>
                                    </div>
                                    <div className="attraction-data">
                                        <div className="attraction-type"
                                             style={getAttractionCardStyle(attraction.type)}>
                                            {attraction.type}
                                        </div>
                                        <div className="attraction-status">
                                            {attraction.status === 'published' ? (
                                                <>
                                                    Published
                                                </>
                                            ) : (
                                                <>
                                                    On Moderation
                                                </>
                                            )
                                            }
                                        </div>
                                        <div className="delete-button">
                                            <button
                                                className="btn btn-danger"
                                                onClick={() => deleteAttraction(attraction.name)}>
                                                DELETE ATTRACTION
                                            </button>
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
                                        <div className="publish">
                                            <button onClick={() => setAttractionName(attraction.name)}>
                                                Publish
                                            </button>
                                        </div>
                                        <div className="name-description">
                                            <h5>{attraction.name}</h5>
                                            <p>{attraction.description}</p>
                                        </div>
                                        <div className="time">
                                            <p>Opening Time: {attraction.openTime}</p>
                                            <p>Closing Time: {attraction.closeTime}</p>
                                        </div>
                                    </div>
                                </div>
                            )
                        })}
                    </div>
                </div>
                <div className="attraction-type-changer-admin">
                    <div className="text-center">
                        <button
                            type="button"
                            className="btn btn-primary mt-3 admin-button"
                            style={onModerationPage ? {backgroundColor: '#007bff'} : {backgroundColor: 'red'}}
                            onClick={() => {
                                setAttractionStatus('published')
                                setOnModerationPage(false)
                            }}
                        >
                            Published
                        </button>
                        <button
                            type="button"
                            className="btn btn-primary mt-3 admin-button"
                            style={onModerationPage ? {backgroundColor: 'red'} : {backgroundColor: '#007bff'}}
                            onClick={() => {
                                setAttractionStatus('on_moderation')
                                setOnModerationPage(true)
                            }}
                        >
                            On Moderation
                        </button>
                        <button
                            type="button"
                            className="btn btn-primary mt-3 admin-button grafana-redirect"
                        >
                            <Link to={`${GRAFANA_URL}`} target="_blank" rel="noopener noreferrer">
                                Grafana
                            </Link>
                        </button>
                    </div>
                </div>
            </div>
            <Settings/>
        </div>
    )
}

export default AdminModeration

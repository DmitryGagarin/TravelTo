import React, {useEffect, useState} from 'react'
import {getAttractionCardStyle} from "../utils/StyleUtils"
import axios from "axios"
import Settings from "./Settings"
import {getImageFormat} from "../utils/ImageUtils"

const AdminModeration = () => {

    const [attractions, setAttractions] = useState([])
    const [attractionStatus, setAttractionStatus] = useState('on_moderation')
    const [attractionName, setAttractionName] = useState('')
    const [currentImageIndex, setCurrentImageIndex] = useState(0)

    const [onModerationPage, setOnModerationPage] = useState(true)

    useEffect(() => {
        const fetchAttractions = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/admin/moderation/${attractionStatus}`,
                    {
                        headers: {
                            'Authorization': `Bearer ${JSON.parse(localStorage.getItem('user')).accessToken}`
                        }
                    })
                setAttractions(response?.data?._embedded?.attractionModelList || [])
            } catch (error) {
                if (error.response && error.response.status === 401) {
                    window.location.href = "http://localhost:4000/signin"
                }
            }
        }
        fetchAttractions()
    }, [attractionStatus])

    useEffect(() => {
        const applyModeration = async () => {
            try {
                await axios.post(`http://localhost:8080/admin/apply-moderation/${attractionName}`, {},
                    {
                        headers: {
                            'Authorization': `Bearer ${JSON.parse(localStorage.getItem('user')).accessToken}`,
                        }
                    })
            } catch (error) {
                if (error.response && error.response.status === 401) {
                    window.location.href = "http://localhost:4000/signin"
                } else {
                    console.log(error.response?.status)
                }
            }
            window.location.reload()

            setAttractionName('')
        }

        if (attractionName) {
            applyModeration()
        }
    }, [attractionName])

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
                            style={onModerationPage ? {backgroundColor: '#007bff'} : {backgroundColor: 'red'}} // Published button color
                            onClick={() => {
                                setAttractionStatus('published');
                                setOnModerationPage(false);
                            }}
                        >
                            Published
                        </button>
                        <button
                            type="button"
                            className="btn btn-primary mt-3 admin-button"
                            style={onModerationPage ? {backgroundColor: 'red'} : {backgroundColor: '#007bff'}} // On Moderation button color
                            onClick={() => {
                                setAttractionStatus('on_moderation');
                                setOnModerationPage(true);
                            }}
                        >
                            On Moderation
                        </button>
                    </div>
                </div>
            </div>
            <Settings/>
        </div>
    )
}

export default AdminModeration

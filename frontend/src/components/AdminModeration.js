import React, { useEffect, useState } from 'react'
import axios from "axios"
import Settings from "./Settings"

const AdminModeration = () => {

    const [attractions, setAttractions] = useState([])
    const [attractionStatus, setAttractionStatus] = useState('on_moderation')
    const [attractionName, setAttractionName] = useState('')
    const [currentImageIndex, setCurrentImageIndex] = useState(0)

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

    const getAttractionCardStyle = (type) => {
        switch (type.toLowerCase()) {
            case 'museum':
                return {backgroundColor: 'yellow', color: 'black'}
            case 'gallery':
                return {backgroundColor: 'orange', color: 'black'}
            case 'park':
                return {backgroundColor: 'green', color: 'white'}
            case 'religious':
                return {backgroundColor: 'lightgray', color: 'black'}
            case 'cafe':
                return {backgroundColor: 'wheat', color: 'black'}
            case 'restaurant':
                return {backgroundColor: 'pink', color: 'black'}
            default:
                return {backgroundColor: 'lightgray', color: 'black'}
        }
    }

    const getImageFormat = (format) => {
        const formats = ['png', 'jpeg', 'jpg', 'webp', 'svg']
        if (formats.includes(format.toLowerCase())) {
            return format.toLowerCase()
        } else {
            return 'jpeg'
        }
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
                                                &lt
                                            </button>

                                            {/* Right Arrow Button */}
                                            <button
                                                className="image-nav-button right"
                                                onClick={() => setCurrentImageIndex(handleNextImage(currentImageIndex, images))}
                                            >
                                                &gt
                                            </button>
                                        </div>
                                    </div>
                                    <div className="attraction-data">
                                        <div className="attraction-type"
                                             style={getAttractionCardStyle(attraction.type)}>
                                            {attraction.type}
                                        </div>
                                        <div className="attraction-status">
                                            {attraction.status}
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
                        <button type="button" className="btn btn-primary mt-3 admin-button"
                                onClick={() => setAttractionStatus('published')}>
                            Published
                        </button>
                        <button type="button" className="btn btn-primary mt-3 admin-button"
                                onClick={() => setAttractionStatus('on_moderation')}>
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

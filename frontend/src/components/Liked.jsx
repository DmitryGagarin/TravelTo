import React, {useEffect, useState} from 'react'
import axios from "axios"
import Header from "./Header"
import {Link} from "react-router-dom"
import {getImageFormat} from "../utils/ImageUtils"
import {FaHeart, FaHeartBroken} from "react-icons/fa";

function Liked() {
    const BACKEND = process.env.REACT_APP_BACKEND_URL
    const TOKEN = JSON.parse(localStorage.getItem('user')).accessToken

    const [likes, setLikes] = useState([])

    const [dislikeAttraction, setDislikeAttraction] = useState(null)
    const [hoveringLikeButton, setHoveringLikeButton] = useState(false)

    const [error, setError] = useState('')
    const [currentImageIndexes, setCurrentImageIndexes] = useState({})

    // TODO: TypeError: Cannot read properties of undefined (reading 'status')
    useEffect(() => {
        const fetchLikes = async () => {
            try {
                const response = await axios.get("http://localhost:8080/like", {
                    headers: {
                        'Authorization': `Bearer ${TOKEN}`
                    }
                })
                if (response) {
                    setLikes(response.data._embedded.attractionModelList)
                } else {
                    setLikes([])
                }
            } catch (error) {
                if (error.response && error.response.data) {
                    const errorMessages = error.response.data
                    setError(
                        Object.entries(errorMessages)
                            .map(([field, message]) => `${field}: ${message}`)
                            .join(', ')
                    )
                } else {
                    if (error.response.status === 401) {
                        window.location.href = "http://localhost:4000/signin"
                    }
                    setError('Business registration failed, please try again.')
                }
            }
        }
        fetchLikes()
    }, [])

    useEffect(() => {
        if (dislikeAttraction) {
            const deleteLike = async (name) => {
                try {
                    await axios.post(`${BACKEND}/like/delete/${name}`, {},
                        {
                            headers: {
                                'Authorization' : `Bearer ${TOKEN}`
                            }
                        })
                } catch (error) {
                    alert("impossible to delete like")
                }
            }
            deleteLike(dislikeAttraction)
        }
    }, [dislikeAttraction])

    if (!likes) {
        return (
            <div>
                <Header/>
                <div>
                    Nothing liked
                </div>
            </div>
        )
    }

    const handleNextImage = (index, images, attractionName) => {
        const newIndex = (index + 1) % images.length
        setCurrentImageIndexes((prev) => ({
            ...prev,
            [attractionName]: newIndex,
        }))
    }

    const handlePrevImage = (index, images, attractionName) => {
        const newIndex = (index - 1 + images.length) % images.length
        setCurrentImageIndexes((prev) => ({
            ...prev,
            [attractionName]: newIndex,
        }))
    }

    // TODO: add image slider
    return (
        <div>
            <Header/>
            <div className="attractions-main-container">
                <div className="attractions-container">
                    <div className="cards-container">
                        {likes.map((attraction, index) => {
                            const currentImageIndex = currentImageIndexes[attraction.name] || 0
                            return (
                                <div key={attraction.name || index} className="attraction-card">
                                    <div className="image-container">
                                        <img
                                            src={`data:image/${getImageFormat(attraction.imagesFormats[currentImageIndex])};base64,${attraction.images[currentImageIndex]}`}
                                            alt={attraction.name}
                                            className="card-image"
                                        />
                                        <div className="image-navigation">
                                            <button
                                                className="image-nav-button left"
                                                onClick={() =>
                                                    handlePrevImage(currentImageIndex, attraction.images, attraction.name)
                                                }
                                            >
                                                ←
                                            </button>

                                            <button
                                                className="image-nav-button right"
                                                onClick={() =>
                                                    handleNextImage(currentImageIndex, attraction.images, attraction.name)
                                                }
                                            >
                                                →
                                            </button>
                                        </div>
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
                                    <div className="like">
                                        {hoveringLikeButton ? (
                                            <FaHeartBroken
                                                className="like-button-heart liked hover-break"
                                                style={{background : "red", color : "black"}}
                                                size={36}
                                                onClick={() => setDislikeAttraction(attraction.name)}
                                                title="You have liked this attraction"
                                                onMouseEnter={() => setHoveringLikeButton(true)}
                                                onMouseLeave={() => setHoveringLikeButton(false)}
                                            />
                                        ) : (
                                            <FaHeart
                                                className="like-button-heart liked"
                                                size={36}
                                                onClick={() => setDislikeAttraction(attraction.name)}
                                                title="You have liked this attraction"
                                                onMouseEnter={() => setHoveringLikeButton(true)}
                                                onMouseLeave={() => setHoveringLikeButton(false)}
                                            />
                                            )
                                        }
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
                                    <div className="opening-time" style={{alignItems: "left"}}>
                                        <p>Opening Time: {attraction.openTime}</p>
                                        <p>Closing Time: {attraction.closeTime}</p>
                                    </div>
                                </div>
                            )
                        })}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Liked
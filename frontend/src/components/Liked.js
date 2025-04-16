import React, {useEffect, useState} from 'react'
import axios from "axios"
import Header from "./Header"
import {Link} from "react-router-dom"
import {getImageFormat} from "../utils/ImageUtils"

function Liked() {
    const [likes, setLikes] = useState([])
    const [error, setError] = useState('')

    // TODO: TypeError: Cannot read properties of undefined (reading 'status')
    useEffect(() => {
    const token = JSON.parse(localStorage.getItem('user')).accessToken
        const fetchLikes = async () => {
            try {
                const response = await axios.get("http://localhost:8080/like", {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                })
                setLikes(response.data._embedded.likesModelList || [])
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

    console.log(likes)

    // TODO: add image slider
    return (
        <div>
            <Header/>
            <div className="attractions-main-container">
                <div className="attractions-container">
                    <div className="cards-container">
                        {likes.map((like, index) => (
                            like.attraction.map((attraction) => (
                                <div key={attraction.name || index} className="attraction-card">
                                    <div className="image-container">
                                        <img
                                            src={`data:image/${getImageFormat(attraction.imagesFormat[0])};base64,${attraction.images[0]}`}
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
                                </div>
                            ))
                        ))}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Liked
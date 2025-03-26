import React, {useEffect, useState} from 'react'
import axios from "axios";
import Header from "./Header";
import {Link} from "react-router-dom";

function Liked() {
    const [likes, setLikes] = useState([])

    useEffect(() => {
        const token = JSON.parse(localStorage.getItem('user')).data.accessToken
        try {
            const fetchLikes = async () => {
                const response = await axios.get("http://localhost:8080/like", {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                })
                console.log(response)
                setLikes(response.data._embedded.likesModelList || [])
            }
        } catch (error) {
            if (error.response.status === 401) {
                window.location.href = "http://localhost:3000/"; // Manually redirect to login
            }
        }
    }, []);

    if (likes == null) {
        return (
            <div>
                <Header/>
                <div>
                    Nothing liked
                </div>
            </div>
        )
    }
    return (
        <div>
            <Header/>
            <div className="attractions-main-container">
                <div className="attractions-container">
                    <div className="cards-container">
                        {likes.map((attraction) => (
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
                        ))}
                    </div>

                </div>
            </div>
        </div>
    )
}

export default Liked
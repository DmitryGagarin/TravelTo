import React, {useEffect, useState} from "react";
import axios from "axios";
import Header from "./Header";
import {Link} from "react-router-dom";

function MyAttractions() {
    const [attractions, setAttractions] = useState([])

    useEffect(() => {
        const fetchAttractions = async () => {
            try {
                const token = JSON.parse(localStorage.getItem('user'))?.token
                const response = await axios.get("http://localhost:8080/attraction/my", {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    },
                })
                setAttractions(response.data._embedded.attractionModelList)
            } catch (err) {}
        }
        fetchAttractions()
    }, [])

    return (
        <div>
            <Header />
            <div className="attractions-main-container">
                <div className="attractions-container">
                    <div className="cards-container">
                        {attractions.map((attraction) => (
                            <div key={attraction.id} className="attraction-card">
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
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default MyAttractions
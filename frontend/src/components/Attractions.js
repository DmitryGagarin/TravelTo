import React, {useEffect, useState} from 'react'
import axios from "axios"
import {Link} from 'react-router-dom'
import Header from './Header'
import {MDBInput} from "mdb-react-ui-kit"
import {FaHeart} from "react-icons/fa"

function Attractions() {
    const [attractions, setAttractions] = useState([])

    const [types, setTypes] = useState([])
    const [selectedTypes, setSelectedTypes] = useState([])

    const [likedAttraction, setLikedAttraction] = useState(null)
    const [currentImageIndex, setCurrentImageIndex] = useState(1)

    const token = JSON.parse(localStorage.getItem('user')).accessToken

    useEffect(() => {
        const fetchAttractions = async () => {
            try {
                const response = await axios.get("http://localhost:8080/attraction", {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    },
                })
                const publishedAttractions = response.data._embedded.attractionModelList.filter(attraction => attraction.status === 'published');
                setAttractions(publishedAttractions)
                const attractionTypes = response.data._embedded.attractionModelList.map(item => item.type)
                setTypes([...new Set(attractionTypes)])
            } catch (error) {
                if (error.response.status === 401) {
                    window.location.href = "http://localhost:3000/";
                }
                console.error(error)
            }
        }
        fetchAttractions()
    }, [token])

    useEffect(() => {
        if (likedAttraction) {
            const handleLike = async (name) => {
                try {
                    await axios.post(`http://localhost:8080/like/add/${name}`, {}, {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${token}`,
                        },
                    })
                } catch (error) {
                    if (error.response.status === 401) {
                        window.location.href = "http://localhost:3000/";
                    }
                    console.error('Error liking attraction:', error)
                }
            }

            handleLike(likedAttraction)
        }
    }, [likedAttraction])

    const handleTypeChange = (event) => {
        const type = event.target.value
        setSelectedTypes(prevSelectedTypes =>
            prevSelectedTypes.includes(type)
                ? prevSelectedTypes.filter(item => item !== type)
                : [...prevSelectedTypes, type]
        )
    }

    const handleSearch = () => {
        const searchText = document.getElementById('filterInput').value.toLowerCase()
        const filteredAttractions = attractions.filter(attraction =>
            attraction.name.toLowerCase().includes(searchText)
        )
        setAttractions(filteredAttractions)
    }

    const filteredAttractions = attractions.filter(attraction =>
        selectedTypes.length === 0 ||
        selectedTypes.includes(attraction.type)
    )

    // TODO: смена картинок распространяется на все карточки на странице
    const handleNextImage = (index, images) => {
        return (index + 1) % images.length
    }

    const handlePrevImage = (index, images) => {
        return (index - 1 + images.length) % images.length
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

    return (
        <div>
            <Header/>
            <div className="attractions-main-container">
                <div className="attractions-container">
                    <div className="cards-container">
                        {filteredAttractions.map((attraction) => {
                            return (
                                <div key={attraction.name} className="attraction-card">
                                    <div className="image-container">
                                        <img
                                            src={`data:image/png;base64,${attraction.images[currentImageIndex]}`}
                                            alt={attraction.name}
                                            className="card-image"
                                        />
                                        <div className="image-navigation">
                                            {/* Left Arrow Button */}
                                            <button
                                                className="image-nav-button left"
                                                onClick={() => setCurrentImageIndex(handlePrevImage(currentImageIndex, attraction.images))}
                                            >
                                                ←
                                            </button>

                                            {/* Right Arrow Button */}
                                            <button
                                                className="image-nav-button right"
                                                onClick={() => setCurrentImageIndex(handleNextImage(currentImageIndex, attraction.images))}
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
                                        <div className="like">
                                            <FaHeart onClick={() => setLikedAttraction(attraction.name)}/>
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
                                        <div className="name-description">
                                            <h5>{attraction.name}</h5>
                                            <p>{attraction.description}</p>
                                        </div>
                                        <div className="learn-more">
                                            <button className="learn-more-button">
                                                <Link to={`/attraction/${attraction.name}`}>Learn More</Link>
                                            </button>
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

                {/* Filter Container */}
                <div className="filter-container">
                    <h5>Filter</h5>
                    <div className="checkboxes">
                        {types.map((type) => (
                            <div key={type} className="checkbox-container">
                                <label>{type}</label>
                                <input
                                    type="checkbox"
                                    value={type}
                                    checked={selectedTypes.includes(type)}
                                    onChange={handleTypeChange}
                                />
                            </div>
                        ))}
                    </div>
                    {/* Search Bar Container */}
                    <MDBInput
                        type="text"
                        id="filterInput"
                        onKeyUp={handleSearch}
                        placeholder="Search"
                    />
                </div>
            </div>
        </div>
    )
}

export default Attractions

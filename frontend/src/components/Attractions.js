import React, {useEffect, useState} from 'react'
import axios from 'axios'
import {Link} from 'react-router-dom'
import Header from './Header'
import {MDBInput} from 'mdb-react-ui-kit'
import {FaHeart} from 'react-icons/fa'

function Attractions() {
    const [attractions, setAttractions] = useState([])
    const [types, setTypes] = useState([])

    const [likedAttraction, setLikedAttraction] = useState(null)

    const [selectedTypes, setSelectedTypes] = useState([])
    const [searchQuery, setSearchQuery] = useState('')

    const [currentImageIndexes, setCurrentImageIndexes] = useState({})

    const token = JSON.parse(localStorage.getItem('user')).accessToken

    useEffect(() => {
        const fetchAttractions = async () => {
            try {
                const response = await axios.get('http://localhost:8080/attraction', {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    },
                })
                const publishedAttractions = response?.data?._embedded?.attractionModelList.filter(
                    (attraction) => attraction.status === 'published'
                )
                setAttractions(publishedAttractions)
                const attractionTypes = publishedAttractions.map((attraction) => attraction.type)
                setTypes([...new Set(attractionTypes)])
            } catch (error) {
                if (error.response.status === 401) {
                    window.location.href = 'http://localhost:3000/signin'
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
                        window.location.href = 'http://localhost:3000/signin'
                    }
                    console.error('Error liking attraction:', error)
                }
            }

            handleLike(likedAttraction)
        }
    }, [likedAttraction])

    const handleSearch = (event) => {
        setSearchQuery(event.target.value)
    }

    const filteredAttractions = attractions.filter((attraction) => {
        const matchesSearch =
            attraction.name.toLowerCase().includes(searchQuery.toLowerCase())
        const matchesType =
            selectedTypes.length === 0 || selectedTypes.includes(attraction.type)

        return matchesSearch && matchesType
    })

    const handleTypeChange = (event) => {
        const type = event.target.value
        setSelectedTypes((prevSelectedTypes) =>
            prevSelectedTypes.includes(type)
                ? prevSelectedTypes.filter((item) => item !== type)
                : [...prevSelectedTypes, type]
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
        const formats = ['png', 'jpeg', 'jpg', 'webp', 'svg'];
        if (formats.includes(format.toLowerCase())) {
            return format.toLowerCase()
        } else {
            return 'jpeg'
        }
    }

    return (
        <div>
            <Header/>
            <div className="attractions-main-container">
                <div className="attractions-container">
                    <div className="cards-container">
                        {(filteredAttractions.length === 0 || !filteredAttractions) && (
                            <div>
                                <h2>Nothing found</h2>
                            </div>
                        )}
                        {filteredAttractions.map((attraction) => {
                            const currentImageIndex = currentImageIndexes[attraction.name] || 0
                            return (
                                <div key={attraction.name} className="attraction-card">
                                    <div className="image-container">
                                        <img
                                            src={`data:image/${getImageFormat(attraction.imagesFormats[currentImageIndex])};base64,${attraction.images[currentImageIndex]}`}
                                            alt={attraction.name}
                                            className="card-image"
                                        />
                                        <div className="image-navigation">
                                            {/* Left Arrow Button */}
                                            <button
                                                className="image-nav-button left"
                                                onClick={() =>
                                                    handlePrevImage(currentImageIndex, attraction.images, attraction.name)
                                                }
                                            >
                                                ←
                                            </button>

                                            {/* Right Arrow Button */}
                                            <button
                                                className="image-nav-button right"
                                                onClick={() =>
                                                    handleNextImage(currentImageIndex, attraction.images, attraction.name)
                                                }
                                            >
                                                →
                                            </button>
                                        </div>
                                    </div>
                                    <div className="attraction-data">
                                        <div
                                            className="attraction-type"
                                            style={getAttractionCardStyle(attraction.type)}
                                        >
                                            {attraction.type}
                                        </div>
                                        <div className="like">
                                            <FaHeart onClick={() => setLikedAttraction(attraction.name)}/>
                                        </div>
                                        <div className="rating">Rating: {attraction.rating}</div>
                                        <div className="contact-info">
                                            <p>
                                                Website:{" "}
                                                <a href={attraction.website} target="_blank" rel="noopener noreferrer">
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
                    <MDBInput
                        type="text"
                        id="filterInput"
                        value={searchQuery}
                        onChange={handleSearch}
                        placeholder="Search"
                    />
                </div>
            </div>
        </div>
    )
}

export default Attractions

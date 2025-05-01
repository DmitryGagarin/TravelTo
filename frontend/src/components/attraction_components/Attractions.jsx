import React, {useEffect, useState} from 'react'
import axios from 'axios'
import {Link} from 'react-router-dom'
import Header from '../Header'
import {MDBInput} from 'mdb-react-ui-kit'
import {FaHeart} from 'react-icons/fa'
import {getAttractionCardStyle, renderStars} from "../../utils/StyleUtils"
import {getImageFormat} from "../../utils/ImageUtils"

function Attractions() {
    const BACKEND = process.env.REACT_APP_BACKEND_URL
    const FRONTEND = process.env.REACT_APP_FRONTEND_URL

    const [attractions, setAttractions] = useState([])
    const [types, setTypes] = useState([])
    const [likedAttraction, setLikedAttraction] = useState(null)

    const [selectedTypes, setSelectedTypes] = useState([])
    const [searchQuery, setSearchQuery] = useState('')

    const [currentImageIndexes, setCurrentImageIndexes] = useState({})

    const token = JSON.parse(localStorage.getItem('user'))?.accessToken

    useEffect(() => {
        const fetchAttractions = async () => {
            try {
                const response = await axios.get(`${BACKEND}/attraction/published`)
                const attractionsData = response?.data?._embedded?.attractionModelList
                if (Array.isArray(attractionsData)) {
                    setAttractions(attractionsData)
                    const attractionTypes = attractionsData.map((attraction) => attraction.type)
                    setTypes([...new Set(attractionTypes)])
                } else {
                    setAttractions([])
                    setTypes([])
                }
            } catch (error) {
                console.error(error)
            }
        }
        fetchAttractions()
    }, [token])

    useEffect(() => {
        if (likedAttraction) {
            const handleLike = async (name) => {
                try {
                    await axios.post(`${BACKEND}/like/add/${name}`, {}, {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${token}`
                        },
                    })
                } catch (error) {
                    if (error.response.status === 401) {
                        window.location.href = `${FRONTEND}/signin`
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
        const matchesSearch = attraction.name.toLowerCase().includes(searchQuery.toLowerCase())
        const matchesType = selectedTypes.length === 0 || selectedTypes.includes(attraction.type)

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
                                <div key={attraction.name} className="attraction-card attractions-attraction-card attractions">
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
                                    </div>
                                    {/*<div className="attraction-data">*/}
                                        <div
                                            className="attraction-type"
                                            style={getAttractionCardStyle(attraction.type)}
                                        >
                                            {attraction.type}
                                        </div>
                                        <div className="like">
                                            <FaHeart color="pink" size={36} onClick={() => setLikedAttraction(attraction.name)}/>
                                        </div>
                                        <div className="contact-info">
                                            <p>
                                                Website:{" "}
                                                <Link to={attraction.website} target="_blank" rel="noopener noreferrer">
                                                    {attraction.website}
                                                </Link>
                                            </p>
                                            <p>Phone: {attraction.phone}</p>
                                        </div>
                                    <div className="rating">{renderStars(attraction.rating)}</div>
                                    <div className="name-description">
                                            <h5>{attraction.name}</h5>
                                            <p>{attraction.description}</p>
                                        </div>
                                        <div className="learn-more">
                                            <button className="learn-more-button">
                                                <Link to={`/attraction/${attraction.name}`}>Visit</Link>
                                            </button>
                                        </div>
                                        <div className="time">
                                            <p>From: {attraction.openTime}</p>
                                            <p>To: {attraction.closeTime}</p>
                                        </div>
                                    {/*</div>*/}
                                </div>
                            )
                        })}
                    </div>
                </div>

                {/* Filter Container */}
                {filteredAttractions.length !== 0 && (
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
                )}
            </div>
        </div>
    )
}

export default Attractions

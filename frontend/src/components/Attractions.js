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
    const [likedAttraction, setLikedAttraction] = useState(null) // State to store the attraction to be liked
    const [currentImageIndex, setCurrentImageIndex] = useState(0); // Initialize the current image index state
    const token = JSON.parse(localStorage.getItem('user'))?.token

    // Fetch the attractions and types
    useEffect(() => {
        const fetchAttractions = async () => {
            try {
                const response = await axios.get("http://localhost:8080/attraction", {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    },
                })
                setAttractions(response.data._embedded.attractionModelList)

                const attractionTypes = response.data._embedded.attractionModelList.map(item => item.type)
                setTypes([...new Set(attractionTypes)])
            } catch (err) {
                console.error(err)
            }
        }
        fetchAttractions()
    }, [token])

    // Trigger the like API call when likedAttraction changes
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
                } catch (err) {
                    console.error('Error liking attraction:', err)
                }
            }

            // Call handleLike when likedAttraction changes
            handleLike(likedAttraction)
        }
    }, [likedAttraction]) // Only run when likedAttraction changes

    // Handle the change in selected types
    const handleTypeChange = (event) => {
        const type = event.target.value
        setSelectedTypes(prevSelectedTypes =>
            prevSelectedTypes.includes(type)
                ? prevSelectedTypes.filter(item => item !== type)
                : [...prevSelectedTypes, type]
        )
    }

    // Handle the search functionality
    const handleSearch = () => {
        const searchText = document.getElementById('filterInput').value.toLowerCase()
        const filteredAttractions = attractions.filter(attraction =>
            attraction.name.toLowerCase().includes(searchText)
        )
        setAttractions(filteredAttractions)
    }

    // Filter attractions based on selected types
    const filteredAttractions = attractions.filter(attraction =>
        selectedTypes.length === 0 || selectedTypes.includes(attraction.type)
    )

    //TODO: смена картинок распространяется на все карточки на странице
    // Handle image navigation
    const handleNextImage = (index, images) => {
        return (index + 1) % images.length; // Wrap around the image array
    };

    const handlePrevImage = (index, images) => {
        return (index - 1 + images.length) % images.length; // Wrap around the image array
    };

    const getAttractionCardStyle = (type) => {
        switch (type.toLowerCase()) {
            case 'museum':
                return { backgroundColor: 'yellow', color: 'black' };
            case 'gallery':
                return { backgroundColor: 'orange', color: 'black' };
            case 'park':
                return { backgroundColor: 'green', color: 'white' };
            case 'religious':
                return { backgroundColor: 'lightgray', color: 'black' };
            case 'cafe':
                return { backgroundColor: 'wheat', color: 'black' };
            case 'restaurant':
                return { backgroundColor: 'pink', color: 'black' };
            default:
                return { backgroundColor: 'lightgray', color: 'black' };
        }
    };

    return (
        <div>
            <Header/>
            <div className="attractions-main-container">
                <div className="attractions-container">
                    <div className="cards-container">
                        {filteredAttractions.map((attraction) => {
                            return (
                                <div key={attraction.id} className="attraction-card">
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
                                                &lt;
                                            </button>

                                            {/* Right Arrow Button */}
                                            <button
                                                className="image-nav-button right"
                                                onClick={() => setCurrentImageIndex(handleNextImage(currentImageIndex, attraction.images))}
                                            >
                                                &gt;
                                            </button>
                                        </div>
                                    </div>
                                    <div className="attraction-data">
                                        <div className="attraction-type" style={getAttractionCardStyle(attraction.type)}>
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
                            );
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
    );
}

export default Attractions;

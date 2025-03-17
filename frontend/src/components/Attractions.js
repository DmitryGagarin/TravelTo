import React, { useEffect, useState } from 'react'
import axios from "axios"
import { Link } from 'react-router-dom'
import Header from './Header'
import { MDBInput } from "mdb-react-ui-kit"

function Attractions() {
    const [attractions, setAttractions] = useState([])
    const [types, setTypes] = useState([])
    const [selectedTypes, setSelectedTypes] = useState([])

    useEffect(() => {
        const fetchAttractions = async () => {
            try {
                const token = JSON.parse(localStorage.getItem('user'))?.token
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
        fetchAttractions().then(r => console.log(r))
    }, [])

    // Handle the change in selected types
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

    // Filter attractions based on selected types
    const filteredAttractions = attractions.filter(attraction =>
        selectedTypes.length === 0 || selectedTypes.includes(attraction.type)
    )

    return (
        <div>
            <Header />
            <div className="attractions-main-container">
                <div className="attractions-container">
                    <div className="cards-container">
                        {filteredAttractions.map((attraction) => (
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

                {/* Filter Container */}
                <div className="filter-container">
                    <h5>Filter by Type</h5>
                    <div className="checkboxes">
                        {types.map((type) => (
                            <div key={type} className="checkbox-container">
                                <input
                                    type="checkbox"
                                    value={type}
                                    checked={selectedTypes.includes(type)}
                                    onChange={handleTypeChange}
                                />
                                <label>{type}</label>
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

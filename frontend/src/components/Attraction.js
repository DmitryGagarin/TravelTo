import React, {useEffect, useState} from 'react'
import {YMaps, Map, Placemark} from '@pbe/react-yandex-maps'
import axios from 'axios'
import Header from "./Header"
import {useParams} from "react-router-dom"
import {MDBInput, MDBTextArea} from "mdb-react-ui-kit"

function Attraction() {
    const API_KEY = process.env.REACT_APP_YANDEX_MAP_API_KEY
    const domain = "https://geocode-maps.yandex.ru/v1/"

    const {name} = useParams()

    const [error, setError] = useState('')
    const [loading, setLoading] = useState(true) // Loading state to track fetching status

    const [attraction, setAttraction] = useState('')
    const [discussions, setDiscussions] = useState([])
    const [attractionUuid, setAttractionUuid] = useState('')

    const [showCreateDiscussionForm, setShowCreateDiscussionForm] = useState(false)
    const [currentAttractionImageIndex, setCurrentAttractionImageIndex] = useState(0) // Initialize the current image index state
    const [currentDiscussionImageIndex, setCurrentDiscussionImageIndex] = useState(0)

    const [title, setTitle] = useState('')
    const [contentLike, setContentLike] = useState('')
    const [contentDislike, setContentDislike] = useState('')
    const [content, setContent] = useState('')
    const [images, setImages] = useState([])
    const [rating, setRating] = useState('')

    const [balloon, setBalloon] = useState('')
    const [latitude, setLatitude] = useState('')
    const [longitude, setLongitude] = useState('')

    useEffect(() => {
        const fetchAttraction = async () => {
            try {
                const token = JSON.parse(localStorage.getItem('user')).data.accessToken
                const response =
                    await axios.get(`http://localhost:8080/attraction/${name}`, {
                        headers: {
                            'Authorization': `Bearer ${token}`,
                        },
                    })
                setAttraction(response.data)
                setAttractionUuid(response.data.uuid)
            } catch (error) {
                if (error.response.status === 401) {
                    window.location.href = "http://localhost:3000/"; // Manually redirect to login
                }
                setError('Failed to fetch attraction data')
            }
        }

        const fetchDiscussions = async () => {
            try {
                const token = JSON.parse(localStorage.getItem('user')).data.accessToken
                const response =
                    await axios.get(`http://localhost:8080/attraction-discussion/${attractionUuid}`, {
                        headers: {
                            'Authorization': `Bearer ${token}`
                        }
                    })
                setDiscussions(response.data._embedded.attractionDiscussionModelList)
            } catch (error) {
                if (error.response.status === 401) {
                    window.location.href = "http://localhost:3000/"; // Manually redirect to login
                }
            }
        }

        console.log(discussions)

        // const fetchAddress = async() => {
        //     let coords = []
        //     try {
        //         coords = await axios.get(
        //         `${domain}?apikey=${API_KEY}&geocode=Москва, улица Амурская, 1Ак1&format=json`
        //         )
        //     }
        //     catch (err) {}
        //     setBalloon(coords.data.response.GeoObjectCollection.featureMember[0].GeoObject.Point.pos)
        // }

        fetchAttraction()
        fetchDiscussions()
        // fetchAddress()
    }, [attractionUuid, name])

    useEffect(() => {
        if (balloon) {
            const lon = balloon.substring(0, balloon.indexOf(' '))
            const lat = balloon.substring(balloon.indexOf(' ') + 1)
            setLatitude(lat)
            setLongitude(lon)
        }
    }, [balloon]) // This effect runs whenever 'balloon' changes

    useEffect(() => {
        // Check if attraction is properly fetched and images is available
        if (attraction && Array.isArray(attraction.images) && attraction.images.length > 0) {
            setCurrentAttractionImageIndex(0) // Set to the first image initially
        }
    }, [attraction])  // Trigger this effect when attraction is updated

    const handleLeaveDiscussion = () => {
        if (showCreateDiscussionForm) {
            setShowCreateDiscussionForm(false)
        } else {
            setShowCreateDiscussionForm(true)
        }
    }

    const handleSendDiscussion = async (e) => {
        e.preventDefault()

        try {
            const formData = new FormData()

            const formJson = {
                title,
                contentLike,
                contentDislike,
                content,
                rating
            }
            formData.append('attractionDiscussionCreateForm',
                new Blob(
                    [JSON.stringify(formJson)],
                    {type: 'application/json'}
                )
            )

            if (images.length > 0) {
                images.forEach((image, index) => {
                    formData.append('images', image) // Each file gets appended as 'images' with a unique key
                })
            }

            await axios.post(
                `http://localhost:8080/attraction-discussion/create/${attractionUuid}`,
                formData,
                {
                    headers: {
                        'Authorization': `Bearer ${JSON.parse(localStorage.getItem('user')).token}`,
                        'Content-Type': 'multipart/form-data'
                    }
                }
            )
            window.location.reload()
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
                    window.location.href = "http://localhost:3000/"; // Manually redirect to login
                }
                setError('Discussion registration failed, please try again.')
            }
        }
    }

    const handleNextImage = (index, images) => {
        return (index + 1) % images.length // Wrap around the image array
    }

    const handlePrevImage = (index, images) => {
        return (index - 1 + images.length) % images.length // Wrap around the image array
    }

    const handleImageChange = (e) => {
        setImages([...e.target.files]) // Set the file object
    }

    // If loading, show loading message or spinner
    if (loading) {
        return <div>Loading...</div>
    }

    if (!attraction) {
        return <div>No attraction data available</div> // If attraction data is null, show an error message
    }

    return (
        <div>
            <Header/>
            <div className="attraction-main-container">
                <div className="attraction-container">
                    <div className="cards-container">
                        <div className="attraction-card">
                            <div className="image-container">
                                <img
                                    // TODO: реакт почему то не видит стейте в этом скоупе
                                    src={`data:image/pngbase64,${attraction.images[currentAttractionImageIndex]}`}
                                    alt={attraction.name}
                                    className="card-image"
                                />
                                <div className="image-navigation">
                                    <button
                                        className="image-nav-button left"
                                        onClick={() => setCurrentAttractionImageIndex(
                                            handlePrevImage(currentAttractionImageIndex, attraction.images)
                                        )}
                                        disabled={attraction.images.length <= 1}
                                    >
                                        &lt
                                    </button>

                                    <button
                                        className="image-nav-button right"
                                        onClick={() => setCurrentAttractionImageIndex(
                                            handleNextImage(currentAttractionImageIndex, attraction.images)
                                        )}
                                        disabled={attraction.images.length <= 1}
                                    >
                                        &gt
                                    </button>
                                </div>
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
                            <div className="opening-time">
                                <p>Opening Time: {attraction.openTime}</p>
                                <p>Closing Time: {attraction.closeTime}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            {/*<YMaps>*/
            }
            {/*    <div className="map">*/
            }
            {/*        <Map defaultState={{ center: [latitude, longitude], zoom: 15}} width="93%" height="40vh">*/
            }
            {/*            <Placemark geometry={[latitude, longitude]}/>*/
            }
            {/*        </Map>*/
            }
            {/*    </div>*/
            }
            {/*</YMaps>*/
            }
            <div className="discussion-main-container">
                <div className="leave-discussion-container">
                    <button className="leave-discussion" onClick={handleLeaveDiscussion}>
                        Were here? Leave your comment!
                    </button>
                </div>
                {error && <p className="text-danger">{error}</p>}
                {/* Pop-up Window */}
                {showCreateDiscussionForm && (
                    <div className="popup">
                        <div className="popup-content">
                            <h2>Leave Comment</h2>
                            <form onSubmit={handleSendDiscussion}>
                                <MDBInput
                                    placeholder="Title"
                                    type="text"
                                    value={title}
                                    onChange={(e) => setTitle(e.target.value)}
                                    required
                                    style={{marginBottom: '10px'}}
                                />
                                <MDBInput
                                    placeholder="What you liked?"
                                    type="text"
                                    value={contentLike}
                                    onChange={(e) => setContentLike(e.target.value)}
                                    required
                                    style={{marginBottom: '10px'}}
                                />
                                <MDBInput
                                    placeholder="What you disliked?"
                                    type="text"
                                    value={contentDislike}
                                    onChange={(e) => setContentDislike(e.target.value)}
                                    required
                                    style={{marginBottom: '10px'}}
                                />
                                <MDBTextArea
                                    placeholder="Overall"
                                    value={content}
                                    onChange={(e) => setContent(e.target.value)}
                                    required
                                    style={{marginBottom: '10px'}}
                                />
                                <MDBInput
                                    wrapperClass='mb-4'
                                    placeholder='Choose images'
                                    id='images'
                                    type='file'
                                    multiple
                                    onChange={handleImageChange}
                                />
                                <MDBInput
                                    placeholder="Mark"
                                    type="number"
                                    min="1"
                                    max="5"
                                    value={rating}
                                    onChange={(e) => setRating(e.target.value)}
                                    required
                                    style={{marginBottom: '10px'}}
                                />
                                <button type="submit">Submit Comment</button>
                            </form>
                        </div>
                    </div>
                )}
                <div className="discussions-main-container">
                    <div className="discussion-container">
                        {discussions.map((discussion) => (
                            <div key={discussion.id} className="discussion-card">
                                <div className="discussion-author discussion-part">
                                    Author: {discussion.author}
                                </div>
                                <div className="discussion-title discussion-part">
                                    Title: {discussion.title}
                                </div>
                                <div className="discussion-like discussion-part">
                                    Liked: {discussion.contentLike}
                                </div>
                                <div className="discussion-dislike discussion-part">
                                    Disliked: {discussion.contentDislike}
                                </div>
                                <div className="discussion-content discussion-part">
                                    Review: {discussion.content}
                                </div>
                                <div className="discussion-ration discussion-part">
                                    Overall: {discussion.rating}
                                </div>
                                <img
                                    // TODO: реакт почему то не видит стейте в этом скоупе
                                    src={`data:image/pngbase64,${discussion.images[currentDiscussionImageIndex]}`}
                                    alt={attraction.name}
                                    className="card-image"
                                />
                                <div className="image-navigation">
                                    <button
                                        className="image-nav-button left"
                                        onClick={() => setCurrentAttractionImageIndex(
                                            handlePrevImage(currentDiscussionImageIndex, discussion.images)
                                        )}
                                        disabled={discussion.images.length <= 1}
                                    >
                                        &lt
                                    </button>
                                    <button
                                        className="image-nav-button right"
                                        onClick={() => setCurrentAttractionImageIndex(
                                            handleNextImage(currentDiscussionImageIndex, discussion.images)
                                        )}
                                        disabled={discussion.images.length <= 1}
                                    >
                                        &gt
                                    </button>
                                </div>
                                <div className="discussion-created_at discussion-part">
                                    Created at: {discussion.createdAt}
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Attraction
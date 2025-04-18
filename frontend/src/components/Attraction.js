import React, {useEffect, useState} from 'react'
import axios from 'axios'
import Header from "./Header"
import {Link, useParams} from "react-router-dom"
import {MDBInput, MDBTextArea} from "mdb-react-ui-kit"
import {FaHeart} from "react-icons/fa"
import {getAttractionCardStyle, renderStars} from '../utils/StyleUtils.js'
import {getImageFormat, handleNextImage, handlePrevImage} from "../utils/ImageUtils"

function Attraction() {
    const BACKEND = process.env.REACT_APP_BACKEND_URL
    const FRONTEND = process.env.REACT_APP_FRONTEND_URL

    const YANDEX_MAP_API_KEY = process.env.REACT_APP_YANDEX_MAP_API_KEY
    const domain = "https://geocode-maps.yandex.ru/v1/"

    const {name} = useParams()

    const [error, setError] = useState('')

    const [attraction, setAttraction] = useState('')
    const [discussions, setDiscussions] = useState([])
    const [attractionUuid, setAttractionUuid] = useState('')

    const [showCreateDiscussionForm, setShowCreateDiscussionForm] = useState(false)
    const [currentAttractionImageIndex, setCurrentAttractionImageIndex] = useState(0)
    const [currentDiscussionImageIndex, setCurrentDiscussionImageIndex] = useState(0)
    const [currentImageIndexes, setCurrentImageIndexes] = useState({})

    const [likedAttraction, setLikedAttraction] = useState(null)

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
                const token = JSON.parse(localStorage.getItem('user')).accessToken
                const response =
                    await axios.get(`${BACKEND}/attraction/${name}`, {
                        headers: {
                            'Authorization': `Bearer ${token}`,
                        },
                    })
                setAttraction(response.data)
                setAttractionUuid(response.data.uuid)
            } catch (error) {
                if (error.response.status === 401) {
                    window.location.href = `${FRONTEND}/signin`
                }
                setError('Failed to fetch attraction data')
            }
        }

        console.log(attraction)

        const fetchDiscussions = async () => {
            try {
                const token = JSON.parse(localStorage.getItem('user')).accessToken
                const response =
                    await axios.get(`${BACKEND}/attraction-discussion/${attractionUuid}`, {
                        headers: {
                            'Authorization': `Bearer ${token}`
                        }
                    })
                setDiscussions(response.data._embedded.attractionDiscussionModelList)
            } catch (error) {
                console.log(error)
            }
        }

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
        if (attraction && Array.isArray(attraction.images) && attraction.images.length > 0) {
            setCurrentAttractionImageIndex(0)
        }
    }, [attraction])

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
                `${BACKEND}/attraction-discussion/create/${attractionUuid}`,
                formData,
                {
                    headers: {
                        'Authorization': `Bearer ${JSON.parse(localStorage.getItem('user')).accessToken}`,
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
                    window.location.href = `${FRONTEND}/signin`
                }
                setError('Discussion registration failed, please try again.')
            }
        }
    }

    const handleImageChange = (e) => {
        setImages([...e.target.files])
    }

    // TODO: add different type of images
    // TODO: arrow sliders broken
    return (
        <div>
            <Header />
            <div className="attractions-main-container">
                <div className="attractions-container">
                    <div className="cards-container">
                        {(!attraction || Object.keys(attraction).length === 0) && (
                            <div>
                                <h2>Nothing found</h2>
                            </div>
                        )}

                        {attraction && Object.keys(attraction).length > 0 && (
                            <div key={attraction.name} className="attraction-card">
                                <div className="image-container">
                                    <img
                                        src={`data:image/${getImageFormat(attraction.imagesFormats[currentImageIndexes[attraction.name] || 0])};base64,${attraction.images[currentImageIndexes[attraction.name] || 0]}`}
                                        alt={attraction.name}
                                        className="card-image"
                                    />
                                    <div className="image-navigation">
                                        <button
                                            className="image-nav-button left"
                                            onClick={() =>
                                                handlePrevImage(currentImageIndexes[attraction.name] || 0, attraction.images, attraction.name)
                                            }
                                        >
                                            ←
                                        </button>
                                        <button
                                            className="image-nav-button right"
                                            onClick={() =>
                                                handleNextImage(currentImageIndexes[attraction.name] || 0, attraction.images, attraction.name)
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
                                        <FaHeart color="pink" onClick={() => setLikedAttraction(attraction.name)} />
                                    </div>
                                    <div className="rating">{renderStars(attraction.rating)}</div>
                                    <div className="contact-info">
                                        <p>
                                            Website:{" "}
                                            <Link to={attraction.website} target="_blank" rel="noopener noreferrer">
                                                Visit
                                            </Link>
                                        </p>
                                        <p>Phone: {attraction.phone}</p>
                                    </div>
                                    <div className="name-description">
                                        <h5>{attraction.name}</h5>
                                        <p>{attraction.description}</p>
                                    </div>
                                    <div className="time">
                                        <p>From: {attraction.openTime}</p>
                                        <p>To: {attraction.closeTime}</p>
                                    </div>
                                </div>
                            </div>
                        )}
                    </div>
                </div>
            </div>

            {/* Comment Section */}
            <div className="discussion-main-container">
                <div className="leave-discussion-container">
                    <button className="leave-discussion" onClick={handleLeaveDiscussion}>
                        Were here? Leave your comment!
                    </button>
                </div>
                {error && <p className="text-danger">{error}</p>}

                {/* Pop-up Window for creating discussion */}
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
                                    style={{ marginBottom: '10px' }}
                                />
                                <MDBInput
                                    placeholder="What you liked?"
                                    type="text"
                                    value={contentLike}
                                    onChange={(e) => setContentLike(e.target.value)}
                                    required
                                    style={{ marginBottom: '10px' }}
                                />
                                <MDBInput
                                    placeholder="What you disliked?"
                                    type="text"
                                    value={contentDislike}
                                    onChange={(e) => setContentDislike(e.target.value)}
                                    required
                                    style={{ marginBottom: '10px' }}
                                />
                                <MDBTextArea
                                    placeholder="Overall"
                                    value={content}
                                    onChange={(e) => setContent(e.target.value)}
                                    required
                                    style={{ marginBottom: '10px' }}
                                />
                                <MDBInput
                                    wrapperClass="mb-4"
                                    placeholder="Choose images"
                                    id="images"
                                    type="file"
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
                                    style={{ marginBottom: '10px' }}
                                />
                                <button type="submit">Submit Comment</button>
                            </form>
                        </div>
                    </div>
                )}

                {/* Displaying Discussions */}
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
                                    Overall: {renderStars(discussion.rating)}
                                </div>
                                <img
                                    src={`data:image/png;base64,${discussion.images[currentDiscussionImageIndex]}`}
                                    alt={attraction.name}
                                    className="card-image"
                                />
                                <div className="image-navigation">
                                    <button
                                        className="image-nav-button left"
                                        onClick={() =>
                                            setCurrentAttractionImageIndex(
                                                handlePrevImage(currentDiscussionImageIndex, discussion.images)
                                            )
                                        }
                                        disabled={discussion.images.length <= 1}
                                    >
                                        ←
                                    </button>
                                    <button
                                        className="image-nav-button right"
                                        onClick={() =>
                                            setCurrentAttractionImageIndex(
                                                handleNextImage(currentDiscussionImageIndex, discussion.images)
                                            )
                                        }
                                        disabled={discussion.images.length <= 1}
                                    >
                                        →
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
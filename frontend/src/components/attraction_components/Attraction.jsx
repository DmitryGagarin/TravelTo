import React, {useEffect, useState} from 'react'
import axios from 'axios'
import Header from "../Header"
import {Link, useParams} from "react-router-dom"
import {MDBContainer, MDBInput, MDBTextArea} from "mdb-react-ui-kit"
import {FaHeart, FaHeartBroken} from "react-icons/fa"
import {getAttractionCardStyle, renderStars} from '../../utils/StyleUtils.js'
import {getImageFormat, handleNextImage, handlePrevImage} from "../../utils/ImageUtils"
import {catchError} from "../../utils/ErrorUtils"
import {Spinner} from 'react-bootstrap'

// TODO: нужна проверка на is_liked когда парсим
function Attraction() {
    const BACKEND = process.env.REACT_APP_BACKEND_URL
    const FRONTEND = process.env.REACT_APP_FRONTEND_URL

    const YANDEX_MAP_API_KEY = process.env.REACT_APP_YANDEX_MAP_API_KEY
    const YANDEX_MAP_API_DOMAIN = "https://geocode-maps.yandex.ru/v1/"

    const ACCESS_TOKEN = JSON.parse(localStorage.getItem('user'))?.accessToken

    const {name} = useParams()

    const [error, setError] = useState('')

    const [attraction, setAttraction] = useState('')
    const [discussions, setDiscussions] = useState([])
    const [feature, setFeature] = useState(null)

    const [attractionUuid, setAttractionUuid] = useState('')

    const [showCreateDiscussionForm, setShowCreateDiscussionForm] = useState(false)
    const [currentAttractionImageIndex, setCurrentAttractionImageIndex] = useState(0)
    const [currentDiscussionImageIndex, setCurrentDiscussionImageIndex] = useState(0)
    const [currentImageIndexes, setCurrentImageIndexes] = useState({})

    const [likedAttraction, setLikedAttraction] = useState(null)
    const [hoveringLiked, setHoveringLiked] = useState(false)
    const [dislikedAttraction, setDislikedAttraction] = useState(null)

    const [title, setTitle] = useState('')
    const [contentLike, setContentLike] = useState('')
    const [contentDislike, setContentDislike] = useState('')
    const [content, setContent] = useState('')
    const [images, setImages] = useState([])
    const [rating, setRating] = useState('')

    const [balloon, setBalloon] = useState('')
    const [latitude, setLatitude] = useState('')
    const [longitude, setLongitude] = useState('')

    const [isLoadingFeature, setIsLoadingFeature] = useState(false)

    useEffect(() => {
        const fetchAttraction = async () => {
            try {
                const response =
                    await axios.get(`${BACKEND}/attraction/${name}`, {
                        headers: {
                            'Authorization': `Bearer ${ACCESS_TOKEN}`,
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
        fetchAttraction()
    }, [])

    useEffect(() => {
        if (!attractionUuid) return
        const fetchDiscussions = async () => {
            try {
                const response = await axios.get(`${BACKEND}/attraction-discussion/${attractionUuid}`, {
                    headers: {
                        'Authorization': `Bearer ${ACCESS_TOKEN}`
                    }
                })
                const embedded = response.data._embedded
                if (embedded && embedded.attractionDiscussionModelList) {
                    setDiscussions(embedded.attractionDiscussionModelList)
                } else {
                    setDiscussions([])
                }
            } catch (error) {
                catchError(error, setError, FRONTEND, 'Impossible to fetch discussions')
            }
        }
        fetchDiscussions()
    }, [attractionUuid])

    // useEffect(() => {
    //     const fetchAddress = async () => {
    //         let coords = []
    //         try {
    //             coords = await axios.get(
    //                 `${domain}?apikey=${API_KEY}&geocode=Москва, улица Амурская, 1Ак1&format=json`
    //             )
    //         } catch (err) {
    //         }
    //         setBalloon(coords.data.response.GeoObjectCollection.featureMember[0].GeoObject.Point.pos)
    //     }
    // }, [attraction])

    useEffect(() => {
        if (attraction.type === 'cafe' || attraction.type === 'restaurant') {
            const fetchMenu = async () => {
                setIsLoadingFeature(true)
                try {
                    const textResponse = await axios.get(
                        `${BACKEND}/attraction-feature/${attraction.name}/get-text-menu`,
                        {
                            headers: {
                                'Authorization': `Bearer ${ACCESS_TOKEN}`
                            }
                        }
                    )
                    if (textResponse.data !== null) {
                        setFeature({type: 'text', data: textResponse.data})
                    } else {
                        const fileResponse = await axios.get(
                            `${BACKEND}/attraction-feature/${attraction.name}/get-file-menu`,
                            {
                                headers: {
                                    'Authorization': `Bearer ${ACCESS_TOKEN}`
                                }
                            }
                        )
                        setFeature({type: 'file', data: fileResponse.data})
                    }
                } catch (textError) {
                    catchError()
                } finally {
                    setIsLoadingFeature(false)
                }
            }
            fetchMenu()
        }
        if (attraction.type === 'park') {
            const fetchParkFacilities = async () => {
                try {
                    const response = await axios.get(`${BACKEND}/attraction-feature/${attraction.name}/get-park-facility`,
                        {
                            headers: {
                                'Authorization': `Bearer ${ACCESS_TOKEN}`
                            }
                        })
                    setFeature({type: 'park', data: response.data})
                } catch (error) {
                    catchError(error, setError, FRONTEND, 'impossible to fetch park facilities')
                }
            }
            fetchParkFacilities()
        }

        if (attraction.type === 'theater' || attraction.type === 'gallery' || attraction.type === 'museum') {
            const fetchPosters = async () => {
                try {
                    const response = await axios.get(`${BACKEND}/attraction-feature/${attraction.name}/get-posters`,
                        {
                            headers: {
                                'Authorization': `Bearer ${ACCESS_TOKEN}`
                            }
                        })
                    setFeature({type: 'poster', data: response.data})
                } catch (error) {
                    catchError(error, setError, FRONTEND, 'impossible to fetch posters')
                }
            }
            fetchPosters()
        }

    }, [attraction])

    useEffect(() => {
        if (balloon) {
            const lon = balloon.substring(0, balloon.indexOf(' '))
            const lat = balloon.substring(balloon.indexOf(' ') + 1)
            setLatitude(lat)
            setLongitude(lon)
        }
    }, [balloon])

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

    useEffect(() => {
        if (dislikedAttraction) {
            const handleDeleteLike = async (name) => {
                try {
                    await axios.post(`${BACKEND}/like/delete/${name}`, {}, {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${ACCESS_TOKEN}`
                        }
                    })
                } catch (error) {
                    alert("Impossible to delete like")
                }
            }
            handleDeleteLike(dislikedAttraction)
        }
    }, [dislikedAttraction]);

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
                        'Authorization': `Bearer ${ACCESS_TOKEN}`,
                        'Content-Type': 'multipart/form-data'
                    }
                }
            )
            // TODO: это ломает nginx (не может понять куда редикретить)
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

    console.log(feature)

    const handleImageChange = (e) => {
        setImages([...e.target.files])
    }

    // TODO: add different type of images
    // TODO: arrow sliders broken
    return (
        <div>
            <Header/>
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
                                        {hoveringLiked ? (
                                            <FaHeartBroken
                                                className="like-button-heart liked hover-break"
                                                style={{background: "red", color: "black"}}
                                                size={36}
                                                onClick={() => setDislikedAttraction(attraction.name)}
                                                title="You have liked this attraction"
                                                onMouseEnter={() => setHoveringLiked(true)}
                                                onMouseLeave={() => setHoveringLiked(false)}
                                            />
                                        ) : (
                                            <FaHeart
                                                className="like-button-heart liked"
                                                size={36}
                                                onClick={() => setDislikedAttraction(attraction.name)}
                                                title="You have liked this attraction"
                                                onMouseEnter={() => setHoveringLiked(true)}
                                                onMouseLeave={() => setHoveringLiked(false)}
                                            />
                                        )}
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
            <MDBContainer>
                <div>
                    {isLoadingFeature && (
                        <Spinner/>
                    )}
                    {feature?.type === 'text' && (
                        feature.data.elements.map((el, index) => (
                            <div key={index}>
                                <h5>{el.dishName}</h5>
                                <p>{el.dishDescription}</p>
                                <p>Price: {el.dishPrice}</p>
                                {el.dishImage && (
                                    <img
                                        src={`data:image/jpeg;base64,${el.dishImage}`}
                                        alt={el.dishName}
                                        style={{maxWidth: '200px'}}
                                    />
                                )}
                            </div>
                        ))
                    )}
                    {feature?.type === 'file' && (
                        <iframe
                            src={`data:application/pdf;base64,${feature.data.elements[0]?.file}`}
                            width="100%"
                            height="600px"
                            style={{border: 'none'}}
                            title="Menu PDF"
                        />
                    )}
                    {feature?.type === 'park' && (
                        feature.data.map((el, index) => (
                            <div key={index}>
                                <h5>{el.name}</h5>
                                <p>{el.description}</p>
                                <p>Open time: {el.openTime}</p>
                                <p>Close time: {el.closeTime}</p>
                                {el.image && (
                                    <img
                                        src={`data:image/${el.imageFormat};base64,${el.image}`}
                                        alt={el.name}
                                        style={{maxWidth: '200px'}}
                                    />
                                )}
                            </div>
                        ))
                    )}
                    {(feature?.type === 'poster') && (
                        feature.data.map((image, index) => (
                            <iframe
                                src={`data:application/pdf;base64,${feature.data[0]}`}
                                width="100%"
                                height="600px"
                                style={{border: 'none'}}
                                title="Menu PDF"
                            />
                        ))
                    )}

                </div>
            </MDBContainer>
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
                                    style={{marginBottom: '10px'}}
                                />
                                <button type="submit">Submit Comment</button>
                            </form>
                        </div>
                    </div>
                )}

                {/* Displaying Discussions */}
                <div className="discussions-main-container">
                    <div className="discussion-container">
                        {discussions.length === 0 ? (
                            <p>No discussions, be first</p>
                        ) : (
                            discussions.map((discussion) => (
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
                            ))
                        )}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Attraction
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import Header from "./Header";
import {useParams} from "react-router-dom";
import {MDBInput, MDBTextArea} from "mdb-react-ui-kit";

function Attraction() {
    const {name} = useParams()
    const [error, setError] = useState('')

    const [attraction, setAttraction] = useState('')
    const [discussions, setDiscussions] = useState([])
    const [attractionUuid, setAttractionUuid] = useState('')

    const [title, setTitle] = useState('')
    const [contentLike, setContentLike] = useState('')
    const [contentDislike, setContentDislike] = useState('')
    const [content, setContent] = useState('')
    const [rating, setRating] = useState('')
    // const [images, setImages] = useState([])

    const [showCreateDiscussionForm, setShowCreateDiscussionForm] = useState(false)

    useEffect(() => {
        const fetchAttraction = async () => {
            try {
                const token = JSON.parse(localStorage.getItem('user'))?.token
                const response =
                    await axios.get(`http://localhost:8080/attraction/${name}`, {
                        headers: {
                            'Authorization': `Bearer ${token}`,
                        },
                    })
                setAttraction(response.data)
                setAttractionUuid(response.data.uuid)
            } catch (err) {
            }
        }

        const fetchDiscussions = async () => {
            try {
                const token = JSON.parse(localStorage.getItem('user'))?.token
                const response =
                    await axios.get(`http://localhost:8080/attraction-discussion/${attractionUuid}`, {
                        headers: {
                            'Authorization': `Bearer ${token}`
                        }
                })
                console.log(response.data)
                setDiscussions(response.data._embedded.attractionDiscussionModelList)
            } catch (err) {}
        }
        fetchAttraction().then(r => {console.log(r)})
        fetchDiscussions().then(r => {console.log(r)})
    }, [attractionUuid, name]);

    const handleLeaveDiscussion = () => {
        setShowCreateDiscussionForm(true); // Open the pop-up
    };

    const handleClosePopup = () => {
        setShowCreateDiscussionForm(false); // Close the pop-up
    };

    const handleSendDiscussion = async(e) => {
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
                    { type: 'application/json' }
                )
            );

            // TODO: загрузка картинок к отзывам

            // if (images) {
            //     formData.append('images', images)
            // }

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
            window.location.reload();
        } catch (error) {
            if (error.response && error.response.data) {
                const errorMessages = error.response.data;
                setError(
                    Object.entries(errorMessages)
                        .map(([field, message]) => `${field}: ${message}`)
                        .join(', ')
                );
            } else {
                setError('Discussion registration failed, please try again.');
            }
        }
    }

    return (
        <div>
            <Header/>
            <div className="attraction-main-container">
                <div className="attraction-container">
                    <div className="cards-container">
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
                            <button className="close-popup" onClick={handleClosePopup}>X</button>
                            <h2>Leave a Comment</h2>
                            <form onSubmit={handleSendDiscussion}>
                                <div>
                                    <label>Title:</label>
                                    <MDBInput
                                        type="text"
                                        value={title}
                                        onChange={(e) => setTitle(e.target.value)}
                                        required
                                    />
                                </div>
                                <div>
                                    <label>What did you like?</label>
                                    <MDBInput
                                        type="text"
                                        value={contentLike}
                                        onChange={(e) => setContentLike(e.target.value)}
                                        required
                                    />
                                </div>
                                <div>
                                    <label>What did you dislike?</label>
                                    <MDBInput
                                        type="text"
                                        value={contentDislike}
                                        onChange={(e) => setContentDislike(e.target.value)}
                                        required
                                    />
                                </div>
                                <div>
                                    <label>Overall thoughts:</label>
                                    <MDBTextArea
                                        value={content}
                                        onChange={(e) => setContent(e.target.value)}
                                        required
                                    />
                                </div>
                                <div>
                                    <label>Rating:</label>
                                    <MDBInput
                                        type="number"
                                        min="1"
                                        max="5"
                                        value={rating}
                                        onChange={(e) => setRating(e.target.value)}
                                        required
                                    />
                                </div>
                                <button type="submit">Submit Comment</button>
                            </form>
                        </div>
                    </div>
                )}
                <div className="discussions-main-container">
                    <div className="discussion-container">
                        {discussions.map((discussion) => (
                            <div key={discussion.id} className="discussion-card">
                                <div className="discussion-author">
                                    Author: {discussion.author}
                                </div>
                                <div className="discussion-title">
                                    Title: {discussion.title}
                                </div>
                                <div className="discussion-like">
                                    Liked: {discussion.contentLike}
                                </div>
                                <div className="discussion-dislike">
                                    Disliked: {discussion.contentDislike}
                                </div>
                                <div className="discussion-content">
                                    Review: {discussion.content}
                                </div>
                                <div className="discussion-ration">
                                    Overall: {discussion.rating}
                                </div>
                                <div className="discussion-created_at">
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
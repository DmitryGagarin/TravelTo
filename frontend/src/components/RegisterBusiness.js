import React, {useEffect, useState} from 'react'
import {MDBContainer, MDBInput} from "mdb-react-ui-kit"
import axios from "axios"
import {Link, useNavigate} from "react-router-dom"
import Settings from "./Settings"
import Inputmask from "inputmask"
import "react-phone-number-input/style.css"
import {getAttractionCardStyle} from "../utils/StyleUtils"

// TODO: улетели настройки
function RegisterBusiness() {
    const BACKEND = process.env.REACT_APP_BACKEND_URL
    const FRONTEND = process.env.REACT_APP_FRONTEND_URL

    const [ownerTelegram, setOwnerTelegram] = useState('')
    const [attractionName, setAttractionName] = useState('')
    const [description, setDescription] = useState('')
    const [city, setCity] = useState('')
    const [street, setStreet] = useState('')
    const [household, setHousehold] = useState('')
    const [images, setImages] = useState([])
    const [phone, setPhone] = useState('')
    const [website, setWebsite] = useState('')
    const [type, setType] = useState('')
    const [openTime, setOpenTime] = useState('')
    const [closeTime, setCloseTime] = useState('')
    const [error, setError] = useState('')

    const [currentImageIndexes, setCurrentImageIndexes] = useState({})

    const history = useNavigate()

    useEffect(() => {
        const phoneInput = document.getElementById("phone")
        Inputmask("+9 999 999-9999").mask(phoneInput)
    }, [])

    const validateForm = () => {
        return (
            ownerTelegram &&
            attractionName &&
            description &&
            city &&
            street &&
            household &&
            phone &&
            type &&
            openTime &&
            closeTime
        )
    }

    const handleImageChange = (e) => {
        const filesArray = Array.from(e.target.files)
        setImages(filesArray)
        setCurrentImageIndexes(prev => ({...prev, [attractionName]: 0}))
    }


    const handleAttractionRegistration = async (e) => {
        e.preventDefault()

        if (!validateForm()) {
            setError('Please fill in all required fields.')
            return
        }

        try {
            const formData = new FormData()

            const formJson = {
                ownerTelegram,
                attractionName,
                description,
                address: city + ", " + street + ", " + household,
                phone,
                website,
                attractionType: type,
                openTime,
                closeTime,
            }

            formData.append('attractionCreateForm',
                new Blob(
                    [JSON.stringify(formJson)],
                    {type: 'application/json'}
                )
            )

            if (images.length > 0) {
                images.forEach((image) => {
                    formData.append('images', image)
                })
            }

            await axios.post(
                `${BACKEND}/attraction/register-business`,
                formData,
                {
                    headers: {
                        'Authorization': `Bearer ${JSON.parse(localStorage.getItem('user'))?.accessToken}`,
                        'Content-Type': 'multipart/form-data'
                    }
                }
            )

            history('/settings/my')
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
                setError('Business registration failed, please try again.')
            }
        }
    }

    const handleNextImage = (currentIndex, imagesArray, name) => {
        const nextIndex = (currentIndex + 1) % imagesArray.length

        setCurrentImageIndexes(prev => ({
            ...prev,
            [name]: nextIndex
        }))
    }

    const handlePrevImage = (currentIndex, imagesArray, name) => {
        const prevIndex = (currentIndex - 1 + imagesArray.length) % imagesArray.length

        setCurrentImageIndexes(prev => ({
            ...prev,
            [name]: prevIndex
        }))
    }

    return (
        <div className="register-business-main-container">
            <div className="register-business-container">
                <MDBContainer>
                    <MDBInput
                        wrapperClass='mb-4'
                        placeholder='Your telegram account'
                        id='ownerTelegram'
                        value={ownerTelegram}
                        type='text'
                        required
                        onChange={(e) => setOwnerTelegram(e.target.value)}
                    />
                    <hr className="divider-vertical"/>
                    <MDBInput
                        wrapperClass='mb-4'
                        placeholder='Attraction Name'
                        id='attractionName'
                        value={attractionName}
                        type='text'
                        required
                        onChange={(e) => setAttractionName(e.target.value)}
                    />
                    <MDBInput
                        wrapperClass='mb-4'
                        placeholder='Description'
                        id='description'
                        value={description}
                        type='text'
                        required
                        onChange={(e) => setDescription(e.target.value)}
                    />
                    <MDBInput
                        wrapperClass='mb-4'
                        placeholder='City'
                        id='city'
                        value={city}
                        type='text'
                        required
                        onChange={(e) => setCity(e.target.value)}
                    />
                    <MDBInput
                        wrapperClass='mb-4'
                        placeholder='Street'
                        id='street'
                        value={street}
                        type='text'
                        required
                        onChange={(e) => setStreet(e.target.value)}
                    />
                    <MDBInput
                        wrapperClass='mb-4'
                        placeholder='Household'
                        id='household'
                        value={household}
                        type='text'
                        required
                        onChange={(e) => setHousehold(e.target.value)}
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
                        wrapperClass='mb-4'
                        placeholder='Phone'
                        id='phone'
                        value={phone}
                        type='tel'
                        required
                        onChange={(e) => setPhone(e.target.value)}
                    />
                    <MDBInput
                        wrapperClass='mb-4'
                        placeholder='Website'
                        id='website'
                        value={website}
                        type='url'
                        onChange={(e) => setWebsite(e.target.value)}
                    />
                    <select
                        className='mb-4'
                        id='type'
                        value={type}
                        required
                        onChange={(e) => setType(e.target.value)}
                    >
                        <option value="" disabled>Type</option>
                        <option value="museum">Museum</option>
                        <option value="gallery">Gallery</option>
                        <option value="park">Park</option>
                        <option value="religious">Religious</option>
                        <option value="cafe">Cafe</option>
                        <option value="restaurant">Restaurant</option>
                    </select>
                    <MDBInput
                        wrapperClass='mb-4'
                        placeholder='OpenTime'
                        id='openTime'
                        value={openTime}
                        type='time'
                        required={true}
                        onChange={(e) => setOpenTime(e.target.value)}
                    />
                    <MDBInput
                        wrapperClass='mb-4'
                        placeholder='CloseTime'
                        id='closeTime'
                        value={closeTime}
                        type='time'
                        required
                        onChange={(e) => setCloseTime(e.target.value)}
                    />
                    {error && <p className="text-danger">{error}</p>}
                    <button onClick={handleAttractionRegistration} disabled={!validateForm()}>Register business</button>
                </MDBContainer>
            </div>
            <div className="attraction-preview">
                <p style={{textAlign: 'center', fontSize: '32px'}}>Attraction preview</p>
                <div className="attractions-preview-container">
                    <div className="cards-container">
                        <div key={attractionName} className="attraction-card">
                            <div className="image-container">
                                {images.length > 0 && images[currentImageIndexes[attractionName] || 0] ? (
                                    <img
                                        src={URL.createObjectURL(images[currentImageIndexes[attractionName] || 0])}
                                        alt={attractionName}
                                        className="card-image"
                                    />
                                ) : (
                                    <p>No image selected</p>
                                )}
                                <div className="image-navigation">
                                    <button
                                        className="image-nav-button left"
                                        onClick={() =>
                                            handlePrevImage(currentImageIndexes[attractionName] || 0, images, attractionName)
                                        }
                                    >
                                        ←
                                    </button>
                                    <button
                                        className="image-nav-button right"
                                        onClick={() =>
                                            handleNextImage(currentImageIndexes[attractionName] || 0, images, attractionName)
                                        }
                                    >
                                        →
                                    </button>
                                </div>
                            </div>
                            <div className="attraction-data">
                                <div
                                    className="attraction-type"
                                    style={getAttractionCardStyle(type)}
                                >
                                    {type}
                                </div>
                                <div className="contact-info">
                                    <p>
                                        Website:{" "}
                                        <Link to={website} target="_blank" rel="noopener noreferrer">
                                            {website}
                                        </Link>
                                    </p>
                                    <p>Phone: {phone}</p>
                                </div>
                                <div className="name-description">
                                    <h5>{attractionName}</h5>
                                    <p>{description}</p>
                                </div>
                                <div className="time">
                                    <p>From: {openTime}</p>
                                    <p>To: {closeTime}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <Settings/>
        </div>
    )
}

export default RegisterBusiness

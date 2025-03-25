import React, {useState} from 'react'
import {MDBContainer, MDBInput} from "mdb-react-ui-kit"
import axios from "axios"
import {useNavigate} from "react-router-dom"
import Settings from "./Settings";

function RegisterBusiness() {

    const [name, setName] = useState('')
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

    const history = useNavigate()
    const authUser = JSON.parse(localStorage.getItem('user'))

    const validateForm = () => {
        return name && description && city && street && household && phone && type && openTime && closeTime;
    }

    const handleImageChange = (e) => {
        setImages([...e.target.files])
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
                name,
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
                images.forEach((image, index) => {
                    formData.append('images', image) // Each file gets appended as 'images' with a unique key
                })
            }

            await axios.post(
                "http://localhost:8080/attraction/register-business",
                formData,
                {
                    headers: {
                        'Authorization': 'Bearer ' + authUser.accessToken,
                        'Content-Type': 'multipart/form-data'
                    }
                }
            )

            history('/home')
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
                setError('Business registration failed, please try again.')
            }
        }
    }

    return (
        <div className="register-business-main-container">
            <div className="register-business-container">
                <MDBContainer>
                    <MDBInput
                        wrapperClass='mb-4'
                        placeholder='Name'
                        id='name'
                        value={name}
                        type='text'
                        required
                        onChange={(e) => setName(e.target.value)}
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
            <Settings/>
        </div>
    )
}

export default RegisterBusiness

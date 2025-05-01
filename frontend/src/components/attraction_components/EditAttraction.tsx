import {useNavigate, useParams} from "react-router-dom"
import axios from "axios"
import Header from '../Header';
import {MDBInput} from "mdb-react-ui-kit"
import {MdDelete} from "react-icons/md"
import {useEffect, useState} from "react"

function EditAttraction() {
    const BACKEND = process.env.REACT_APP_BACKEND_URL
    const FRONTEND = process.env.REACT_APP_FRONTEND_URL

    const {name} = useParams()
    const [attraction, setAttraction] = useState({
        website: "",
        phone: "",
        name: "",
        description: "",
        openTime: "",
        closeTime: "",
        address: "",
        type: "",
        images: [] as File[]
    })
    const [city, setCity] = useState('')
    const [street, setStreet] = useState('')
    const [household, setHousehold] = useState('')
    const [error, setError] = useState('')

    const history = useNavigate()

    const token = JSON.parse(localStorage.getItem('user')).accessToken

    useEffect(() => {
        const fetchAttraction = async () => {
            try {
                const response = await axios.get(`${BACKEND}/attraction/${name}`, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    },
                })
                setAttraction(response.data)
                const addressData = response.data.address.split(',')
                setCity(addressData[0])
                setStreet(addressData[1])
                setHousehold(addressData[2])

                const files: File[] = []
                response.data.images.forEach((image: string) => {
                    // Decode the base64 string
                    const byteCharacters = atob(image)
                    const byteArrays = []
                    for (let i = 0; i < byteCharacters.length; i++) {
                        byteArrays.push(byteCharacters.charCodeAt(i))
                    }
                    const byteArray = new Uint8Array(byteArrays)

                    // Create a Blob from the byte array
                    const blob = new Blob([byteArray], { type: "image/png" }) // You may need to adjust the MIME type based on the image format

                    // Convert the Blob to a File
                    const file = new File([blob], `image_${Math.random().toString(36).substring(7)}.png`, { type: "image/png" })

                    // Push the file to the array
                    files.push(file)
                })

                // Update the state with the array of File objects
                setAttraction((prevState) => ({
                    ...prevState,
                    images: files
                }))
            } catch (error) {
                if (error.response.status === 401) {
                    window.location.href = `${FRONTEND}/signin`
                }
                setError('Failed to fetch attraction data')
            }
        }
        fetchAttraction()
    }, [name, token])

    const handleAttractionEdit = async () => {
        if (!validateForm()) {
            setError('Please fill in all required fields.')
            return
        }
        try {
            const formData = new FormData()

            const formJson = {
                attractionName: attraction.name,
                description: attraction.description,
                address: `${city}, ${street}, ${household}`,
                phone: attraction.phone,
                website: attraction.website,
                attractionType: attraction.type,
                openTime: attraction.openTime,
                closeTime: attraction.closeTime,
            }

            formData.append('attractionEditForm',
                new Blob(
                    [JSON.stringify(formJson)],
                    {type: 'application/json'}
                )
            )

            attraction.images.forEach((image) => {
                formData.append('images', image)
            })

            await axios.post(
                `${BACKEND}/attraction/edit/${name}`,
                formData,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-type': 'multipart/form-data',
                    },
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
                setError('Business registration failed, please try again.')
            }
        }
    }

    const validateForm = () => {
        return (
            attraction.name &&
            attraction.description &&
            city &&
            street &&
            household &&
            attraction.phone &&
            attraction.type &&
            attraction.openTime &&
            attraction.closeTime
        )
    }

    const handleDeleteImage = (image) => {
        setAttraction((prev) => ({
            ...prev,
            images: prev.images.filter((img) => img !== image),
        }))
    }

    const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        // Ensure e.target.files is properly typed
        const files = e.target.files ? Array.from(e.target.files) : []

        setAttraction((prev) => ({
            ...prev,
            images: [...prev.images, ...files], // Append the files to the existing images array
        }))
    }

    return (
        <div>
            <Header/>
            <div className="attraction-main-container">
                <div className="attraction-container">
                    <div className="cards-container">
                        <div className="attraction-card">
                            <div className="contact-info">
                                <div className="input-group">
                                    <label htmlFor="website">Website</label>
                                    <MDBInput
                                        id="website"
                                        value={attraction.website}
                                        type="text"
                                        required
                                        onChange={(e) => setAttraction({...attraction, website: e.target.value})}
                                    />
                                </div>
                                <div className="input-group">
                                    <label htmlFor="phone">Phone</label>
                                    <MDBInput
                                        id="phone"
                                        value={attraction.phone}
                                        type="text"
                                        required
                                        onChange={(e) => setAttraction({...attraction, phone: e.target.value})}
                                    />
                                </div>
                            </div>
                            <div className="name-description">
                                <div className="input-group">
                                    <label htmlFor="name">Attraction Name</label>
                                    <MDBInput
                                        id="name"
                                        value={attraction.name}
                                        type="text"
                                        required
                                        onChange={(e) => setAttraction({...attraction, name: e.target.value})}
                                    />
                                </div>
                                <div className="input-group">
                                    <label htmlFor="description">Description</label>
                                    <MDBInput
                                        id="description"
                                        value={attraction.description}
                                        type="text"
                                        required
                                        onChange={(e) => setAttraction({...attraction, description: e.target.value})}
                                    />
                                </div>
                            </div>
                            <div className="opening-time">
                                <div className="input-group">
                                    <label htmlFor="open-time">Opening Time</label>
                                    <MDBInput
                                        id="open-time"
                                        value={attraction.openTime}
                                        type="time"
                                        required
                                        onChange={(e) => setAttraction({...attraction, openTime: e.target.value})}
                                    />
                                </div>
                                <div className="input-group">
                                    <label htmlFor="close-time">Closing Time</label>
                                    <MDBInput
                                        id="close-time"
                                        value={attraction.closeTime}
                                        type="time"
                                        required
                                        onChange={(e) => setAttraction({...attraction, closeTime: e.target.value})}
                                    />
                                </div>
                            </div>
                            <div className="address">
                                <div className="input-group">
                                    <label htmlFor="city">City</label>
                                    <MDBInput
                                        id="city"
                                        value={city}
                                        type="text"
                                        required
                                        onChange={(e) => setCity(e.target.value)}
                                    />
                                </div>
                                <div className="input-group">
                                    <label htmlFor="street">Street</label>
                                    <MDBInput
                                        id="street"
                                        value={street}
                                        type="text"
                                        required
                                        onChange={(e) => setStreet(e.target.value)}
                                    />
                                </div>
                                <div className="input-group">
                                    <label htmlFor="household">Household</label>
                                    <MDBInput
                                        id="household"
                                        value={household}
                                        type="text"
                                        required
                                        onChange={(e) => setHousehold(e.target.value)}
                                    />
                                </div>
                            </div>
                            <div className="type">
                                <label htmlFor="type">Type</label>
                                <select
                                    className="mb-4"
                                    id="type"
                                    value={attraction.type}
                                    required
                                    onChange={(e) => setAttraction({...attraction, type: e.target.value})}
                                >
                                    <option value="" disabled>Type</option>
                                    <option value="museum">Museum</option>
                                    <option value="gallery">Gallery</option>
                                    <option value="park">Park</option>
                                    <option value="religious">Religious</option>
                                    <option value="cafe">Cafe</option>
                                    <option value="restaurant">Restaurant</option>
                                </select>
                            </div>
                            <div className="input-group">
                                <MDBInput
                                    wrapperClass='mb-4'
                                    placeholder='Choose images'
                                    id='images'
                                    type='file'
                                    multiple
                                    onChange={handleImageChange}
                                />
                            </div>
                        </div>
                        <div>
                            {attraction.images.map((image, index) => (
                                <div className="image-container-edit" key={index}>
                                    {image instanceof File ? (
                                        <img
                                            src={URL.createObjectURL(image)}
                                            alt={`image-${index}`}
                                            className="card-image"
                                        />
                                    ) : (

                                        // TODO: переделать на все форматы картинов
                                        <img
                                            src={`data:image/png;base64,${image}`}
                                            alt={`image-${index}`}
                                            className="card-image"
                                        />
                                    )}
                                    <button className="delete-image-button" onClick={() => handleDeleteImage(image)}>
                                        <MdDelete size={24}/>
                                    </button>
                                </div>
                            ))}
                            {error && <p className="text-danger">{error}</p>}
                            <button className="save-changes-button" onClick={handleAttractionEdit}> Save edit</button>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    )
}

export default EditAttraction

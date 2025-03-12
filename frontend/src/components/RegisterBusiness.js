import React, {useState} from 'react'
import {MDBInput, MDBContainer} from "mdb-react-ui-kit";
import axios from "axios";
import {useNavigate} from "react-router-dom";

function RegisterBusiness() {

    const [name, setName] = useState('')
    const [description, setDescription] = useState('')
    const [address, setAddress] = useState('')
    const [image, setImage] = useState('')
    const [phone, setPhone] = useState('')
    const [website, setWebsite] = useState('')
    const [type, setType] = useState('')
    const [openTime, setOpenTime] = useState('')
    const [closeTime, setCloseTime] = useState('')
    const [error, setError] = useState('')

    const history = useNavigate()

    const authUser = JSON.parse(localStorage.getItem('user'))

    const handleRegistration = async () => {
        try {
            await axios.post(
                    "http://localhost:8080/settings/register-business",
                    {
                        name,
                        description,
                        address,
                        image,
                        phone,
                        website,
                        type,
                        openTime,
                        closeTime
                    }, {
                        headers: {
                            'Content-type': 'application/json',
                            'Authorization': 'Bearer ' + authUser.token
                        }
                    }
                )
        } catch (error) {
            if (error.response && error.response.data) {
                const errorMessages = error.response.data;
                setError(
                    Object.entries(errorMessages)
                        .map(([field, message]) => `${field}: ${message}`)
                        .join(', ')
                );
            } else {
                console.error('Signup failed:', error.message);
                setError('Signup failed, please try again.');
            }
        }
    }

    const handleHome = () => {
        return history('/home')
    }

    return (
        <div>
            <button onClick={handleHome}>Back</button>
            <MDBContainer>
                <MDBInput wrapperClass='mb-4' placeholder='Name' id='name' value={name} type='text'
                          onChange={(e) => setName(e.target.value)}/>
                <MDBInput wrapperClass='mb-4' placeholder='Description' id='description' value={description} type='text'
                          onChange={(e) => setDescription(e.target.value)}/>
                <MDBInput wrapperClass='mb-4' placeholder='Address' id='address' value={address} type='text'
                          onChange={(e) => setAddress(e.target.value)}/>
                <MDBInput wrapperClass='mb-4' placeholder='Image' id='image' value={image} type='image'
                          onChange={(e) => setImage(e.target.value)}/>
                <MDBInput wrapperClass='mb-4' placeholder='Phone' id='phone' value={phone} type='tel'
                          onChange={(e) => setPhone(e.target.value)}/>
                <MDBInput wrapperClass='mb-4' placeholder='Website' id='website' value={website} type='url'
                          onChange={(e) => setWebsite(e.target.value)}/>
                <select
                    className='mb-4'
                    id='type'
                    value={type}
                    onChange={(e) => setType(e.target.value)}
                >
                    <option value="" disabled>
                        Type
                    </option>
                    <option value="museum">Museum</option>
                    <option value="park">Park</option>
                    <option value="religious">Religious</option>
                </select>
                <MDBInput wrapperClass='mb-4' placeholder='OpenTime' id='openTime' value={openTime} type='time'
                          onChange={(e) => setOpenTime(e.target.value)}/>
                <MDBInput wrapperClass='mb-4' placeholder='CloseTime' id='closeTime' value={closeTime} type='time'
                          onChange={(e) => setCloseTime(e.target.value)}/>
                {error && <p className="text-danger">{error}</p>}
                <button onClick={handleRegistration}>Register business</button>
            </MDBContainer>
        </div>
    )
}

export default RegisterBusiness
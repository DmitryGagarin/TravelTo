import React, {useState} from 'react'
import {MDBInput, MDBContainer} from "mdb-react-ui-kit";
import axios from "axios";

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

    const authUser = JSON.parse(localStorage.getItem('user'))

    const handleRegistration = async () => {
        try {
            const response = await
                axios.post(
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
            console.log("Failed to registred new attraction")
        }
    }

    return (
        <div>
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
                <MDBInput wrapperClass='mb-4' placeholder='Type' id='type' value={type} type='text'
                          onChange={(e) => setType(e.target.value)}/>
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
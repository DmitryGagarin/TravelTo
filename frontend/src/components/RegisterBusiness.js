import React, { useState } from 'react';
import { MDBInput, MDBContainer } from "mdb-react-ui-kit";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function RegisterBusiness() {

    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [address, setAddress] = useState('');
    const [image, setImage] = useState('');
    const [phone, setPhone] = useState('');
    const [website, setWebsite] = useState('');
    const [type, setType] = useState('');
    const [openTime, setOpenTime] = useState('');
    const [closeTime, setCloseTime] = useState('');
    const [error, setError] = useState('');

    const history = useNavigate();
    const authUser = JSON.parse(localStorage.getItem('user'));

    const handleImageChange = (e) => {
        setImage(e.target.files[0]); // Set the file object
    };

    const handleRegistration = async (e) => {
        e.preventDefault();

        try {
            // Create a FormData object to send both fields and image
            const formData = new FormData();

            // Create a single object with all form values
            const formJson = {
                name,
                description,
                address,
                phone,
                website,
                attractionType: type,
                openTime,
                closeTime,
            };

            // Append the form data as JSON
            formData.append('attractionCreateForm',
                new Blob(
                    [JSON.stringify(formJson)],
                    { type: 'application/json' }
                )
            );

            // Append the image file as a separate part
            if (image) {
                formData.append('image', image);
            }

            // Send the request
            await axios.post(
                "http://localhost:8080/attraction/register-business",
                formData,
                {
                    headers: {
                        'Authorization': 'Bearer ' + authUser.token,
                        'Content-Type': 'multipart/form-data'
                    }
                }
            )

            history('/home')
        } catch (error) {
            if (error.response && error.response.data) {
                const errorMessages = error.response.data;
                setError(
                    Object.entries(errorMessages)
                        .map(([field, message]) => `${field}: ${message}`)
                        .join(', ')
                );
            } else {
                setError('Business registration failed, please try again.');
            }
        }
    };

    const handleHome = () => {
        return history('/home');
    };

    return (
        <div>
            <button onClick={handleHome}>Back</button>
            <MDBContainer>
                <MDBInput
                    wrapperClass='mb-4'
                    placeholder='Name'
                    id='name'
                    value={name}
                    type='text'
                    onChange={(e) => setName(e.target.value)}
                />
                <MDBInput
                    wrapperClass='mb-4'
                    placeholder='Description'
                    id='description'
                    value={description}
                    type='text'
                    onChange={(e) => setDescription(e.target.value)}
                />
                <MDBInput
                    wrapperClass='mb-4'
                    placeholder='Address'
                    id='address'
                    value={address}
                    type='text'
                    onChange={(e) => setAddress(e.target.value)}
                />
                <MDBInput
                    wrapperClass='mb-4'
                    placeholder='Choose an image'
                    id='image'
                    type='file'
                    onChange={handleImageChange}
                />
                <MDBInput
                    wrapperClass='mb-4'
                    placeholder='Phone'
                    id='phone'
                    value={phone}
                    type='tel'
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
                    onChange={(e) => setType(e.target.value)}
                >
                    <option value="" disabled>Type</option>
                    <option value="museum">Museum</option>
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
                    onChange={(e) => setOpenTime(e.target.value)}
                />
                <MDBInput
                    wrapperClass='mb-4'
                    placeholder='CloseTime'
                    id='closeTime'
                    value={closeTime}
                    type='time'
                    onChange={(e) => setCloseTime(e.target.value)}
                />
                {error && <p className="text-danger">{error}</p>}
                <button onClick={handleRegistration}>Register business</button>
            </MDBContainer>
        </div>
    );
}

export default RegisterBusiness;

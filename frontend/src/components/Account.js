import React, {useEffect, useState} from 'react'
import axios from 'axios'
import {
    MDBContainer,
    MDBInput,
} from "mdb-react-ui-kit";
import {data, useNavigate} from "react-router-dom";

function Account() {

    const [user, setUser] = useState('')
    const [name, setName] = useState('')
    const [surname, setSurname] = useState('')
    const [email, setEmail] = useState('')
    const [phone, setPhone] = useState('')
    const [error, setError] = useState('')

    const history = useNavigate();

    useEffect(() => {
        // Make the GET request to /settings/profile using axios
        axios.get('http://localhost:8080/settings/profile')
            .then(response => {
                setUser(response.data); // Set the user data
                setName(response.data.name)
                setSurname(response.data.surname)
                setEmail(response.data.email)
                setPhone(response.data.phone)
            })
            .catch(error => {
                console.error('Error fetching user profile:', error);
            });
    }, []);

    const handleChange = async () => {
        try {
            const response = await
                axios.post("http://localhost:8080/settings/save-changes", {
                    name,
                    surname,
                    email,
                    phone
                }
            )
            console.log("changed applied")
            localStorage.setItem('user', JSON.stringify(response.data));
            history('/home');
        } catch (error) {
            setError("Failed to change data")
        }
    }

    return (
        <div>
            <MDBContainer>
                <MDBInput wrapperClass='mb-4' placeholder='Name' id='name' value={name} type='text' onChange={(e) => setName(e.target.value)} />
                <MDBInput wrapperClass='mb-4' placeholder='Surname' id='surname' value={surname} type='text' onChange={(e) => setSurname(e.target.value)} />
                <MDBInput wrapperClass='mb-4' placeholder='Email' id='email' value={email} type='email' onChange={(e) => setEmail(e.target.value)} />
                <MDBInput wrapperClass='mb-4' placeholder='Phone' id='phone' value={phone} type='tel' onChange={(e) => setPhone(e.target.value)} />
                {error && <p className="text-danger">{error}</p>}
                <button onClick={handleChange}> Save changes </button>
            </MDBContainer>
        </div>
    );
}

export default Account
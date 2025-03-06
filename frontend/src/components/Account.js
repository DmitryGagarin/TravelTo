import React, {use, useState} from 'react'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
import {
    MDBContainer,
    MDBInput,
    MDBBtn
} from "mdb-react-ui-kit";

function Account() {

    const [email, setEmail] = useState('');
    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');
    const [phone, setPhone] = useState('')
    const [error, setError] = useState('')


    useEffect(() => {
        // Fetch user data
        const fetchUserData = async () => {
            try {
                const token = localStorage.getItem('token');
                const response = await axios.get('http://localhost:8080/settings/{profile}', {
                    header: {
                        'Authorization' : `Bearer ${token}`
                    }
                })
                const userData = response.data;
                setName(userData.name);
                setSurname(userData.surname);
                setEmail(userData.email);
                setPhone(userData.phone);
            } catch (err) {
                setError('Failed to load user data');
            }
        };

        fetchUserData();
    }, []);

    const handleChange = async () => {
        try {
            const response = await axios.post("http://localhost:8080/settings/save-changes", {
                name,
                surname,
                email,
                phone
            })
            console.log("changed applied")
            history('/')
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
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import {
    MDBContainer,
    MDBInput,
} from "mdb-react-ui-kit";
import {useNavigate} from "react-router-dom";

function Account() {

    const [name, setName] = useState('')
    const [surname, setSurname] = useState('')
    const [phone, setPhone] = useState('')
    const [email, setEmail] = useState('')
    const [error, setError] = useState('')

    const history = useNavigate();
    const authUser = JSON.parse(localStorage.getItem('user'))

    useEffect(() => {
        setName(authUser.name)
        setSurname(authUser.surname)
        setPhone(authUser.phone)
        setEmail(authUser.email)
    }, [])

    const handleChange = async () => {
        try {
            const response = await
                axios.post("http://localhost:8080/settings/save-changes",
                    {
                        name,
                        surname,
                        phone,
                        email
                    }, {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': 'Bearer ' + authUser.token
                        }
                    })
            localStorage.setItem('user', JSON.stringify(response.data));
            history('/home');
        } catch (error) {
            console.log(authUser)
            setError("Failed to change data")
        }
    }

    const handleDelete = async () => {
        try {
            console.log(authUser.token)
            console.log(`Bearer ${authUser.token}`)
            await axios.post("http://localhost:8080/user/delete",
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${authUser.token}`
                    }
                })
            localStorage.removeItem('user')
            history('/')
        } catch (error) {
            // console.log(authUser)
            setError("Failed to change data")
        }
    }

    const handleHome = () => {
        history("/home")
    }

    return (
        <div>
            <MDBContainer>
                <button onClick={handleHome}> Home</button>
            </MDBContainer>
            <MDBContainer>
                <MDBInput wrapperClass='mb-4' placeholder='Name' id='name' value={name} type='text'
                          onChange={(e) => setName(e.target.value)}/>
                <MDBInput wrapperClass='mb-4' placeholder='Surname' id='surname' value={surname} type='text'
                          onChange={(e) => setSurname(e.target.value)}/>
                <MDBInput wrapperClass='mb-4' placeholder='Phone' id='phone' value={phone} type='tel'
                          onChange={(e) => setPhone(e.target.value)}/>
                <MDBInput wrapperClass='mb-4' placeholder='Email' id='email' value={email} type='email'
                          onChange={(e) => setEmail(e.target.value)}/>
                {error && <p className="text-danger">{error}</p>}
                <button onClick={handleChange}>Save changes</button>
                <button onClick={handleDelete}>Delete account</button>
            </MDBContainer>

        </div>
    );
}

export default Account
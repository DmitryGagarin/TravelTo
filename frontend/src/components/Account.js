import React, {useEffect, useState} from 'react'
import axios from 'axios'
import {MDBContainer, MDBInput} from "mdb-react-ui-kit"
import {useNavigate} from "react-router-dom"
import Settings from "./Settings"
import Inputmask from "inputmask"

function Account() {
    const authUser = JSON.parse(localStorage.getItem('user'))

    const [name, setName] = useState(authUser.name)
    const [surname, setSurname] = useState(authUser.surname)
    const [phone, setPhone] = useState(authUser.phone)
    const [email, setEmail] = useState(authUser.email)

    const [error, setError] = useState('')

    const [deleteButtonClicked, setDeleteButtonClicked] = useState(false)

    const history = useNavigate()

    useEffect(() => {
        const phoneInput = document.getElementById("phone")
        Inputmask("+9 (999) 999-9999").mask(phoneInput)
    }, [])

    const handleChange = async () => {
        try {
            const response = await axios.post("http://localhost:8080/setting/save-changes",
                {
                    name, surname, email, phone
                }, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${authUser.accessToken}`
                    }
                })
            if (response) {
                localStorage.setItem('user', JSON.stringify(response.data))
            }
            window.location.reload()
        } catch (error) {
            if (error.response.status === 401) {
                window.location.href = "http://localhost:4000/signin"
            }
            console.log(error)
            setError("Failed to change data")
        }
    }

    useEffect(() => {
        if (!deleteButtonClicked) return

        const handleDelete = async () => {
            try {
                await axios.post("http://localhost:8080/user/delete", {}, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${authUser.accessToken}`
                    }
                })

                localStorage.removeItem('user')
                history('/signin')
            } catch (error) {
                console.log(error)
                setError("Failed to delete account")
            }
        }

        handleDelete()

        setDeleteButtonClicked(false)
    }, [deleteButtonClicked, authUser.accessToken, history])

    return (
        <div className="account-details-main-container">
            <div className="account-details-container">
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
                        placeholder='Surname'
                        id='surname'
                        value={surname}
                        type='text'
                        onChange={(e) => setSurname(e.target.value)}
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
                        placeholder='Email'
                        id='email'
                        value={email}
                        type='email'
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    {error && <p className="text-danger">{error}</p>}
                    <button onClick={handleChange}>Save changes</button>
                    <button onClick={() => setDeleteButtonClicked(true)}>Delete account</button>
                </MDBContainer>
            </div>
            <Settings/>
        </div>
    )
}

export default Account

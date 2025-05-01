import React, {useEffect, useState} from 'react'
import {MDBContainer, MDBRadio} from "mdb-react-ui-kit"
import axios from "axios"
import {useNavigate} from "react-router-dom"
import Settings from "../Settings"
import Inputmask from "inputmask"
import "react-phone-number-input/style.css"
import {validateAttractionData, validationTextMenuData} from "../../utils/AttractionUtils"
import {catchError} from "../../utils/ErrorUtils"
import AttractionCreateForm from "./AttractionCreateForm"
import FileMenuCreateForm from "./FileMenuCreateForm"
import AttractionPreview from "./AttractionPreview"
import TextMenuCreateForm from "./TextMenuCreateForm"

// TODO: улетели настройки
function RegisterBusiness() {
    const BACKEND = process.env.REACT_APP_BACKEND_URL
    const FRONTEND = process.env.REACT_APP_FRONTEND_URL
    const ACCESS_TOKEN = JSON.parse(localStorage.getItem('user'))?.accessToken

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

    const [fileMenuSelected, setFileMenuSelected] = useState(false)
    const [fileMenuFiles, setFileMenuFile] = useState('')

    const [dishes, setDishes] = useState([0])

    const [textMenuNames, setTextMenuNames] = useState([])
    const [textMenuDescriptions, setTextMenuDescriptions] = useState([])
    const [textMenuPrices, setTextMenuPrices] = useState([])
    const [textMenuImages, setTextMenuImages] = useState([])

    const history = useNavigate()

    useEffect(() => {
        const phoneInput = document.getElementById("phone")
        Inputmask("+9 999 999-9999").mask(phoneInput)
    }, [])

    const handleImageChange = (e) => {
        const filesArray = Array.from(e.target.files)
        setImages(filesArray)
        setCurrentImageIndexes(prev => ({...prev, [attractionName]: 0}))
    }

    const handleMenuFileChange = (e) => {
        const filesArray = Array.from(e.target.files)
        setFileMenuFile(filesArray)
    }

    const handleMenuRegistration = async () => {
        if (!fileMenuSelected) {
            if (!validationTextMenuData(
                textMenuNames,
                textMenuDescriptions,
                textMenuPrices,
                textMenuImages
            )) {
                setError('Check menu creation form')
                return
            }

            try {
                const formData = new FormData()
                const formJson = {
                    names: textMenuNames,
                    description: textMenuDescriptions,
                    prices: textMenuPrices
                }
                formData.append(
                    'textMenuCreateForm',
                    new Blob(
                        [JSON.stringify(formJson)],
                        {type: 'application/json'}
                    )
                )

                images.forEach((image) => {
                    formData.append('images', image)
                })

                await axios.post(
                    `${BACKEND}/attraction/${attractionName}/features/create-text-menu`,
                    formData,
                    {
                        headers: {
                            'Authorization': `Bearer ${ACCESS_TOKEN}`,
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                )
                return true
            } catch (error) {
                catchError(error, setError, FRONTEND, 'Problem in creating a menu')
                return false
            }
        } else {
            try {
                await axios.post(
                    `${BACKEND}/attraction/${attractionName}/features/create-file-menu`,
                    fileMenuFiles,
                    {
                        headers: {
                            'Authorization': `Bearer ${ACCESS_TOKEN}`
                        }
                    }
                )
                return true
            } catch (error) {
                catchError(error, setError, FRONTEND, 'Problem in creating an attraction')
                return false
            }
        }
    }

    const handleAttractionRegistration = async (e) => {
        e.preventDefault()

        if (!validateAttractionData(
            ownerTelegram, attractionName,
            description, city, street,
            household, phone, type,
            openTime, closeTime
        )) {
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
                        'Authorization': `Bearer ${ACCESS_TOKEN}`,
                        'Content-Type': 'multipart/form-data'
                    }
                }
            )
            return true
        } catch (error) {
            catchError(error, setError, FRONTEND, 'Impossible to register an attraction')
            return false
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

    const generateNewDishForm = () => {
        setDishes([...dishes, dishes.length])
    }

    const registerAttraction = async (e) => {
        e.preventDefault()

        // First validate and register attraction
        const attractionValid = await handleAttractionRegistration(e)
        if (!attractionValid) {
            alert('impossible to create the attraction')
        }

        // Then validate and register menu
        const menuValid = await handleMenuRegistration()
        if (!menuValid) {
            alert('impossible to create menu to the attraction')
        }

        // If both succeed, redirect
        history('/settings/my')
    }

    return (
        <div className="register-business-main-container">
            <div className="register-business-container">
                <MDBContainer>
                    <AttractionCreateForm {...{ ownerTelegram, setOwnerTelegram, attractionName, setAttractionName, description, setDescription, city, setCity, street, setStreet, household, setHousehold, phone, setPhone, website, setWebsite, type, setType, openTime, setOpenTime, closeTime, setCloseTime, handleImageChange }} />
                    {error && <p className="text-danger">{error}</p>}
                    <hr className="divider-vertical" />
                    <MDBRadio name="file-menu-check" id="file-menu-check" value="file-menu" label="File Menu" inline checked={fileMenuSelected} onChange={() => setFileMenuSelected(true)} />
                    <MDBRadio name="text-menu-check" id="text-menu-check" value="text-menu" label="Text Menu" inline checked={!fileMenuSelected} onChange={() => setFileMenuSelected(false)} />
                    {fileMenuSelected ? <FileMenuCreateForm handleMenuFileChange={handleMenuFileChange} /> : <TextMenuCreateForm {...{ dishes, setTextMenuNames, setTextMenuDescriptions, setTextMenuPrices, setTextMenuImages, generateNewDishForm }} />}
                </MDBContainer>
                <button onClick={registerAttraction} disabled={!validateAttractionData}>Register business</button>
            </div>
            <AttractionPreview {...{ attractionName, type, phone, website, openTime, closeTime, description, images, currentImageIndexes, handlePrevImage, handleNextImage }} />
            <Settings/>
        </div>
    )
}

export default RegisterBusiness

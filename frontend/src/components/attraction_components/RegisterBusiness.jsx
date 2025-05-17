import React, {useEffect, useState} from 'react'
import {MDBContainer, MDBRadio} from "mdb-react-ui-kit"
import axios from "axios"
import {useNavigate} from "react-router-dom"
import Settings from "../Settings"
import Inputmask from "inputmask"
import "react-phone-number-input/style.css"
import {validateAttractionData} from "../../utils/AttractionUtils"
import {catchError} from "../../utils/ErrorUtils"
import AttractionCreateForm from "./create_forms/AttractionCreateForm"
import FileMenuCreateForm from "./create_forms/FileMenuCreateForm"
import AttractionPreview from "./preview_componenets/AttractionPreview"
import {TextMenuCreateForm} from "./create_forms/TextMenuCreateForm"
import {ParkFacilityCreateForm} from "./create_forms/ParkFacilityCreateForm"
import {PosterCreateForm} from "./create_forms/PosterCreateForm"
import AttractionPreviewPoster from "./preview_componenets/AttractionPreviewPoster"
import AttractionPreviewFileMenu from "./preview_componenets/AttractionPreviewFileMenu"
import AttractionPreviewPark from "./preview_componenets/AttractionPreviewPark"
import AttractionPreviewTextMenu from "./preview_componenets/AttractionPreviewTextMenu"

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
    const [fileMenuFiles, setFileMenuFiles] = useState([])
    const [posterImages, setPosterImages] = useState([])

    const [dishes, setDishes] = useState([
        {name: '', description: '', price: '', image: null}
    ])

    const [facilities, setFacilities] = useState([
        {name: '', description: '', openTime: '', closeTime: '', image: null}
    ])

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
        setFileMenuFiles(filesArray)
    }

    const handlePosterFileChange = (e) => {
        const filesArray = Array.from(e.target.files)
        setPosterImages(filesArray)
    }

    const handleMenuRegistration = async () => {
        if (!fileMenuSelected) {
            try {
                const formData = new FormData()

                formData.append(
                    'textMenuCreateForm',
                    new Blob(
                        [JSON.stringify({
                            names: dishes.map(d => d.name),
                            descriptions: dishes.map(d => d.description),
                            prices: dishes.map(d => d.price),
                        })],
                        {type: 'application/json'}
                    )
                )

                dishes.forEach((dish) => {
                    if (dish.image) {
                        formData.append('images', dish.image)
                    }
                })

                await axios.post(
                    `${BACKEND}/attraction-feature/${attractionName}/create-text-menu`,
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
                const fileFormData = new FormData()
                fileMenuFiles.forEach((file) => {
                    fileFormData.append('files', file)
                })

                await axios.post(
                    `${BACKEND}/attraction-feature/${attractionName}/create-file-menu`,
                    fileFormData,
                    {
                        headers: {
                            'Authorization': `Bearer ${ACCESS_TOKEN}`,
                        }
                    }
                )
                return true
            } catch (error) {
                catchError(error, setError, FRONTEND, 'Problem in creating a menu')
                return false
            }
        }
    }

    const handleParkFacilityRegistration = async () => {
        try {
            const formData = new FormData()

            formData.append(
                'parkFacilityCreateForm',
                new Blob(
                    [JSON.stringify({
                        names: facilities.map(d => d.name),
                        descriptions: facilities.map(d => d.description),
                        openTimes: facilities.map(d => d.openTime),
                        closeTimes: facilities.map(d => d.closeTime)
                    })],
                    {type: 'application/json'}
                )
            )

            facilities.forEach((facility) => {
                if (facility.image) {
                    formData.append('images', facility.image)
                }
            })

            await axios.post(
                `${BACKEND}/attraction-feature/${attractionName}/create-park-facility`,
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
            catchError(error, setError, FRONTEND, 'Problem in creating park facilities')
            return false
        }
    }

    const handlePosterRegistration = async () => {
        const fileFormData = new FormData()
        posterImages.forEach((image) => {
            fileFormData.append('images', image)
        })

        await axios.post(
            `${BACKEND}/attraction-feature/${attractionName}/create-poster`,
            fileFormData,
            {
                headers: {
                    'Authorization': `Bearer ${ACCESS_TOKEN}`,
                }
            }
        )
        return true
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

    const updateDish = (index, field, value) => {
        const updated = [...dishes]
        updated[index][field] = value
        setDishes(updated)
    }

    const updateFacility = (index, field, value) => {
        const updated = [...facilities]
        updated[index][field] = value
        setFacilities(updated)
    }

    const generateNewDishForm = () => {
        setDishes([...dishes, {name: '', description: '', price: '', image: null}])
    }

    const generateNewFacilityForm = () => {
        setFacilities([...facilities, {name: '', description: '', openTime: '', closeTime: '', image: null}])
    }

    const registerAttraction = async (e) => {
        e.preventDefault()
        let correct = true

        const attractionValid = await handleAttractionRegistration(e)

        if (!attractionValid) {
            alert('impossible to create the attraction')
            correct = false
        }

        if (type === 'cafe' || type === 'restaurant') {
            const menuValid = await handleMenuRegistration()
            if (!menuValid) {
                alert('impossible to create menu to the attraction')
                correct = false
            }
        }

        if (type === 'park') {
            const parkValid = await handleParkFacilityRegistration()
            if (!parkValid) {
                alert('impossible to create list of facilities to the attraction')
                correct = false
            }
        }

        if (type === 'theater' || type === 'gallery' || type === 'museum') {
            const posterValid = await handlePosterRegistration()
            if (!posterValid) {
                alert('impossible to create poster to the attraction')
                correct = false
            }
        }

        if (correct) {
            history('/settings/my')
        }
    }

    return (
        <div className="register-business-main-container">
            <div className="register-business-container">
                <MDBContainer>
                    <AttractionCreateForm {...{
                        ownerTelegram, setOwnerTelegram,
                        attractionName, setAttractionName,
                        description, setDescription,
                        city, setCity,
                        street, setStreet,
                        household, setHousehold,
                        phone, setPhone,
                        website, setWebsite,
                        type, setType,
                        openTime, setOpenTime,
                        closeTime, setCloseTime,
                        handleImageChange
                    }} />
                    {error && <p className="text-danger">{error}</p>}
                    <hr className="divider-vertical"/>

                    {(type === 'cafe' || type === 'restaurant') && (
                        <>
                            <MDBRadio name="file-menu-check"
                                      id="file-menu-check"
                                      value="file-menu"
                                      label="File Menu"
                                      inline
                                      checked={fileMenuSelected} onChange={() => setFileMenuSelected(true)}/>
                            <MDBRadio name="text-menu-check"
                                      id="text-menu-check"
                                      value="text-menu"
                                      label="Text Menu"
                                      inline
                                      checked={!fileMenuSelected} onChange={() => setFileMenuSelected(false)}/>
                            {fileMenuSelected ? (
                                <FileMenuCreateForm handleMenuFileChange={handleMenuFileChange}/>
                            ) : (
                                <TextMenuCreateForm
                                    dishes={dishes}
                                    updateDish={updateDish}
                                    generateNewDishForm={generateNewDishForm}
                                />
                            )}
                        </>
                    )}
                    {type === 'park' && (
                        <>
                            <ParkFacilityCreateForm
                                facilities={facilities}
                                updateFacility={updateFacility}
                                generateNewFacilityForm={generateNewFacilityForm}
                            />
                        </>
                    )}
                    {(type === 'theater' || type === 'gallery' || type === 'museum') && (
                        <>
                            <PosterCreateForm handlePosterFileChange={handlePosterFileChange}/>
                        </>
                    )}
                    <button onClick={registerAttraction} disabled={!validateAttractionData}>Register business</button>
                    <AttractionPreview {...{
                        attractionName,
                        type,
                        phone,
                        website,
                        openTime,
                        closeTime,
                        description,
                        images,
                        currentImageIndexes,
                        handlePrevImage,
                        handleNextImage
                    }} />
                    {(type === 'cafe' || type === 'restaurant') && (
                        (fileMenuFiles ? (
                            <AttractionPreviewFileMenu files={fileMenuFiles}/>
                        ) : (
                            <AttractionPreviewTextMenu {...{dishes}}/>
                        ))
                    )}
                    {type === 'park' && (
                        <AttractionPreviewPark {...{facilities}}/>
                    )}
                    {(type === 'theater' || type === 'gallery' || type === 'museum') && (
                        <AttractionPreviewPoster posterImages={posterImages}/>
                    )}
                </MDBContainer>
            </div>
            <Settings/>
        </div>
    )
}

export default RegisterBusiness

import React from 'react';
import {MDBInput} from 'mdb-react-ui-kit';

const AttractionCreateForm = ({
                                  ownerTelegram, setOwnerTelegram,
                                  attractionName, setAttractionName,
                                  description, setDescription,
                                  city, setCity, street, setStreet,
                                  household, setHousehold, phone,
                                  setPhone, website, setWebsite,
                                  type, setType, openTime,
                                  setOpenTime, closeTime, setCloseTime,
                                  handleImageChange
                              }) => (
    <>
        <MDBInput
            wrapperClass='mb-4'
            placeholder='Your telegram account'
            id='ownerTelegram'
            value={ownerTelegram}
            type='text'
            required
            onChange={(e) => setOwnerTelegram(e.target.value)}
        />
        <MDBInput
            wrapperClass='mb-4'
            placeholder='Attraction Name'
            id='attractionName'
            value={attractionName}
            type='text'
            required
            onChange={(e) => setAttractionName(e.target.value)} />
        <MDBInput
            wrapperClass='mb-4'
            placeholder='Description'
            id='description'
            value={description}
            type='text'
            required
            onChange={(e) => setDescription(e.target.value)} />
        <MDBInput
            wrapperClass='mb-4'
            placeholder='City'
            id='city'
            value={city}
            type='text'
            required
            onChange={(e) => setCity(e.target.value)} />
        <MDBInput
            wrapperClass='mb-4'
            placeholder='Street'
            id='street'
            value={street} type='text'
            required
            onChange={(e) => setStreet(e.target.value)} />
        <MDBInput
            wrapperClass='mb-4'
            placeholder='Household'
            id='household'
            value={household}
            type='text'
            required
            onChange={(e) => setHousehold(e.target.value)} />
        <MDBInput
            wrapperClass='mb-4'
            placeholder='Choose images'
            id='images'
            type='file'
            multiple
            onChange={handleImageChange} />
        <MDBInput
            wrapperClass='mb-4'
            placeholder='Phone'
            id='phone'
            value={phone}
            type='tel'
            required
            onChange={(e) => setPhone(e.target.value)} />
        <MDBInput
            wrapperClass='mb-4'
            placeholder='Website'
            id='website'
            value={website}
            type='url'
            onChange={(e) => setWebsite(e.target.value)} />
        <select className='mb-4' id='type' value={type} required onChange={(e) => setType(e.target.value)}>
            <option value="" disabled>Type</option>
            <option value="museum">Museum</option>
            <option value="gallery">Gallery</option>
            <option value="park">Park</option>
            <option value="religious">Religious</option>
            <option value="cafe">Cafe</option>
            <option value="restaurant">Restaurant</option>
            <option value="theater">Theatre</option>
        </select>
        <MDBInput
            wrapperClass='mb-4'
            placeholder='OpenTime'
            id='openTime'
            value={openTime}
            type='time'
            required
            onChange={(e) => setOpenTime(e.target.value)} />
        <MDBInput
            wrapperClass='mb-4'
            placeholder='CloseTime'
            id='closeTime'
            value={closeTime}
            type='time'
            required
            onChange={(e) => setCloseTime(e.target.value)} />
    </>
);

export default AttractionCreateForm;

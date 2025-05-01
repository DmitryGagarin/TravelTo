import React from 'react';
import {MDBContainer, MDBInput} from 'mdb-react-ui-kit';

const TextMenuCreateForm = ({ dishes, setTextMenuNames, setTextMenuDescriptions, setTextMenuPrices, setTextMenuImages, generateNewDishForm }) => (
    <div>
        {dishes.map((_, index) => (
            <MDBContainer key={index}>
                <MDBInput wrapperClass='mb-4' placeholder='Dish Name' id='dish-name' type='text' required onChange={(e) => setTextMenuNames(Array.from(e.target.value))} />
                <MDBInput wrapperClass='mb-4' placeholder='Dish Description' id='dish-description' type='text' required onChange={(e) => setTextMenuDescriptions(Array.from(e.target.value))} />
                <MDBInput wrapperClass='mb-4' placeholder='Dish Price' id='dish-price' type='text' required onChange={(e) => setTextMenuPrices(Array.from(e.target.value))} />
                <MDBInput wrapperClass='mb-4' placeholder='Dish image' id='dish-image' type='file' onChange={(e) => setTextMenuImages(Array.from(e.target.files))} />
            </MDBContainer>
        ))}
        <button onClick={generateNewDishForm}>Add dish</button>
    </div>
);

export default TextMenuCreateForm;

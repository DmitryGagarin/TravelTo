import {MDBContainer, MDBInput} from "mdb-react-ui-kit";

export const TextMenuCreateForm = ({ dishes, updateDish, generateNewDishForm }) => (
    <div>
        {dishes.map((dish, index) => (
            <MDBContainer key={index}>
                <MDBInput
                    wrapperClass='mb-4'
                    placeholder='Dish Name'
                    value={dish.name}
                    type='text'
                    required
                    onChange={(e) => updateDish(index, 'name', e.target.value)}
                />
                <MDBInput
                    wrapperClass='mb-4'
                    placeholder='Dish Description'
                    value={dish.description}
                    type='text'
                    required
                    onChange={(e) => updateDish(index, 'description', e.target.value)}
                />
                <MDBInput
                    wrapperClass='mb-4'
                    placeholder='Dish Price'
                    value={dish.price}
                    type='text'
                    required
                    onChange={(e) => updateDish(index, 'price', e.target.value)}
                />
                <MDBInput
                    wrapperClass='mb-4'
                    placeholder='Dish Image'
                    type='file'
                    onChange={(e) => updateDish(index, 'image', e.target.files[0])}
                />
            </MDBContainer>
        ))}
        <button type="button" onClick={generateNewDishForm}>Add dish</button>
    </div>
);

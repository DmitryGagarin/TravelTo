import React from 'react';
import {MDBInput} from 'mdb-react-ui-kit';

export const PosterCreateForm = ({ handlePosterFileChange }) => (
    <MDBInput
        wrapperClass='mb-4'
        placeholder='Choose posters images'
        id='poster-images'
        type='file'
        multiple
        accept=".pdf,.jpg,.png,.pdf"
        onChange={handlePosterFileChange}
    />
);
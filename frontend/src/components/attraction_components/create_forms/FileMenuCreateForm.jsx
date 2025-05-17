import React from 'react'
import {MDBInput} from 'mdb-react-ui-kit'

const FileMenuCreateForm = ({ handleMenuFileChange }) => (
    <MDBInput
        wrapperClass='mb-4'
        placeholder='Choose menu files'
        id='menu-files'
        type='file'
        multiple
        onChange={handleMenuFileChange}
        accept=".pdf,.jpg,.png,.pdf"
    />
);

export default FileMenuCreateForm;

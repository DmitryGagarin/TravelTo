import {MDBContainer, MDBInput} from "mdb-react-ui-kit";

export const ParkFacilityCreateForm = ({ facilities, updateFacility, generateNewFacilityForm }) => (
    <div>
        {facilities.map((facility, index) => (
            <MDBContainer key={index}>
                <MDBInput
                    wrapperClass='mb-4'
                    placeholder='Facility Name'
                    value={facility.name}
                    type='text'
                    required
                    onChange={(e) => updateFacility(index, 'name', e.target.value)}
                />
                <MDBInput
                    wrapperClass='mb-4'
                    placeholder='Facility Description'
                    value={facility.description}
                    type='text'
                    required
                    onChange={(e) => updateFacility(index, 'description', e.target.value)}
                />
                <MDBInput
                    wrapperClass='mb-4'
                    placeholder='Open time'
                    value={facility.openTime}
                    type='time'
                    required
                    onChange={(e) => updateFacility(index, 'openTime', e.target.value)}
                />
                <MDBInput
                    wrapperClass='mb-4'
                    placeholder='Close time'
                    value={facility.closeTime}
                    type='time'
                    required
                    onChange={(e) => updateFacility(index, 'closeTime', e.target.value)}
                />
                <MDBInput
                    wrapperClass='mb-4'
                    placeholder='Facility Image'
                    type='file'
                    onChange={(e) => updateFacility(index, 'image', e.target.files[0])}
                />
            </MDBContainer>
        ))}
        <button type="button" onClick={generateNewFacilityForm}>Add facility</button>
    </div>
);

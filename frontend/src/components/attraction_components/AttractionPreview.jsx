import React from 'react';
import {Link} from 'react-router-dom';
import {getAttractionCardStyle} from '../../utils/StyleUtils';

const AttractionPreview = ({
                               attractionName, type, phone, website, openTime, closeTime,
                               description, images, currentImageIndexes, handlePrevImage,
                               handleNextImage
                           }) => (
    <div className="attraction-preview">
        <p style={{textAlign: 'center', fontSize: '32px'}}>Attraction preview</p>
        <div className="attractions-preview-container">
            <div className="cards-container">
                <div key={attractionName} className="attraction-card">
                    <div className="image-container">
                        {images.length > 0 && images[currentImageIndexes[attractionName] || 0] ? (
                            <img
                                src={URL.createObjectURL(images[currentImageIndexes[attractionName] || 0])}
                                alt={attractionName}
                                className="card-image"
                            />
                        ) : (
                            <p>No image selected</p>
                        )}
                        <div className="image-navigation">
                            <button className="image-nav-button left" onClick={() => handlePrevImage(currentImageIndexes[attractionName] || 0, images, attractionName)}>←</button>
                            <button className="image-nav-button right" onClick={() => handleNextImage(currentImageIndexes[attractionName] || 0, images, attractionName)}>→</button>
                        </div>
                    </div>
                    <div className="attraction-data">
                        <div className="attraction-type" style={getAttractionCardStyle(type)}>{type}</div>
                        <div className="contact-info">
                            <p>Website: <Link to={website} target="_blank" rel="noopener noreferrer">{website}</Link></p>
                            <p>Phone: {phone}</p>
                        </div>
                        <div className="name-description">
                            <h5>{attractionName}</h5>
                            <p>{description}</p>
                        </div>
                        <div className="time">
                            <p>From: {openTime}</p>
                            <p>To: {closeTime}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
);

export default AttractionPreview;

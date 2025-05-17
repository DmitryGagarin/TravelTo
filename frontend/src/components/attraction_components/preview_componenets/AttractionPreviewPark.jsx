import React from "react";

const AttractionPreviewPark = ({facilities}) => (
    <div className="attraction-preview">
        <h2 style={{textAlign: "center", fontSize: "32px", marginBottom: "24px"}}>
            Facility Preview
        </h2>

        <div className="facility-items-container">
            {facilities.map((facility, index) => (
                <div key={index} className="facility-item">
                    <div className="facility-item-name">
                        <h3>
                            {facility.name || "Facility Name"}
                        </h3>
                        <div>
                            <span>
                                {`Since ${facility.openTime}`}
                            </span>
                            <span>
                                {`Until ${facility.closeTime}`}
                            </span>
                        </div>
                    </div>
                    <p>
                        {facility.description || "No description provided"}
                    </p>
                    {facility.image && (
                        <div className="facility-item-image">
                            <img
                                src={URL.createObjectURL(facility.image)}
                                alt={facility.name || "Facility image"}
                            />
                        </div>
                    )}
                </div>
            ))}

            {facilities.length === 1 && !facilities[0].name && !facilities[0].description && !facilities[0].price && (
                <p style={{textAlign: "center", color: "#999", fontStyle: "italic"}}>
                    No menu items added yet
                </p>
            )}
        </div>
    </div>
)

export default AttractionPreviewPark
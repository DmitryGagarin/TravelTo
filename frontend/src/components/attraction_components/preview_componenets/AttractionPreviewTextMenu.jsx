import React from "react";

const AttractionPreviewTextMenu = ({dishes}) => (
    <div className="attraction-preview">
        <h2 style={{textAlign: "center", fontSize: "32px", marginBottom: "24px"}}>
            Menu Preview
        </h2>

        <div className="menu-items-container">
            {dishes.map((dish, index) => (
                <div key={index} className="menu-item">
                    <div className="menu-item-name">
                        <h3>
                            {dish.name || "Dish Name"}
                        </h3>
                        <span>
                            {dish.price ? `$${dish.price}` : "$0.00"}
                        </span>
                    </div>
                    <p>
                        {dish.description || "No description provided"}
                    </p>
                    {dish.image && (
                        <div className="menu-item-image">
                            <img
                                src={URL.createObjectURL(dish.image)}
                                alt={dish.name || "Dish image"}
                            />
                        </div>
                    )}
                </div>
            ))}

            {dishes.length === 1 && !dishes[0].name && !dishes[0].description && !dishes[0].price && (
                <p style={{textAlign: "center", color: "#999", fontStyle: "italic"}}>
                    No menu items added yet
                </p>
            )}
        </div>
    </div>
);

export default AttractionPreviewTextMenu;
import {FaRegStar, FaStar} from "react-icons/fa";
import React from "react";

export const getAttractionCardStyle = (type) => {
    switch (type.toLowerCase()) {
        case 'museum':
            return {backgroundColor: 'yellow', color: 'black'}
        case 'gallery':
            return {backgroundColor: 'orange', color: 'black'}
        case 'park':
            return {backgroundColor: 'green', color: 'white'}
        case 'religious':
            return {backgroundColor: 'wheat', color: 'black'}
        case 'cafe':
            return {backgroundColor: 'wheat', color: 'black'}
        case 'restaurant':
            return {backgroundColor: 'pink', color: 'black'}
        default:
            return {backgroundColor: 'lightgray', color: 'black'}
    }
}

export const getAttractionStatusStyle = (status) => {
    switch (status.toLowerCase()) {
        case 'PUBLISHED':
            return { color: 'green' }
        case 'ON_MODERATION':
            return { color: 'red' }
        default:
            return { backgroundColor: 'lightgray', color: 'black' }
    }
}

export const renderStars = (rating) => {
    const stars = []
    for (let i = 1; i <= 5; i++) {
        if (i <= rating) {
            stars.push(<FaStar key={i} className="yellow-rating-star star"/>)
        } else {
            stars.push(<FaRegStar key={i} className="grey-rating-star star"/>)
        }
    }
    return stars;
}
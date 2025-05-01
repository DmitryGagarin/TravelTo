export const validateAttractionData = (
    ownerTelegram,
    attractionName,
    description,
    city,
    street,
    household,
    phone,
    type,
    openTime,
    closeTime
) => {
    return (
        ownerTelegram &&
        attractionName &&
        description &&
        city &&
        street &&
        household &&
        phone &&
        type &&
        openTime &&
        closeTime
    )
}

export const validationTextMenuData = (
    dishes
) => {
    return (
        dishes.names.length === dishes.descriptions.length &&
        dishes.descriptions.length === dishes.prices.length &&
        dishes.prices.length === dishes.images.length
    )
}
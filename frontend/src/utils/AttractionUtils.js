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
    names,
    descriptions,
    prices,
    images
) => {
    return (
        names.length === descriptions.length &&
        descriptions.length === prices.length &&
        prices.length === images.length
    )
}
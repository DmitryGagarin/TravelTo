export const getImageFormat = (format) => {
    const formats = ['png', 'jpeg', 'jpg', 'webp', 'svg']
    if (format) {
        if (formats.includes(format.toLowerCase())) {
            return format.toLowerCase()
        } else {
            return 'jpeg'
        }
    }
}

export const handleNextImage = (index, images) => {
    return (index + 1) % images.length
}

export const handlePrevImage = (index, images) => {
    return (index - 1 + images.length) % images.length
}


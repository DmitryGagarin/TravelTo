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

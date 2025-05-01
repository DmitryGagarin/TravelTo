export const catchError = (error, setError, frontend, errorText) => {
    if (error.response && error.response.data) {
        const errorMessages = error.response.data;
        setError(
            Object.entries(errorMessages)
                .map(([field, message]) => `${field}: ${message}`)
                .join(', ')
        );
    } else {
        if (error.response && error.response.status === 401) {
            window.location.href = `${frontend}/signin`;
        } else {
            setError(errorText);
        }
    }
}

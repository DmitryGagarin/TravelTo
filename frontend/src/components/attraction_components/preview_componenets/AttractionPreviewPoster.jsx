const AttractionPreviewPoster = ({posterImages}) => (
    <div className="attraction-preview">
        <h2>
            Menu Preview
        </h2>
        <div className="poster-items-container">
            {posterImages ? (
                posterImages.map((image, index) => (
                    <div key={index} className="poster-menu-item">
                        {image.type === 'application/pdf' ? (
                            <iframe
                                src={URL.createObjectURL(image)}
                                width="100%"
                                height="600px"
                                style={{border: 'none'}}
                                title={`Menu PDF ${index + 1}`}
                            />
                        ) : (
                            <img
                                src={URL.createObjectURL(image)}
                                alt={image.name}
                                style={{}}
                            />
                        )}
                    </div>
                ))
            ) : (
                <p style={{textAlign: 'center', color: '#999'}}>
                    No menu files uploaded yet
                </p>
            )}
        </div>
    </div>
)

export default AttractionPreviewPoster
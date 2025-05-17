const AttractionPreviewFileMenu = ({files}) => (
    <div className="attraction-preview">
        <h2>
            Menu Preview
        </h2>
        <div className="menu-items-container">
            {files ? (
                files.map((file, index) => (
                    <div key={index} className="file-menu-item">
                        {file.type === 'application/pdf' ? (
                            <iframe
                                src={URL.createObjectURL(file)}
                                width="100%"
                                height="600px"
                                style={{border: 'none'}}
                                title={`Menu PDF ${index + 1}`}
                            />
                        ) : (
                            <img
                                src={URL.createObjectURL(file)}
                                alt={file.name}
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

export default AttractionPreviewFileMenu
import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import Header from './Header'; // Import the Header component

function Home() {

    const history = useNavigate();


    return (
        <div>
            <Header />
        </div>
    );

}

export default Home;

import 'bootstrap/dist/css/bootstrap.min.css';

import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './SignInPage';
import SignupPage from './SignUpPage';
import Home from './Home';
import Account from './Account';

function App() {
    return (
        <div className="App">
            <Router>
                <Routes>
                    <Route path="/" element={<LoginPage/>}/>
                    <Route path="/signup" element={<SignupPage/>}/>
                    <Route path="/home" element={<Home/>}/>
                    <Route path="/settings/profile" element={<Account/>}/>
                </Routes>
            </Router>
        </div>
    );
}

export default App;

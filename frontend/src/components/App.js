import 'bootstrap/dist/css/bootstrap.min.css';

import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';

import LoginPage from './SignInPage';
import SignupPage from './SignUpPage';
import Home from './Home';
import Settings from './Settings';
import Account from './Account'
import RegisterBusiness from './RegisterBusiness'

function App() {
    return (
        <div className="App">
            <Router>
                <Routes>
                    <Route path="/" element={<LoginPage/>}/>
                    <Route path="/signup" element={<SignupPage/>}/>
                    <Route path="/home" element={<Home/>}/>
                    <Route path="/settings" element={<Settings/>}/>
                    <Route path="/settings/account" element={<Account/>}/>
                    <Route path="/settings/business" element={<RegisterBusiness/>}/>
                </Routes>
            </Router>
        </div>
    );
}

export default App;

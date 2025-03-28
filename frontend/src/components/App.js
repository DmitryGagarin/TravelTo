import 'bootstrap/dist/css/bootstrap.min.css';

import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';

import LoginPage from './SignIn';
import SignUpStepOne from './SignUpStepOne';
import SignUpStepTwo from './SignUpStepTwo'
import Home from './Home';
import MyAttractions from './MyAttractions'
import Attractions from './Attractions'
import Attraction from './Attraction'
import Settings from './Settings';
import Account from './Account'
import RegisterBusiness from './RegisterBusiness'
import Liked from './Liked'
import AdminModeration from './AdminModeration'

function App() {
    return (
        <div className="App">
            <Router>
                <Routes>
                    <Route path="/signin" element={<LoginPage/>}/>
                    {/*<Route path="/home" element={<Home/>}/>*/}
                    <Route path="/likes" element={<Liked/>}/>
                    <Route path="/signup" element={<SignUpStepOne/>}/>
                    <Route path="/signup/name" element={<SignUpStepTwo/>}/>
                    <Route path="/attractions" element={<Attractions/>}/>
                    <Route path="/attraction/:name" element={<Attraction/>}/>
                    <Route path="/settings" element={<Settings/>}/>
                    <Route path="/settings/account" element={<Account/>}/>
                    <Route path="/settings/business" element={<RegisterBusiness/>}/>
                    <Route path="/settings/my" element={<MyAttractions/>}/>
                    <Route path="/settings/admin/moderation/:type" element={<AdminModeration/>}/>
                </Routes>
            </Router>
        </div>
    );
}

export default App;

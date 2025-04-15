import 'bootstrap/dist/css/bootstrap.min.css'

import React from 'react'
import {BrowserRouter as Router, Navigate, Route, Routes} from 'react-router-dom'

import LoginPage from './SignIn'
import SignUpStepOne from './SignUpStepOne'
import SignUpStepTwo from './SignUpStepTwo'
import MyAttractions from './MyAttractions'
import Attractions from './Attractions'
import Attraction from './Attraction'
import Settings from './Settings'
import Account from './Account'
import RegisterBusiness from './RegisterBusiness'
import Liked from './Liked'
import AdminModeration from './AdminModeration'
import EditAttraction from "./EditAttraction.tsx"
import ResetPassword from "./ResetPassword"
import VerifyAccount from "./VerifyAccount"
import VerificationCompleted from "./VerificationCompleted"
import ResetPasswordCompletion from "./ResetPasswordCompletion"

function App() {
    return (
        <div className="App">
            <Router>
                <Routes>
                    <Route path="/" element={<Navigate to="/signin"/>} />
                    <Route path="/signin" element={<LoginPage/>}/>
                    <Route path="/reset-password" element={<ResetPassword/>}/>
                    <Route path="/reset-password-completion" element={<ResetPasswordCompletion/>}/>
                    {/*<Route path="/home" element={<Home/>}/>*/}
                    <Route path="/signup" element={<SignUpStepOne/>}/>
                    <Route path="/signup/name" element={<SignUpStepTwo/>}/>
                    <Route path="/verify/:email" element={<VerifyAccount/>}/>
                    <Route path="/verification-completed" element={<VerificationCompleted/>} />
                    <Route path="/likes" element={<Liked/>}/>
                    <Route path="/attractions" element={<Attractions/>}/>
                    <Route path="/attraction/:name" element={<Attraction/>}/>
                    <Route path="/attraction/edit/:name" element={<EditAttraction/>}/>
                    <Route path="/settings" element={<Settings/>}/>
                    <Route path="/settings/account" element={<Account/>}/>
                    <Route path="/settings/business" element={<RegisterBusiness/>}/>
                    <Route path="/settings/my" element={<MyAttractions/>}/>
                    <Route path="/settings/admin/moderation/:type" element={<AdminModeration/>}/>
                </Routes>
            </Router>
        </div>
    )
}

export default App

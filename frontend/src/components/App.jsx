import 'bootstrap/dist/css/bootstrap.min.css'

import React from 'react'
import {BrowserRouter as Router, Navigate, Route, Routes} from 'react-router-dom'

import LoginPage from './user_components/SignIn'
import SignUpStepOne from './user_components/SignUpStepOne'
import SignUpStepTwo from './user_components/SignUpStepTwo'
import MyAttractions from './attraction_components/MyAttractions'
import Attractions from './attraction_components/Attractions'
import Attraction from './attraction_components/Attraction'
import Settings from './Settings'
import Account from './user_components/Account'
import RegisterBusiness from './attraction_components/RegisterBusiness'
import Liked from './Liked'
import AdminModeration from './AdminModeration'
import EditAttraction from "./attraction_components/EditAttraction.tsx"
import ResetPassword from "./user_components/ResetPassword"
import VerifyAccount from "./user_components/VerifyAccount"
import VerificationCompleted from "./user_components/VerificationCompleted"
import ResetPasswordCompletion from "./user_components/ResetPasswordCompletion"
import UsabilityQuestionnaire from "./UsabilityQuestionnaire";
import Donate from "./Donate";

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
                    <Route path="/questionnaire/usability" element={<UsabilityQuestionnaire/>}/>
                    <Route path="/donate" element={<Donate/>}/>
                </Routes>
            </Router>
        </div>
    )
}

export default App

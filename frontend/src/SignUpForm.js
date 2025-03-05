import React, { useEffect, useState } from 'react';

function SignUpForm() {
    return (
        <div>
            <form action={"http://localhost:8080/signup"} method={"POST"}>
                <input type={"text"} placeholder={"username"}/>
                <input type={"email"} placeholder={"email"}/>
                <input type={"password"} placeholder={"password"}/>
                <input type={"password"} placeholder={"repeat password"}/>
                <button type={"submit"}/>
            </form>
        </div>
    );
}

export default SignUpForm;
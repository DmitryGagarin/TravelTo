import React, { useEffect, useState } from 'react';

function SignInForm() {
    return (
        <div>
            <form action={"http://localhost:8080/signin"} method={"POST"}>
                <input type={"text"} placeholder={"username"}/>
                <input type={"password"} placeholder={"password"}/>
                <button type={"submit"}/>
            </form>
        </div>
    );
}

export default SignInForm;
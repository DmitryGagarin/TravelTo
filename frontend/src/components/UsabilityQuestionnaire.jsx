import React, {useState} from "react"
import axios from "axios";
import {MDBBtn, MDBContainer, MDBInput} from "mdb-react-ui-kit";
import Settings from "./Settings";

function UsabilityQuestionnaire() {
    const BACKEND = process.env.REACT_APP_BACKEND_URL

    const [q1, setQ1] = useState(0)
    const [q2, setQ2] = useState(0)
    const [q3, setQ3] = useState(0)
    const [q4, setQ4] = useState(0)
    const [q5, setQ5] = useState(0)
    const [q6, setQ6] = useState(0)
    const [q7, setQ7] = useState(0)
    const [q8, setQ8] = useState(0)
    const [q9, setQ9] = useState(0)
    const [q10, setQ10] = useState(0)

    const [error, setError] = useState('')

    const handleSaveAnswers = async () => {
        // TODO: переделать эту порнуху
        if (
            (q1 >= 0 && q1 <= 5) &&
            (q2 >= 0 && q2 <= 5) &&
            (q3 >= 0 && q3 <= 5) &&
            (q4 >= 0 && q4 <= 5) &&
            (q5 >= 0 && q5 <= 5) &&
            (q6 >= 0 && q6 <= 5) &&
            (q7 >= 0 && q7 <= 5) &&
            (q8 >= 0 && q8 <= 5) &&
            (q9 >= 0 && q9 <= 5) &&
            (q10 >= 0 && q10 <= 5)
        ) {
            try {
                await axios.post(`${BACKEND}/questionnaire/mark-usability`, {
                        q1,
                        q2,
                        q3,
                        q4,
                        q5,
                        q6,
                        q7,
                        q8,
                        q9,
                        q10
                    }, {
                        headers: {
                            'Authorization': `Bearer ${JSON.parse(localStorage.getItem('user'))?.accessToken}`
                        }
                    }
                )
            } catch (error) {
                alert(error)
            }
        } else {
            setError("All answers should in range from 0 to 5")
        }
    }

    return (
        <div className="usability-main-container">
            <div className="usability-container">
                <MDBContainer>
                {error && <p className="text-danger">{error}</p>}
                    <label>1. I think that I would like to use the system frequently</label>
                    <MDBInput
                        placeholder="rate from 0 to 5"
                        type="number"
                        name="q1"
                        min="0"
                        max="5"
                        onChange={(e) => setQ1(e.target.value)}
                        required
                    />
                    <label>2. I found the system unnecessarily complex</label>
                    <MDBInput
                        placeholder="rate from 0 to 5"
                        type="number"
                        min="0"
                        max="5"
                        onChange={(e) => setQ2(e.target.value)}
                        required
                    />
                    <label>3. I thought the system was easy to use</label>
                    <MDBInput
                        placeholder="rate from 0 to 5"
                        type="number"
                        min="0"
                        max="5"
                        onChange={(e) => setQ3(e.target.value)}
                        required
                    />
                    <label>4. I think that I would need the support of a technical person to use this system</label>
                    <MDBInput
                        placeholder="rate from 0 to 5"
                        type="number"
                        min="0"
                        max="5"
                        onChange={(e) => setQ4(e.target.value)}
                        required
                    />
                    <label>5. I found the various functions of this system were well integrated</label>
                    <MDBInput
                        placeholder="rate from 0 to 5"
                        type="number"
                        min="0"
                        max="5"
                        onChange={(e) => setQ5(e.target.value)}
                        required
                    />
                    <label>6. I thought there was too much inconsistency in this system</label>
                    <MDBInput
                        placeholder="rate from 0 to 5"
                        type="number"
                        min="0"
                        max="5"
                        onChange={(e) => setQ6(e.target.value)}
                        required
                    />
                    <label>7. I would imagine that most people would learn to use this system very quickly</label>
                    <MDBInput
                        placeholder="rate from 0 to 5"
                        type="number"
                        min="0"
                        max="5"
                        onChange={(e) => setQ7(e.target.value)}
                        required
                    />
                    <label>8. I found the system very cumbersome to use</label>
                    <MDBInput
                        placeholder="rate from 0 to 5"
                        type="number"
                        min="0"
                        max="5"
                        onChange={(e) => setQ8(e.target.value)}
                        required
                    />
                    <label>9. I felt very confident using the system</label>
                    <MDBInput
                        placeholder="rate from 0 to 5"
                        type="number"
                        min="0"
                        max="5"
                        onChange={(e) => setQ9(e.target.value)}
                        required
                    />
                    <label>10. I needed to learn a lot of things before I could get going with this system</label>
                    <MDBInput
                        placeholder="rate from 0 to 5"
                        type="number"
                        min="0"
                        max="5"
                        onChange={(e) => setQ10(e.target.value)}
                        required
                    />
                    <MDBBtn
                        color="success"
                        onClick={() => handleSaveAnswers()}
                    >
                        Save answers
                    </MDBBtn>
                </MDBContainer>
            </div>
            <Settings/>
        </div>
    )
}

export default UsabilityQuestionnaire
import {useState} from "react"
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

    const handleSaveAnswers = async () => {
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
                        'Authorization': `${JSON.parse(localStorage.getItem('user'))?.accessToken}`
                    }
                }
            )
        } catch (error) {
            alert(error)
        }
    }

    return (
        <div>
            <div>
                <MDBContainer>
                    <MDBInput
                        placeholder="q1"
                        type="number"
                        min="0"
                        max="5"
                        value={q1}
                        onChange={(e) => setQ1(e.target.value)}
                        required
                    />
                    <MDBInput
                        placeholder="q2"
                        type="number"
                        min="0"
                        max="5"
                        value={q2}
                        onChange={(e) => setQ2(e.target.value)}
                        required
                    />
                    <MDBInput
                        placeholder="q3"
                        type="number"
                        min="0"
                        max="5"
                        value={q3}
                        onChange={(e) => setQ3(e.target.value)}
                        required
                    />
                    <MDBInput
                        placeholder="q4"
                        type="number"
                        min="0"
                        max="5"
                        value={q4}
                        onChange={(e) => setQ4(e.target.value)}
                        required
                    />
                    <MDBInput
                        placeholder="q5"
                        type="number"
                        min="0"
                        max="5"
                        value={q5}
                        onChange={(e) => setQ5(e.target.value)}
                        required
                    />
                    <MDBInput
                        placeholder="q6"
                        type="number"
                        min="0"
                        max="5"
                        value={q6}
                        onChange={(e) => setQ6(e.target.value)}
                        required
                    />
                    <MDBInput
                        placeholder="q7"
                        type="number"
                        min="0"
                        max="5"
                        value={q7}
                        onChange={(e) => setQ7(e.target.value)}
                        required
                    />
                    <MDBInput
                        placeholder="q8"
                        type="number"
                        min="0"
                        max="5"
                        value={q8}
                        onChange={(e) => setQ8(e.target.value)}
                        required
                    />
                    <MDBInput
                        placeholder="q9"
                        type="number"
                        min="0"
                        max="5"
                        value={q9}
                        onChange={(e) => setQ9(e.target.value)}
                        required
                    />
                    <MDBInput
                        placeholder="q10"
                        type="number"
                        min="0"
                        max="5"
                        value={q10}
                        onChange={(e) => setQ10(e.target.value)}
                        required
                    />
                    <MDBBtn
                        color="success"
                        onClick={() => handleSaveAnswers()}
                    />
                </MDBContainer>
            </div>
            <Settings/>
        </div>
    )
}

export default UsabilityQuestionnaire
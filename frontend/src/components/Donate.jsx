import {Link} from "react-router-dom";
import Settings from "./Settings";

function Donate() {
    return (
        <div>
            <div className="donation-main-container">
                <h2>
                    If you like that service, you can donate to creators
                </h2>
                <div>
                    <Link to={"https://www.donationalerts.com/r/travelto"}>Donation alerts</Link>
                </div>
                <div className="donation-qr-code">
                    <img
                        src="https://static.donationalerts.ru/uploads/qr/11733569/qr_73495396fa5075d4db86576b1aaf258a.png"
                        alt={"qr code"}
                    />
                </div>
            </div>
            <Settings/>
        </div>
    )
}

export default Donate
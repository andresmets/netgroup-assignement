import { useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';
import './CommonStyles.css';
import type {IError} from "../types/Types";

function BookingPage() {
    const { id } = useParams();
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [personalCode, setPersonalCode] = useState('');
    const [error, setError] = useState<IError|null>(null); // State to manage error messages
    const history = useNavigate(); // Get the history object for redirection

    const handleBooking = async () => {
        try {
            // Check for empty fields
            if (!firstName || !lastName || !personalCode) {
                const  validationError: IError = { error: 'Please fill in all fields.' };
                setError(validationError);
                return;
            }

            await axios.post('http://localhost:8081/book', {
                id,
                firstName,
                lastName,
                personalCode
            },
            {withCredentials: true});
            history('/');
        } catch (error:any) {
            // Handle signup error
            setError({ error: error.message });
        }
    };

    return (
        <div>
            <div className="user-form">
                    <h2 className="text">Register to event</h2>
                    {/* Render error message if exists */}
                    {error && <p className="error">{error.error}</p>}
                    <div>
                        <input id='firstName' placeholder={"First Name"} value={firstName} type='text'
                              onChange={(e) => setFirstName(e.target.value)}/>
                    </div>
                    <div>
                        <input placeholder={"Last Name"} id='lastName' value={lastName}
                              type='text'
                              onChange={(e) => setLastName(e.target.value)}/>
                    </div>
                    <div>
                        <input placeholder={"Personal Code"} id='personalCode' value={personalCode}
                              type='text'
                              onChange={(e) => setPersonalCode(e.target.value)}/>
                    </div>
                    <div>
                        <button className="button"
                                onClick={handleBooking}>Register
                        </button>
                    </div>
                    <div>
                         <Link to="/">
                            <button className="button">View all events</button>
                          </Link>
                    </div>
            </div>
        </div>
    );
}

export default BookingPage;
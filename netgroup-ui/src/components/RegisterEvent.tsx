import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './CommonStyles.css';
import type {IError} from "../types/Types";

function RegisterPage() {
    const [eventName, setEventName] = useState('');
    const [eventDate, setEventDate] = useState('');
    const [amount, setAmount] = useState('');
    const [error, setError] = useState<IError|null>(null); // State to manage error messages
    const [registeredEvent, setRegisteredEvent] = useState('');
    const history = useNavigate();

    const handleSignup = async () => {
        try {
            setError({ error: '' });
            setRegisteredEvent('');
            // Check for empty fields
            if (!eventName || !eventDate || !amount) {
                const  validationError: IError = { error: 'Please fill in all fields.' };
                setError(validationError);
                return;
            }

            const response = await axios.post('http://localhost:8081/add/event', {
                eventName,
                eventDate,
                amount
            },
            {withCredentials: true});
            setAmount('');
            setEventDate('');
            setEventName('');
            setRegisteredEvent(response.data.eventName);
            history('/register');
        } catch (error:any) {
            setError({ error: error.message });
        }
    };

    return (
        <div>
            <div className="user-form">
                    <h2 className="text">Register event</h2>
                    {/* Render error message if exists */}
                    {error && <p className="error">{error.error}</p>}
                    {/* Render error message if exists */}
                    {registeredEvent && <p className="text">{registeredEvent} added</p>}
                    <div>
                        <input id='fullName' placeholder={"Event Name"} value={eventName} type='text'
                              onChange={(e) => setEventName(e.target.value)}/>
                    </div>
                    <div>
                        <input placeholder='Event date/time (yyyy/MM/dd HH:mm)' id='date' value={eventDate}
                              type='text'
                              onChange={(e) => setEventDate(e.target.value)}/>
                    </div>
                    <div>
                        <input placeholder='Maximum amount of people' id='amount' value={amount}
                              type='text'
                              onChange={(e) => setAmount(e.target.value)}/>
                    </div>
                    <div>
                        <button className="button"
                                style={{height: '40px', width: '100%'}}
                                onClick={handleSignup}>Register
                        </button>
                    </div>
                    <div>
                         <Link to="/">
                            <button className="button"
                                    style={{height: '40px', width: '100%'}}>View all events</button>
                          </Link>
                    </div>
            </div>
        </div>
    );
}

export default RegisterPage;
import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import type {IError} from "../types/Types";

import './Dashboard.css';
import './CommonStyles.css';

function Dashboard() {
    const [events, setEvents] = useState([]);
    const [error, setError] = useState<IError|null>(null);

    useEffect(() => {
        getEvents();
    }, [])
    const getEvents = async () => {
        try {
            const response = await axios.get('http://localhost:8081/get/events');
            setEvents(response.data);
        } catch (error:any) {
            console.error('Event loading failed:', error.response ? error.response.data : error.message);
            setError(error.response ? error.response.data : error.message);
        }
    };

   const listItems = events.map((event:any) =>
       <Link to={'booking/' + event.id}>
       <li key={event.id}>
         <p>
           <b>{event.eventName}:</b>
           {' ' + event.eventDate + ' '}
           {' ' + event.amount + ' '}
         </p>
       </li>
       </Link>
     );
     return <div className="user-form">
                {error && <p className="error">{error.error}</p>}
                <div className="top right">
                    <Link to="/login">
                        <button className="button">Login</button>
                    </Link>
                </div>
                <ul className="text-left">{listItems}</ul>
            </div>;
}

export default Dashboard;
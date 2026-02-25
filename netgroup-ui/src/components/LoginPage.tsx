import { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import './CommonStyles.css';
import type {IError} from "../types/Types";

function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState<IError|null>(null); // State to manage error messages
    const history = useNavigate();

    const handleLogin = async () => {
        try {
            if (!username || !password) {
                setError({ error: 'Please enter both username and password.'});
                return;
            }
            await axios.post('http://localhost:8081/auth/signin', { username, password });
            history('/register');
        } catch (error:any) {
            setError({ error: error.message });
        }
    };

    return (
        <div>
            <div className="user-form">
                    <h2 className="text-center">Login Page</h2>
                    {error && <p className="error">{error.error}</p>} {/* Render error message if exists */}

                    <input placeholder='email' id='username' value={username} type='email' onChange={(e) => setUsername(e.target.value)} />
                    <input placeholder='Password' id='password' type='password' value={password} onChange={(e) => setPassword(e.target.value)} />
                    <button className="button" onClick={handleLogin}>Sign in</button>

            <div>
                 <Link to="/">
                    <button className="button">View all events</button>
                  </Link>
            </div>
            </div>
        </div>
    );
}

export default LoginPage;
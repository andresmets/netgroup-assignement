import { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import './CommonStyles.css';

function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const history = useNavigate();

    const handleLogin = async () => {
        try {
            if (!username || !password) {
                setError('Please enter both username and password.');
                return;
            }
            await axios.post('http://localhost:8081/auth/signin', { username, password });
            history('/register');
        } catch (error:any) {
            console.error('Login failed:', error.response ? error.response.data : error.message);
            setError('Invalid username or password.');
        }
    };

    return (
        <div>
            <div className="user-form">
                    <h2 className="text-center">Login Page</h2>
                    <input placeholder='email' id='username' value={username} type='email' onChange={(e) => setUsername(e.target.value)} />
                    <input placeholder='Password' id='password' type='password' value={password} onChange={(e) => setPassword(e.target.value)} />
                    {error && <p className="error">{error}</p>} {/* Render error message if exists */}

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
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import LoginPage from './components/LoginPage';
import RegisterEvent from './components/RegisterEvent';
import Dashboard from "./components/Dashboard";
import EventBooking from "./components/EventBooking"

function App() {
  return (
      <div className="App">
      <Router>

            <Routes>
                <Route path="/login" element={<LoginPage/>} />
                <Route path="/register" element={ <RegisterEvent/>} />
                <Route path = "/" element={<Dashboard/>}/>
                <Route path = "/booking/:id" element={<EventBooking/>}/>
            </Routes>

      </Router>
      </div>
  );
}

export default App;
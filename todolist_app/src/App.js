import './App.css';
import { Route, Routes } from 'react-router-dom';
import { Login } from './Users/Login';
import { UserRegistration } from './Users/UserRegistration';
import ResendToken from './Users/ResendToken';
import Dashboard from './Dashboard/dashboard';
import PrivateRoute from './PrivateRoute';




function App() {
  const token = sessionStorage.getItem("token");
  return (


    <Routes>
      <Route path='/' element={<Login />}></Route>
      <Route path='/Register' element={<UserRegistration />} ></Route>
      
        <Route path='/Dashboard' element={<PrivateRoute  element={<Dashboard />}/>}></Route> 
      
      <Route path='/ResendToken' element={<ResendToken />}></Route>
    </Routes>

  );
}

export default App;

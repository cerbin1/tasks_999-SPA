import React, { useState } from 'react';
import logo from '../logo.svg';
import { useNavigate } from 'react-router-dom';

function Login(props) {
  const [user, setUser] = useState({})

  const apiUrl = 'http://localhost:8080/auth/';

  const navigate = useNavigate()

  function register(event) {
  }

  function handleChange(event) {
    setUser({
      ...user,
      [event.target.id]: event.target.value
    })
  }

  function login(event) {
    event.preventDefault();

    fetch(apiUrl + 'login', {
      method: 'POST',
      body: JSON.stringify(user),
      headers: {
        "Content-type": "application/json; charset=UTF-8"
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        console.log(data)
        localStorage.setItem("token", data.token);
        navigate('/list')
      })
      .catch(error => {
        alert(error)
      });
  }

  return <div>
    <img src={logo} className="App-logo" alt="logo" />
    <form onSubmit={login}>
      <div className="form-group">
        <label htmlFor="username">Username</label>
        <input className="form-control" id="username" value={user.username} placeholder="Enter username" onChange={handleChange} />
      </div>
      <div className="form-group">
        <label htmlFor="password">Password</label>
        <input type="password" className="form-control" id="password" value={user.password} placeholder="Password" onChange={handleChange} />
      </div>
      <button type="submit" className="btn btn-primary">Login</button>
    </form>
  </div>
}

export default Login;

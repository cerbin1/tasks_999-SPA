import React, { useState } from 'react';
import logo from '../logo.svg';
import { useNavigate } from 'react-router-dom';

function Login(props) {
  const [user, setUser] = useState({ username: '', password: '' });
  const [errors, setErrors] = useState();

  const apiUrl = 'http://localhost:8080/auth/';

  const navigate = useNavigate()

  function handleChange(event) {
    setUser({
      ...user,
      [event.target.id]: event.target.value
    })
  }
  function validateValues() {
    let errors = {};
    if (user.username.length == 0) {
      errors.username = true;
    }
    if (user.password.length == 0) {
      errors.password = true;
    }
    return errors;
  };

  function login(event) {
    event.preventDefault();

    const errorValues = validateValues();
    setErrors(errorValues)
    if (Object.keys(errorValues).length !== 0) {
      return;
    }

    fetch(apiUrl + 'login', {
      method: 'POST',
      body: JSON.stringify(user),
      headers: {
        "Content-type": "application/json; charset=UTF-8"
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('There was a problem with login. Did you activate your account? (link was sent by email).')
        }
        return response.json();
      })
      .then(data => {
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
        {errors && errors.username &&
          <div className="alert alert-danger" role="alert">
            You must enter username before submitting.
          </div>
        }
      </div>
      <div className="form-group">
        <label htmlFor="password">Password</label>
        <input type="password" className="form-control" id="password" value={user.password} placeholder="Password" onChange={handleChange} />
        {errors && errors.password &&
          <div className="alert alert-danger" role="alert">
            You must enter username password submitting.
          </div>
        }
      </div>
      <button type="submit" className="btn btn-primary">Login</button>
    </form>
  </div>
}

export default Login;

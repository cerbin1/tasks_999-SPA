import React, { useState } from 'react';
import logo from '../logo.svg';
import { useNavigate } from 'react-router-dom';

function Register(props) {
  const [user, setUser] = useState({ email: '', username: '', password: '', name: '', surname: '' })
  const [errors, setErrors] = useState();

  const apiUrl = 'http://localhost:8080/auth/';

  const navigate = useNavigate()

  function validateValues() {
    let errors = {};
    if (user.email.length == 0) {
      errors.email = true;
    }
    if (user.username.length == 0) {
      errors.username = true;
    }
    if (user.password.length == 0) {
      errors.password = true;
    }
    if (user.name.length == 0) {
      errors.name = true;
    }
    if (user.surname.length == 0) {
      errors.surname = true;
    }
    return errors;
  };

  function register(event) {
    event.preventDefault();

    const errorValues = validateValues();
    setErrors(errorValues)
    if (Object.keys(errorValues).length !== 0) {
      return;
    }

    fetch(apiUrl + 'register', {
      method: 'POST',
      body: JSON.stringify(user),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Could not register user');
        }
        return response.json();
      })
      .then(data => {
        navigate('/login')
      })
      .catch(error => {
        alert(error)
      });
  }

  function handleChange(event) {
    setUser({
      ...user,
      [event.target.id]: event.target.value
    })
  }

  return <div>
    <img src={logo} className="App-logo" alt="logo" />
    <form onSubmit={register}>
      <div className="form-group">
        <label htmlFor="email">Email</label>
        <input type="email" className="form-control" id="email" value={user.email} placeholder="Enter email" onChange={handleChange} />
        {errors && errors.email &&
          <div className="alert alert-danger" role="alert">
            You must enter email before submitting.
          </div>
        }
      </div>
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
            You must enter password before submitting.
          </div>
        }
      </div>
      <div className="form-group">
        <label htmlFor="name">Name</label>
        <input className="form-control" id="name" value={user.name} placeholder="Enter name" onChange={handleChange} />
        {errors && errors.name &&
          <div className="alert alert-danger" role="alert">
            You must enter name before submitting.
          </div>
        }
      </div>
      <div className="form-group">
        <label htmlFor="surname">Surname</label>
        <input className="form-control" id="surname" value={user.surname} placeholder="Enter surname" onChange={handleChange} />
        {errors && errors.surname &&
          <div className="alert alert-danger" role="alert">
            You must enter surname before submitting.
          </div>
        }
      </div>
      <button type="submit" className="btn btn-primary">Register</button>
    </form>
  </div>
}

export default Register;

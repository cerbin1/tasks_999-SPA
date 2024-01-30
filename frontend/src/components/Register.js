import React from 'react';
import logo from '../logo.svg';

function Register(props) {

  function register(event) {
    event.preventDefault();
    alert('register')
  }

  return <div>
    <img src={logo} className="App-logo" alt="logo" />
    <form onSubmit={register}>
      <div class="form-group">
        <label for="email">Email</label>
        <input type="email" class="form-control" id="email" placeholder="Enter email" />
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" class="form-control" id="password" placeholder="Password" />
      </div>
      <button type="submit" class="btn btn-primary">Register</button>
    </form>
  </div>
}

export default Register;

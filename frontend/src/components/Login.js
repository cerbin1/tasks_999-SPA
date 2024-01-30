import React from 'react';
import logo from '../logo.svg';

function Login(props) {

  function login(event) {
    event.preventDefault();
    alert('login')
  }

  return <div>
    <img src={logo} className="App-logo" alt="logo" />
    <form onSubmit={login}>
      <div class="form-group">
        <label for="email">Email</label>
        <input type="email" class="form-control" id="email" placeholder="Enter email" />
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" class="form-control" id="password" placeholder="Password" />
      </div>
      <button type="submit" class="btn btn-primary">Login</button>
    </form>
  </div>
}

export default Login;

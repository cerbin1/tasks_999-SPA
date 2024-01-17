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
      <input name="email" placeholder="Email" />
      <input name="password" placeholder="Password" />
      <button type="submit" >Login</button>
    </form>
  </div>
}

export default Login;

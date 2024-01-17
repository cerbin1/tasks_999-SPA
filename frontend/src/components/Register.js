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
      <input name="email" placeholder="Email" />
      <input name="password" placeholder="Password" />
      <button type="submit" >Register</button>
    </form>
  </div>
}

export default Register;

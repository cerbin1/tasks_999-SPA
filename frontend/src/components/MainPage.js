import { BrowserRouter, Route, Routes } from 'react-router-dom';
import React, { useState } from 'react'

import Login from './Login'
import Register from './Register'
import Tasks from './Tasks'
import CreateTask from './CreateTask'
import EditTask from './EditTask'
import LoggedInUserLayout from './LoggedInUserLayout'
import LoggedOutUserLayout from './LoggedOutUserLayout'

export const UserContext = React.createContext(null);

function MainPage(props) {
  const [token] = useState(localStorage.getItem('token'));
  let content;
  const isLogged = token;

  if (isLogged) {
    content =
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<LoggedInUserLayout />} >
            <Route path="list" component={<Tasks />} />
            <Route path="create" element={<CreateTask />} />
            <Route path="edit" element={<EditTask />} />
          </Route>
        </Routes>
      </BrowserRouter>
  } else {
    content = <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoggedOutUserLayout />} >
          <Route path="login" element={<Login />} />
          <Route path="register" element={<Register />} />
        </Route>
      </Routes>
    </BrowserRouter>
  }


  return (content);
}

export default MainPage;

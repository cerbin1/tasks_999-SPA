import { BrowserRouter, Route, Routes } from 'react-router-dom';
import React, { useState } from 'react'

import Login from './Login'
import Register from './Register'
import Tasks from './Tasks'
import MyTasks from './MyTasks'
import CreateTask from './CreateTask'
import EditTask from './EditTask'
import TaskDetails from './TaskDetails'
import LoggedInUserLayout from './LoggedInUserLayout'
import LoggedOutUserLayout from './LoggedOutUserLayout'
import AdminPanelUserList from './AdminPanelUserList'
import Notifications from './Notifications'

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
            <Route path="list" element={<Tasks />} />
            <Route path="create" element={<CreateTask />} />
            <Route path="myList" element={<MyTasks />} />
            <Route path="edit" element={<EditTask />} />
            <Route path="details" element={<TaskDetails />} />
            <Route path="admin" element={<AdminPanelUserList />} />
            <Route path="notifications" element={<Notifications />} />
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

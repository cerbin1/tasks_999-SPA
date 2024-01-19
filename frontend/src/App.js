
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import './App.css';
import Login from './components/Login'
import Register from './components/Register'
import MainPage from './components/MainPage'
import Tasks from './components/Tasks'
import CreateTask from './components/CreateTask'
import EditTask from './components/EditTask'
import Layout from './components/Layout'

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route index element={<MainPage />} />
            <Route path="login" element={<Login />} />
            <Route path="register" element={<Register />} />
            <Route path="list" element={<Tasks />} />
            <Route path="create" element={<CreateTask />} />
            <Route path="edit" element={<EditTask />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;

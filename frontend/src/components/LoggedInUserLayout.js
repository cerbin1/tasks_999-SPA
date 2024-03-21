import { useState, useEffect } from "react";
import { Outlet, Link, useNavigate } from "react-router-dom";

const LoggedInUserLayout = () => {
  const [notificationsCount, setNotificationsCount] = useState(0);

  const apiUrl = 'http://localhost:8080/api/';

  const navigate = useNavigate()

  function handleLogoutButton(event) {
    event.preventDefault();

    localStorage.removeItem("token");
    navigate("/");
  }


  useEffect(() => {
    fetch(apiUrl + 'notifications/count?', {
      headers: {
        "Authorization": `Bearer ` + localStorage.getItem('token'),
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        setNotificationsCount(data)
      })
      .catch(error => {
        alert(error)
      });

  }, []);

  return (
    <>
      <nav className="navbar navbar-expand-md navbar-light bg-light">
        <a className="navbar-brand" href="#">Navbar</a>
        <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav">
            <li className="nav-item active">
              <Link className="nav-link" to="/">Main Page</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/list">List tasks</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/create">Create task</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/myList">My tasks</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/notifications">Notifications ({notificationsCount})</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/admin">User list</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/stats">Stats</Link>
            </li>
            <button type="button" className="btn btn-warning" onClick={handleLogoutButton}>Logout</button>
          </ul>
        </div>
      </nav>

      <Outlet />
    </>
  )
};

export default LoggedInUserLayout;

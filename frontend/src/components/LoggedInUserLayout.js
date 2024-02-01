import { Outlet, Link, useNavigate } from "react-router-dom";

const LoggedInUserLayout = () => {
  const navigate = useNavigate()

  function handleLogoutButton(event) {
    event.preventDefault();

    localStorage.removeItem("token");
    navigate("/");
  }

  return (
    <>
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <a className="navbar-brand" href="#">Navbar</a>
        <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav">
            <li className="nav-item active">
              <a className="nav-link" href="#"><Link to="/">Main Page</Link></a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#"><Link to="/list">List tasks</Link></a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#"><Link to="/create">Create task</Link></a>
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

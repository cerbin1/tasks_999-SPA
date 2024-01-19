import { Outlet, Link } from "react-router-dom";

const Layout = () => {
  return (
    <>
      <nav>
        <p>
          <Link to="/">Main Page</Link>
        </p>
        <p>
          <Link to="/login">Login</Link>
        </p>
        <p>
          <Link to="/register">Register</Link>
        </p>
        <p>
          <Link to="/list">List tasks</Link>
        </p>
        <p>
          <Link to="/create">Create task</Link>
        </p>
        <p>
          <Link to="/edit">Edit task</Link>
        </p>
      </nav>

      <Outlet />
    </>
  )
};

export default Layout;
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom'


function Tasks(props) {
  const [data, setData] = useState([])



  const apiUrl = 'http://localhost:8080/api/users';

  useEffect(() => {
    loadUsers();
  }, []);

  function loadUsers() {
    fetch(apiUrl + '/admin', {
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
        setData(data);
      })
      .catch(error => {
        alert(error);
      });
  }

  function handleRemove(id) {
    fetch(apiUrl + '/' + id, {
      method: 'DELETE',
      headers: {
        "Authorization": `Bearer ` + localStorage.getItem('token'),
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        loadUsers();
      })
      .catch(error => {
        alert(error)
      });
  }

  const users = data.map((user) =>
    <tr key={user.id}>
      <th scope="row">{user.id}</th>
      <td>{user.email}</td>
      <td>{user.username}</td>
      <td>{user.name}</td>
      <td>{user.surname}</td>
      <td>
        <button type="button" className="btn btn-danger" onClick={() => handleRemove(user.id)}>Remove</button>
      </td>
    </tr>
  );

  return <div>

    <table className="table">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Email</th>
          <th scope="col">Login</th>
          <th scope="col">Name</th>
          <th scope="col">Surname</th>
          <th scope="col">Remove</th>
        </tr>
      </thead>
      <tbody>
        {users}
      </tbody>
    </table>
    {users.length === 0 &&
      <h1>Access denied</h1>}
  </div>
}

export default Tasks;

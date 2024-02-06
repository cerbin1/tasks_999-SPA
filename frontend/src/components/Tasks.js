import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom'


function Tasks(props) {
  const [data, setData] = useState([])
  const [search, setSearch] = useState()



  const apiUrl = 'http://localhost:8080/api/tasks';

  useEffect(() => {
    loadTasks();
  }, []);

  function loadTasks() {
    fetch(apiUrl, {
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
        loadTasks();
      })
      .catch(error => {
        alert(error)
      });
  }

  function handleChange(event) {
    setSearch(event.target.value)
  }

  function handleSearch() {

  }

  const listOfElements = data.map((task) =>
    <tr key={task.id}>
      <th scope="row">{task.id}</th>
      <td>{task.name}</td>
      <td>{task.deadline.toString()}</td>
      <td>{task.assignee.name}</td>
      <td>{task.priority.value}</td>
      <td>
        <Link to='/edit' state={{ id: task.id }}>Edit</Link>
      </td>
      <td>
        <button type="button" className="btn btn-danger" onClick={() => handleRemove(task.id)}>Remove</button>
      </td>
    </tr>
  );

  return <div>
    <table className="table">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Name</th>
          <th scope="col">Deadline</th>
          <th scope="col">Assignee</th>
          <th scope="col">Priority</th>
          <th scope="col">Edit</th>
          <th scope="col">Remove</th>
        </tr>
      </thead>
      <tbody>
        {listOfElements}
      </tbody>
    </table>
    <input type="text" className="form-control" id="name" value={search} onChange={handleChange} />
    <button type="button" className="btn btn-primary" onClick={handleSearch}>Search by name</button>
  </div>
}

export default Tasks;

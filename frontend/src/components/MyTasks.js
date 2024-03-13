import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom'


function MyTasks(props) {
  const [data, setData] = useState([])

  const apiUrl = 'http://localhost:8080/api/tasks';

  useEffect(() => {
    loadUserTasks();
  }, []);

  function loadUserTasks() {
    fetch(apiUrl + '/userTasks', {
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

  const listOfElements = data.map((task) =>
    <tr key={task.id}>
      <th scope="row">{task.id}</th>
      <td>{task.name}</td>
      <td>{task.deadline.toString()}</td>
      <td>{task.assignee.name}</td>
      <td>{task.priority.value}</td>
      <td>{task.subtasks.length}</td>
      <td>{task.completed ? "Yes" : "No"}</td>
      <td>{task.completeDate ? task.completeDate.toString() : ""}</td>
      <td>
      <Link to={`/${task.id}/details`}>Details</Link>
      </td>
    </tr>
  );

  return <div>
    {listOfElements.length == 0 ? <span>No results</span> :
      <table className="table">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Name</th>
            <th scope="col">Deadline</th>
            <th scope="col">Assignee</th>
            <th scope="col">Priority</th>
            <th scope="col">Subtasks Count</th>
            <th scope="col">Is Completed</th>
            <th scope="col">Complete date</th>
            <th scope="col">Details</th>
          </tr>
        </thead>
        <tbody>
          {listOfElements}
        </tbody>
      </table>
    }
  </div>
}

export default MyTasks;

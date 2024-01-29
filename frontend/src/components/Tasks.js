import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom'


function Tasks(props) {
  const [data, setData] = useState([])



  const apiUrl = 'http://localhost:8080/tasks';

  useEffect(() => {

    fetch(apiUrl)
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        return response.json();
      })
      .then(data => {
        setData(data)
      })
      .catch(error => {
        alert(error)
      });
  }, []);

  const listOfElements = data.map((task) =>
    <tr key={task.id}>
      <td>{task.id}</td>
      <td>{task.name}</td>
      <td>{task.deadline.toString()}</td>
      <td>{task.assignee.name}</td>
      <td>{task.priority.value}</td>
      <td>
        {/* <button>asd        </button> */}
        <Link to='/edit' state={{id: task.id}}>Edit</Link>
      </td>
    </tr>
  );

  return <div>
    <table>
      <tbody>
        <tr>
          <th>Id</th>
          <th>Name</th>
          <th>Deadline</th>
          <th>Assignee</th>
          <th>Priority</th>
        </tr>
      </tbody>
      <tbody>
        {listOfElements}
      </tbody>
    </table>
  </div>
}

export default Tasks;

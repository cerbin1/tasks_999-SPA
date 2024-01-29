import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom'

function EditTask(props) {
  const [task, setTask] = useState()
  const [users, setUsers] = useState()
  const [priorities, setPriorities] = useState()

  const apiUrl = 'http://localhost:8080/';

  const location = useLocation()
  const { id } = location.state

  useEffect(() => {

    fetch(apiUrl + 'tasks/' + id)
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        setTask(data)
      })
      .catch(error => {
        alert(error)
      });


    fetch(apiUrl + 'users')
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        setUsers(data)
      })
      .catch(error => {
        alert(error)
      });

    fetch(apiUrl + 'priorities')
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        setPriorities(data)
      })
      .catch(error => {
        alert(error)
      });

  }, []);

  function updateTask(event) {
    event.preventDefault();
    alert('updateTask')
  }

  function handleChange(event) {
    setTask({
      ...task,
      [event.target.id]: event.target.value
    })
  }


  return <div>
    {task &&
      <form onSubmit={updateTask}>
        <label forhtml="name">Name</label>
        <input id="name" name="name" value={task.name} onChange={handleChange} />

        <label forhtml="deadline">Deadline</label>
        <input id="deadline" type="datetime-local" value={task.deadline} onChange={handleChange} />

        {users &&
          <label>
            Assignee:
            <select name="assignee" defaultValue={task.assignee.id}>
              {users.map((user, index) => <option onChange={handleChange} key={index} value={user.id}>{user.name}</option>)}
            </select>
          </label>
        }

        {priorities &&
          <label>
            Priority:
            <select name="priority" defaultValue={task.priority.id}>
              {priorities.map((priority, index) => <option onChange={handleChange} key={index} value={priority.id}>{priority.value}</option>)}
            </select>
          </label>
        }

        <button type="submit" >Update</button>
      </form>
    }
  </div>
}

export default EditTask;

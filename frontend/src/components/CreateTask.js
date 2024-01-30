import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'

function CreateTask(props) {
  const [task, setTask] = useState({ name: '', deadline: '' })
  const [users, setUsers] = useState()
  const [priorities, setPriorities] = useState()

  const apiUrl = 'http://localhost:8080/';

  const navigate = useNavigate();

  useEffect(() => {
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

  function handleChange(event) {
    setTask({
      ...task,
      [event.target.id]: event.target.value
    })
  }

  function handleAssigneeChange(event) {
    const findAssignee = users.find((user) => {
      return user.id == event.target.value
    });
    setTask(task => {
      return {
        ...task,
        assignee: findAssignee
      };
    });
  }

  function handlePriorityChange(event) {
    const findPriority = priorities.find((priority) => {
      return priority.id == event.target.value
    });
    setTask(task => {
      return {
        ...task,
        priority: findPriority
      };
    });
  }

  function createTask(event) {
    event.preventDefault();

    fetch(apiUrl + 'tasks', {
      method: 'POST',
      body: JSON.stringify(task),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        navigate('/list');
      })
      .catch(error => {
        alert(error)
      });
  }

  return <div>
    <form onSubmit={createTask}>
      <label forhtml="name">Name</label>
      <input id="name" name="name" value={task.name} defaultValue={''} onChange={handleChange} />

      <label forhtml="deadline">Deadline</label>
      <input id="deadline" type="datetime-local" value={task.deadline} onChange={handleChange} />

      {users &&
        <label>
          Assignee:
          <select name="assignee" onChange={handleAssigneeChange}>
            {users.map((user, index) => <option key={index} value={user.id}>{user.name}</option>)}
          </select>
        </label>
      }

      {priorities &&
        <label>
          Priority:
          <select name="priority" onChange={handlePriorityChange}>
            {priorities.map((priority, index) => <option key={index} value={priority.id}>{priority.value}</option>)}
          </select>
        </label>
      }

      <button type="submit" >Create Task</button>
    </form>
  </div>
}

export default CreateTask;

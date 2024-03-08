import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'

function CreateTask(props) {
  const [task, setTask] = useState({ name: '', deadline: '', subtasks: [{ name: '' }] })
  const [users, setUsers] = useState()
  const [priorities, setPriorities] = useState()
  const [errors, setErrors] = useState();

  const apiUrl = 'http://localhost:8080/api/';

  const navigate = useNavigate();

  useEffect(() => {
    fetch(apiUrl + 'users', {
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
        setUsers(data)
      })
      .catch(error => {
        alert(error)
      });

    fetch(apiUrl + 'priorities', {
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

  function validateValues() {
    let errors = {};
    if (task.name.length == 0) {
      errors.name = true;
    }
    if (task.deadline.length == 0) {
      errors.deadline = true;
    }
    return errors;
  };

  function createTask(event) {
    event.preventDefault();

    const errorValues = validateValues();
    setErrors(errorValues)
    if (Object.keys(errorValues).length !== 0) {
      return;
    }

    fetch(apiUrl + 'tasks', {
      method: 'POST',
      body: JSON.stringify(task),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
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
        navigate('/list');
      })
      .catch(error => {
        alert(error)
      });
  }

  function handleCancelButton() {
    navigate('/list');
  }

  function handleSubtaskChange(index, event) {
    let subtasks = task.subtasks.slice();
    subtasks[index].name = event.target.value;

    setTask({
      ...task,
      subtasks: subtasks
    })
  }

  function handleRemoveSubtaskButton(index, event) {
    let subtasks = task.subtasks.slice();
    subtasks.splice(index, 1)

    setTask({
      ...task,
      subtasks: subtasks
    })
  }

  function handleAddSubtaskButton() {
    setTask({
      ...task,
      subtasks: [...task.subtasks, { name: '' }]
    })
  }

  return <div className='container'>
    <form onSubmit={createTask}>
      <div className="form-group row">
        <label htmlFor="name" className="col-sm-2 col-form-label">Name</label>
        <div className="col-sm-10">
          <input type="text" className="form-control" id="name" value={task.name} onChange={handleChange} />
          {errors && errors.name &&
            <div className="alert alert-danger" role="alert">
              You must enter name before submitting.
            </div>
          }
        </div>
      </div>
      <div className="form-group row">
        <label htmlFor="deadline" className="col-sm-2 col-form-label">Deadline</label>
        <div className="col-sm-10">
          <input className="form-control" id="deadline" type="datetime-local" value={task.deadline} onChange={handleChange} />
          {errors && errors.deadline &&
            <div className="alert alert-danger" role="alert">
              You must enter deadline before submitting.
            </div>
          }
        </div>
      </div>

      {users &&
        <div className="form-group row">
          <label htmlFor="prority" className="col-sm-2 col-form-label">Assignee</label>
          <div className="col-sm-10">
            <select className="form-select" name="assignee" onChange={handleAssigneeChange}>
              {users.map((user, index) => <option key={index} value={user.id}>{user.name} {user.surname}</option>)}
            </select>
          </div>
        </div>
      }

      {priorities &&
        <div className="form-group row">
          <label htmlFor="prority" className="col-sm-2 col-form-label">Priority</label>
          <div className="col-sm-10">
            <select className="form-select" name="priority" onChange={handlePriorityChange}>
              {priorities.map((priority, index) => <option key={index} value={priority.id}>{priority.value}</option>)}
            </select>
          </div>
        </div>
      }

      <h1>Subtasks</h1>
      <div className="d-flex align-items-center justify-content-center">
        <div className="form-group col-md-3">
          {task.subtasks.map((subtask, index) => {
            return <div key={index} className="input-group sm-3">
              <input className="form-control" type="text" style={{ textAlign: "center" }} value={subtask.name} onChange={handleSubtaskChange.bind(this, index)} />
              <button type="button" className="btn btn-danger" onClick={handleRemoveSubtaskButton}>Delete</button>
            </div>
          })}
        </div>
      </div>


      <div className="form-group row">
        <div className='form-control'>
          <button type="button" className="btn btn-secondary" onClick={handleCancelButton}>Cancel</button>
          <button type="button" className="btn btn-success" onClick={handleAddSubtaskButton}>Add subtask</button>
          <button type="submit" className="btn btn-primary">Create Task</button>
        </div>
      </div>
    </form>
  </div>
}

export default CreateTask;

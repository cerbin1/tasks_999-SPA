import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

function EditTask(props) {
  const [task, setTask] = useState()
  const [users, setUsers] = useState()
  const [priorities, setPriorities] = useState()
  const [errors, setErrors] = useState();

  const apiUrl = 'http://localhost:8080/api/';

  const location = useLocation()
  const { id } = location.state

  const navigate = useNavigate();

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

  function updateTask(event) {
    event.preventDefault();

    const errorValues = validateValues();
    setErrors(errorValues)
    if (Object.keys(errorValues).length !== 0) {
      return;
    }

    fetch(apiUrl + 'tasks/' + task.id, {
      method: 'PUT',
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

  function handleCancelButton() {
    navigate('/list');
  }

  return <div>
    {task &&
      <form onSubmit={updateTask}>
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
            {errors && errors.name &&
              <div className="alert alert-danger" role="alert">
                You must enter name before submitting.
              </div>
            }
          </div>
        </div>

        {users &&
          <div className="form-group row">
            <label htmlFor="prority" className="col-sm-2 col-form-label">Assignee</label>
            <div className="col-sm-10">
              <select className="form-select" name="assignee" defaultValue={task.assignee.id} onChange={handleAssigneeChange}>
                {users.map((user, index) => <option key={index} value={user.id}>{user.name}</option>)}
              </select>
            </div>
          </div>
        }

        {priorities &&
          <div className="form-group row">
            <label htmlFor="prority" className="col-sm-2 col-form-label">Priority</label>
            <div className="col-sm-10">
              <select className="form-select" name="priority" defaultValue={task.priority.id} onChange={handlePriorityChange}>
                {priorities.map((priority, index) => <option key={index} value={priority.id}>{priority.value}</option>)}
              </select>
            </div>
          </div>
        }

        <div className="form-group row">
          <div className="col-sm-10">
            <button type="button" className="btn btn-secondary" onClick={handleCancelButton}>Cancel</button>
            <button type="submit" className="btn btn-primary">Update</button>
          </div>
        </div>
      </form>
    }
  </div>
}

export default EditTask;

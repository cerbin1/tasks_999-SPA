import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

function EditTask(props) {
  const [task, setTask] = useState()
  const [users, setUsers] = useState()
  const [priorities, setPriorities] = useState()

  const apiUrl = 'http://localhost:8080/';

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

  function updateTask(event) {
    event.preventDefault();

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
        <div class="form-group row">
          <label for="name" class="col-sm-2 col-form-label">Name</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="name" value={task.name} onChange={handleChange} />
          </div>
        </div>
        <div class="form-group row">
          <label for="deadline" class="col-sm-2 col-form-label">Deadline</label>
          <div class="col-sm-10">
            <input class="form-control" id="deadline" type="datetime-local" value={task.deadline} onChange={handleChange} />
          </div>
        </div>

        {users &&
          <div class="form-group row">
            <label for="prority" class="col-sm-2 col-form-label">Assignee</label>
            <div class="col-sm-10">
              <select class="form-select" name="assignee" defaultValue={task.assignee.id} onChange={handleAssigneeChange}>
                {users.map((user, index) => <option key={index} value={user.id}>{user.name}</option>)}
              </select>
            </div>
          </div>
        }

        {priorities &&
          <div class="form-group row">
            <label for="prority" class="col-sm-2 col-form-label">Priority</label>
            <div class="col-sm-10">
              <select class="form-select" name="priority" defaultValue={task.priority.id} onChange={handlePriorityChange}>
                {priorities.map((priority, index) => <option key={index} value={priority.id}>{priority.value}</option>)}
              </select>
            </div>
          </div>
        }

        <div class="form-group row">
          <div class="col-sm-10">
            <button type="button" class="btn btn-secondary" onClick={handleCancelButton}>Cancel</button>
            <button type="submit" class="btn btn-primary">Update</button>
          </div>
        </div>
      </form>
    }
  </div>
}

export default EditTask;

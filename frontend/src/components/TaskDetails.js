import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import Subtask from './Subtask';

function TaskDetails(props) {
  const [task, setTask] = useState()

  const apiUrl = 'http://localhost:8080/api/';

  const location = useLocation()
  const { id } = location.state

  const navigate = useNavigate();

  useEffect(() => {

    fetch(apiUrl + 'tasks/' + id, {
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
        setTask(data)
      })
      .catch(error => {
        alert(error)
      });

  }, []);

  function updateTask(event) {
    event.preventDefault();

    fetch(apiUrl + 'tasks/markAsCompleted?taskId=' + task.id, {
      method: 'PUT',
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
        
        navigate('/myList');
      })
      .then(data => {
      })
      .catch(error => {
        alert(error)
      });
  }


  function handleCancelButton() {
    navigate('/myList');
  }

  return <div className='container'>
    {task &&
      <div>
        <div className="row">
          <label className="col-sm-4 col-form-label fw-bold">Name</label>
          <p className="col-sm-8">{task.name}</p>
        </div>
        <div className="row">
          <label className="col-sm-4 col-form-label fw-bold">Deadline</label>
          <p className="col-sm-8">{task.deadline}</p>
        </div>

        <div className="row">
          <label htmlFor="prority" className="col-sm-4 col-form-label fw-bold">Assignee</label>
          <p className="col-sm-8">{task.assignee.name}</p>
        </div>

        <div className="row">
          <label className="col-sm-4 col-form-label fw-bold">Priority</label>
          <p className="col-sm-8">{task.priority.value}</p>
        </div>

        <h1>Subtasks</h1>
        <div className="d-flex align-items-center justify-content-center">
          <div className="col-md-3">
            {task.subtasks.map((subtask, index) => {
              return <Subtask key={index} name={subtask.name} />;
            })}
          </div>
        </div>

        <div className='form-control'>
          <button type="button" className="btn btn-secondary" onClick={handleCancelButton}>Cancel</button>
          <button type="button" className="btn btn-success" onClick={updateTask}>Mark as completed</button>
        </div>
      </div>
    }
  </div>
}

export default TaskDetails;

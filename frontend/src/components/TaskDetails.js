import React, { useEffect, useRef, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Subtask from './Subtask';

function TaskDetails(props) {
  const [task, setTask] = useState()
  const [showChat, setShowChat] = useState()
  const [messageContent, setMessageContent] = useState()
  const [log, setLog] = useState({ date: '', minutes: '', comment: '' })
  const closeModal = useRef();
  const openModal = useRef();
  const [errors, setErrors] = useState();
  const [logErrors, setLogErrors] = useState();

  const apiUrl = 'http://localhost:8080/api/';

  const { id } = useParams();

  const navigate = useNavigate();

  useEffect(() => loadTaskDetails(), []);

  function loadTaskDetails() {
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
  }

  function validateValues() {
    let errors = {};
    if (messageContent.length == 0) {
      errors.messageContent = true;
    }
    return errors;
  };

  function sendMessage(event) {
    event.preventDefault();

    const errorValues = validateValues();
    setErrors(errorValues)
    if (Object.keys(errorValues).length !== 0) {
      return;
    }

    fetch(apiUrl + 'chat/messages', {
      method: 'POST',
      body: JSON.stringify({ content: messageContent, taskId: task.id }),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
        "Authorization": `Bearer ` + localStorage.getItem('token'),
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        setMessageContent('')
        loadTaskDetails()
      })
      .then(data => {
      })
      .catch(error => {
        alert(error)
      });
  }

  function markTaskAsCompleted(event) {
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


  function handleMessageContentChange(event) {
    setMessageContent(event.target.value);
  }

  function handleChange(event) {
    console.log(event)
    setLog({
      ...log,
      [event.target.id]: event.target.value
    })
  }

  function handleCancelButton() {
    navigate('/myList');
  }


  function validateLogValues() {
    let logErrors = {};
    if (log.date.length == 0) {
      logErrors.date = true;
    }
    if (log.minutes.length == 0) {
      logErrors.minutes = true;
    }
    console.log(logErrors)
    return logErrors;
  };

  function logTime(event) {
    event.preventDefault();

    console.log(event)

    const logErrors = validateLogValues();
    setLogErrors(logErrors)
    if (Object.keys(logErrors).length !== 0) {
      return;
    }

    fetch(apiUrl + 'tasks/worklog?taskId=' + task.id, {
      method: 'PUT',
      body: JSON.stringify(log),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
        "Authorization": `Bearer ` + localStorage.getItem('token'),
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
      })
      .then(() => {
        loadTaskDetails();
        closeModal.current.click();
        setLog({ date: '', minutes: '', comment: '' });
      })
      .catch(error => {
        alert(error)
      });
  }

  function handleDeleteWorklogButton(worklogId) {
    fetch(apiUrl + 'tasks/worklog/' + worklogId, {
      method: 'DELETE',
      headers: {
        "Authorization": `Bearer ` + localStorage.getItem('token'),
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
      })
      .then(() => {
        loadTaskDetails();
      })
      .catch(error => {
        alert(error)
      });
  }


  function handleEditWorklogButton(setLog, worklog, openModal) {
    return () => {
      setLog({
        id: worklog.id,
        date: worklog.date,
        minutes: worklog.minutes,
        comment: worklog.comment,
      });
      openModal.current.click();
    };
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
          <label className="col-sm-4 col-form-label fw-bold">Assignee</label>
          <p className="col-sm-8">{task.assignee.name}</p>
        </div>

        <div className="row">
          <label className="col-sm-4 col-form-label fw-bold">Priority</label>
          <p className="col-sm-8">{task.priority.value}</p>
        </div>

        <h1>Labels</h1>
        {task.labels && task.labels.length == 0 ?
          "No labels added." :
          task.labels.map((label, index) => {
            return <div key={index} className="input-group mb-1">
              <span className="form-control">{label}</span>
            </div>
          })
        }

        <h1>Category</h1>
        <div className="row">
          <label className="col-sm-4 col-form-label fw-bold">Category</label>
          <p className="col-sm-8">{task.category}</p>
        </div>

        <h1>Subtasks</h1>
        <div className="d-flex align-items-center justify-content-center">
          <div className="col-md-3">
            {task.subtasks.map((subtask, index) => {
              return <Subtask key={index} name={subtask.name} />;
            })}
          </div>
        </div>

        <h1>Chat</h1>
        {showChat ?
          <>
            {task.messages.length == 0 ? <p>No messages yet.</p> :
              <div>
                {task.messages.map((message, index) => {
                  if (message.sender.id == localStorage.getItem('userId')) {
                    return <div className="d-flex flex-row" key={index}>
                      <div>
                        <b>{message.sender.name}:</b>
                        <p>{message.content} </p>
                      </div>
                    </div>
                  }
                  else {
                    return <div className="d-flex flex-row-reverse" key={index}>
                      <div>
                        <b>{message.sender.name}:</b>
                        <p>{message.content} </p>
                      </div>
                    </div>
                  }
                })
                }
              </div>
            }
            <form onSubmit={sendMessage}>
              <input type="text" className="form-control" id="name" value={messageContent} onChange={handleMessageContentChange} placeholder='Message content
              ' />
              {errors && errors.messageContent &&
                <div className="alert alert-danger" role="alert">
                  You must enter content before sending the message.
                </div>
              }
              <button type="submit" className="btn btn-primary">Send Message</button>

            </form>
          </> :
          <button type="button" className="btn btn-primary" onClick={() => setShowChat(true)}>Show chat</button>
        }
        <h1>Files</h1>
        <div className="list-group">
          {task.taskFiles.map((file, i) => (
            <a key={i} className="list-group-item list-group-item-action" href={`${apiUrl}files/${file.id}`}>{file.name}</a>
          ))}
        </div>

        <h1>Worklogs</h1>
        <div className="list-group">
          {task.worklogs.length ?
            <table className="table">
              <thead>
                <tr>
                  <th scope="col">Date</th>
                  <th scope="col">Minutes</th>
                  <th scope="col">Comment</th>
                  <th scope="col">Edit</th>
                  <th scope="col">Delete</th>
                </tr>
              </thead>
              <tbody>
                {task.worklogs.map((worklog, i) => (
                  <tr key={i}>
                    <td>{worklog.date}</td>
                    <td>{worklog.minutes}</td>
                    <td>{worklog.comment}</td>
                    <td><button type="button" className="btn btn-primary" onClick={handleEditWorklogButton(setLog, worklog, openModal)
                    }>Edit</button></td>
                    <td><button type="button" className="btn btn-danger" onClick={() => handleDeleteWorklogButton(worklog.id)}>Delete</button></td>
                  </tr>
                ))}
              </tbody>
            </table>
            : <b>No data.</b>}
        </div>

        <div className='form-control'>
          <button type="button" className="btn btn-secondary" onClick={handleCancelButton}>Cancel</button>
          <button type="button" className="btn btn-primary" data-bs-toggle="modal" data-bs-target="#logTimeModal" ref={openModal}>
            Log time
          </button>
          <button type="button" className="btn btn-success" onClick={markTaskAsCompleted}>Mark as completed</button>
        </div>

        <div className="modal fade" id="logTimeModal" tabIndex="-1" aria-labelledby="#logTimeModalLabel" >
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title" id="logTimeModalLabel">Worklog</h5>
                <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close" ref={closeModal}></button>
              </div>
              <div className="modal-body">
                <form onSubmit={logTime}>
                  <input type="date" className="form-control" id="date" value={log.date} onChange={handleChange} />
                  {logErrors && logErrors.date &&
                    <div className="alert alert-danger" role="alert">
                      You must enter content before sending the message.
                    </div>
                  }
                  <input type="number" className="form-control" id="minutes" value={log.minutes} placeholder="Minutes worked" min="1" max="1000" onChange={handleChange} />
                  {logErrors && logErrors.minutes &&
                    <div className="alert alert-danger" role="alert">
                      You must enter content before sending the message.
                    </div>
                  }
                  <input type="text" className="form-control" id="comment" value={log.comment} placeholder='Comment' onChange={handleChange} />
                  <div className="modal-footer">
                    <button type="button" className="btn btn-secondary" data-bs-dismiss="modal"
                    >Close</button>
                    <button type="submit" className="btn btn-primary">Log Time</button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>

    }
  </div>
}

export default TaskDetails;

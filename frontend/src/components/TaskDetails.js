import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import Subtask from './Subtask';

function TaskDetails(props) {
  const [task, setTask] = useState()
  const [showChat, setShowChat] = useState()
  const [messageContent, setMessageContent] = useState()
  const [errors, setErrors] = useState();

  const apiUrl = 'http://localhost:8080/api/';

  const location = useLocation()
  const { id } = location.state

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


  function handleChange(event) {
    setMessageContent(event.target.value);
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

        <h1>Chat</h1>
        {showChat ?
          <>
            {task.messages.length == 0 ? <p>No messages yet.</p> :
              <div>
                {task.messages.map((message, index) => {
                  console.log(task.messages)
                  if (message.sender.id == localStorage.getItem('userId')) {
                    return <div class="d-flex flex-row" key={index}>
                      <div>
                        <b>{message.sender.name}:</b>
                        <p>{message.content} </p>
                      </div>
                    </div>
                  }
                  else {
                    return <div class="d-flex flex-row-reverse" key={index}>
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
              <input type="text" className="form-control" id="name" value={messageContent} onChange={handleChange} placeholder='Message content
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



        <div className='form-control'>
          <button type="button" className="btn btn-secondary" onClick={handleCancelButton}>Cancel</button>
          <button type="button" className="btn btn-success" onClick={markTaskAsCompleted}>Mark as completed</button>
        </div>
      </div>
    }
  </div>
}

export default TaskDetails;

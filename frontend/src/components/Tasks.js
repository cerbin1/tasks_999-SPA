import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom'


function Tasks(props) {
  const [data, setData] = useState([])
  const [categories, setCategories] = useState()
  const [searchByName, setSearchByName] = useState('')
  const [searchByCategory, setSearchByCategory] = useState('')



  const apiUrl = 'http://localhost:8080/api/tasks';

  useEffect(() => {
    fetch(apiUrl + '/categories', {
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
        setCategories(data)
      })
      .catch(error => {
        alert(error)
      });

    loadTasks();
  }, []);

  function loadTasks() {
    fetch(apiUrl, {
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

  function handleRemove(id) {
    fetch(apiUrl + '/' + id, {
      method: 'DELETE',
      headers: {
        "Authorization": `Bearer ` + localStorage.getItem('token'),
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        loadTasks();
      })
      .catch(error => {
        alert(error)
      });
  }

  function handleSearchByNameChange(event) {
    setSearchByName(event.target.value)
  }

  function handleSearchByNameButton() {
    fetch(apiUrl + '/searchByName?value=' + searchByName, {
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
        alert(error)
      });
  }

  function handleSearchByCategoryChange(event) {
    setSearchByCategory(event.target.value)
  }

  function handleSearchByCategoryButton() {
    fetch(apiUrl + '/searchByCategory?value=' + searchByCategory, {
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
        alert(error)
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
        <Link to='/edit' state={{ id: task.id }}>Edit</Link>
      </td>
      <td>
        <button type="button" className="btn btn-danger" onClick={() => handleRemove(task.id)}>Remove</button>
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
            <th scope="col">Edit</th>
            <th scope="col">Remove</th>
          </tr>
        </thead>
        <tbody>
          {listOfElements}
        </tbody>
      </table>
    }
    <h1>Filters</h1>
    <div className="form-group row">
      <input type="text" className="form-control" id="name" value={searchByName} onChange={handleSearchByNameChange} />
      <button type="button" className="btn btn-primary" onClick={handleSearchByNameButton}>Search by name</button>
    </div>
    <div className="form-group row">
      {categories &&
        <div className="d-flex align-items-center justify-content-center">
          <div className="form-group col-md-3">
            <select className="form-select" name="category" onChange={handleSearchByCategoryChange}>
              {categories.map((category, index) => <option key={index} value={category}>{category}</option>)}
            </select>
          </div>
        </div>
      }
      <button type="button" className="btn btn-primary" onClick={handleSearchByCategoryButton}>Search by category</button>

    </div>
  </div>
}

export default Tasks;

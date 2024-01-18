import React from 'react';

function CreateTask(props) {

  const persons = [
    { name: "Mike" },
    { name: "John" },
    { name: "Bart" },
  ]

  const priorities = [1, 2, 3, 4, 5]

  function createTask(event) {
    event.preventDefault();
    alert('createTask')
  }

  return <div>

    <form onSubmit={createTask}>
      <label forHtml="name">Name</label>
      <input id="name" name="name" placeholder="Name" />

      <label forHtml="deadline">Deadline</label>
      <input id="deadline" type="date" />


      <label>
        Assignee:
        <select name="assignee">
          {persons.map((person) => <option value={person.name}>{person.name}</option>)}
        </select>
      </label>

      <label>
        Priority:
        <select name="priority">
          {priorities.map((priority) => <option value={priority}>{priority}</option>)}
        </select>
      </label>

      <button type="submit" >Create Task</button>
    </form>
  </div>
}

export default CreateTask;

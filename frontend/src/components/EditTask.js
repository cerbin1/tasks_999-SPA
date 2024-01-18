import React from 'react';

function EditTask(props) {

  const persons = [
    { name: "Mike" },
    { name: "John" },
    { name: "Bart" },
  ]

  const priorities = [1, 2, 3, 4, 5]

  function updateTask(event) {
    event.preventDefault();
    alert('updateTask')
  }

  return <div>

    <form onSubmit={updateTask}>
      <label forhtml="name">Name</label>
      <input id="name" name="name" value="task 1" />

      {/* { id: 1, name: 'task 1', deadline: new Date(), assigned: 'Mike', priority: 1 }, */}

      <label forhtml="deadline">Deadline</label>
      <input id="deadline" type="date" value="2023-02-02" />


      <label>
        Assignee:
        <select name="assignee">
          {persons.map((person, index) => <option key={index} value={person.name}>{person.name}</option>)}
        </select>
      </label>

      <label>
        Priority:
        <select name="priority">
          {priorities.map((priority, index) => <option key={index} value={priority}>{priority}</option>)}
        </select>
      </label>

      <button type="submit" >Update</button>
    </form>
  </div>
}

export default EditTask;

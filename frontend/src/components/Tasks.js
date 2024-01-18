import React from 'react';

function Tasks(props) {
  const list = [
    { id: 1, name: 'task 1', deadline: new Date(), assigned: 'Mike', priority: 1 },
    { id: 2, name: 'task 2', deadline: new Date(), assigned: 'Mike', priority: 2 },
    { id: 3, name: 'task 3', deadline: new Date(), assigned: 'Mike', priority: 3 },
    { id: 4, name: 'task 4', deadline: new Date(), assigned: 'Mike', priority: 1 },
    { id: 5, name: 'task 5', deadline: new Date(), assigned: 'Mike', priority: 2 },
  ];

  const listOfElements = list.map((task) =>
    <tr key={task.id}>
      <td>{task.id}</td>
      <td>{task.name}</td>
      <td>{task.deadline.toString()}</td>
      <td>{task.assigned}</td>
      <td>{task.priority}</td>
    </tr>
  );

  return <div>
    <table>
      <tbody>
        <tr>
          <th>Id</th>
          <th>Name</th>
          <th>Deadline</th>
          <th>Assignee</th>
          <th>Priority</th>
        </tr>
      </tbody>
      <tbody>
        {listOfElements}
      </tbody>
    </table>
  </div>
}

export default Tasks;

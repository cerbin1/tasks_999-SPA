import React, { useState } from 'react';

function Subtask({ name }) {
    const [isDone, setIsDone] = useState(false);

    return (
        <div className="input-group">
            <div className={`col-md-9 ${isDone ? "text-decoration-line-through" : ""}`} style={{ textAlign: "center" }}>{name}</div>
            <button type="button" className="btn btn-success col-md-3" onClick={() => setIsDone(!isDone)}>Done</button>
        </div>
    );
}

export default Subtask;

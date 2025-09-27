import { AddTask, DoneTask } from "../Action/action";
import { store } from "../store";

// Action Creator
export const addNewTaskActionCreator = (newTask, dispatch) => {
    console.log("newTask:", newTask);

    const token = sessionStorage.getItem("token");
    if (!token) {
        alert("Authorization token is missing. Please log in again.");
        return;
    }

    const URL = "http://localhost:8080/task/add";

    fetch(URL, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`, // Send the token in Authorization header
            'Content-Type': 'application/json', // Ensure correct header value
        },
        body: JSON.stringify(newTask), // Convert task object to JSON
    })
        .then((res) => {
            if (!res.ok) {
                return res.json().then((error) => {
                    throw new Error(error.message || "Failed to add task");
                });
            }
            return res.json();
        })
        .then( async (data) => {
            alert(data.message); // Ensure `data.message` exists in API response
            let task = data.data; // Extract task data from response
            console.log("Before modification:", task);

            // Modify task object if needed
            task = {
                ...task,
                isDone: task.done, // Map `done` to `isDone` if required
                tasklistId:task.tasklistId
            };
            console.log("lists in the store ", store.getState().user.tasklists);
            console.log("After modification:", task);
            console.log("Dispatching AddTask action:", task);
             dispatch(AddTask(task));
            
            console.log("Action dispatched successfully.");
            
            console.log("State after dispatch:", store.getState().user.tasklists);
        })
        .catch((error) => {
            console.error("Error:", error);
            alert("Failed to add task. Please try again. Details: " + error.message);
        });
};



export const addTaskDoneActionCreator = (taskId, dispatch) => {

    const token = sessionStorage.getItem("token");
    if (!token) {
        alert("Authorization token is missing. Please log in again.");
        return;
    }

    const URL = "http://localhost:8080/task/done";

    fetch(URL, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`, // Send the token in Authorization header
            'Content-Type': 'application/json', // Ensure correct header value
        },
        body: JSON.stringify({id:taskId}), // Convert task object to JSON
    })
        .then((res) => {
            if (!res.ok) {
                return res.json().then((error) => {
                    throw new Error(error.message || "Failed to add task");
                });
            }
            return res.json();
        })
        .then( async (data) => {
            alert(data.message); // Ensure `data.message` exists in API response
            let task = data.data; // Extract task data from response
            console.log("Before modification:", task);

            // Modify task object if needed
            task = {
                ...task,
                isDone: task.done, // Map `done` to `isDone` if required
                tasklistId:task.tasklistId
            };
           
             dispatch(DoneTask(task));
            
            console.log("Action dispatched successfully.");
            
            console.log("State after dispatch:", store.getState().user.tasklists);
        })
        .catch((error) => {
            console.error("Error:", error);
            alert("Failed to add task. Please try again. Details: " + error.message);
        });
};


export const deleteCompletedTasksActionCreator = (tasklistId, dispatch) => {

    const token = sessionStorage.getItem("token");
    if (!token) {
        alert("Authorization token is missing. Please log in again.");
        return;
    }

    const URL = "http://localhost:8080/task/deleteCompleted";

    fetch(URL, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`, // Send the token in Authorization header
            'Content-Type': 'application/json', // Ensure correct header value
        },
        body: JSON.stringify({tasklistId:tasklistId}), // Convert task object to JSON
    })
        .then((res) => {
            if (!res.ok) {
                return res.json().then((error) => {
                    throw new Error(error.message || "Failed to add task");
                });
            }
            return res.json();
        })
        .then( async (data) => {
            alert(data.message); // Ensure `data.message` exists in API response
           })
        .catch((error) => {
            console.error("Error:", error);
            alert("Failed to add task. Please try again. Details: " + error.message);
        });
};
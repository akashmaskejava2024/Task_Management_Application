import React, { useEffect, useRef, useState } from 'react';
import "./dashboard.css";
import { useDispatch, useSelector } from 'react-redux';
import { isListedChangeAction, LoggedInUserAction, Logout } from '../Action/action';
import { addTaskListActionCreator, addTaskListisListedActionCreator } from '../ActionCreator/listActionCreator';
import { addNewTaskActionCreator, addTaskActionCreator, addTaskDoneActionCreator, deleteCompletedTasksActionCreator } from '../ActionCreator/taskActionCreator';
import { Button } from 'bootstrap/dist/js/bootstrap.bundle.min';
import { date } from 'yup';
import { isVisible } from '@testing-library/user-event/dist/utils';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const username = useSelector((state) => state.user.username);
    const user = useSelector((state) => state.user); // Fetch user from Redux store
    const tasklists = user.tasklists;

    const [showModal, setShowModal] = useState(false);


    const [newListName, setNewListName] = useState('');
    const [newTaskName, setNewTaskName] = useState('');
    const [newTaskDesc, setNewTaskDesc] = useState('');
    const [newTaskDate, setNewTaskDate] = useState('');
    const [newTaskListIdforTask, setTaskListIdforTask] = useState();

    const handleListNameChange = (e) => setNewListName(e.target.value);
    const handleTaskNameChange = (e) => setNewTaskName(e.target.value);
    const handleTaskDescChange = (e) => setNewTaskDesc(e.target.value);
    const handleTaskDateChange = (e) => setNewTaskDate(e.target.value);
    const handleTaskListChange = (e) => setTaskListIdforTask(e.target.value);
    // {

    //     const selectedId = e.target.value;
    //     const selectedList = tasklists.find(list => String(list.id) === selectedId);
    //     console.log("Selected task list ID: ", selectedId);
    //     console.log(typeof selectedId);  // Check the type of selectedId
    //     console.log(typeof tasklists[0]?.id); // Check the type of list.id

    //     console.log("istasklists an array ", Array.isArray(tasklists)); // Should log true
    //     console.log("Task lists: ", tasklists);
    //     console.log("Selected task list object: ", selectedList);
    //     setTaskListforTask(selectedList);
    // };

    // if list is already listed or not it will be automatically added to this 
    const [checkedListIds, setCheckedListIds] = useState({});
    const [checkedTaskIds, setCheckedTaskIds] = useState({});

    useEffect(() => {

        // Set the initial checked state from tasklists
        const initialCheckedList = {};
        tasklists.forEach((list) => {
            initialCheckedList[list.id] = list.isListed; // Assuming isListed is the initial checked state
        });

        const initialTaskCheckedList = {};
        tasklists.forEach((list)=>
            list.tasks.forEach((task) => {
                initialTaskCheckedList[task.id] = task.isDone;
            })
        )
        setCheckedListIds(initialCheckedList);
        setCheckedTaskIds(initialTaskCheckedList);
    }, [tasklists]); // Re-run this effect if tasklists change


    const handleListCheckboxChange = async (list) => {
        // Toggle the local checkbox state
        const updatedState = {
            ...checkedListIds,
            [list.id]: !checkedListIds[list.id],
        };
        setCheckedListIds(updatedState);

        // Prepare the updated list
        const updatedList = {
            ...list,
            isListed: updatedState[list.id],
        };
        const tasklist = {
            listname: list.listname,
            username: username,
            listed: updatedList.isListed
            // in server side instead of  isListed, lilsted is used
        }
        console.log("dashboard ", tasklist);


        // Update the Redux store by dispatching the action
        await addTaskListisListedActionCreator(tasklist, dispatch);




    };

    const addNewList = (e) => {
        e.preventDefault();
        if (newListName) {
            const tasklist = { listname: newListName, username: username, listed: true };
            console.log(tasklist);

            addTaskListActionCreator(tasklist, dispatch);
            setNewListName('');
        }
    };



    const addNewTaskinMain = (e) => {
        e.preventDefault();

        const newTask = {

            name: newTaskName,
            isDone: false,
            desc: newTaskDesc,
            dueDate: newTaskDate,
            tasklistId: newTaskListIdforTask,
            username: username
        };
        setNewTaskName('');
        setNewTaskDesc('');
        setNewTaskDate('');
        setTaskListIdforTask('');
        setTaskForminListVisibleOrNot({
            listId: '',
            isVisible: false
        });

        addNewTaskActionCreator(newTask, dispatch);

    }


    const handlekeyDown = (e) => {

        if (e.key === 'Enter') {
            addNewTaskinMain(e);
        }
    };

    const formatDate = (date) => {

        const d = new Date(date);
        const year = d.getFullYear();
        const month = String(d.getMonth() + 1).padStart(2, '0');
        const day = String(d.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    };

    const handleToday = () => {

        const today = new Date();
        setNewTaskDate(formatDate(today));

    };

    const handleTommorow = () => {

        const tommorow = new Date();
        tommorow.setDate(tommorow.getDate() + 1);
        setNewTaskDate(formatDate(tommorow));
    };


    const [isTaskForminListVisible, setTaskForminListVisibleOrNot] = useState({

        listId: '',
        isVisible: false
    });
    //toggle taskform in list 
    const toggleTaskForminList = (tasklistId) => {

        setTaskForminListVisibleOrNot({
            listId: tasklistId,
            isVisible: !(isTaskForminListVisible.isVisible)
        })

    };

    const taskDone = (taskId) => {
        const updatedCheckedTaskIds = {
            ...checkedTaskIds, // Preserve previous task's checked state
            [taskId]: !checkedTaskIds[taskId], // Toggle the state of the task
        };
        setCheckedTaskIds(updatedCheckedTaskIds); // Update the state
        
        addTaskDoneActionCreator(taskId, dispatch);

    };

    const deleteCompletedTasks = (tasklistId) => {

        deleteCompletedTasksActionCreator(tasklistId);
    }

    const logout = () => {
        // Implement logout logic, like clearing session or redirecting user

        sessionStorage.clear();
        dispatch(Logout());
        navigate("/");  
    };



    return (
        <>
            <nav className="navbar navbar-expand-lg bg-body-tertiary">
                <div className="container-fluid">
                    <a className="navbar-brand" href="#">TodoList</a>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                        aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <button className="btn btn-outline-success" type="button" onClick={logout}>Logout</button>
                    </div>
                </div>
            </nav>

            <div className="d-flex">
                <div className="listContainer p-2 w-10">
                    <div className="d-flex">
                        <button className="btn btn-outline-success" data-bs-toggle="modal" data-bs-target="#CreateTaskModal">
                            Create Task
                        </button>
                    </div>
                    <div className="d-flex p-2">Lists</div>
                    <ul className="list-group">
                        {tasklists.map((list) => (
                            list && (
                                <li className="list-group-item p-2" key={list.id}>
                                    <input
                                        className="form-check-input me-1"
                                        type="checkbox"
                                        id={`listcheckbox${list.id}`}
                                        checked={checkedListIds[list.id] || list.isListed}
                                        onChange={() => handleListCheckboxChange(list)}
                                    />
                                    <label className="form-check-label" htmlFor={`listcheckbox${list.id}`}>{list.listname}</label>
                                </li>
                            )
                        ))}
                        <li className="list-group-item p-2">
                            <button className="btn btn-outline-success" data-bs-toggle="modal" data-bs-target="#CreatelistModal">
                                Create List
                            </button>
                        </li>
                    </ul>
                </div>

                <div className="TaskContianer p-2 w-100">
                    <div className="card-container">
                        {tasklists.map((list) => (
                            list && list.isListed && (
                                <div className="card h-100" key={list.id}>
                                    <div className="card-body">
                                        <h5 className="card-title">{list.listname}</h5>
                                        <p className="card-text">
                                            <ul className="list-group">
                                                <li className="list-group-item p-2">
                                                    <button className="btn p-1" onClick={() => { setTaskListIdforTask(list.id); toggleTaskForminList(list.id) }}>
                                                        Add Task
                                                    </button>
                                                </li>
                                                {isTaskForminListVisible.listId != null && String(isTaskForminListVisible.listId) == String(list.id) && isTaskForminListVisible.isVisible
                                                    ? <li className="list-group-item p-2" >
                                                        <div id="createTaskinList" className="d-flex">
                                                            <div className="checkboxContainer w-10">
                                                                <input class="form-check-input me-1" type="checkbox" id="taskcheckbox1" />
                                                            </div>

                                                            <div className="taskinfoConatainer w-100 mx-1">
                                                                <form>
                                                                    <div className="input-group input-group-sm">

                                                                        <input type="text" className="form-control task-in-list-input" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm" style={{ border: "none", outline: 'none', boxShadow: 'none' }} placeholder="Title" onChange={(e) => setNewTaskName(e.target.value)} onKeyDown={handlekeyDown} />
                                                                    </div>

                                                                    <div className="input-group input-group-sm">
                                                                        <span className="input-group-text" style={{ width: "12%", border: "none", background: "white" }}><i class="bi bi-body-text"></i></span>
                                                                        <input type="text" className="form-control task-in-list-input" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm" style={{ border: "none", outline: 'none', boxShadow: 'none' }} placeholder="Desc" onChange={(e) => setNewTaskDesc(e.target.value)} onKeyDown={handlekeyDown} />
                                                                    </div>
                                                                    <div className="input-group input-group-sm">
                                                                        <div className="d-flex">
                                                                            <button type="button" class="btn btn-primary"
                                                                                style={{
                                                                                    padding: '0.2rem 0.2rem', // Direct padding override
                                                                                    fontSize: '0.5rem', marginRight: '0.2rem'
                                                                                }}
                                                                                onClick={handleToday}>
                                                                                Today
                                                                            </button>
                                                                            <button type="button" class="btn btn-primary"
                                                                                style={{
                                                                                    padding: '0.2rem 0.2rem', // Direct padding override
                                                                                    fontSize: '0.5rem', marginRight: '0.2rem'
                                                                                }}
                                                                                onClick={handleTommorow}>
                                                                                Tommorow
                                                                            </button>
                                                                            <input type="date" className="form-control" style={{
                                                                                padding: '0.2rem 0.2rem', // Direct padding override
                                                                                fontSize: '0.5rem'
                                                                            }}
                                                                                onChange={(e) => setNewTaskDate(e.target.value)} />
                                                                            {/* <button type="button" class="btn btn-primary btn-sm">Today</button>
                                                                    <button type="button" class="btn btn-primary btn-sm">Tommorow</button> */}

                                                                            <button type="submit" style={{ display: "none" }} />

                                                                        </div>


                                                                    </div>
                                                                </form>

                                                            </div>


                                                        </div>


                                                    </li> : null
                                                }
                                                {list.tasks.slice()
                                                    .reverse()
                                                    .map((task) => (
                                                        !task.isDone && (
                                                            <li className="list-group-item p-2" key={task.id}>
                                                                <input
                                                                    className="form-check-input me-1"
                                                                    type="checkbox"
                                                                    id={`taskcheckbox${task.id}`}
                                                                    checked={checkedTaskIds[task.id] || task.isDone}
                                                                    onChange={() => taskDone(task.id)}
                                                                />
                                                                <label className="form-check-label mx-1" htmlFor={`taskcheckbox${task.id}`}>{task.name}</label>
                                                                {task.desc && (
                                                                    <div className='d-flex px-4'>
                                                                        <label className="form-check-label " style={{fontSize:"12px"}}>{task.desc}</label>
                                                                    </div>
                                                                )}
                                                                {task.dueDate && (
                                                                    <div className='d-flex px-4'>
                                                                        <label className="form-check-label" style={{fontSize:"13px"}}>{task.dueDate}</label>
                                                                    </div>
                                                                )}

                                                            </li>
                                                        )
                                                    ))}
                                                     <li className="list-group-item p-2">
                                                    <button className="btn btn-danger btn-sm" onClick={() => { deleteCompletedTasks(list.id) }}>
                                                        Delete Completed Tasks
                                                    </button>
                                                </li>
                                                    
                                            </ul>
                                        </p>
                                    </div>
                                </div>
                            )
                        ))}
                    </div>
                </div>
            </div>

            {/* Task Addition Modal */}
            <div className="modal fade" id="CreateTaskModal" tabIndex="-1" aria-labelledby="CreateTaskModal" aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <form onSubmit={addNewTaskinMain}>
                            <div className="modal-header">
                                <h1 className="modal-title fs-5">New Task</h1>
                                <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div className="modal-body">
                                <div className="mb-3">
                                    <input type="text" className="form-control" value={newTaskName} onChange={handleTaskNameChange} placeholder="Task Name" required />
                                </div>
                                <div className="mb-3">
                                    <input type="text" className="form-control" value={newTaskDesc} onChange={handleTaskDescChange} placeholder="Description" />
                                </div>
                                <div className="mb-3">
                                    <input type="date" className="form-control" value={newTaskDate} onChange={handleTaskDateChange} />
                                </div>
                                <div className="mb-3">
                                    <select className="form-select" id="inputGroupSelectMainTask" value={newTaskListIdforTask} onChange={handleTaskListChange}>
                                        <option selected disabled>Select List</option>
                                        {
                                            tasklists.map(
                                                (list) => (
                                                    <option key={list.id} value={list.id}>{list.listname}</option>
                                                )
                                            )
                                        }
                                    </select>
                                </div>
                            </div>
                            <div className="modal-footer">
                                <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" className="btn btn-primary" data-bs-dismiss="modal">Save Task</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            {/* List Modal */}
            <div className="modal fade" id="CreatelistModal" tabIndex="-1" aria-labelledby="CreatelistModal" aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <form onSubmit={addNewList}>
                            <div className="modal-header">
                                <h1 className="modal-title fs-5">New List</h1>
                                <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div className="modal-body">
                                <input type="text" className="form-control" value={newListName} onChange={handleListNameChange} placeholder="List Name" required />
                            </div>
                            <div className="modal-footer">
                                <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" className="btn btn-primary">Save List</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </>
    );
}




export default Dashboard;

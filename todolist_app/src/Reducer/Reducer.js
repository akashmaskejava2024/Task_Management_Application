// reducer.js

import { act } from "react";

const initialState = {
  user: {
    id: null,
    username: "",
    tasklists: [
      {
        id: null,
        listname: "",
        isListed: false,
        tasks: [
          {
            id: null,
            name: "",
            desc: "",
            dueDate: "",
            isDone: false

          }

        ]
      }
    ]
  },
  // user: {
  //   username: "John Doe",
  //   tasklists: [
  //     {
  //       id: 1,
  //       name: "Personal Tasks",
  //       isListed: true,
  //       tasks: [
  //         { id: 101, name: "Buy groceries", isDone: false, dueDate: "2024-11-30", desc: "Milk, Bread, Eggs" },
  //         { id: 102, name: "Call plumber", isDone: true, dueDate: null, desc: null },
  //       ],
  //     },
  //     {
  //       id: 2,
  //       name: "Work Tasks",
  //       isListed: false,
  //       tasks: [
  //         { id: 201, name: "Finish report", isDone: false, dueDate: "2024-12-01", desc: "Monthly sales report" },
  //       ]
  //     }
  //   ]
  // },

};

const Reducer = (state = initialState, action) => {
  switch (action.type) {

    case 'AddUser':
      return { ...state, user: action.payload };

   
    case 'AddList':
      return {
        ...state, user: {
          ...state.user,
          tasklists: [...state.user.tasklists, action.payload]
        }
      };

    case 'AddTask':
      console.log("Reducer received action:", action);

      return {
        ...state,
        user: {
          ...state.user,
          tasklists: state.user.tasklists.map((list) =>
            list.id === action.payload.tasklistId
              ? { ...list, tasks: [...list.tasks, action.payload] }
              : list
          )
        }
      };

    case 'isListedChange':
      return {
        ...state,
        user: {
          ...state.user,
          tasklists: state.user.tasklists.map((list) =>
            list.listname === action.payload.listname
              ? { ...list, isListed: action.payload.isListed }
              : list
          )
        }
      };


    case 'DoneTask':
      return {
        ...state,
        user: {
          ...state.user,
          tasklists: state.user.tasklists.map((list) =>
            list.id === action.payload.tasklistId
              ? {
                ...list, tasks: list.tasks.map((task) =>
                  task.id === action.payload.id
                    ? { ...task, isDone: action.payload.isDone } 
                    : task

                )
              } : list
          )
        }
      };



      case 'Logout' :
        return{
          state: null
        };
    default:
      return state;
  }
};

export default Reducer;

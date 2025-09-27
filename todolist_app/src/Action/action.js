// actions.js

import { type } from "@testing-library/user-event/dist/type"


export const AddUserAction = (addedUser) => (
  {
    type: 'AddUser',
    payload: addedUser
  }
);

export const AddList = (addedList) => (
  {
    type: 'AddList',
    payload: addedList
  }
);


export const isListedChangeAction = (tasklist) => (
  {
    type: 'isListedChange',
    payload: tasklist
  }
);


export const AddTask = (addedTask) => {
  console.log("Action Created: AddTask", { type: 'AddTask', payload: addedTask });
  return {
    type: 'AddTask',
    payload: addedTask

  }

}


export const DoneTask = (task) => {

  return {
    type: 'DoneTask',
    payload: task
  }
}

export const LoggedInUserAction = (loggedinUser) => (
  {
    type: 'loggedinuser',
    payload: loggedinUser
  }
);

export const Logout = () => {
  return {
    type: 'Logout'
  }
}

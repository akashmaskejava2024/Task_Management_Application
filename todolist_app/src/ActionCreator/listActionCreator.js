import { AddList, isListedChangeAction, isListedTrueAction } from "../Action/action";
import { store } from "../store";

export const addTaskListActionCreator = (tasklist, dispatch) => {

    const token = sessionStorage.getItem("token");
    const URL = "http://localhost:8080/list/add";

    fetch(URL,
        {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,  // Send the token in Authorization header
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(tasklist)
        }
    ).then((res) => {
        if (!res.ok) {
            return res.json().then((error) => {
                throw new Error(error.message || "Failed to add list");
            });
        }
        return res.json();
    })
        .then((data) => {
            alert(data.message); // Ensure `data.message` exists in the API response.
            console.log(data.data);
            

            const normalizedTasklist= {
                ...data.data,
                tasks:Array(0),
                isListed: data.data.listed,
            };
            // const tasklists = [...store.getState().user.tasklists, normalizedTasklist]; // Add the new tasklist to the existing ones
        //    console.log(tasklists);
           
            dispatch(AddList(normalizedTasklist));
            console.log("State after dispatch:", store.getState().user.tasklists);

        })
        .catch((error) => {
            alert("Error: " + error.message); // Use `error.message` here.
        });
}




export const addTaskListisListedActionCreator = (tasklist, dispatch) => {

    const token = sessionStorage.getItem("token");
    const URL = "http://localhost:8080/list/changeisListed";

    fetch(URL,
        {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,  // Send the token in Authorization header
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(tasklist)
        }
    ).then((res) => {
        if (!res.ok) {
            return res.json().then((error) => {
                throw new Error(error.message || "Failed to add list");
            });
        }
        return res.json();
    })
        .then((data) => {
            alert(data.message); // Ensure `data.message` exists in the API response.
            // the make isListed in store true
            let tasklist = data.data;
            console.log(data.data);
            
            console.log("before change" , tasklist);
            tasklist =  {
                ...tasklist,
                isListed: tasklist.listed,
            }
            console.log("after change" , tasklist);

            
            dispatch(isListedChangeAction(tasklist));
            console.log("State after dispatch:", store.getState().user.tasklists);

        })
        .catch((error) => {
            alert("Error: " + error.message); // Use `error.message` here.
        });
}


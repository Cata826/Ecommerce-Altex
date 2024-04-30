
// import React, { useState, useEffect } from 'react';
// import Calendar from 'react-calendar';
// import 'react-calendar/dist/Calendar.css';
// import axios from 'axios';

// function CalendarComponent() {
//   const [date, setDate] = useState(new Date());
//   const [users, setUsers] = useState([]);

//   // Converts the date to the format YYYY-MM-DD using local timezone
//   const formatDate = (date) => {
//     return date.toLocaleDateString('en-CA'); // Ensures the date format is YYYY-MM-DD
//   };

//   const fetchUsers = async (selectedDate) => {
//     try {
//       const formattedDate = formatDate(selectedDate);
//       const response = await axios.get(`http://localhost:8080/api/v1/login/by-last-logged-date?date=${formattedDate}`);
//       setUsers(response.data); // Assuming the data is an array of user objects
//     } catch (error) {
//       console.error('Failed to fetch users', error);
//       setUsers([]);
//     }
//   };

//   // Update users whenever the date changes
//   useEffect(() => {
//     fetchUsers(date);
//   }, [date]);

//   const onChange = newDate => {
//     setDate(newDate);
//   };

//   return (
//     <div>
//       <h2>Select a date from a popup or inline calendar</h2>
//       <div>
//         Date: <input type="text" value={formatDate(date)} readOnly />
//       </div>
//       <div>
//         <Calendar
//           onChange={onChange}
//           value={date}
//         />
//       </div>
//       <div>
//         <h3>Users Logged in on Selected Date:</h3>
//         {users.length > 0 ? (
//           <ul>
//             {users.map(user => (
//               <li key={user.id}>{user.firstName} {user.lastName} - {user.email}</li>
//             ))}
//           </ul>
//         ) : (
//           <p>No users found for this date.</p>
//         )}
//       </div>
//     </div>
//   );
// }

// export default CalendarComponent;
import React, { useState, useEffect } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import axios from 'axios';

function CalendarComponent() {
  const [date, setDate] = useState(new Date());
  const [users, setUsers] = useState([]);

  // Converts the date to the format YYYY-MM-DD using local timezone
  const formatDate = (date) => {
    return date.toLocaleDateString('en-CA'); // Ensures the date format is YYYY-MM-DD
  };
  const downloadUserXml = async (userId) => {
    try {
      const response = await axios({
        url: `http://localhost:8080/api/v1/login/${userId}/xml`,
        method: 'GET',
        responseType: 'blob', // Important
      });
  
      // Create a new Blob object using the response data of the onload object
      const blob = new Blob([response.data], { type: 'application/xml' });
  
      // Create an anchor element and dispatch a click event on it
      // to trigger a download of the blob content
      const downloadUrl = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = downloadUrl;
      link.setAttribute('download', `user_${userId}.xml`); // Any file name
      document.body.appendChild(link);
      link.click();
      link.remove(); // Clean up
    } catch (error) {
      console.error('Failed to download XML', error);
    }
  };
  
  const fetchUsers = async (selectedDate) => {
    try {
      const formattedDate = formatDate(selectedDate);
      const response = await axios.get(`http://localhost:8080/api/v1/login/by-last-logged-date?date=${formattedDate}`);
      setUsers(response.data); // Assuming the data is an array of user objects
    } catch (error) {
      console.error('Failed to fetch users', error);
      setUsers([]);
    }
  };

  const fetchUserXml = async (userId) => {
    try {
      const response = await axios.get(`http://localhost:8080/api/v1/login/${userId}/xml`, {
        headers: {
          'Accept': 'application/xml'  // Ensures that the server knows we want XML
        }
      });
      console.log(response.data); // For now, just log the XML to the console
      alert(`XML Data for User ID ${userId}: ${response.data}`);
    } catch (error) {
      console.error('Failed to fetch XML data for user', error);
      alert('Failed to fetch XML data');
    }
  };

  useEffect(() => {
    fetchUsers(date);
  }, [date]);

  const onChange = newDate => {
    setDate(newDate);
  };

  return (
    <div>
      <h2>Select a date from a popup or inline calendar</h2>
      <div>
        Date: <input type="text" value={formatDate(date)} readOnly />
      </div>
      <div>
        <Calendar
          onChange={onChange}
          value={date}
        />
      </div>
      <div>
        <h3>Users Logged in on Selected Date:</h3>
        {users.length > 0 ? (
          <ul>
            {users.map(user => (
              <li key={user.id}>
                <button onClick={() => downloadUserXml(user.id)}>
                  {user.firstName} {user.lastName} - {user.email}
                </button>
              </li>
            ))}
          </ul>
        ) : (
          <p>No users found for this date.</p>
        )}
      </div>
    </div>
  );
}

export default CalendarComponent;

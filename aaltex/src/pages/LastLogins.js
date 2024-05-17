
import React, { useState, useEffect } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import axios from 'axios';
import './CalendarComponent.css';

function CalendarComponent() {
  const [date, setDate] = useState(new Date());
  const [users, setUsers] = useState([]);

  const formatDate = (date) => {
    return date.toLocaleDateString('en-CA');
  };
  
  const downloadUserXml = async (userId) => {
    try {
      console.log('Downloading XML for user with ID:', userId); // Log the user ID
      const response = await axios({
        url: `http://localhost:8080/api/v1/login/${userId}/xml`,
        method: 'GET',
        responseType: 'blob',
      });

      const blob = new Blob([response.data], { type: 'application/xml' });
      const downloadUrl = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = downloadUrl;
      link.setAttribute('download', `user_${userId}.xml`);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      console.error('Failed to download XML', error);
    }
  };

  const fetchUsers = async (selectedDate) => {
    try {
      const formattedDate = formatDate(selectedDate);
      console.log(formattedDate)
      const response = await axios.get(`http://localhost:8080/api/v1/login/by-last-logged-date?date=${formattedDate}`);
      console.log('Response:', response.data);
      setUsers(response.data);
    } catch (error) {
      console.error('Failed to fetch users', error);
      setUsers([]);
    }
  };

  useEffect(() => {
    fetchUsers(date);
  }, [date]);

  const onChange = (newDate) => {
    setDate(newDate);
  };

  return (
    <div className="calendar-container">
      <div className="calendar-page">
        <div className="calendar-wrapper">
          <Calendar
            onChange={onChange}
            value={date}
            className="custom-calendar"
          />
        </div>
        <div className="info-wrapper">
          <div className="date-display">
            <label></label>
            <input type="text" value={formatDate(date)} readOnly className="date-input" />
          </div>
          <div>
            <h3 className="user-list-header">Users Logged in on Selected Date</h3>
            {users.length > 0 ? (
              <ul className="user-list">
                {users.map(user => (
                  <li key={user.id} className="user-item">
                    <button onClick={() => downloadUserXml(user.id)} className="download-btn">
                      {user.firstName} {user.lastName} - {user.email}
                    </button>
                  </li>
                ))}
              </ul>
            ) : (
              <p className="no-users">No users found for this date.</p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default CalendarComponent;
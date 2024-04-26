import React, { useState } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

function CalendarComponent() {
  const [date, setDate] = useState(new Date());

  const onChange = newDate => {
    setDate(newDate);
  };

  // This function converts the date to the format YYYY-MM-DD
  const formatDate = (date) => {
    return date.toISOString().split('T')[0];
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
    </div>
  );
}

export default CalendarComponent;

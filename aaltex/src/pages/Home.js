

import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useParams } from 'react-router-dom';

export default function Home() {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        loadUsers();
    }, []);

    const { id } = useParams();

    const loadUsers = async () => {
        const result = await axios.get("http://localhost:8080/api/v1/login");
        setUsers(result.data);
    };

    const deleteUser = async (id) => {
        await axios.delete(`http://localhost:8080/api/v1/login/${id}`);
        loadUsers();
    };

    return (
      <table className="table table-bordered table-hover shadow">
          <thead className="thead-dark">
              <tr>
                  <th scope="col">#</th>
                  <th scope="col">First Name</th>
                  <th scope="col">Last Name</th>
                  <th scope="col">Email</th>
                  {/* <th scope="col">Password</th> */}
                 
                   <th scope="col">Actions</th> 
              </tr>
          </thead>
          <tbody>
              {users.map((user, index) => (
                  <tr key={user.id}>
                      <td>{index + 1}</td>
                      <td>{user.firstName}</td>
                      <td>{user.lastName}</td>
                      <td>{user.email}</td>
                      {/* <td>{user.password}</td> */}
                      {/*<td>{user.personRole}</td> */}
                      <td>
                          <Link
                              className="btn btn-info btn-sm mx-1"
                              to={`/viewuser/${user.id}`}
                          >
                              View
                          </Link>
                          <Link
                              className="btn btn-primary btn-sm mx-1"
                              to={`/edituser/${user.id}`}
                          >
                              Edit
                          </Link>
                          <button
                              className="btn btn-danger btn-sm mx-1"
                              onClick={() => deleteUser(user.id)}
                          >
                              Delete
                          </button>
                      </td>
                  </tr>
              ))}
          </tbody>
      </table>
  );
}

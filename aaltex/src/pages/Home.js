import React, { useEffect, useState } from 'react'
import axios from 'axios'
import { Link ,useParams} from 'react-router-dom';
export default function Home() {

    const [users,setUsers]=useState([])

    useEffect(()=>{
        loadUsers();

    },[]);
    const {id}=useParams();
    const loadUsers=async()=>{
        const result=await axios.get("http://localhost:8080/persons");
        setUsers(result.data);
    }

    const deleteUser=async(id) =>
    {
        await axios.delete(`http://localhost:8080/person/${id}`)
        loadUsers();
    }
    return (
        <div className="container">
          <div className="py-4">
            <table className="table border shadow">
              <thead>
                <tr>
                  <th scope="col">Id</th>
                  <th scope="col">Name</th>
                  <th scope="col">Username</th>
                  <th scope="col">Email</th>
                  <th scope="col">Password</th>
                  <th scope="col">Role</th>
                  
                </tr>
              </thead>
              <tbody>
                {users.map((user, id) => (
                  <tr>
                 
                    <td>{user.id}</td>
                    <td>{user.name}</td>
                    <td>{user.username}</td>
                    <td>{user.email}</td>
                    <td>{user.password}</td>
                    <td>{user.personRole}</td>
                    <td>

                    <Link
                    className="btn btn-danger mx-2"
                    to={`/viewuser/${user.id}`}
                    >
                    View
                  </Link>
                        <Link className="btn btn-outline-danger mx-2" 
                        to={`/edituser/${user.id}`}
                        
                        >Edit</Link>
                        <Link className="btn btn-danger mx-2" 
                        onClick={()=>deleteUser(user.id)}>Delete</Link>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      );
    }
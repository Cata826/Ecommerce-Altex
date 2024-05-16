// import axios from "axios";
// import React, { useEffect,useState } from "react";
// import { Link, useParams } from "react-router-dom";

// export default function ViewUser() {
//   const [user, setUser] = useState({
//     name: "",
//     username: "",
//     email: "",
//     password: "",
//     user_id: "",
   
//   });

//   const { id } = useParams();

//   useEffect(() => {
//     loadUser();
//   }, []);

//   const loadUser = async () => {
//     const result = await axios.get(`http://localhost:8080/api/v1/login/${id}`);
//     setUser(result.data);
//   };

//   return (
//     <div className="container">
//       <div className="row">
//         <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
//           <h2 className="text-center m-4">User Details</h2>

//           <div className="card">
//             <div className="card-header">
//               Details of user id : {user.id}
//               <ul className="list-group list-group-flush">
//                 <li className="list-group-item">
//                   <b>Name:</b>
//                   {user.firstName}
//                 </li>
//                 <li className="list-group-item">
//                   <b>UserName:</b>
//                   {user.username}
//                 </li>
//                 <li className="list-group-item">
//                   <b>Email:</b>
//                   {user.email}
//                 </li>
//                 <li className="list-group-item">
//                   <b>Password:</b>
//                   {user.password}
//                 </li>
           
//               </ul>
//             </div>
//           </div>
//           <Link className="btn btn-danger my-2" to={"/admininterface"}>
//             Back to Home
//           </Link>
//         </div>
//       </div>
//     </div>
//   );
// }
import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

export default function ViewUser() {
  const [user, setUser] = useState({
    name: "",
    username: "",
    email: "",
    password: "",
    id: "",
  });

  const { id } = useParams();

  useEffect(() => {
    loadUser();
  }, []);

  const loadUser = async () => {
    const result = await axios.get(`http://localhost:8080/api/v1/login/${id}`);
    setUser(result.data);
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">User Details</h2>

          <div className="card">
            <div className="card-header">
              Details of user id : {user.id}
              <ul className="list-group list-group-flush">
                <li className="list-group-item">
                  <b>Name:</b> {user.name}
                </li>
                <li className="list-group-item">
                  <b>Username:</b> {user.username}
                </li>
                <li className="list-group-item">
                  <b>Email:</b> {user.email}
                </li>
                <li className="list-group-item">
                  <b>Password:</b> {user.password}
                </li>
              </ul>
            </div>
          </div>
          <Link className="btn btn-danger my-2" to={"/admininterface"}>
            Back to Home
          </Link>
        </div>
      </div>
    </div>
  );
}

// import axios from "axios";
// import React, { useState } from "react";
// import { Link, useNavigate } from "react-router-dom";

// export default function AddUser() {
//   let navigate = useNavigate();

//   const [user, setUser] = useState({
//     name: "",
//     username: "",
//     email: "",
//     password: "",
//   });

//   const { name, username, email ,password} = user;

//   const onInputChange = (e) => {
//     setUser({ ...user, [e.target.name]: e.target.value });
//   };

//   const onSubmit = async (e) => {
//     e.preventDefault();
//     if (user.password !== user.passwordconfirmation) {
//         alert("Password and Confirm Password must match!");
//         return; 
//       }
//     await axios.post("http://localhost:8080/api/v1/registration", user);
//     navigate("/login");
//   };

//   return (
//     <div className="container">
//       <div className="row">
//         <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
//           <h2 className="text-center m-4">Register Form</h2>

//           <form onSubmit={(e) => onSubmit(e)}>
//             <div className="mb-3">
//               <label htmlFor="Name" className="form-label">
//                 Name
//               </label>
//               <input
//                 type={"text"}
//                 className="form-control"
//                 placeholder="Enter your name"
//                 name="name"
//                 value={name}
//                 onChange={(e) => onInputChange(e)}
//               />
//             </div>
//             <div className="mb-3">
//               <label htmlFor="Username" className="form-label">
//                 Username
//               </label>
//               <input
//                 type={"text"}
//                 className="form-control"
//                 placeholder="Enter your username"
//                 name="username"
//                 value={username}
//                 onChange={(e) => onInputChange(e)}
//               />
//             </div>
//             <div className="mb-3">
//               <label htmlFor="Email" className="form-label">
//                 E-mail
//               </label>
//               <input
//                 type={"text"}
//                 className="form-control"
//                 placeholder="Enter your e-mail address"
//                 name="email"
//                 value={email}
//                 onChange={(e) => onInputChange(e)}
//               />
//             </div>
//             <div className="mb-3">
//               <label htmlFor="Password" className="form-label">
//                 Password
//               </label>
//               <input
//                 type={"text"}
//                 className="form-control"
//                 placeholder="Enter your password"
//                 name="password"
//                 value={password}
//                 onChange={(e) => onInputChange(e)}
//               />
//             </div>
//             <div className="mb-3">
//               <label htmlFor="PasswordConfirmation" className="form-label">
//                 Password confirmation
//               </label>
//               <input
//                 type={"text"}
//                 className="form-control"
//                 placeholder="Confirm your password"
//                 name="passwordconfirmation"
//                 onChange={(e) => onInputChange(e)}
//               />
//             </div>
//             <button type="submit" className="btn btn-outline-danger">
//               Register
//             </button>
//             <Link className="btn btn-outline-danger mx-2" to="/admininterface">
//               Cancel
//             </Link>
//           </form>
//         </div>
//       </div>
//     </div>
//   );
// }
import axios from "axios";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export default function AddUser() {
  let navigate = useNavigate();

  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    
  });

  const [passwordConfirmation, setPasswordConfirmation] = useState("");

  const onInputChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  const onPasswordConfirmationChange = (e) => {
    setPasswordConfirmation(e.target.value);
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    if (user.password !== passwordConfirmation) {
      alert("Password and Confirm Password must match!");
      return;
    }
    try {
      await axios.post("http://localhost:8080/api/v1/registration", user);
      navigate("/login");
    } catch (error) {
      console.error("There was an error registering the user", error);

    }
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Register Form</h2>

          <form onSubmit={onSubmit}>
            <div className="mb-3">
              <label htmlFor="FirstName" className="form-label">
                First Name
              </label>
              <input
                type="text"
                className="form-control"
                placeholder="Enter your first name"
                name="firstName"
                value={user.firstName}
                onChange={onInputChange}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="LastName" className="form-label">
                Last Name
              </label>
              <input
                type="text"
                className="form-control"
                placeholder="Enter your last name"
                name="lastName"
                value={user.lastName}
                onChange={onInputChange}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="Email" className="form-label">
                E-mail
              </label>
              <input
                type="email" 
                className="form-control"
                placeholder="Enter your e-mail address"
                name="email"
                value={user.email}
                onChange={onInputChange}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="Password" className="form-label">
                Password
              </label>
              <input
                type="password" 
                className="form-control"
                placeholder="Enter your password"
                name="password"
                value={user.password}
                onChange={onInputChange}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="PasswordConfirmation" className="form-label">
                Password Confirmation
              </label>
              <input
                type="password"
                className="form-control"
                placeholder="Confirm your password"
                name="passwordConfirmation"
                value={passwordConfirmation}
                onChange={onPasswordConfirmationChange}
              />
            </div>
            <button type="submit" className="btn btn-outline-danger">
              Register
            </button>
            <Link className="btn btn-outline-danger mx-2" to="/login">
              Cancel
            </Link>
          </form>
        </div>
      </div>
    </div>
  );
}

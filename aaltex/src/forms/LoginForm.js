import axios from "axios";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export default function LoginForm() {
  const navigate = useNavigate();
  const [user, setUser] = useState({
    email: "",
    password: "",
  });

  const { email, password } = user;

  const onInputChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };
  // When logging in, assuming you receive userId from your login response

  const onSubmit = async (e) => {
    e.preventDefault();

    try {

      const loginResponse = await axios.post(`http://localhost:8080/api/v1/login/login`, user);

      const roleResponse = await axios.get(`http://localhost:8080/api/v1/login/role`, {
        params: { email: user.email }
      });
      localStorage.setItem('userId', loginResponse.userId);

     // localStorage.setItem('userId', loginResponse.data.userId);

      const enableResponse = await axios.get(`http://localhost:8080/api/v1/login/enable`, {
        params: { email: user.email }
      });

      const idResponse = await axios.get(`http://localhost:8080/api/v1/login/id`, {
        params: { email: user.email }
      });
      const dateResponse = await axios.get(`http://localhost:8080/api/v1/login/update-last-logged/${idResponse.data}`);
      console.log('User ID from response:', idResponse.data);
      if(enableResponse.data == 0)
      {
        alert("NU TI-AI CONFIRMAT CONTU , VERIFICATI MAIL-UL");
      }else{
      if (roleResponse.data === "ADMIN") {
        navigate("/admin");
      } else {
        navigate(`/user/${idResponse.data}`);
      }}

    } catch (error) {
      console.error("Error during login or fetching role:", error.response ? error.response.data : error);
    }
    
  };

  return (
    <div className="container">
      
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Login Form</h2>

          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="Email" className="form-label">
                E-mail
              </label>
              <input
                type="text"
                className="form-control"
                placeholder="Enter your e-mail address"
                name="email"
                value={email}
                onChange={(e) => onInputChange(e)}
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
                value={password}
                onChange={(e) => onInputChange(e)}
              />
            </div>

            <button type="submit" className="btn btn-outline-danger mx-2">
              Login
            </button>
            <Link className="btn btn-outline-danger mx-2" to="/register">
              Register
            </Link>
            <Link className="btn btn-outline-danger mx-2" to="/admininterface">
              Cancel
            </Link>
          </form>
        </div>
        
      </div>
    </div>
  );
}

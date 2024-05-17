
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "./LoginForm.css";

export default function LoginForm() {
  const navigate = useNavigate();
  const [user, setUser] = useState({ email: "", password: "" });
  const { email, password } = user;

  const onInputChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    try {
      const loginResponse = await axios.post("http://localhost:8080/api/v1/login/login", user);
      const roleResponse = await axios.get("http://localhost:8080/api/v1/login/role", { params: { email: user.email } });
      const enableResponse = await axios.get("http://localhost:8080/api/v1/login/enable", { params: { email: user.email } });
      const idResponse = await axios.get("http://localhost:8080/api/v1/login/id", { params: { email: user.email } });
      await axios.get(`http://localhost:8080/api/v1/login/update-last-logged/${idResponse.data}`);
      localStorage.setItem("userId", loginResponse.userId);

      if (enableResponse.data === 0) {
        alert("You have not confirmed your account. Please check your email.");
      } else {
        if (roleResponse.data === "ADMIN") {
          navigate("/admin");
        } else {
          navigate(`/user/${idResponse.data}`);
        }
      }
    } catch (error) {
      console.error("Error during login or fetching role:", error.response ? error.response.data : error);
    }
  };

  return (
    <div className="login-container">
      <div className="login-left">
        <div className="curved-background"></div>

        <div className="circles circle1"></div>
        <div className="circles circle2"></div>
        <div className="circles circle3"></div>
      </div>
      <div className="login-box">
       <img src="data:image/png;base64,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" alt="Logo" className="logo" />

        <h2 className="welcome-text">Welcome to you</h2>
        <form onSubmit={onSubmit}>
          <div className="input-group">
            <span className="icon">&#128100;</span>
            <input
              type="text"
              className="form-control"
              placeholder="Username"
              name="email"
              value={email}
              onChange={onInputChange}
            />
          </div>
          <div className="input-group">
            <span className="icon">&#128274;</span>
            <input
              type="password"
              className="form-control"
              placeholder="Password"
              name="password"
              value={password}
              onChange={onInputChange}
            />
          </div>
          <div className="forgot-password">
            <Link to="/forgot-password">Forgot password?</Link>
          </div>
          <button type="submit" className="btn login-button">Login</button>
        </form>
      </div>
    </div>
  );
}

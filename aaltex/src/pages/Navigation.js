
import React from "react";
import { Link } from "react-router-dom";
import "./Navigation.css"; // Assuming you save your CSS in this file

function Navigation() {
  return (
    <div className="navigation-container">
      <h2 className="navigation-title">Admin Panel</h2>
      <div className="navigation-link">
        <Link to="/addproduct">
          <button className="btn btn-futuristic">Add Product</button>
        </Link>
      </div>
      <div className="navigation-link">
        <Link to="/admininterface">
          <button className="btn btn-futuristic">See All Users</button>
        </Link>
      </div>
      <div className="navigation-link">
        <Link to="/products">
          <button className="btn btn-futuristic">See All Products</button>
        </Link>
      </div>
      <div className="navigation-link">
        <Link to="/lastlogin">
          <button className="btn btn-futuristic">See Last Logins</button>
        </Link>
      </div>
    </div>
  );
}

export default Navigation;

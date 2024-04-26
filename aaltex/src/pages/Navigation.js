
import React from "react";
import { Link } from "react-router-dom";

function Navigation() {
  return (
    <div>
      <h2>Navigation</h2>
      {/* <div>
        <Link to="/adduser">
        <button className="btn btn-danger" style={{ marginRight: '10px' }}> Add user</button>
           </Link>
      </div> */}
      <div>
        <Link to="/addproduct">   
        <button className="btn btn-danger" style={{ marginRight: '10px' }}>Add product</button>
        </Link>
      </div>
      <div>
        <Link to="/admininterface">
        <button className="btn btn-danger" style={{ marginRight: '10px' }}> See all users</button>
        </Link>
      </div>
      <div>
        <Link to="/products">
        <button className="btn btn-danger" style={{ marginRight: '10px' }}> See all products</button>
            </Link>
      </div>
      <div>
        <Link to="/lastlogin">
        <button className="btn btn-danger" style={{ marginRight: '10px' }}> See last logins</button>
            </Link>
      </div>
    </div>
  );
}

export default Navigation;

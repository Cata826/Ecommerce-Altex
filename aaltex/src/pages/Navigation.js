
// import React from "react";
// import { Link } from "react-router-dom";

// function Navigation() {
//   return (
//     <div>
//       <h2>Navigation</h2>
//       {/* <div>
//         <Link to="/adduser">
//         <button className="btn btn-danger" style={{ marginRight: '10px' }}> Add user</button>
//            </Link>
//       </div> */}
//       <div>
//         <Link to="/addproduct">   
//         <button className="btn btn-danger" style={{ marginRight: '10px' }}>Add product</button>
//         </Link>
//       </div>
//       <div>
//         <Link to="/admininterface">
//         <button className="btn btn-danger" style={{ marginRight: '10px' }}> See all users</button>
//         </Link>
//       </div>
//       <div>
//         <Link to="/products">
//         <button className="btn btn-danger" style={{ marginRight: '10px' }}> See all products</button>
//             </Link>
//       </div>
//       <div>
//         <Link to="/lastlogin">
//         <button className="btn btn-danger" style={{ marginRight: '10px' }}> See last logins</button>
//             </Link>
//       </div>
//     </div>
//   );
// }

// export default Navigation;


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

// import React from "react";
// import { Link } from "react-router-dom";
// import "./Navigation.css"; // Assuming you save your CSS in this file

// function Navigation() {
//   return (
//     <div className="navigation-container">
//       <h2 className="navigation-title">Navigation</h2>
//       <div className="navigation-link">
//         <Link to="/addproduct">
//           <button className="btn btn-danger">Add product</button>
//         </Link>
//       </div>
//       <div className="navigation-link">
//         <Link to="/admininterface">
//           <button className="btn btn-danger">See all users</button>
//         </Link>
//       </div>
//       <div className="navigation-link">
//         <Link to="/products">
//           <button className="btn btn-danger">See all products</button>
//         </Link>
//       </div>
//       <div className="navigation-link">
//         <Link to="/lastlogin">
//           <button className="btn btn-danger">See last logins</button>
//         </Link>
//       </div>
//     </div>
//   );
// }

// export default Navigation;

// import React from 'react'
// import { Link } from 'react-router-dom'
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
// import { faHeart } from '@fortawesome/free-solid-svg-icons'

// export default function NavBar() {
//   return (
//     <div>

// <nav className="navbar navbar-expand-lg navbar-dark bg-danger">
//   <div className="container-fluid">


//     <Link className="navbar-brand" to="/">
//         Altex
//     </Link>
//     <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
//       <span className="navbar-toggler-icon"></span>
//     </button>
    

//     <div style={{ margin: '20px' }}>
//         <Link to="/register">
//           <button className="btn btn-danger" style={{ marginRight: '10px' }}>Register</button>
//         </Link>
        
//         <Link to="/login">
//           <button className="btn btn-danger">Login</button>
//         </Link>


//       </div>
  
    
//   </div>
// </nav>

//     </div>
//   )
// }
import React from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSignInAlt, faUserPlus } from '@fortawesome/free-solid-svg-icons';

export default function NavBar() {
    return (
        <div>
            <nav className="navbar navbar-expand-lg navbar-dark bg-danger">
                <div className="container-fluid">
                    <Link className="navbar-brand" to="/">
                        Altex
                    </Link>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                            <li className="nav-item">
                                <Link className="nav-link" to="/register">
                                    <FontAwesomeIcon icon={faUserPlus} /> Register
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/login">
                                    <FontAwesomeIcon icon={faSignInAlt} /> Login
                                </Link>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
    );
}

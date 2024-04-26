import React from 'react'
import { Link } from 'react-router-dom'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHeart } from '@fortawesome/free-solid-svg-icons'

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
    

    <div style={{ margin: '20px' }}>
        {/* <Link to="/register">
          <button className="btn btn-danger" style={{ marginRight: '10px' }}>Register</button>
        </Link>
        
        <Link to="/login">
          <button className="btn btn-danger">Login</button>
        </Link> */}
        <Link to="/wishlist">
          <FontAwesomeIcon icon={faHeart} size="1x" color="yellow" />
        </Link>
    

      </div>
  
    
  </div>
</nav>

    </div>
  )
}

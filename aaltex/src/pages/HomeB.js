import React, { useEffect, useState } from 'react'
import axios from 'axios'
import { Link ,useParams} from 'react-router-dom';
export default function HomeB() {

    const [products,setProducts]=useState([])

    useEffect(()=>{
        loadProducts();

    },[]);
    const {id}=useParams();
    const loadProducts=async()=>{
        const result=await axios.get("http://localhost:8080/api/v1/products");
        setProducts(result.data);
    }

    const deleteUser=async(id) =>
    {
        await axios.delete(`http://localhost:8080/api/v1/products/${id}`)
        loadProducts();
    }
    return (
        <div className="container">
          <div className="py-4">
            <table className="table border shadow">
              <thead>
                <tr>
                  <th scope="col">Id</th>
                  <th scope="col">Name</th>
                </tr>
              </thead>
              <tbody>
                {products.map((product, id) => (
                  <tr>
                 
                    <td>{product.id}</td>
                    <td>{product.name}</td>
                    {/* <td>{user.username}</td>
                    <td>{user.email}</td>
                    <td>{user.password}</td>
                    <td>{user.personRole}</td> */}
                    <td>

                    <Link
                    className="btn btn-danger mx-2"
                    to={`/viewproduct/${product.id}`}
                    >
                    View
                  </Link>
                        <Link className="btn btn-outline-danger mx-2" 
                        to={`/editproduct/${product.id}`}
                        
                        >Edit</Link>
                        <Link className="btn btn-danger mx-2" 
                        onClick={()=>deleteUser(product.id)}>Delete</Link>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      );
    }
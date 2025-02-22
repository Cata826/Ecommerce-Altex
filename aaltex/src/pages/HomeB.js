
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useParams } from 'react-router-dom';
import './HomeB.css'; 

export default function HomeB() {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        loadProducts();
    }, []);

    const { id } = useParams();
    
    const loadProducts = async () => {
        const result = await axios.get("http://localhost:8080/api/v1/products");
        setProducts(result.data);
    };

    const deleteUser = async (id) => {
        await axios.delete(`http://localhost:8080/api/v1/products/${id}`);
        loadProducts();
    };

    return (
        <div className="container-fluid table-container">
            <div className="table-responsive">
                <table className="table table-bordered table-hover shadow">
                    <thead className="thead-dark">
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Name</th>
                            {/* <th scope="col">Actions</th> */}
                            <th scope="col">Price</th>
                            <th scope="col">Quantity</th>
                            <th scope="col">Short description</th>
                            <th scope="col">Long desctiption</th>
                            <th scope="col">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {products.map((product) => (
                            <tr key={product.id}>
                                <td>{product.id}</td>
                                <td>{product.name}</td>
                                <td>{product.pret}</td>
                                <td>{product.quantity}</td>
                                <td>{product.short_description}</td>
                                <td>{product.long_description}</td>
                                <td>
                                    <Link
                                        className="btn btn-info btn-sm mx-1"
                                        to={`/viewproduct/${product.id}`}
                                    >
                                        View
                                    </Link>
                                    <Link
                                        className="btn btn-primary btn-sm mx-1"
                                        to={`/editproduct/${product.id}`}
                                    >
                                        Edit
                                    </Link>
                                    <button
                                        className="btn btn-danger btn-sm mx-1"
                                        onClick={() => deleteUser(product.id)}
                                    >
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}



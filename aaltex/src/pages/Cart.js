



import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faShoppingCart } from '@fortawesome/free-solid-svg-icons';
import { FaShoppingCart } from 'react-icons/fa';

export default function Cart() {
    const [products, setProducts] = useState([]);
    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        console.log('UserID from URL:', id);
        loadProductsForOrder();
    }, [id]);

    const loadProductsForOrder = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/v1/cart/user/${id}/products`);
            setProducts(response.data);
            console.log(response.data);
        } catch (error) {
            console.error("Error loading products for wishlist:", error);
        }
    };

    const submitDetails = () => {
        navigate(`/payment/${id}`);
    };
    return (
        <div className="container-fluid" style={{ width: '100vw', height: 'auto' }}>
            <h2>Products that were added to the cart</h2>
            <div style={{ width: '100%', height: '400px', overflowY: 'auto' }}>
                <table className="table border shadow" style={{ width: '100%' }}>
                    <thead>
                        <tr>
                            <th scope="col">Product Name</th>
                            <th scope="col">Price</th>
                            <th scope="col">Short Description</th>
                            <th scope="col">Image</th>
                        </tr>
                    </thead>
                    <tbody>
                        {products.map((product, index) => (
                            <tr key={index}>
                                <td>{product.name}</td>
                                <td>${product.pret}</td>
                                <td>{product.short_description}</td>
                                <td><img src={product.imageUrl} alt={product.name} style={{ width: '100px', height: 'auto' }} /></td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <button
                onClick={submitDetails}
                style={{
                    backgroundColor: 'yellow',
                    color: 'black',
                    fontSize: '16px',
                    padding: '10px',
                    marginTop: '20px',
                    border: 'none',
                    borderRadius: '5px',
                    cursor: 'pointer'
                }}
            >
                Start completing the shipping process
            </button>
        </div>
    );
};



import React, { useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import './AddProduct.css';

function AddProduct() {
    const navigate = useNavigate();
    const [productName, setProductName] = useState('');
    const [shortDescription, setShortDescription] = useState('');
    const [longDescription, setLongDescription] = useState('');
    const [pret, setPret] = useState('');
    const [quantity, setQuantity] = useState('');
    const [categoryNames, setCategoryNames] = useState('');
    const [message, setMessage] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        const productData = {
            name: productName,
            short_description: shortDescription,
            long_description: longDescription,
            pret: parseFloat(pret),
            quantity: parseInt(quantity, 10),
            categories: categoryNames.split(',').map(name => ({ name: name.trim() })),
        };

        try {
            const response = await axios.post("http://localhost:8080/api/v1/products/", productData);
            setMessage(`Product "${response.data.name}" added successfully!`);
            navigate('/');
        } catch (error) {
            setMessage('Error adding product.');
            console.error('Error:', error);
        }
    };

    return (
        <div className="container">
            <div className="row justify-content-center">
                <div className="col-md-8 border rounded p-4 mt-4 shadow">
                    <h2 className="text-center mb-4">Add Product</h2>
                    <form onSubmit={handleSubmit}>
                        <div className="mb-3">
                            <label htmlFor="productName" className="form-label">Product Name:</label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter product name"
                                value={productName}
                                onChange={e => setProductName(e.target.value)}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="shortDescription" className="form-label">Short Description:</label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter short description"
                                value={shortDescription}
                                onChange={e => setShortDescription(e.target.value)}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="longDescription" className="form-label">Long Description:</label>
                            <textarea
                                className="form-control"
                                placeholder="Enter long description"
                                value={longDescription}
                                onChange={e => setLongDescription(e.target.value)}
                            />
                        </div>
                        <div className="row">
                            <div className="col-md-6">
                                <div className="mb-3">
                                    <label htmlFor="pret" className="form-label">Price:</label>
                                    <input
                                        type="number"
                                        className="form-control"
                                        placeholder="Enter price"
                                        value={pret}
                                        onChange={e => setPret(e.target.value)}
                                    />
                                </div>
                            </div>
                            <div className="col-md-6">
                                <div className="mb-3">
                                    <label htmlFor="quantity" className="form-label">Quantity:</label>
                                    <input
                                        type="number"
                                        className="form-control"
                                        placeholder="Enter quantity"
                                        value={quantity}
                                        onChange={e => setQuantity(e.target.value)}
                                    />
                                </div>
                            </div>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="categoryNames" className="form-label">Categories (comma-separated):</label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter category names"
                                value={categoryNames}
                                onChange={e => setCategoryNames(e.target.value)}
                            />
                        </div>
                        <div className="d-flex justify-content-end">
                            <button type="submit" className="btn btn-danger me-2">Add Product</button>
                            <Link className="btn btn-outline-danger" to="/">Cancel</Link>
                        </div>
                    </form>
                    {message && <p className="mt-3">{message}</p>}
                </div>
            </div>
        </div>
    );
}

export default AddProduct;

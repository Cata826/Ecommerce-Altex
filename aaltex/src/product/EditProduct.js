import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

export default function EditProduct() {
  let navigate = useNavigate();

  const { id } = useParams();

  const [product, setProduct] = useState({
    name: "",
  });

  const { name } = product;

  const onInputChange = (e) => {
    setProduct({ ...product, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    loadProduct();
  }, []);

  const onSubmit = async (e) => {
    e.preventDefault();
    await axios.put(`http://localhost:8080/api/v1/products/${id}`,product);

    setProduct({"name" : "product.name" });
   console.log(product.name);
    navigate("/products");
  };

const loadProduct = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/v1/products/${id}`);
      const { name } = response.data; // Assuming response.data contains product details
      //console.log(response.data);
   setProduct(response.data);
    } catch (error) {
      console.error("Error fetching product:", error);
    }
  };
  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Edit User</h2>

          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="Name" className="form-label">
                Name
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter your name"
                name="name"
                value={name}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <button type="submit" className="btn btn-outline-danger">
              Submit
            </button>
            <Link className="btn btn-outline-danger mx-2" to="/admin">
              Cancel
            </Link>
          </form>
        </div>
      </div>
    </div>
  );
}
// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import { useParams } from 'react-router-dom';

// export default function Wishlist() {
//     const [wishlistItems, setWishlistItems] = useState([]);
//   //  const { userId } = useParams(); // Extracting userId from URL
//     const { id } = useParams();

//     useEffect(() => {
//       console.log('UserID from URL:', id);
//       loadWishlistItems();
//     }, [id]);
  
//     // useEffect(() => {
//     //     loadWishlistItems();
//     // }, [userId]); // Reload when userId changes

//     const loadWishlistItems = async () => {
//         try {
//             const response = await axios.get(`http://localhost:8080/api/v1/wishlist/user/${id}`);
//             setWishlistItems(response.data);
//             console.log(response.data);
//         } catch (error) {
//             console.error("Error loading wishlist items:", error);
//         }
//     };

//     return (
//         <div className="container">
//             <h2>User's Wishlist</h2>
//             <table className="table border shadow">
//                 <thead>
//                     <tr>
//                         <th scope="col">Wishlist ID</th>
//                         <th scope="col">User ID</th>
//                         <th scope="col">Product ID</th>
//                         <th scope="col">Product Name</th>
//                         <th scope="col">Actions</th>
//                     </tr>
//                 </thead>
//                 <tbody>
//                     {wishlistItems.map((item, index) => (
//                         <tr key={index}>
//                             <td>{item.id}</td>
//                             {/* <td>{item.user.id}</td>
//                             <td>{item.product.id}</td> */}
//                             {/* <td>{item.product.name}</td> */}
//                             <td>
//                                 {/* Actions here, like remove from wishlist */}
//                             </td>
//                         </tr>
//                     ))}
//                 </tbody>
//             </table>
//         </div>
//     );
// }
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

export default function Wishlist() {
    const [products, setProducts] = useState([]); // Changed state to 'products'
    const { id } = useParams(); // Assuming 'id' is the userId obtained from the URL

    useEffect(() => {
        console.log('UserID from URL:', id);
        loadProductsForWishlist();
    }, [id]);
  
    const loadProductsForWishlist = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/v1/wishlist/user/${id}/products`);
            setProducts(response.data); // Setting the products returned from the API
            console.log(response.data);
        } catch (error) {
            console.error("Error loading products for wishlist:", error);
        }
    };

    return (
        <div className="container">
            <h2>User's Wishlist Products</h2>
            <table className="table border shadow">
                <thead>
                    <tr>
                        <th scope="col">Product ID</th>
                        <th scope="col">Product Name</th>
                        <th scope="col">Price</th>
                        <th scope="col">Short Description</th>
                        {/* ... Include other product details as necessary ... */}
                        <th scope="col">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {products.map((product, index) => (
                        <tr key={index}>
                            <td>{product.id}</td>
                            <td>{product.name}</td>
                            <td>{product.short_description}</td>
                            <td>{product.pret}</td>
                            {/* Render other product details as necessary */}
                            <td>
                                {/* Actions here, like remove from wishlist */}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

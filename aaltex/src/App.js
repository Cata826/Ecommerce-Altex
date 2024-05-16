import './App.css';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import NavBar from './layout/NavBar';
import Home from './pages/Home'
import { BrowserRouter as Router,Route,Routes}from 'react-router-dom';
import AddUser from './users/AddUser';
import ViewUser from './users/ViewUser';
import ViewProduct from './product/ViewProduct.js';
import EditUser from './users/EditUser';
import First from './pages/First.js';
import RegisterForm from './forms/RegisterForm.js'
import LoginForm from './forms/LoginForm.js'
// import Home2 from './pages/Home2.js';
import AddProduct from './product/AddProduct.js';
import Navigation from './pages/Navigation.js';
import HomeB from './pages/HomeB.js';
import EditProduct from './product/EditProduct.js';
import Home2B from './see/Home2B.js';
import Wishlist from './pages/Wishlist.js'
import Order from './pages/Cart.js';
import Cart from './pages/Cart.js';
import Chat from './Chat.js';
import Card from './pages/Card.js'
import LastLogins from './pages/LastLogins.js'
// import Footer from './layout/Footer.js';
function App() {
  return (
    <div className="App">
     <Router>

     <NavBar/>


    <Routes>
    <Route exact path="/user/:id" element={<Home2B/>}/>
    <Route exact path="/cart/:id" element={<Cart/>}/>
    <Route exact path="/order/:id" element={<Order/>}/>
    <Route exact path="/" element={<First/>}/>
    <Route exact path="/products" element={<HomeB />}/>
    <Route exact path="/admin" element={<Navigation/>}/>
    <Route exact path="/register" element={<RegisterForm/>}/>
    <Route exact path="/login" element={<LoginForm/>}/>
    <Route exact path="/admininterface" element={<Home/>}/>
    <Route exact path="/addproduct" element={<AddProduct/>}/>
    {/* <Route exact path="/userinterface" element={<Home2/>}/> */}
    <Route exact path="/adduser" element={<AddUser/>}/>
    <Route exact path="/edituser/:id" element={<EditUser/>}/>
    <Route exact path="/editproduct/:id" element={<EditProduct/>}/>
    <Route exact path="/viewuser/:id" element={<ViewUser/>}/>
    <Route exact path="/wishlist/:id" element={<Wishlist/>}/> 
    <Route exact path="/viewproduct/:id/:di" element={<ViewProduct/>}/> 
    <Route exact path="/viewproduct/:id/" element={<ViewProduct/>}/> 
    {/* <Route exact path="/chat" element={<AppChat/>}/>  */}
    <Route exact path="/chat/:id" element={<Chat/>}/>
    <Route exact path="/lastlogin" element={<LastLogins/>}/>
    <Route exact path="/payment/:id" element={<Card/>}/>
    </Routes>
    {/* <Footer/> */}
     </Router>
  

    </div>
  );
}

export default App;

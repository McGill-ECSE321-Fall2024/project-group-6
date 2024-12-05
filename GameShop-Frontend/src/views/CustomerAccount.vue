<!-- Author: Annabelle -->

<template>
  <link
      href="https://cdnjs.cloudflare.com/ajax/libs/boxicons/2.1.4/css/boxicons.min.css"
      rel="stylesheet"
    />
  <div>
    <!-- Header Section -->
    <header>
      <nav class="navbar">
        <div class="logo">
          <h2>GameShop</h2>
        </div>
        <div class="navmenu">
        </div>
          <div class="user-options">
            <div class="dropdown">
              <button class="dropbtn"><img src="../assets/account.png" class="account-img"></button>
              <div class="nav-buttons">                    
                    <button @click="goToCustomerMainPage">Home</button>
                    <button @click="goToCustomerOrders">Orders</button>
                    <button @click="logout">Sign out</button>
                </div>
            </div>
            <button @click="goToCustomerWishlist"><img src="../assets/White-Heart.png" alt="WishList" class="wishlist-img" @click="goToCustomerWishlist"></button>
            <button @click="goToCart"><img src="../assets/pngaaa.com-5034351.png" alt="Cart" class="cart-img" @click="goToCustomerCart"></button>
          </div>
      </nav>
    </header>

    <!-- Account Info Section -->
    <div class="account-page">
      <div v-if="showPopup" class="popup">
        {{ popupMessage }}
      </div>
      <div class="account-info">
        <h1>Account Details</h1>
        <div class="form">
          <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" v-model="account.username" required />
          </div>
          <div class="form-group">
            <label for="email">Email Address</label>
            <input type="email" id="email" v-model="account.email" required />
          </div>
          <div class="form-group">
            <label for="phone">Phone Number</label>
            <input type="text" id="phone" v-model="account.phone" required/>
          </div>
          <div class="form-group">
            <label for="shippingAddress">Address</label>
            <input type="text" id="shippingAddress" v-model="account.shippingAddress" required />
          </div>
          <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" v-model="account.password" required/>
          </div>
        </div>
        <button @click="updateAccount" class="btn-update">
          Update Account
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { RouterLink } from "vue-router";

export default {
  props: ['customerId', 'loggedIn'],
  data() {
    return {
      customerID: 0,
      account: {
        username: "",
        email: "",
        phone: "",
        shippingAddress: "",
        password: "",
      },
      popupMessage: "",
      showPopup: false,
    };
  },
  methods: {
    isLoggedIn(){
        return this.loggedIn;
    },
    async fetchAccountInfo() {
      try {
        const response = await axios.get(
          `http://localhost:8080/customers/${this.customerID}`
        );
        this.account = response.data;
        debugger
        this.account.shippingAddress = response.data.shippingAddress;
      } catch (error) {
        console.error("Failed to fetch account details:", error);
        alert("Failed to load account details. Please try again later.");
      }
    },
    async updateAccount() {
      if(!this.account.username||!this.account.email||!this.account.shippingAddress||!this.account.password||!this.account.phone){
        this.popupMessage = "Please fill in all the fields";
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);
        return;
      }
      try {
        await axios.put(`http://localhost:8080/customers/${this.customerID}`, this.account);
        
        await this.fetchAccountInfo();
        this.popupMessage = "Account updated successfully!";
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);
      } catch (error) {
        this.popupMessage = "Error saving the changes, please try again.";
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);
      }
    },
    async goToCustomerHome() { //nav method
      this.$router.push({
          name: 'customer-homepage',
          params: {
            employeeId: this.customerID,
            loggedIn: true
          }
        });        
    },
    async goToCustomerMainPage(){ //nav method
        this.$router.push({
          name: 'customer-homepage',
          params: {
            customerId: this.customerId,
            loggedIn: true
          }  
        });
    },
    async goToCart() { //nav method
      this.$router.push({
          name: 'customer-cart',
          params: {
            customerId: this.customerId,
            loggedIn: true
          }         
        }); 
    },
    async goToCustomerWishlist() { //nav method
      this.$router.push({
          name: 'customer-wishlist',
          params: {
            customerId: this.customerId,
            loggedIn: true
          }
          
        });
    }, 
    async goToCustomerOrders() { //nav method
      this.$router.push({
          name: 'customer-orders',
          params: {
            customerId: this.customerId,
            loggedIn: true
          }
        }); 
    },   
    logout() {
        this.$router.push('/'); //go back to homepage
    },
  },
  created() {
      if (!this.isLoggedIn()) { //ensure user is a custoner and is logged in
        this.$router.push({ name: "sign in" });
        alert("Please log in before accessing this page.");
      } 
      else {
        this.customerID = this.customerId; 
        this.fetchAccountInfo();
      }   
    },
};
</script>

<style scoped>
/* General Styles */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "poppins";
}

body {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  overflow-x: hidden; 
}

/* Navbar Styles */
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 80px;
  background: #1033a4;
  padding: 0 20px;
}

.navbar h2 {
  color: #fff;
  font-size: 25px;
}

.navmenu {
  display: flex;
  align-items: center;
}

.search-box {
  margin-right: 20px;
  position: relative;
}

.search-box .search {
  width: 300px;
  padding: 8px;
  border-radius: 20px;
  border: none;
}

.search-box .bx-search {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  background: #1140d9;
  color: #fff;
  padding: 8px;
  border-radius: 50%;
}

.navmenu img {
  width: 40px;
  margin-left: 20px;
}

.account-img {
  padding-top: -2px;

}

.user-options {
  display: flex;
  /* Aligns child elements (buttons) horizontally */

  /* Adds spacing between buttons (adjust as needed) */
  align-items: center;
  /* Vertically aligns buttons if needed */
}

.user-options button {
  background: none;
  /* Remove default button background */
  border: none;
  /* Remove default button border */
  padding: 0;
  /* Remove padding around buttons */
  cursor: pointer;
}

.user-options img {
  margin-top: 15px;
  margin-right: 10px;
  align-items: center;
  width: 40px;
}

.dropdown .nav-buttons {
  display: none;
  /* Initially hide dropdown content */
  position: absolute;
  background-color: rgba(255, 255, 255, 0.906);
  background: #ffff;

  color: #ffff;
  z-index: 1;
}

.dropdown:hover .nav-buttons {
  display: block;
  /* Show dropdown on hover */
  border: solid;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.nav-buttons {
  display: flex;
  align-items: center;
}

.nav-buttons button {
font-size: 1rem;
color: #1033a4;
border: none;
padding: 0.5rem 1rem;
border-radius: 5px;
cursor: pointer;
text-align: center;
/* Centers the text horizontally */
height: 50px;
/* Set a fixed height to ensure vertical centering */
display: flex;
justify-content: center;
align-items: center;
/* Centers the button text vertically */
}

.nav-buttons button img {
padding-bottom: 15px;
padding-left: 10px;

}

.nav-buttons button:hover {
background-color: #eff2f1;
}

.nav-buttons {
padding: 10px;
}

/* Account Info Page Styles */
.account-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh; 
  width: 100%; 
  padding: 20px; 
  overflow-x: hidden; 
  background-color: #f9f9f9;
}

.account-info {
  max-width: 600px; 
  width: 100%; 
  margin: auto; 
  padding: 20px;
  border-radius: 20px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  background-color: #fff;
}

.account-info h1 {
  text-align: center;
  color: #1033a4;
}

.popup {
  position: fixed;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  background-color: #eee;
  color: #000000;
  padding: 10px 20px;
  border-radius: 5px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  font-weight: bold;
  z-index: 0;
}
.form {
  margin-top: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  font-weight: bold;
  display: block;
  margin-bottom: 5px;
  color: #1033a4;
}

.form-group input {
  width: 100%;
  padding: 10px;
  border-radius: 20px;
  border: 1px solid #ccc;
}

.btn-update {
  width: 100%;
  padding: 10px;
  border-radius: 20px;
  background: #49d8b9;
  color: #fff;
  border: none;
  cursor: pointer;
  font-size: 1rem;
  margin-top: 10px;
}

.btn-update:hover {
  background: #1033a4;
  color: #fff;
}
</style>
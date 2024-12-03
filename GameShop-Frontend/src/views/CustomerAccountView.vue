<template>
  <div>
    <!-- Header Section -->
    <header>
      <nav class="navbar">
        <div class="logo">
          <h2>GameShop</h2>
        </div>
        <div class="navmenu">
          <div class="search-box">
            <input type="search" v-model="searchQuery" class="search" placeholder="Search game..." />
            <i class="bx bx-search" @click="searchByName"></i>
          </div>
        </div>
          <div class="user-options">
            <button @click="goToCart"><img src="../assets/pngaaa.com-5034351.png" alt="Cart" class="cart-img" @click="goToCustomerCart"></button>
            <button @click="goToCustomerWishlist"><img src="../assets/White-Heart.png" alt="WishList" class="wishlist-img" @click="goToCustomerWishlist"></button>
            <div class="dropdown">
                <button class="dropbtn"><img src="../assets/person-circle.svg" alt="Account" class="account-img"></button>
                <div class="nav-buttons">
                    <button @click="goToCustomerOrders" class="order-btn">Orders</button>
                    <button @click="logout" class="logout-btn">Logout</button>
                </div>
            </div>
        </div>
      </nav>
    </header>

    <!-- Account Info Section -->
    <div class="account-page">
      <div class="account-info">
        <h1>Account Details</h1>
        <div class="form">
          <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" v-model="account.username" />
          </div>
          <div class="form-group">
            <label for="email">Email Address</label>
            <input type="email" id="email" v-model="account.email" />
          </div>
          <div class="form-group">
            <label for="phone">Phone Number</label>
            <input type="text" id="phone" v-model="account.phone" />
          </div>
          <div class="form-group">
            <label for="shippingAddress">Address</label>
            <input type="text" id="shippingAddress" v-model="account.shippingAddress" />
          </div>
          <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" v-model="account.password" />
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
      try {
        await axios.put(`http://localhost:8080/customers/${this.customerID}`, this.account);
        alert("Account updated successfully!");
        await this.fetchAccountInfo();
      } catch (error) {
        console.error("Failed to update account:", error);
        alert("Failed to update account. Please try again later.");
      }
    },
    async searchByName() {
      try {
        
        const response = await axios.get(`http://localhost:8080/games/name/${this.searchQuery}`);
        this.games = [response.data];
      } catch (error) {
        console.error('Error searching for games:', error);
      }
    },
    goToCustomerHome() {
        router.push({
          name: 'customer-homepage',
          params: {
            employeeId: this.customerID,
            loggedIn: true
          }
        });        
    },
    async goToCustomerMainPage(){
            router.push({
          name: 'customer-homepage',
          params: {
            customerId: this.customerId,
            loggedIn: true
          }  
        });
    },
    async goToCart() {
        router.push({
          name: 'customer-cart',
          params: {
            customerId: this.customerId,
            loggedIn: true
          }         
        }); 
    },
    async goToCustomerWishlist() {
        router.push({
          name: 'customer-wishlist',
          params: {
            customerId: this.customerId,
            loggedIn: true
          }
          
        });
    }, 
    async goToCustomerOrders() {
        router.push({
          name: 'customer-orders',
          params: {
            customerId: this.customerId,
            loggedIn: true
          }
        }); 
    },   
    logout() {
        this.$router.push('/SignIn');
    },
  },
  created() {
      if (!this.isLoggedIn()) {
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

<style>
/* General Styles */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "poppins";
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

.cart-img,
.wishlist-img,
.account-img {
  width: 40px; /* Set the width */
  height: auto; /* Let the height adjust automatically to maintain aspect ratio */
  object-fit: contain; /* Ensure the image is contained without distortion */
}

.user-options {
  display: flex;
  align-items: center; /* Ensures the icons and buttons are vertically centered */
  gap: 20px; /* Adds space between the elements */
}

.user-options button {
  background: none;
  border: none;
  padding: 0;
  cursor: pointer;
}

.user-options img {
  width: 30px; /* Adjust to your desired size */
  height: 30px;
}

.dropdown {
  position: relative;
}

.nav-buttons {
  display: none;
  flex-direction: column;
  position: absolute;
  background-color: #fff;
  top: 40px;
  right: 0;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 10px;
}

.dropdown:hover .nav-buttons, .nav-buttons:hover {
  display: flex; /* Show dropdown on hover */
}

.dropdown:hover .nav-buttons {
  display: flex !important; 
}

/* Account Info Page Styles */
.account-page {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 40px;
  background-color: #f9f9f9;
  height: calc(100vh - 80px); /* Full height minus the navbar */
  width: 100vw; /* Full width */
}

.account-info {
  background: #fff;
  padding: 20px;
  border-radius: 20px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 600px;
}

.account-info h1 {
  text-align: center;
  color: #1033a4;
}

.form {
  margin-top: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
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
  margin-top: 10px;
}

.btn-update:hover {
  background: #1033a4;
  color: #fff;
}
</style>

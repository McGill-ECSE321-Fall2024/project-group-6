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
            <input type="search" class="search" placeholder="Search game..." />
            <i class="bx bx-search"></i>
          </div>
          <div class="iconAccount">
            <img src="./account.png" alt="Account" />
          </div>
          <RouterLink to="/wishlist">
            <img src="./White-Heart.png" alt="Wishlist" />
          </RouterLink>
          <RouterLink to="/checkout">
            <img src="./pngaaa.com-5034351.png" alt="Cart" />
          </RouterLink>
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
  data() {
    return {
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
    async fetchAccountInfo() {
      try {
        const response = await axios.get(
          `http://localhost:8080/customers/${this.$route.query.id}`
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
        await axios.put(`http://localhost:8080/customers/${this.$route.query.id}`, this.account);
        alert("Account updated successfully!");
        await this.fetchAccountInfo();
      } catch (error) {
        console.error("Failed to update account:", error);
        alert("Failed to update account. Please try again later.");
      }
    },
  },
  created() {
    this.fetchAccountInfo();
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

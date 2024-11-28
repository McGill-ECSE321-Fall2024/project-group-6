<!-- Author: Marine Dupuy -->

<template>
  <link
    href="https://cdnjs.cloudflare.com/ajax/libs/boxicons/2.1.4/css/boxicons.min.css"
    rel="stylesheet"
  />
  <div class="main-container">
    <header class="header">
      <div class="app-name" @click="goToEmployeeHome">GameShop</div>
      <div class="navmenu">
        <div class="search-box">
          <input type="search" class="search" placeholder="Search game..." />
          <i class="bx bx-search"></i>
        </div>
      </div>
      <div class="nav-buttons">
        <button @click="logout" class="logout-btn">Logout</button>
      </div>
    </header>

    <div class="content">
      <h3>Employee Account Information</h3>
      <form @submit.prevent="saveChanges">
        <div class="form-group">
          <label for="username">Username</label>
          <input type="text" id="username" v-model="employee.username" disabled />
        </div>
        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" id="email" v-model="employee.email" />
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" id="password" v-model="employee.password" />
        </div>
        <div class="form-group">
          <label for="phone">Phone</label>
          <input type="text" id="phone" v-model="employee.phone" />
        </div>
        <div class="form-group">
          <label for="address">Address</label>
          <input type="text" id="address" v-model="employee.address" />
        </div>
        <button type="submit" class="save-btn">Save Changes</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { RouterLink } from "vue-router";

export default {
  props: ["employeeId", "loggedIn"],

  data() {
    return {
      employee: {
        name: "",
        employeeId: 0,
        role: "",
        email: "",
        phone: "",
      },
    };
  },
  methods: {
    isLoggedIn() {
      return this.loggedIn;
    },
    async fetchEmployeeDetails() {
      try {
        const response = await axios.get(
          `http://localhost:8080/employees/${this.employeeId}`
        );
        this.employee = response.data;
      } catch (error) {
        console.error("Error fetching employee details:", error);
        alert("Unable to fetch employee details. Please try again later.");
      }
    },
    async saveChanges() {
      try {
        await axios.put(
          `http://localhost:8080/employees/${this.employeeId}`,
          this.employee
        );
        alert("Changes saved successfully!");
      } catch (error) {
        console.error("Error saving employee details:", error);
        alert("Failed to save changes. Please try again.");
      }
    },
    goToEmployeeHome() {
      this.$router.push('/employee');
    },
    logout() {
      this.$router.push('/signin');
    },
  },
  created() {
    if (!this.isLoggedIn()) {
      this.$router.push({ name: "sign in" });
      alert("Please log in before accessing this page.");
    } else {
      this.fetchEmployeeDetails();
    }
  },
};
</script>

<style>
/* Header styles */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;  /* Centers all items vertically */
  background-color: #1033a4;
  padding: 1rem;
  color: white;
}

.app-name {
  font-size: 1.5rem;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;  /* Makes sure the text is vertically centered */
  text-align: center;
  cursor: pointer;
}

.navmenu {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-grow: 1;
}

.navmenu img {
    align-items: center;
    position: relative;
    margin: 10px;
    display: inline-block;
    height: 40px;
}

.search-box .search {
  width: 600px;
  padding: 10px;
  border-radius: 50px;
  font-size: 16px;
}

.search-box {
  margin-left: 40px; /* Adjust position */
  display: flex;
  align-items: center; /* Center search box vertically */
}

.navmenu .search-box i {
  color: #ffffff;
  position: relative;
  right: 40px;
  background-color: #1140d9;
  padding: 8px;
  border-radius: 50px;
}

.nav-buttons {
  display: flex;
  align-items: center;
}

.nav-buttons button {
  background-color: white;
  color: #0056b3;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;  /* Centers the text horizontally */
  height: 50px;  /* Set a fixed height to ensure vertical centering */
  display: flex;
  justify-content: center;
  align-items: center;  /* Centers the button text vertically */
}

.nav-buttons button:hover {
  background-color: #0056b3;
}

.logout-btn {
  background-color: #0056b3;
}

/* Form styles */
.content {
  padding: 2rem;
}

h3 {
  margin-bottom: 1rem;
}

form {
  display: flex;
  flex-direction: column;
}

.form-group {
  margin-bottom: 1.2rem;
}

.form-group label {
  font-size: 1rem;
  padding: 0 2rem;
  margin-bottom: 0.8rem;
}

.form-group input {
  padding: 0.5rem;
  font-size: 2rem;
  border: 1px solid #ccc;
  border-radius: 5px;

}

.save-btn {
  margin-top: 2rem;
  padding: 0.8rem;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.save-btn:hover {
  background-color: #218838;
}
.main-container {
  background-color: #ffff;
  color: #000;
}
</style>
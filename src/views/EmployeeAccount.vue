
<!-- Author: Marine Dupuy -->

<template>
    <div class="main-container">
      <header class="header">
        <div class="app-name">GameShop - Employee Account</div>
        <div class="nav-buttons">
          <button @click="goToEmployeeHome" class="back-btn">Back to Dashboard</button>
          <button @click="logout" class="logout-btn">Logout</button>
        </div>
      </header>
      <div class="content">
        <h3>Account Information</h3>
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
  import axios from 'axios';
  import { mapState } from 'vuex';
  
  export default {
    data() {
      return {
        employee: {
          username: '',
          email: '',
          password: '',
          phone: '',
          address: '',
        },
      };
    },
    computed: {
      ...mapState({
        employeeId: state => state.userId, // Retrieve the employee ID from Vuex
      }),
    },
    methods: {
      async fetchEmployeeDetails() {
        try {
          // Use the employee ID from Vuex to fetch employee details
          const response = await axios.get(`http://localhost:8080/employee/${this.employeeId}`);
          this.employee = response.data;
        } catch (error) {
          console.error('Error fetching employee details:', error);
        }
      },
      async saveChanges() {
        try {
          // Use the employee ID from Vuex to update employee details
          const response = await axios.put(
            `http://localhost:8080/employee/${this.employeeId}`,
            this.employee
          );
          alert('Your information has been updated.');
        } catch (error) {
          console.error('Error saving changes:', error);
          alert('Failed to update information.');
        }
      },
      goToEmployeeHome() {
        this.$router.push('/employee'); // Navigate to EmployeeHome
      },
      logout() {
        this.$router.push('/signin'); // Log out and redirect to sign-in page
      },
    },
    async created() {
      if (!this.employeeId) {
        this.$router.push('/signin'); // Redirect if no employee ID is found
        return;
      }
      await this.fetchEmployeeDetails(); // Fetch employee details on page load
    },
  };
  </script>
  
  <style scoped>
  /* Header styles */
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #333;
    padding: 1rem;
    color: white;
  }
  
  .app-name {
    font-size: 1.5rem;
  }
  
  .nav-buttons {
    display: flex;
    gap: 1rem;
  }
  
  .nav-buttons button {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 0.5rem 1rem;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .nav-buttons button:hover {
    background-color: #0056b3;
  }
  
  .logout-btn {
    background-color: #dc3545;
  }
  
  .logout-btn:hover {
    background-color: #c82333;
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
    margin-bottom: 1rem;
  }
  
  .form-group label {
    font-size: 1rem;
    margin-bottom: 0.5rem;
  }
  
  .form-group input {
    padding: 0.8rem;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
  
  .save-btn {
    margin-top: 1rem;
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
  </style>
  

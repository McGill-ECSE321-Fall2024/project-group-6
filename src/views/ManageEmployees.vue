<template>
    <link
    href="https://cdnjs.cloudflare.com/ajax/libs/boxicons/2.1.4/css/boxicons.min.css"
    rel="stylesheet"
  />
  <div class="employee-management">
    <header>
      <nav class="navbar">
        <div class="logo">
          <h2>GameShop</h2>
        </div>
          <div class="button-container">
            <button @click="goToManagerHome" class="homepage-btn">HomePage</button>
            <button @click="logout" class="logout-btn">Sign Out</button>
          </div>
      </nav>
    </header>
    <div class="main-header">
      <div class="header">
        <h2>Employee Management</h2>
        <h5>Manage your team efficiently and effortlessly!</h5>
      </div>
    </div>
    <div class="container">
      <!-- Add Employee Section -->
      <div class="section add-employee">
        <h2>Add Employee</h2>
        <form class="form">
          <input type="text" name="name" placeholder="Full name" v-model="employeeName" class="input" required>
          <input type="email" name="email" placeholder="Email" v-model="employeeEmail" class="input" required>
          <input type="password" name="password" placeholder="Password" v-model="employeePassword" class="input" required>
          <input type="text" name="phone" placeholder="Phone number" v-model="employeePhone" class="input" required>
          <button type="submit" @click="addEmployee()" class="btn">Add Employee</button>
        </form>
      </div>
      <!-- Employee List Section -->
      <div class="section employee-list">
        <h2>Employee List</h2>
        <div v-if="employees.length === 0" class="employees">No employees found</div>
        <div class="list">
          <div v-for="employee in employees" :key="employee.employeeId" class="employee-card">
            <div class="info">
              <h3>Name: {{ employee.username }}</h3>
              <h3>Email: {{ employee.email }}</h3>
            </div>
            <div class="actions">
              <button @click="deactivateEmployee(employee.employeeId)" class="btn">Deactivate</button>
              <button @click="assignTask(employee.employeeId)" class="btn">Assign Task</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { RouterLink } from 'vue-router';
import router from '@/router';
export default {
  props: ["managerId", "loggedIn"],

  data() {
    return {
      searchQuery: "",
      managerID: 0,
      employees: [],
      newEmployee: {
        username: '',
        email: '',
        password: '',
        phone: ''
      },
      employeeToDelete: {
        username: '',
        email: '',
        password: '',
        phone: ''
      },
      employeeName: '',
      employeeEmail: '',
      employeePassword: '',
      employeePhone: '',
      employeeId: 0,
    };
  },
  methods: {
    isLoggedIn() {
        return this.loggedIn;
    },

    async searchByName() {

        console.log(gameId);
      this.$router.push({ name: "manager-gamepage",
      params: {
        gameId: gameId,
        managerId:this.managerID,
        loggedIn:true
      } 
    });
    },

    async fetchEmployees() {
      try {
        const response = await axios.get("http://localhost:8080/employees");
        this.employees = response.data["employees"];
        for (let i = 0; i < this.employees.length; i++) {
          if (this.employees[i].username === "deactivated") {
            this.employees.splice(i, 1);  // Remove the employee at index i
          }
        }
        console.log(this.employees);
      } catch (error) {
        console.error("Error fetching employees:", error);
      }
    },

    async addEmployee() {
      try {
        const exists = this.employees.some(employee => employee.email === this.employeeEmail);
        if (!exists) {
          const newEmployee = {
            username: this.employeeName,
            email: this.employeeEmail,
            password: this.employeePassword,
            phone: this.employeePhone
          };

          await axios.post('http://localhost:8080/employees', newEmployee);
          this.empl
          await this.fetchEmployees();
          alert("Employee added successfully!");
        } else {
          alert("The employee already exists");
        }
      } catch (error) {
        console.error("Error adding employee: ", error)
      }
    },

    async deactivateEmployee(id) {
      try{
        const response = await axios.get(`http://localhost:8080/employees/${id}`);
        console.log(response);
        this.employeeToDelete = response.data;
      } catch(error){
        alert(error);
      }
      try {
        await axios.put(`http://localhost:8080/employees/deactivate/${id}`);
        alert("Changes saved successfully!");
        this.fetchEmployees();
      } catch (error) {
        console.error("Error deactivating employee:", error);
        alert(error);
      }
    },

    async goToManagerHome() {
      router.push({
          name: 'manager-homepage',
          params: {
            employeeId: this.managerID,
            loggedIn: true
          } 
        });
    },

    async assignTask(id) {
      try {
        const task = prompt('Enter the task to assign:');
        if (!task) return; // No task provided
        await axios.put(`http://localhost:8080/employees/${id}/tasks`, { task });
        alert(`Assigned task "${task}" to employee with ID: ${id}`); // Mock task assignment
      } catch(error) {
        console.error("Error assigning task: ", error)
      }
    },

    logout() {
      this.$router.push('/'); // Redirect to login
    },
  },

  created() {
      if (!this.isLoggedIn()) {
        this.$router.push({ name: "sign in" });
        alert("Please log in before accessing this page.");
      } else {
        this.managerID = this.managerId; 
        this.fetchEmployees();
      }
        
    },
};
</script>


<style scoped>

* {
    margin: 0;
    padding: 0;
    text-decoration: none;
    list-style: none;
    font-family: "poppins";
}

/* Shared styles with Wishlist Page */
.employee-management {
  font-family: "poppins";
  background-color: #ffffff;
  color: #000000;
  padding: 0;
  margin: 0;
}

.container {
  height: 100vh;
}

/* Navbar */
.navbar {
    display: flex;
    justify-content: space-between;
    width: 100%;
    height: 90px;
    background: #1033a4;
    padding: 0 40px; 
    align-items: center;
}

.navbar h2 {
    color: #ffffff;
    font-size: 25px;
    font-weight: 500;
    margin-left: 20px; 
    text-decoration: none; 
}

/* Unapproved Games Box */
.unapproved-box {
    margin-top: 1rem;
    text-align: center;
}

/* Search Box */
.search-box .search {
    width: 600px;
    padding: 10px;
    border-radius: 50px;
    font-size: 16px;
}

.search-box {
    margin-left: 40px; 
    display: flex;
    align-items: center;
}

.navmenu .search-box i {
    color: #ffffff;
    position: relative;
    right: 40px;
    background-color: #1140d9;
    padding: 8px;
    border-radius: 50px;
}

.btn {
  background: #1140d9;
  border: 1px solid;
  font-size: 15px;
  color: #ffffff;
  font-weight: 400;
  padding: 4px 20px;
  border-radius: 50px;
}

.btn:hover {
  cursor: pointer;
  background: #3559ce;
}

.button-container {
    display: flex;
    justify-content: space-between;
    gap: 1rem; 
}

.button-container:hover {
  cursor: pointer;
}

/* Main header styles */
.main-header {
  text-align: center;
  padding: 40px 20px;
  background-color: #ffffff;
}

.main-header h2 {
  font-size: 38px;
  font-weight: bold;
}

.main-header h5 {
  font-size: 20px;
  margin-top: 10px;
  font-weight: 550;
}

/* Add Employee Section */
.add-employee {
  padding: 20px;
  margin: 30px auto;
  margin-top: 30px;
  max-width: 600px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
  border-radius: 20px;
}

.add-employee h2 {
  font-weight: bold;
  text-align: center;
}

.homepage-btn {
    display: flex;
    background-color: #22bae0;
    color: white;
    border: none;
    padding: 0.5rem 1rem;
    border-radius: 20px;
    font-size: 0.9rem;
   
}

.homepage-btn:hover {
  cursor: pointer;
  background-color: #77c3d5;
}

.logout-btn {
    background-color: #ff6f61;
    color: white;
    border: none;
    padding: 0.5rem 1rem;
    border-radius: 20px;
    font-size: 0.9rem;
}

.logout-btn:hover {
  cursor: pointer;
  background-color: #fa978e;
}

.input {
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  border-radius: 10px;
  border: 1px solid #ccc;
}

.form .btn {
  width: 100%;
  text-align: center;
  font-size: 1rem;
}

/* Employee List Section */
.employee-list .list {
  padding: 20px;
}

.employee-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 10px;
  margin-bottom: 15px;
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.05);
}

.employee-card .info h3 {
  margin: 0;
}

.employee-card .info p {
  margin: 5px 0;
}

/* Employee Card Actions */
.employee-card .actions {
  display: flex;
  gap: 10px;
}

.actions .btn {
  flex: 1;
  text-align: center;
}

.employee-list h2 {
  text-align: center;
  font-size: 38px;
  font-weight: bold;
}

.employees {
  font-size: 20px;
  margin-top: 10px;
  font-weight: 550;
  text-align: center;
}

</style>

<template>
  <div class="employee-management">
    <header class="navbar">
      <div class="logo">
        <h2>GameShop</h2>
      </div>
      <button @click="logout" class="btn logout-btn">Logout</button>
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
        <form @submit.prevent="addEmployee" class="form">
          <input
            v-model="newEmployee.name"
            type="text"
            placeholder="Name"
            class="input"
            required
          />
          <input
            v-model="newEmployee.email"
            type="email"
            placeholder="Email"
            class="input"
            required
          />
          <input
            v-model="newEmployee.password"
            type="password"
            placeholder="Password"
            class="input"
            required
          />
          <button type="submit" class="btn">Add Employee</button>
        </form>
      </div>
      <!-- Employee List Section -->
      <div class="section employee-list">
        <h2>Employee List</h2>
        <div class="list">
          <div v-for="employee in employees" :key="employee.id" class="employee-card">
            <div class="info">
              <h3>{{ employee.name }}</h3>
              <p>Email: {{ employee.email }}</p>
            </div>
            <div class="actions">
              <button @click="deleteEmployee(employee.id)" class="btn">Delete</button>
              <button @click="deactivateEmployee(employee.id)" class="btn">Deactivate</button>
              <button @click="assignTask(employee.id)" class="btn">Assign Task</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      employees: [], // List of employees
      newEmployee: {
        name: '',
        email: '',
        password: '',
      },
    };
  },
  methods: {
    async fetchEmployees() {
      try {
        const response = await axios.get('http://localhost:8080/employees');
        this.employees = response.data;
      } catch (error) {
        console.error('Error fetching employees:', error);
      }
    },
    async addEmployee() {
      try {
        await axios.post('http://localhost:8080/employees', {
          ...this.newEmployee,
          role: 'employee', // Automatically set role to 'employee'
        });
        alert('Employee added successfully!');
        this.fetchEmployees(); // Refresh employee list
        this.newEmployee = { name: '', email: '', password: '' }; // Reset form
      } catch (error) {
        console.error('Error adding employee:', error);
        alert('Failed to add employee.');
      }
    },
    async deleteEmployee(id) {
      if (!confirm('Are you sure you want to delete this employee?')) return;
      try {
        await axios.delete(`http://localhost:8080/employees/${id}`);
        alert('Employee deleted successfully!');
        this.fetchEmployees(); // Refresh employee list
      } catch (error) {
        console.error('Error deleting employee:', error);
        alert('Failed to delete employee.');
      }
    },
    async deactivateEmployee(id) {
      try {
        await axios.patch(`http://localhost:8080/employees/${id}/deactivate`);
        alert('Employee deactivated successfully!');
        this.fetchEmployees(); // Refresh employee list
      } catch (error) {
        console.error('Error deactivating employee:', error);
        alert('Failed to deactivate employee.');
      }
    },
    async assignTask(id) {
      const task = prompt('Enter the task to assign:');
      if (!task) return; // No task provided
      try {
        await axios.post(`http://localhost:8080/employees/${id}/assign-task`, { task });
        alert('Task assigned successfully!');
      } catch (error) {
        console.error('Error assigning task:', error);
        alert('Failed to assign task.');
      }
    },
    logout() {
      this.$router.push('/signin'); // Redirect to login
    },
  },
  async created() {
    await this.fetchEmployees(); // Fetch employee list on load
  },
};
</script>

<style scoped>
*
/* Shared styles with Wishlist Page */
.employee-management {
  font-family: "poppins";
  background-color: #ffffff;
  color: #000000;
  padding: 0;
  margin: 0;
}

.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 80px;
  background: #1033a4;
  padding: 0 20px;
}

.navbar h2 {
  color: #ffffff;
  font-size: 25px;
}

.btn {
  background: #88b9df;
  border: 1px solid #88b9df;
  font-size: 15px;
  color: #ffffff;
  font-weight: 400;
  padding: 4px 20px;
  border-radius: 50px;
}

.btn:hover {
  background: #ffffff;
  color: #88b9df;
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
  max-width: 600px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
  border-radius: 20px;
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

</style>

<!-- Author: Joseph -->

<template>
  <main class="signup-container">
    <h1>User Login</h1>
    <div class="form-container">
      <select v-model="userType">
        <option value="" disabled>Select User Type</option>
        <option value="manager">Manager</option>
        <option value="employee">Employee</option>
        <option value="customer">Customer</option>
      </select>
      <input type="email" placeholder="Email" v-model="email" />
      <input type="password" placeholder="Password" v-model="password" />
      <input v-if="userType === 'employee'" type="text" placeholder="Employee Number" v-model="employeeNumber" />
      <input v-if="userType === 'manager'" type="text" placeholder="Manager Number" v-model="managerNumber" />
      <button @click="login" :disabled="!isFormValid()">Login</button>
      <button class="danger-btn" @click="clearInputs()">Clear</button>
    </div>
    <p v-if="errorMessage" class="danger-btn">{{ errorMessage }}</p>
  </main>
</template>

<script>
import axios from 'axios';
import router from '@/router';

const axiosClient = axios.create({
  baseURL: "http://localhost:8080"
});

export default {
  data() {
    return {
      userType: '',
      email: '',
      password: '',
      employeeNumber: '',
      managerNumber: '',
      errorMessage: ''
    };
  },
  methods: {
    isFormValid() {
      if (this.userType === 'employee' && this.employeeNumber !== '12345678') {
        return false;
      } else if (this.userType === 'manager' && this.managerNumber !== '1234567890') {
        return false;
      }
      return this.userType && this.email && this.password;
    },
    async login() {
      if (!this.isFormValid()) {
        return;
      }

      try {
        const user = { username:"123",email: this.email.toLowerCase(), password: this.password, phone:"123" };
        const response = await axiosClient.post(`/login`, user);

        if (response.data.email.toLowerCase() === this.email.toLowerCase() && response.data.username!=="deactivated") {
      
        localStorage.setItem('loggedIn', 'true'); 
        
      if (this.userType === 'employee') {
        var idToPass= await this.getRoleID(response.data.userId);
        const response4 = await axios.get(`http://localhost:8080/employees/${idToPass}`);
        console.log(response4.data.email.toLowerCase()+" "+this.email.toLowerCase());
        if(response4.data.email.toLowerCase()==this.email.toLowerCase()){
        router.push({
          name: 'employee-homepage',
          params: {
            employeeId: await this.getRoleID (response.data.userId),
            loggedIn: true
          }
          
        });
      }else{
      alert("This user is not registered as an employee");
      }
        
      }
      else if(this.userType === 'manager'){
       var idToPass= await this.getManagerID(response.data.userId);
        const response2 = await axios.get(`http://localhost:8080/manager/${idToPass}`);
        console.log(response2.data.email+" comparedto "+this.email);
        if(response2.data.email.toLowerCase()==this.email.toLowerCase()){
        router.push({
          name: 'manager-homepage',
          params: {
            managerId: idToPass,
            loggedIn: true
          }
          
        });
      }else{
        alert("This user is not registered as a manager");
      }
        
      
      }
      else if(this.userType === 'customer'){
       
        var idToPass= await this.getCustomerID(response.data.userId);
        const response3 = await axios.get(`http://localhost:8080/customers/${idToPass}`);
        
        if(response3.data.email.toLowerCase()==this.email.toLowerCase()){
       router.push({
         name: 'customer-homepage',
         params: {
           customerId: await this.getCustomerID(response.data.userId),
           loggedIn: true
         }
        
       });
      } 
    }

        } 
        else if(response.data.username=="deactivated"){
          this.errorMessage="Employee has been deactivated";
        }
       
      } catch (error) {
        this.errorMessage = 'Invalid login credentials';
        console.log(error);
      }
    },
    async getRoleID(id) {
      
        try {
          const response = await axios.get(`http://localhost:8080/employees/id/${id}`);
          return response.data;
        } catch (error) {
          console.error('Error filtering games by category:', error);
        }
      },
      async getManagerID(id) {
      
      try {
        const response = await axios.get(`http://localhost:8080/manager/id/${id}`);
        return response.data;
      } catch (error) {
        console.error( error);
      }
    },
    async getCustomerID(id) {
      
      try {
        const response = await axios.get(`http://localhost:8080/customers/id/${id}`);
        return response.data;
      } catch (error) {
        console.error( error);
      }
    },
    clearInputs() {
      this.userType = '';
      this.email = '';
      this.password = '';
      this.employeeNumber = '';
      this.managerNumber = '';
      this.errorMessage = '';
    }
    
    },
  
   
  };

</script>

<style scoped>
.signup-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 2rem;
  background: #f9f9f9;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  border-radius: 10px;
  font-family: 'Arial', sans-serif;
}

.signup-container h1 {
  text-align: center;
  color: #333;
  margin-bottom: 1.5rem;
}

.form-container {
  display: flex;
  flex-direction: column;
}

.form-container select,
.form-container input {
  margin-bottom: 1rem;
  padding: 0.8rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.form-container button {
  padding: 0.8rem;
  font-size: 1rem;
  margin-bottom: 1rem;
  cursor: pointer;
  border: none;
  border-radius: 5px;
}

#signup-btn {
  background-color: #28a745;
  color: white;
}

#signup-btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.danger-btn {
  background-color: #dc3545;
  color: white;
}

.danger-btn:hover {
  background-color: #c82333;
}

p.danger-btn {
  background: none;
  color: #dc3545;
  text-align: center;
}
</style>
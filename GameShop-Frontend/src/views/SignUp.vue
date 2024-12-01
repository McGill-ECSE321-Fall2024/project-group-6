<template>
  <main class="signup-container">
    <h1>User Sign-Up</h1>
    <div class="form-container">
      <input type="text" placeholder="Username" v-model="username" />
      <input type="email" placeholder="Email" v-model="email" />
      <input type="password" placeholder="Password" v-model="password" />
      <input type="password" placeholder="Retype Password" v-model="passwordCopy" />
      <input type="text" placeholder="Shipping Address" v-model="shippingAddress" />
      <input type="text" placeholder="Phone Number" v-model="phone" />
      <button id="signup-btn" @click="signUp" v-bind:disabled="!isFormValid()">Sign Up</button>
      <button class="danger-btn" @click="clearInputs">Clear</button>
    </div>
    <p v-if="errorMessage" class="danger-btn">{{ errorMessage }}</p>
  </main>
</template>

<script>
import axios from "axios";

const axiosClient = axios.create({
  baseURL: "http://localhost:8080"
});

export default {
  data() {
    return {
      username: '',
      email: '',
      password: '',
      shippingAddress: '',
      phone: '',
      errorMessage: '',
      passwordCopy:''
    };
  },
  methods: {
    isFormValid() {
      return this.username && this.email && this.password && this.shippingAddress && this.phone;
    },
    async signUp() {
      try {
        const newUser = {
          shippingAddress: this.shippingAddress,
          username: this.username,
          email: this.email,
          phone: this.phone,
          password: this.password,
          cart: null,         
          wishlist: null      
        };
        await axiosClient.post('/customers', newUser);
        this.clearInputs();
      }  catch (error) {
    if (this.password.length<10) {
      this.errorMessage = "Your password is less than 10 characters";
    }
    else if(this.password!==this.passwordCopy){
      this.errorMessage = "Your passwords don't match";
    
    } else {
      this.errorMessage = 'You are already a regisstered customer, use sign in';
    }
  }

    },
    clearInputs() {
      this.username = '';
      this.email = '';
      this.password = '';
      this.shippingAddress = '';
      this.phone = '';
      this.errorMessage = '';
    }
  }
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

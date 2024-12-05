<!-- Author: Marine and Joseph -->

<template>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/boxicons/2.1.4/css/boxicons.min.css" rel="stylesheet" />
  <div class="main-container">
    <header class="header">
      <div class="app-name" @click="goToEmployeeHome">GameShop</div>
      <div class="navmenu">

      </div>
      <div class="nav-buttons">
        <button @click="goToEmployeeHome" class="homepage-btn">HomePage</button>
        <button @click="logout" class="logout-btn">Logout</button>
      </div>
    </header>

    <div class="content">
      <div v-if="showPopup" class="popup">
        {{ popupMessage }}
      </div>
      <h3>Employee Account Information</h3>
      <form @submit.prevent="saveChanges">
        <div class="form-group">
          <label for="username">Username</label>
          <input type="text" id="username" v-model="employee.username" />
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

        <button type="submit" class="save-btn" @click="saveChanges">Save Changes</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

import router from '@/router';

export default {
  props: ["employeeId", "loggedIn"],

  data() {
    return {
      employeeID: 0,
      employee: {
        username: "",
        email: "",
        phone: "",
        password: "",
        tasks: "",
        activated: ""
      },
      popupMessage: "",
      showPopup: false,
    };
  },
  methods: {
    isLoggedIn() {
      return this.loggedIn;
    },
    async fetchEmployeeDetails() {
      try {
        const response = await axios.get(
          `http://localhost:8080/employees/${this.employeeID}`
        );
        this.employee = response.data;
      } catch (error) {
        //console.error("Error fetching employee details:", error);
        alert("Unable to fetch employee details. Please try again later.");
      }
    },
    async saveChanges() {
      if(!this.employee.username||!this.employee.email||!this.employee.password||!this.employee.phone){
        this.popupMessage = "Please fill in all the fields";
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);
        return;
      }
      try {
        await axios.put(
          `http://localhost:8080/employees/${this.employeeID}`,
          this.employee
        );
       
        await this.fetchEmployeeDetails();
        this.popupMessage = "Changes saved successfully!";
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);
      } catch (error) {
        this.popupMessage = "Error saving the changes, please try again";
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);
      }
    },

    goToEmployeeHome() {
      router.push({
        name: 'employee-homepage',
        params: {
          employeeId: this.employeeID,
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
    } else {
      this.employeeID = this.employeeId;
      this.fetchEmployeeDetails();
    }

  },

};
</script>

<style scoped>
/* Header styles */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  /* Centers all items vertically */
  background-color: #1033a4;
  padding: 1rem;
  color: white;
}

.app-name {
  font-size: 1.5rem;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  /* Makes sure the text is vertically centered */
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
  margin-left: 40px;
  /* Adjust position */
  display: flex;
  align-items: center;
  /* Center search box vertically */
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
  justify-content: space-between;
  gap: 1rem;
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


/* Form styles */
.content {
  padding: 2rem;
  height: 100vh;
}

.content h3 {
  text-align: center;
  font-weight: bold;
  font-size: 1.5rem;
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
  display: block;
  font-weight: bold;
}

.form-group input {
  padding: 0.5rem;
  width: 100%;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;

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
.save-btn {
  margin-top: 2rem;
  font-size: 1rem;
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

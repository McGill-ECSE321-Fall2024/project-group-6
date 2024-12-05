<!-- Author: Marine, Joseph and Mario -->

<template>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/boxicons/2.1.4/css/boxicons.min.css" rel="stylesheet" />
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
      <div class="nav-buttons">
        <button @click="goToEmployeeAccount"><img src="../assets/account.png" class="nav-btn"></button>
      </div>
      <div class="button-container">
        <button @click="logout" class="logout-btn">Logout</button>
      </div>


    </nav>
  </header>
  <div class="container">
    <div v-if="showPopup" class="popup">
      {{ popupMessage }}
    </div>
    <aside class="categories">
      <select v-model="selectedCategory" @change="filterByCategory">
        <option value="" disabled>Select Category</option>
        <option value="all">All games</option>
        <option v-for="category in categories" :key="category.id" :value="`${category.name}`" class="options">
          {{ `${category.name} (${category.id})` }}
        </option>
      </select>
    </aside>


    <main class="catalog">
      <div v-if="games.length === 0">No games found</div>
      <div v-for="game in games" :key="game.gameId" class="game-card">
        <img :src="game.photoURL" alt="Game Image" class="game-image" />
        <div class="game-info">
          <h3>{{ game.name }}</h3>
          <p v-if="game.promotion > 0" class="game-price promo">
            <strong>Price:</strong> ${{ game.price }}
            <span class="promo-badge">-{{ game.promotion * 100 }}% off!</span>
          </p>
          <p v-else class="game-price">
            <strong>Price:</strong> ${{ game.price }}
          </p>
          <p class="game-stock"><strong>Stock:</strong> {{ game.stockQuantity }} left</p>
          <button class="btn-danger" @click="viewGameDetails(game.gameId)">Edit</button>
          <button class="btn-dangers" @click="requestDelete(game.gameId)">Request Delete</button>
        </div>
      </div>
    </main>

    <aside class="tasks-box">
      <h3>Your Assigned Tasks: </h3>
      <ul v-if="tasks.length > 0">
        <li v-for="task in tasks">
          <h4>{{ task }}</h4>
          <div class="buttons">
            <button @click=" taskCompleted(task)" class="btn">Task Completed</button>
          </div>
        </li>

      </ul>
      <p v-else>No tasks assigned</p>
    </aside>
  </div>
</template>



<script>
import axios from "axios";
import { RouterLink } from 'vue-router';
import router from '@/router';

export default {
  props: ['employeeId', 'loggedIn'],

  data() {
    return {
      searchQuery: "",
      selectedCategory: "",
      categories: [],
      games: [],
      tasks: [],
      employeeID: 0,
      game: {
        name: "",
        description: "",
        price: 0.0,
        stockQuantity: 0.0,
        photoURL: "",
        toBeAdded: "",
        toBeRemoved: "",
        promotion: 0.0,
        categories: ""
      },
      categoryIdsArray: [],
      popupMessage: "",
      showPopup: false,

    };
  },
  methods: {
    isLoggedIn() {
      return this.loggedIn;
    },
    async fetchCategories() {
      try {
        const response = await axios.get('http://localhost:8080/categories');
        this.categories = response.data["categories"];
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    },
    async fetchGames() {
      try {
        const response = await axios.get("http://localhost:8080/games");
        this.games = response.data["games"];
      } catch (error) {
        console.error("Error fetching games:", error);
      }
    },
    async fetchTasks() {
      try {
        const response = await axios.get(
          `http://localhost:8080/employees/${this.employeeID}`
        );
        const taskStrings = response.data["assignedTasks"];
        for (let i = 0; i < taskStrings.length; i++) {
          const taskString = taskStrings[i];
          const parsedTask = JSON.parse(taskString.trim());
          this.tasks.push(parsedTask.task);
        }
      } catch (error) {
        console.error("Error fetching tasks:", error);
      }
    },
    viewGameDetails(gameId) {
      this.$router.push({
        name: "employee-gamepage",
        params: {
          gameId: gameId,
          employeeId: this.employeeID,
          loggedIn: true
        }
      });
    },

    async searchByName() {
      try {

        const response = await axios.get(`http://localhost:8080/games/name/${this.searchQuery}`);
        this.games = [response.data];
      } catch (error) {
        console.error('Error searching for games:', error);
      }
    },
    async filterByCategory() {
      if (this.selectedCategory === 'all') {
        await this.fetchGames();
      } else {
        try {
          console.log(this.selectedCategory);
          const response = await axios.get(`http://localhost:8080/games/category/${this.selectedCategory}`);
          this.games = response.data["games"];
        } catch (error) {
          console.error('Error filtering games by category:', error);
        }
      }
    },
    async requestDelete(id) {
      this.categoryIdsArray = [];
      try {
        const response = await axios.get(`http://localhost:8080/games/id/${id}`);
        console.log(response);
        this.game = response.data;
      } catch (error) {
        alert(error);
      }
      try {
        for (var i = 0; i < this.game.categories.length; i++) {
          if (this.game.categories[i]["categoryId"] !== id) {
            this.categoryIdsArray.push(this.game.categories[i]["categoryId"]);
          }
        }
        this.game.categories = this.categoryIdsArray;
        this.game.toBeRemoved = true;
        console.log(this.game);
        await axios.put(`http://localhost:8080/games/id/${id}`, this.game);
        this.popupMessage = `${this.game.name} was requested to be deleted.`;
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);

      } catch (error) {
        console.error("Error saving game details:", error);
        this.popupMessage = `${this.game.name} cannot be requested to be deleted.`;
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);
      }
    },
    async goToEmployeeAccount() {
      router.push({
        name: 'employee-account',
        params: {
          employeeId: this.employeeID,
          loggedIn: true
        }

      });

    },
    async taskCompleted(task) {
      var employee = '';

      for (var i = 0; i < this.tasks.length; i++) {

        if (this.tasks[i] == task) {
          this.tasks.splice(i, 1);
        }

      }

      const response = await axios.get(
        `http://localhost:8080/employees/${this.employeeID}`
      );
      try {
        employee = response.data;
        employee.assignedTasks = this.tasks.map(task => JSON.stringify({ task: task }));
        console.log(employee.assignedTasks);
        const newEmployee = {
          "username": employee.username,
          "email": employee.email,
          "phone": employee.phone,
          "password": employee.password,
          "assignedTasks": employee.assignedTasks,
          "activated": true
        };
        console.log(newEmployee);


        await axios.put(
          `http://localhost:8080/employees/${this.employeeID}`,
          newEmployee
        );
        this.popupMessage = "Task deleted successfully";
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);
      } catch (error) {
        alert(error);
      }


    },
    logout() {
      this.$router.push('/');
    }
  },
  created() {

    if (!this.isLoggedIn()) {
      this.$router.push({ name: 'sign in' });
      alert('Please log in before accessing this page.');
    } else {

      this.employeeID = this.employeeId;
      console.log(this.employeeID);
      this.fetchCategories();
      this.fetchGames();
      this.fetchTasks();
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


.navbar {
  display: flex;
  right: 0px;
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

.navmenu {
  display: flex;
  justify-content: space-between;
  align-items: center;

}


.search-box .search {
  width: 500px;
  padding: 8px 8px;
  border-radius: 50px;
  font-size: 16px;
}

.search-box {
  margin-left: 40px;
  display: flex;
  align-items: center;
}

.nav-buttons,
.button-container {
  display: inline-block;
  /* Ensure the buttons are displayed in a row */
  margin: 1px;
  /* Adds some space between the buttons (can be adjusted) */
}

.navmenu .search-box i {
  color: #ffffff;
  position: relative;
  right: 40px;
  background: #1033a4;
  padding: 8px;
  border-radius: 50px;
}

.iconAccount img {
  width: 50px;
  margin-left: 20px;
  cursor: pointer;
}

.iconAccount {
  display: flex;
  align-items: right;
}


.container {
  display: flex;
  justify-content: space-between;
  background-color: #ffff;
  color: #000;
  height: 100vh;
  padding: 20px;
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
.categories {
  width: 200px;
}

.categories select {
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
}

@keyframes pulse {

  0%,
  100% {
    transform: scale(1);
    opacity: 1;
  }

  50% {
    transform: scale(1.1);
    opacity: 0.9;
  }
}

.tasks-box {
  width: 300px;
  background: #ffffff;
  border-radius: 8px;
  padding: 20px;
  margin-left: 20px;

}


.tasks-box h4 {
  padding: 15px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s;
}


.tasks-box h3 {
  font-weight: bold;
  font-size: 20px;
  margin-bottom: 10px;
  color: #1033a4;
}

.tasks-box ul {
  list-style-type: none;
  padding: 0;
  color: #1033a4;
}

.tasks-box h4 {
  margin-bottom: 15px;
  color: black;
}

.tasks-box li p {
  margin: 5px 0 0;
  font-size: 14px;
  color: #1033a4;
}


.catalog {
  flex: 9;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1rem;
  padding-left: 2rem;
}

.game-card {
  display: flex;
  flex-direction: column;
  padding: 10px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  width: 300px;
  height: 450px;
  /* Ensure consistent height for all cards */
  text-align: center;
  position: relative;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.game-card:hover {
  transform: scale(1.05);
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.2);
}


.game-image {
  max-width: 100%;
  height: 60%;
  margin: 0.5rem;
}

.game-info {
  position: absolute;
  bottom: 10px;
  /* Ensure info starts from the bottom */
  left: 0;
  right: 0;
  padding: 15px;
  background-color: #fff;
  /* Optional: Add a subtle background */
}

.game-info h3 {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 10px;
}

.promo {
  position: relative;
}

.promo-badge {
  display: inline-block;
  margin-left: 10px;
  padding: 3px 8px;
  background-color: rgba(236, 137, 137, 0.999);
  /* Bright orange to catch attention */
  color: #fff;
  font-size: 0.9rem;
  font-weight: bold;
  border-radius: 5px;
  text-transform: uppercase;
  animation: pulse 1.5s infinite;
}

.game-price {
  font-size: 1rem;
  margin: 0.5em 0;
  color: #333;
}

.game-price strong {
  font-weight: bold;
}

.game-description {
  color: #333;
}

.game-stock {
  font-size: 1rem;
  margin: 0.5em 0;
  color: #333;
}

.game-stock strong {
  font-weight: bold;
}

.account-img {
  padding-top: -2px;
}

/* Styling for the container of the nav buttons */
.nav-buttons {
  display: flex;
  justify-content: flex-end;
  /* Align to the right */
  align-items: center;
  padding: 10px;
}

/* Styling for the button */
.nav-buttons button {
  background-color: transparent;
  /* No background color */
  border: none;
  /* Remove border */
  cursor: pointer;
  /* Indicate that it's clickable */
  padding: 0;
  /* Remove default padding */
  display: flex;
  /* Ensure the content is centered */
  justify-content: center;
  /* Center the content horizontally */
  align-items: center;
  /* Center the content vertically */
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  /* Add transition for hover effects */
}

/* Styling for the image */
.nav-btn {
  width: 40px;
  /* Set a fixed width */
  height: 40px;
  /* Set a fixed height */
  border-radius: 50%;
  /* Make the image circular */
  object-fit: cover;
  /* Ensure the image fits within the circular frame */
  transition: transform 0.3s ease;
  /* Smooth transition when hovered */
}

/* Hover effects */
.nav-buttons button:hover {
  transform: scale(1.1);
  /* Slightly enlarge the button on hover */
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
  /* Add subtle shadow on hover */
}

/* Focus effect to indicate button interaction */
.nav-buttons button:focus {
  outline: none;
  /* Remove default outline */
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.6);
  /* Add blue shadow for focus */
}

/* Optional: Add a "active" state for when the button is clicked */
.nav-buttons button:active {
  transform: scale(1);
  /* Reset the scale on click */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.15);
  /* Slight shadow effect on click */
}

.btn-danger {
  background-color: yellow;
  /* Yellow background */
  color: black;
  /* Black text */
  border: none;
  /* No border */
  padding: 10px 20px;
  /* Padding for the button */
  border-radius: 5px;
  /* Rounded corners */
  font-size: 16px;
  /* Font size */
  cursor: pointer;
  /* Pointer cursor on hover */
  font-weight: bold;
  /* Bold text */
  transition: background-color 0.3s;
  /* Smooth transition */
}

.btn-dangers {
  margin-left: 5px;
  background-color: red;
  /* Yellow background */
  color: black;
  /* Black text */
  border: none;
  /* No border */
  padding: 10px 20px;
  /* Padding for the button */
  border-radius: 5px;
  /* Rounded corners */
  font-size: 16px;
  /* Font size */
  cursor: pointer;
  /* Pointer cursor on hover */
  font-weight: bold;
  /* Bold text */
  transition: background-color 0.3s;
  /* Smooth transition */
}


.btn-danger:hover {
  background-color: darkorange;
  /* Darker shade on hover */
}

.btn-dangers:hover {
  background-color: darkorange;
}

.options {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.5rem;
  color: black;
}

.button-container {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
}

.button-container:hover {
  cursor: pointer;
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

.buttons button {
  padding: 0 6px;

  height: 20px;
  border: none;
  border-radius: 20px;
  background-color: green;
  margin-bottom: 20px;
  color: #ffffff;
}

.buttons {
  margin: 1rem;
}

.nav-buttons {
  padding-left: 10px;
  margin-left: 15%;

}
</style>
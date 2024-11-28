<!-- Author: Marine Dupuy -->
 
<template>
  <link
    href="https://cdnjs.cloudflare.com/ajax/libs/boxicons/2.1.4/css/boxicons.min.css"
    rel="stylesheet"
  />
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
        <RouterLink to="/account"><img src="./account.png"></RouterLink>
      </div>
    </nav>
  </header>
  <div class="container">
    <!-- Game Catalog Section -->
    <main class="catalog">
      <h3>Game Inventory</h3>
      <div v-if="games.length === 0">No games found</div>
      <div
        v-for="game in games"
        :key="game.id"
        class="game-card"
        @click="viewGameDetails(game.id)"
      >
        <img :src="game.imageUrl" alt="Game Image" class="game-image" />
        <div class="game-info">
          <h3>{{ game.name }}</h3>
          <p><strong>Price:</strong> ${{ game.price }}</p>
          <p><strong>Stock:</strong> {{ game.stockQuantity }} left</p>
        </div>
      </div>
    </main>

    <!-- Tasks Section -->
    <aside class="tasks-box">
      <h3>Your Assigned Tasks</h3>
      <ul v-if="tasks.length > 0">
        <li v-for="task in tasks" :key="task.id">
          <strong>{{ task.description }}</strong>
          <p>Due: {{ task.dueDate }}</p>
        </li>
      </ul>
      <p v-else>No tasks assigned</p>
    </aside>
  </div>
</template>
Script
Update your fetchTasks method to fetch tasks for the specific employeeID. Pass the employeeID dynamically.

vue
Copy code
<script>
import axios from "axios";

export default {
  data() {
    return {
      searchQuery: "",
      selectedCategory: "",
      categories: [],
      games: [],
      tasks: [],
      employeeID: 1, // Example: Replace with the logged-in employee's ID.
    };
  },
  methods: {
    async fetchCategories() {
      try {
        const response = await axios.get("http://localhost:8080/categories");
        this.categories = response.data;
      } catch (error) {
        console.error("Error fetching categories:", error);
      }
    },
    async fetchGames() {
      try {
        const response = await axios.get("http://localhost:8080/games");
        this.games = response.data;
      } catch (error) {
        console.error("Error fetching games:", error);
      }
    },
    async fetchTasks() {
      try {
        const response = await axios.get(
          `http://localhost:8080/tasks/employee/${this.employeeID}`
        );
        this.tasks = response.data;
      } catch (error) {
        console.error("Error fetching tasks:", error);
      }
    },
    viewGameDetails(gameId) {
      this.$router.push({ name: "game-details", params: { id: gameId } });
    },
  },
  async created() {
    await this.fetchCategories();
    await this.fetchGames();
    await this.fetchTasks();
  },
};
</script>

<style>
* {
    margin: 0;
    padding: 0;
    text-decoration: none;
    list-style: none;
    font-family: "poppins";
}

/* Navbar Styles */
.navbar {
  display: flex;
  right: 0px;
  justify-content: space-between;
  width: 100%;
  height: 90px;
  background: #1033a4;
  padding: 0 40; /* Adds space on the sides */
  align-items: center;
}

.navbar h2 {
  color: #ffffff;
  font-size: 25px;
  font-weight: 500;
  margin-left: 20px; /* Adjust spacing */
  text-decoration: none; /* Prevent underline */
  align-items: center;
}

.navmenu {
  display: flex;
  justify-content: space-between;
  align-items: center;
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
  margin-left: 20px; /* Adjust position */
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

.iconAccount img {
  width: 50px; /* Slightly larger icon */
  margin-left: 5px;
  cursor: pointer;
}

.iconAccount {
  display: flex;
  align-items: center; /* Centers vertically */
}

/* Tasks Section */
.tasks-box {
  width: 300px;
  background: #ffffff;
  border: 2px solid #1140d9;
  border-radius: 8px;
  padding: 20px;
  margin-right: 50px;
}

.tasks-box h3 {
  font-size: 20px;
  margin-bottom: 10px;
  color: #1140d9;
}

.tasks-box ul {
  list-style-type: none;
  padding: 0;
  color: #1140d9;
}

.tasks-box li {
  margin-bottom: 15px;
  color: #1140d9;
}

.tasks-box li p {
  margin: 5px 0 0;
  font-size: 14px;
  color: #1140d9;
}

.container {
  background-color: #ffff;
  color: #000;
  height:100%;
}

</style>

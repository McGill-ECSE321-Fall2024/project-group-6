<!-- Author: Marine Dupuy -->
 
<template>
  <div class="main-container">
    <header class="header">
      <div class="app-name">GameShop - Employee Dashboard</div>
      <div class="nav-buttons">
        <button @click="goToAccountPage" class="account-btn">Account</button>
        <button @click="logout" class="logout-btn">Logout</button>
      </div>
    </header>
    <div class="content">
      <aside class="categories">
        <h3>Search Games</h3>
        <div class="search-bar">
          <input type="text" v-model="searchQuery" placeholder="Search for games by name" />
          <button @click="searchByName">Search</button>
        </div>
        <div class="category-filter">
          <h4>Filter by Category</h4>
          <select v-model="selectedCategory" @change="filterByCategory">
            <option value="" disabled>Select Category</option>
            <option v-for="category in categories" :key="category.id" :value="category.name">
              {{ category.name }}
            </option>
          </select>
        </div>
        <button @click="showAddGameModal = true" class="add-game-btn">Add New Game</button>
      </aside>
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
      <aside class="tasks">
        <h3>Your Tasks</h3>
        <ul>
          <li v-for="task in tasks" :key="task.id">
            {{ task.description }} (Due: {{ task.dueDate }})
          </li>
        </ul>
      </aside>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      searchQuery: '',
      selectedCategory: '',
      categories: [],
      games: [],
      tasks: [],
      showAddGameModal: false,
      newGame: {
        name: '',
        price: 0,
        stockQuantity: 0,
        description: '',
        imageUrl: '',
      },
    };
  },
  methods: {
    async fetchCategories() {
      try {
        const response = await axios.get('http://localhost:8080/categories');
        this.categories = response.data;
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    },
    async fetchGames() {
      try {
        const response = await axios.get('http://localhost:8080/games');
        this.games = response.data;
      } catch (error) {
        console.error('Error fetching games:', error);
      }
    },
    async fetchTasks() {
      try {
        const response = await axios.get('http://localhost:8080/tasks/employee');
        this.tasks = response.data;
      } catch (error) {
        console.error('Error fetching tasks:', error);
      }
    },
    viewGameDetails(gameId) {
      this.$router.push({ name: 'game-details', params: { id: gameId } });
    },
    goToAccountPage() {
      this.$router.push({ name: 'account' }); // Navigate to the account page
    },
    logout() {
      this.$router.push('/signin');
    },
  },
  async created() {
    await this.fetchCategories();
    await this.fetchGames();
    await this.fetchTasks();
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

/* Content styles */
.content {
  display: flex;
  padding: 2rem;
}

.tasks {
  width: 300px;
  margin-left: 2rem;
}

.tasks ul {
  list-style: none;
  padding: 0;
}

.tasks li {
  margin-bottom: 1rem;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
}
</style>


<template>
  <div class="main-container">
    <header class="header">
      <div class="app-name">GameShop</div>
      <div class="search-bar">
        <input type="text" v-model="searchQuery" placeholder="Search for games by name" />
        <button @click="searchByName">Search</button>
      </div>
      <div class="auth-buttons">
        <RouterLink to="/hello">Login</RouterLink>
        <RouterLink to="/">Sign Up</RouterLink>
      </div>
    </header>
    <div class="content">
      <aside class="categories">
        <select v-model="selectedCategory" @change="filterByCategory">
          <option value="" disabled>Select Category</option>
          <option value="all">All games</option>
          <option v-for="category in categories" :key="category.id" :value="category.name">
            {{ category.name }}
          </option>
        </select>
      </aside>
      <main class="catalog">
        <div v-if="games.length === 0">No games found</div>
        <div v-for="game in games" :key="game.id" class="game-card">
          <img :src="game.imageUrl" alt="Game Image" class="game-image" />
          <div class="game-info">
            <h3>{{ game.name }}</h3>
            <p><strong>Price:</strong> ${{ game.price }}</p>
            <p><strong>Description:</strong> {{ game.description }}</p>
            <p><strong>Stock:</strong> {{ game.stockQuantity }} left</p>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { RouterLink } from 'vue-router';

export default {
  data() {
    return {
      searchQuery: '',
      selectedCategory: '',
      categories: [],
      games: [
      ]
    };
  },
  methods: {
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
        const response = await axios.get('http://localhost:8080/games');
        this.games = response.data["games"];
      } catch (error) {
        console.error('Error fetching games:', error);
      }
    },
    async searchByName() {
      
      try {
        
        const response = await axios.get(`http://localhost:8080/games/name/${this.searchQuery}`);
        this.games = [response.data];
        console.log(this.games);
       // console.log(games.length);
      } catch (error) {
        console.error('Error searching for games:', error);
      }
    },
    async filterByCategory() {
  if (this.selectedCategory === 'all') {
    await this.fetchGames(); //always use this, cest mieu
  } else {
    try {
      const response = await axios.get(`http://localhost:8080/games/category/${this.selectedCategory}`);
      this.games = response.data["games"];
    } catch (error) {
      console.error('Error filtering games by category:', error);
    }
  }
}

  },
  async created() {
    await this.fetchCategories();
    await this.fetchGames();
  }
};
</script>

<style scoped>
.main-container {
  font-family: Arial, sans-serif;
}

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

.search-bar {
  display: flex;
  align-items: center;
}

.search-bar input {
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.search-bar button {
  padding: 0.5rem 1rem;
  margin-left: 0.5rem;
  background-color: #28a745;
  border: none;
  color: white;
  cursor: pointer;
  border-radius: 5px;
}

.search-bar button:hover {
  background-color: #218838;
}

.auth-buttons {
  display: flex;
  gap: 1rem;
}

.auth-buttons a {
  color: white;
  text-decoration: none;
  background-color: #007bff;
  padding: 0.5rem 1rem;
  border-radius: 5px;
  cursor: pointer;
}

.auth-buttons a:hover {
  background-color: #0056b3;
}

.content {
  display: flex;
  padding: 2rem;
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

.catalog {
  flex: 1;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 1rem;
  padding-left: 2rem;
}

.game-card {
  background: #f9f9f9;
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 1rem;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
}

.game-card h3 {
  margin: 0;
}

.game-image {
  max-width: 100%;
  height: auto;
  margin: 0.5rem 0;
}

.game-info {
  text-align: center;
}

.game-card p {
  margin-top: 0.5rem;
}
</style>


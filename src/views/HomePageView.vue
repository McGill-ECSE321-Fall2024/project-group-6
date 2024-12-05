<template>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/boxicons/2.1.4/css/boxicons.min.css" rel="stylesheet">
  <header>
    <nav class="navbar">
      <div class="logo">
        <h2>GameShop</h2>
      </div>
      <div class="navmenu"  >
        <div class="search-box">
              <input type="text" v-model="searchQuery" class="search" placeholder="Search game...">
              <i class='bx bx-search' @click="searchByName"></i>
        </div>
        <div class="auth-links ">
          <RouterLink to="/SignIn">Sign In</RouterLink>
          <RouterLink to="/SignUp" >Sign Up</RouterLink>
        </div>
      </div>
    </nav>
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
      <div v-for="game in games" :key="game.gameId" class="game-card">
        <img :src="game.photoURL" alt="Game Image" class="game-image" @click="goToGamePage(game)"/>
        <div class="game-info">
          <h3>{{ game.name }}</h3>
          <p v-if="game.promotion > 0" class="game-price promo">
            <strong>Price:</strong> ${{ game.price }} 
            <span class="promo-badge">{{ game.promotion }}% off!</span>
          </p>
          <p v-else class="game-price">
            <strong>Price:</strong> ${{ game.price }}
          </p>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import axios from 'axios';
import { RouterLink } from 'vue-router';
import router from '@/router';

export default {
  data() {
    return {
      searchQuery: '',
      selectedCategory: '',
      categories: [],
      games: []
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
        console.log(this.games);
      } catch (error) {
        console.error('Error fetching games:', error);
      }
    },
    async searchByName() {
      try {
        console.log("I am here "+this.searchQuery);
        const response = await axios.get(`http://localhost:8080/games/name/${this.searchQuery}`);
        this.games = [response.data];
      } catch (error) {
        console.error('Error searching for games:', error);
      }
    },
    async goToGamePage(game){
      router.push({
                name: 'gamepage',
                params: {
                  gameId: game.gameId
                }
            });
    },
    async filterByCategory() {
      if (this.selectedCategory === 'all') {
        await this.fetchGames();
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
* {
    margin: 0;
    padding: 0;
    text-decoration: none;
    list-style: none;
    font-family: "poppins";
}

.navbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    text-align: center;
    width: 100%;
    height: 80px;
    background: #1033a4;
}

.navbar h2 {
    color: #ffffff;
    font-size: 25px;
    font-weight: 500;
    padding: 20px 20px;
}

.navmenu {
    height: 50px;
    line-height: 60px;
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
    margin-right: 200px;
}

.navmenu .search-box i {
    color: #ffffff;
    position: relative;
    right: 40px;
    top: 2px;
    background-color: #1140d9;
    padding: 8px;
    border-radius: 50px;
}

header img {
    margin-top: 15px;
    margin-right: 10px;
    align-items: center;
    width: 40px;
}

.navmenu .iconcCart {
    align-items: center;
    position: relative;
    margin: 10px;
    z-position: 1;
    display: inline-block;
}

.main-header {
    width: 100%;
    margin: auto;
    height: 40px;
    display: flex;
    padding-bottom: 30px;
}

.main-header .header {
    margin-left: 30px;
    margin-bottom: 30px;
}

.main-header h5 {
    font-size: 20px;
    font-weight: 550px;
    margin-top: 10px;
    margin-bottom: 15px;
}

.main-header h2 {
    font-weight: bold;
    font-size: 38px;
    width: 500px;
    margin-top: 10px;
    margin-bottom: 15px;
}

.main-header .a {
    margin-bottom: 30px;
}

.main-header .btn {
    background: #88b9df;
    border: 1px solid #88b9df;
    font-size: 15px;
    color: #ffffff;
    font-weight: 400;
    padding: 4px 20px;
    border-radius: 50px;
}

.main-header .btn:hover {
    color: #88b9df;
    background: #ffffff;
}

.auth-links {
  display: flex;
  gap: 1rem;
  padding-right: 1rem; 
}

.auth-links a {
  color: white;
  font-size: 1rem;
  font-weight: bold;

}

.auth-links a:hover {
  text-decoration: underline;
}

.promo {
  position: relative;
}

.promo-badge {
  display: inline-block;
  margin-left: 10px;
  padding: 3px 8px;
  background-color: rgba(236, 137, 137, 0.999); /* Bright orange to catch attention */
  color: #fff;
  font-size: 0.9rem;
  font-weight: bold;
  border-radius: 5px;
  text-transform: uppercase;
  animation: pulse 1.5s infinite;
}

/* Animation to add a pulsing effect */
@keyframes pulse {
  0%, 100% {
    transform: scale(1);
    opacity: 1;
  }
  50% {
    transform: scale(1.1);
    opacity: 0.9;
  }
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

.content {
  display: flex;
  justify-content: space-between;
  background-color: #ffff;
  color: #000;
  height: 100vh;
  padding: 20px;
}

.catalog {
  flex: 9;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
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
  height: 450px; /* Ensure consistent height for all cards */
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
  margin: 0.5rem ;
}

.game-info {
  position: absolute;
  bottom: 10px; /* Ensure info starts from the bottom */
  left: 0;
  right: 0;
  padding: 15px;
}

.game-info h3 {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 10px;
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

</style>
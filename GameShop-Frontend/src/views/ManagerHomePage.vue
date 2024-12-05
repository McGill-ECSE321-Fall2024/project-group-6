<!-- Author: Joseph and Mario -->

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
      <div class="button-container">
        <button @click="manageEmployees" class="manage-btn">Manage Employees</button>
        <button @click="logout" class="logout-btn">Sign Out</button>
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
      <div class="adjust-category">
        <div class="form-group">
          <label for="newCategoryId">Add New Category </label>
          <input type="String" v-model="categoryName" />
          <button @click="addCategory(categoryName)" class="add-category">Add Category</button>
        </div>

      </div>
      <aside class="unapproved-box">
        <aside class="game-management-box">
          <h3>Manage Games</h3>
          <form id="add-game-form">
            <label for="game-name">Name:</label>
            <input type="String" id="game-name" name="name" placeholder="Name" v-model="gameName" required>

            <label for="game-description">Description:</label>
            <textarea type="String" id="game-description" name="description" placeholder="Description"
              v-model="gameDescription" required></textarea>

            <label for="game-price">Price:</label>
            <input type="float" id="game-price" name="price" placeholder="20.0" v-model="gamePrice" required>

            <label for="game-stockQuantity">Stock Quantity:</label>
            <input type="number" id="game-stockQuantity" name="stockQuantity" placeholder="80" v-model="stockQuantity"
              required>

            <label for="game-photoURL">Photo URL:</label>
            <input type="String" id="game-photoURL" name="photoURL" placeholder="https://example.com/images/game.jpg"
              v-model="gameURL" required>

            <label for="game-categories">Categories:</label>
            <input type="number" id="game-categories" name="categories" placeholder="Id of the category to add"
              v-model="categoryId" required>

            <button type="submit" @click="createGame()">Add Game</button>
          </form>
        </aside>
      </aside>
    </aside>

    <main class="catalog">
      <div v-if="games.length === 0">No games found</div>
      <div v-for="game in games" :key="game.gameId" class="game-card">
        <img :src="game.photoURL" alt="Game Image" class="game-image" @click="viewGameDetails(game)" />
        <div class="game-info">
          <h3>{{ game.name }}</h3>
          <div class="price-box">
            <p v-if="game.promotion > 0" class="game-price promo">
              <strong>Price:</strong> ${{ game.price }}
              <span class="promo-badge">-{{ game.promotion*100 }}% off!</span>
            </p>
            <p v-else class="game-price">
              <strong>Price:</strong> ${{ game.price }}
            </p>
            <button v-if="game.toBeRemoved" class="btn-dangers" @click="deleteGame(game.gameId)">Delete</button>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import axios from "axios";
import { RouterLink } from 'vue-router';
import router from '@/router';
const axiosClient = axios.create({
  baseURL: "http://localhost:8080"
});
export default {
  props: ['loggedIn', 'managerId'],

  data() {
    return {
      searchQuery: "",
      selectedCategory: "",
      selectedCategoryId: null,
      categories: [],
      categoryId: '',
      categoryName: '',
      categoryIdsArray: [],
      games: [],
      unapprovedGames: [],
      newCategoryName: '',
      category: {
        categoryName: '',
      },
      categoryIdRemove: "",
      successMessage: '',
      managerID: 0,
      gameName: '',
      gameDescription: '',
      gamePrice: 0.0,
      stockQuantity: 0,
      gameURL: '',
      showPopup: false,
      popupMessage: "",

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
        console.log(this.categories);
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    },

    async filterByCategory() {
      if (this.selectedCategory === 'all' || !this.selectedCategory) {
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

    async fetchGames() {
      try {
        const response = await axios.get("http://localhost:8080/games");
        this.games = response.data["games"];
      } catch (error) {
        console.error("Error fetching games:", error);
      }
    },
    async deleteGame(id) {
      try {
        const response = await axios.delete(`http://localhost:8080/games/${id}`);
        await this.fetchGames();
        this.popupMessage = `Game was deleted.`;
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);
      } catch (error) {
        this.popupMessage = `Game cannot be deleted.`;
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);
      }
    },

    async manageEmployees() {
      router.push({
        name: 'manage-employees',
        params: {
          managerId: this.managerID,
          loggedIn: true
        }
      });
    },

    async addCategory(name) {
      var check = "false";
      //this.categoryIdsArray.push(id);
      for (var i = 0; i < this.categories.length; i++) {
        if (this.categories[i]["name"] == name) {
          check = "true";
        }
      }
      //this.categories=this.categoryIdsArray;
      if (check == "false") {
        const newCategory = {
          name: this.categoryName
        };

        await axiosClient.post('/categories', newCategory);
        await this.fetchCategories();

        this.popupMessage = `${this.categoryName} was added to categories.`;
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);

      }
      else {
        this.popupMessage = `${this.categoryName} already exists.`;
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);
      }
    },


    viewGameDetails(game) {
      
      this.$router.push({
        name: "manager-gamepage",
        params: {
          gameId: game.gameId,
          managerId: this.managerID,
          loggedIn: true
        }
      });
    },

    async searchByName() {
      try {

        const response = await axios.get(`http://localhost:8080/games/name/${this.searchQuery}`);
        console.log(response.data);
        this.games = [response.data];
      } catch (error) {
        console.error('Error searching for games:', error);
      }
    },

    async goToManagerAccount() {
      router.push({
        name: 'manager-account',
        params: {
          managerId: this.managerId,
          loggedIn: true
        }

      });

    },

    logout() {
      this.$router.push('/');
    },
    async createGame() {
      var check = "false";

      for (var i = 0; i < this.games.length; i++) {
        if (this.games[i]["name"] == this.gameName) {
          check = "true";
        }
      }
      //this.categories=this.categoryIdsArray;
      if (check == "false") {
        const newgame = {
          name: this.gameName,
          description: this.gameDescription,
          price: this.gamePrice,
          stockQuantity: this.stockQuantity,
          photoURL: this.gameURL,
          toBeAdded: true,
          toBeRemoved: false,
          promotion: 0.0,
          categories: [this.categoryId]
        };
        try {
          await axiosClient.post('/employees/games', newgame).then(()=>{
            this.fetchGames();
          })
          this.popupMessage = `${this.gameName} was added to the catalog.`;
          this.showPopup = true;
          setTimeout(() => (this.showPopup = false), 3000);

        } catch (error) {
          console.log(error);
          this.popupMessage = `${this.gameName} "This game already exists".`;
          this.showPopup = true;
          setTimeout(() => (this.showPopup = false), 3000);

        }
      }
      }
    },
    created() {
      if (!this.isLoggedIn()) {
        this.$router.push({ name: 'sign in' });
        alert('Please log in before accessing this page.');
      } else {
        this.managerID = this.managerId;
        this.fetchGames();
        this.fetchCategories();
        //this.filterByCategory();
      }
    },
  }
</script>


<style scoped>
/* General Styles */
* {
  margin: 0;
  padding: 0;
  text-decoration: none;
  list-style: none;
  font-family: "poppins";
}

/* Success Message */
.success-message {
  color: #1140d9;
  font-weight: bold;
  text-align: center;
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

.navmenu {
  display: flex;
  justify-content: space-between;
  align-items: center;
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

/* Account Icon */
.iconAccount img {
  width: 50px;
  margin-left: 20px;
  cursor: pointer;
}

.iconAccount {
  display: flex;
  align-items: right;
}

.form-group label {
  font-size: 1rem;
  font-weight: bold;
}

.add-category {
  background-color: #1140d9;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
  font-size: 1rem;
  transition: background-color 0.3s;
}

/* Container */
.container {
  display: flex;
  justify-content: space-between;
  background-color: #ffff;
  color: #000;
  height: 100vh;
  padding: 20px;
  min-height: 200vh;
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

/* Categories */
.categories {
  width: 200px;
  gap: 1rem;
}

.categories select {
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-bottom: 1rem;
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

.game-button-container {
  display: flex;
  justify-content: center;
  gap: 10px;
}

.game-button-container .btn {
  background: #88b9df;
}

.game-button-container .btn:hover {
  color: #88b9df;
  background: #ffffff;
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
  color: #007bff;
  font-weight: bold;
}

/* Specific Styles */
.account-img {
  padding-top: -2px;
}

.nav-buttons {
  display: flex;
  align-items: center;
}

.nav-buttons button {
  background-color: White;
  color: #0056b3;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
  /* Centers the text horizontally */
  height: 50px;
  /* Set a fixed height to ensure vertical centering */
  display: flex;
  justify-content: center;
  align-items: center;
  /* Centers the button text vertically */
}


.nav-buttons button:hover {
  background-color: #eff2f1;
}

.nav-buttons {
  padding: 10px;
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

.btn-danger:hover {
  background-color: darkorange;
  /* Darker shade on hover */
}

.options {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.5rem;
  color: black;
}

.adjust-category {
  align-items: center;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.adjust-category button:hover {
  background-color: #0d369b;
}

.manage-employees-button {
  display: flex;
  flex-direction: column;
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
}

/* Game Management Box */
.game-management-box {
  margin-top: 1rem;
  padding: 20px;
  background: #f4f4f4;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.game-management-box h3 {
  font-weight: bold;
  text-align: center;
  margin-bottom: 1rem;
}

.game-management-box form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.game-management-box label {
  font-weight: bold;
}

.game-management-box input,
.game-management-box textarea {
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.game-management-box button {
  background-color: #1140d9;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
  font-size: 1rem;
  transition: background-color 0.3s;
}

.game-management-box button:hover {
  background-color: #0d369b;
}

.button-container {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
}

.manage-btn {
  display: flex;
  background-color: #22bae0;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 20px;
  font-size: 0.9rem;
}

.manage-btn:hover {
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
</style>
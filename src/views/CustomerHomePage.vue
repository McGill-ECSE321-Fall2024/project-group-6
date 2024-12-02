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
            <input type="search" v-model="searchQuery" class="search" placeholder="Search game..." />
            <i class="bx bx-search" @click="searchByName"></i>
          </div>
          </div>
          <div class="user-options">
            <div class="dropdown">
                <button class="dropbtn"><img src="../assets/person-circle.svg" class="account-img"></button>
                <div class="nav-buttons">
                    <button @click="goToCustomerAccount" >Account</button>
                    <button @click="goToCustomerOrders" class="order-btn">Orders</button>
                    <button @click="logout" class="logout-btn">Log Out</button>
                </div>
            </div>
            <button @click="goToCustomerCart"><img src="../assets/pngaaa.com-5034351.png" class="cart-img" @click="goToCustomerCart"></button>
            <button @click="goToCustomerWishlist"><img src="../assets/White-Heart.png" class="wishlist-img" @click="goToCustomerWishlist"></button>
        </div>
      </nav>
    </header>
    <div class="container">
     
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
        <h3 id="catalog-title">Game Inventory</h3>
        <div v-if="games.length === 0">No games found</div>
        <div v-for="game in games":key="game.gameId"class="game-card" @click="viewGameDetails(game.gameId)">
          <img :src="game.photoURL" alt="Game Image" class="game-image" />
          <div class="game-info">
            <h3>{{ game.name }}</h3>
            <p class="game-price"><strong>Price:</strong> ${{ game.price }}</p>
            <p class="game-description"><strong>Description:</strong> {{ game.description }}</p>
            <p class="game-stock"><strong>Stock:</strong> {{ game.stockQuantity }} left</p>
            <button class="btn-danger" @click="viewGameDetails(game.gameId)">Edit</button>
          </div>
        </div>
      </main>

    </div>
  </template>
  
  

  <script>
import axios from "axios";
import { RouterLink } from 'vue-router';
import router from '@/router';

export default {
props: ['customerId', 'loggedIn'],

  data() {
    return {
      searchQuery: "",
      selectedCategory: "",
      categories: [],
      games: [],
      tasks: [],
      customerId: 0,
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
          `http://localhost:8080/customers/${this.customerId}`
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
      this.$router.push({ name: "customer-gamepage", 
      params: { 
        gameId: gameId,
        customerId:this.customerId,
        loggedIn:true
      } });
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
    async goToCustomerAccount(){
      router.push({
          name: 'customer-account',
          params: {
            customerId: this.customerId,
            loggedIn: true
          }
          
        });
    },

    logout() {
        this.$router.push('/SignIn');
    },

    async goToCustomerOrders() {
        router.push({
          name: 'customer-orders',
          params: {
            customerId: this.customerId,
            loggedIn: true
          }
          
        }); 
    },
    async goToCustomerCart() {
        router.push({
          name: 'cart',
          params: {
            customerId: this.customerId,
            loggedIn: true
          }
          
        }); 
    },
    async goToCustomerWishlist() {
        router.push({
          name: 'wishlist',
          params: {
            customerId: this.customerId,
            loggedIn: true
          }
          
        });
    }
  },
  created() {
    if (!this.isLoggedIn()) {
        this.$router.push({ name: 'sign in' });
        alert('Please log in before accessing this page.');
      } else {
        this.customerId = this.customerId;
        console.log(this.customerId);
        this.fetchCategories();
        this.fetchGames();
        this.fetchTasks();
    }
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

.user-options {
  display: flex; /* Aligns child elements (buttons) horizontally */
  gap: 10px; /* Adds spacing between buttons (adjust as needed) */
  align-items: center; /* Vertically aligns buttons if needed */
}

.user-options button {
  background: none; /* Remove default button background */
  border: none; /* Remove default button border */
  padding: 0; /* Remove padding around buttons */
  cursor: pointer;
}

.user-options img {
  width: 30px; /* Adjust size of images */
  height: 30px;
}

.dropdown .nav-buttons {
  display: none; /* Initially hide dropdown content */
  position: absolute;
  background-color: white;
  min-width: 160px;
  box-shadow: 0px 8px 16px rgba(0,0,0,0.2);
  z-index: 1;
}

.dropdown:hover .nav-buttons {
  display: block; /* Show dropdown on hover */
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
  height: 100%;
  padding: 20px;
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


.tasks-box {
  width: 300px;
  background: #ffffff;
  border-radius: 8px;
  padding: 20px;
  margin-left: 20px; 
  
}


.tasks-box h4{

padding: 15px;
border-radius: 10px;
box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
transition: transform 0.2s;
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

.tasks-box h4 {
  margin-bottom: 15px;
  color: black
;
}

.tasks-box li p {
  margin: 5px 0 0;
  font-size: 14px;
  color: #1140d9;
}


.catalog {
  flex: 1;
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.game-card {
  background: #f9f9f9;
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 1rem;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 20px;
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

.game-price {
  color: #e60000;
  font-weight: bold;
}

.game-description {
  color: #333;
}

.game-stock {
  color: #007bff;
  font-weight: bold;
}
.account-img {
    padding-top: -2px;
   
}
#catalog-title{
    padding-bottom: 20px;
}
.nav-buttons {

    display: flex;
    align-items: center;
  }
  
  .nav-buttons button {
    background-color: #0056b3;
    color: #0056b3;
    border: none;
    padding: 0.5rem 1rem;
    border-radius: 5px;
    cursor: pointer;
    text-align: center;  /* Centers the text horizontally */
    height: 50px;  /* Set a fixed height to ensure vertical centering */
    display: flex;
    justify-content: center;
    align-items: center;  /* Centers the button text vertically */
  }
  .nav-buttons button img{
    padding-bottom: 15px;
    padding-left: 10px;
    
  }
  
  .nav-buttons button:hover {
    background-color: #eff2f1;
  }
  .nav-buttons{
    padding: 10px;
  }

  .btn-danger {
    background-color: yellow; /* Yellow background */
    color: black; /* Black text */
    border: none; /* No border */
    padding: 10px 20px; /* Padding for the button */
    border-radius: 5px; /* Rounded corners */
    font-size: 16px; /* Font size */
    cursor: pointer; /* Pointer cursor on hover */
    font-weight: bold; /* Bold text */
    transition: background-color 0.3s; /* Smooth transition */
  }

  .btn-danger:hover {
    background-color: darkorange; /* Darker shade on hover */
  }
  .options{
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.5rem;
  color: black;
}

  </style>
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

        <div class="user-options">
          <div class="dropdown">
            <button class="dropbtn"><img src="../assets/account.png" class="account-img"></button>
            <div class="nav-buttons">
              <button @click="goToCustomerAccount">Account</button>
              <button @click="goToCustomerOrders" class="order-btn">Orders</button>
              <button @click="logout" class="logout-btn">Log Out</button>
            </div>
          </div>
          <RouterLink><img src="../assets/White-Heart.png" @click="goToCustomerWishlist">
          </RouterLink>

          <RouterLink><img src="../assets/pngaaa.com-5034351.png" @click="goToCustomerCart">
          </RouterLink>
        </div>
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
          {{ `${category.name}` }}
        </option>
      </select>
    </aside>


    <main class="catalog">
      <div v-if="games.length === 0">No games found</div>
      <div v-for="game in games" :key="game.gameId" class="game-card">
        <img :src="game.photoURL" alt="Game Image" class="game-image" @click="goToGamePage(game)"/>
        <div class="game-info" >
          <h3>{{ game.name }}</h3>
          <div class="price-box">
          <p v-if="game.promotion > 0" class="game-price promo">
            <strong>Price:</strong> ${{ game.price }} 
            <span class="promo-badge">{{ game.promotion*100}}% off!</span>
          </p>
          <p v-else class="game-price">
            <strong>Price:</strong> ${{ game.price }}
          </p>
        </div>
        <div class="button-container">
          <button class="btn" @click="addToCart(game)">Add to Cart</button>
          <button class="btn" @click="addToWishlist(game)">Add to Wishlist</button>
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

export default {
  props: ['customerId', 'loggedIn'],

  data() {
    return {
      searchQuery: "",
      selectedCategory: "",
      categories: [],
      games: [],
      tasks: [],
      customerID: 0,
      showPopup: false,
      popupMessage: "",
    };
  },
  methods: {
    isLoggedIn() {
      return this.loggedIn;
    },

    calculateFinalPrice(price, promotion) {
    const discount = (price * promotion) / 100;
    const finalPrice = price - discount;
    return finalPrice.toFixed(2); // Return price rounded to 2 decimal places
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
    async goToGamePage(game){
      router.push({
                name: 'customer-gamepage',
                params: {
                  customerId: this.customerId,
          loggedIn: true,
                    gameId: game.gameId
                }

            });
    },
    async addToWishlist(game) {
      try {

        const response = await axios.put(`http://localhost:8080/customers/${this.customerID}/wishlist/add/${game.gameId}`);
        //this.games = [response.data];
        this.popupMessage = `${game.name} was added to your wishlist.`;
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 2000);
      } catch (error) {
        console.error('Error searching for games:', error);
        alert(response.data);
      }
    },
    async addToCart(game) {
      try {

        const response = await axios.put(`http://localhost:8080/customers/${this.customerID}/cart/add/${game.gameId}`);
        //this.games = [response.data];
        this.popupMessage = `${game.name} was added to your cart.`;
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 2000);
      } catch (error) {
        console.error('Error searching for games:', error);

        alert(response.data);
      }
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
    async goToCustomerAccount() {
      router.push({
        name: 'customer-account',
        params: {
          customerId: this.customerId,
          loggedIn: true
        }

      });
    },

    logout() {
      this.$router.push('/');
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
        name: 'customer-cart',
        params: {
          customerId: this.customerId,
          loggedIn: true
        }

      });
    },
    async goToCustomerWishlist() {
      router.push({
        name: 'customer-wishlist',
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
      this.customerID = this.customerId;
      console.log(this.customerId);
      this.fetchCategories();
      this.fetchGames();
      this.fetchTasks();
    }
  }
}
</script>


<style scoped>
* {
  margin: 0;
  padding: 0;
  text-decoration: none;
  list-style: none;
  font-family: "poppins";
}

.user-options {
    display: flex;

    align-items: center;
    /* Vertically aligns buttons if needed */
}

.user-options button {
    background: none;
    /* Remove default button background */
    border: none;
    /* Remove default button border */
    padding: 0;
    /* Remove padding around buttons */
    cursor: pointer;
}

.user-options img {
    margin-top: 15px;
    margin-right: 10px;
    align-items: center;
    width: 40px;
}

.dropdown .nav-buttons {
    display: none;
    /* Initially hide dropdown content */
    position: absolute;
    background-color: rgba(255, 255, 255, 0.906);
    background: #ffff;

    color: #ffff;
    z-index: 1;
}

.dropdown:hover .nav-buttons {
    display: block;
    /* Show dropdown on hover */
    border: solid;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.dropdown:hover .nav-buttons button {
    display: flex;
    /* Show dropdown on hover */

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

header .img {
  margin-top: 15px;
  margin-right: 10px;
  align-items: center;
  width: 40px;
}

.container {
  display: flex;
  justify-content: space-between;
  background-color: #ffff;
  color: #000;
  height: 100vh;
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


.tasks-box h4 {

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
  color: black;
}

.tasks-box li p {
  margin: 5px 0 0;
  font-size: 14px;
  color: #1140d9;
}

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
  background-color: #fff; /* Optional: Add a subtle background */
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
  background-color: rgba(236, 137, 137, 0.999); /* Bright orange to catch attention */
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

.account-img {
  padding-top: -2px;

}

.nav-buttons {

  display: flex;
  align-items: center;
}

button {
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.nav-buttons button {
font-size: 1rem;
color: #1033a4;
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

.nav-buttons button img {
padding-bottom: 15px;
padding-left: 10px;

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

.button-container {
  display: flex;
  justify-content: center;
  gap: 10px;
}

.button-container .btn {
  background: #88b9df;
}

.button-container .btn:hover {
  color: #88b9df;
  background: #ffffff;
}
</style>
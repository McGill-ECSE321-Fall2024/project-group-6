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
          <div class="button-container">
            <button @click="logout" class="manage-btn">Manage Employees</button>
          
            <button @click="logout" class="logout-btn">Sign Out</button>
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
        <input type="String" id="game-name" name="name" placeholder="Gamey Game" v-model="gameName" required>

        <label for="game-description">Description:</label>
        <textarea type="String" id="game-description" name="description" placeholder="Write something attractive" v-model="gameDescription"  required></textarea>

        <label for="game-price">Price:</label>
        <input type="float" id="game-price" name="price" placeholder="20.0" v-model="gamePrice" required>

        <label for="game-stockQuantity">Stock Quantity:</label>
        <input type="number" id="game-stockQuantity" name="stockQuantity" placeholder="80" v-model="stockQuantity" required>

        <label for="game-photoURL">Photo URL:</label>
        <input type="String" id="game-photoURL" name="photoURL" placeholder="https://example.com/images/game.jpg" v-model="gameURL" required>

        <label for="game-categories">Categories:</label>
        <input type="number" id="game-categories" name="categories" placeholder="Id of the category to add" v-model="categoryId" required> 

        <button type="submit" @click="createGame()">Add Game</button>
    </form>
    </aside>
    </aside>
    </aside>

      <main class="catalog">
        <h3 id="catalog-title">Game Inventory</h3>
        <div v-if="games.length === 0">No games found</div>
        <div v-for="game in games":key="game.gameId"class="game-card" >
          <img :src="game.photoURL" alt="Game Image" class="game-image" />
          <div class="game-info">
            <h3>{{ game.name }}</h3>
            <p class="game-price"><strong>Price:</strong> ${{ game.price }}</p>
            <p class="game-description"><strong>Description:</strong> {{ game.description }}</p>
            <p class="game-stock"><strong>Stock:</strong> {{ game.stockQuantity }} left</p>
            <button class="btn-danger" @click="viewGameDetails(game.gameId)">Edit</button>
            <button v-if="game.toBeRemoved" class="btn-dangers" @click="deleteGame(game.gameId)">Delete</button>
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
props: ['loggedIn'],

  data() {
    return {
      searchQuery: "",
      selectedCategory: "",
      selectedCategoryId: null,
      categories: [],
      categoryId: '',
      categoryName:'',
      categoryIdsArray:[],
      games: [],
      unapprovedGames: [],
      newCategoryName: '',
      category: {
        categoryName: '',
      },
      categoryIdRemove:"",
      successMessage: '',
      managerId: 0,
      gameName:'',
      gameDescription:'',
      gamePrice:0.0,
      stockQuantity:0,
      gameURL:'',

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
      } catch (error) {
        alert(error);
        console.error("Error fetching games:", error);
      }
    },


    async addCategory(name) {
    var check="false";
    //this.categoryIdsArray.push(id);
        for(var i=0;i<this.categories.length;i++){
            if(this.categories[i]["name"]==name){
                check="true";
            }
        }
        //this.categories=this.categoryIdsArray;
        if(check =="false"){
            const newCategory = {
            name:this.categoryName     
          };
        
          await axiosClient.post('/categories', newCategory);
          await this.fetchCategories();
        }
        else{
          alert("The category already exists");
          //await this.fetchGameDetails();
        }
    },

 
    viewGameDetails(gameId) {
        console.log(gameId);
      this.$router.push({ name: "manager-gamepage",
      params: {
        gameId: gameId,
        managerId:this.managerID,
        loggedIn:true
      } });
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

    async goToManagerAccount(){
      router.push({
          name: 'manager-account',
          params: {
            managerId: this.managerId,
            loggedIn: true
          }
          
        });
        
    },

    async manageEmployees() {
      this.$router.push('/manager/employees');
    },



    logout() {
      this.$router.push('/SignIn');
    },
    async createGame(){
        var check="false";

        for(var i=0;i<this.games.length;i++){
            if(this.games[i]["name"]==this.gameName){
                check="true";
            }
        }
        //this.categories=this.categoryIdsArray;
        if(check =="false"){
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

        await axiosClient.post('/employees/games',newgame);
          await this.fetchGames();
        }else{
          alert("This game already exists");
          
          
        }
    }
  },
  created() {
      if (!this.isLoggedIn()) {
        this.$router.push({ name: 'sign in' });
        alert('Please log in before accessing this page.');
      } else {
        this.employeeID = this.employeeId;
        console.log(this.employeeID);
        this.fetchGames();
        this.fetchCategories();
        this.filterByCategory();
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

/* Container */
.container {
    display: flex;
    justify-content: space-between;
    background-color: #ffff;
    color: #000;
    height: 100%;
    padding: 20px;
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

/* Catalog */
.catalog {
    flex: 1;
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
}

/* Game Card */
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

/* Specific Styles */
.account-img {
    padding-top: -2px;
}

#catalog-title {
    padding-bottom: 20px;
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
    text-align: center;  /* Centers the text horizontally */
    height: 50px;  /* Set a fixed height to ensure vertical centering */
    display: flex;
    justify-content: center;
    align-items: center;  /* Centers the button text vertically */
}


.nav-buttons button:hover {
    background-color: #eff2f1;
}

.nav-buttons {
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

.options {
    display: flex;
    justify-content: space-between;
    margin-bottom: 0.5rem;
    color: black;
}

.adjust-category {
    display: flex;
    flex-direction: column;
    gap: 1rem;
}

.adjust-category button {
    padding: 0.5rem;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 5px;
    color: black;
    background-color: white;
    cursor: pointer;
}

.adjust-category button:hover {
    background-color: lightgrey;
}

.adjust-category button:active {
    background-color: grey;
}

.manage-employees-button {
    display: flex;
    flex-direction: column;
    padding: 0.5rem;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 5px;
    color: black;
    background-color: white;
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

.logout-btn {
    background-color: #ff6f61;
    color: white;
    border: none;
    padding: 0.5rem 1rem;
    border-radius: 20px;
    font-size: 0.9rem;
}
.btn-dangers {
    margin-left: 5px;
    background-color: red; /* Yellow background */
    color: black; /* Black text */
    border: none; /* No border */
    padding: 10px 20px; /* Padding for the button */
    border-radius: 5px; /* Rounded corners */
    font-size: 16px; /* Font size */
    cursor: pointer; /* Pointer cursor on hover */
    font-weight: bold; /* Bold text */
    transition: background-color 0.3s; /* Smooth transition */
  }

  </style>
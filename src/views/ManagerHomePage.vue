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
          <div class="nav-buttons">
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
          <input type="number" v-model="categoryId" />
          <button @click="addCategory(categoryId)" class="add-category">Add Category</button>
        </div>
        <div class="form-group">
          <label for="removeCategoryId">Remove Category </label>
          <input type="number" v-model="categoryIdRemove" />
          <button @click="removeCategory(categoryIdRemove)" class="add-category">Remove Category</button>
        </div>
        </div>
      <aside class="unapproved-box">
        <h3 >Unapproved Games: </h3>
        <ul v-if="unapprovedGames.length > 0">
          <li v-for="unapprovedGame in unapprovedGames" >
            <h4>{{ unapprovedGame }}</h4>
          </li>
        </ul>
        <p v-else>No unapproved games</p>
      </aside>
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
props: ['loggedIn'],

  data() {
    return {
      searchQuery: "",
      selectedCategory: "",
      selectedCategoryId: null,
      categories: [],
      categoryId: '',
      categoryIdsArray:[],
      games: [],
      unapprovedGames: [],
      newCategoryName: '',
      category: {
        categoryName: '',
      },
      categoryIdRemove:"",
      successMessage: '',
      managerId: 0
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

    async saveAfterCategoryChange(){
        try {
        
        await axios.put(`http://localhost:8080/games/id/${this.gameID}`, this.game);
        alert("Changes saved successfully!");
        await this.fetchGameDetails();
      } catch (error) {
        console.error("Error saving game details:", error);
        //alert(error.message);
      }
    },

    async addCategory(id) {
    var check="false";
        this.categoryIdsArray.push(id);
        for(var i=0;i<this.categories.length;i++){
            if(this.categories[i]["categoryId"]!==id){
            this.categoryIdsArray.push(this.categories[i]["categoryId"]);
            }else{
              check="true";
              //alert("The category already is assigned to this game");
            }
        }
        this.categories=this.categoryIdsArray;
        if(check =="false"){
        await this.saveAfterCategoryChange();
        }else{
          alert("The category already is assigned to this game");
          await this.fetchGameDetails();
        }
    },

  removeCategory() {
    if (!this.selectedCategoryId) {
      console.error("Please select a category to remove");
      return;
  }

  axios
    .delete(`http://localhost:8080/categories/${this.selectedCategoryId}`)
    .then(response => {
      console.log("Category removed successfully");
      // Remove the category from the local list
      this.categories = this.categories.filter(category => category.id !== this.selectedCategoryId);
      this.selectedCategoryId = null; // Clear the selection
    })
    .catch(error => {
      console.error("Error removing category:", error);
    });
  },

    viewGameDetails(gameId) {
      this.$router.push({ name: "manager-gamepage",
      params: {
        gameId: gameId,
        managerId:this.managerId,
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

    async fetchUnapprovedGames() {
      try {
        const response = await axios.get(
          `http://localhost:8080/manager/${this.managerID}`
        );
        const unapprovedGames = response.data["unapprovedGames"];
        for (let i = 0; i < unapprovedGames.length; i++) {
          const unapprovedGame = unapprovedGames[i];
          const parsedGame = JSON.parse(unapprovedGame.trim());
          this.unapprovedGames.push(parsedGame.unapprovedGame);
    }
      } catch (error) {
        console.error("Error fetching unapproved games:", error);
      }
    },

    logout() {
      this.$router.push('/SignIn');
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

.success-message {
  color: #1140d9;
  font-weight: bold;
  text-align: center;
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

.unapproved-box {
  margin-top: 1rem;
  text-align: center;
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
  </style>
<!-- Author: Joseph -->

<template>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/boxicons/2.1.4/css/boxicons.min.css" rel="stylesheet" />
  <header>
    <nav class="navbar">
      <div class="logo">
        <h2>GameShop</h2>
      </div>
      <div class="navmenu"></div>
      <div class="button-container">
        <button @click="goToEmployeeHome" class="homepage-btn">HomePage</button>
        <button @click="logout" class="logout-btn">Logout</button>
      </div>

    </nav>
  </header>
  <div class="container">
    <div v-if="showPopup" class="popup">
      {{ popupMessage }}
    </div>
    <main class="catalog">
      <div class="form-group">
        <label for="name">Name</label>
        <input type="text" id="name" v-model="game.name" />
      </div>
      <div class="form-group">
        <label for="description">Description</label>
        <input type="text" id="description" v-model="game.description" />
      </div>
      <div class="form-group">
        <label for="price">Price</label>
        <input type="float" id="price" v-model="game.price" />
      </div>
      <div class="form-group">
        <label for="stockQuantity">Stock Quantity</label>
        <input type="number" id="stockQuantity" v-model="game.stockQuantity" />
      </div>
      <div class="form-group">
        <label for="photoURL">Photo URL</label>
        <input type="text" id="photoURL" v-model="game.photoURL" />
      </div>

      <div class="form-group">
        <label for="promotion">Promotion (From 0-1)</label>
        <input type="float" id="promotion" v-model="game.promotion" />
      </div>
      <button type="submit" class="save-btn" @click="saveChanges">Save Changes</button>
    </main>

    <aside class="categories-aside">
      <h3>Categories</h3>
      <ul class="categories-list">
        <li v-for="(category) in game.categories">
          <span class="category-name">{{ category.categoryName }}</span>
          <span class="category-id">{{ category.categoryId }}</span>
        </li>
      </ul>

      <div classs="container">
        <h3 style="margin: 1;">Search for Category Ids</h3>
        <select>
          <option v-for="category in catList" :key="category.id" :value="category.name">
            {{ `${category.name} (${category.id})` }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label for="newCategoryId">Add New Category To Game </label>
        <input type="number" v-model="categoryId" />
        <button @click="addCategory(categoryId)" class="add-category">Add Category By Id</button>
      </div>
      <div class="form-group">
        <label for="removeCategoryId">Remove Category From This Game </label>
        <input type="number" v-model="categoryIdRemove" />
        <button @click="removeCategory(categoryIdRemove)" class="add-category">Remove Category By Id</button>
      </div>

    </aside>
  </div>
</template>
<script>
import axios from "axios";
import { RouterLink } from 'vue-router';
import router from '@/router';

export default {
  props: ['employeeId', 'loggedIn', 'gameId'],
  data() {
    return {
      searchQuery: "",
      categoryId: "",
      categoryIdRemove: "",
      categories: "",
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
      employeeID: 0,
      gameID: 0,
      catList: "",
      popupMessage: "",
      showPopup: false,
    };
  },
  methods: {
    isLoggedIn() {
      return this.loggedIn;
    },
    async fetchGameDetails() {

      try {

        const response = await axios.get(`http://localhost:8080/games/id/${this.gameID}`);
        //console.log(response);
        this.game = response.data;
      } catch (error) {
        console.error("Error fetching game details:", error);
      }
    },
    async saveChanges() {
      try {
        for (var i = 0; i < this.game.categories.length; i++) {
          this.categoryIdsArray.push(this.game.categories[i]["categoryId"]);

        }
        this.game.categories = this.categoryIdsArray;
        console.log("array of category ids is " + this.categoryIdsArray);
        await axios.put(`http://localhost:8080/games/id/${this.gameID}`, this.game);

        await this.fetchGameDetails();

        this.popupMessage = "Changes saved successfully!";
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);
      } catch (error) {
        this.popupMessage = "Error saving game details:";
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);
      }
    },
    async saveAfterCategoryChange() {
      this.categoryIdsArray = [];
      try {

        await axios.put(`http://localhost:8080/games/id/${this.gameID}`, this.game);
        await this.fetchGameDetails();

        this.popupMessage = "Changes saved successfully!";
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);

      } catch (error) {
        this.popupMessage = "Unable to save changes";
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);
      }
    },
    async addCategory(id) {
      var check = "false";
      this.categoryIdsArray = [];
      var counter = 0;
      await this.fetchCategories();

      for (var j = 0; j < this.categories.length; j++) {
        console.log(this.categories[j]["id"]);
        if (this.categories[j]["id"] !== id) {
          counter++;
        }
      }
      if (counter == this.categories.length) {
        this.fetchGameDetails();
        this.popupMessage = "The category does not exist";
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);
      }
      else {
        this.categoryIdsArray.push(id);
        for (var i = 0; i < this.game.categories.length; i++) {
          if (this.game.categories[i]["categoryId"] !== id) {
            this.categoryIdsArray.push(this.game.categories[i]["categoryId"]);
          }
          else {
            check = "true";
          }
        }
        this.game.categories = this.categoryIdsArray;
        if (check == "false") {
          this.game.categories = this.categoryIdsArray;
          await this.saveAfterCategoryChange();
        } else {
          this.fetchGameDetails();
          this.popupMessage = "The category already is assigned to this game";
          this.showPopup = true;
          setTimeout(() => (this.showPopup = false), 3000);

        }
        this.categoryId="";
      }
    },
    goToEmployeeHome() {
      router.push({
        name: 'employee-homepage',
        params: {
          employeeId: this.employeeId,
          loggedIn: true
        }

      });
    },
    async fetchCategories() {
      try {
        const response = await axios.get('http://localhost:8080/categories');

        this.categories = response.data["categories"];
        this.catList = response.data["categories"];
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    },
    logout() {
      this.$router.push('/');
    },
    async removeCategory(id) {
      this.categoryIdsArray = [];
      var counter = 0;
      var check = this.game.categories.length;
      for (var i = 0; i < this.game.categories.length; i++) {
        console.log(this.game.categories[i]["categoryId"] + " the id I am passig is " + id);;
        if (this.game.categories[i]["categoryId"] != id) {
          console.log("I am here");
          this.categoryIdsArray.push(this.game.categories[i]["categoryId"]);
          counter++;
        }
      }
      this.game.categories = this.categoryIdsArray;
     
      if (counter !== check) {
        await this.saveAfterCategoryChange();
      } else {
        this.popupMessage = "This category does not exist for this game, try another one";
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);
      }
      this.categoryId="";
    }
  },
  created() {
    if (!this.isLoggedIn()) {
      this.$router.push({ name: "sign in" });
      alert("Please log in before accessing this page.");
    } else {
      this.employeeID = this.employeeId;
      this.gameID = this.gameId;
      console.log(this.gameID);
      this.fetchGameDetails();
      this.fetchCategories();
    }
  }

};
</script>
<style scoped>
* {
  margin: 0;
  padding: 0;
  text-decoration: none;
  list-style: none;
  font-family: "Poppins", sans-serif;
}

.navbar {
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 90px;
  background-color: #1033a4;
  padding: 0 40px;
  align-items: center;
}

.navbar h2 {
  color: #ffffff;
  font-size: 25px;
  font-weight: 500;
  margin-left: 20px;
}

.navmenu {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.navmenu img {
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
  height: 100vh;
  padding: 20px;
}

.container h3 {
  font-weight: bold;
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

.catalog {
  flex: 2;
  padding: 20px;
  display: flex;
  flex-direction: column;
}

.categories-aside {
  flex: 1;
  padding: 20px;

  border-radius: 8px;
  margin-left: 20px;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  font-size: 1rem;
  margin-bottom: 0.5rem;
}

.form-group input {
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: 100%;
}

.form-group label {
  font-weight: bold;
}

.save-btn {
  margin-top: 1.5rem;
  padding: 0.75rem;
  font-size: 1rem;
  background-color: #22bae0;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.save-btn:hover {
  background-color: #61c2da;
}

.add-category {
  margin-top: 1.5rem;
  padding: 0.75rem;
  background-color: #1033a4;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  width: 100%
}

.add-category:hover {
  background-color: #445ba3;
}

.categories-list {
  list-style-type: none;
  padding: 0;
  margin: 0 0 20px 0;
}

.categories-list li {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.5rem;
  color: black;
}

.categories-list li span {
  font-size: 1rem;
  margin-right: 10px;
  /* Add space between the name and ID */
}

.category-id {
  font-size: 1rem;
}

.button-container {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
}

.button-container:hover {
  cursor: pointer;
}

.homepage-btn {
  display: flex;
  background-color: #22bae0;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 20px;
  font-size: 0.9rem;
}

.homepage-btn:hover {
  background-color: #61c2da;
  cursor: pointer;
}

.logout-btn:hover {
  background-color: #fa8c82;
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

select {
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-bottom: 1rem;
}
</style>

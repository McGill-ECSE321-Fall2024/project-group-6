<template>
  <div class="main-container">
    <header class="header">
      <div class="logo">
        <h2>GameShop</h2>
      </div>
      <div class="navmenu">
        <div class="search-box">
          <input type="search" class="search" placeholder="Search game...">
          <i class="bx bx-search"></i>
        </div>
        <button @click="goToDashboard" class="back-btn">Dashboard</button>
        <button @click="logout" class="logout-btn">Logout</button>
      </div>
    </header>
    <div class="content">
      <h3>Edit Game Information</h3>
      <form @submit.prevent="updateGame">
        <div class="form-group">
          <label for="name">Game Name</label>
          <input type="text" id="name" v-model="game.name" />
        </div>
        <div class="form-group">
          <label for="price">Price ($)</label>
          <input type="number" id="price" v-model.number="game.price" />
        </div>
        <div class="form-group">
          <label for="stockQuantity">Stock Quantity</label>
          <input type="number" id="stockQuantity" v-model.number="game.stockQuantity" />
        </div>
        <div class="form-group">
          <label for="description">Description</label>
          <textarea id="description" v-model="game.description"></textarea>
        </div>
        <div class="form-group">
          <label for="imageUrl">Image URL</label>
          <input type="text" id="imageUrl" v-model="game.imageUrl" />
        </div>
        <button type="submit" class="save-btn">Save Changes</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      game: {
        name: "",
        price: 0,
        stockQuantity: 0,
        description: "",
        imageUrl: "",
      },
    };
  },
  methods: {
    async fetchGameDetails() {
      const gameId = this.$route.params.id; // Get game ID from route params
      try {
        const response = await axios.get(`http://localhost:8080/games/${gameId}`);
        this.game = response.data;
      } catch (error) {
        console.error("Error fetching game details:", error);
      }
    },
    async updateGame() {
      const gameId = this.$route.params.id;
      try {
        await axios.put(`http://localhost:8080/games/${gameId}`, this.game);
        alert("Game information updated successfully!");
        this.$router.push("/manager"); // Redirect to dashboard after saving
      } catch (error) {
        console.error("Error updating game:", error);
        alert("Failed to update game information.");
      }
    },
    goToDashboard() {
      this.$router.push("/manager"); // Navigate back to manager dashboard
    },
    logout() {
      this.$router.push("/signin"); // Redirect to login page
    },
  },
  async created() {
    await this.fetchGameDetails();
  },
};
</script>

<style scoped>
/* General Reset */
* {
  margin: 0;
  padding: 0;
  text-decoration: none;
  font-family: "Poppins", sans-serif;
}

/* Header and Navigation */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #1033a4;
  color: white;
  padding: 1rem 2rem;
}

.logo h2 {
  font-size: 25px;
  font-weight: 500;
}

.navmenu {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.search-box {
  display: flex;
  align-items: center;
  position: relative;
}

.search-box .search {
  width: 400px;
  padding: 8px 12px;
  border-radius: 50px;
  border: none;
  font-size: 16px;
}

.search-box .bx-search {
  color: white;
  background-color: #1140d9;
  padding: 8px;
  border-radius: 50%;
  position: absolute;
  right: 10px;
  cursor: pointer;
}

.back-btn,
.logout-btn {
  background-color: #88b9df;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 50px;
  font-size: 14px;
  cursor: pointer;
}

.back-btn:hover,
.logout-btn:hover {
  background-color: white;
  color: #88b9df;
  border: 1px solid #88b9df;
}

/* Main Content */
.content {
  margin: 2rem auto;
  padding: 2rem;
  max-width: 800px;
  background-color: white;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

h3 {
  font-size: 24px;
  margin-bottom: 1rem;
  text-align: center;
}

.form-group {
  margin-bottom: 1.5rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
}

input,
textarea {
  width: 100%;
  padding: 0.8rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
}

textarea {
  resize: none;
}

.save-btn {
  display: block;
  width: 100%;
  padding: 0.8rem;
  background-color: #88b9df;
  border: none;
  color: white;
  border-radius: 50px;
  font-size: 16px;
  cursor: pointer;
}

.save-btn:hover {
  background-color: white;
  color: #88b9df;
  border: 1px solid #88b9df;
}
</style>

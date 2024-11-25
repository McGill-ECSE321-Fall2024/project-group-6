<!-- Author: Marine Dupuy -->

<template>
  <div class="main-container">
    <header class="header">
      <div class="app-name">GameShop - Game Details</div>
      <div class="nav-buttons">
        <button @click="goToAccountPage" class="account-btn">Account</button>
        <button @click="logout" class="logout-btn">Logout</button>
      </div>
    </header>
    <div class="content">
      <div class="game-details">
        <img :src="game.imageUrl" alt="Game Image" class="game-image" />
        <div class="game-info">
          <h1>{{ game.name }}</h1>
          <p><strong>Price:</strong> ${{ game.price }}</p>
          <p><strong>Stock:</strong> {{ game.stockQuantity }}</p>
          <p><strong>Description:</strong> {{ game.description }}</p>
        </div>
      </div>
      <div class="actions">
        <h3>Manage Game</h3>
        <div class="category-assignment">
          <h4>Assign to Category</h4>
          <select v-model="selectedCategory">
            <option value="" disabled>Select Category</option>
            <option v-for="category in categories" :key="category.id" :value="category.name">
              {{ category.name }}
            </option>
          </select>
          <button @click="assignCategory">Assign</button>
        </div>
        <button class="danger-btn" @click="deleteGame">Delete Game</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      game: {}, // Details of the current game
      categories: [], // Available categories
      selectedCategory: '', // Selected category for assignment
    };
  },
  methods: {
    async fetchGameDetails() {
      const gameId = this.$route.params.id;
      try {
        const response = await axios.get(`http://localhost:8080/games/${gameId}`);
        this.game = response.data;
      } catch (error) {
        console.error('Error fetching game details:', error);
      }
    },
    async fetchCategories() {
      try {
        const response = await axios.get('http://localhost:8080/categories');
        this.categories = response.data;
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    },
    async assignCategory() {
      if (!this.selectedCategory) {
        alert('Please select a category to assign.');
        return;
      }
      try {
        const response = await axios.put(
          `http://localhost:8080/games/${this.game.id}/category`,
          { category: this.selectedCategory }
        );
        alert('Category assigned successfully!');
        this.selectedCategory = ''; // Reset the dropdown
      } catch (error) {
        console.error('Error assigning category:', error);
        alert('Failed to assign category.');
      }
    },
    async deleteGame() {
      if (!confirm('Are you sure you want to delete this game?')) {
        return;
      }
      try {
        await axios.delete(`http://localhost:8080/games/${this.game.id}`);
        alert('Game deleted successfully!');
        this.$router.push('/employee'); // Redirect to dashboard
      } catch (error) {
        console.error('Error deleting game:', error);
        alert('Failed to delete game.');
      }
    },
    goBack() {
      this.$router.push('/employee'); // Navigate back to employee dashboard
    },
    goToAccountPage() {
      this.$router.push({ name: 'account' }); // Navigate to the account page
    },
    logout() {
      this.$router.push('/signin'); // Redirect to login page
    },
  },
  async created() {
    await this.fetchGameDetails();
    await this.fetchCategories();
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
  padding: 2rem;
}

.game-details {
  display: flex;
  gap: 2rem;
  margin-bottom: 2rem;
}

.game-image {
  max-width: 300px;
  height: auto;
  border-radius: 5px;
}

.game-info h1 {
  margin: 0 0 1rem;
}

.actions {
  margin-top: 2rem;
}

.category-assignment {
  margin-bottom: 2rem;
}

.category-assignment select {
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.category-assignment button {
  margin-top: 1rem;
  padding: 0.8rem;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.category-assignment button:hover {
  background-color: #218838;
}

.danger-btn {
  padding: 0.8rem;
  background-color: #dc3545;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.danger-btn:hover {
  background-color: #c82333;
}
</style>

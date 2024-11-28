<template>
  <div class="main-container">
    <header class="header">
      <div class="logo">
        <h2>GameShop - Manager Dashboard</h2>
      </div>
      <div class="navmenu">
        <div class="search-box">
          <input type="search" class="search" placeholder="Search game..." v-model="searchQuery" />
          <i class="bx bx-search"></i>
        </div>
        <button @click="goToAccountPage" class="account-btn">Account</button>
        <button @click="logout" class="logout-btn">Logout</button>
      </div>
    </header>

    <div class="content">
      <h1>Game Catalog</h1>

      <!-- All Games Section -->
      <div class="section">
        <h2>All Games</h2>
        <div class="game-list">
          <div 
            class="game-card" 
            v-for="game in filteredAllGames" 
            :key="game.id"
          >
            <img :src="game.imageUrl" alt="Game Image" class="game-image" />
            <div class="game-info">
              <h3>{{ game.name }}</h3>
              <p><strong>Price:</strong> ${{ game.price }}</p>
              <p><strong>Stock:</strong> {{ game.stockQuantity }}</p>
            </div>
            <button class="details-btn" @click="viewGameDetails(game.id)">View Details</button>
          </div>
        </div>
      </div>

      <!-- Games Pending Approval Section -->
      <div class="section">
        <h2>Games Pending Approval</h2>
        <div class="game-list">
          <div 
            class="game-card" 
            v-for="game in filteredPendingGames" 
            :key="game.id"
          >
            <img :src="game.imageUrl" alt="Game Image" class="game-image" />
            <div class="game-info">
              <h3>{{ game.name }}</h3>
              <p><strong>Requested By:</strong> {{ game.requestedBy }}</p>
              <p><strong>Description:</strong> {{ game.description }}</p>
            </div>
            <div class="actions">
              <button class="approve-btn" @click="approveGame(game.id)">Approve</button>
              <button class="reject-btn" @click="rejectGame(game.id)">Reject</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      allGames: [], // All games in the catalog
      gamesPendingApproval: [], // Games that require manager approval
      searchQuery: "", // For filtering games
    };
  },
  computed: {
    filteredAllGames() {
      return this.allGames.filter((game) =>
        game.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
    filteredPendingGames() {
      return this.gamesPendingApproval.filter((game) =>
        game.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
  },
  methods: {
    async fetchAllGames() {
      try {
        const response = await axios.get("http://localhost:8080/games");
        this.allGames = response.data;
      } catch (error) {
        console.error("Error fetching games:", error);
      }
    },
    async fetchPendingApprovalGames() {
      try {
        const response = await axios.get("http://localhost:8080/games/pending");
        this.gamesPendingApproval = response.data;
      } catch (error) {
        console.error("Error fetching pending approval games:", error);
      }
    },
    async approveGame(gameId) {
      try {
        await axios.put(`http://localhost:8080/games/${gameId}/approve`);
        alert("Game approved successfully!");
        this.fetchPendingApprovalGames();
      } catch (error) {
        console.error("Error approving game:", error);
        alert("Failed to approve game.");
      }
    },
    async rejectGame(gameId) {
      try {
        await axios.delete(`http://localhost:8080/games/${gameId}/reject`);
        alert("Game rejected successfully!");
        this.fetchPendingApprovalGames();
      } catch (error) {
        console.error("Error rejecting game:", error);
        alert("Failed to reject game.");
      }
    },
    viewGameDetails(gameId) {
      this.$router.push(`/game/id/${gameId}`);
    },
    goToAccountPage() {
      this.$router.push({ name: "account" });
    },
    logout() {
      this.$router.push("/signin");
    },
  },
  async created() {
    await this.fetchAllGames();
    await this.fetchPendingApprovalGames();
  },
};
</script>

<style scoped>
/* General Reset */
* {
  margin: 0;
  padding: 0;
  font-family: "Poppins", sans-serif;
  box-sizing: border-box;
}

/* Header Styles */
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
  font-weight: bold;
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

.navmenu button {
  background-color: #88b9df;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 50px;
  font-size: 14px;
  cursor: pointer;
}

.navmenu button:hover {
  background-color: white;
  color: #88b9df;
  border: 1px solid #88b9df;
}

/* Content */
.content {
  padding: 2rem;
}

.section {
  margin-bottom: 2rem;
}

.game-list {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
}

.game-card {
  border: 1px solid #ccc;
  border-radius: 10px;
  padding: 1rem;
  width: 250px;
  background-color: #f9f9f9;
  text-align: center;
}

.game-image {
  width: 100%;
  height: auto;
  margin-bottom: 1rem;
  border-radius: 10px;
}

.game-info {
  margin-bottom: 1rem;
}

.details-btn,
.approve-btn,
.reject-btn {
  padding: 0.5rem;
  border: none;
  border-radius: 50px;
  cursor: pointer;
}

.details-btn {
  background-color: #007bff;
  color: white;
}

.details-btn:hover {
  background-color: #0056b3;
}

.approve-btn {
  background-color: #28a745;
  color: white;
}

.reject-btn {
  background-color: #dc3545;
  color: white;
}

.approve-btn:hover {
  background-color: #218838;
}

.reject-btn:hover {
  background-color: #c82333;
}
</style>

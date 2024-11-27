<template>
  <div>
    <!-- Header Section -->
    <header>
      <nav class="navbar">
        <div class="logo">
          <h2>GameShop</h2>
        </div>
        <div class="navmenu">
          <div class="search-box">
            <input type="search" class="search" placeholder="Search game...">
            <i class="bx bx-search"></i>
          </div>
          <div class="iconAccount">
            <img src="./account.png" alt="Account">
          </div>
          <RouterLink to="/wishlist"><img src="./White-Heart.png" alt="Wishlist"></RouterLink>
          <RouterLink to="/checkout"><img src="./pngaaa.com-5034351.png" alt="Cart"></RouterLink>
        </div>
      </nav>
    </header>
  
    <!-- Main Game Details Section -->
    <main class="game-page">
      <div class="game-details">
        <img :src="game.photoUrl" alt="Game Image" class="game-image" />
        <div class="game-info">
          <h1>{{ game.name }}</h1>
          <p><strong>Description:</strong> {{ game.description }}</p>
          <p><strong>Price:</strong> ${{ game.price }}</p>
          <p><strong>Stock Quantity:</strong> {{ game.stockQuantity }}</p>
          <p><strong>Categories:</strong> {{ game.categories.join(", ") }}</p>
          <button @click="addToCart" class="btn-add-to-cart">Add to Cart</button>
        </div>
      </div>

      <!-- Reviews Section -->
      <div class="reviews-section">
        <h2>Reviews</h2>
        <div class="reviews" v-if="reviews.length > 0">
          <div v-for="review in reviews" :key="review.id" class="review">
            <p><strong>{{ review.reviewerName || "Anonymous" }}</strong>: {{ review.comment }}</p>
            <p>Rating: {{ review.rating }}/5</p>
          </div>
        </div>
        <p v-else>No reviews available for this game.</p>
        
        <!-- Add Review Form -->
        <h3>Add a Review</h3>
        <form @submit.prevent="submitReview">
          <div class="form-group">
            <label for="rating">Rating (1-5):</label>
            <input type="number" id="rating" v-model="newReview.rating" min="1" max="5" required />
          </div>
          <div class="form-group">
            <label for="comment">Comment:</label>
            <textarea id="comment" v-model="newReview.comment" required></textarea>
          </div>
          <button type="submit" class="btn-submit-review">Submit Review</button>
        </form>
      </div>
    </main>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      game: {
        id: 1,
        name: "Detroit: Become Human",
        description: "Detroit: Become Human is a 2018 adventure game developed by Quantic Dream and published by Sony Interactive Entertainment.",
        price: 50.50,
        stockQuantity: 3,
        categories: [],
        photoUrl: "https://upload.wikimedia.org/wikipedia/en/e/ee/Detroit_Become_Human.jpg",
      },
      reviews: [],
      newReview: {
        rating: null,
        comment: "",
      },
    };
  },
  methods: {
    async fetchGameDetails() {
      try {
        const gameId = this.$route.params.id; 
        const response = await axios.get(`http://localhost:8080/games/id/${gameId}`);
        this.game = response.data;
      } catch (error) {
        console.error("Failed to fetch game details:", error);
      }
    },
    async fetchReviews() {
      try {
        const gameId = this.$route.params.id;
        const response = await axios.get(`http://localhost:8080/review/game/${gameId}`);
        this.reviews = response.data.reviews;
      } catch (error) {
        console.error("Failed to fetch reviews:", error);
      }
    },
    async submitReview() {
      try {
        const gameId = this.$route.params.id;
        const payload = {
          rating: this.newReview.rating,
          comment: this.newReview.comment,
          gameId: gameId,
        };
        await axios.post("http://localhost:8080/review", payload);
        alert("Review added successfully!");
        this.fetchReviews();
        this.newReview.rating = null;
        this.newReview.comment = "";
      } catch (error) {
        console.error("Failed to submit review:", error);
        alert("Failed to submit review. Please try again.");
      }
    },
    async addToCart() {
      try {
        const customerId = 1;
        await axios.put(`http://localhost:8080/customers/${customerId}/cart`, this.game.name);
        alert("Game added to cart successfully!");
      } catch (error) {
        console.error("Failed to add game to cart:", error);
        alert("Failed to add game to cart. Please try again.");
      }
    },
  },
  mounted() {
    this.fetchGameDetails();
    this.fetchReviews();
  },
};
</script>

<style>
/* General Styles */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Poppins", sans-serif;
}

/* Navbar Styles */
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 80px;
  background: #1033a4;
  padding: 0 20px;
  width: 100%;
}

.navbar h2 {
  color: #fff;
  font-size: 25px;
}

.navmenu {
  display: flex;
  align-items: center;
}

.search-box {
  margin-right: 20px;
  position: relative;
}

.search-box .search {
  width: 300px;
  padding: 8px;
  border-radius: 20px;
  border: none;
}

.search-box .bx-search {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  background: #1140d9;
  color: #fff;
  padding: 8px;
  border-radius: 50%;
}

.navmenu img {
  width: 40px;
  margin-left: 20px;
}

/* Main Game Page */
.game-page {
  padding: 40px 20px;
  background-color: #f9f9f9;
  min-height: calc(100vh - 80px); /* Full height minus the navbar */
  width: 100vw; /* Ensures the page spans full width */
}

.game-details {
  display: flex;
  align-items: flex-start;
  gap: 20px;
  margin-bottom: 40px;
  width: 100%; /* Full width */
}

.game-image {
  width: 250px;
  height: auto;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.game-info {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
}

.game-info h1 {
  color: #1033a4;
  margin-bottom: 10px;
}

.game-info p {
  margin-bottom: 10px;
}

.btn-add-to-cart {
  padding: 10px 20px;
  background-color: #49D8B9;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  max-width: 200px; /* Set a max-width to prevent stretching */
  align-self: flex-start; /* Align the button to the left, inside the game-info section */
  margin-top: 10px;
}

.btn-add-to-cart:hover {
  background-color: #1033a4;
}

/* Reviews Section */
.reviews-section {
  margin-top: 40px;
  width: 100%; /* Full width */
}

.reviews {
  margin-bottom: 20px;
}

.review {
  padding: 15px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background: #fff;
  margin-bottom: 10px;
}

form {
  margin-top: 20px;
}

.form-group {
  margin-bottom: 15px;
}

textarea {
  width: 100%;
  height: 100px;
  border-radius: 5px;
  border: 1px solid #ccc;
  padding: 10px;
}

.btn-submit-review {
  padding: 10px 20px;
  background-color: #49D8B9;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  max-width: 200px; /* Set a max-width to prevent stretching */
  align-self: flex-start; /* Align the button to the left */
  margin-top: 10px;
}

.btn-submit-review:hover {
  background-color: #1033a4;
}
</style>

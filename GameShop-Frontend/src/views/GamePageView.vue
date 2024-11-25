<template>
    <main>
      <h1>Game Details</h1>
      <div class="game-details">
        <img :src="game.photoUrl" alt="Game Image" class="game-image" />
        <div>
          <h2>{{ game.name }}</h2>
          <p><strong>Description:</strong> {{ game.description }}</p>
          <p><strong>Price:</strong> ${{ game.price }}</p>
          <p><strong>Stock Quantity:</strong> {{ game.stockQuantity }}</p>
          <p><strong>Categories:</strong> {{ game.categories.join(", ") }}</p>
        </div>
      </div>
  
      <h2>Reviews</h2>
      <div class="reviews" v-if="reviews.length > 0">
        <div v-for="review in reviews" :key="review.id" class="review">
          <p><strong>{{ review.reviewerName || "Anonymous" }}</strong>: {{ review.comment }}</p>
          <p>Rating: {{ review.rating }}/5</p>
        </div>
      </div>
      <p v-else>No reviews available for this game.</p>
  
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
        <button type="submit">Submit Review</button>
      </form>
  
      <button @click="addToCart">Add to Cart</button>
    </main>
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
          const gameId = this.$route.params.id; // Assuming game ID comes from route params
          const response = await axios.get(`http://localhost:8080/games/id/${gameId}`);
          this.game = response.data;
        } catch (error) {
          console.error("Failed to fetch game details:", error);
        }
      },
      async fetchReviews() {
        try {
          const gameId = this.$route.params.id;
          const response = await axios.get(`http://localhost:8080/review/game/${gameId}`); // Endpoint to fetch reviews by game ID
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
            gameId: gameId, // Assuming your backend associates reviews with game IDs
          };
          await axios.post("http://localhost:8080/review", payload);
          alert("Review added successfully!");
          this.fetchReviews(); // Refresh the reviews after submission
          this.newReview.rating = null;
          this.newReview.comment = "";
        } catch (error) {
          console.error("Failed to submit review:", error);
          alert("Failed to submit review. Please try again.");
        }
      },
      async addToCart() {
        try {
          const customerId = 1; // Replace with the actual customer ID
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
  main {
    display: flex;
    flex-direction: column;
    align-items: center;
    font-family: Arial, sans-serif;
    margin: 20px;
  }
  
  .game-details {
    display: flex;
    gap: 20px;
    align-items: flex-start;
    margin: 20px 0;
  }
  
  .game-image {
    width: 200px;
    height: auto;
    border-radius: 10px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  }
  
  .reviews {
    width: 100%;
    max-width: 600px;
    margin-top: 20px;
  }
  
  .review {
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    margin-bottom: 10px;
  }
  
  button {
    margin-top: 20px;
    padding: 10px 20px;
    background-color: #28a745;
    color: white;
    border: none;
    border-radius: 5px;
    font-size: 16px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #218838;
  }
  
  form {
    margin-top: 20px;
    width: 100%;
    max-width: 600px;
  }
  
  .form-group {
    margin-bottom: 15px;
  }
  
  textarea {
    width: 100%;
    height: 100px;
  }
  </style>
  
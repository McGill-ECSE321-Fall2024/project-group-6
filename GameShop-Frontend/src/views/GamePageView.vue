<template>
  <link
      href="https://cdnjs.cloudflare.com/ajax/libs/boxicons/2.1.4/css/boxicons.min.css"
      rel="stylesheet"
    />
  <div>
    <!-- Header Section -->
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
            <button @click="goToCart"><img src="../assets/pngaaa.com-5034351.png" alt="Cart" class="cart-img" @click="goToCart"></button>
            <button @click="goToCustomerWishlist"><img src="../assets/White-Heart.png" alt="WishList" class="wishlist-img" @click="goToCustomerWishlist"></button>
            <div class="dropdown">
                <button class="dropbtn"><img src="../assets/person-circle.svg" alt="Account" class="account-img"></button>
                <div class="nav-buttons">
                    <button @click="goToCustomerMainPage" class="mainpage-btn">Home</button>
                    <button @click="goToCustomerAccount" >Account</button>
                    <button @click="goToCustomerOrders" class="order-btn">Orders</button>
                    <button @click="logout" class="logout-btn">Logout</button>
                </div>
            </div>
        </div>
      </nav>
    </header>

    <!-- Main Game Details Section -->
    <main class="game-page">
      <div class="game-details">
        <img v-bind:src="game.photoURL" alt="Game Image" class="game-image" />
        <div class="game-info">
          <h1>{{ game.name }}</h1>
          <p><strong>Description:</strong> {{ game.description }}</p>
          <p><strong>Price:</strong> ${{ game.price }}</p>
          <p><strong>Stock Quantity:</strong> {{ game.stockQuantity }}</p>
          <p><strong>Categories:</strong> {{ game.categories.join(", ") }}</p>
          <button @click="addToCart" class="btn-add-to-cart">Add to Cart</button>
          <button @click="addToWishlist" class="btn-add-to-wishlist">Add to Wishlist</button> <!-- Added Wishlist Button -->
        </div>
      </div>

      <!-- Reviews Section -->
      <div class="reviews-section">
        <h2>Reviews</h2>
        <div class="reviews" v-if="reviews.length > 0">
          <div v-for="review in reviews" :key="review.reviewId" class="review">
            <p><strong>{{ review.customer.person.username || "Anonymous" }}</strong>: {{ review.comment }}</p>
            <p>Rating: {{ review.rating }}</p>
            <p>Likes: {{ review.amountOfLikes || 0 }}</p>
            <!-- Like Button -->
            <button 
              v-if="!checkIfLiked(review.reviewId)"
              @click="likeReview(review)"
              class="btn-like">
              Like
            </button>
            
            <!-- Unlike Button -->
            <button 
              v-else
              @click="unlikeReview(review)"
              class="btn-unlike">
              Unlike
            </button>
            <!-- Delete Button -->
            <button 
            v-if="review.customer.roleId == this.customerID"  
            @click="deleteReview(review)"
              class="btn-delete">
              Delete
            </button>
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
import { RouterLink } from "vue-router";

export default {
  props: ['customerId', 'loggedIn', 'gameId'],
  data() {
    return {
      customerID: 0,
      gameID: 0,
      searchQuery: "",
      game: {
        id: 0,
        name: "",
        description: "",
        price: 0.0,
        stockQuantity: 0,
        photoURL: "",
        categories: [],
      },
      reviews: [],
      newReview: {
        rating: null,
        comment: "",
      },
      likedReviews:[],
    };
  },
  methods: {
    isLoggedIn() {
      return this.loggedIn;
    },
    async fetchGameDetails() {
      try {
        const response = await axios.get(`http://localhost:8080/games/id/${this.gameID}`);
        const gameInfo = response.data;
        gameInfo.categories = gameInfo.categories.map(category => category.categoryName);
        this.game = gameInfo;
      } catch (error) {
        console.error("Failed to fetch game details:", error);
        alert("Failed to load game details. Please try again later.");
      }
    },

    async fetchReviews() {
      try {
        const response = await axios.get(`http://localhost:8080/games/${this.gameID}/reviews`);
        this.reviews = response.data.reviews || [];
        
        // Initialize 'likedByCustomer' for each review
        this.reviews.forEach((review) => {
          review.likedByCustomer = this.checkIfLiked(review);
        });
      } catch (error) {
        console.error("Failed to fetch reviews:", error);
        alert("Failed to load reviews. Please try again later.");
      }
    },

    checkIfLiked(reviewId) {
      return this.likedReviews.includes(reviewId);
    },

    parseRating(rating) {
      const ratingMap = {
        "OneStar": 0,
        "TwoStar": 1,
        "ThreeStar": 2,
        "FourStar": 3,
        "FiveStar": 4
      };
      return ratingMap[rating] || 0;
    },

    async likeReview(review) {
      try {
        debugger
        const payload = {
          rating: this.parseRating(review.rating),
          comment: review.comment,
          amountOfLikes: review.amountOfLikes + 1,
          reply: review.reply || "",
        };
        if (review.customer.roleId == this.customerID){
          alert("You cannot like your own review.")
        }
        else{
          const response = await axios.put(`http://localhost:8080/review/${review.reviewId}`, payload);
          this.fetchReviews(); // Refresh the reviews list after updating
          this.likedReviews.push(review.reviewId);
        }

      } catch (error) {
        console.error("Failed to like the review:", error);
        alert("Failed to like/unlike the review. Please try again.");
        this.likedReviews = this.likedReviews.filter(id => id !== review.reviewId);
      }
    },

    async unlikeReview(review) {
      try {   
        const payload = {
          rating: this.parseRating(review.rating),
          comment: review.comment,
          amountOfLikes: review.amountOfLikes - 1,
          reply: review.reply || "",
        };
        const response = await axios.put(`http://localhost:8080/review/${review.reviewId}`, payload);
        this.fetchReviews(); // Refresh the reviews list after updating
        this.likedReviews = this.likedReviews.filter(id => id !== review.reviewId);
      } catch (error) {
        console.error("Failed to unlike the review:", error);
        alert("Failed to like/unlike the review. Please try again.");
        this.likedReviews.push(review.reviewId);
      }
    },
    async deleteReview(review){
      try {   
        if (review.customer.roleId == this.customerID){
          const response = await axios.delete(`http://localhost:8080/review/${review.reviewId}`);
          this.fetchReviews(); // Refresh the reviews list after updating
        }
      } catch (error) {
        console.error("Failed to unlike the review:", error);
        alert("Failed to like/unlike the review. Please try again.");
        this.likedReviews.push(review.reviewId);
      }
    },
    async submitReview() {
      try {
        await axios.post(`http://localhost:8080/review/${this.customerId}/${this.gameId}`, this.newReview);
        alert("Review added successfully!");
        this.fetchReviews();  // Refresh the reviews after adding a new one
        this.newReview.rating = null;
        this.newReview.comment = "";
      } catch (error) {
        console.error("Failed to submit review:", error);
        alert("Failed to submit review. Please try again later.");
      }
    },

    async addToCart() {
      try {
        await axios.put(`http://localhost:8080/customers/${this.customerId}/cart/add/${this.gameId}`, {
          gameID: this.game.id,
        });
        alert("Game added to cart successfully!");
      } catch (error) {
        console.error("Failed to add game to cart:", error);
        alert("Failed to add game to cart. Please try again later.");
      }
    },

    async addToWishlist() {
      try {        
        const response = await axios.put(`http://localhost:8080/customers/${this.customerID}/wishlist/add/${this.gameId}`, null);
        alert("Game added to wishlist successfully!");
      } catch (error) {
        console.error("Failed to add game to wishlist:", error);
        alert("Failed to add game to wishlist. Please try again later.");
      }
    },
    async searchByName() {
      try {
        const response = await axios.get(`http://localhost:8080/games/name/${this.searchQuery}`);
      } catch (error) {
        console.error('Error searching for games:', error);
      }
    },
    async goToCustomerMainPage(){
      this.$router.push({
          name: 'customer-homepage',
          params: {
            customerId: this.customerId,
            loggedIn: true
          }  
        });
    },
    async goToCart() {
      this.$router.push({
          name: 'customer-cart',
          params: {
            customerId: this.customerId,
            loggedIn: true
          }         
        }); 
    },
    async goToCustomerWishlist() {
      this.$router.push({
          name: 'customer-wishlist',
          params: {
            customerId: this.customerId,
            loggedIn: true
          }
          
        });
    },
    async goToCustomerOrders() {
      this.$router.push({
          name: 'customer-orders',
          params: {
            customerId: this.customerId,
            loggedIn: true
          }
        }); 
    },
    async goToCustomerAccount(){
      this.$router.push({
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
  },

  created() {
    if (!this.isLoggedIn()) {
      this.$router.push({ name: "sign in" });
      alert("Please log in before accessing this page.");
    } else {
      this.customerID = this.customerId; 
      this.gameID = this.gameId; 
      this.fetchGameDetails();
      this.fetchReviews();
    }
  }
};
</script>

<style scoped>
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

.cart-img,
.wishlist-img,
.account-img {
  width: 40px; /* Set the width */
  height: auto; /* Let the height adjust automatically to maintain aspect ratio */
  object-fit: contain; /* Ensure the image is contained without distortion */
}

.user-options {
  display: flex;
  align-items: center; /* Ensures the icons and buttons are vertically centered */
  gap: 20px; /* Adds space between the elements */
}

.user-options button {
  background: none;
  border: none;
  padding: 0;
  cursor: pointer;
}

.user-options img {
  width: 30px; /* Adjust to your desired size */
  height: 30px;
}

.dropdown {
  position: relative;
}

.nav-buttons {
  display: none;
  flex-direction: column;
  position: absolute;
  background-color: #fff;
  top: 40px;
  right: 0;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 10px;
}

.dropdown:hover .nav-buttons, .nav-buttons:hover {
  display: flex; /* Show dropdown on hover */
}

.dropdown:hover .nav-buttons {
  display: flex !important; /* Ensure it is displayed when hovering over the parent */
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
  width: 400px;
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

.btn-add-to-wishlist {
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
.btn-add-to-wishlist:hover {
  background-color: #1033a4;
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

/* Like/Unlike Button Styles */
.btn-like, .btn-unlike, .btn-delete {
  padding: 5px 15px;
  font-size: 14px;
  background-color: #49D8B9;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.btn-like:hover, .btn-unlike:hover, .btn-delete:hover {
  background-color: #1033a4;
}

.btn-unlike, .btn-delete {
  background-color: #D84949;
}
</style>

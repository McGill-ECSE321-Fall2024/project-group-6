<template>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/boxicons/2.1.4/css/boxicons.min.css" rel="stylesheet" />

  <!-- Header Section -->
  <header>
    <nav class="navbar">
      <div class="logo">
        <h2>GameShop</h2>
      </div>
      <div class="navmenu">


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

  <!-- Main Game Details Section -->
  <div class="content">
    <a @click="goToMainPage">Keep shopping</a>
    <div v-if="showPopup" class="popup">
      {{ popupMessage }}
    </div>
    <div class="game-info">
      <div class="game-content">
        <div class="game-image-container">
          <img :src="game.photoURL" alt="Game Image" class="game-image" />
        </div>
        <main class="catalog">
          <h1>{{ game.name }}</h1>
          <p><strong>Description:</strong> {{ game.description }}</p>
          <p><strong>Price:</strong> ${{ game.price }}</p>
          <p><strong v-if="game.promotion > 0">Promotion: {{ game.promotion * 100 }}%</strong></p>
          <p><strong>Stock Quantity:</strong> {{ game.stockQuantity }}</p>
          <p><strong>Categories:</strong> {{ game.categories.join(", ") }}</p>
          <button @click="addToCart" class="btn-add-to-cart">Add to Cart</button>
          <button @click="addToWishlist" class="btn-add-to-wishlist">Add to Wishlist</button>
        </main>
      </div>
    </div>


    <!-- Reviews Section -->
    <div class="comments-section">
      <h2>Reviews</h2>
      <div class="comment-card" v-if="reviews.length > 0">
        <div v-for="review in reviews" :key="review.reviewId" class="review">
          <div class="comment-header">
            <p><strong>{{ review.customer.person.username }}</strong> wrote:</p>
            <p class="comment-content">{{ review.comment }}</p>
          </div>
          <div v-if="review.reply && review.reply.trim()" class="manager-reply">
            <p><strong>GameShop's Reply:</strong> {{ review.reply }}</p>
          </div>
          <p>Rating: {{ parseRating(review.rating) + 1 }}/5</p>
          <p>Likes: {{ review.amountOfLikes || 0 }}</p>
          <!-- Like Button -->
          <button v-if="!checkIfLiked(review.reviewId)" @click="likeReview(review)" class="btn-like">
            Like
          </button>

          <!-- Unlike Button -->
          <button v-else @click="unlikeReview(review)" class="btn-unlike">
            Unlike
          </button>
          <!-- Delete Button -->
          <button v-if="review.customer.roleId == this.customerID" @click="deleteReview(review)" class="btn-delete">
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

  </div>
</template>

<script>
import axios from "axios";
import { RouterLink } from "vue-router";
import router from '@/router';

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
      likedReviews: [],
      showPopup: false,
      popupMessage: "",
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

        this.reviews.forEach((review) => {
          review.likedByCustomer = this.checkIfLiked(review); //update the liked status
        });
      } catch (error) {
        console.error("Failed to fetch reviews:", error);

      }
    },

    checkIfLiked(reviewId) {
      return this.likedReviews.includes(reviewId); //allows to show different buttons depending on if review was liked by customer
    },

    parseRating(rating) { //parses from starRating type to integer
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
        if (review.customer.roleId == this.customerID) {
          alert("You cannot like your own review.")
        }
        else {//update the review by increasing number of likes by 1
          const response = await axios.put(`http://localhost:8080/review/${review.reviewId}`, payload);
          this.fetchReviews(); // Refresh the reviews list after updating
          this.likedReviews.push(review.reviewId);
        }

      } catch (error) {
        console.error("Failed to like the review:", error);
        this.popupMessage = `Failed to like the review.`;
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 2000);
        this.likedReviews = this.likedReviews.filter(id => id !== review.reviewId);
      }
    },

    async unlikeReview(review) {
      try { //update the review by decreasing number of likes by 1
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
        this.popupMessage = "Failed to like/unlike the review. Please try again.";
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 2000);
        this.likedReviews.push(review.reviewId);
      }
    },
    async deleteReview(review) {
      try {
        if (review.customer.roleId == this.customerID) {
          const response = await axios.delete(`http://localhost:8080/review/${review.reviewId}`);
          this.fetchReviews(); // Refresh the reviews list after updating
          this.popupMessage = "Your review was deleted";
          this.showPopup = true;
          setTimeout(() => (this.showPopup = false), 2000);
        }
      } catch (error) {
        console.error("Failed to unlike the review:", error);
        this.popupMessage = "Failed to delete review. Please try again.";
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 2000);
        this.likedReviews.push(review.reviewId);
      }
    },
    async submitReview() {
      try {
        debugger
        this.newReview.rating = this.newReview.rating - 1;
        await axios.post(`http://localhost:8080/review/${this.customerId}/${this.gameId}`, this.newReview);
        alert("Review added successfully!");
        this.fetchReviews();  // Refresh the reviews after adding a new one
        this.newReview.rating = null;
        this.newReview.comment = "";
        this.popupMessage = "Your review was submitted.";
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 2000);
      } catch (error) {
        console.error("Failed to submit review:", error);
        this.popupMessage = "Failed to submit review. Please try again.";
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 2000);
      }
    },

    async addToCart() {
      try {
        await axios.put(`http://localhost:8080/customers/${this.customerId}/cart/add/${this.gameId}`, {
          gameID: this.game.id,
        });
        this.popupMessage = `${this.game.name} was added to your cart.`;
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);

      } catch (error) {
        console.error("Failed to add game to cart:", error);
        this.popupMessage = `Failed to add ${this.game.name} to your cart.`;
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);

      }
    },

    async addToWishlist() {
      try {
        const response = await axios.put(`http://localhost:8080/customers/${this.customerID}/wishlist/add/${this.gameId}`, null);
        this.popupMessage = `${this.game.name} was added to your wishlist.`;
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 2000);
      } catch (error) {
        console.error("Failed to add game to wishlist:", error);
        this.popupMessage = `Failed to add ${this.game.name} to your wishlist.`;
        this.showPopup = true;
        setTimeout(() => (this.showPopup = false), 3000);
      }
    },
    async goToCustomerMainPage() { //nav method
      this.$router.push({
        name: 'customer-homepage',
        params: {
          customerId: this.customerId,
          loggedIn: true
        }
      });
    },
    async goToCustomerAccount() { //nav method
      this.$router.push({
        name: 'customer-account',
        params: {
          customerId: this.customerId,
          loggedIn: true
        }

      });
    },

    logout() {
      this.$router.push('/'); //go back to homepage
    },

    async goToCustomerOrders() { //nav method
      this.$router.push({
        name: 'customer-orders',
        params: {
          customerId: this.customerId,
          loggedIn: true
        }

      });
    },
    async goToCustomerCart() { //nav method
      this.$router.push({
        name: 'customer-cart',
        params: {
          customerId: this.customerId,
          loggedIn: true
        }

      });
    },
    async goToCustomerWishlist() { //nav method
      this.$router.push({
        name: 'customer-wishlist',
        params: {
          customerId: this.customerId,
          loggedIn: true
        }

      });
    }
  },

  created() { //constructor
    if (!this.isLoggedIn()) { //ensure user is a customner and is logged in
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

.nav-buttons {

  display: flex;
  align-items: center;
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



/* Main Game Page */
/* Content */
.content {
  min-height: 100vh;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 2rem;
  background-color: #ffffff;
  color: #000;
}

.content a {
  text-align: center;
  font-size: 2rem;
}

.game-info {
  background-color: white;
  grid-template-columns: 200px 70px;
  padding: 1.5rem;
  border-radius: 10px;
  box-shadow: 0 10px 20px rgba(85, 85, 85, 0.2);
}

.game-info h2 {
  font-size: 2rem;
  margin-bottom: 1rem;
}

.game-content {
  display: grid;
  grid-template-columns: 1fr 2fr;
  gap: 1.5rem;
  align-items: start;
}

.game-image-container {
  display: flex;
  justify-content: center;
}

.game-image {
  max-width: 100%;
  height: auto;
  border-radius: 10px;
}

.game-description {
  font-size: 1rem;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.game-description p {
  margin: 10px;
}

.catalog {
  flex: 2;
  padding: 20px;
  display: flex;
  flex-direction: column;
}

.catalog p {
  font-weight: bold;
}

h3 {
  font-size: 1.5rem;
  margin-top: 1rem;
  font-weight: bold;
}

.btn-add-to-cart {
  padding: 10px 20px;
  background-color: #49D8B9;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  max-width: 200px;
  align-self: flex-start;
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
  max-width: 200px;
  align-self: flex-start;
  margin-top: 10px;
}

.btn-add-to-wishlist:hover {
  background-color: #1033a4;
}

.btn-add-to-cart:hover {
  background-color: #1033a4;
}

/* Reviews Section */
.comments-section {
  background-color: white;
  padding: 1.5rem;
  border-radius: 10px;
  box-shadow: 0 10px 20px rgba(85, 85, 85, 0.2);
}

.comments-section h2 {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 1rem;
}

.comment-card {
  border-top: 1px solid #eee;
  padding: 1rem 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.comment-header {
  display: flex;
  flex-direction: column;
}

.comment-content {
  font-size: 1rem;
  margin: 0.5rem 0;
}


.reviews-section {
  margin-top: 40px;
  width: 100%;
  /* Full width */
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

.form-group label {
  font-weight: bold;
  font-size: 1rem;
}

textarea {
  width: 100%;
  height: 100px;
  border-radius: 5px;
  border: 1px solid #ccc;
  padding: 10px;
}
.manager-reply {
    margin-top: 10px;
    padding: 10px;
    background-color: #f9f9f9;
    border-left: 4px solid #4caf50;
}

.manager-reply p {
    margin: 0;
    font-size: 14px;
}
.btn-submit-review {
  padding: 10px 20px;
  font-size: 1rem;
  background-color: #49D8B9;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  max-width: 200px;
  align-self: flex-start;
  margin-top: 10px;
}

.btn-submit-review:hover {
  background-color: #1033a4;
}

/* Like/Unlike Button Styles */
.btn-like,
.btn-unlike,
.btn-delete {
  padding: 5px 15px;
  font-size: 14px;
  background-color: #49D8B9;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.btn-like:hover,
.btn-unlike:hover,
.btn-delete:hover {
  background-color: #1033a4;
}

.btn-unlike,
.btn-delete {
  background-color: #D84949;
}
</style>
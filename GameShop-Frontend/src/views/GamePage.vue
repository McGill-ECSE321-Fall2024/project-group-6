<!-- Author: Maissa -->

<template>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/boxicons/2.1.4/css/boxicons.min.css" rel="stylesheet" />

  <!-- Header Section -->
  <header>
    <nav class="navbar">
      <div class="logo">
        <h2>GameShop</h2>
      </div>
      <div class="navmenu">
        <div class="auth-links ">
          <RouterLink to="/SignIn">Sign In</RouterLink>
          <RouterLink to="/SignUp">Sign Up</RouterLink>
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
                  <p><strong v-if="game.promotion>0">Promotion: {{ game.promotion*100 }}%</strong></p>
                  <p><strong>Stock Quantity:</strong> {{ game.stockQuantity }}</p>
                  <p><strong>Categories:</strong> {{ game.categories.join(", ") }}</p>
              </main>
          </div>
      </div>


      <!-- Reviews Section -->
      <div class="reviews-section">
          <h2>Reviews</h2>
          <div class="reviews" v-if="reviews.length > 0">
              <div v-for="review in reviews" :key="review.reviewId" class="review">
                  <p><strong>{{ review.customer.person.username || "Anonymous" }}</strong>: {{ review.comment }}
                  </p>
                  <p>Rating: {{ parseRating(review.rating)+1 }}/5</p>
                  <p>Likes: {{ review.amountOfLikes || 0 }}</p>
                  <!-- Like Button -->
                
              </div>
          </div>
          <p v-else class="no-reviews">No reviews available for this game!</p>
      </div>
  </div>

</template>

<script>
import axios from "axios";
import { RouterLink } from "vue-router";
import router from '@/router';

export default {
  props: ['gameId'],
  data() {
      return {

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

          likedReviews: [],
      };
  },
  methods: {

      async fetchGameDetails() {
          try {
              const response = await axios.get(`http://localhost:8080/games/id/${this.gameID}`);
              const gameInfo = response.data;
              gameInfo.categories = gameInfo.categories.map(category => category.categoryName);
              this.game = gameInfo;
          } catch (error) {
              console.error("Failed to fetch game details:", error);

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
          this.popupMessage = `${game.name} was added to your cart.`;
          this.showPopup = true;
          setTimeout(() => (this.showPopup = false), 3000);
      },

      async searchByName() {
          try {
              const response = await axios.get(`http://localhost:8080/games/name/${this.searchQuery}`);
          } catch (error) {
              console.error('Error searching for games:', error);
          }
      },
      async goToMainPage() {
          this.$router.push("/");
      },

  },

  created() {
      this.gameID = this.$route.params.gameId;
      this.fetchGameDetails();
      this.fetchReviews();

  }
};
</script>

<style scoped>
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
}

.user-options button {
  background: none;
  border: none;
  padding: 0;
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
  position: absolute;
  background-color: rgba(255, 255, 255, 0.906);
  background: #ffff;

  color: #ffff;
  z-index: 1;
}

.dropdown:hover .nav-buttons {
  display: block;
  border: solid;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.dropdown:hover .nav-buttons button {
  display: flex;
 
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
  
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
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

.auth-links {
  display: flex;
  gap: 1rem;
  padding-right: 1rem;
}

.auth-links a {
  color: white;
  font-size: 1rem;
  font-weight: bold;

}

.auth-links a:hover {
  text-decoration: underline;
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
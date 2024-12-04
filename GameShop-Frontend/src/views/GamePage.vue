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
                    <p><strong v-if="game.promotion>0">Promotion: -{{ game.promotion }}%</strong></p>
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
                    <p>Rating: {{ review.rating }}</p>
                    <p>Likes: {{ review.amountOfLikes || 0 }}</p>
                    <!-- Like Button -->
                    <button v-if="!checkIfLiked(review.reviewId)" @click="likeReview(review)" class="btn-like">
                        Like
                    </button>
                </div>
            </div>
            <p v-else>No reviews available for this game.</p>
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
/* General Styles */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: "poppins";
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

.dropdown:hover .nav-buttons,
.nav-buttons:hover {
    display: flex;
    /* Show dropdown on hover */
}

.dropdown:hover .nav-buttons {
    display: flex !important;
    /* Ensure it is displayed when hovering over the parent */
}

/* Main Game Page */
* {
    margin: 0;
    padding: 0;
    text-decoration: none;
    list-style: none;
    font-family: "poppins";
}

.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #1033a4;
    color: white;
    padding: 1rem;
}

.app-name {
    font-size: 1.5rem;
}

.navmenu {
    display: flex;
    gap: 10px
}

.manager-btn {
    display: flex;
    background-color: #22bae0;
    color: white;
    border: none;
    padding: 0.5rem 1rem;
    border-radius: 20px;
    font-size: 0.9rem;

}

.logout-btn {
    background-color: #ff6f61;
    color: white;
    border: none;
    padding: 0.5rem 1rem;
    border-radius: 20px;
    font-size: 0.9rem;
}

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
    /* Use grid layout */
    grid-template-columns: 1fr 2fr;
    /* Two columns: image (1fr), description (2fr) */
    gap: 1.5rem;
    /* Space between columns */
    align-items: start;
    /* Align content at the top */
}

.game-image-container {
    display: flex;
    justify-content: center;
    /* Center the image horizontally */
}

.game-image {
    max-width: 100%;
    /* Ensure the image fits its container */
    height: auto;
    border-radius: 10px;
}

.game-description {
    font-size: 1rem;
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    /* Spacing between paragraphs */
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

.catalog h3 {
    font-weight: bold;
}


/* Comments Section */
.comments-section {
    background-color: white;
    padding: 1.5rem;
    border-radius: 10px;
    box-shadow: 0 10px 20px rgba(85, 85, 85, 0.2);
}

.comments-section h2 {
    font-size: 1.5rem;
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

.reply-input {
    width: 100%;
    padding: 0.5rem;
    border: 1px solid #ccc;
    border-radius: 10px;
    resize: none;
}

.reply-btn {
    align-self: flex-end;
    background-color: #88b9df;
    /* Same as wishlist button */
    color: white;
    border: none;
    padding: 0.5rem 1rem;
    border-radius: 20px;
    font-size: 0.9rem;
}

.reply-btn:hover {
    background-color: #ffffff;
    color: #88b9df;
}

.button-container {
    display: flex;
    justify-content: space-between;
    gap: 1rem;
    /* Add a gap between the buttons if needed */
}

select {
    width: 100%;
    padding: 0.5rem;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 5px;
    margin-bottom: 1rem;
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
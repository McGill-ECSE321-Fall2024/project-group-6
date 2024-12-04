
<template>
    <div class="game-details-container">
        <header class="header">
          
            <div class="app-name">GameShop</div>
            <div class="button-container">
            <button @click="goToManagerHome" class="manager-btn">HomePage</button>
            <button @click="logout" class="logout-btn">Logout</button>
        </div>
        </header>
        <div class="content">
            <!-- Game Information Section -->
            <div class="game-info">

                <div class="game-content">
                    <div class="game-image-container">
                        <img :src="game.photoURL" alt="Game Image" class="game-image" />
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
                            <input type="number" id="price" v-model="game.price" />
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
                            <label for="promotion">Promotion</label>
                            <input type="number" id="promotion" v-model="game.promotion" />
                        </div>

                        <button type="submit" class="save-btn" @click="saveChanges">Save Changes</button>
                        <h3>Categories</h3>
                        <ul class="categories-list">
                            <li v-for="(category) in game.categories">
                                <span class="category-name">{{ category.categoryName }}</span>
                                <span class="category-id">{{ category.categoryId }}</span>
                            </li>
                        </ul>
                        <div class="form-group">
                            <label for="newCategoryId">Add New Category </label>
                            <input type="number" v-model="categoryId" />
                            <button @click="addCategory(categoryId)" class="add-category">Add Category</button>
                        </div>
                        <div class="form-group">
                            <label for="removeCategoryId">Remove Category </label>
                            <input type="number" v-model="categoryIdRemove" />
                            <button @click="removeCategory(categoryIdRemove)" class="remove-category">Remove Category</button>
                        </div>
                    </main>


                </div>
            </div>


            <div class="comments-section">
                <h2>Customer Reviews</h2>
                <div class="comment-card" v-for="review in reviews" :key="review.reviewId">
                    <div class="comment-header">
                        <p><strong>{{ review.customer }}</strong> said:</p>
                        <p class="comment-content">{{ review.comment }}</p>
                    </div>
                    <div class="replies" v-if="review.reply">
                        <p class="reply">
                            <strong>GameShop:</strong> {{ review.reply }}
                        </p>
                    </div>
                    <textarea v-model="addReply" placeholder="Write a reply..." class="reply-input"></textarea>
                    <button @click="replyToComment(review.reviewId)" class="btn reply-btn">
                        Reply
                    </button>
                </div>
            </div>
        </div>
    </div>

</template>

<script>
import axios from "axios";
import router from '@/router';
export default {
    props: ['managerId', 'loggedIn', 'gameId'],
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

            gameID: 0,
            managerID: 0,

            reviews: [],
            addReply: "",
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
                console.log("The game Id is "+this.gameID);
                const response = await axios.get(`http://localhost:8080/games/id/${this.gameID}`);
                console.log(response);
                this.game = response.data;
            } catch (error) {
                console.error("Error fetching game details:", error);
            }
        },
        async saveChanges() {
            this.categoryIdsArray = [];
            try {
                for (var i = 0; i < this.game.categories.length; i++) {
                    this.categoryIdsArray.push(this.game.categories[i]["categoryId"]);

                }
                this.game.categories = this.categoryIdsArray;
                console.log("array of category ids is " + this.categoryIdsArray);
                await axios.put(`http://localhost:8080/games/id/${this.gameID}`, this.game);
                alert("Changes saved successfully!");
                await this.fetchGameDetails();
            } catch (error) {
                console.error("Error saving game details:", error);
                alert(error);
            }
        },
        async saveAfterCategoryChange() {
            try {

                await axios.put(`http://localhost:8080/games/id/${this.gameID}`, this.game);
                alert("Changes saved successfully!");
                await this.fetchGameDetails();
            } catch (error) {
                console.error("Error saving game details:", error);
                //alert(error.message);
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
                alert("The category does not exist");
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
                    alert("The category already is assigned to this game");

                }
                this.categoryId="";
            }
        },
        async removeCategory(id){
        this.categoryIdsArray=[];
        var counter=0;
        var check=this.game.categories.length;
        for(var i=0;i<this.game.categories.length;i++){
         console.log( this.game.categories[i]["categoryId"]+" the id I am passig is "+id);;
            if(this.game.categories[i]["categoryId"]!=id){
              console.log("I am here");
            this.categoryIdsArray.push(this.game.categories[i]["categoryId"]);
            counter++;
            }
        }
        this.game.categories=this.categoryIdsArray;
      //  console.log(this.categoryIdsArray);
      //  console.log(this.game.categories);
        if(counter!==check){
        await this.saveAfterCategoryChange();
        }else{
          alert("This category does not exist for this game, try another one");
          await this.fetchGameDetails();
        }
        this.categoryIdRemove="";
      },
        goToManagerHome() {
            router.push({
                name: 'manager-homepage',
                params: {
                    managerId: this.managerId,
                    loggedIn: true
                }

            });
        },
        async fetchCategories() {
            try {
                const response = await axios.get('http://localhost:8080/categories');
                this.categories = response.data["categories"];
            } catch (error) {
                console.error('Error fetching categories:', error);
            }
        },
        async fetchReviews() {
            try {
                const response = await axios.get(`http://localhost:8080/review/game/${this.game.gameId}`);
                this.reviews = response.data.reviews;

            } catch (error) {
                console.error("Error fetching reviews:", error);
            }
        },
        async replyToComment(reviewId) {
            const review = this.reviews.find((c) => c.reviewId === reviewId);
            review.reply = this.addReply;

            try {
                await axios.put(`http://localhost:8080/review/${reviewId}`, review);
                this.popupMessage = `Reply posted successfully.`;
                this.fetchReviews();
                this.showPopup = true;

                setTimeout(() => {
                    this.showPopup = false;
                }, 3000);

                this.addReply = ""; // Clear reply box
            } catch (error) {
                console.error("Error posting reply:", error);
                alert("Failed to post reply.");
            }
        },
        logout() {
            this.$router.push("/SignIn"); // Redirect to login
        },
    },
    created(){
    this.managerID = this.managerId; 
    this.gameID = this.gameId; 
    console.log("The game id before accessing is "+this.gameId);
     this.fetchGameDetails();
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
.navmenu{
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

.form-group {
    margin-bottom: 1rem;
}

.form-group label {
    font-size: 1rem;
    font-weight: bold;
    margin-bottom: 0.5rem;
}

.form-group input {
    padding: 0.5rem;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 5px;
    width: 100%;
}

.save-btn {
    margin-top: 1.5rem;
    margin-bottom: 1.5rem;
    padding: 0.75rem;
    background-color: #28a745;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.save-btn:hover {
    background-color: #218838;
}

.add-category {
    margin-top: 1.5rem;
    margin-bottom: 1.5rem;
    padding: 0.75rem;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    background-color: #1033a4;
    width: 100%;
}

.remove-category {
    background-color: red;
    margin-top: 1.5rem;
    margin-bottom: 1.5rem;
    padding: 0.75rem;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    width: 100%;
}

.add-category:hover {
    background-color: #887821;
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
    gap: 1rem; /* Add a gap between the buttons if needed */
}
</style>

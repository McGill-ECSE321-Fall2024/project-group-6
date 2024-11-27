<template>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/boxicons/2.1.4/css/boxicons.min.css" rel="stylesheet">
    <header>
        <nav class="navbar">
            <div class="logo">
                <h2>GameShop</h2>
            </div>
            <div class="navmenu">
                <div class="search-box">
                    <input type="search" class="search" placeholder="Search game...">
                    <i class='bx bx-search'></i>
                </div>
                <div class="iconAccount">
                    <img src="./account.png">
                </div>
                <RouterLink to="/wishlist"><img src="./White-Heart.png"></RouterLink>

                <RouterLink to="/checkout"><img src="./pngaaa.com-5034351.png"></RouterLink>
            </div>
        </nav>
    </header>
    <div class="wishlist">
        <div class="main-header">
            <div class="header">
                <h2>Your GameShop Wishlist</h2>
                <h5>Your favorite games all in one place! Grab them now before they're gone!</h5>
                <a @click="addWishlistToCart()" class="btn">Add wishlist to cart</a>
            </div>
        </div>
        <div class="container">
            <div class="checkoutLayout">
                <div class="returnCart">
                    <div v-if="showPopup" class="popup">
                        {{ popupMessage }}
                    </div>
                    <a href="/homepage">Keep shopping</a>
                    <div class="list">
                        <div v-for="game in wishlist" :key="game.id" class="game-card">
                            <div class="item">
                                <img :src="game.imageUrl">
                                <div class="info">
                                    <div class="name">{{ game.name }}</div>
                                    <p><strong>Description:</strong> {{ game.description }}</p>
                                    <p><strong>Stock:</strong> {{ game.stockQuantity }} left</p>
                                </div>
                                <div class="returnPrice">{{ game.price }}$</div>
                                <div class="buttons">
                                    <button @click="addToCart(game)" class="btn">Add to cart</button>
                                    <button @click="removeFromWishlist(game)" class="btn">Remove</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>

<script>
import axios from "axios";
import { RouterLink } from 'vue-router';

export default {
    data() {
        return {
            wishlist: [
                {
                    id: 1,
                    name: 'Cyberpunk 2077',
                    imageUrl: "https://upload.wikimedia.org/wikipedia/en/9/9f/Cyberpunk_2077_box_art.jpg",
                    price: 59.99,
                    description: 'An open-world, action-adventure story set in Night City.',
                    stockQuantity: 20
                },
                {
                    id: 2,
                    name: 'Rainbow 6 Siege',
                    imageUrl: 'https://upload.wikimedia.org/wikipedia/en/4/47/Tom_Clancy%27s_Rainbow_Six_Siege_cover_art.jpg',
                    price: 39.99,
                    description: 'A tactical, team-based FPS where players engage in strategic battles with unique operators.',
                    stockQuantity: 15
                },
                {
                    id: 3,
                    name: 'Red Dead Redemption 2',
                    imageUrl: 'https://upload.wikimedia.org/wikipedia/en/4/44/Red_Dead_Redemption_II.jpg',
                    price: 49.99,
                    description: 'An epic tale of life in America’s unforgiving heartland.',
                    stockQuantity: 10
                }
            ],
            cart: [],
            showPopup: false,
            popupMessage: "",
        };
    },
    methods: {

        async fetchGames() {
            try {
                const response = await axios.get('http://localhost:8080/customers');
                this.games = response.data;
            } catch (error) {
                console.error('Error fetching games:', error);
            }
        },
        addToCart(game) {
            this.cart.push(game);
            this.wishlist = this.wishlist.filter(item => item.id !== game.id);
            this.popupMessage = `${game.name} was added to your cart.`;
            this.showPopup = true;

            setTimeout(() => {
                this.showPopup = false;
            }, 3000);
        },
        removeFromWishlist(game) {
            this.wishlist = this.wishlist.filter(item => item.id !== game.id);
            this.popupMessage = `${game.name} was removed from the wishlist.`;
            this.showPopup = true;

            setTimeout(() => {
                this.showPopup = false;
            }, 3000);
        },
        async addWishlistToCart() {
            this.cart.push(this.wishlist);
            this.wishlist = [];
            this.popupMessage = `Wishlist was added to your cart.`;
            this.showPopup = true;

            setTimeout(() => {
                this.showPopup = false;
            }, 3000);
        }
    }
}
</script>

<style>
* {
    margin: 0;
    padding: 0;
    text-decoration: none;
    list-style: none;
    font-family: "poppins";
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

header img {
    margin-top: 15px;
    margin-right: 10px;
    align-items: center;
    width: 40px;
}

.navmenu .iconcCart {
    align-items: center;
    position: relative;
    margin: 10px;
    z-position: 1;
    display: inline-block;
}

.main-header {
    width: 90%;
    margin: auto;
    height: 40px;
    display: flex;
    padding-bottom: 30px;
}

.main-header .header {
    margin-left: 30px;
    margin-bottom: 30px;
}

.main-header h5 {
    font-size: 20px;
    font-weight: 550px;
    margin-top: 10px;
    margin-bottom: 15px;
}

.main-header h2 {
    font-weight: bold;
    font-size: 38px;
    width: 500px;
    margin-top: 10px;
    margin-bottom: 15px;
}

.main-header .a {
    margin-bottom: 30px;
}

.main-header .btn {
    background: #88b9df;
    border: 1px solid #88b9df;
    font-size: 15px;
    color: #ffffff;
    font-weight: 400;
    padding: 4px 20px;
    border-radius: 50px;
}

.main-header .btn:hover {
    color: #88b9df;
    background: #ffffff;
}


.wishlist .container {
    padding: 200px;
    align-items: center;

}

.wishlist,
.main-header {
    background-color: #ffffff;
    color: #000000;
    margin: 0;
    padding: 0;
}

html {
    font-family: "poppins";
}


.wishlist .returnCart .list {
    border-top: 1px solid #eee;
    padding: 20px 0;
    ;
}

.returnCart .list .item img {
    height: 100%;
}

.wishlist .returnCart .list .item {
    display: grid;
    grid-template-columns: 150px 400px 100px 150px;
    align-items: center;
    gap: 10px;
    margin-top: 20px;
    margin-bottom: 30px;
    padding: 10px 0;
    box-shadow: 0 10px 20px #5555;
    border-radius: 20px;
}

.wishlist .returnCart .list .item .btn {
    width: 80%;
    margin-bottom: 5px;
    display: flex;
    flex-direction: column;
    gap: 10px;
    align-items: center;
    background: #88b9df;
    border: 1px solid #88b9df;
    font-size: 15px;
    color: #ffffff;
    font-weight: 400;
    border-radius: 50px;
    padding: 4px 2px;
}

.wishlist .returnCart .list .item .btn:hover {
    color: #88b9df;
    background: #ffffff;
}

.wishlist .returnCart .list .item {
    display: grid;
    grid-template-columns: 80px 400px 70px 120px;
    align-items: center;
    gap: 20px;
    margin-bottom: 30px;
    padding: 10px;
    box-shadow: 0 10px 20px #5555;
    border-radius: 20px;
}

.wishlist .returnCart .list .item .name,
.wishlist .returnCart .list .item .returnPrice {
    font-size: large;
    font-weight: bold;

}
</style>
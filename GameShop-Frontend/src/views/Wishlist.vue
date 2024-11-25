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
                <a href="" class="btn">Add wishlist to cart</a>
            </div>
        </div>
        <div class="container">
            <div class="checkoutLayout">
                <div class="returnCart">
                    <a href="/homepage">Keep shopping</a>
                    <div class="list">
                        <div v-for="game in wishlist" :key="game.id" class="game-card">
                            <div class="item">
                                <img :src="game.imageUrl">
                                <div class="info">
                                    <div class="name">{{ game.name }}</div>
                                    <p><strong>Description:</strong> {{ game.description }}</p>
                                    <p><strong>Stock:</strong> {{ game.stockQuantity }} left</p>
                                    <div class="price">{{ game.price }}</div>
                                </div>
                                <div class="returnPrice">{{ game.price }}</div>
                                <div class="buttons">
                                    <a href="" class="btn">Add to cart</a>
                                    <a href="" class="btn">Remove</a>
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
                    imageUrl: 'https://example.com/images/cyberpunk2077.jpg',
                    price: 59.99,
                    description: 'An open-world, action-adventure story set in Night City.',
                    stockQuantity: 20
                },
                {
                    id: 2,
                    name: 'The Witcher 3: Wild Hunt',
                    imageUrl: 'https://example.com/images/witcher3.jpg',
                    price: 39.99,
                    description: 'A story-driven open world RPG set in a visually stunning fantasy universe.',
                    stockQuantity: 15
                },
                {
                    id: 3,
                    name: 'Red Dead Redemption 2',
                    imageUrl: 'https://example.com/images/reddead2.jpg',
                    price: 49.99,
                    description: 'An epic tale of life in America’s unforgiving heartland.',
                    stockQuantity: 10
                }
            ]
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
    }
}
</script>

<style>
* {
    margin: 0;
    padding: 0;
    text-decoration: none;
    list-style: none;
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

.main-header .header{
    margin-bottom: 30px;
}
.main-header h5 {
    font-size: 20px;
    font-weight: 550px;
    margin-top: 10px;
    margin-bottom: 15px;
}

.main-header h2 {
    font-size: 38px;
    width: 500px;
    margin-top: 10px;
    margin-bottom: 15px;
}

.main-header .a{
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
.wishlist .container{
   padding: 150px;

}
.wishlist, .main-header {
    margin: 0;
    padding: 0;
}

html {
    font-family: "poppins";
    ;
}

.checkoutLayout {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 50px;
    padding: 20px;
}

.checkoutLayout .right {
    background-color: #5358B3;
    border-radius: 20px;
    padding: 40px;
    color: #fff;
}

.checkoutLayout .right .form {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 20px;
    border-bottom: 1px solid #7a7fe2;
    padding-bottom: 20px;
}

.checkoutLayout .form h1,
.checkoutLayout .form .group:nth-child(-n+3) {
    grid-column-start: 1;
    grid-column-end: 3;
}

.checkoutLayout .form input,
.checkoutLayout .form select {
    width: 100%;
    padding: 10px 20px;
    box-sizing: border-box;
    border-radius: 20px;
    margin-top: 10px;
    border: none;
    background-color: #6a6fc9;
    color: #fff;
}

.checkoutLayout .right .return .row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 10px;
}

.checkoutLayout .right .return .row div:nth-child(2) {
    font-weight: bold;
    font-size: x-large;
}

.buttonCheckout {
    width: 100%;
    height: 40px;
    border: none;
    border-radius: 20px;
    background-color: #49D8B9;
    margin-top: 20px;
    font-weight: bold;
    color: #fff;
}

.returnCart h1 {
    border-top: 1px solid #eee;
    padding: 20px 0;
}

.returnCart .list .item img {
    height: 80px;
}

.wishlist .returnCart .list .item {
    display: grid;
    grid-template-columns: 90px 400px 80px 150px;
    align-items: center;
    gap: 20px;
    margin-top: 20px;
    margin-bottom: 30px;
    padding: 10px 0;
    box-shadow: 0 10px 20px #5555;
    border-radius: 20px;
}

.returnCart .list .item .buttons a {
    padding: 0 6px;
    width: 100%;
    height: 40px;
    border: none;
    border-radius: 20px;
    background-color: red;
    margin-top: 20px;
    font-weight: bold;
    color: #ffffff;

}

.returnCart .list .item {
    display: grid;
    grid-template-columns: 80px 1fr 70px 30px;
    align-items: center;
    gap: 20px;
    margin-bottom: 30px;
    padding: 10px;
    box-shadow: 0 10px 20px #5555;
    border-radius: 20px;
}

.returnCart .list .item .name,
.returnCart .list .item .returnPrice {
    font-size: large;
    font-weight: bold;

}
</style>
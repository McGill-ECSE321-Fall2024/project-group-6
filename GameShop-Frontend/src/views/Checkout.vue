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
    <div class="container">
        <div class="checkoutLayout">
            <div class="returnCart">
                <a href="/homepage">Keep shopping</a>
                <h1>List Product in Cart</h1>
                <div class="list">
                    <div v-if="showPopup" class="popup">
                        {{ popupMessage }}
                    </div>
                    <div v-for="game in cart" :key="game.id" class="game-card">
                        <div class="item">
                            <img :src="game.photoURL">
                            <div class="info">
                                <div class="name">{{ game.name }}</div>
                                <p><strong>Stock:</strong> {{ game.stockQuantity }} left</p>
                                <div class="price">{{ game.price }}</div>
                            </div>
                            <div class="returnPrice">{{ game.price }}$</div>
                            <div class="buttons">
                                <a @click="removeFromCart(game)" class="btn">-</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="payement">
                    <h1>Payement Method</h1>
                    <div class="list">
                        <div v-for="payement in payements" :key="payement.id" class="payement-card">
                            <div class="item">
                                <input type="radio" id="payment-{{ payement.id }}" name="payment" :value="payement.id"
                                    v-model="selectedPayment" @click="handleClick(payement.id)" />

                                <img src="https://pngimg.com/d/credit_card_PNG24.png">
                                <div class="info">
                                    <div class="name">{{ payement.name }}</div>
                                    <div class="number">Credit Card ending in {{ payement.number }}</div>
                                </div>
                            </div>
                        </div>
                        <div class="add-payement">
                            <h2 @click="isAddPaymentVisible = !isAddPaymentVisible" style="cursor: pointer;">
                                <span v-if="isAddPaymentVisible">▲</span>
                                <span v-else>▼</span>
                                Add Payment
                            </h2>
                            <div class="form" v-show="isAddPaymentVisible">
                                <div class="group">
                                    <input type="text" name="number" id="number" placeholder="Enter Card">
                                </div>

                                <div class="group">
                                    <input type="text" name="exp" id="exp" placeholder="MMYY">
                                    <input type="text" name="cvv" id="cvv" placeholder="CVV">
                                </div>
                                <div class="group">
                                    <input type="text" name="address" id="address" placeholder="Billing address">
                                </div>
                                <button @click=addPayement() class="savePayement">Save</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="right">
                <h1>Checkout</h1>

                <div class="form">
                    <div class="group">
                        <label for="name">Full Name</label>
                        <input type="text" name="name" id="name">
                    </div>

                    <div class="group">
                        <label for="phone">Phone number</label>
                        <input type="text" name="number" id="number">
                    </div>

                    <div class="group">
                        <label for="address">Address</label>
                        <input type="text" name="address" id="address">
                    </div>

                    <div class="group">
                        <label for="country">Country</label>
                        <select name="country" id="country">
                            <option value="">Choose..</option>
                            <option value="">Canada</option>
                        </select>
                    </div>

                    <div class="group">
                        <label for="city">Province</label>
                        <select name="city" id="city">
                            <option value="">Choose..</option>
                            <option value="">Quebec</option>
                        </select>
                    </div>
                </div>

                <div class="return">
                    <div class="row">
                        <div>Total Quantity</div>
                        <div class="totalQuantity">{{ totalQuantity }}</div>
                    </div>
                    <div class="row">
                        <div>Total Price</div>
                        <div class="totalPrice">{{ totalPrice }}</div>
                    </div>
                </div>
                <button @click=checkout() class="buttonCheckout">CHECKOUT</button>
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
            /*
            cart: [
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
            */
            isAddPaymentVisible: false,
            payements: [
                {
                    id: 1,
                    name: "Maissa Mehdi",
                    number: "••••7654",
                    date: "08/25",
                    address: "3454 Milton street",
                    cvc: 343,

                }
            ],
            cart: [],
            customer: null,
            selectedPayment: null,
            showPopup: false,
            popupMessage: "",
            command: {
                customer: this.customer
    
            }
        };
    },
    computed: {
        totalQuantity() {
            return this.cart.length;
        },
        totalPrice() {
            return this.cart.reduce((total, game) => total + game.price, 0);
        }
    },
    methods: {
        async fetchCart() {
            try {
                const response = await axios.get(`http://localhost:8080/customers/1402`);
                this.customer= response.data;
                this.cart = this.customer.cart; 
               
                
            } catch (error) {
                console.error("Error fetching cart:", error);
            }
        },

        handleClick(id) {
            if (this.selectedPayment === id) {
                this.selectedPayment = null;
            } else {
                this.selectedPayment = id;
            }
        },
        removeFromCart(game) {
            axios.put(`http://localhost:8080/customers/${this.customer.id}/cart/game`, { game } )
            this.cart = this.cart.filter(item => item.id !== game.id);
            this.popupMessage = `${game.name} was removed from the wishlist.`;
            this.showPopup = true;

            setTimeout(() => {
                this.showPopup = false;
            }, 3000);
        
        },
        checkout() {
            axios.post(`http://localhost:8080/commands`, { command } )
        }
    },
    mounted() {
    this.fetchCart();
  },
};
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


html {
    font-family: "poppins";
}

.container {
    background-color: #ffffff;
    color: #000000;
}

h2 {
    padding-top: 0.5em;
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

.checkoutLayout {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 50px;
    padding: 20px;
}

.checkoutLayout .right {
    margin-top: 80px;
    background-color: #3148de;
    border-radius: 20px;
    padding: 40px;
    color: #fff;
}

.checkoutLayout .right .form {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 20px;
    border-bottom: 1px solid #3e78ff;
    padding-bottom: 20px;
}

.checkoutLayout h1 {
    font-weight: bold;
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
    background-color: #3e78ff;
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
    margin-top: 40px;
    font-weight: bold;
    color: #fff;
}

.returnCart h1 {
    border-top: 1px solid #eee;
    padding: 20px 0;
}

.returnCart .list {
    border-bottom: 2px solid #eee;
    padding: 20px 0;
}

.returnCart .list .item img {
    height: 80px;
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
    margin-bottom: 20px;
    padding: 10px;
    box-shadow: 0 10px 20px #5555;
    border-radius: 20px;
}

.returnCart .list .item .name,
.returnCart .list .item .returnPrice {
    font-size: large;
    font-weight: bold;

}

.payement .list .item {
    display: grid;
    grid-template-columns: 40px 1fr 300px;
    align-items: center;
    gap: 20px;
    margin-bottom: 20px;
    padding: 10px;
    box-shadow: 0 10px 20px #5555;
    border-radius: 20px;
}

.add-payement {
    display: grid;
    align-items: center;
    gap: 20px;
    margin-bottom: 20px;
    padding: 10px;
    box-shadow: 0 10px 20px #5555;
    border-radius: 20px;
}

.add-payement .form {
    display: grid;
    grid-template-columns: 1fr;
    gap: 10px;
    margin-bottom: 20px;

}

.add-payement .form .group {
    display: grid;
    grid-template-columns: 1fr 1fr;
    /* Two equal columns */
    gap: 10px;

}

.add-payement .form .group:first-child {
    grid-template-columns: 1fr;
    /* Single column for Enter Card */
}

.add-payement .form input {
    padding: 10px;
    border: 1px solid #ddd;
    color: #000000;
    border-radius: 5px;
    width: 100%;
    box-sizing: border-box;
    background-color: #eee;
}

.add-payement button {
    padding: 10px;
    background-color: #3148de;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-weight: bold;
    width: 100%;
}

.payement .list .item .name {
    width: 100%;
    font-size: large;
    font-weight: bold;
}
</style>
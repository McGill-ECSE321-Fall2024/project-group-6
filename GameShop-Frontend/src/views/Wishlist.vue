<template>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/boxicons/2.1.4/css/boxicons.min.css" rel="stylesheet">
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

          <RouterLink><img src="../assets/White-Heart.png" @click="goToWishlist">
          </RouterLink>

          <RouterLink><img src="../assets/pngaaa.com-5034351.png" @click="goToCart">
          </RouterLink>
        </div>
      </div>
    </nav>
  </header>
    <div class="wishlist">
        <div class="main-header">
            <div class="header">
                <h2>Your GameShop Wishlist</h2>
                <h5>Your favorite games all in one place! Grab them now before they're gone!</h5>
                
            </div>
        </div>
        <div class="container">
            <div class="checkoutLayout">
                <div class="returnCart">
                    <div v-if="showPopup" class="popup">
                        {{ popupMessage }}
                    </div>
                    <a @click="goToCustomerMainPage">Keep shopping</a>
                    <div class="list">
                        <div v-for="game in customer.wishlist" :key="game.id" class="game-card">
                            <div class="item">
                                <img :src="game.photoURL">
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
import router from '@/router';
export default {
    props: ['customerId', 'loggedIn'],
    data() {
        return {
            customer: {
                shippingAddress: "",
                username: "",
                email: "",
                phone: "",
                customerId: null,
                wishlist: [],
                cart: []

            },
            showPopup: false,
            popupMessage: "",
            customerID: 0
        };
    },
    methods: {
        async fetchWishlist() {
            try {
                const response = await axios.get(`http://localhost:8080/customers/${this.customerID}`);
                this.customer = response.data;

            } catch (error) {
                console.error("Error fetching wishlist:", error);
            }
        },

        async addToCart(game) {
            try {
                const response = await axios.put(`http://localhost:8080/customers/${this.customer.customerId}/cart/add/${game.gameId}`);

                // Update local state with the updated cart
                this.customer = response.data; // Assuming the response contains the updated customer object
                await axios.put(`http://localhost:8080/customers/${this.customer.customerId}/wishlist/${game.gameId}`);

                // Update frontend state
                this.fetchWishlist();

                // Display success message
                this.popupMessage = `${game.name} was added to your cart.`;
                this.showPopup = true;
                setTimeout(() => (this.showPopup = false), 3000);

            } catch (error) {
                console.error("Error adding game to cart:", error);
            }
        },
        async removeFromWishlist(game) {
            try {
                // Remove game from the wishlist in backend
                await axios.put(`http://localhost:8080/customers/${this.customer.customerId}/wishlist/${game.gameId}`);

                // Update frontend state
                this.fetchWishlist();

                // Show confirmation message
                this.popupMessage = `${game.name} was removed from the wishlist.`;
                this.showPopup = true;

                // Hide popup after 3 seconds
                setTimeout(() => {
                    this.showPopup = false;
                }, 3000);
            } catch (error) {
                console.error("Error removing game from wishlist:", error);
            }
        },
        async goToCustomerMainPage() {
            router.push({
                name: 'customer-homepage',
                params: {
                    customerId: this.customerID,
                    loggedIn: true
                }

            });
        },
        async goToCart() {
            router.push({
                name: 'customer-cart',
                params: {
                    customerId: this.customerId,
                    loggedIn: true
                }

            });
        },
        async goToWishlist() {
        router.push({
          name: 'customer-wishlist',
          params: {
            customerId: this.customerId,
            loggedIn: true
          }
          
        }); 
    },
    async goToCustomerAccount() {
            router.push({
                name: 'customer-account',
                params: {
                    customerId: this.customerId,
                    loggedIn: true
                }

            });
        },

        logout() {
            this.$router.push('/');
        },

        async goToCustomerOrders() {
            router.push({
                name: 'customer-orders',
                params: {
                    customerId: this.customerId,
                    loggedIn: true
                }

            });
        }
    },
    created() {

        this.customerID = this.customerId;
        this.fetchWishlist();
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
    font-weight: bold;
    padding-bottom: 10px;
    padding-left: 5px;
}

.main-header h2 {
    font-weight: bold;
    font-size: 38px;
    width: 500px;
    margin-top: 10px;

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

.wishlist .container {
    padding: 150px 200px;
    align-items: center;
    min-height: 100vh;
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
}

.returnCart .list .item img {
    height: 90px;
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
<template>
    
  <link href="https://cdnjs.cloudflare.com/ajax/libs/boxicons/2.1.4/css/boxicons.min.css" rel="stylesheet" />
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

  <div class="command">
    <div class="main-header">
      <div class="header">
        <h2>Your Order has been placed!</h2>
        <h5>
          You will receive an email confirmation once your order has been
          shipped.
        </h5>
      </div>
    </div>
    <div class="receipt" v-if="command">
      <div class="id">Order number: {{ command.commandId }}</div>
      <div class="list">
        <div class="order">
          <h5>Items:</h5>
          <div v-for="game in cart" :key="game.id" class="game-row">
            <p class="game-name">{{ game.name }}</p>
            <p class="game-price">{{ game.price }}$</p>
          </div>

          <div class="returnPrice"><strong>Total: </strong> {{ command.totalPrice }}$</div>
          <h5>Order details:</h5>
          <div class="shippement">
            <strong>Shipping Address:</strong> {{ command.customer.shippingAddress }}
          </div>
          <div class="payement">
            <strong>Payment ending in: {{ selectedPayment.paymentNumber }} </strong> 
          </div>
        </div>
        <a @click="backHome">Return to main page</a>
      </div>
    </div>
    <div v-else>
      <h3>Loading order details...</h3>
    </div>

  </div>

</template>


<script>
import { RouterLink } from "vue-router";
import axios from 'axios';
import router from '@/router';
export default {
props: ['paymentId', 'commandId','customerId','loggedIn'],
data() {

  return {
   
    command: null,
    selectedPayment: null,
    cart: []
  };
},
methods: {
  async fetchCommand() {
    try {
      const commandId = this.$route.params.commandId

      const response = await axios.get(`http://localhost:8080/command/${commandId}`);
      this.command = response.data;



    } catch (error) {
      console.error("Error fetching order:", error);
    }
  },
  async backHome(){
    router.push({
      name: 'customer-homepage',
      params: {
        customerId: this.customerId,
        loggedIn: true
      }
      
    });
  },

  async fetchPayment() {
    try {
      const paymentId = this.$route.params.paymentId;

      const response = await axios.get(`http://localhost:8080/payment/${paymentId}`);
      this.selectedPayment = response.data;



    } catch (error) {
      console.error("Error fetching payment:", error);
    }
  },
  async goToWishlist() {
        router.push({
            name: 'customer-wishlist',
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
computed: {
  totalPrice() {
    return this.cart.reduce((total, game) => total + game.price, 0);
  },
  
},
mounted() {
  
  const cart = JSON.parse(sessionStorage.getItem('cart'));
  this.cart = cart ? cart : [];
  this.fetchCommand().then(() => {
    this.fetchPayment();
  }
  );
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

.dropdown:hover {
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


.command .receipt {
padding: 150px 200px;
align-items: center;
}

.command,
.main-header {
background-color: #ffffff;
color: #000000;
margin: 0;
padding: 0;
}

html {
font-family: "poppins";
}

.command {
min-height: 100vh;
}

.command a {
text-align: center;
color: #d45119;
}

.command .receipt .list {
border-top: 1px solid #eee;
padding: 20px 0;
}


.receipt .id {
font-size: 20px;
font-weight: bold;
}

.receipt .order h5 {
font-size: 15px;
font-weight: bold;
margin-bottom: 10px;
border-bottom: 1px solid #eee;
}


.command .receipt .list .order {
margin-bottom: 30px;
padding: 10px;
box-shadow: 0 10px 20px #5555;
border-radius: 20px;
}

.command .receipt .list .game-row {
display: grid;
grid-template-columns: 1fr auto;
gap: 20px;
margin-bottom: 15px;
align-items: center;
}

.game-name {
font-size: 16px;
font-weight: 500;
}

.game-price {
font-size: 16px;
font-weight: bold;
text-align: right;
}

.list a {
  font-size: 2rem;
  font-weight: bold;
}

.command .receipt .list .returnPrice {

text-align: right;
}

.returnPrice,
.shippement,
.payement {
margin-top: 20px;
font-size: 16px;
font-weight: bold;
margin-bottom: 10px;
}
</style>
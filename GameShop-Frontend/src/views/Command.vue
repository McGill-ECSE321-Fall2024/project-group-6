<template>
  <div>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/boxicons/2.1.4/css/boxicons.min.css" rel="stylesheet" />
    <header>
      <nav class="navbar">
        <div class="logo">
          <h2>GameShop</h2>
        </div>
        <div class="navmenu">
          <div class="search-box">
            <input type="search" class="search" placeholder="Search game..." />
            <i class="bx bx-search"></i>
          </div>
          <div class="iconAccount">
            <img src="./account.png" alt="Account Icon" />
          </div>
          <RouterLink to="/wishlist">
            <img src="./White-Heart.png" alt="Wishlist Icon" />
          </RouterLink>
          <RouterLink to="/checkout">
            <img src="./pngaaa.com-5034351.png" alt="Checkout Icon" />
          </RouterLink>
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

            <div class="returnPrice"><strong>Total: </strong> {{ totalPrice }}$</div>
            <h5>Order details:</h5>
            <div class="shippement">
              <strong>Shipping Address:</strong> {{ command.customer.shippingAddress }}
            </div>
            <div class="payement">
              <strong>Payment ending in: {{ selectedPayment.paymentNumber }} </strong> 
            </div>
          </div>
          <a href="/homepage">Return to main page</a>
        </div>
      </div>
      <div v-else>
        <h3>Loading order details...</h3>
      </div>

    </div>
  </div>
</template>


<script>
import { RouterLink } from "vue-router";
import axios from 'axios';

export default {
  data() {
    return {
      name: "",
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

    async fetchPayment() {
      try {
        const paymentId = this.$route.params.paymentId;

        const response = await axios.get(`http://localhost:8080/payment/${paymentId}`);
        this.selectedPayment = response.data;



      } catch (error) {
        console.error("Error fetching payment:", error);
      }
    }
  },
  computed: {
    totalPrice() {
      return this.cart.reduce((total, game) => total + game.price, 0);
    },
  },
  mounted() {
    this.name = sessionStorage.getItem('name');
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
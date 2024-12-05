<!-- Author: Maissa -->

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
    <div class="container">
        <a @click="goToCustomerMainPage">Keep shopping</a>
        <div class="checkoutLayout">
            <div class="returnCart">
                <h1>Product List in Cart</h1>
                <div class="list">
                    <div v-if="showPopup" class="popup">
                        {{ popupMessage }}
                    </div>
                    <div v-for="game in customer.cart" :key="game.id" class="game-card">
                        <div class="item">
                            <img :src="game.photoURL">
                            <div class="info">
                                <div class="name">{{ game.name }}</div>
                                <p><strong>Stock:</strong> {{ game.stockQuantity }} left</p>
                                <p><strong>Price:</strong> {{ game.price }}$ </p>
                            </div>
                            <div class="returnPrice">{{ (game.price*(1-game.promotion)).toFixed(2) }}$</div>
                            <div class="buttons">
                                <a @click="removeFromCart(game)" class="btn">-</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="payment">
                    <h1>Payment Method</h1>
                    <div class="list">
                        <div v-for="payment in payments" :key="payment.paymentId" class="payment-card">
                            <div class="item">
                                <input type="radio" id="payment-{{ payment.paymentId }}" name="payment"
                                    :value="payment.paymentId" v-model="selectedPayment"
                                    @click="handleClick(payment.paymentId)" />

                                <img src="https://pngimg.com/d/credit_card_PNG24.png">
                                <div class="info">
                                    <div class="name">Credit Card ending in {{ payment.paymentNumber }}</div>
                                    <div class="number">Billing Address {{ payment.billingAddress }}</div>
                                </div>
                            </div>
                        </div>
                        <div class="add-payment">
                            <h2 @click="isAddPaymentVisible = !isAddPaymentVisible" style="cursor: pointer;">
                                <span v-if="isAddPaymentVisible">▲</span>
                                <span v-else>▼</span>
                                Add Payment
                            </h2>
                            <div class="form" v-show="isAddPaymentVisible">
                                <div class="group">
                                    <input type="number" v-model="newPayment.creditCardNumber" placeholder="Enter Card">
                                </div>

                                <div class="group">
                                    <input type="text" v-model="newPayment.expirationDate" placeholder="MM/YY">
                                    <input type="number" v-model="newPayment.cvc" placeholder="CVC">
                                </div>
                                <div class="group">
                                    <input type="text" v-model="newPayment.billingAddress"
                                        placeholder="Billing address">
                                </div>
                                <button @click=addPayment() class="savePayment">Save</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="right">
                <h1>Checkout</h1>

                <div class="form">
                    <div class="group">
                        <label for="name">E-mail address</label>
                        <input type="text" v-model="customer.email" name="name" id="name">
                    </div>

                    <div class="group">
                        <label for="phone">Phone number</label>
                        <input type="text" v-model="customer.phone" name="number" id="number">
                    </div>

                    <div class="group">
                        <label for="address">Address</label>
                        <input type="text" v-model="customer.shippingAddress" name="address" id="address">
                    </div>

                    <div class="group">
                        <label for="country">Country</label>
                        <select name="country" id="country" v-model="country">
                            <option value="" >Choose..</option>
                            <option value="Canada">Canada</option>
                        </select>
                    </div>

                    <div class="group">
                        <label for="city">Province</label>
                        <select name="city" id="city" v-model="city">
                            <option value="">Choose..</option>
                            <option value="Quebec">Quebec</option>
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
import router from '@/router';
export default {
    props: ['customerId', 'loggedIn'],
    data() {

        return {

            payments: [],
            //Updating customer's info


            //Adding a payment
            isAddPaymentVisible: false,
            newPayment: {
                creditCardNumber: null,
                expirationDate: "",
                billingAddress: "",
                cvc: null,
            },

            command: {},
            customer: {
                shippingAddress: "",
                username: "",
                email: "",
                phone: "",
                password: "",
                wishlist: [],
                cart: []

            },
            country:"",
            city:"",
            selectedPayment: null,
            showPopup: false,
            popupMessage: "",
            customerID: 0,
        };
    },
    computed: {
        totalQuantity() {
            return this.customer.cart.length;
        },
        totalPrice() {
            const total=this.customer.cart.reduce((total, game) => total + game.price*(1-game.promotion), 0);
            return total.toFixed(2);
        },

    },
    methods: {
        async fetchCart() {
            try {
                const response = await axios.get(`http://localhost:8080/customers/${this.customerID}`);
                this.customer = response.data;
                this.customer.password=response.data.password;
                //this.newCustomer = JSON.parse(JSON.stringify(this.customer));

            } catch (error) {
                console.error("Error fetching cart:", error);
            }
        },
        async fetchPayments() {
            try {
                const response = await axios.get(`http://localhost:8080/customers/${this.customerID}/payments`);
                this.payments = response.data.payments;

            } catch (error) {
                console.error("Error fetching payments:", error);
            }
        },

        handleClick(paymentId) {
            if (this.selectedPayment === paymentId) {
                this.selectedPayment = null;
            } else {
                this.selectedPayment = paymentId;
            }
        },
        async removeFromCart(game) {

            await axios.put(`http://localhost:8080/customers/${this.customerID}/cart/${game.gameId}`)

            this.fetchCart();

            this.popupMessage = `${game.name} was removed from the cart.`;
            this.showPopup = true;

            setTimeout(() => {
                this.showPopup = false;
            }, 3000);

        },

        async addPayment() {
            // Basic validation
            const { creditCardNumber, expirationDate, billingAddress, cvc } = this.newPayment;
            if (!creditCardNumber || !expirationDate || !billingAddress || !cvc) {
                this.popupMessage = "Please fill in all fields.";
                this.showPopup = true;
                setTimeout(() => (this.showPopup = false), 3000);
                return;
            }

            try {
                //  add the new payment
                await axios.post(
                    `http://localhost:8080/payment/${this.customerID}`, this.newPayment);

                this.popupMessage = `this.newPayment `;
                this.showPopup = true;
                // Reset form fields

                this.newPayment = {
                    creditCardNumber: null,
                    expirationDate: "",
                    billingAddress: "",
                    cvc: null,
                };

                this.isAddPaymentVisible = false;

                this.fetchPayments();

                this.popupMessage = "Payment method added successfully!";
                this.showPopup = true;
                setTimeout(() => (this.showPopup = false), 3000);
            } catch (error) {
                if (error.response && error.response.data && error.response.data.errors) {
                    this.popupMessage = `Failed to add payment method: ${error.response.data.errors}`; //display error from backend
                } else {
                    this.popupMessage = "Failed to add payment method."; 
                }
                this.showPopup = true;
                setTimeout(() => (this.showPopup = false), 3000);
            }
        },

        async checkout() {
            //const { shippingAddress, phone, email } = this.newCustomer;

            if (this.selectedPayment == null) {
                this.popupMessage = "Please select a payment method.";
                this.showPopup = true;
                setTimeout(() => (this.showPopup = false), 3000);
                return;
            }
            if (!this.customer.cart || this.customer.cart.length === 0) {
                this.popupMessage = "Cannot checkout with an empty cart";
                this.showPopup = true;
                setTimeout(() => (this.showPopup = false), 3000);
                return;
            }
            if(!this.customer.email||!this.customer.shippingAddress||!this.customer.phone||!this.country||!this.city){
                this.popupMessage = "Please fill out all the fields";
                this.showPopup = true;
                setTimeout(() => (this.showPopup = false), 3000);
                return;
            }

            try{
                await axios.put(`http://localhost:8080/customers/${this.customerID}`, this.customer)
            }catch(error){
                if (error.response && error.response.data && error.response.data.errors) {
                    this.showPopup = true;
                setTimeout(() => (this.showPopup = false), 3000);
                     this.popupMessage = `Failed to update information: ${error.response.data.errors}`; //display error from backend
                } else {
                    this.showPopup = true;
                setTimeout(() => (this.showPopup = false), 3000);
                     this.popupMessage = "Failed to update information."; 
                }
                
            }



            //save cart
            sessionStorage.setItem('cart', JSON.stringify(this.customer.cart));

            try {
                const response = await axios.post(`http://localhost:8080/command/${this.customerID}`, this.command)
                this.command = response.data;


                this.$router.push(`/command/${this.command.commandId}/${this.selectedPayment}/${this.customerID}/true`);

            } catch (error) {
                console.error("Error creating command:", error);
                alert("An error occurred while creating the command. Please try again.");
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
        async goToCustomerMainPage() {
            router.push({
                name: 'customer-homepage',
                params: {
                    customerId: this.customerID,
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

        this.customerID = this.$route.params.customerId;
        this.fetchCart().then(() => {
            this.fetchPayments();
        })

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
.container {
    height: 150vh;
    background-color: #ffffff;
    color: #000000;
}

.info strong {
    font-weight: bold;
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
    font-size: 1rem;
    border-radius: 20px;
    background-color: #49D8B9;
    margin-top: 40px;
    font-weight: bold;
    color: #fff;
}

.buttonCheckout:hover {
    background-color: #61c2da;
    cursor: pointer;
}

.container a {
    text-align: center;
    font-size: 1.5rem;
    margin-top: 3rem;
    font-weight: bold;
  }

.returnCart h1 {
    padding: 20px 0;
}

.returnCart .list {
    padding: 20px 0;
}

.returnCart .list .item img {
    height: 80px;
}

.returnCart .list .item a {
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

.buttons .btn {
    border-radius: 50%;
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

.payment .list .item {
    display: grid;
    grid-template-columns: 40px 1fr 300px;
    align-items: center;
    gap: 20px;
    margin-bottom: 20px;
    padding: 10px;
    box-shadow: 0 10px 20px #5555;
    border-radius: 20px;
}

.add-payment {
    display: grid;
    align-items: center;
    gap: 20px;
    margin-bottom: 20px;
    padding: 10px;
    box-shadow: 0 10px 20px #5555;
    border-radius: 20px;
}

.add-payment .form {
    display: grid;
    grid-template-columns: 1fr;
    gap: 10px;
    margin-bottom: 20px;

}

.add-payment .form .group {
    display: grid;
    grid-template-columns: 1fr 1fr;
    /* Two equal columns */
    gap: 10px;

}

.add-payment .form .group:first-child {
    grid-template-columns: 1fr;
    /* Single column for Enter Card */
}

.add-payment .form input {
    padding: 10px;
    border: 1px solid #ddd;
    color: #000000;
    border-radius: 5px;
    width: 100%;
    box-sizing: border-box;
    background-color: #eee;
}

.add-payment button {
    padding: 10px;
    background-color: #3148de;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-weight: bold;
    width: 100%;
}
</style>
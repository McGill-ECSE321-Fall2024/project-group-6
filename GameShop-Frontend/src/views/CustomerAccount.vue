<template>
    <div>
      <!-- Header Section -->
      <header>
        <nav class="navbar">
          <div class="logo">
            <h2>GameShop</h2>
          </div>
          <div class="navmenu">
            <div class="search-box">
              <input type="search" class="search" placeholder="Search game...">
              <i class="bx bx-search"></i>
            </div>
            <div class="iconAccount">
              <img src="./account.png" alt="Account">
            </div>
            <RouterLink to="/wishlist"><img src="./White-Heart.png" alt="Wishlist"></RouterLink>
            <RouterLink to="/checkout"><img src="./pngaaa.com-5034351.png" alt="Cart"></RouterLink>
          </div>
        </nav>
      </header>
  
      <!-- Customer Account Page -->
      <div class="account-page">
        <div class="account-section">
          <!-- Left Section: Account Information -->
          <div class="account-info">
            <h1>Account Details</h1>
            <div class="form">
              <div class="form-group">
                <label for="name">Full Name</label>
                <input type="text" id="name" v-model="account.name">
              </div>
              <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" v-model="account.email">
              </div>
              <div class="form-group">
                <label for="phone">Phone Number</label>
                <input type="text" id="phone" v-model="account.phone">
              </div>
              <div class="form-group">
                <label for="address">Address</label>
                <input type="text" id="address" v-model="account.address">
              </div>
            </div>
            <button @click="updateAccount" class="btn-update">Update Account</button>
          </div>
  
          <!-- Right Section: Order History -->
          <div class="order-history">
            <h1>Order History</h1>
            <div class="order-list">
              <div v-if="orders.length === 0" class="no-orders">No orders found.</div>
              <div v-else>
                <div v-for="order in orders" :key="order.id" class="order-card">
                  <div class="order-details">
                    <p><strong>Order #:</strong> {{ order.id }}</p>
                    <p><strong>Date:</strong> {{ order.date }}</p>
                    <p><strong>Total:</strong> {{ order.total }}$</p>
                    <p><strong>Status:</strong> {{ order.status }}</p>
                  </div>
                  <button @click="viewOrderDetails(order)" class="btn-view">View Details</button>
                </div>
              </div>
            </div>
          </div>
  
          <!-- Left Section: Saved Payments -->
          <div class="saved-payments">
            <h1>Saved Payments</h1>
            <div class="payment-list">
              <div v-for="payment in payments" :key="payment.cardNumber" class="payment-card">
                <p>Card: **** **** **** {{ payment.cardNumber.slice(-4) }}</p>
                <button @click="removePayment(payment)" class="btn-remove">Remove</button>
              </div>
            </div>
  
            <!-- Add New Payment Section -->
            <h2>Add New Payment</h2>
            <div class="payment-form">
              <div class="form-group">
                <label for="cardNumber">Card Number</label>
                <input type="text" id="cardNumber" v-model="newPayment.cardNumber" placeholder="Enter card number" />
              </div>
              <div class="form-group">
                <label for="expiryDate">Expiry Date</label>
                <input type="month" id="expiryDate" v-model="newPayment.expiryDate" placeholder="MM/YY" />
              </div>
              <div class="form-group">
                <label for="cardHolder">Cardholder Name</label>
                <input type="text" id="cardHolder" v-model="newPayment.cardHolder" placeholder="Cardholder Name" />
              </div>
              <div class="form-group">
                <label for="cvc">Cvc</label>
                <input type="text" id="cvc" v-model="newPayment.cvc" placeholder="Cvc" />
              </div>
              <button @click="addPayment" class="btn-add-payment">Add Payment</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
export default {
  data() {
    return {
      account: {
        name: "John Doe",
        email: "john.doe@example.com",
        phone: "123-456-7890",
        address: "123 Main St, City, Country",
      },
      orders: [
        {
          id: 101,
          date: "2024-11-25",
          total: 129.99,
          status: "Delivered",
        },
        {
          id: 102,
          date: "2024-11-20",
          total: 59.99,
          status: "Shipped",
        },
      ],
      payments: [
        { cardNumber: "1234 5678 9012 3456" },
        { cardNumber: "5678 1234 8765 4321" },
      ],
      newPayment: {
        cardNumber: "",
        expirationDate: "",
        cvc: ""
      },
    };
  },
  methods: {
    updateAccount() {
      alert("Account details updated successfully!");
    },
    viewOrderDetails(order) {
      alert(`Viewing details for Order #${order.id}`);
    },
    removePayment(payment) {
      const index = this.payments.indexOf(payment);
      if (index !== -1) {
        this.payments.splice(index, 1);
      }
    },
    addPayment() {
      if (
        this.newPayment.cardNumber &&
        this.newPayment.expirationDate &&
        this.newPayment.cvc
      ) {
        this.payments.push({
          cardNumber: this.newPayment.cardNumber,
          expirationDate: this.newPayment.expirationDate,
          cvc: this.newPayment.cvc,
        });
        // Reset the form after adding the payment
        this.newPayment.cardNumber = "";
        this.newPayment.expirationDate = "";
        this.newPayment.cvc = "";
        alert("Payment method added successfully!");
      } else {
        alert("Please fill in all fields.");
      }
    },
  },
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
  
  /* Account Page Styles */
  .account-page {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 40px;
    background-color: #f9f9f9;
    height: calc(100vh - 80px); /* Full height minus the navbar */
    width: 100vw; /* Full width */
  }
  
  .account-section {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); /* Responsive columns */
    gap: 40px;
    width: 95%;
    max-width: 1400px;
    height: 100%;
  }
  
  /* Account Info Styles */
  .account-info,
  .order-history,
  .saved-payments {
    background: #fff;
    padding: 20px;
    border-radius: 20px;
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  
  .account-info h1,
  .order-history h1,
  .saved-payments h1 {
    text-align: center;
    color: #1033a4;
  }
  
  /* Account Form */
  .form {
    margin-top: 20px;
  }
  
  .form-group {
    margin-bottom: 15px;
  }
  
  .form-group label {
    display: block;
    margin-bottom: 5px;
    color: #1033a4;
  }
  
  .form-group input {
    width: 100%;
    padding: 10px;
    border-radius: 20px;
    border: 1px solid #ccc;
  }
  
  .btn-update {
    width: 100%;
    padding: 10px;
    border-radius: 20px;
    background: #49D8B9;
    color: #fff;
    border: none;
    cursor: pointer;
    margin-top: 10px;
  }

  .btn-update:hover {
    background: #1033a4;
    color: #fff;
  }

  /* Order History */
  .order-list {
    margin-top: 20px;
  }

  .order-card {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    border-radius: 20px;
    border: 1px solid #ccc;
    margin-bottom: 10px;
    background: #f9f9f9;
  }

  .btn-view {
    padding: 8px 20px;
    background: #49D8B9;
    color: #fff;
    border: none;
    border-radius: 10px;
    cursor: pointer;
  }

  .btn-view:hover {
    background: #1033a4;
    color: #fff;
  }

  /* Saved Payments */
  .payment-list {
    margin-top: 20px;
  }

  .payment-card {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    border-radius: 20px;
    border: 1px solid #ccc;
    margin-bottom: 10px;
    background: #f9f9f9;
  }

  .btn-remove {
    padding: 8px 20px;
    background: #d9534f;
    color: #fff;
    border: none;
    border-radius: 10px;
    cursor: pointer;
  }

  .btn-remove:hover {
    background: #c9302c;
    color: #fff;
  }

  /* Add Payment Form */
  .payment-form {
    margin-top: 20px;
  }

  .payment-form .form-group {
    margin-bottom: 15px;
  }

  .payment-form label {
    display: block;
    margin-bottom: 5px;
    color: #1033a4;
  }

  .payment-form input {
    width: 100%;
    padding: 10px;
    border-radius: 20px;
    border: 1px solid #ccc;
  }

  .btn-add-payment {
    width: 100%;
    padding: 10px;
    border-radius: 20px;
    background: #49D8B9;
    color: #fff;
    border: none;
    cursor: pointer;
    margin-top: 10px;
  }

  .btn-add-payment:hover {
    background: #1033a4;
    color: #fff;
  }
</style>
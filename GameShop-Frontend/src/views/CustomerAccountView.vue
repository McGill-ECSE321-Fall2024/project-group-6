<template>
    <main>
        <h1>Customer Account</h1>

        <!-- Customer Information -->
        <h2>Edit Your Information</h2>
        <div class="account-box">
            <form @submit.prevent="saveDetails">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" v-model="customer.username" />
                </div>
                <div class="form-group">
                    <label for="shippingAddress">Shipping Address</label>
                    <input type="text" id="shippingAddress" v-model="customer.shippingAddress" />
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" v-model="customer.password" />
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" v-model="customer.email" />
                </div>
                <div class="form-group">
                    <label for="phoneNumber">Phone Number</label>
                    <input type="text" id="phoneNumber" v-model="customer.phoneNumber" />
                </div>
                <div class="form-group">
                    <button type="submit">Save</button>
                </div>
            </form>
        </div>

        <!-- Current Customer Information -->
        <h2>Your Current Information</h2>
        <table>
            <tbody>
                <tr>
                    <th>Field</th>
                    <th>Value</th>
                </tr>
                <tr>
                    <td>Username</td>
                    <td>{{ customer.username }}</td>
                </tr>
                <tr>
                    <td>Shipping Address</td>
                    <td>{{ customer.shippingAddress }}</td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td>{{ customer.password }}</td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td>{{ customer.email }}</td>
                </tr>
                <tr>
                    <td>Phone Number</td>
                    <td>{{ customer.phoneNumber }}</td>
                </tr>
            </tbody>
        </table>

        <!-- Payment Information -->
        <h2>Your Payment Methods</h2>
        <table>
            <thead>
                <tr>
                    <th>Billing Address</th>
                    <th>Credit Card</th>
                    <th>Expiration Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="payment in payments" :key="payment.id">
                    <td>{{ payment.billingAddress }}</td>
                    <td>{{ payment.creditCardNumber }}</td>
                    <td>{{ payment.expirationDate }}</td>
                    <td>
                        <button @click="deletePayment(payment.id)">Delete</button>
                    </td>
                </tr>
            </tbody>
        </table>

        <!-- Add Payment -->
        <h3>Add Payment Method</h3>
        <div class="account-box">
            <form @submit.prevent="addPayment">
                <div class="form-group">
                    <label for="billingAddress">Billing Address</label>
                    <input type="text" id="billingAddress" v-model="newPayment.billingAddress" />
                </div>
                <div class="form-group">
                    <label for="creditCardNumber">Credit Card Number</label>
                    <input type="text" id="creditCardNumber" v-model="newPayment.creditCardNumber" />
                </div>
                <div class="form-group">
                    <label for="expirationDate">Expiration Date(mm\yy)</label>
                    <input type="text" id="expirationDate" v-model="newPayment.expirationDate" />
                </div>
                <div class="form-group">
                    <label for="cvc">CVC</label>
                    <input type="text" id="cvc" v-model="newPayment.cvc" />
                </div>
                <div class="form-group">
                    <button type="submit">Add Payment</button>
                </div>
            </form>
        </div>
    </main>
</template>

<script>
import axios from "axios";

export default {
    data() {
        return {
            customer: {
                username: "david",
                shippingAddress: "555 Sherbrooke West",
                password: "password123",
                email: "david@gmail.com",
                phoneNumber: "123-456-7890"
            },
            payments: [], // Holds existing payment methods
            newPayment: {
                billingAddress: "",
                creditCardNumber: "",
                expirationDate: "",
                cvc: ""
            }
        };
    },
    methods: {
        async saveDetails() {
            try {
                const customerId = 1; // Replace with the actual customer ID
                await axios.put(`http://localhost:8080/customers/${customerId}`, {
                    username: this.customer.username,
                    email: this.customer.email,
                    password: this.customer.password,
                    phone: this.customer.phoneNumber,
                    shippingAddress: this.customer.shippingAddress
                });
                alert("Customer details updated successfully!");
            } catch (error) {
                alert("Failed to update customer details.");
                console.error(error);
            }
        },
        async fetchPayments() {
            try {
                const response = await axios.get("http://localhost:8080/payment");
                this.payments = response.data.payments;
            } catch (error) {
                console.error("Error fetching payments:", error);
            }
        },
        async addPayment() {
            try {
                const response = await axios.post("http://localhost:8080/payment", this.newPayment);
                this.payments.push(response.data); // Add the new payment to the list
                alert("Payment method added successfully!");
                this.newPayment = { billingAddress: "", creditCardNumber: "", expirationDate: "", cvc: "" }; // Reset form
            } catch (error) {
                alert("Failed to add payment method.");
                console.error(error);
            }
        },
        async deletePayment(paymentId) {
            try {
                await axios.delete(`http://localhost:8080/payment/${paymentId}`);
                this.payments = this.payments.filter(payment => payment.id !== paymentId);
                alert("Payment method deleted successfully!");
            } catch (error) {
                alert("Failed to delete payment method.");
                console.error(error);
            }
        }
    },
    mounted() {
        this.fetchPayments(); // Load payments when the component is mounted
    }
};
</script>

<style>
main {
    display: flex;
    flex-direction: column;
    align-items: center;
    font-family: Arial, sans-serif;
}

h1 {
    text-align: center;
    margin-top: 20px;
}

h2 {
    text-decoration: underline;
    padding-top: 1em;
}

.account-box {
    width: 100%;
    max-width: 400px;
    padding: 20px;
    margin: 20px auto;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.form-group {
    margin-bottom: 15px;
}

.form-group label {
    display: block;
    font-weight: bold;
    margin-bottom: 5px;
}

.form-group input {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

.form-group button {
    width: 100%;
    padding: 10px;
    background-color: #28a745;
    color: white;
    font-size: 16px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.form-group button:hover {
    background-color: #218838;
}

table {
    margin-top: 20px;
    border-collapse: collapse;
    width: 100%;
    max-width: 600px;
}

td, th {
    border: 1px solid var(--color-border);
    padding: 0.5em;
    text-align: left;
}

.danger-btn {
    border: 1px solid red;
    color: red;
    cursor: pointer;
}
</style>

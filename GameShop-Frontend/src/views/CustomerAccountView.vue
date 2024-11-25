<template>
    <main>
        <h1>Customer Account</h1>
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
    </main>
</template>

<script>
import axios from "axios";

export default {
    data() {
        return {
            customer: {
                username: "johndoe",
                shippingAddress: "123 Main Street",
                password: "password123",
                email: "johndoe@example.com",
                phoneNumber: "123-456-7890"
            }
        };
    },
    methods: {
        async saveDetails() {
            try {
                // Send PUT request to update customer details
                const customerId = 1; // Replace with actual customer ID if needed
                const response = await axios.put(`http://localhost:8087/customers/${customerId}`, {
                    username: this.customer.username,
                    email: this.customer.email,
                    password: this.customer.password,
                    phone: this.customer.phoneNumber,
                    shippingAddress: this.customer.shippingAddress
                });
                
                // Handle successful save (You can show a success message here)
                alert("Customer details updated successfully!");
                console.log(response.data); // You can log the response from the backend if needed
            } catch (error) {
                // Handle error in updating customer details
                alert("Failed to update customer details. Please try again.");
                console.error(error);
            }
        }
    }
};
</script>

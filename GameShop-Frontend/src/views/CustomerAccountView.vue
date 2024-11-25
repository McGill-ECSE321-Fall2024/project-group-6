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
    </main>
</template>

<script>
export default {
    data() {
        return {
            customer: {
                username: "david",
                shippingAddress: "555 Sherbrooke West",
                password: "password123",
                email: "david@gmail.com",
                phoneNumber: "123-456-7890"
            }
        };
    },
    methods: {
        async saveDetails() {
            try {
                // Send PUT request to update customer details
                const customerId = 1; // Replace with actual customer ID if needed
                const response = await axios.put(`http://localhost:8080/customers/${customerId}`, {
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

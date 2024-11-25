<template>
<div class="main-container">
    <header class="header">
    <div class="app-name">GameShop</div>
    <div class="search-bar">
        <input type="text" v-model="searchQuery" placeholder="Search for games by name" />
        <button @click="searchByName">Search</button>
    </div>
    <div class="auth-buttons">
        <RouterLink to="/Homepage">Sign Out</RouterLink>
    </div>
    </header>
    <div class="content">
    <aside class="categories">
        <select v-model="selectedCategory" @change="filterByCategory">
        <option value="" disabled>Select Category</option>
        <option v-for="category in categories" :key="category.id" :value="category.name">
            {{ category.name }}
        </option>
        </select>
        <div class="adjust-category">
            <button @click="addCategory">Add Category</button>
            <button @click="removeCategory">Remove Category</button>
            <button @click="unapprovedGames">Unapproved Games</button>
            <RouterLink to="/Manager-Homepage/Manage-Employees" class="manage-employees-button">Manage Employees</RouterLink>
        </div>
    </aside>
    <main class="catalog">
        <div v-if="games.length === 0">No games found</div>
        <div v-for="game in games" :key="game.id" class="game-card">
        <img :src="game.imageUrl" alt="Game Image" class="game-image" />
        <div class="game-info">
            <h3>{{ game.name }}</h3>
            <p><strong>Price:</strong> ${{ game.price }}</p>
            <p><strong>Description:</strong> {{ game.description }}</p>
            <p><strong>Stock:</strong> {{ game.stockQuantity }} left</p>
        </div>
        </div>
    </main>
    </div>
</div>
</template>

<script>
import axios from 'axios';
import { RouterLink } from 'vue-router';

export default {
data() {
    return {
    searchQuery: '',
    selectedCategory: '',
    categories: [],
    games: [
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
    async fetchCategories() {
    try {
        const response = await axios.get('http://localhost:8080/categories');
        this.categories = response.data;
    } catch (error) {
        console.error('Error fetching categories:', error);
    }
    },
    async fetchGames() {
    try {
        const response = await axios.get('http://localhost:8080/games');
        this.games = response.data;
    } catch (error) {
        console.error('Error fetching games:', error);
    }
    },
    async searchByName() {
    try {
        const response = await axios.get(`http://localhost:8080/games/search?name=${this.searchQuery}`);
        this.games = response.data;
    } catch (error) {
        console.error('Error searching for games:', error);
    }
    },
    async filterByCategory() {
    try {
        const response = await axios.get(`http://localhost:8080/games?category=${this.selectedCategory}`);
        this.games = response.data;
    } catch (error) {
        console.error('Error filtering games by category:', error);
    }
    }
},
async created() {
    await this.fetchCategories();
    await this.fetchGames();
}
};
</script>

<style scoped>
.main-container {
font-family: Arial, sans-serif;
}

.header {
display: flex;
justify-content: space-between;
align-items: center;
background-color: #333;
padding: 1rem;
color: white;
}

.app-name {
font-size: 1.5rem;
}

.search-bar {
display: flex;
align-items: center;
}

.search-bar input {
padding: 0.5rem;
font-size: 1rem;
border: 1px solid #ccc;
border-radius: 5px;
}

.search-bar button {
padding: 0.5rem 1rem;
margin-left: 0.5rem;
background-color: #28a745;
border: none;
color: white;
cursor: pointer;
border-radius: 5px;
}

.search-bar button:hover {
background-color: #218838;
}

.auth-buttons {
display: flex;
gap: 1rem;
}

.auth-buttons a {
color: white;
text-decoration: none;
background-color: #28a745;
padding: 0.5rem 1rem;
border-radius: 5px;
cursor: pointer;
}

.auth-buttons a:hover {
    background-color: #218838;
}

.content {
display: flex;
padding: 2rem;
}

.categories {
width: 200px;
}

.categories select {
width: 100%;
padding: 0.5rem;
font-size: 1rem;
border: 1px solid #ccc;
border-radius: 5px;
margin-bottom: 1rem;
}

.categories select:hover {
    background-color: lightgrey;
}

.categories select:active {
    background-color: lightgrey;
}

.catalog {
flex: 1;
display: grid;
grid-template-columns: repeat(3, 1fr);
gap: 1rem;
padding-left: 2rem;
}

.game-card {
background: #f9f9f9;
border: 1px solid #ccc;
border-radius: 5px;
padding: 1rem;
box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
display: flex;
flex-direction: column;
align-items: center;
}

.game-card h3 {
margin: 0;
}

.game-image {
max-width: 100%;
height: auto;
margin: 0.5rem 0;
}

.game-info {
text-align: center;
}

.game-card p {
margin-top: 0.5rem;
}

.adjust-category {
    display: flex;
    flex-direction: column;
    gap: 1rem;
}

.adjust-category button {
    padding: 0.5rem;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 5px;
    color: black;
    background-color: white;
    cursor: pointer;
}

.adjust-category button:hover {
    background-color: lightgrey;
}

.adjust-category button:active {
    background-color: grey;
}

.manage-employees-button {
    display: flex;
    flex-direction: column;
    padding: 0.5rem;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 5px;
    color: black;
    background-color: white;
    cursor: pointer;
    text-align: center;
}
</style>

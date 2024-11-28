<template>
    <div class="game-details-container">
      <header class="header">
        <div class="app-name">GameShop - Game Management</div>
        <button @click="logout" class="logout-btn">Logout</button>
      </header>
      <div class="content">
        <!-- Game Information Section -->
        <div class="game-info">
          <h2>{{ game.name }}</h2>
          <img :src="game.imageUrl" alt="Game Image" class="game-image" />
          <p><strong>Description:</strong> {{ game.description }}</p>
          <p><strong>Price:</strong> {{ game.price }}$</p>
          <p><strong>Stock:</strong> {{ game.stockQuantity }} left</p>
        </div>
  
        <!-- Comments Section -->
        <div class="comments-section">
          <h2>Customer Comments</h2>
          <div class="comment-card" v-for="comment in comments" :key="comment.id">
            <div class="comment-header">
              <p><strong>{{ comment.customerName }}</strong> said:</p>
              <p class="comment-content">{{ comment.content }}</p>
            </div>
            <textarea
              v-model="comment.replies"
              placeholder="Write a reply..."
              class="reply-input"
            ></textarea>
            <button @click="replyToComment(comment.id)" class="btn reply-btn">
              Reply
            </button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    data() {
      return {
        game: {
          id: 1,
          name: "Cyberpunk 2077",
          imageUrl: "https://upload.wikimedia.org/wikipedia/en/9/9f/Cyberpunk_2077_box_art.jpg",
          price: 59.99,
          description: "An open-world, action-adventure story set in Night City.",
          stockQuantity: 20,
        },
        comments: [
          {
            id: 1,
            customerName: "John Doe",
            content: "Amazing game! Totally worth it.",
            replies: "",
          },
          {
            id: 2,
            customerName: "Jane Smith",
            content: "The game has bugs, but it's still enjoyable.",
            replies: "",
          },
        ],
      };
    },
    methods: {
      async replyToComment(commentId) {
        const comment = this.comments.find((c) => c.id === commentId);
        try {
          await axios.post(`http://localhost:8080/comments/${commentId}/reply`, {
            reply: comment.replies,
          });
          alert("Reply posted successfully!");
          comment.replies = ""; // Clear reply box
        } catch (error) {
          console.error("Error posting reply:", error);
          alert("Failed to post reply.");
        }
      },
      logout() {
        this.$router.push("/signin"); // Redirect to login
      },
    },
  };
  </script>
  
  <style scoped>
  /* Header */
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #1033a4; /* Same as wishlist page */
    color: white;
    padding: 1rem;
  }
  .app-name {
    font-size: 1.5rem;
  }
  .logout-btn {
    background-color: #ff6f61; /* Soft red for logout button */
    color: white;
    border: none;
    padding: 0.5rem 1rem;
    border-radius: 20px;
    font-size: 0.9rem;
  }
  
  /* Content */
  .content {
    margin: 2rem;
    display: flex;
    flex-direction: column;
    gap: 2rem;
  }
  .game-info {
    background-color: white;
    padding: 1.5rem;
    border-radius: 10px;
    box-shadow: 0 10px 20px rgba(85, 85, 85, 0.2);
  }
  .game-info h2 {
    font-size: 2rem;
    margin-bottom: 1rem;
  }
  .game-image {
    width: 200px;
    height: auto;
    margin-bottom: 1rem;
    border-radius: 10px;
  }
  .game-info p {
    font-size: 1rem;
    margin: 0.5rem 0;
  }
  
  /* Comments Section */
  .comments-section {
    background-color: white;
    padding: 1.5rem;
    border-radius: 10px;
    box-shadow: 0 10px 20px rgba(85, 85, 85, 0.2);
  }
  .comments-section h2 {
    font-size: 1.5rem;
    margin-bottom: 1rem;
  }
  .comment-card {
    border-top: 1px solid #eee;
    padding: 1rem 0;
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
  }
  .comment-header {
    display: flex;
    flex-direction: column;
  }
  .comment-content {
    font-size: 1rem;
    margin: 0.5rem 0;
  }
  .reply-input {
    width: 100%;
    padding: 0.5rem;
    border: 1px solid #ccc;
    border-radius: 10px;
    resize: none;
  }
  .reply-btn {
    align-self: flex-end;
    background-color: #88b9df; /* Same as wishlist button */
    color: white;
    border: none;
    padding: 0.5rem 1rem;
    border-radius: 20px;
    font-size: 0.9rem;
  }
  .reply-btn:hover {
    background-color: #ffffff;
    color: #88b9df;
  }
  </style>
  
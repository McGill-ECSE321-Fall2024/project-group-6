<template>
<body>
<header>
    <nav class="navbar">
        <div class="logo">
            <h2>GameShop</h2>
        </div>
        <div class ="navmenu">
            <div class="search-box">
                <input type="search" class="search" placeholder="Search game...">
                <i class='bx bx-search'></i>
            </div>
            <div class="iconAccount">
                <img src="account.png">
            </div>
            <RouterLink to="/wishlist"><img src="White-Heart.png"></RouterLink>

            <RouterLink to="/cart"><img src="pngaaa.com-5034351.png"></RouterLink>
        </div>
    </nav>

    <div class="container">
        <div class="checkoutLayout">
            <div class="returnCart">
            <a href="/">Keep shopping</a>
            <h1>List Product in Cart</h1>
            <div class="list">
                    <div v-for="game in cart" :key="game.id" class="game-card">
                        <div class="item">
                            <img :src="game.imageUrl">
                            <div class="info">
                                <div class="name">{{ game.name }}</div>
                                <p><strong>Stock:</strong> {{ game.stockQuantity }} left</p>
                                <div class="price">{{ game.price }}</div>
                            </div>
                            <div class="returnPrice">{{ game.price }}</div>
                            <div class="buttons">
                                <a href="" class="btn">-</a>
                            </div>
                        </div>
                    </div>
            </div>
        </div>


        <div class="right">
            <h1>Checkout</h1>

            <div class="form">
                <div class="group">
                    <label for="name">Full Name</label>
                    <input type="text" name="name" id="name">
                </div>

                <div class="group">
                    <label for="phone">Phone Number</label>
                    <input type="text" name="phone" id="phone">
                </div>

                <div class="group">
                    <label for="address">Address</label>
                    <input type="text" name="address" id="address">
                </div>

                <div class="group">
                    <label for="country">Country</label>
                    <select name="country" id="country">
                        <option value="">Choose..</option>
                        <option value="">Canada</option>
                    </select>
                </div>

                <div class="group">
                    <label for="city">Province</label>
                    <select name="city" id="city">
                        <option value="">Choose..</option>
                        <option value="">Quebec</option>
                    </select>
                </div>
            </div>
            <div class="return">
                <div class="row">
                    <div>Total Quantity</div>
                    <div class="totalQuantity">70</div>
                </div>
                <div class="row">
                    <div>Total Price</div>
                    <div class="totalPrice">$900</div>
                </div>
            </div>
            <button class="buttonCheckout">CHECKOUT</button>
        </div>
    </div>
</div>
</header>
</body>
</template>

<script>
import axios from "axios";
import { RouterLink } from 'vue-router';

  export default {
    data() {
      return {
        wishlist: [
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
      async fetchGames() {
        try {
          const response = await axios.get('http://localhost:8080/customers');
          this.games = response.data;
        } catch (error) {
          console.error('Error fetching games:', error);
        }
      },
  }
}
</script>

<style>
*{
    margin :0;
    padding:0;
    text-decoration: none;
    list-style: none;

}

.navbar{
    display: flex;
    justify-content: space-between;
    align-items: center;
    text-align: center;
    width: 100%;
    height: 80px;
    background: #1033a4;
}
.navbar h2{
    color: #ffffff;
    font-size: 25px;
    font-weight: 500;
    padding: 20px 20px;
}
.navmenu{
    height: 50px;
    line-height: 60px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.search-box .search{
    width: 500px;
    padding: 8px 8px;
    border-radius: 50px;
    font-size: 16px;
}
.search-box{
    margin-right: 200px;
}

.navmenu .search-box i{
    color: #ffffff;
    position: relative;
    right: 40px;
    top: 2px;
    background-color:#1140d9;
    padding: 8px;
    border-radius: 50px;
}
header img{
   margin-top: 15px;
   margin-right: 10px;
    align-items: center;
    width: 40px;
}
.navmenu .iconcCart{
    align-items: center;
    position: relative;
    margin: 10px;
    z-position: 1;
    display: inline-block;
}

header{
    background: #e7f0f3ac;
    width: 100%;
    height: 100vh;
}
.main-header{
    width: 90%;
    margin: auto;
    height: 40px;
    display: flex;
     margin-bottom: 15px;
}
.main-header h5{
   font-size: 20px;
   font-weight: 550px;
   margin-top: 10px;
   margin-bottom: 15px;
}
.main-header h2{
   font-size: 38px;
   width: 500px;
   margin-top: 10px;
   margin-bottom: 15px;
}
.main-header p{
    margin-bottom: 60px;
}
.main-header .btn{
    background: #88b9df;
    border: 1px solid #88b9df;
    font-size: 15px;
    color: #ffffff;
    font-weight: 400;
    padding: 4px 20px;
    border-radius: 50px;
}
.main-header .btn:hover{
    color: #88b9df;
    background: #ffffff;
}


.wishlist{

    justify-content: center;
    margin-top: 150px;
    width: 80%;
    margin-left: 200px;

}


html {
	font-family:  "poppins";;
}

h2 {
	padding-top: 0.5em;
}



.listProduct{
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
}
.listProduct .item img{
    width: 100%;
        height: 430px;
    object-fit: cover;
}
.listProduct .item{
    position: relative;
}
.listProduct .item h2{
    font-weight: 700;
    font-size: x-large;
}
.listProduct .item .price{
    font-size: x-large;
}

.listProduct .item button{
    position: absolute;
    top: 50%;
    left: 50%;
    background-color: #e6572c;
    color: #fff;
    width: 50%;
    border: none;
    padding: 20px 30px;
    box-shadow: 0 10px 50px #000;
    cursor: pointer;
    transform: translateX(-50%) translateY(100px);
    opacity: 0;
}
.listProduct .item:hover button{
    transition:  0.5s;
    opacity: 1;
    transform: translateX(-50%) translateY(0);
}
.cart{
    color: #fff;
    position: fixed;
    width: 400px;
    max-width: 80vw;
    height: 100vh;
    background-color: #0E0F11;
    top: 0px;
    right: -100%;
    display: grid;
    grid-template-rows: 50px 1fr 50px;
    gap: 20px;
    transition: right 1s;
}

.cart .buttons .checkout{
    background-color: #E8BC0E;
    color: #000;
}
.cart h2{
    color: #E8BC0E;
    padding: 20px;
    height: 30px;
    margin: 0;
}

.cart .listCart .item{
    display: grid;
    grid-template-columns: 50px 1fr 70px;
    align-items: center;
    gap: 20px;
    margin-bottom: 20px;

}
.cart .listCart img{
    width: 100%;
    height: 70px;
    object-fit: cover;
    border-radius: 10px;
}
.cart .listCart .item .name{
    font-weight: bold;
}
.cart .listCart .item .quantity{
    display: flex;
    justify-content: end;
    align-items: center;
}
.cart .listCart .item .quantity span{
    display: block;
    width: 50px;
    text-align: center;
}

.cart .listCart{
    padding: 20px;
    overflow: auto;
}
.cart .listCart::-webkit-scrollbar{
    width: 0;
}

.cart .buttons{
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    text-align: center;
}
.cart .buttons div{
    background-color: #000;
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight: bold;
    cursor: pointer;
}
.cart .buttons a{
    color: #fff;
    text-decoration: none;
}
.checkoutLayout{
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 50px;
    padding: 20px;
}
.checkoutLayout .right{
    background-color: #5358B3;
    border-radius: 20px;
    padding: 40px;
    color: #fff;
}
.checkoutLayout .right .form{
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 20px;
    border-bottom: 1px solid #7a7fe2;
    padding-bottom: 20px;
}
.checkoutLayout .form h1,
.checkoutLayout .form .group:nth-child(-n+3){
    grid-column-start: 1;
    grid-column-end: 3;
}
.checkoutLayout .form input,
.checkoutLayout .form select
{
    width: 100%;
    padding: 10px 20px;
    box-sizing: border-box;
    border-radius: 20px;
    margin-top: 10px;
    border:none;
    background-color: #6a6fc9;
    color: #fff;
}
.checkoutLayout .right .return .row{
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 10px;
}
.checkoutLayout .right .return .row div:nth-child(2){
    font-weight: bold;
    font-size: x-large;
}

.buttonCheckout{
    width: 100%;
    height: 40px;
    border: none;
    border-radius: 20px;
    background-color: #49D8B9;
    margin-top: 20px;
    font-weight: bold;
    color: #fff;
}

.returnCart h1{
    border-top: 1px solid #eee;
    padding: 20px 0;
}
.returnCart .list .item img{
    height: 80px;
}
.wishlist .returnCart .list .item{
    display: grid;
    grid-template-columns: 90px 400px 150px 100px;
    align-items: center;
    gap: 20px;
    margin-top: 20px;
    margin-bottom: 30px;
    padding: 10px 0;
    box-shadow: 0 10px 20px #5555;
    border-radius: 20px;
}
.returnCart .list .item .buttons a{
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
.returnCart .list .item{
    display: grid;
    grid-template-columns: 80px 1fr 70px 30px;
    align-items: center;
    gap: 20px;
    margin-bottom: 30px;
    padding: 10px;
    box-shadow: 0 10px 20px #5555;
    border-radius: 20px;
}
.returnCart .list .item .name,
.returnCart .list .item .returnPrice{
    font-size: large;
    font-weight: bold;

}
</style>
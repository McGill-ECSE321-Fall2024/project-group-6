import { createRouter, createWebHistory } from 'vue-router'
import SignUp from '@/views/SignUp.vue'
import SignIn from '@/views/SignIn.vue'
import HomePageView from '@/views/HomePageView.vue'
import Wishlist from '@/views/Wishlist.vue'
import Checkout from '@/views/Checkout.vue'
import Command from '@/views/Command.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'events',
      component: SignUp
    },
    {
      path: '/homepage',
      name: 'event-details',
      component: HomePageView
    },
   
    {
      path: '/hello',
      name: 'home',
      component: SignIn,
    },
    {
      path: '/wishlist',
      name: 'wishlist',
      component: Wishlist,
    },
    {
      path: '/checkout',
      name: 'checkout',
      component: Checkout,
    },
    {
      path: '/command',
      name: 'command',
      component: Command,
    },
    
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
    },
  ],
})

export default router

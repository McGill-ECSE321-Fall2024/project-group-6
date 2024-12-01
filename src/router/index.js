import { createRouter, createWebHistory } from 'vue-router'

import SignUp from '@/views/SignUp.vue'
import SignIn from '@/views/SignIn.vue'
import HomePageView from '@/views/HomePageView.vue'
import ManagerMainPage from '@/views/ManagerMainPage.vue'
import CustomerMainPage from '@/views/CustomerMainPage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'events',
      component: SignUp
    },

    {
      path: '/Homepage',
      name: 'event-details',
      component: HomePageView
    },

    {
      path: '/hello',
      name: 'home',
      component: SignIn,
    },
    
    {
      path: '/Manager-Homepage',
      name: 'manager-home-page',
      component: ManagerMainPage
    },

    {
      path: '/Customer-Homepage',
      name: 'customer-home-page',
      component: CustomerMainPage,
    },
    
  ],
})

export default router

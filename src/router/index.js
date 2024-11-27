import { createRouter, createWebHistory } from 'vue-router'

import SignUp from '@/views/SignUp.vue'
import SignIn from '@/views/SignIn.vue'
import HomePageView from '@/views/HomePageView.vue'

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
    }
    
  ],
})

export default router

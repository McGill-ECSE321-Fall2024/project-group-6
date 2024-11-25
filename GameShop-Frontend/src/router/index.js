import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import CustomerAccountView from '@/views/CustomerAccountView.vue'
import GamePageView from '@/views/GamePageView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/home',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
    },
    {
      path: '/customerAccount',
      name: 'customerAccount',
      component: CustomerAccountView
    },
    {
      path: '/gamePage',
      name: 'gamePage',
      component: GamePageView
    },
  ],
})

export default router

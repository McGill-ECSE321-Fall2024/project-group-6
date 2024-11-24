import { createRouter, createWebHistory } from 'vue-router'
import EventDetailsView from '../views/EventDetailsView.vue'
import EventsView from '../views/EventsView.vue'
import HomeView from '../views/HomeView.vue'
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

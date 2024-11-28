import { createRouter, createWebHistory } from 'vue-router'
import SignUp from '@/views/SignUp.vue'
import SignIn from '@/views/SignIn.vue'
import HomeView from '@/views/HomeView.vue'
import EmployeeHome from '@/views/EmployeeHome.vue'   


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
      component: HomeView
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
    {
      path: '/employee',
      name: 'employee-dashboard', // Ensure this matches SignIn.vue
      component: EmployeeHome, // Points to EmployeeHome.vue
      meta: { requiresAuth: true, role: 'employee' },
    },
    {
      path: '/game/:id',
      name: 'game-details',
      component: () => import('@/views/GameDetails.vue'),
      meta: { requiresAuth: true, role: 'employee' },
    },
    {
      path: '/account',
      name: 'account',
      component: () => import('@/views/EmployeeAccount.vue'),
      meta: { requiresAuth: true },
    },
    {
      path: '/manager/games/edit',
      name: 'game-information',
      component: () => import('@/views/EditGameManager.vue'),
      meta: { requiresAuth: true, role: 'manager' },
    },
    {
      path: '/manager/games',
      name: 'game-catalog-manager',
      component: () => import('@/views/GameCatalogManager.vue'),
      meta: { requiresAuth: true, role: 'manager' },
    },
    {
      path: '/manager/employees',
      name: 'employee-management',
      component: () => import('@/views/ManageEmployees.vue'),
      meta: { requiresAuth: true },
    },
    {
      path: '/wishlist',
      name: 'wishlist',
      component: () => import('@/views/Wishlist.vue'),
      meta: { requiresAuth: true, role: 'manager' },
    },
    {
      path: '/manager/games/:id',
      name: 'game-page-manager',
      component: () => import('@/views/GamePageManager.vue'),
      meta: { requiresAuth: true, role: 'manager' },
    }     
  ],
})

export default router
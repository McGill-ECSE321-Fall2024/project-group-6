import { createRouter, createWebHistory } from 'vue-router'

import SignUp from '@/views/SignUp.vue'
import SignIn from '@/views/SignIn.vue'
import HomePageView from '@/views/HomePageView.vue'
import Wishlist from '@/views/Wishlist.vue'
import EmployeeHomePage from '@/views/EmployeeHomePage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/SignUp',
      name: 'events',
      component: SignUp
    },
    {
      path: '/homepage',
      name: 'event-details',
      component: HomePageView
    },
   
    {
      path: '/SignIn',
      name: 'sign in',
      component: SignIn,
    },
    {
      path: '/wishlist',
      name: 'wishlist',
      component: Wishlist,
    },
      {
        path: '/employeeHomePage/:employeeId/:loggedIn',
        name: 'employee-homepage',
        component: EmployeeHomePage,
        props: true,
        beforeEnter: (to, from, next) => {
          if (localStorage.getItem('loggedIn') === 'true') {
            next();
          } else {
            alert('Please sign in before accessing this page.');
            next({ name: 'sign in' });
          }
        }
      }
      

      
    
    
  ],
})

export default router

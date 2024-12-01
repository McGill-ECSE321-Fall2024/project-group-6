import { createRouter, createWebHistory } from 'vue-router'

import SignUp from '@/views/SignUp.vue'
import SignIn from '@/views/SignIn.vue'
import HomePageView from '@/views/HomePageView.vue'
import ManagerMainPage from '@/views/ManagerMainPage.vue'
import CustomerMainPage from '@/views/CustomerMainPage.vue'
import Wishlist from '@/views/Wishlist.vue'
import EmployeeHomePage from '@/views/EmployeeHomePage.vue'
import EmployeeAccount from '@/views/EmployeeAccount.vue'
import CustomerAccount from '@/views/CustomerAccount.vue'
import EmployeeGameView from '@/views/EmployeeGameView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/SignUp',
      name: 'events',
      component: SignUp
    },

    {
      path: '/Homepage',
      name: 'event-details',
      component: HomePageView
    },

    {
      path: '/SignIn',
      name: 'sign in',
      component: SignIn,
    },
    
    {
      path: '/managerHomePage/:managerId/:loggedIn',
      name: 'manager-homepage',
      component: ManagerHomePage,
      props: true,
      beforeEnter: (to, from, next) => {
        if (localStorage.getItem('loggedIn') === 'true') {
          next();
        } else {
          alert('Please sign in before accessing this page.');
          next({ name: 'sign in' });
        }
      }
    },

    {
      path: '/customerHomePage/:customerId/:loggedIn',
      name: 'customer-homepage',
      component: CustomerHomePage,
      props: true,
      beforeEnter: (to, from, next) => {
        if (localStorage.getItem('loggedIn') === 'true') {
          next();
        } else {
          alert('Please sign in before accessing this page.');
          next({ name: 'sign in' });
        }
      }
    },

    {
      path: '/wishlist',
      name: 'wishlist',
      component: Wishlist,
    },
    {
      path: '/employeeAccount/:employeeId/:loggedIn',
      name: 'employee-account',
      component: EmployeeAccount,
      
      props:true,
      
      beforeEnter: (to, from, next) => {
        if (localStorage.getItem('loggedIn') === 'true') {
          next();
        } else {
          alert('Please sign in before accessing this page.');
          next({ name: 'sign in' });
        }
      }
      
    },
    {
      path: '/customerAccount',
      name: 'customer-account',
      component: CustomerAccount,
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
    },
      {
        path: '/employeeGamePage/:employeeId/:loggedIn/:gameId',
        name: 'employee-gamepage',
        component: EmployeeGameView,
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

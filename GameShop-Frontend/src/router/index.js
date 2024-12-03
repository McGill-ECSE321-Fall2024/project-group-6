import { createRouter, createWebHistory } from 'vue-router'

import SignUp from '@/views/SignUp.vue'
import SignIn from '@/views/SignIn.vue'
import HomePageView from '@/views/HomePageView.vue'
import Wishlist from '@/views/Wishlist.vue'
import EmployeeHomePage from '@/views/EmployeeHomePage.vue'
import EmployeeAccount from '@/views/EmployeeAccount.vue'
import CustomerAccountView from '@/views/CustomerAccountView.vue'
import EmployeeGameView from '@/views/EmployeeGameView.vue'
import ManagerGameView from '@/views/ManagerGameView.vue'
import ManagerHomePage from '@/views/ManagerHomePage.vue'
import CustomerHomePage from '@/views/CustomerHomePage.vue'
import Checkout from '@/views/Checkout.vue'
import Command from '@/views/Command.vue'
import GamePageView from '@/views/GamePageView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    //{
      //path: '/home',
      //name: 'home',
      //component: HomeView,
    //},
    //{
     // path: '/about',
      //name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      //component: () => import('../views/AboutView.vue'),
    //},
    {
      path: '/customerAccount/:customerId/:loggedIn',
      name: 'customer-account',
      component: CustomerAccountView,
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
      path: '/customerGamePage/:customerId/:loggedIn/:gameId',
      name: 'customer-gamepage',
      component: GamePageView,
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
      path: '/SignUp',
      name: 'events',
      component: SignUp
    },
    {
      path: '/',
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
        path: '/managerGamePage/:managerId/:loggedIn/:gameId',
        name: 'manager-gamepage',
        component: ManagerGameView,
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
        path: '/wishlist/:customerId/:loggedIn',
        name: 'customer-wishlist',
        component: Wishlist,
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
        path: '/checkout/:customerId/:loggedIn',
        name: 'customer-cart',
        component: Checkout,
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
        path: '/command/:commandId/:paymentId/:customerId/:loggedIn',
        name: 'command',
        component: Command,
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

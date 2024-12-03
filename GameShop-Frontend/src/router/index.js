import { createRouter, createWebHistory } from 'vue-router'
import SignUp from '@/views/SignUp.vue'
import SignIn from '@/views/SignIn.vue'
import HomePageView from '@/views/HomePageView.vue'
import Wishlist from '@/views/Wishlist.vue'
import Checkout from '@/views/Checkout.vue'
import Command from '@/views/Command.vue'
import EmployeeHomePage from '@/views/EmployeeHomePage.vue'
import EmployeeAccount from '@/views/EmployeeAccount.vue'
import CustomerAccount from '@/views/CustomerAccount.vue'
import EmployeeGameView from '@/views/EmployeeGameView.vue'
import ManagerGameView from '@/views/ManagerGameView.vue'
import ManageEmployees from '@/views/ManageEmployees.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/SignUp',
      name: 'events',
      component: SignUp
    },
    {
      path: '/SignIn',
      name: 'sign in',
      component: SignIn,
    },
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
      path: '/command/:commandId/:paymentId',
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
      },
      {
        path: '/managerGamePage',
        name: 'manager-gamepage',
        component: ManagerGameView,
      },
      {
        path: '/manageEmployees',
        name: 'manage-employees',
        component: ManageEmployees,
      }
  ],
})

export default router

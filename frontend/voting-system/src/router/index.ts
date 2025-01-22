import { createRouter, createWebHistory } from 'vue-router'
import VoteList from '../views/VoteList.vue'
import VoteManagement from '../views/VoteManagement.vue'
import Register from '../views/Register.vue'
import Login from '../views/Login.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/register',
      name: 'register-user',
      component: Register,
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
    },
    {
      path: '/',
      name: 'vote-list',
      component: VoteList,
    },
    {
      path: '/manage',
      name: 'vote-management',
      component: VoteManagement
    },
  ],
})

export default router

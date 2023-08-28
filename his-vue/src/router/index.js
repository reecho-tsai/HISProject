import Vue from 'vue'
import VueRouter from 'vue-router'
const Home = () => import('../views/Home.vue')

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login')
  },
  {
    path: '/login_code',
    name: 'PhoneLogin',
    component: () => import('@/views/Login_phone')
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    children: [
      {
        path: '/constant-type',
        name: 'ConstantType',
        component: () => import('@/views/constant-type')
      },
      {
        path: '/constant-item',
        name: 'ConstantItem',
        component: () => import('@/views/constant-item')
      },
      {
        path: '/registlevel',
        name: 'Registlevel',
        component: () => import('@/views/registlevel')
      },
      {
        path: '/department',
        name: 'Department',
        component: () => import('@/views/department')
      },
      {
        path: '/role',
        name: 'Role',
        component: () => import('@/views/role')
      },
      {
        path: '/user',
        name: 'User',
        component: () => import('@/views/user')
      },
      {
        path: '/permission',
        name: 'Permission',
        component: () => import('@/views/permission')
      },
      {
        path: '/register',
        name: 'Register',
        component: () => import('@/views/register')
      },
      {
        path: '/return-num',
        component: () => import('@/views/register/return-num')
      },
      {
        path: '/fee',
        component: () => import('@/views/register/fee')
      },
      {
        path: '/refund',
        component: () => import('@/views/register/refund')
      },
      {
        path: '/check-item',
        name: 'CheckItem',
        component: () => import('@/views/check-item')
      },
      {
        path: '/inspect-item',
        name: 'InspectItem',
        component: () => import('@/views/inspect-item')
      },
      {
        path: '/doctor',
        name: 'Doctor',
        component: () => import('@/views/doctor')
      },
      {
        path: '/cases',
        name: 'Cases',
        component: () => import('@/views/doctor/Cases')
      },
      {
        path: '/work',
        name: 'work',
        component: () => import('@/views/work')
      },
      {
        path: '/dept_list',
        name: 'DeptList',
        component: () => import('@/views/doctor/dept_list')
      }
    ]
  }
]

const router = new VueRouter({
  routes
})

// 挂载路由导航守卫
router.beforeEach((to, from, next) => {
  // to 将要访问的路径
  // from 代表从哪个路径跳转而来
  // next 是一个函数，表示放行
  //     next()  放行    next('/login')  强制跳转
  if (to.path === '/login') return next()
  if (to.path === '/login_code') return next()
  // 获取token
  const tokenStr = window.sessionStorage.getItem('token')
  if (!tokenStr) return next('/login')
  next()
})

export default router

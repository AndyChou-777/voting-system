<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import { ref, onMounted } from 'vue'
import { authService } from './api/authService'

// 定義用戶狀態
interface UserState {
  isLoggedIn: boolean
  role: string | null
}

const userState = ref<UserState>({
  isLoggedIn: false,
  role: null
})

// 檢查登入狀態
const checkSession = async () => {
  console.log('檢查登入狀態')
  try {

    const response = await authService.checkSession()
    console.log(response)
    if (response.status === 200) {
      userState.value.isLoggedIn = true
      userState.value.role = response.data.role
    } else {
      userState.value.isLoggedIn = false
      userState.value.role = null
    }
  } catch (error) {
    userState.value.isLoggedIn = false
    userState.value.role = null
  }
}

// 處理登出
const handleLogout = async () => {
  try {
    await authService.logout()
    userState.value.isLoggedIn = false
    userState.value.role = null
    // 可以添加路由導航到首頁
    // router.push('/')
  } catch (error) {
    console.error('登出失敗:', error)
  }
}

// 組件掛載時檢查登入狀態
onMounted(() => {
  checkSession()
})
</script>

<template>
  <header class="fixed-header">
    <div class="wrapper">
      <nav class="top-nav">
        <!-- 所有用戶都能看到首頁 -->
        <RouterLink to="/">首頁</RouterLink>

        <!-- 未登入用戶可以看到註冊和登入 -->
        <template v-if="!userState.isLoggedIn">
          <RouterLink to="/register">會員註冊</RouterLink>
          <RouterLink to="/login">會員登入</RouterLink>
        </template>

        <!-- Admin 可以看到後臺管理 -->
        <RouterLink 
          v-if="userState.isLoggedIn && userState.role === 'Admin'" 
          to="/manage">後臺管理</RouterLink>

        <!-- 登入用戶可以看到登出按鈕 -->
        <a 
          v-if="userState.isLoggedIn" 
          href="#" 
          @click.prevent="handleLogout"
          class="logout-link">登出</a>
      </nav>
    </div>
  </header>

  <div class="content-wrapper">
    <RouterView />
  </div>
</template>

<style scoped>
.fixed-header {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.top-nav {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 1rem;
  gap: 2rem;
}

.top-nav a {
  text-decoration: none;
  color: #333;
  font-size: 1rem;
  transition: color 0.3s ease;
}

.top-nav a:hover {
  color: #666;
}

.top-nav a.router-link-exact-active {
  color: #42b983;
}

.logout-link {
  cursor: pointer;
}

.content-wrapper {
  margin-top: 60px; /* 根據導航欄高度調整 */
  padding: 20px;
}

@media (max-width: 768px) {
  .top-nav {
    flex-direction: column;
    gap: 1rem;
  }
}
</style>
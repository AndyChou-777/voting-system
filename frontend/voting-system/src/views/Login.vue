<template>
    <div class="login-container">
      <div class="login-card">
        <h1 class="login-title">用戶登入</h1>
        
        <!-- 错误或成功消息 -->
        <div v-if="message" 
             :class="['message', { 'error': isError, 'success': !isError }]">
          {{ message }}
        </div>
  
        <form @submit.prevent="login" class="login-form">
          <div class="form-group">
            <label for="email">電子信箱</label>
            <input 
              id="email" 
              v-model="form.email" 
              type="email" 
              required
              placeholder="請輸入電子信箱"
              maxlength="255"
              :disabled="loading"
            />
          </div>
  
          <div class="form-group">
            <label for="password">密碼</label>
            <input 
              id="password" 
              v-model="form.password" 
              type="password" 
              required
              placeholder="請輸入密碼"
              :disabled="loading"
              minlength="8"
              maxlength="24"
              title="密碼需包含至少 8 個字符，最大 24 個字符"
            />
          </div>
  
          <button 
            type="submit" 
            class="login-button"
            :disabled="loading"
          >
            {{ loading ? '登入中...' : '確認登入' }}
          </button>
        </form>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, reactive } from 'vue';
  import { authService } from '../api/authService';
  import { useRouter } from 'vue-router';
  
  const router = useRouter();
  const loading = ref(false);
  const message = ref('');
  const isError = ref(false);
  
  const form = reactive({
    email: '',
    password: ''
  });
  
  const login = async () => {
    loading.value = true;
    message.value = '';
    isError.value = false;
    
    try {
      const response = await authService.login(form.email, form.password);
      message.value = '登入成功！即將跳轉到主頁...';
      isError.value = false;
      
      // 保存用户信息到本地存储或状态管理
      localStorage.setItem('user', JSON.stringify(response.data));
  
      // 跳转到主页面
      setTimeout(() => {
        router.push('/');
      }, 3000);
    } catch (error: any) {
      isError.value = true;
      if (error.response?.data) {
        message.value = error.response.data.message || '登入失敗';
      } else {
        message.value = '登入失敗，請稍後再試';
      }
    } finally {
      loading.value = false;
    }
  };
  </script>
  
  <style scoped>
  .login-container {
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 20px;
    background-color: #f5f5f5;
  }
  
  .login-card {
    background: white;
    padding: 2rem;
    border-radius: 8px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 400px;
  }
  
  .login-title {
    text-align: center;
    color: #333;
    margin-bottom: 1.5rem;
    font-size: 1.5rem;
  }
  
  .login-form {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }
  
  .form-group {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
  }
  
  .form-group label {
    color: #666;
    font-size: 0.9rem;
  }
  
  .form-group input {
    padding: 0.75rem;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 1rem;
    transition: border-color 0.2s;
  }
  
  .form-group input:focus {
    border-color: #4CAF50;
    outline: none;
  }
  
  .login-button {
    margin-top: 1rem;
    padding: 0.75rem;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 4px;
    font-size: 1rem;
    cursor: pointer;
    transition: background-color 0.2s;
  }
  
  .login-button:hover:not(:disabled) {
    background-color: #45a049;
  }
  
  .login-button:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
  }
  
  .message {
    padding: 1rem;
    margin-bottom: 1rem;
    border-radius: 4px;
    text-align: center;
  }
  
  .error {
    background-color: #ffebee;
    color: #c62828;
    border: 1px solid #ffcdd2;
  }
  
  .success {
    background-color: #e8f5e9;
    color: #2e7d32;
    border: 1px solid #c8e6c9;
  }
  
  @media (max-width: 480px) {
    .login-card {
      padding: 1.5rem;
    }
  }
  </style>
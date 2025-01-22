import api from './config'

export const authService =  {

    // 用戶註冊
    registerUser(email: string, password: string, username: string, role: string) {
        return api.post('/auth/register', {
            email,
            password,
            username,
            role,
          })
      },

    // 用戶登入
    login(email: string, password: string) {
        return api.post('/auth/login', {
        email,
        password,
        })
    },

    // 用戶登出
    logout() {
        return api.get('/auth/logout')
    },

    // 檢查登入狀態
    checkSession() {
        return api.get('/auth/session')
    },

  }
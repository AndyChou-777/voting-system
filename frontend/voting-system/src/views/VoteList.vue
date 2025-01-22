<template>
    <div class="vote-list">
      <h1>投票列表</h1>
      
      <!-- 錯誤提示 -->
      <div v-if="error" class="error">
        {{ error }}
      </div>
  
      <!-- 載入中提示 -->
      <div v-if="loading" class="loading">
        載入中...
      </div>
  
      <!-- 投票列表 -->
      <div v-else class="vote-items">
        <div v-for="item in voteItems" :key="item.id" class="vote-item">
          <input 
            type="checkbox" 
            :value="item.id" 
            v-model="selectedItems"
            :disabled="loading"
          >
          <span>{{ item.itemName }}</span>
          <span>|</span>
          <span>目前累計投票數: {{ item.voteCount }} 票</span>
        </div>
      </div>
  
      <!-- 提交按鈕 -->
      <button 
        @click="submitVote" 
        :disabled="loading || !selectedItems.length"
        class="submit-button"
      >
        提交投票
      </button>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { votingService } from '../api/votingService'
  
  const voteItems = ref([])
  const selectedItems = ref([])
  const loading = ref(false)
  const error = ref('')
  
  // 獲取投票項目
  const fetchVoteItems = async () => {
    loading.value = true
    error.value = ''
    try {
      const response = await votingService.getAllVoteItems()
      console.log(response)
      voteItems.value = response.data
    } catch (err) {
      error.value = '獲取投票項目失敗'
      console.error('獲取投票項目失敗:', err)
    } finally {
      loading.value = false
    }
  }
  
  // 提交投票
  const submitVote = async () => {
    if (!selectedItems.value.length) return
    
    loading.value = true
    error.value = ''
    try {
      await votingService.addVoteRecords(selectedItems.value)  // 1 是假設的用戶ID
      alert('投票成功！')
      selectedItems.value = [] // 清空選擇
    } catch (err) {
      error.value = '提交投票失敗，請先進行登入!'
      console.error('提交投票失敗:', err)
    } finally {
      loading.value = false
    }
  }
  
  // 組件掛載時獲取數據
  onMounted(() => {
    fetchVoteItems()
  })
  </script>
  
  <style scoped>
  .vote-list {
    max-width: 600px;
    margin: 20px auto;
    padding: 20px;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  }
  
  .vote-items {
    margin: 20px 0;
  }
  
  .vote-item {
    padding: 12px;
    margin: 8px 0;
    border: 1px solid #eee;
    border-radius: 4px;
    display: flex;
    align-items: center;
    gap: 12px;
  }
  
  .vote-item input[type="checkbox"] {
    width: 18px;
    height: 18px;
    cursor: pointer;
  }
  
  .vote-item span {
    font-size: 16px;
  }
  
  .submit-button {
    width: 100%;
    padding: 12px 20px;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
    margin-top: 20px;
    transition: background-color 0.3s;
  }
  
  .submit-button:hover {
    background-color: #45a049;
  }
  
  .submit-button:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
  }
  
  .error {
    color: #f44336;
    padding: 10px;
    margin: 10px 0;
    background-color: #ffebee;
    border-radius: 4px;
    text-align: center;
  }
  
  .loading {
    text-align: center;
    padding: 20px;
    color: #666;
  }
  </style>
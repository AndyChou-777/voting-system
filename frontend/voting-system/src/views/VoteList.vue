<template>
    <div class="vote-list">
      <h1>投票列表</h1>
      <div class="vote-items">
        <div v-for="item in voteItems" :key="item.id" class="vote-item">
          <input 
            type="checkbox" 
            :value="item.id" 
            v-model="selectedItems"
          >
          <span>{{ item.itemName }}</span>
        </div>
      </div>
      <button @click="submitVote" :disabled="!selectedItems.length">
        提交投票
      </button>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  
  const voteItems = ref([])
  const selectedItems = ref([])
  
  // 獲取投票項目
  const fetchVoteItems = async () => {
    try {
      const response = await axios.get('/api/voting/items')
      voteItems.value = response.data
    } catch (error) {
      console.error('獲取投票項目失敗:', error)
    }
  }
  
  // 提交投票
  const submitVote = async () => {
    try {
      await axios.post('/api/voting/records', {
        userId: 1, // 這裡應該是實際的用戶ID
        voteItemIds: selectedItems.value
      })
      alert('投票成功！')
      selectedItems.value = [] // 清空選擇
    } catch (error) {
      console.error('提交投票失敗:', error)
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
    margin: 0 auto;
    padding: 20px;
  }
  
  .vote-items {
    margin: 20px 0;
  }
  
  .vote-item {
    padding: 10px;
    border-bottom: 1px solid #eee;
  }
  
  button {
    padding: 10px 20px;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  button:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
  }
  </style>
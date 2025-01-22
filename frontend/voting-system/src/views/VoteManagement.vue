<template>
    <div class="vote-management">
      <h2>投票項目管理</h2>
  
      <!-- 新增投票項目 -->
      <div class="add-item">
        <input 
          v-model="newItemName" 
          placeholder="輸入新投票項目名稱"
          :disabled="loading"
        >
        <button @click="handleAddItem" :disabled="loading || !newItemName">
          新增
        </button>
      </div>
  
      <!-- 錯誤訊息 -->
      <div v-if="error" class="error">
        {{ error }}
      </div>
  
      <!-- 載入中 -->
      <div v-if="loading" class="loading">
        載入中...
      </div>
  
      <!-- 投票項目列表 -->
      <div v-else class="item-list">
        <div v-for="item in voteItems" :key="item.id" class="item">
          <div v-if="editingId === item.id" class="edit-mode">
            <input 
              v-model="editingName" 
              :placeholder="item.itemName"
            >
            <button @click="handleUpdateItem(item.id)" class="save">保存</button>
            <button @click="cancelEdit" class="cancel">取消</button>
          </div>
          <div v-else class="view-mode">
            <span>{{ item.itemName }}</span>
            <div class="actions">
              <button @click="startEdit(item)" class="edit">編輯</button>
              <button @click="handleDeleteItem(item.id)" class="delete">刪除</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted } from 'vue'
  import { votingService } from '../api/votingService'
  
  interface VoteItem {
    id: number
    itemName: string
  }
  
  const voteItems = ref<VoteItem[]>([])
  const loading = ref(false)
  const error = ref('')
  const newItemName = ref('')
  const editingId = ref<number | null>(null)
  const editingName = ref('')
  
  // 獲取所有投票項目
  const fetchVoteItems = async () => {
    loading.value = true
    error.value = ''
    try {
      const response = await votingService.getAllVoteItems()
      console.log(response.data)
      voteItems.value = response.data
    } catch (err) {
      error.value = '獲取投票項目失敗'
      console.error('Error fetching items:', err)
    } finally {
      loading.value = false
    }
  }
  
  // 新增投票項目
  const handleAddItem = async () => {
    if (!newItemName.value) return
    
    loading.value = true
    error.value = ''
    try {
      await votingService.addVoteItem(newItemName.value)
      await fetchVoteItems() // 重新獲取列表
      newItemName.value = '' // 清空輸入
    } catch (err) {
      error.value = '新增投票項目失敗'
      console.error('Error adding item:', err)
    } finally {
      loading.value = false
    }
  }
  
  // 開始編輯
  const startEdit = (item: VoteItem) => {
    editingId.value = item.id
    editingName.value = item.itemName
  }
  
  // 取消編輯
  const cancelEdit = () => {
    editingId.value = null
    editingName.value = ''
  }
  
  // 更新投票項目
  const handleUpdateItem = async (itemId: number) => {
    if (!editingName.value) return
    
    loading.value = true
    error.value = ''
    try {
      await votingService.updateVoteItem(itemId, editingName.value)
      await fetchVoteItems()
      cancelEdit()
    } catch (err) {
      error.value = '更新投票項目失敗'
      console.error('Error updating item:', err)
    } finally {
      loading.value = false
    }
  }
  
  // 刪除投票項目
  const handleDeleteItem = async (itemId: number) => {
    if (!confirm('確定要刪除此投票項目嗎？')) return
    
    loading.value = true
    error.value = ''
    try {
      await votingService.deleteVoteItem(itemId)
      await fetchVoteItems()
    } catch (err) {
      error.value = '刪除投票項目失敗'
      console.error('Error deleting item:', err)
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
  .vote-management {
    max-width: 800px;
    margin: 20px auto;
    padding: 20px;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  }
  
  .add-item {
    display: flex;
    gap: 10px;
    margin-bottom: 20px;
  }
  
  .add-item input {
    flex: 1;
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 4px;
  }
  
  .item {
    padding: 10px;
    margin: 5px 0;
    border: 1px solid #eee;
    border-radius: 4px;
  }
  
  .view-mode {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .edit-mode {
    display: flex;
    gap: 10px;
  }
  
  .edit-mode input {
    flex: 1;
    padding: 8px;
  }
  
  .actions {
    display: flex;
    gap: 10px;
  }
  
  button {
    padding: 8px 16px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  button.save { background-color: #4CAF50; color: white; }
  button.cancel { background-color: #9e9e9e; color: white; }
  button.edit { background-color: #2196F3; color: white; }
  button.delete { background-color: #f44336; color: white; }
  
  button:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
  }
  
  .error {
    color: red;
    padding: 10px;
    margin: 10px 0;
    background-color: #ffebee;
    border-radius: 4px;
  }
  
  .loading {
    text-align: center;
    padding: 20px;
    color: #666;
  }
  </style>
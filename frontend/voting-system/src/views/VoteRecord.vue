<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { votingService } from '../api/votingService'

interface VoteRecord {
  id: number
  itemName: string
  voteDate: string
}

const voteRecords = ref<VoteRecord[]>([])
const loading = ref(true)
const error = ref('')

const fetchVoteRecords = async () => {
  try {
    loading.value = true
    const response = await votingService.getVoteRecordsByUserId()
    console.log('收到數據:', response)

    // 映射數據
    voteRecords.value = response.data.map((record: any) => ({
      id: record[0],
      itemName: record[2], // 將第3欄（項目名稱）映射到 itemName
      voteDate: record[3],  // 將第4欄（投票時間）映射到 voteDate
    }))
    console.log('轉換的數據:', voteRecords)
  } catch (err) {
    error.value = '獲取投票記錄失敗'
    console.error(err)
  } finally {
    loading.value = false
  }
}

const formatDate = (dateString: string) => {
  const date = new Date(dateString)
  return date.toLocaleString('zh-TW', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
    hour12: false,
  })
}

onMounted(() => {
  fetchVoteRecords()
})
</script>

<template>
  <div class="vote-records">
    <h1 class="text-2xl font-bold mb-6">我的投票記錄</h1>

    <div v-if="loading" class="loading-state">
      載入中...
    </div>

    <div v-else-if="error" class="error-state">
      {{ error }}
    </div>

    <div v-else-if="voteRecords.length === 0" class="empty-state">
      還沒有投票記錄
    </div>

    <div v-else class="records-table">
      <table class="w-full">
        <thead>
          <tr>
            <th class="px-4 py-2 text-left">項目名稱</th>
            <th class="px-4 py-2 text-left">投票時間</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="record in voteRecords" 
              :key="record.id"
              class="border-t">
            <td class="px-4 py-3">{{ record.itemName }}</td>
            <td class="px-4 py-3">{{ formatDate(record.voteDate) }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
.vote-records {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.loading-state,
.error-state,
.empty-state {
  text-align: center;
  padding: 40px;
  color: #666;
}

.records-table {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

table {
  border-collapse: separate;
  border-spacing: 0;
}

th {
  background: #f8f9fa;
  font-weight: 600;
}

tr:hover {
  background: #f8f9fa;
}
</style>
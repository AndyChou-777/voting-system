import api from './config'

export const votingService = {
  // 獲取所有投票項目
  getAllVoteItems() {
    return api.get('/voting/items')
  },

  // 新增投票項目
  addVoteItem(itemName: string) {
    return api.post('/voting/items', itemName)
  },

  // 更新投票項目
  updateVoteItem(itemId: number, itemName: string) {
    return api.put(`/voting/items/${itemId}`, itemName)
  },

  // 刪除投票項目
  deleteVoteItem(itemId: number) {
    return api.delete(`/voting/items/${itemId}`)
  },

  // 新增投票記錄（複選）
  addVoteRecords(userId: number, voteItemIds: number[]) {
    return api.put(`/voting/records?userId=${userId}`, voteItemIds)
  },

  // 獲取用戶投票記錄
  getVoteRecordsByUserId(userId: number) {
    return api.get(`/voting/records/user/${userId}`)
  }
}
<template>
  <el-dialog
    :title="!dataForm.auctionId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="拍卖品名称" prop="auctionName">
      <el-input v-model="dataForm.auctionName" placeholder="拍卖品名称"></el-input>
    </el-form-item>
    <el-form-item label="拍卖数量" prop="auctionAmount">
      <el-input v-model="dataForm.auctionAmount" placeholder="拍卖数量"></el-input>
    </el-form-item>
    <el-form-item label="起拍价" prop="startPrice">
      <el-input v-model="dataForm.startPrice" placeholder="起拍价"></el-input>
    </el-form-item>
    <el-form-item label="竞拍次数" prop="auctionNumber">
      <el-input v-model="dataForm.auctionNumber" placeholder="竞拍次数"></el-input>
    </el-form-item>
    <el-form-item label="最高价" prop="maxPrice">
      <el-input v-model="dataForm.maxPrice" placeholder="最高价"></el-input>
    </el-form-item>
    <el-form-item label="竞拍状态" prop="auctionState">
      <el-input v-model="dataForm.auctionState" placeholder="竞拍状态"></el-input>
    </el-form-item>
    <el-form-item label="结束时间" prop="endTime">
      <el-input v-model="dataForm.endTime" placeholder="结束时间"></el-input>
    </el-form-item>
    <el-form-item label="发布时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="发布时间"></el-input>
    </el-form-item>
    <el-form-item label="修改时间" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder="修改时间"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          auctionId: 0,
          auctionName: '',
          auctionAmount: '',
          startPrice: '',
          auctionNumber: '',
          maxPrice: '',
          auctionState: '',
          endTime: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          auctionName: [
            { required: true, message: '拍卖品名称不能为空', trigger: 'blur' }
          ],
          auctionAmount: [
            { required: true, message: '拍卖数量不能为空', trigger: 'blur' }
          ],
          startPrice: [
            { required: true, message: '起拍价不能为空', trigger: 'blur' }
          ],
          auctionNumber: [
            { required: true, message: '竞拍次数不能为空', trigger: 'blur' }
          ],
          maxPrice: [
            { required: true, message: '最高价不能为空', trigger: 'blur' }
          ],
          auctionState: [
            { required: true, message: '竞拍状态不能为空', trigger: 'blur' }
          ],
          endTime: [
            { required: true, message: '结束时间不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '发布时间不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '修改时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.auctionId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.auctionId) {
            this.$http({
              url: this.$http.adornUrl(`/product/auction/info/${this.dataForm.auctionId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.auctionName = data.auction.auctionName
                this.dataForm.auctionAmount = data.auction.auctionAmount
                this.dataForm.startPrice = data.auction.startPrice
                this.dataForm.auctionNumber = data.auction.auctionNumber
                this.dataForm.maxPrice = data.auction.maxPrice
                this.dataForm.auctionState = data.auction.auctionState
                this.dataForm.endTime = data.auction.endTime
                this.dataForm.createTime = data.auction.createTime
                this.dataForm.updateTime = data.auction.updateTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/product/auction/${!this.dataForm.auctionId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'auctionId': this.dataForm.auctionId || undefined,
                'auctionName': this.dataForm.auctionName,
                'auctionAmount': this.dataForm.auctionAmount,
                'startPrice': this.dataForm.startPrice,
                'auctionNumber': this.dataForm.auctionNumber,
                'maxPrice': this.dataForm.maxPrice,
                'auctionState': this.dataForm.auctionState,
                'endTime': this.dataForm.endTime,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>

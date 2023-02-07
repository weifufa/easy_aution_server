<template>
  <el-dialog :title="!dataForm.catId ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
      label-width="80px">
      <el-form-item label="分类名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="分类名称"></el-input>
      </el-form-item>
      <el-form-item label="是否显示" prop="showStatus">
        <el-input v-model="dataForm.showStatus" placeholder="是否显示"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
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
  data() {
    return {
      visible: false,
      dataForm: {
        catId: 0,
        name: '',
        parentCid: '',
        catLevel: '',
        showStatus: '',
        sort: '',
        icon: '',
        productUnit: '',
        productCount: ''
      },
      dataRule: {
        name: [
          { required: true, message: '分类名称不能为空', trigger: 'blur' }
        ],
        showStatus: [
          { required: true, message: '是否显示[0-不显示，1显示]不能为空', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '排序不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init(id) {
      this.dataForm.catId = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.catId) {
          this.$http({
            url: this.$http.adornUrl(`/product/category/info/${this.dataForm.catId}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.name = data.category.name
              this.dataForm.showStatus = data.category.showStatus
              this.dataForm.sort = data.category.sort
            }
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/product/category/${!this.dataForm.catId ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'catId': this.dataForm.catId || undefined,
              'name': this.dataForm.name,
              'showStatus': this.dataForm.showStatus,
              'sort': this.dataForm.sort
            })
          }).then(({ data }) => {
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

<template>
  <el-dialog
    :title="!dataForm.orderId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="80px"
    >
      <el-form-item label="拍品名称" prop="autionName">
        <el-input
          v-model="dataForm.autionName"
          placeholder="拍品名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="卖家" prop="seller">
        <el-input v-model="dataForm.seller" placeholder="卖家"></el-input>
      </el-form-item>
      <el-form-item label="卖家" prop="buyer">
        <el-input v-model="dataForm.buyer" placeholder="卖家"></el-input>
      </el-form-item>
      <el-form-item label="订单状态" prop="orderState">
        <el-input
          v-model="dataForm.orderState"
          placeholder="订单状态"
        ></el-input>
      </el-form-item>
      <el-form-item label="成交价" prop="currentPrice">
        <el-input
          v-model="dataForm.currentPrice"
          placeholder="成交价"
        ></el-input>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-input
          v-model="dataForm.createTime"
          placeholder="创建时间"
        ></el-input>
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
        orderId: 0,
        autionName: "",
        seller: "",
        buyer: "",
        orderState: "",
        currentPrice: 0,
        createTime: "",
      },
      dataRule: {
        autionName: [
          { required: true, message: "拍品名称不能为空", trigger: "blur" },
        ],
        seller: [{ required: true, message: "卖家不能为空", trigger: "blur" }],
        buyer: [{ required: true, message: "卖家不能为空", trigger: "blur" }],
        orderState: [
          { required: true, message: "订单状态不能为空", trigger: "blur" },
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    init(id) {
      this.dataForm.orderId = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.orderId) {
          this.$http({
            url: this.$http.adornUrl(
              `/product/order/info/${this.dataForm.orderId}`
            ),
            method: "get",
            params: this.$http.adornParams(),
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.currentPrice = data.order.currentPrice;
              this.dataForm.autionName = data.order.autionName;
              this.dataForm.seller = data.order.seller;
              this.dataForm.buyer = data.order.buyer;
              this.dataForm.orderState = data.order.orderState;
              this.dataForm.createTime = data.order.createTime;
            }
          });
        }
      });
    },
    // 表单提交
    dataFormSubmit() {
    //表单提交
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(
              `/product/order/${this.dataForm.orderId ? "update" : "save"}`
            ),
            method: "post",
            data: this.$http.adornData(this.dataForm),
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message.success("操作成功");
              this.visible = false;
              this.$emit("refreshDataList");
            } else {
              this.$message.error(data.msg);
            }
          });
        }
      });
    },
  },
};
</script>

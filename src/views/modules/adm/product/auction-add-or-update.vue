<template>
  <el-dialog
    :title="!dataForm.auctionId ? '新增' : '修改'"
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
      <el-form-item label="拍品名称" prop="auctionName" style="width: 400px">
        <el-input
          v-model="dataForm.auctionName"
          placeholder="拍卖品名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="文字描述" prop="remark" style="width: 400px">
        <el-input type="textarea" v-model="dataForm.remark"></el-input>
      </el-form-item>
      <el-form-item label="拍品图片">
        <multi-upload v-model="dataForm.images"></multi-upload>
      </el-form-item>
      <el-form-item label="拍卖数量" prop="auctionAmount">
        <el-input-number
          v-model="dataForm.auctionAmount"
          :min="1"
          :max="10"
          :step="1"
        ></el-input-number>
      </el-form-item>
      <el-form-item label="起拍价" prop="startPrice" style="width: 280px">
        <el-input
          v-model="dataForm.startPrice"
          type="number"
          :min="0"
          :max="9999.99"
          :step="100"
          placeholder="请输入价格"
        ></el-input>
      </el-form-item>
      <el-form-item label="开拍时间" prop="auctionStartTime">
        <el-date-picker
          v-model="dataForm.auctionStartTime"
          type="datetime"
          placeholder="选择日期时间"
          @change="startTimeChange"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="auctionEndTime">
        <el-date-picker
          v-model="dataForm.auctionEndTime"
          type="datetime"
          placeholder="选择日期时间"
          @change="endTimeChange"
        >
        </el-date-picker>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import MultiUpload from "@/components/upload/multiUpload";
export default {
  components: { MultiUpload },
  data() {
    return {
      visible: false,
      dataForm: {
        remark: "",
        images: [],
        auctionId: 0,
        auctionName: "",
        auctionAmount: "",
        startPrice: "",
        auctionNumber: "",
        maxPrice: "",
        auctionState: "",
        auctionEndTime: "",
        auctionStartTime: "",
      },
      dataRule: {
        auctionName: [
          { required: true, message: "拍卖品名称不能为空", trigger: "blur" },
        ],
        auctionAmount: [
          { required: true, message: "拍卖数量不能为空", trigger: "blur" },
        ],
        startPrice: [
          { required: true, message: "起拍价不能为空", trigger: "blur" },
        ],
        auctionEndTime: [
          { required: true, message: "请选择结束时间", trigger: "blur" },
        ],
        auctionStartTime: [
          { required: true, message: "请选择开始时间", trigger: "blur" },
        ],
        remark: [
          { required: true, message: "请填写拍品说明", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    init(id) {
      this.dataForm.auctionId = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        // this.$refs["dataForm"].resetFields();
        if (this.dataForm.auctionId) {
          this.$http({
            url: this.$http.adornUrl(
              `/product/auction/info/${this.dataForm.auctionId}`
            ),
            method: "get",
            params: this.$http.adornParams(),
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.auctionName = data.auction.auctionName;
              this.dataForm.auctionAmount = data.auction.auctionAmount;
              this.dataForm.startPrice = data.auction.startPrice;
              this.dataForm.auctionNumber = data.auction.auctionNumber;
              this.dataForm.maxPrice = data.auction.maxPrice;
              this.dataForm.auctionState = data.auction.auctionState;
              this.dataForm.endTime = data.auction.endTime;
              this.dataForm.createTime = data.auction.createTime;
              this.dataForm.updateTime = data.auction.updateTime;
            }
          });
        }
      });
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(
              `/product/auction/${!this.dataForm.auctionId ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              auctionId: this.dataForm.auctionId || undefined,
              auctionName: this.dataForm.auctionName,
              auctionAmount: this.dataForm.auctionAmount,
              startPrice: this.dataForm.startPrice,
              auctionNumber: this.dataForm.auctionNumber,
              maxPrice: this.dataForm.maxPrice,
              auctionState: this.dataForm.auctionState,
              auctionEndTime: this.dataForm.auctionEndTime,
              auctionStartTime: this.dataForm.auctionStartTime,
              remark: this.dataForm.remark,
              images: JSON.stringify(this.dataForm.images),
            }),
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.visible = false;
                  this.$emit("refreshDataList");
                },
              });
            } else {
              this.$message.error(data.msg);
            }
          });
        }
      });
    },
    startTimeChange() {
      if (
        this.dataForm.auctionEndTime &&
        this.dataForm.auctionStartTime >= this.dataForm.auctionEndTime
      ) {
        debugger;
        this.$message.error("开始时间不能大于等结束时间");
        this.dataForm.auctionStartTime = "";
      }
    },
    endTimeChange() {
      if (
        this.dataForm.auctionStartTime &&
        this.dataForm.auctionEndTime <= this.dataForm.auctionStartTime
      ) {
        debugger;
        this.$message.error("结束时间不能小于等于开始时间");
        this.dataForm.auctionEndTime = "";
      }
    },
  },
};
</script>

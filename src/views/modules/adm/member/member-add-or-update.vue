<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="450px"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="80px"
    >
      <el-form-item label="用户名" prop="username" style="width: 280px">
        <el-input v-model="dataForm.username" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item label="头像" prop="header" style="width: 280px">
        <single-upload
          :imageUrl="dataForm.header"
          @input="getHeader"
        ></single-upload>
      </el-form-item>
      <el-form-item label="手机号" prop="phone" style="width: 280px">
        <el-input v-model="dataForm.phone" placeholder="手机号"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email" style="width: 280px">
        <el-input v-model="dataForm.email" placeholder="邮箱"></el-input>
      </el-form-item>
      <el-form-item label="性别" style="width: 280px">
        <el-select v-model="dataForm.gender" placeholder="请选择性别">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import SingleUpload from "@/components/upload/singleUpload.vue";
export default {
  components: { SingleUpload },
  data() {
    var validateUserName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("用户名不能为空"));
      } else if (!/\d{3}/.test(value)) {
        return callback(new Error("用户名长度必须大于3"));
      }
      return callback();
    };
    var validateEmail = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("邮箱不能为空"));
      } else if (!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value)) {
        return callback(new Error("请填写正确邮箱"));
      }
      return callback();
    };
    var validatePhone = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("手机号不能为空"));
      } else if (!/0?(13|14|15|18)[0-9]{9}/.test(value)) {
        return callback(new Error("手机号不正确"));
      }
      return callback();
    };
    return {
      visible: false,
      options: [
        {
          value: 0,
          label: "男",
        },
        {
          value: 1,
          label: "女",
        },
      ],
      dataForm: {
        id: 0,
        username: "",
        password: "",
        nickname: "",
        phone: "",
        email: "",
        gender: "",
        header: "",
      },
      dataRule: {
        username: [
          {
            trigger: "blur",
            validator: validateUserName,
          },
        ],
        email: [
          {
            trigger: "blur",
            validator: validateEmail,
          },
        ],
        gender: [{ required: true, message: "请选择性别", trigger: "blur" }],
        phone: [
          {
            trigger: "blur",
            validator: validatePhone,
          },
        ],
      },
    };
  },
  methods: {
    init(id) {
      this.dataForm.id = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/member/info/${this.dataForm.id}`),
            method: "get",
            params: this.$http.adornParams(),
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.username = data.Member.username;
              this.dataForm.nickname = data.Member.nickname;
              this.dataForm.phone = data.Member.phone;
              this.dataForm.email = data.Member.email;
              this.dataForm.gender = data.Member.gender;
              this.dataForm.header = data.Member.header;
            }
          });
        }
      });
    },
    // 表单提交
    dataFormSubmit() {
      debugger;
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(
              `/member/${!this.dataForm.id ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              id: this.dataForm.id || undefined,
              username: this.dataForm.username,
              phone: this.dataForm.phone,
              email: this.dataForm.email,
              gender: this.dataForm.gender,
              header: this.dataForm.header,
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
        } else {
          console.log("失败");
        }
      });
    },
    getHeader(val) {
      this.dataForm.header = val;
    },
  },
};
</script>

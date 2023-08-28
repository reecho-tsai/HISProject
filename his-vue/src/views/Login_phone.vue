<template>
  <div class="login_container">
    <div class="login_box">
      <p class="login_title">欢迎使用云医院HIS服务平台</p>
      <!-- 表单区域 -->
      <el-form ref="phoneFormRef" :model="loginForm" :rules="loginFormRules" label-width="0px" class="login_form">
        <!-- 用户名 -->
        <el-form-item prop="telephone">
          <el-input v-model="loginForm.telephone" prefix-icon="el-icon-phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item prop="code">
          <el-input v-model="loginForm.code" prefix-icon="el-icon-lock" placeholder="请输入验证码"></el-input>
        </el-form-item>
        <!-- 按钮区域 -->
        <el-form-item class="btns">
          <el-button type="info" @click="sendCode">发送验证码</el-button>
          <el-button type="primary" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    // 验证手机号的规则
    var checkMobile = (rule, value, cb) => {
      // 验证手机号的正则表达式
      const regMobile = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/

      if (regMobile.test(value)) {
        return cb()
      }

      cb(new Error('请输入合法的手机号'))
    }
    return {
      // 这是登录表单的数据绑定对象 超级管理员
      loginForm: {
        telephone: '15243456759',
        code: ''
      },
      // 体检室 13831562475 密码123456
      // 体检室专家 13839562475
      // 这是表单的验证规则对象
      loginFormRules: {
        // 验证手机号是否合法
        telephone: [
          { required: true, message: '请输入11位手机号', trigger: 'blur' },
          { validator: checkMobile, trigger: 'blur' }
        ],
        // 验证密码是否合法
        password: [
          { required: true, message: '请输入登录密码', trigger: 'blur' },
          { min: 4, max: 15, message: '长度在 4 到 15 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 点击重置按钮，重置登录表单
    resetLoginForm () {
      // console.log(this);
      this.$refs.phoneFormRef.resetFields()
    },
    login () {
      this.$refs.phoneFormRef.validate(async valid => {
        if (!valid) return
        if (this.loginForm.telephone !== window.sessionStorage.getItem('telephone') || this.loginForm.code !== window.sessionStorage.getItem('code')) {
          this.$message({
            message: '验证码错误',
            type: 'error'
          })
          return
        }
        this.$axios.post('/loginByCode',
          response => {
            window.sessionStorage.setItem('token', response.data.token)
            window.sessionStorage.setItem('id', response.data.id)
            window.sessionStorage.setItem('deptId', response.data.deptId)
            this.$router.push('/home')
          },
          this.loginForm
        )
      })
    },
    sendCode () {
      this.$refs.phoneFormRef.validate(async valid => {
        if (!valid) return
        console.log(this.loginForm.telephone)
        this.$axios.post('/sendCode',
          response => {
            window.sessionStorage.setItem('telephone', response.data.telephone)
            window.sessionStorage.setItem('code', response.data.code)
            // this.$router.push('/home')
          },
          this.loginForm
        )
      })
    }
  }
}
</script>

<style lang="less" scoped>
.login_container {
  background-color: #EBEEF5;
  height: 100%;
}

.login_title {
  text-align: center;
  font-size:20px;
  font-weight:bold;
}

.login_box {
  width: 450px;
  height: 250px;
  background-color: #fff;
  border-radius: 10px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.login_form {
  //position: absolute;
  //bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}

.btns {
  display: flex;
  justify-content: flex-end;
}
</style>

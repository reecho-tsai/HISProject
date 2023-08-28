<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" :disabled="isupdate"></el-input>
      </el-form-item>
      <el-form-item label="密码" >
        <el-input type="password" v-model="form.password" show-password  clearable></el-input>
      </el-form-item>
      <el-form-item label="真实姓名" prop="realname">
        <el-input  v-model="form.realname" ></el-input>
      </el-form-item>
      <el-form-item label="电话号码" prop="telephone">
        <el-input v-model="form.telephone" ></el-input>
      </el-form-item>

      <el-form-item label="用户类型">
        <el-select v-model="form.userType" placeholder="请选择" :popper-append-to-body="false">
          <el-option
            v-for="(role, id) in roles"
            :key="id"
            :label="role.name"
            :value="id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="归属科室">
        <el-select v-model="form.deptId" placeholder="请选择" :popper-append-to-body="false">
          <el-option
            v-for="(value, id) in department"
            :key="id"
            :label="value.name"
            :value="id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="工作时间" prop="wtime">
        <el-checkbox-group v-model="form.type">
           <el-tag style="width:54px;">周一</el-tag><el-tag style="width:54px;">周二</el-tag><el-tag style="width:54px;">周三</el-tag>
           <el-tag style="width:54px;">周四</el-tag><el-tag style="width:54px;">周五</el-tag><el-tag style="width:54px;">周六</el-tag>
           <el-tag style="width:54px;">周日</el-tag>
           <br/>
           <el-checkbox label="1" name="type" style="width:23px;height:15px;font-size:12px;border-left: 0;">上午</el-checkbox>
           <el-checkbox label="3" name="type" style="width:23px;height:15px;font-size:12px;border-left: 0;">上午</el-checkbox>
           <el-checkbox label="5" name="type" style="width:23px;height:15px;font-size:12px;border-left: 0;">上午</el-checkbox>
           <el-checkbox label="7" name="type" style="width:23px;height:15px;font-size:12px;border-left: 0;">上午</el-checkbox>
           <el-checkbox label="9" name="type" style="width:23px;height:15px;font-size:12px;border-left: 0;">上午</el-checkbox>
           <el-checkbox label="11" name="type" style="width:23px;height:15px;font-size:12px;border-left: 0;">上午</el-checkbox>
           <el-checkbox label="13" name="type" style="width:23px;height:15px;font-size:12px;border-left: 0;">上午</el-checkbox>

           <el-checkbox label="2" name="type" style="width:23px;height:15px;font-size:12px;border-left: 0;">下午</el-checkbox>
           <el-checkbox label="4" name="type" style="width:23px;height:15px;font-size:12px;border-left: 0;">下午</el-checkbox>
           <el-checkbox label="6" name="type" style="width:23px;height:15px;font-size:12px;border-left: 0;">下午</el-checkbox>
           <el-checkbox label="8" name="type" style="width:23px;height:15px;font-size:12px;border-left: 0;">下午</el-checkbox>
           <el-checkbox label="10" name="type" style="width:23px;height:15px;font-size:12px;border-left: 0;">下午</el-checkbox>
           <el-checkbox label="12" name="type" style="width:23px;height:15px;font-size:12px;border-left: 0;">下午</el-checkbox>
           <el-checkbox label="14" name="type" style="width:23px;height:15px;font-size:12px;border-left: 0;">下午</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" plain @click="save">保存数据</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'UserEdit',
  props: ['editid'],
  data () {

    var checkName = (rule, value, cb) => {
      if (this.editid) cb()
      const promise = this.$axios.http.get('/users/check', { params: { name: value } })
      promise.then(
        response => {
          if (response.data.status !== 200) {
            cb(new Error('该用户名已存在'))
          }
          cb()
        }
      )
    }
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
      form: {
        username: '',
        realname: '',
        password: '',
        telephone: '',
        userType: '',
        deptId: '',
        worktime0: '',
        numbmax:10,
        type : [ ],
      },
      worktime1: '',
      isupdate: false,
      rules: {
        // 验证用户名是否合法
        username: [
          { required: true, message: '请输入登录名称', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 10 个字符', trigger: 'blur' },
          { validator: checkName, trigger: 'blur' }
        ],
        // 验证手机号是否合法
        telephone: [
          { required: true, message: '请输入11位手机号', trigger: 'blur' },
          { validator: checkMobile, trigger: 'blur' }
        ],
        // 验证真实姓名
        realname: [
          { required: true, message: '请输入真实姓名', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    if (this.editid) {
      // 如果是修改 用户名输入框禁用
      this.isupdate = true
      // 通过id 读原始数据
      this.$axios.get(`/users/${this.editid}`, response => {
        this.form.username = response.data.username
        this.form.password = response.data.password
        this.form.realname = response.data.realname
        this.form.telephone = response.data.telephone
        this.worktime1 = response.data.worktime0
        this.form.userType = String(response.data.userType)
        if (response.data.deptId !== 0) {
          this.form.deptId = String(response.data.deptId)
        }
      })
    }
    this.$store.dispatch('getRoles')
    this.$store.dispatch('getDepartment')
  },
  computed: {
    ...mapState(['roles', 'department'])
  },
  methods: {
    save () {
      let i=0;
      for(i=0;i<14;i++){
        if(this.form.type[i]==='1'){
          this.form.worktime0 =this.form.worktime0+'1a';
        }
        else if(this.form.type[i]==='2'){
          this.form.worktime0=this.form.worktime0+'1b';
        }
        else if(this.form.type[i]==='3'){
          this.form.worktime0=this.form.worktime0+'2a';
        }
        else if(this.form.type[i]==='4'){
          this.form.worktime0=this.form.worktime0+'2b';
        }
        else if(this.form.type[i]==='5'){
          this.form.worktime0=this.form.worktime0+'3a';
        }
        else if(this.form.type[i]==='6'){
          this.form.worktime0=this.form.worktime0+'3b';
        }
        else if(this.form.type[i]==='7'){
          this.form.worktime0=this.form.worktime0+'4a';
        }
        else if(this.form.type[i]==='8'){
          this.form.worktime0=this.form.worktime0+'4b';
        }
        else if(this.form.type[i]==='9'){
          this.form.worktime0=this.form.worktime0+'5a';
        }
        else if(this.form.type[i]==='10'){
          this.form.worktime0=this.form.worktime0+'5b';
        }
        else if(this.form.type[i]==='11'){
          this.form.worktime0=this.form.worktime0+'6a';
        }
        else if(this.form.type[i]==='12'){
          this.form.worktime0=this.form.worktime0+'6b';
        }
        else if(this.form.type[i]==='13'){
          this.form.worktime0=this.form.worktime0+'7a';
        }
        else if(this.form.type[i]==='14'){
          this.form.worktime0=this.form.worktime0+'7b';
        }
      }
      if(this.form.worktime0==''){
        this.form.worktime0 = this.worktime1;
      }
      if(this.form.password!=''){
      console.log(this.form.worktime0);
      this.$refs.form.validate(valid => {
        if (!valid) return
        if (this.editid) {
          this.$axios.put(`/users/${this.editid}`, response => {
            // 子组件要改变父组件的值
            this.$emit('update:show', false)
            this.$emit('getData')
          }, this.form)
        } else {
          this.$axios.post('/users', response => {
            // 子组件要改变父组件的值
            this.$emit('update:show', false)
            this.$emit('getData')
          }, this.form)
        }
      })
      }else {
         this.$message.error('请先输入密码')
       }
    }
  }
}
</script>

<style scoped>
.el-select-dropdown {
  position: absolute;
  top: 38px !important;
  left: 0 !important;
}
</style>

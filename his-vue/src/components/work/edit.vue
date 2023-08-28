<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="医生" prop="name">
        <el-input v-model="form.name" :disabled="isupdate"></el-input>
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
      <el-form-item label="最大人数" prop="numbMax">
        <el-input  v-model="form.numbMax" ></el-input>
      </el-form-item>
      <el-form-item label="工作时间" prop="wtime">
        <el-radio-group v-model="form.wtime">
           <el-tag style="width:54px;">周一</el-tag><el-tag style="width:54px;">周二</el-tag><el-tag style="width:54px;">周三</el-tag>
           <el-tag style="width:54px;">周四</el-tag><el-tag style="width:54px;">周五</el-tag><el-tag style="width:54px;">周六</el-tag>
           <el-tag style="width:54px;">周日</el-tag>
           <br/>
           <el-radio :label="1" style="width:23px;height:15px;font-size:12px;border-left: 0;">上午</el-radio>
           <el-radio :label="3" style="width:23px;height:15px;font-size:12px;border-left: 0;">上午</el-radio>
           <el-radio :label="5" style="width:23px;height:15px;font-size:12px;border-left: 0;">上午</el-radio>
           <el-radio :label="7" style="width:23px;height:15px;font-size:12px;border-left: 0;">上午</el-radio>
           <el-radio :label="9" style="width:23px;height:15px;font-size:12px;border-left: 0;">上午</el-radio>
           <el-radio :label="11" style="width:23px;height:15px;font-size:12px;border-left: 0;">上午</el-radio>
           <el-radio :label="13" style="width:23px;height:15px;font-size:12px;border-left: 0;">上午</el-radio>

           <el-radio :label="2" style="width:23px;height:15px;font-size:12px;border-left: 0;">下午</el-radio>
           <el-radio :label="4" style="width:23px;height:15px;font-size:12px;border-left: 0;">下午</el-radio>
           <el-radio :label="6" style="width:23px;height:15px;font-size:12px;border-left: 0;">下午</el-radio>
           <el-radio :label="8" style="width:23px;height:15px;font-size:12px;border-left: 0;">下午</el-radio>
           <el-radio :label="10" style="width:23px;height:15px;font-size:12px;border-left: 0;">下午</el-radio>
           <el-radio :label="12" style="width:23px;height:15px;font-size:12px;border-left: 0;">下午</el-radio>
           <el-radio :label="14" style="width:23px;height:15px;font-size:12px;border-left: 0;">下午</el-radio>
        </el-radio-group>
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
    return {
      form: {
        name: '',
        userType: '',
        deptId: '',
        workTime0: '',
        numbMax:10,
        numb:0,
        wtime:'',
        type : [ ],
      },
      isupdate: false,
    }
  },
  created () {
    if (this.editid) {
      // 如果是修改 用户名输入框禁用
      this.isupdate = true
      // 通过id 读原始数据
      this.$axios.get(`/works/${this.editid}`, response => {
        this.form.name = response.data.name
        this.form.numbMax = response.data.numbMax
        this.form.workTime0 = response.data.workTime0

        this.form.userType = String(response.data.userType)
        if (response.data.deptId !== 0) {
          this.form.deptId = String(response.data.deptId)
        }
        if(this.form.workTime0==='1a'){
          this.form.wtime=1;
        }
        else if(this.form.workTime0==='1b'){
          this.form.wtime=2;
        }
        else if(this.form.workTime0==='2a'){
          this.form.wtime=3;
        }
        else if(this.form.workTime0==='2b'){
          this.form.wtime=4;
        }
        else if(this.form.workTime0==='3a'){
          this.form.wtime=5;
        }
        else if(this.form.workTime0==='3b'){
          this.form.wtime=6;
        }
        else if(this.form.workTime0==='4a'){
          this.form.wtime=7;
        }
        else if(this.form.workTime0==='4b'){
          this.form.wtime=8;
        }
        else if(this.form.workTime0==='5a'){
          this.form.wtime=9;
        }
        else if(this.form.workTime0==='5b'){
          this.form.wtime=10;
        }
        else if(this.form.workTime0==='6a'){
          this.form.wtime=11;
        }
        else if(this.form.workTime0==='6b'){
          this.form.wtime=12;
        }
        else if(this.form.workTime0==='7a'){
          this.form.wtime=13;
        }
        else if(this.form.workTime0==='7b'){
          this.form.wtime=14;
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
      console.log(this.form.workTime0);
      if(this.form.wtime===1){
        this.form.workTime0 ='1a';
      }
      else if(this.form.wtime===2){
        this.form.workTime0='1b';
      }
      else if(this.form.wtime===3){
        this.form.workTime0='2a';
      }
      else if(this.form.wtime===4){
        this.form.workTime0='2b';
      }
      else if(this.form.wtime===5){
        this.form.workTime0='3a';
      }
      else if(this.form.wtime===6){
        this.form.workTime0='3b';
      }
      else if(this.form.wtime===7){
        this.form.workTime0='4a';
      }
      else if(this.form.wtime===8){
        this.form.workTime0='4b';
      }
      else if(this.form.wtime===9){
        this.form.workTime0='5a';
      }
      else if(this.form.wtime===10){
        this.form.workTime0='5b';
      }
      else if(this.form.wtime===11){
        this.form.workTime0='6a';
      }
      else if(this.form.wtime===12){
        this.form.workTime0='6b';
      }
      else if(this.form.wtime===13){
        this.form.workTime0='7a';
      }
      else if(this.form.wtime===14){
        this.form.workTime0='7b';
      }

      this.$refs.form.validate(valid => {
        if (!valid) return
        if (this.editid) {
          this.$axios.put(`/works/${this.editid}`, response => {
            // 子组件要改变父组件的值
            this.$emit('update:show', false)
            this.$emit('getData')
          }, this.form)
        } else {
          this.$axios.post('/works', response => {
            // 子组件要改变父组件的值
            this.$emit('update:show', false)
            this.$emit('getData')
          }, this.form)
        }
      })

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
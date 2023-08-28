<template>
  <div>
    <el-form ref="form" :rules="rules" :model="form" label-width="80px">
      <el-form-item label="权限名"  prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="请求地址"  prop="url">
        <el-input v-model="form.url"></el-input>
      </el-form-item>
      <el-form-item label="类别"  prop="type">
        <el-radio-group v-model="form.type" size="small">
          <el-radio-button :label="0">目录</el-radio-button>
          <el-radio-button :label="1">页面</el-radio-button>
          <!-- <el-radio-button :label="2">按钮</el-radio-button> -->
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" plain @click="save">保存数据</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  props: ['editid', 'parentId'],
  data () {
    var checkName = (rule, value, cb) => {
      if (this.editid) cb()
      const promise = this.$axios.http.get('/permissions/check', { params: { name: value } })
      promise.then(
        response => {
          if (response.data.status !== 200) {
            cb(new Error('该权限名称已存在'))
          }
          cb()
        }
      )
    }
    return {
      form: {
        name: '',
        url: '',
        type: 0,
        parentId: 0
      },
      rules: {
        name: [
          { required: true, message: '权限名不能为空', trigger: 'blur' },
          { validator: checkName, trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    if (this.editid) {
      // 通过id 读原始数据
      this.$axios.get(`/permissions/${this.editid}`, response => {
        this.form.name = response.data.name
        this.form.url = response.data.url
        this.form.type = response.data.type
        this.form.parentId = response.data.parentId
      })
    } else {
      this.form.parentId = this.parentId
      if (this.parentId !== 0) {
        this.form.type = 1
      }
    }
  },
  methods: {
    save () {
      this.$refs.form.validate(valid => {
        if (!valid) return
        if (this.editid) {
          this.$axios.put(`/permissions/${this.editid}`, response => {
            // 子组件要改变父组件的值
            this.$emit('update:show', false)
            this.$emit('getData')
          }, this.form)
        } else {
          this.$axios.post('/permissions', response => {
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

<style>
</style>

<template>
  <div>
    <el-form ref="form" :rules="rules" :model="form" label-width="120px">
      <el-form-item label="状态码类别代码"  prop="code">
        <el-input v-model="form.code"></el-input>
      </el-form-item>
      <el-form-item label="状态码类别名"  prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" plain @click="save">保存数据</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'ConstantTypeEdit',
  props: ['editid'],
  data () {
    // code 必须唯一的
    var checkCode = (rule, value, cb) => {
      if (this.editid) cb()
      const promise = this.$axios.http.get('/constantTypes/check', { params: { name: value } })
      promise.then(
        response => {
          if (response.data.status !== 200) {
            cb(new Error('该状态码类别代码已存在'))
          }
          cb()
        }
      )
    }
    return {
      form: {
        code: '',
        name: ''
      },
      rules: {
        code: [
          { required: true, message: '编码不能为空', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' },
          { validator: checkCode, trigger: 'blur' }
        ],
        name: [
          { required: true, message: '类别名不能为空', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    if (this.editid) {
      // 通过id 读原始数据
      this.$axios.get(`/constantTypes/${this.editid}`, response => {
        this.form.code = response.data.code
        this.form.name = response.data.name
      })
    }
  },
  methods: {
    save () {
      this.$refs.form.validate((valid) => {
        if (!valid) return
        if (this.editid) {
          this.$axios.put(`/constantTypes/${this.editid}`, response => {
            // 子组件要改变父组件的值
            this.$emit('update:show', false)
            this.$emit('getData')
          }, this.form)
        } else {
          this.$axios.post('/constantTypes', response => {
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

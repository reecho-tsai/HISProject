<template>
  <div>
    <el-form ref="form" :rules="rules" :model="form" label-width="120px">
      <el-form-item label="检验项目名称"  prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="检验内容"  prop="name">
        <el-input v-model="form.content"></el-input>
      </el-form-item>
      <el-form-item label="费用单价"  prop="fee">
        <el-input v-model="form.fee"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" plain @click="save">保存数据</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'InspectItemEdit',
  props: ['editid'],
  data () {
    return {
      options: [],
      form: {
        name: '',
        fee: '',
        content: ''
      },
      rules: {
        fee: [
          { required: true, message: '费用不能为空', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    if (this.editid) {
      // 通过id 读原始数据
      this.$axios.get(`/inspectItems/${this.editid}`, response => {
        this.form.content = response.data.content
        this.form.name = response.data.name
        this.form.fee = response.data.fee
      })
    }
  },
  methods: {
    save () {
      this.$refs.form.validate(valid => {
        if (!valid) return
        if (this.editid) {
          this.$axios.put(`/inspectItems/${this.editid}`, response => {
            // 子组件要改变父组件的值
            this.$emit('update:show', false)
            this.$emit('getData')
          }, this.form)
        } else {
          this.$axios.post('/inspectItems', response => {
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

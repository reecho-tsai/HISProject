<template>
  <div>
    <el-form ref="form" :rules="rules" :model="form" label-width="120px">
      <el-form-item label="状态码类别" prop="typeId">
        <el-select v-model="form.typeId" placeholder="状态码类别">
          <el-option v-for="(item, id) in constantTypes"
          :key="id"
          :label="item.name"
          :value="id">
          </el-option>
        </el-select>

      </el-form-item>

      <el-form-item label="状态代码" prop="code">
        <el-input v-model="form.code"></el-input>
      </el-form-item>

      <el-form-item label="状态名称" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="排序序号" prop="sort">
        <el-input v-model="form.sort"></el-input>
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
  name: 'ConstantItemEdit',
  props: ['editid'],
  data () {
    return {
      form: {
        typeId: '',
        name: '',
        code: '',
        sort: ''
      },
      rules: {
        code: [
          { required: true, message: '编码不能为空', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '类别名不能为空', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    ...mapState(['constantTypes'])
  },
  created () {
    this.$store.dispatch('getConstantTypes')
    if (this.editid) {
      // 通过id 读原始数据
      this.$axios.get(`/constantItems/${this.editid}`, response => {
        this.form.typeId = String(response.data.typeId)
        this.form.code = response.data.code
        this.form.name = response.data.name
        this.form.sort = response.data.sort
      })
    }
  },
  methods: {
    save () {
      this.$refs.form.validate(valid => {
        if (!valid) return
        if (this.editid) {
          this.$axios.put(`/constantItems/${this.editid}`, response => {
            // 子组件要改变父组件的值
            this.$emit('update:show', false)
            this.$emit('getData')
          }, this.form)
        } else {
          this.$axios.post('/constantItems', response => {
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

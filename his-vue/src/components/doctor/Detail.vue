<template>
  <div>
    <el-card class="box-card">
      <div class="text item">
        <el-tag>健康描述: </el-tag>
        {{form.readme}}
      </div>
      <div class="text item">
        <el-tag>现病史: </el-tag>
        {{form.present}}
      </div>
      <div class="text item">
        <el-tag>现病史治疗情况: </el-tag>
        {{form.presentTreat}}
      </div>
      <div class="text item">
        <el-tag>既往史: </el-tag>
        {{form.history}}
      </div>
      <div class="text item">
        <el-tag>过敏史: </el-tag>
        {{form.allergy}}
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  props: ['register_id'],
  data () {
    return {
      form: {}
    }
  },
  mounted () {
    this.$axios.get(`/registers/${this.register_id}`, (response) => {
      this.form = response.data
    })
  }
}
</script>

<style scoped>
  .text {
    font-size: 14px;
  }

  .item {
    padding: 18px 0;
  }

  .box-card {
    width: 480px;
  }
</style>

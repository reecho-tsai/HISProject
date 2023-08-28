<template>
  <div>
    <el-row>
      <el-divider content-position="left">用户信息</el-divider>
      病例号：<el-tag>{{form.id}}</el-tag>
      姓名：<el-tag>{{form.name}}</el-tag>
      身份证号:  <el-tag>{{form.idno}}</el-tag>
    </el-row>
    <el-tabs type="border-card">
      <el-tab-pane value='1' label="病例情况">
        <el-collapse>
          <el-collapse-item title="健康描述" name="1">
           {{form.readme}}
          </el-collapse-item>
          <el-collapse-item title="现病史" name="2">
           {{form.present}}
          </el-collapse-item>
          <el-collapse-item title="现病史治疗情况" name="3">
            {{form.presentTreat}}
          </el-collapse-item>
          <el-collapse-item title="既往史" name="4">
            {{form.history}}
          </el-collapse-item>
          <el-collapse-item title="过敏史" name="5">
            {{form.allergy}}
          </el-collapse-item>
        </el-collapse>
      </el-tab-pane>
      <el-tab-pane label="检查申请">
        <CheckList :caseId="form.id"></CheckList>
      </el-tab-pane>
      <el-tab-pane label="检验申请">
        <InspectList :caseId="form.id"></InspectList>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import CheckList from './CheckList'
import InspectList from './InspectList'
export default {
  props: ['register_id'],
  data () {
    return {
      form: {}
    }
  },
  components: {
    CheckList,
    InspectList
  },
  created () {
    this.$axios.get(`/registers/${this.register_id}`, (response) => {
      this.form = response.data
    })
  },
  methods: {}
}
</script>

<style scoped>
.el-tag  {
  margin-right: 10px;
}
.el-tabs {
  margin-top: 10px;
}
</style>

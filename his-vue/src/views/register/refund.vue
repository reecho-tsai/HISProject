<template>
  <div>
    <Breadcrumb msg1="挂号收费" msg2="退费" />

    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="10">
        <el-col :span="10">
          <el-input v-model="id" placeholder="请输入病例号" clearable>
            <el-button slot="append" icon="el-icon-search" @click="getData"></el-button>
          </el-input>
        </el-col>
        <el-col :span="14">
          <el-button type="primary" icon="el-icon-circle-plus-outline" plain @click="refund()">退费</el-button>
        </el-col>
      </el-row>

      <el-row>
         <el-divider content-position="left">用户信息</el-divider>
          姓名：<el-tag>{{form.name}}</el-tag>
          性别： <el-tag>{{form.gender===0?'男':'女'}}</el-tag>
          身份证号:  <el-tag>{{form.idno}}</el-tag>
          退费金额：<el-tag type="success">{{fee}}</el-tag>
      </el-row>

      <el-divider content-position="left">检查费用列表</el-divider>

      <el-table
        :data="checkTableData"
        style="width: 100%"
        @selection-change="checkSelectChange"
      >
        <el-table-column type="selection"></el-table-column>
        <el-table-column type="index"> </el-table-column>
        <el-table-column label="项目名称" prop="itemName"></el-table-column>
        <el-table-column label="检查费用" prop="fee"></el-table-column>
        <el-table-column label="创建时间" prop="createtime"></el-table-column>
        <el-table-column label="状态">
          <template slot-scope="scope">
            {{check_status[scope.row.status]}}
          </template>
        </el-table-column>
      </el-table>

      <el-divider content-position="left">检验费用列表</el-divider>

      <el-table
        :data="inspectTableData"
        style="width: 100%"
        @selection-change="InspectSelectChange"
      >
        <el-table-column type="selection"></el-table-column>
        <el-table-column type="index"> </el-table-column>
        <el-table-column label="项目名称" prop="itemName"></el-table-column>
        <el-table-column label="检验费用" prop="fee"></el-table-column>
        <el-table-column label="创建时间" prop="createtime"></el-table-column>
        <el-table-column label="状态">
          <template slot-scope="scope">
            {{inspect_status[scope.row.status]}}
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import Breadcrumb from '@/components/Breadcrumb'
import { mapState } from 'vuex'
export default {
  data () {
    return {
      id: '',
      form: {},
      checkTableData: [],
      inspectTableData: [],
      checkSel: [],
      inspectSel: [],
      checkFee: 0,
      inspectFee: 0
    }
  },
  components: {
    Breadcrumb
  },
  computed: {
    ...mapState(['check_status', 'inspect_status']),
    fee: function () {
      return this.checkFee + this.inspectFee
    }
  },
  methods: {
    getData () {
      if (!this.id) {
        this.$message.error('请输入正确的病例号')
      } else {
        this.getCase(parseInt(this.id))
      }
    },
    getCase (id) {
      this.$axios.http.get(
        `/registers/${id}`
      ).then(repsonse => {
        if (repsonse.data.status !== 200) {
          Promise.reject(repsonse.data.message)
        } else {
          this.form = repsonse.data.data
          return this.$axios.http.get(`/checkApplys/list/${id}?status=2`)
        }
      }).then(repsonse => {
        this.checkTableData = repsonse.data.data
        return this.$axios.http.get(`/inspectApplys/list/${id}?status=2`)
      }).then(repsonse => {
        this.inspectTableData = repsonse.data.data
        this.$message.success('查询成功')
      }).catch(error => {
        this.$message.error('' + error)
      })
    },
    checkSelectChange (val) {
      this.checkSel = val
      this.checkFee = this.checkSel.map(val => val.fee).reduce((acc, val) => acc + val, 0)
    },
    InspectSelectChange (val) {
      this.inspectSel = val
      this.inspectFee = this.inspectSel.map(val => val.fee).reduce((acc, val) => acc + val, 0)
    },
    refund () {
      if (this.fee === 0) {
        this.$message('没有任何被选中的数据')
        return
      }
      if (this.checkFee !== 0) {
        const ids = this.checkSel.map(val => val.id)
        this.$axios.put('/checkApplys/refund', () => { this.getData() }, { ids: ids.join(',') })
      }
      if (this.inspectFee !== 0) {
        const ids = this.inspectSel.map(val => val.id)
        this.$axios.put('/inspectApplys/refund', () => { this.getData() }, { ids: ids.join(',') })
      }
      this.getData()
    }
  }
}
</script>
<style scoped>
.el-divider {
  margin-top: 30px;
}
.el-tag {
  margin-right: 10px;
  margin-left: 5px;
}
</style>

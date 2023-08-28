<template>
  <div>
    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="10">
        <el-button type="primary" icon="el-icon-circle-plus-outline" plain @click="add()">添加项目</el-button>
      </el-row>
      <el-table
        :data="tableData"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection"></el-table-column>
        <el-table-column type="index"> </el-table-column>
        <el-table-column label="项目名称" prop="itemName"></el-table-column>
        <el-table-column label="检查费用" prop="fee"></el-table-column>
        <el-table-column label="创建时间" prop="createtime"></el-table-column>
        <el-table-column label="状态">
          <template slot-scope="scope">
            <el-tag>{{check_status[scope.row.status]}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="220px">
          <template slot-scope="scope">
            <el-button type="danger" icon="el-icon-delete" v-if="scope.row.status===1" @click="del(scope.row.id)">删除项目</el-button>
            <el-button type="primary" icon="el-icon-delete" v-if="scope.row.status===2"  @click="check(scope.row)">开立检查</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog :title="'添加'+title+'项目'" :visible.sync="show" :close-on-click-modal="false" :show-close="false" width="1200px">
      <Add v-if="show" :title="title" :show.sync="show" :caseId="caseId" url="check"></Add>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="show = false;getData();">确 定</el-button>
     </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import Add from './Add'
export default {
  props: ['caseId'],
  data () {
    return {
      tableData: [],
      show: false,
      selectedrow: [],
      title: ''
    }
  },
  components: {
    Add
  },
  computed: {
    ...mapState(['check_status'])
  },
  mounted () {
    // console.log('mounted ' + this.caseId)
    // this.getData()
  },
  watch: {
    // 正确给 caseId 赋值的 方法
    caseId: function (newVal, oldVal) {
      this.caseId = newVal // newVal即是caseId
      this.getData()
    }
  },
  methods: {
    getData () {
      this.$axios.get(`/checkApplys/list/${this.caseId}`,
        response => {
          this.tableData = response.data
        }
      )
    },
    add () {
      this.title = '检查'
      this.show = true
    },
    handleSelectionChange (val) {
      this.selectedrow = val // [ {} ,{} ,{}    ]
    },
    del (id) {
      this.$axios.del(`/checkApplys/${id}`, () => { this.getData() })
    },
    check (item) {
      this.$axios.put(`/checkApplys/check/${item.id}`, () => { item.status = 3 })
    }
  }
}
</script>
<style scoped>
</style>

<template>
  <div>
    <Breadcrumb msg1="医生工作站" msg2="科室病例" />

    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="10">
        <el-col :span="10">
          <el-input v-model="queryInfo.name" placeholder="请输入要查的病人姓名" clearable @clear="getData">
            <el-button slot="append" icon="el-icon-search" @click="getData"></el-button>
          </el-input>
        </el-col>
        <el-col :span="14">
          <el-button type="warning" icon="el-icon-delete" plain @click="batchdel()">批量删除</el-button>
        </el-col>
      </el-row>

      <el-table
        :data="tableData"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
      <el-table-column type="selection"></el-table-column>
      <el-table-column label="病历号"  prop="id" width="80px"></el-table-column>
      <el-table-column label="姓名" prop="name" ></el-table-column>
      <el-table-column label="性别" width="50px">
        <template slot-scope="scope">
          {{scope.row.gender===0?'男':'女'}}
        </template>
      </el-table-column>
      <el-table-column label="身份证号" prop="idno"> </el-table-column>
      <el-table-column label="年龄" prop="age" width="50px"> </el-table-column>
      <el-table-column label="挂号费用" prop="fee" width="100px"> </el-table-column>
      <el-table-column label="挂号状态">
         <template slot-scope="scope">
          <el-tag>{{register_status[scope.row.status]}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="是否有效" width="100">
        <template slot-scope="scope">
          <el-switch
            :value="scope.row.active === 1"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="stateChanged(scope.row)"
            >
          </el-switch>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="220px">
        <template slot-scope="scope">
          <el-tooltip effect="dark" content="查看病例信息" placement="top" :enterable="false">
            <el-button type="info" icon="el-icon-message" circle @click="caseshowById(scope.row.id)"></el-button>
          </el-tooltip>
          <el-tooltip effect="dark" content="接诊" placement="top" :enterable="false">
            <el-button type="success" icon="el-icon-document-add" circle @click="casedetailShow(scope.row)"></el-button>
          </el-tooltip>
          <el-button type="danger" icon="el-icon-delete" circle  @click="handleDelete(scope.row.id)"></el-button>
        </template>
      </el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.page"
        :page-sizes="[3, 5, 10, 20]"
        :page-size="queryInfo.limit"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    <el-row  v-if="show" id="user">
      <el-button type="warning" @click="show=false">关闭</el-button><br/>
      <CaseShow :register_id="register_id"></CaseShow>
    </el-row>
    </el-card>
    <el-dialog title="查看病例情况" :visible.sync="caseShow" :close-on-click-modal="false" width="500px">
      <Detail v-if="caseShow" :show.sync="caseShow" :register_id="register_id"></Detail>
    </el-dialog>
  </div>
</template>

<script>
import Breadcrumb from '@/components/Breadcrumb'
import CaseShow from '@/components/doctor/CaseShow'
import Detail from '@/components/doctor/Detail'
import { mapState } from 'vuex'
export default {
  data () {
    return {
      queryInfo: {
        name: '',
        page: 1,
        limit: 10,
        dept_id: 0
      },
      tableData: [],
      total: 0,
      selectedrow: [],
      register_id: null,
      show: false,
      caseShow: false
    }
  },
  components: {
    Breadcrumb,
    CaseShow,
    Detail
  },
  mounted () {
    this.getData()
    this.queryInfo.dept_id = window.sessionStorage.getItem('deptId')
    if (!this.queryInfo.dept_id) {
      this.$message.error('登录信息已过期，请重新登录')
      this.$router.push('/login')
    }
    this.$store.dispatch('getConstantTypes')
  },
  computed: {
    ...mapState(['register_status'])
  },
  methods: {
    getData () {
      this.$axios.get('/registers',
        response => {
          this.tableData = response.data.records
          this.total = parseInt(response.data.total)
        },
        this.queryInfo
      )
    },
    casedetailShow (cases) {
      if (cases.status === 3) {
        this.$message.error('该用户已退号，无法接诊')
        return
      }
      if (cases.status === 1) {
        this.recevie(cases.id)
      }

      this.register_id = cases.id
      this.show = true
    },
    caseshowById (id) {
      this.register_id = id
      this.caseShow = true
    },
    handleDelete (id) {
      this.$axios.del(`/registers/${id}`, () => {
        if (this.total === (this.queryInfo.page - 1) * this.queryInfo.limit + 1) this.queryInfo.page -= 1
        this.getData()
      })
    },
    handleSelectionChange (val) {
      // val参数为所有选中行的数据
      this.selectedrow = val // [ {} ,{} ,{}    ]
    },
    batchdel () {
      if (this.selectedrow.length === 0) {
        this.$message('没有任何被选中的数据')
      } else {
        const ids = []
        for (let i = 0; i < this.selectedrow.length; i++) {
          ids.push(this.selectedrow[i].id)
        }
        this.$axios.del('/registers/batchdel',
          () => {
            if (this.total === (this.queryInfo.page - 1) * this.queryInfo.limit + ids.length) this.queryInfo.page -= 1
            this.getData()
          },
          {
            ids: ids.join(',')
          }
        )
      }
    },
    handleSizeChange (newSize) {
      this.queryInfo.limit = newSize
      this.getData()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange (newPage) {
      this.queryInfo.page = newPage
      this.getData()
    },
    // 监听 switch 开关状态的改变
    stateChanged (info) {
      const active = info.active === 1 ? 0 : 1
      this.$axios.put(`/registers/${info.id}/state/${active}`, response => {
        info.active = active
      })
    },
    recevie (id) {
      this.$axios.put(`/registers/receive/${id}`)
    }
  }
}
</script>
<style scoped>
#user {
  margin-top: 10px;
}
</style>

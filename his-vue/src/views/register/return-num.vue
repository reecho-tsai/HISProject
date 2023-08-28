<template>
  <div>
    <Breadcrumb msg1="挂号收费" msg2="退号" />

    <!-- 卡片视图区域 -->
    <el-card>
      <el-row >
        <el-col :span="10">
          <el-input v-model="queryInfo.name" placeholder="请输入要查的病人姓名或病例号" clearable @clear="getData">
              <el-button slot="append" icon="el-icon-search" @click="getData"></el-button>
          </el-input>
        </el-col>
      </el-row>

      <el-table
        :data="tableData"
        style="width: 100%"
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
      <el-table-column label="医生" prop="doctorId" width="50px"> </el-table-column>
      <el-table-column label="挂号费用" prop="fee" width="100px"> </el-table-column>
      <el-table-column label="创建时间" prop="createtime" > </el-table-column>
      <el-table-column label="操作" width="220px">
      ddd  <template slot-scope="scope">
          <el-button type="danger" round @click="del(scope.row.id,scope.row.doctorId)">退号</el-button>
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
    </el-card>
  </div>
</template>

<script>
import Breadcrumb from '@/components/Breadcrumb'
export default {
  data () {
    return {
      queryInfo: {
        name: '',
        page: 1,
        limit: 10,
        status: 1,
        active: 1,

      },
      tableData: [],
      total: 0
    }
  },
  components: {
    Breadcrumb
  },
  mounted () {
    this.getData()
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
    del (id,doctorId) {
      this.$axios.put(`/registers/num/${id}`, () => { this.getData() })
      let minusid=doctorId;//减少挂号数
      this.$axios.http.put(`works/minus/${minusid}`, response => {})
    },
    handleSizeChange (newSize) {
      this.queryInfo.limit = newSize
      this.getData()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange (newPage) {
      this.queryInfo.page = newPage
      this.getData()
    }
  }
}
</script>
<style scoped>
</style>

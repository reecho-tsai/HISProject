<template>
  <div>
    <el-card>
      <el-row :gutter="10">
        <el-col :span="10">
          <el-input v-model="queryInfo.name" :placeholder="'请输入要查的'+title+'项目名称'" clearable @clear="getData">
            <el-button slot="append" icon="el-icon-search" @click="getData"></el-button>
          </el-input>
        </el-col>
      </el-row>

      <el-table
        :data="tableData"
        style="width: 100%"
      >
        <el-table-column label="ID" width="80" prop="id" ></el-table-column>
        <el-table-column :label="title+'名称'" prop="name"></el-table-column>
        <el-table-column :label="title+'内容'" prop="content"></el-table-column>
        <el-table-column :label="title+'费用'" prop="fee" width="80px"></el-table-column>
        <el-table-column label="操作" width="220px">
          <template slot-scope="scope">
              <el-tooltip effect="dark" content="添加" placement="top" :enterable="false">
                <el-button type="success" icon="el-icon-check" circle @click="add(scope.row)"></el-button>
              </el-tooltip>
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
export default {
  props: ['title', 'caseId', 'url'],
  data () {
    return {
      queryInfo: {
        name: '',
        page: 1,
        limit: 5,
        active: 1
      },
      tableData: [],
      total: 0,
      selectedrow: []
    }
  },
  mounted () {
    if (this.url) {
      this.getData()
    }
  },
  watch: {
    // 正确给 caseId 赋值的 方法
    url: function (newVal, oldVal) {
      this.url = newVal // newVal即是caseId
      console.log(this.url)
      this.getData()
    }
  },
  methods: {
    getData () {
      this.$axios.get(`/${this.url}Items`,
        response => {
          this.tableData = response.data.records
          this.total = parseInt(response.data.total)
        },
        this.queryInfo
      )
    },
    add (item) {
      this.$axios.post(`/${this.url}Applys`,
        () => {},
        {
          registerId: this.caseId,
          itemId: item.id,
          fee: item.fee,
          itemName: item.name,
          status: 1
        }
      )
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

<style lang='less' scoped>
</style>

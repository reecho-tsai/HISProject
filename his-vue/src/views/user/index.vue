<template>
  <div>
    <Breadcrumb msg1="用户管理" msg2="用户列表" />

    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="10">
        <el-col :span="10">
          <el-input v-model="queryInfo.name" placeholder="请输入要查的用户名称或姓名" clearable @clear="getData">
            <el-button slot="append" icon="el-icon-search" @click="getData"></el-button>
          </el-input>
        </el-col>
        <el-col :span="14">
          <el-button type="primary" icon="el-icon-circle-plus-outline" plain @click="add()">添加用户</el-button>
          <el-button type="warning" icon="el-icon-delete" plain @click="batchdel()">批量删除</el-button>
        </el-col>
      </el-row>

      <el-table
        :data="tableData"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection"></el-table-column>

        <el-table-column label="ID" width="80" prop="id"></el-table-column>

        <el-table-column label="用户名" prop="username"></el-table-column>
        <el-table-column label="真实姓名" prop="realName"></el-table-column>
        <el-table-column label="最后一次登录时间" prop="lastLogin"></el-table-column>
        <el-table-column label="角色">
          <template slot-scope="scope">
            {{roles[scope.row.userType].name}}
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
              <el-button type="primary" icon="el-icon-edit" round @click="handleEdit(scope.row.id)">编辑</el-button>
              <el-button type="danger" icon="el-icon-delete" round @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <div>
        <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.page"
        :page-sizes="[3, 5, 10, 30]"
        :page-size="queryInfo.limit"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
      </div>
    </el-card>

    <el-dialog :title="title" :visible.sync="show" :close-on-click-modal="false" width="500px">
      <UserEdit v-if="show" :show.sync="show" @getData="getData()" :editid="editid"></UserEdit>
    </el-dialog>
  </div>
</template>

<script>
import UserEdit from '@/components/user/edit.vue'
import Breadcrumb from '@/components/Breadcrumb'
import { mapState } from 'vuex'
export default {
  data () {
    return {
      queryInfo: {
        name: '',
        page: 1,
        limit: 10
      },
      tableData: [],
      total: 0,
      show: false,
      editid: null,
      selectedrow: [],
      title: ''
    }
  },
  components: {
    UserEdit,
    Breadcrumb
  },
  created () {
    this.getData()
    this.$store.dispatch('getRoles')
  },
  computed: {
    ...mapState(['roles'])
  },
  methods: {
    getData () {
      this.$axios.get('/users',
        response => {
          this.tableData = response.data.records
          this.total = parseInt(response.data.total)
        },
        this.queryInfo
      )
    },
    handleEdit (id) {
      this.title = '修改用户'
      this.editid = id
      this.show = true
    },
    add () {
      this.title = '添加用户'
      this.editid = null
      this.show = true
    },
    handleDelete (id) {
      this.$axios.del(`/users/${id}`, () => {
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
        this.$axios.del('/users/batchdel',
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
      this.$axios.put(`/users/${info.id}/state/${active}`, response => {
        info.active = active
      })
    }
  }
}
</script>
<style scoped>
</style>

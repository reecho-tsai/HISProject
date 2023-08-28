<template>
  <div>
    <Breadcrumb msg1="用户管理" msg2="角色列表" />

    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="10">
        <el-col :span="10">
          <el-input v-model="queryInfo.name" placeholder="请输入要查的角色名" clearable @clear="getData">
            <el-button slot="append" icon="el-icon-search" @click="getData"></el-button>
          </el-input>
        </el-col>
        <el-col :span="14">
          <el-button type="primary" icon="el-icon-circle-plus-outline" plain @click="add()">添加角色</el-button>
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
        <el-table-column label="角色名" width="200" prop="name"> </el-table-column>
        <el-table-column label="创建时间" width="300" prop="createtime"> </el-table-column>
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

        <el-table-column label="操作" >
          <template slot-scope="scope">
            <el-button type="info" icon="el-icon-bank-card" round @click="getPermissions(scope.row.id)">编辑权限</el-button>
            <el-button type="primary" icon="el-icon-edit" round @click="handleEdit(scope.row.id)">修改角色</el-button>
            <el-button type="danger" icon="el-icon-delete" round @click="handleDelete(scope.row.id)">删除角色</el-button>
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

    <el-dialog :title="title" :visible.sync="show" :close-on-click-modal="false" width="500px">
      <RoleEdit v-if="show" :show.sync="show" @getData="getData()" :editid="editid"></RoleEdit>
    </el-dialog>

        <!-- 分配权限的对话框 -->
    <el-dialog title="分配权限" :visible.sync="setRightDialogVisible" width="50%" @close="setRightDialogClosed">
      <!-- 树形控件 -->
      <el-tree :data="rightslist" :props="treeProps" show-checkbox node-key="id" default-expand-all :default-checked-keys="defKeys" ref="treeRef"></el-tree>
      <span slot="footer" class="dialog-footer">
        <el-button @click="setRightDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="allotRights">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import RoleEdit from '@/components/role/edit.vue'
import Breadcrumb from '@/components/Breadcrumb'
export default {
  data () {
    return {
      queryInfo: {
        name: '',
        page: 1,
        limit: 5
      },
      tableData: [],
      total: 0,
      show: false,
      editid: null,
      selectedrow: [],
      title: '',
      setRightDialogVisible: false,
      // 所有权限的数据
      rightslist: [],
      // 树形控件的属性绑定对象
      treeProps: {
        label: 'name',
        children: 'children'
      },
      // 默认选中的节点Id值数组
      defKeys: [],
      // 当前即将分配权限的角色id
      roleId: '',
      // 获取用户权限后，对应数据库的字段Id
      id: 0,
      // 权限父节点
      parent: []
    }
  },
  components: {
    RoleEdit,
    Breadcrumb
  },
  mounted () {
    this.getData()
    this.$axios.http.get('/permissions/parent').then(response => {
      this.parent = response.data.data
    })
  },
  methods: {
    getData () {
      this.$axios.get('/roles',
        response => {
          this.tableData = response.data.records
          this.total = parseInt(response.data.total)
        },
        this.queryInfo
      )
    },
    handleEdit (id) {
      this.title = '修改角色'
      this.editid = id
      this.show = true
    },
    add () {
      this.title = '修改角色'
      this.editid = null
      this.show = true
    },
    handleDelete (id) {
      this.$axios.del(`/roles/${id}`, () => {
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
        this.$axios.del('/roles/batchdel',
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
      this.$axios.put(`/roles/${info.id}/state/${active}`, response => {
        info.active = active
      })
    },
    async getPermissions (id) {
      this.roleId = id
      // 获取所有权限的数据
      const { data: res } = await this.$axios.http.get('permissions')

      if (res.status !== 200) {
        return this.$message.error('获取权限数据失败！')
      }
      // 把获取到的权限数据保存到 data 中
      this.rightslist = res.data
      // console.log(this.rightslist)
      this.getLeafKeys(id)
      this.setRightDialogVisible = true
    },
    // 通过递归的形式，获取角色下所有二级权限的id，并保存到 defKeys 数组中
    async getLeafKeys (id) {
      const { data: res } = await this.$axios.http.get(`/role-permission/${id}`)
      if (res.status !== 200) {
        this.id = 0
        return
      }
      this.id = res.data.id
      let ids = res.data.permissioinId || ''
      ids = ids.split(',')
      this.defKeys = ids.map(val => parseInt(val)).filter(val => !this.parent.includes(val))
      console.log(this.defKeys)
    },
    // 监听分配权限对话框的关闭事件
    setRightDialogClosed () {
      this.defKeys = []
    },
    // 点击为角色分配权限
    allotRights () {
      const keys = [
        ...this.$refs.treeRef.getCheckedKeys(),
        ...this.$refs.treeRef.getHalfCheckedKeys()
      ]
      // console.log(keys)
      if (this.id === 0) {
        this.$axios.post('/role-permission', () => {}, {
          roleId: this.roleId,
          permissioinId: keys.join(',')
        })
      } else {
        this.$axios.put(`/role-permission/${this.id}`, () => {}, {
          permissioinId: keys.join(',')
        })
      }
      this.setRightDialogVisible = false
    }

  }
}
</script>
<style scoped>
</style>

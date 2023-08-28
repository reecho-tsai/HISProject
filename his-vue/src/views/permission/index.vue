<template>
  <div>
    <Breadcrumb msg1="用户管理" msg2="权限列表" />

    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="10">
        <el-col :span="14">
          <el-button type="primary" icon="el-icon-circle-plus-outline" plain @click="add(0)">添加权限</el-button>
        </el-col>
      </el-row>

      <el-table
        :data="tableData"
        style="width: 100%"
        :tree-props="{children: 'children'}"
        row-key="id"
      >
      <el-table-column label="id"  prop="id" width="100px"></el-table-column>
      <el-table-column label="权限名"  prop="name"> </el-table-column>
      <el-table-column label="资源路径" prop="url"></el-table-column>
      <el-table-column label="类型">
        <template slot-scope="scope">
          <el-tag>{{type[scope.row.type]}}</el-tag>
        </template>
      </el-table-column>
        <el-table-column label="是否有效" width="100px">
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

        <el-table-column label="操作" width="300">
          <template slot-scope="scope">
            <template v-if="scope.row.active === 1">
              <el-tooltip effect="dark" content="添加子权限" placement="top" :enterable="false">
                <el-button type="warning" icon="el-icon-plus" circle v-if="scope.row.parentId === 0"  @click="add(scope.row.id)"></el-button>
              </el-tooltip>
              <el-button type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.row.id)"></el-button>
          </template>
          <el-button type="danger" icon="el-icon-delete" circle @click="handleDelete(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog :title="title" :visible.sync="show" :close-on-click-modal="false" width="500px">
      <PermissionEdit v-if="show" :show.sync="show" @getData="getData()" :editid="editid" :parentId="parentId"></PermissionEdit>
    </el-dialog>
  </div>
</template>

<script>
import PermissionEdit from '@/components/permission/edit.vue'
import Breadcrumb from '@/components/Breadcrumb'
export default {
  data () {
    return {
      type: ['目录', '页面', '按钮'],
      tableData: [],
      show: false,
      editid: null,
      selectedrow: [],
      title: '',
      parentId: 0
    }
  },
  components: {
    PermissionEdit,
    Breadcrumb
  },
  created () {
    this.getData()
  },
  methods: {
    getData () {
      this.$axios.get('/permissions',
        response => {
          this.tableData = response.data
        }
      )
    },
    handleEdit (id) {
      this.title = '修改权限'
      this.editid = id
      this.show = true
    },
    add (id) {
      this.parentId = id
      this.title = '添加权限'
      this.editid = null
      this.show = true
    },
    handleDelete (id) {
      this.$axios.del(`/permissions/${id}`, () => {
        this.getData()
      })
    },
    // 监听 switch 开关状态的改变
    stateChanged (info) {
      const active = info.active === 1 ? 0 : 1
      this.$axios.put(`/permissions/${info.id}/state/${active}`, response => {
        info.active = active
      })
    }
  }
}
</script>
<style scoped>
</style>

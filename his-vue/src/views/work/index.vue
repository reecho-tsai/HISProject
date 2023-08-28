<template>
  <div>
    <Breadcrumb msg1="医生工作站" msg2="排班管理" />

    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="10">
        <el-col :span="10">
          <el-input v-model="queryInfo.name" placeholder="请输入要查的医生姓名" clearable @clear="getData">
            <el-button slot="append" icon="el-icon-search" @click="getData"></el-button>
          </el-input>
        </el-col>
        <el-col :span="14">
          <el-button type="primary" icon="el-icon-circle-plus-outline" plain @click="add()">添加排班</el-button>
          <el-button type="warning" icon="el-icon-delete" plain @click="batchdel()">批量删除</el-button>
          <el-button icon="el-icon-circle-plus-outline" plain @click="updatework()">更新排班</el-button>
        </el-col>
      </el-row>

      <el-table
        :data="tableData"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection"></el-table-column>

        <el-table-column label="ID" width="80" prop="id"></el-table-column>

        <el-table-column label="用户名" prop="name"></el-table-column>

        <el-table-column label="科室类别">
           <template slot-scope="scope">
             {{department[scope.row.deptId].name}}
           </template>
        </el-table-column>
        <el-table-column label="用户类别">
          <template slot-scope="scope">
            {{roles[scope.row.userType].name}}
          </template>
        </el-table-column>
        <el-table-column label="工作时间" prop="workTime0" :formatter="workFormat">
        </el-table-column>
        <el-table-column label="已挂号人数" prop="numb"></el-table-column>
        <el-table-column label="最大人数" prop="numbMax"></el-table-column>
        <el-table-column label="是否已满" width="100">
          <template slot-scope="scope">
            <el-switch
              :value="scope.row.state === 1"
              active-color="#13ce66"
              inactive-color="#ff4949"
              @change="stateChanged(scope.row)"
              >
            </el-switch>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="220px">
          <template slot-scope="scope">
              <el-button type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.row.id)"></el-button>
              <el-button type="danger" icon="el-icon-delete" circle  @click="handleDelete(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.page"
        :page-sizes="[3, 5, 10, 30]"
        :page-size="queryInfo.limit"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>

    <el-dialog :title="title" :visible.sync="show" :close-on-click-modal="false" width="500px">
      <UserEdit v-if="show" :show.sync="show" @getData="getData()" :editid="editid"></UserEdit>
    </el-dialog>
  </div>
</template>

<script>
import UserEdit from '@/components/work/edit.vue'
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
      title: '',
      retime: 0,
    }
  },
  components: {
    UserEdit,
    Breadcrumb
  },
  created () {
    this.getData()
    this.$store.dispatch('getRoles')
    this.$store.dispatch('getDepartment')
  },
  computed: {
    ...mapState(['roles', 'department'])
  },
  methods: {
    retime() {//页面加载获取时间
        const nowDate = new Date();//获取当前时间
        let year = nowDate.getFullYear();
        let month =nowDate.getMonth() + 1 < 10? "0" + (nowDate.getMonth() + 1): nowDate.getMonth() + 1;
        let date =nowDate.getDate() < 10? "0" + nowDate.getDate(): nowDate.getDate();
        let hh =nowDate.getHours() < 10? "0" + nowDate.getHours(): nowDate.getHours();
        let mm =nowDate.getMinutes() < 10? "0" + nowDate.getMinutes(): nowDate.getMinutes();
        let ss =nowDate.getSeconds() < 10? "0" + nowDate.getSeconds(): nowDate.getSeconds();
        let week = nowDate.getDay();//得到星期
        let weeks = ["7","1","2","3","4","5","6"];
        let getWeek = weeks[week];
        this.form.retime2 = year + "-" + month + "-" + date;//修改日期初始值
        this.form.retime = getWeek;//星期传给retime,获取当前日期
    },
    updatework () {

      this.$axios.http.put(`users/update_work`, response => {})
    },
    getData () {
      this.$axios.get('/works',
        response => {
          this.tableData = response.data.records
          this.total = parseInt(response.data.total)
        },
        this.queryInfo
      )
    },
    handleEdit (id) {
      this.title = '修改排班'
      this.editid = id
      this.show = true
    },
    add () {
      this.title = '添加排班'
      this.editid = null
      this.show = true
    },
    handleDelete (id) {
      this.$axios.del(`/works/${id}`, () => {
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
        this.$axios.del('/works/batchdel',
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
    workFormat(row, column){//工作时间转化
        if (row.workTime0 === '1a') {
            return '周一上午'
        } else if(row.workTime0 === '1b'){
            return '周一下午'
        } else if (row.workTime0 === '2a') {
            return '周二上午'
        } else if(row.workTime0 === '2b'){
            return '周二下午'
        } else if (row.workTime0 === '3a') {
            return '周三上午'
        } else if(row.workTime0 === '3b'){
            return '周三下午'
        } else if (row.workTime0 === '4a') {
            return '周四上午'
        } else if(row.workTime0 === '4b'){
            return '周四下午'
        } else if (row.workTime0 === '5a') {
            return '周五上午'
        } else if(row.workTime0 === '5b'){
            return '周五下午'
        }else if (row.workTime0 === '6a') {
            return '周六上午'
        } else if(row.workTime0 === '6b'){
            return '周六下午'
        } else if (row.workTime0 === '7a') {
            return '周日上午'
        } else if(row.workTime0 === '7b'){
            return '周日下午'
        }


    },
    // 监听 switch 开关状态的改变
    stateChanged (info) {
      const state = info.state === 1 ? 0 : 1
      this.$axios.put(`/works/${info.id}/state/${state}`, response => {
        info.state = state
      })
    }
  }
}
</script>
<style scoped>
</style>

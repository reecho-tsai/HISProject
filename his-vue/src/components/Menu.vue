<template>
  <div>
    <el-menu
      background-color="#EBEEF5"
      text-color="#909399"
      active-text-color="#606266"
      unique-opened
      :collapse="isCollapse"
      :collapse-transition="false"
      router
      :default-active="activeurl"
    >
      <!-- 一级菜单 -->
      <el-submenu :index="item.id + ''" v-for="item in menulist" :key="item.id">
        <!-- 一级菜单的模板区域 -->
        <template slot="title">
          <!-- 图标 -->
          <i :class="iconsObj[item.id]"></i>
          <!-- 文本 -->
          <span>{{ item.name }}</span>
        </template>

        <!-- 二级菜单 -->
        <el-menu-item
          :index="'/' + subItem.url"
          v-for="subItem in item.children"
          :key="subItem.id"
          @click="saveNavState('/' + subItem.url)"
        >
          <template slot="title">
            <!-- 图标 -->
            <i class="el-icon-menu"></i>
            <!-- 文本 -->
            <span>{{ subItem.name }}</span>
          </template>
        </el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'Menu',
  data () {
    return {
      // 左侧导航栏菜单数据 可以通过token向服务器获取用户的菜单信息（根据角色权限不同）
      menulist: [],
      // menulist: [
      //   {
      //     id: 100,
      //     name: '用户管理',
      //     url: 'users',
      //     children: [
      //       {
      //         id: 101,
      //         name: '用户列表',
      //         url: 'user',
      //         children: []
      //       },
      //       {
      //         id: 102,
      //         name: '角色管理',
      //         url: 'role',
      //         children: []
      //       },
      //       {
      //         id: 103,
      //         name: '权限管理',
      //         url: 'permission',
      //         children: []
      //       }
      //     ]
      //   },
      //   {
      //     id: 200,
      //     name: '基本信息管理',
      //     url: 'constant',
      //     children: [
      //       {
      //         id: 201,
      //         name: '常数类别管理',
      //         url: 'constant-type',
      //         children: []
      //       },
      //       {
      //         id: 202,
      //         name: '常数项目管理',
      //         url: 'constant-item',
      //         children: []
      //       },
      //       {
      //         id: 203,
      //         name: '挂号级别',
      //         url: 'registlevel',
      //         children: []
      //       },
      //       {
      //         id: 204,
      //         name: '科室管理',
      //         url: 'department',
      //         children: []
      //       },
      //       {
      //         id: 205,
      //         name: '检查项目管理',
      //         url: 'check-item',
      //         children: []
      //       },
      //       {
      //         id: 206,
      //         name: '检验项目管理',
      //         url: 'inspect-item',
      //         children: []
      //       }
      //     ]
      //   },
      //   {
      //     id: 300,
      //     name: '挂号收费',
      //     url: 'regist',
      //     children: [
      //       {
      //         id: 301,
      //         name: '挂号',
      //         url: 'register',
      //         children: []
      //       },
      //       {
      //         id: 302,
      //         name: '退号',
      //         url: 'return-num',
      //         children: []
      //       },
      //       {
      //         id: 303,
      //         name: '收费',
      //         url: 'fee',
      //         children: []
      //       },
      //       {
      //         id: 304,
      //         name: '退费',
      //         url: 'refund',
      //         children: []
      //       }
      //     ]
      //   },
      //   {
      //     id: 400,
      //     name: '医生工作站',
      //     url: 'doctor',
      //     children: [
      //       {
      //         id: 401,
      //         name: '病历首页',
      //         url: 'doctor',
      //         children: []
      //       },
      //       {
      //         id: 402,
      //         name: '科室病例',
      //         url: 'dept_list',
      //         children: []
      //       },
      //       {
      //         id: 403,
      //         name: '挂号列表',
      //         url: 'cases',
      //         children: []
      //       }
      //     ]
      //   }
      // ],
      // 通过对象的key 和上面的菜单来改变图标样式
      // iconsObj: {
      //   100: 'el-icon-user',
      //   200: 'el-icon-setting',
      //   300: 'el-icon-s-order',
      //   400: 'el-icon-s-cooperation'
      // },
      iconsObj: {
        1: 'el-icon-user',
        2: 'el-icon-setting',
        3: 'el-icon-s-order',
        4: 'el-icon-s-cooperation'
      },
      // 被激活的链接地址
      activeurl: ''
    }
  },
  created () {
    // this.getMenuList(); 用来获取列表信息
    this.activeurl = window.sessionStorage.getItem('activeurl')
    this.$axios.http.get('/permissions/userPermissionList').then(reponse => {
      // console.log(reponse.data.data)
      if (reponse.data.status !== 200) {
        this.$message.error(reponse.data.message)
        this.$router.push('/login')
      }
      this.menulist = reponse.data.data
    })
  },
  methods: {
    // 保存链接的激活状态
    saveNavState (activeurl) {
      window.sessionStorage.setItem('activeurl', activeurl)
      this.activeurl = activeurl
    }
  },
  computed: {
    ...mapState({
      isCollapse: 'isCollapse'
    })
  }
}
</script>

<style lang="less" scoped>
.el-menu {
  border-right: none;
}

</style>

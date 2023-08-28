<template>
  <el-container class="home-container">
    <!-- 头部区域 -->
    <el-header>
      <div>
        <img src="../assets/logo.png" alt />
        <span>云医院HIS服务平台</span>
      </div>
      <el-button type="primary" plain @click="logout" icon="el-icon-switch-button">退出</el-button>
    </el-header>
    <!-- 页面主体区域 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside :width="isCollapse ? '64px' : '200px'">
        <!-- 侧边栏菜单区域 -->
        <Menu/>
      </el-aside>
      <!-- 右侧内容主体 -->
      <el-main>
        <!-- 路由占位符 -->
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Menu from '../components/Menu.vue'
import { mapState, mapActions } from 'vuex'
export default {
  components: {
    Menu
  },
  methods: {
    logout () {
      window.sessionStorage.clear()
      this.$router.push('/login')
    }, // 退出登录
    ...mapActions([
      'toggleCollapse'
    ])
  },
  computed: {
    ...mapState({
      isCollapse: 'isCollapse'
    })
  }
}
</script>

<style lang="less" scoped>
.home-container {
  height: 100%;
}
.el-header {
  background-color: #409EEF;
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  align-items: center;
  color: #fff;
  font-size: 20px;
  > div {
    display: flex;
    align-items: center;
    img {
      height: 20px;
      width: 20px;
      margin-left: 20px;
    }
    span {
      margin-left: 15px;
    }
  }
}

.el-aside {
  background-color: #EBEEF5;
}

.el-main {
  background-color: #eaedf1;
}

.iconfont {
  margin-right: 10px;
}

.toggle-button {
  background-color: #4a5064;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
}
</style>

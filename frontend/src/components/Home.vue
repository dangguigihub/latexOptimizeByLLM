
<template>
  <div class="home-container">
    <!-- 侧边栏 -->
    <el-menu
      :default-active="activeIndex"
      class="sidebar"
      :collapse="isCollapse"
      @select="handleSelect"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
    >
      <el-menu-item index="1">
        <i class="el-icon-edit"></i>
        <span slot="title">LaTex优化</span>
      </el-menu-item>
      <el-menu-item index="2">
        <i class="el-icon-s-operation"></i>
        <span slot="title">用户信息</span>
      </el-menu-item>
      <el-menu-item index="3">
        <i class="el-icon-switch-button"></i>
        <span slot="title">退出</span>
      </el-menu-item>
    </el-menu>

    <!-- 主内容区 -->
    <div class="main-content">
      <div class="content-wrapper">
        <component :is="currentComponent"></component>
      </div>
    </div>
  </div>
</template>

<script>
import LatexOptimize from './LatexOptimize'
import UserManage from './UserManage'

export default {
  name: 'Home',
  components: {
    LatexOptimize,
    UserManage
  },
  data() {
    return {
      activeIndex: '1',
      isCollapse: false,
      currentComponent: 'LatexOptimize'
    }
  },
  methods: {
    handleSelect(index) {
      if (index === '1') {
        this.currentComponent = 'LatexOptimize'
      } else if (index === '2') {
        this.currentComponent = 'UserManage'
      } else if (index === '3') {
        this.$confirm('确定要退出系统吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$store.state.token='';
          localStorage.removeItem("token");
          this.$router.push('/login');
        })
      }
    },
    async openModelDialog() {
      try {
        const response = await this.$axios.get("/api/user/used");
        if (response.data.code === 1) {
          this.modelOptions = response.data.data;
          this.$store.state.modelOptions = this.modelOptions;
          console.log(this.modelOptions)
        } else {
          this.$message.error(response.data.msg || "获取模型列表失败");
        }
      } catch (error) {
        console.error("请求失败:", error);
        this.$message.error("获取模型列表失败");
      }
    },
  },
  mounted() {
    // 响应式处理
    const handleResize = () => {
      this.isCollapse = window.innerWidth < 768
    }
    window.addEventListener('resize', handleResize)
    handleResize()
    this.openModelDialog()
  }
}
</script>

<style scoped>
.home-container {
  display: flex;
  height: 100vh;
  overflow: hidden;
}

.sidebar {
  min-height: 100%;
  transition: width 0.3s;
}

.sidebar:not(.el-menu--collapse) {
  width: 200px;
}

.main-content {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background-color: #f0f2f5;
}

.content-wrapper {
  background: #fff;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
}

@media (max-width: 768px) {
  .sidebar {
    position: fixed;
    z-index: 1000;
    height: 100%;
  }
  
  .main-content {
    margin-left: 64px;
  }
}
</style>

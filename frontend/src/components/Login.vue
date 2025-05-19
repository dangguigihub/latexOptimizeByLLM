<!-- Login.vue -->
<template>
  <!-- 新增布局容器 -->
  <div class="global-container">
    <div class="auth-container">
      <h2>登录</h2>
      <form @submit.prevent="handleSubmit">
        <label for="login-username">用户名:</label>
        <input v-model="form.username" type="text" id="login-username" required>

        <label for="login-password">密码:</label>
        <input v-model="form.password" type="password" id="login-password" required>

        <button type="submit">登录</button>
      </form>
      <p>还没有账号？ <router-link to="/register">注册</router-link></p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';  // 导入axios

export default {
  data() {
    return {
      form: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    async handleSubmit() {
      try {
        // 发送登录请求
        const response = await axios.post('/api/auth/login', {
          username: this.form.username,
          password: this.form.password
        }, {
          headers: {
            "Content-Type": "application/json"
          }
        });
        // 根据后端返回的code判断（假设code: 1表示成功）
        if(response.data.code === 1) {
          this.$message.success('登录成功');
          // 保存token到本地存储（根据实际响应结构调整）
          localStorage.setItem('token', response.data.data);
          this.$store.state.token=response.data.data;
          // 跳转到首页
          setTimeout(() => {
            this.$router.push('/home');
          }, 1000);
          
        } else {
          // 显示具体错误信息
          this.$message.error(response.data.msg);
        }
      } catch (error) {
        // 细化错误处理
        this.$message.error(`登录失败，请检查网络设置`);
      }
    }
  }
}
</script>

<style scoped>
/* 新增布局容器样式 */
.global-container {
  font-family: Arial, sans-serif;
  background-color: #f4f4f9;
  margin: 0;
  padding: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  width: 100%;
}

.auth-container {
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 300px;
  text-align: center;
}

/* 其他样式保持不变 */
h2 {
  margin-bottom: 20px;
}

form {
  display: flex;
  flex-direction: column;
}

label {
  margin-bottom: 5px;
  text-align: left;
}

input {
  margin-bottom: 15px;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

button {
  padding: 10px;
  background-color: #5cb85c;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #4cae4c;
}

a {
  color: #5cb85c;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}
</style>
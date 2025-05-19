<template>
  <div class="global-container">
    <div class="auth-container register-container">
      <h2>注册</h2>
      <form @submit.prevent="handleSubmit">
        <label for="reg-username">用户名:</label>
        <input v-model="form.username" type="text" id="reg-username" required />

        <label for="reg-email">邮箱:</label>
        <input v-model="form.email" type="email" id="reg-email" required />

        <label for="reg-password">密码:</label>
        <input
          v-model="form.password"
          type="password"
          id="reg-password"
          required
        />
        <button type="submit">注册</button>
      </form>
      <p>已有账号？ <router-link to="/login">登录</router-link></p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      form: {
        username: '',
        email: '',
        password: ''
      }
    }
  },
  methods: {
    async handleSubmit() {
      try {
        const response = await axios.post('/api/auth/register', {
          username: this.form.username,
          email: this.form.email,
          password: this.form.password
        }, {
          headers: {
            "Content-Type": "application/json"
          }
        });
        
        if(response.data.code === 1) {
          this.$message.success('注册成功，请登录！');
          setTimeout(() => {
            this.$router.push('/login');
          }, 1000);
        } else {
          this.$message.error(response.data.msg);
        }
      } catch (error) {
        this.$message.error('注册失败');
      }
    }
  }
}
</script>

<style scoped>
/* 全局容器样式 */
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

/* 认证容器通用样式 */
.auth-container {
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 300px;
  text-align: center;
}

/* 标题样式 */
h2 {
  color: #333;
  margin-bottom: 20px;
  font-size: 24px;
}

/* 表单样式 */
form {
  display: flex;
  flex-direction: column;
  margin-bottom: 15px;
}

/* 输入标签样式 */
label {
  color: #666;
  font-size: 14px;
  margin-bottom: 5px;
  text-align: left;
}

/* 输入框样式 */
input {
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  margin-bottom: 15px;
  transition: border-color 0.3s ease;
}

input:focus {
  outline: none;
  border-color: #5cb85c;
  box-shadow: 0 0 3px rgba(92, 184, 92, 0.3);
}

/* 按钮样式 */
button {
  background-color: #5cb85c;
  color: white;
  padding: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #4cae4c;
}

/* 链接样式 */
a {
  color: #5cb85c;
  text-decoration: none;
  font-weight: bold;
  transition: color 0.3s ease;
}

a:hover {
  color: #4cae4c;
  text-decoration: underline;
}

/* 段落样式 */
p {
  color: #666;
  font-size: 14px;
  margin-top: 15px;
}
</style>
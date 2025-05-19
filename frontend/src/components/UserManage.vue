<template>
  <div>
    <div v-if="loading" class="loading-container">加载中...</div>
    <div v-else>
      <!-- 用户信息展示 -->
      <el-card class="user-card">
        <el-descriptions title="用户信息" border>
          <el-descriptions-item label="用户名">{{
            userInfo.username
          }}</el-descriptions-item>
          <el-descriptions-item label="邮箱">{{
            userInfo.email
          }}</el-descriptions-item>
        </el-descriptions>
      </el-card>

      <!-- 添加模型按钮 -->
      <div class="action-container">
        <el-button type="primary" @click="openModelDialog">添加模型</el-button>
      </div>

      <!-- 添加模型对话框 -->
      <el-dialog title="添加模型" :visible.sync="dialogVisible" width="30%">
        <el-form
          :model="form"
          label-width="100px"
          :rules="rules"
          ref="modelForm"
        >
          <!-- 模型选择 -->
          <el-form-item label="模型名称" prop="modelName">
            <el-select
              v-model="form.modelName"
              placeholder="请先选择模型"
              @change="handleModelChange"
              style="width: 100%"
              filterable
              clearable
            >
              <el-option
                v-for="model in Object.keys(modelToProviders)"
                :key="model"
                :label="model"
                :value="model"
              ></el-option>
            </el-select>
          </el-form-item>

          <!-- 供应商选择 -->
          <el-form-item label="供应商" prop="provider">
            <div class="select-wrapper" @click="handleProviderClick">
              <el-select
                v-model="form.provider"
                placeholder="请先选择模型"
                :disabled="!form.modelName"
                style="width: 100%"
                filterable
                clearable
              >
                <el-option
                  v-for="provider in availableProviders"
                  :key="provider"
                  :label="provider"
                  :value="provider"
                ></el-option>
              </el-select>
              <div v-if="showProviderTip" class="select-tip">
                请先选择模型名称
              </div>
            </div>
          </el-form-item>

          <!-- API Key -->
          <el-form-item label="API Key" prop="apiKey">
            <el-input
              v-model="form.apiKey"
              show-password
              placeholder="请输入API密钥"
              clearable
            ></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitModel">提交</el-button>
        </span>
      </el-dialog>

      <!-- 配额使用表格 -->
      <el-card class="quota-card">
        <el-table :data="quotas" style="width: 100%">
          <el-table-column
            prop="modelName"
            label="模型名称"
            width="180"
          ></el-table-column>
          <el-table-column prop="provider" label="供应商"></el-table-column>
          <el-table-column label="总配额" align="center">
            <template slot-scope="scope">
              {{ scope.row.totalQuota }} tokens
            </template>
          </el-table-column>
          <el-table-column label="已用配额" align="center">
            <template slot-scope="scope">
              {{ scope.row.usedQuota }} tokens
            </template>
          </el-table-column>
          <el-table-column label="使用进度" width="200">
            <template slot-scope="scope">
              <el-progress
                :percentage="
                  parseFloat(
                    (
                      (scope.row.usedQuota / scope.row.totalQuota) *
                      100
                    ).toFixed(1)
                  )
                "
                :color="customColors"
                :format="progressFormat"
              ></el-progress>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loading: false,
      userInfo: {
        username: "",
        email: "",
      },
      quotas: [],
      dialogVisible: false,
      modelOptions: [],
      form: {
        modelName: "",
        provider: "",
        apiKey: "",
      },
      customColors: [
        { color: "#f56c6c", percentage: 80 },
        { color: "#e6a23c", percentage: 60 },
        { color: "#5cb87a", percentage: 40 },
      ],
      rules: {
        modelName: [
          { required: true, message: "请选择模型名称", trigger: "change" },
        ],
        provider: [
          { required: true, message: "请选择供应商", trigger: "change" },
        ],
        apiKey: [
          { required: true, message: "请输入API密钥", trigger: "blur" },
          { min: 8, message: "API密钥长度至少8位", trigger: "blur" },
        ],
      },
      modelToProviders: {},
      availableProviders: [],
      showProviderTip: false,
    };
  },
  created() {
    this.fetchUserInfo();
  },
  methods: {
    async fetchUserInfo() {
      try {
        this.loading = true;
        const response = await this.$axios.get("/api/user/info");
        if (response.data.code === 1) {
          this.userInfo = {
            username: response.data.data.username,
            email: response.data.data.email,
          };
          this.quotas = response.data.data.quotas;
        } else {
          this.$message.error(response.data.msg || "获取用户信息失败");
        }
      } catch (error) {
        console.error("请求失败:", error);
        this.$message.error("获取用户信息失败，请稍后重试");
      } finally {
        this.loading = false;
      }
    },

    async openModelDialog() {
      try {
        const response = await this.$axios.get("/api/model/info");
        if (response.data.code === 1) {
          this.modelOptions = response.data.data;
          this.buildMappings();
          this.dialogVisible = true;
          this.$store.state.modelOptions = this.modelOptions;
        } else {
          this.$message.error(response.data.msg || "获取模型列表失败");
        }
      } catch (error) {
        console.error("请求失败:", error);
        this.$message.error("获取模型列表失败");
        this.dialogVisible = false;
      }
    },

    buildMappings() {
      const mappings = {};
      this.modelOptions.forEach((item) => {
        if (!mappings[item.modelName]) {
          mappings[item.modelName] = new Set();
        }
        mappings[item.modelName].add(item.provider);
      });
      this.modelToProviders = Object.fromEntries(
        Object.entries(mappings).map(([k, v]) => [k, Array.from(v)])
      );
    },

    handleModelChange(model) {
      this.form.provider = "";
      this.availableProviders = this.modelToProviders[model] || [];
      this.showProviderTip = false;
    },

    handleProviderClick() {
      if (!this.form.modelName) {
        this.showProviderTip = true;
        setTimeout(() => {
          this.showProviderTip = false;
        }, 2000);
      }
    },

    validateCombination() {
      return this.modelOptions.some(
        (item) =>
          item.modelName === this.form.modelName &&
          item.provider === this.form.provider
      );
    },

    submitModel() {
      this.$refs.modelForm.validate((valid) => {
        if (valid && this.validateCombination()) {
          this.$axios
            .post("/api/model/add", this.form, {
              headers: {
                "Content-Type": "application/json",
              },
            })
            .then((response) => {
              console.log("提交数据:", this.form);
              this.$message.success("模型添加成功");
              this.fetchUserInfo();
              this.dialogVisible = false;
              this.resetForm();
            })
            .catch((error) => {
              console.error("提交失败:", error);
              this.$message.error("模型添加失败");
            });
        } else {
          this.$message.error("请选择有效的模型与供应商组合");
        }
      });
    },
    resetForm() {
      this.form = {
        modelName: "",
        provider: "",
        apiKey: "",
      };
      this.availableProviders = [];
      this.$nextTick(() => {
        this.$refs.modelForm.clearValidate();
      });
    },

    progressFormat(percentage) {
      return `${percentage}%`;
    },
  },
};
</script>

<style scoped>
.loading-container {
  padding: 20px;
  text-align: center;
  font-size: 16px;
  color: #666;
}

.user-card {
  margin-bottom: 20px;
}

.action-container {
  margin: 20px 0;
  text-align: right;
}

.quota-card {
  margin-top: 20px;
}

.el-progress {
  padding: 5px 0;
}

.el-descriptions {
  margin: 10px;
}

.select-wrapper {
  position: relative;
}

.select-tip {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.9);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #f56c6c;
  font-size: 14px;
  z-index: 1;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.el-select {
  width: 100%;
  margin-bottom: 10px;
}

.el-select.is-disabled .el-input__inner {
  background-color: #fff;
  cursor: not-allowed;
}
</style>
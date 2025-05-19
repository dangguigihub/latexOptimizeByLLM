<template>
  <div class="container">
    <el-row :gutter="40" type="flex" justify="center" class="wrapper">
      <!-- 左侧输入区域 -->
      <el-col :span="10" class="section-group">
        <el-card class="section-card">
          <div slot="header">用户输入</div>
          <el-input
            v-model="userInput"
            type="textarea"
            :autosize="{ minRows: 10 }"
            placeholder="输入LaTeX公式"
            class="input-area"
          />
        </el-card>

        <el-card class="section-card">
          <div slot="header">实时渲染（输入）</div>
          <div class="render-area" v-html="inputPreview"></div>
        </el-card>
      </el-col>

      <!-- 中间操作按钮 -->
      <el-col :span="2" class="button-col">
        <el-select
          v-model="selectedModel"
          placeholder="选择模型"
          class="model-select"
          :disabled="modelOptions.length === 0"
        >
          <el-option
            v-for="model in modelOptions"
            :key="model.modelId"
            :label="`${model.modelName} (${model.provider})`"
            :value="model.modelId"
          />
        </el-select>
        <el-button
          type="primary"
          class="optimize-btn"
          @click="handleOptimize"
          :loading="loading"
        >
          <span class="btn-text">AI优化</span>
        </el-button>
      </el-col>

      <!-- 右侧结果区域 -->
      <el-col :span="10" class="section-group">
        <el-card class="section-card">
          <div slot="header">优化结果</div>
          <pre class="result-code">{{ optimizedCode }}</pre>
        </el-card>

        <el-card class="section-card">
          <div slot="header">实时渲染（结果）</div>
          <div class="render-area" v-html="resultPreview"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";
import katex from "katex";
import "katex/dist/katex.min.css";

export default {
  data() {
    return {
      userInput: "",
      optimizedCode: "",
      loading: false,
      renderError: null,
      selectedModel: "",
    };
  },

  computed: {
    inputPreview() {
      return this.renderLatex(this.userInput);
    },

    resultPreview() {
      return this.renderLatex(this.optimizedCode);
    },
    modelOptions() {
      return this.$store.state.modelOptions || [];
    },
  },

  methods: {
    renderLatex(content) {
      try {
        return katex.renderToString(content, {
          throwOnError: true,
          displayMode: true,
        });
      } catch (e) {
        return `<div class="error">渲染错误: ${e.message}</div>`;
      }
    },

    async handleOptimize() {
      if (!this.userInput.trim()) {
        this.$message.warning("请输入内容");
        return;
      }
      if (this.selectedModel === "") {
        this.$message.warning("请选择模型");
        return;
      }
      this.loading = true;
      try {
        const response = await axios.post("/api/model/optimize", {
          latex: this.userInput,
          modelId: this.selectedModel,
        });
        this.optimizedCode = response.data.data;
        this.$message.success("优化成功");
      } catch (error) {
        this.$message.error(`优化失败: ${error.message}`);
        console.error("API Error:", error);
      } finally {
        this.loading = false;
      }
    },
  },
};
</script>

<style scoped>
.container {
  padding: 30px;
  min-height: 100vh;
}

.wrapper {
  height: 80vh;
}

.section-group {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.section-card {
  flex: 1;
  margin-bottom: 20px;
  display: flex;
  flex-direction: column;
}

.section-card >>> .el-card__body {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.input-area {
  flex: 1;
}

.render-area {
  flex: 1;
  overflow: auto;
  padding: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.result-code {
  white-space: pre-wrap;
  word-break: break-all;
  height: 100%;
  margin: 0;
}
.error {
  color: #f56c6c;
  font-size: 14px;
}

.button-col {
  display: flex !important;
  align-items: center;
  justify-content: center;
  height: 100%; /* 确保列高度撑满父容器 */
}
.button-col {
  display: flex !important;
  flex-direction: column;
  gap: 20px; /* 下拉框与按钮间距 */
}

.model-select {
  width: 100%;
  margin-bottom: 10px;
}

.optimize-btn {
  width: 100%;
  /* 保持原有按钮样式 */
}
</style>
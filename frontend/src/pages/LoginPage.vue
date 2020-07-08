<template>
  <el-row justify="center" type="flex" id="login-panel">
    <el-col :span="6">
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="登录" name="login">
          <LoginForm />
        </el-tab-pane>
        <el-tab-pane label="注册" name="register">
          <RegisterForm />
        </el-tab-pane>
      </el-tabs>
    </el-col>
  </el-row>
</template>

<script>
    import LoginForm from "@/components/LoginForm";
    import RegisterForm from "@/components/RegisterForm";
    export default {
        name: "LoginPage",
        components: {LoginForm, RegisterForm},
        data: function () {
            return {
                activeName: 'login'
            };
        }
    }
</script>

<style scoped>
  #login-panel {
    margin-top: 130px;
  }
</style>

<template>
  <el-form :model="form" label-width="80px">
    <el-form-item label="用户名">
      <el-input v-model="form.username" aria-placeholder="请输入用户名" />
    </el-form-item>
    <el-form-item label="密码">
      <el-input v-model="form.password" aria-placeholder="请输入密码" />
    </el-form-item>
    <el-form-item label="记住我">
      <el-switch v-model="form.rememberMe" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="login">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
    export default {
        name: "LoginForm",
        data: function () {
            return {
                form: {
                    username: '',
                    password: '',
                    rememberMe: false
                }
            };
        },
        methods: {
            login() {
                //
            }
        }
    }
</script>

<style scoped>

</style>

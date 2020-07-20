<template>
  <el-row justify="center" type="flex" id="login-panel">
    <el-col :span="6">
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="登录" name="login">
          <el-form :model="loginForm" label-width="80px">
            <el-form-item label="用户名">
              <el-input id="login-username" v-model="loginForm.username" aria-placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="密码">
              <el-input id="login-password" v-model="loginForm.password" aria-placeholder="请输入密码" show-password />
            </el-form-item>
            <el-form-item>
              <el-button id="login-btn" type="primary" @click="login">登录</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="注册" name="register">
          <el-form :model="registerForm" label-width="80px">
            <el-form-item label="用户名">
              <el-input id="register-username" v-model="registerForm.username" aria-placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="密码">
              <el-input id="register-password" v-model="registerForm.password" aria-placeholder="请输入密码" show-password />
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input id="register-mail" v-model="registerForm.mailAddr" aria-placeholder="请输入邮箱" />
            </el-form-item>
            <el-form-item>
              <el-button id="register-btn" type="primary" @click="register">注册</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-col>
  </el-row>
</template>

<script>
    import {POST} from "@/ajax";
    import {loginUrl, registerUrl} from "@/utils/constants";
    export default {
        name: "LoginDialog",
        data: function () {
            return {
                activeName: 'login',
                loginForm: {
                    username: '',
                    password: '' // ,
                    // rememberMe: false
                },
                registerForm: {
                    username: '',
                    password: '',
                    mailAddr: ''
                }
            };
        },
        methods: {
            login() {
                POST(loginUrl, {
                    username: this.loginForm.username,
                    password: this.loginForm.password
                }, (data) => {
                    if (data.status === 0) {
                        this.$message.success(data.message);
                        this.$store.commit('setUserData', data.extraData);
                        this.$router.push('/index');
                    } else {
                        this.$message.error(data.message);
                    }
                });
            },
            register() {
                POST(registerUrl, this.registerForm, (data) => {
                    if (data.status === 0) {
                        this.activeName = 'login';
                        this.$message.success(data.message);
                    } else {
                        this.$message.error(data.message);
                    }
                });
            }
        }
    }
</script>

<style scoped>
  #login-panel {
    margin-top: 0;
  }
</style>

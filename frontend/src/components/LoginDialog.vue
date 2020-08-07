<template>
  <el-row justify="center" type="flex" id="login-panel">
    <el-col :span="6">
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="登录" name="login">
          <el-form :model="loginForm" label-width="80px">
            <el-form-item label="用户名">
              <el-input v-model="loginForm.username" aria-placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="loginForm.password" aria-placeholder="请输入密码" show-password />
            </el-form-item>
            <el-form-item>
              <el-button-group>
                <el-button id="login-btn" type="primary" @click="login">登录</el-button>
                <el-button @click="$router.back()">取消</el-button>
              </el-button-group>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="注册" name="register">
          <div v-if="!isMailAddrValid"><i class="el-icon-error" />邮箱格式不正确</div>
          <div v-if="!isPasswordConsistent"><i class="el-icon-error" />两次输入密码不一致</div>
          <el-form :model="registerForm" label-width="80px">
            <el-form-item label="用户名">
              <el-input v-model="registerForm.username" aria-placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="registerForm.password" aria-placeholder="请输入密码" show-password />
            </el-form-item>
            <el-form-item label="确认密码">
              <el-input v-model="registerForm.confirm" aria-placeholder="请再次输入密码" show-password />
<!--              <div v-if="!isPasswordConsistent"><i class="el-icon-error" />两次输入密码不一致</div>-->
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="registerForm.mailAddr" aria-placeholder="请输入邮箱"/>
<!--              <div v-if="!isMailAddrValid"><i class="el-icon-error" />邮箱格式不正确</div>-->
            </el-form-item>
            <el-form-item>
              <el-button-group>
                <el-button id="regi-btn" type="primary" @click="register"
                           :disabled="isRegisterAvailable">
                  注册
                </el-button>
                <el-button @click="$router.back()">取消</el-button>
              </el-button-group>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-col>
  </el-row>
</template>

<script>
import {POST} from "@/ajax";
import {Constants} from "@/utils/constants";
import axios from 'axios';
export default {
    name: "LoginDialog",
    data: function () {
        return {
            activeName: 'login',
            loginForm: {
                username: '',
                password: ''
            },
            registerForm: {
                username: '',
                password: '',
                confirm: '',
                mailAddr: ''
            }
        };
    },
    methods: {
        login() {
            let params = new URLSearchParams();
            params.append("username", this.loginForm.username);
            params.append("password", this.loginForm.password);
            return axios.post("/login", params)
                .then(response => {
                    if (response.data.status === 0) {
                        console.log(response)
                        this.$message.success(response.data.message);
                        this.$store.commit('setUserData', response.data.extraData);
                        this.$router.back();
                    } else {
                        this.$message.error(response.data.message);
                    }
                });
        },
        register() {
            return POST(Constants.registerUrl, {
                username: this.registerForm.username,
                password: this.registerForm.password,
                mailAddr: this.registerForm.mailAddr
            }, (data) => {
                if (data.status === 0) {
                    this.activeName = 'login';
                    this.$message.success(data.message);
                } else {
                    this.$message.error(data.message);
                }
                // this.$store.state.logged
            });
        }
    },
    computed: {
        isPasswordConsistent: function () {
            return (this.registerForm.confirm === this.registerForm.password);
        },
        isMailAddrValid: function () {
            const mailAddressReg =
                /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/g;
            return (mailAddressReg.test(this.registerForm.mailAddr));
        },
        isRegisterAvailable: function () {
            return !this.isMailAddrValid || !this.isPasswordConsistent
        }
    }
}
</script>

<style scoped>
#login-panel {
    margin-top: 0;
}
</style>

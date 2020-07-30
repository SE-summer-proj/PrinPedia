<template>
  <el-form :model="form" label-width="80px">
    <el-form-item label="用户名">
      <el-input v-model="form.username" aria-placeholder="请输入用户名" />
    </el-form-item>
    <el-form-item label="密码">
      <el-input v-model="form.password" aria-placeholder="请输入密码" show-password />
    </el-form-item>
    <el-form-item label="邮箱">
      <el-input v-model="form.mailAddress" aria-placeholder="请输入邮箱" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="register">注册</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
    import {POST} from "@/utils/Utils";
    import {registerUrl} from "@/utils/Constants";

    export default {
        name: "RegisterForm",
        data: function () {
            return {
                form: {
                    username: '',
                    password: '',
                    mailAddress: ''
                }
            };
        },
        methods: {
            register() {
                POST(registerUrl, this.form, (data) => {
                    console.log(data);
                })
            }
        }
    }
</script>

<style scoped>

</style>

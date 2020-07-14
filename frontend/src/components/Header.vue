<template>
  <div id="header">
    <el-row v-if="!logged" type="flex" justify="end">
      <el-col :span="2">
        <el-button type="text" @click="loginDialogVisible = true">登录</el-button>
      </el-col>
    </el-row>
    <el-row v-else type="flex" justify="end">
      <el-col :span="4">
<!--        <el-avatar size="small" :src="userData.avatar" style="margin-right: 10px" />-->
        <span>
          <router-link :to="'/' + userData.username">{{userData.username}}</router-link>
        </span>
      </el-col>
    </el-row>
    <el-dialog :visible.sync="loginDialogVisible">
      <LoginDialog />
    </el-dialog>
  </div>
</template>

<script>
    import LoginDialog from "@/components/LoginDialog";
    export default {
        name: "Header",
        components: {LoginDialog},
        data: function () {
            return {
                userData: {
                    avatar: this.$store.state.avatar,
                    username: this.$store.state.username
                },
                logged: this.$store.state.logged,
                loginDialogVisible: false
            }
        }
    }
</script>

<style scoped>
  #header {
    margin-top: 10px;
  }
</style>

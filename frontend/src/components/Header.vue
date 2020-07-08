<template>
  <div id="header">
    <el-row v-if="userData.username === ''" type="flex" justify="end">
      <el-col :span="2">
        <router-link to="/login">登录</router-link>
      </el-col>
    </el-row>
    <el-row v-else type="flex" justify="end">
      <el-col :span="4">
        <el-avatar size="small" :src="userData.avatar" style="margin-right: 10px" />
        <span>
          <router-link :to="'/' + userData.username">{{userData.username}}</router-link>
        </span>
      </el-col>
    </el-row>
  </div>
</template>

<script>
    export default {
        name: "Header",
        data: function () {
            return {
                userData: {
                    avatar: this.$store.state.avatar,
                    username: this.$store.state.username
                }
            }
        }
    }
</script>

<style scoped>
  #header {
    margin-top: 10px;
  }
</style>

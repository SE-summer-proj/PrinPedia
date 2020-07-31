<template>
  <div id="header">

    <el-row v-if="!$store.state.logged" type="flex" justify="end">
      <el-col :span="2">
          <el-button type="text" @click="$router.push('/index')">首页</el-button>
        <el-button id="login-button" type="text" @click="$router.push('/login')">登录</el-button>
      </el-col>
    </el-row>
    <el-row v-else type="flex" justify="end">
      <el-col :span="4">
<!--        <el-avatar size="small" :src="userData.avatar" style="margin-right: 10px" />-->
        <span>
          <el-button-group>
            <el-button type="text" @click="$router.push('/user')">
              {{$store.state.userData.username}}
            </el-button>
            <el-button id="logout-button" type="text" @click="logout">登出</el-button>
          </el-button-group>
        </span>
      </el-col>
    </el-row>
  </div>
</template>

<script>
    // import {POST} from "@/ajax";
    // import {Constants} from "@/utils/constants";
    import axios from "axios";

    export default {
        name: "Header",
        methods: {
            // logout() {
            //     return POST(Constants.logoutUrl, null, () => {
            //         this.$store.commit('logout');
            //         this.$message.success('logout success')
            //     });
            // }
            logout() {
                return axios.get("/logout", null)
                    .then(response => {
                        console.log(response);
                        if (response.data.status === 0) {
                            this.$store.commit('logout');
                            this.$message.success('logout success');
                        } else {
                            this.$message.error(response.data.message);
                        }
                    })
            }
        }
    }
</script>

<style scoped>
  #header {
    margin-top: 10px;
  }
</style>

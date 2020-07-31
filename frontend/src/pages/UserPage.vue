<template>
  <el-container>
    <el-header><Header /></el-header>
    <el-main>
      <div id="personal">
        <div>
          <i class="el-icon-s-custom" />
          <span>个人信息</span>
        </div>
        <div>用户名：{{$store.state.userData.username}}</div>
        <div v-if="$store.state.userData.userType.indexOf('ROLE_ADMIN') >= 0">
<!--        <div>-->
          <el-button type="text" @click="$router.push('/admin')">后台管理</el-button>
        </div>
      </div>
      <div id="requests">
        <div>
          <i class="el-icon-upload" />
          <span>我的贡献</span>
        </div>
        <el-table :data="userLog" :row-class-name="getRowClassName">
          <el-table-column prop="id" label="申请编号" />
          <el-table-column prop="date" label="申请时间" />
          <el-table-column label="词条详情">
            <template slot-scope="scope">
              <router-link :to="'/userLogDetail/' + scope.row.id">{{scope.row.title}}</router-link>
            </template>
          </el-table-column>
          <el-table-column label="申请状态">
            <template slot-scope="scope">
              <div v-if="scope.row.status === 0">
                <i class="el-icon-document" />
                <span>审核中</span>
              </div>
              <div v-else-if="scope.row.status === 1" style="color: green">
                <i class="el-icon-document-checked" />
                <span>已通过</span>
              </div>
              <div v-else-if="scope.row.status === 2" style="color: #ff0000">
                <i class="el-icon-document-delete" />
                <span>未通过</span>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-main>
    <el-footer><Footer /></el-footer>
  </el-container>
</template>

<script>
    import Header from "@/components/Header";
    import Footer from "@/components/Footer";
    // import {GET} from "@/ajax";
    // import {Constants} from "@/utils/constants";
    import axios from "axios";
    export default {
        name: "UserPage",
        components: {Footer, Header},
        data: function () {
            return {
                userLog: []
            };
        },
        methods: {
            // getUserLog() {
            //     return GET(Constants.userLogUrl, {
            //         username: this.$store.state.userData.username
            //     }, (data) => {
            //         this.userLog = data.extraData
            //     });
            // },
            getUserLog() {
              console.log(this.$store.state.userData.userType)
              var URL = '/entry/edit/userLog?username=' + this.$store.state.userData.username
              return axios.get(URL)
                      .then(response => {
                        console.log(response);
                        this.userLog = response.data.extraData
                      });
            },
            getClassName(status) {
                if (status === 0) {
                    return 'unchecked';
                } else if (status === 1) {
                    return 'passed';
                } else if (status === 2) {
                    return 'rejected';
                } else {
                    return 'status';
                }
            },
            getRowClassName({row}) {
                // console.log(rowIndex);
                return this.getClassName(row);
            }
        },
        mounted() {
            return this.getUserLog();
        }
    }
</script>

<style scoped>
  .el-table .unchecked {
    background-color: aqua;
  }
  .el-table .passed {
    background-color: lightgreen;
  }
  .el-table .rejected {
    background-color: orange;
  }
</style>

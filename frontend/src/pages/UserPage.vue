<template>
  <el-container>
    <el-header><Header /></el-header>
    <el-main>
      <div>个人信息（准备中）</div>
      <div>我的贡献</div>
      <el-table :data="userLog" :row-class-name="getRowClassName">
        <el-table-column prop="id" label="申请编号" />
        <el-table-column prop="date" label="申请时间" />
        <el-table-column label="词条详情">
          <template slot-scope="scope">
            <router-link :to="'/userLogDetail/' + scope.row.id">{{scope.row.title}}</router-link>
          </template>
        </el-table-column>
        <el-table-column label="申请状态">
          <template slot-scope="scope">{{getClassName(scope.row.status)}}</template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-footer><Footer /></el-footer>
  </el-container>
</template>

<script>
    import Header from "@/components/Header";
    import Footer from "@/components/Footer";
    import {GET} from "@/ajax";
    import {Constants} from "@/utils/constants";
    export default {
        name: "UserPage",
        components: {Footer, Header},
        data: function () {
            return {
                userLog: []
            };
        },
        methods: {
            getUserLog() {
                return GET(Constants.userLogUrl, {
                    username: this.$store.state.userData.username
                }, (data) => {
                    this.userLog = data.extraData
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
            getRowClassName({row, rowIndex}) {
                console.log(rowIndex);
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

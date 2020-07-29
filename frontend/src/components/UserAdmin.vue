<template>
  <el-table :data="users">
    <el-table-column label="邮箱" prop="email" />
    <el-table-column label="用户名" prop="username" />
    <el-table-column label="状态">
      <template slot-scope="scope">
        <span>{{scope.row.disabled ? '已禁用' : '正常'}}</span>
        <span>
          <el-button type="danger" @click="changeDisableState(scope.row)">
            {{scope.row.disabled ? '解禁' : '禁用'}}
          </el-button>
        </span>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
    import {GET, POST} from "@/ajax";
    import {Constants} from "@/utils/constants";

    export default {
        name: "UserAdmin",
        data: function () {
            return {
                users: []
            };
        },
        methods: {
            getAllUsers() {
                return GET(Constants.usersUrl, {}, (data) => {
                    this.users = data.extraData;
                })
            },
            changeDisableState(row) {
                return POST(Constants.disableUrl, {
                    username: row.username,
                    enabled: row.disabled
                }, () => {});
            }
        },
        mounted() {
            return this.getAllUsers();
        }
    }
</script>

<style scoped>

</style>

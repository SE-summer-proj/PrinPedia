<template>
  <el-table :data="users">
    <el-table-column label="邮箱" prop="email" />
    <el-table-column label="用户名" prop="username" />
    <el-table-column label="状态">
      <template slot-scope="scope">
        <span style="margin-right: 10px">{{scope.row.enabled ? '正常' : '已禁用'}}</span>
        <span>
          <el-button type="text" @click="changeDisableState(scope.row)">
            {{scope.row.enabled ? '禁用' : '解禁'}}
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
                console.log(data)
                this.users = data.extraData;
            })
        },
        changeDisableState(row) {
            console.log(row.enabled)
            var status
            if(row.enabled === true) status = false
            if(row.enabled === null) status = true
            if(row.enabled === false) status = true
            console.log(status)
            return POST(Constants.disableUrl, {
                username: row.username,
                enabled: status
                // enabled: !row.disabled
            }, async () => {
                await this.getAllUsers();
            });
        }
    },
    mounted() {
        return this.getAllUsers();
    }
}
</script>

<style scoped>

</style>

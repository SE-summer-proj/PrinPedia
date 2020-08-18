<template>
  <el-container>
    <el-header><Header /></el-header>
    <el-main>
      <el-button-group>
        <el-button @click="$router.back()">返回</el-button>
        <el-button @click="$router.push('/stat')" type="primary">数据统计</el-button>
        <el-button @click="$router.push('/admin/feedback')" type="primary">意见箱</el-button>
      </el-button-group>
      <el-tabs :active-name="activeName">
        <el-tab-pane name="user-admin" label="用户管理">
          <UserAdmin />
        </el-tab-pane>
        <el-tab-pane name="edit-admin" label="处理申请">
          <EditAdmin />
        </el-tab-pane>
      </el-tabs>
    </el-main>
    <el-footer><Footer /></el-footer>
  </el-container>
</template>

<script>
import UserAdmin from "@/components/UserAdmin";
import EditAdmin from "@/components/EditAdmin";
import Header from "@/components/Header";
import Footer from "@/components/Footer";
export default {
    name: "AdminPage",
    components: {Footer, Header, EditAdmin, UserAdmin},
    data: function () {
        return {
            activeName: 'user-admin'
        };
    }
}
</script>

<style scoped>

</style>

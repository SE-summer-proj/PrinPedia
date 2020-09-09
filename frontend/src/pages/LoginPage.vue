<template>
  <el-container>
    <el-header><Header /></el-header>
    <el-main><LoginDialog /></el-main>
    <el-footer><Footer /></el-footer>
  </el-container>
</template>

<script>
import LoginDialog from "@/components/LoginDialog";
import Header from "@/components/Header";
import Footer from "@/components/Footer";
export default {
    name: "LoginPage",
    components: {Footer, Header, LoginDialog}
}
</script>

<style scoped>

</style>

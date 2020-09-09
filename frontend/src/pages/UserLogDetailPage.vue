<template>
  <el-container>
    <el-header><Header /></el-header>
    <el-main>
      <el-row>
        <el-col :span="6">申请编号：{{logDetail.id}}</el-col>
        <el-col :span="6">申请时间：{{logDetail.date}}</el-col>
        <el-col :span="6">词条标题：{{logDetail.title}}</el-col>
        <el-col :span="6">
          <span>状态：</span>
          <span style="color: darkblue" v-if="logDetail.status === 0">未审核</span>
          <span style="color: green" v-else-if="logDetail.status === 1">已通过</span>
          <span style="color: red" v-else-if="logDetail.status === 2">未通过</span>
        </el-col>
      </el-row>
      <el-row><VueWikitext :source="logDetail.wikiText" /></el-row>
      <el-row><el-button @click="$router.back()">返回</el-button></el-row>
    </el-main>
    <el-footer><Footer /></el-footer>
  </el-container>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import VueWikitext from "@/components/VueWikitext";
import {GET} from "@/ajax";
import {Constants} from "@/utils/constants";
export default {
    name: "UserLogDetailPage",
    components: {VueWikitext, Footer, Header},
    data: function () {
        return {
            logDetail: ''
        };
    },
    methods: {
        getWikiText() {
            return GET(Constants.editDetailUrl, {
                id: this.$route.params.userLogId
            }, (data) => {
                this.logDetail = data.extraData;
            });
        }
    },
    mounted() {
        return this.getWikiText();
    }
}
</script>

<style scoped>

</style>

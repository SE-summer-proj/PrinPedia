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
          <span style="color: darkblue" v-if="logDetail.status === 0">未读</span>
          <span style="color: green" v-else-if="logDetail.status === 1">通过</span>
          <span style="color: red" v-else-if="logDetail.status === 2">未通过</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <div>当前：</div>
          <VueWikitext :source="current.wikiText" />
        </el-col>
        <el-col :span="12">
          <div>修改：</div>
          <VueWikitext :source="logDetail.wikiText" />
        </el-col>
      </el-row>
      <el-row style="text-align: center">
        <el-col>
          <el-button-group>
            <el-button type="primary" @click="submit(true)">通过申请</el-button>
            <el-button type="danger" @click="submit(false)">拒绝申请</el-button>
            <el-button @click="$router.back()">返回</el-button>
          </el-button-group>
        </el-col>
      </el-row>
    </el-main>
    <el-footer><Footer /></el-footer>
  </el-container>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import VueWikitext from "@/components/VueWikitext";
import {GET, POST} from "@/ajax";
import {Constants} from "@/utils/constants";
import moment from 'moment';
export default {
    name: "ComparePage",
    components: {VueWikitext, Footer, Header},
    data: function () {
        return {
            logDetail: {},
            current: ''
        };
    },
    methods: {
        getDetail() {
            return GET(Constants.editDetailUrl, {
                id: this.$route.params.logId
            }, (data) => {
                this.logDetail = data.extraData;
                this.logDetail.date = moment(this.logDetail.date).format('YYYY-MM-DD hh:mm:ss');
            });
        },
        getCurrent() {
            return GET(Constants.entryUrl, {
                entryName: this.logDetail.title
            }, (data) => {
                this.current = data.extraData;
            })
        },
        submit(passed) {
            return POST(Constants.examineUrl, {
                id: this.$route.params.logId,
                passed: passed
            }, (data) => {
                this.$message.info(data.message);
                this.$router.push('/');
            });
        }
    },
    async mounted() {
        await this.getDetail();
        return this.getCurrent();
    }
}
</script>

<style scoped>

</style>

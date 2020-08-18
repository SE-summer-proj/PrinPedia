<template>
  <el-container>
    <el-header><Header /></el-header>
    <el-main>
      <el-button @click="$router.back()">返回</el-button>
      <el-tabs :active-name="activeName">
        <el-tab-pane label="未读" name="non-replied">
          <Feedback :table-data="nonReplied" :replied="false" />
        </el-tab-pane>
        <el-tab-pane label="已读" name="replied">
          <Feedback :table-data="replied" :replied="true" />
        </el-tab-pane>
      </el-tabs>
    </el-main>
    <el-footer><Footer /></el-footer>
  </el-container>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import {GET} from "@/ajax";
import {Constants} from "@/utils/constants";
import Feedback from "@/components/Feedback";
export default {
    name: "FeedbackAdminPage",
    components: {Feedback, Footer, Header},
    data() {
        return {
            activeName: 'non-replied',
            replied: [],
            nonReplied: []
        }
    },
    methods: {
        async getTableData() {
            await GET(Constants.adminGetMsgUrl, {
                isReplied: true
            }, (data) => {
                this.replied = data.extraData;
            });
            return GET(Constants.adminGetMsgUrl, {
                isReplied: false
            }, (data) => {
                this.nonReplied = data.extraData;
            });
        }
    },
    mounted() {
        return this.getTableData();
    }
}
</script>

<style scoped>

</style>

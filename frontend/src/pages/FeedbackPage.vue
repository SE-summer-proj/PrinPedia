<template>
  <el-container>
    <el-header><Header /></el-header>
    <el-main>
      <el-tabs :active-name="activeName">
        <el-tab-pane name="new" label="提交反馈">
          <el-input type="textarea" v-model="textInput" aria-placeholder="请输入反馈内容……" />
          <el-button-group>
            <el-button type="primary" @click="submit">提交</el-button>
          </el-button-group>
        </el-tab-pane>
        <el-tab-pane name="message" label="查看反馈">
          <el-table :data="tableData">
            <el-table-column prop="id" title="反馈编号" />
            <el-table-column label="反馈时间">
              <template slot-scope="scope">
                {{ timestamp2time(scope.row.time) }}
              </template>
            </el-table-column>
            <el-table-column title="反馈内容">
              <template slot-scope="scope">
                <el-popover placement="bottom" title="详情" trigger="hover" :content="scope.row.content">
                  <el-button slot="reference" type="text">{{ thumb(scope.row.content) }}</el-button>
                </el-popover>
              </template>
            </el-table-column>
            <el-table-column title="回复内容">
              <template slot-scope="scope">
                <el-popover placement="bottom" title="详情" trigger="hover" :content="scope.row.reply">
                  <el-button slot="reference" type="text">{{ thumb(scope.row.reply) }}</el-button>
                </el-popover>
              </template>
            </el-table-column>
            <el-table-column title="状态">
              <template slot-scope="scope">
                {{scope.row.isReplied ? '已读' : '未读'}}
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-main>
    <el-footer><Footer /></el-footer>
  </el-container>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import {GET, POST} from "@/ajax";
import {Constants} from "@/utils/constants";
import moment from "moment";
export default {
    name: "FeedbackPage",
    components: {Footer, Header},
    data() {
        return {
            activeName: '',
            textInput: '',
            tableData: []
        };
    },
    methods: {
        timestamp2time(timestamp) {
            return moment(timestamp).format('YYYY-MM-DD hh:mm:ss');
        },
        submit() {
            return POST(Constants.createMsgUrl, {
                username: this.$store.state.userData.username,
                content: this.textInput
            }, (data) => {
                this.$message.info(data.message);
            });
        },
        getTableData() {
            return GET(Constants.userGetMsgUrl, {
                username: this.$store.state.userData.username
            }, (data) => {
                this.tableData = data.extraData;
            });
        },
        thumb(string) {
            return string.substring(0,
                (string.length > 100) ? 100 : string.length);
        },
    },
    mounted() {
        return this.getTableData();
    }
}
</script>

<style scoped>

</style>

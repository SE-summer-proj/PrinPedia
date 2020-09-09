<template>
  <el-table :data="tableData">
    <el-table-column prop="id" label="反馈编号" />
    <el-table-column label="提交时间">
      <template slot-scope="scope">
        {{ timestamp2time(scope.row.time) }}
      </template>
    </el-table-column>
    <el-table-column label="反馈内容">
      <template slot-scope="scope">
        <el-popover placement="bottom" title="详情" trigger="hover" :content="scope.row.content">
          <el-button slot="reference" type="text">{{ thumb(scope) }}</el-button>
        </el-popover>
      </template>
    </el-table-column>
    <el-table-column prop="username" label="提交用户" />
    <el-table-column label="状态">
      <template slot-scope="scope">
        {{scope.row.isReplied ? '已读' : '未读'}}
      </template>
    </el-table-column>
    <el-table-column label="操作" v-if="!replied">
      <template slot-scope="scope">
        <el-button @click = "dialogVisible = true">回复</el-button>
        <el-dialog title="回复" :visible.sync="dialogVisible">
          <el-input v-model="textInput" aria-placeholder="请输入回复内容" type="textarea" />
          <div slot="footer">
            <el-button-group>
              <el-button type="primary" @click="submitReply(scope.row.id)">提交</el-button>
              <el-button @click="dialogVisible = false">取消</el-button>
            </el-button-group>
          </div>
        </el-dialog>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import {POST} from "@/ajax";
import {Constants} from "@/utils/constants";
import moment from "moment";

export default {
    name: "Feedback",
    props: {
        tableData: Array,
        replied: Boolean
    },
    data() {
        return {
            textInput: '',
            dialogVisible: false
        };
    },
    methods: {
        timestamp2time(timestamp) {
            return moment(timestamp).format('YYYY-MM-DD hh:mm:ss');
        },
        thumb(scope) {
            return scope.row.content.substring(0,
                (scope.row.content.length > 100) ? 100 : scope.row.content.length);
        },
        submitReply(id) {
            return POST(Constants.replyMsgUrl, {
                reply: this.textInput,
                id: id
            }, (data) => {
                this.$message.info(data.message);
                this.dialogVisible = false;
            });
        }
    }
}
</script>

<style scoped>

</style>

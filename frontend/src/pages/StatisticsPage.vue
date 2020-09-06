<template>
  <el-container>
    <el-header><Header /></el-header>
    <el-main>
      <el-button @click="$router.back()">返回</el-button>
      <el-tabs :active-name="activeName">
        <el-tab-pane label="时段访问量" name="entry">
          <el-date-picker
              v-model="entryTable.period"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期" />
          <el-button type="primary" @click="getEntry"
              :disabled="entryTable.period === ''">统计</el-button>
          <el-table :data="entryTable.tableData">
            <el-table-column prop="title" title="词条名" />
            <el-table-column prop="count" title="访问量" />
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="每日访问量" name="daily">
          <el-row>
            <el-input v-model="dailyTable.title" aria-placeholder="请输入标题" />
          </el-row>
          <el-date-picker
              v-model="dailyTable.period"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期" />
          <el-button type="primary" @click="getDaily"
              :disabled="dailyTable.title === '' || dailyTable.period === ''">
            统计</el-button>
          <el-table :data="dailyTable.tableData">
            <el-table-column prop="date" title="日期" />
            <el-table-column prop="title" title="词条名" />
            <el-table-column prop="count" title="访问量" />
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="搜索量" name="search">
          <el-date-picker
              v-model="searchTable.period"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期" />
          <el-button type="primary" @click="getSearch"
                     :disabled="searchTable.period === ''">统计</el-button>
          <el-table :data="searchTable.tableData">
            <el-table-column prop="title" title="词条名" />
            <el-table-column prop="count" title="搜索量" />
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="用户访问量" name="user">
          <el-date-picker
              v-model="userTable.period"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期" />
          <el-button type="primary" @click="getUser"
                     :disabled="userTable.period === ''">统计</el-button>
          <el-table :data="userTable.tableData">
            <el-table-column prop="date" title="日期" />
            <el-table-column prop="count" title="访问量" />
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
import {GET} from "@/ajax";
import {Constants} from "@/utils/constants";
import moment from 'moment';
export default {
    name: "StatisticsPage",
    components: {Footer, Header},
    data() {
        return {
            activeName: 'entry',
            entryTable: {
                tableData: [],
                period: ''
            },
            dailyTable: {
                tableData: [],
                title: '',
                period: ''
            },
            searchTable: {
                tableData: [],
                period: ''
            },
            userTable: {
                tableData: [],
                period: ''
            }
        };
    },
    methods: {
        getEntry() {
            const start = moment(this.entryTable.period[0]).format('yyyy-MM-dd');
            const end = moment(this.entryTable.period[1]).format('yyyy-MM-dd');
            return GET(Constants.entryStatUrl, {
                start,
                end
            }, (data) => {
                this.entryTable.tableData = data.extraData;
            });
        },
        getDaily() {
            const start = moment(this.dailyTable.period[0]).format('yyyy-MM-dd');
            const end = moment(this.dailyTable.period[1]).format('yyyy-MM-dd');
            return GET(Constants.dailyStatUrl, {
                start,
                end,
                title: this.dailyTable.title
            }, (data) => {
                this.dailyTable.tableData = data.extraData;
            });
        },
        getUser() {
            const start = moment(this.userTable.period[0]).format('yyyy-MM-dd');
            const end = moment(this.userTable.period[1]).format('yyyy-MM-dd');
            return GET(Constants.userStatUrl, {
                start,
                end,
            }, (data) => {
                this.userTable.tableData = data.extraData;
            });
        },
        getSearch() {
            const start = moment(this.searchTable.period[0]).format('yyyy-MM-dd');
            const end = moment(this.searchTable.period[1]).format('yyyy-MM-dd');
            return GET(Constants.searchStatUrl, {
                start,
                end
            }, (data) => {
                this.searchTable.tableData = data.extraData;
            });
        }
    }
}
</script>

<style scoped>

</style>

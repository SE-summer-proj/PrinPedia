<template>
  <el-container>
    <el-header><Header /></el-header>
    <el-main>
      <div id="personal">
        <el-row>
          <i class="el-icon-s-custom" />
          <span>个人信息</span>
          <el-button type="text" style="margin-left: 10px" @click="showEditDialog">
            <i class="el-icon-edit" />
            <span>编辑</span>
          </el-button>
        </el-row>
        <el-dialog :visible.sync="dialogVisible">
          <el-form :model="$store.state.userData">
            <el-form-item label="邮箱">
              <el-input v-model="userInfo.email" />
            </el-form-item>
            <el-form-item label="生日">
              <el-date-picker v-model="userInfo.birthday" type="date" />
            </el-form-item>
          </el-form>
          <span slot="footer">
            <el-button-group>
              <el-button type="primary" @click="editUserInfo">确定</el-button>
              <el-button @click="cancelEdit">取消</el-button>
            </el-button-group>
          </span>
        </el-dialog>
        <el-row>
          <el-col :span="4">用户名</el-col>
          <el-col :span="20">{{$store.state.userData.username}}</el-col>
        </el-row>
        <el-row>
          <el-col :span="4">邮箱</el-col>
          <el-col :span="20">{{$store.state.userData.email}}</el-col>
        </el-row>
        <el-row>
          <el-col :span="4">生日</el-col>
          <el-col :span="20">{{birthday}}</el-col>
        </el-row>
        <div v-if="$store.state.userType.indexOf('ROLE_ADMIN') >= 0">
          <el-button type="text" @click="$router.push('/admin')">后台管理</el-button>
        </div>
      </div>
      <div id="requests">
        <el-row>
          <i class="el-icon-upload" />
          <span>我的贡献</span>
        </el-row>
        <el-table :data="userLog" :row-class-name="getRowClassName">
          <el-table-column prop="id" label="申请编号" />
          <el-table-column label="申请时间">
            <template slot-scope="scope">
              {{ timestamp2time(scope.row.date) }}
            </template>
          </el-table-column>
          <el-table-column label="词条详情">
            <template slot-scope="scope">
              <router-link :to="'/userLogDetail/' + scope.row.id">{{scope.row.title}}</router-link>
            </template>
          </el-table-column>
          <el-table-column label="申请状态">
            <template slot-scope="scope">
              <div v-if="scope.row.status === 0">
                <i class="el-icon-document" />
                <span>审核中</span>
              </div>
              <div v-else-if="scope.row.status === 1" style="color: green">
                <i class="el-icon-document-checked" />
                <span>已通过</span>
              </div>
              <div v-else-if="scope.row.status === 2" style="color: #ff0000">
                <i class="el-icon-document-delete" />
                <span>未通过</span>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div id="collection">
        <el-row>
          <i class="el-icon-collection" />
          <span>我的收藏</span>
        </el-row>
        <el-table :data="collection">
          <el-table-column title="词条名称">
            <template slot-scope="scope">
              <router-link :to="'/entry/' + scope.row.title">{{scope.row.title}}</router-link>
            </template>
          </el-table-column>
          <el-table-column label="收藏时间">
            <template slot-scope="scope">
              {{ timestamp2time(scope.row.time) }}
            </template>
          </el-table-column>
          <el-table-column title="操作">
            <template slot-scope="scope">
              <el-button @click="switchCollectionStat(scope.row.title)">取消收藏</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-main>
    <el-footer><Footer /></el-footer>
  </el-container>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import axios from "axios";
import {GET, POST} from "@/ajax";
import {Constants} from "@/utils/constants";
import moment from 'moment';
export default {
    name: "UserPage",
    components: {Footer, Header},
    data: function () {
        return {
            userInfo: {
                username: '',
                birthday: '',
                email: '',
                avatarBase64: ''
            },
            userLog: [],
            collection: [],
            dialogVisible: false
        };
    },
    methods: {
        timestamp2time(timestamp) {
            return moment(timestamp).format('YYYY-MM-DD hh:mm:ss');
        },
        getUserInfo() {
            return GET(Constants.userDetailUrl, {
                username: this.$store.state.username
            }, (data) => {
                this.$store.commit('setUserData', data.extraData);
            });
        },
        getUserLog() {
            console.log(this.$store.state.userType);
            const URL = '/entry/edit/userLog?username=' + this.$store.state.username;
            return axios.get(URL)
                .then(response => {
                    console.log(response);
                    this.userLog = response.data.extraData;
                });
        },
        showEditDialog() {
            this.userInfo = {
                username: this.$store.state.userData.username,
                birthday: this.$store.state.userData.birthday,
                email: this.$store.state.userData.email,
                avatarBase64: this.$store.state.userData.avatarBase64
            };
            this.dialogVisible = true;
        },
        editUserInfo() {
            return POST(Constants.editUserInfoUrl, this.userInfo, (data) => {
                this.$store.state.userData.email = this.userInfo.email;
                this.$store.state.userData.birthday = this.userInfo.birthday;
                this.$message.success(data.message);
                this.dialogVisible = false;
            });
        },
        cancelEdit() {
            this.userInfo = {
                username: this.$store.state.userData.username,
                birthday: this.$store.state.userData.birthday,
                email: this.$store.state.userData.email,
                avatarBase64: this.$store.state.userData.avatarBase64
            };
            this.dialogVisible = false;
        },
        getClassName(status) {
            if (status === 0) {
                return 'unchecked';
            } else if (status === 1) {
                return 'passed';
            } else if (status === 2) {
                return 'rejected';
            } else {
                return 'status';
            }
        },
        getRowClassName({row}) {
            return this.getClassName(row);
        },
        getCollection() {
            return GET(Constants.userCollectionUrl, {
                username: this.$store.state.username
            }, (data) => {
                this.collection = data.extraData;
            });
        },
        switchCollectionStat(title) {
            // const url = this.isInCollection ? Constants.removeCollectionUrl : Constants.addCollectionUrl;
            return POST(Constants.removeCollectionUrl, {
                title: title,
                username: this.$store.state.username
            }, async () => {
                await this.getCollection();
            });
        }
    },
    computed: {
        birthday() {
            return moment(this.$store.state.userData.birthday).format('YYYY-MM-DD');
        }
    },
    async mounted() {
        await this.getUserInfo();
        await this.getCollection();
        return this.getUserLog();
    }
}
</script>

<style scoped>
  .el-table .unchecked {
    background-color: aqua;
  }
  .el-table .passed {
    background-color: lightgreen;
  }
  .el-table .rejected {
    background-color: orange;
  }
</style>

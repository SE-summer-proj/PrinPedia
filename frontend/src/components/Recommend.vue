<template>
  <div id="recommend">
    <el-row>
      <el-col :span="4">猜你想搜：</el-col>
      <el-col :span="(20 - 20 % entries.length) / entries.length" v-for="entry in entries" :key="entry.K">
        <router-link :to=" '/entry/' + entry">{{entry}}</router-link>
      </el-col>
    </el-row>
  </div>
</template>

<script>
    // import {GET} from "@/utils/Utils";
    // import {recommendUrl} from "@/utils/Constants";

    export default {
        name: "Recommend",
        data: function () {
            return {
                entries: []
            };
        },
        methods: {
            getRecommend() {
                // GET(recommendUrl, {
                //     userId: this.$store.state.userData.userId
                // }, (data) => {
                //     this.entries = data;
                // });
                this.entries = ['上海交大','迟先生'];
            }
        },
        mounted() {
            this.getRecommend();
        }
    }
</script>

<style scoped>
  #recommend {
    margin-top: 10px;
    margin-bottom: 10px;
  }
</style>

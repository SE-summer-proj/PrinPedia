<template>
  <div class="recommend">
    <el-row>
      <el-col :span="4">猜你想搜：</el-col>
      <el-col :span="(20 - 20 % entries.length) / entries.length" v-for="entry in entries" :key="entry.K">
        <router-link :to=" '/entry/' + entry">{{entry}}</router-link>
      </el-col>
    </el-row>
  </div>
</template>

<script>
    // import {GET} from "@/ajax";
    // import {Constants} from "@/utils/constants";
    import axios from "axios";


    export default {
        name: "Recommend",
        data: function () {
            return {
                entries: []
            };
        },
        methods: {
            // getRecommend() {
            //     return GET(Constants.recommendUrl, {username:this.$store.state.userData.username}, (data) => {
            //       console.log(data)
            //       // this.entries = data.extraData;
            //     });
            // }
          getRecommend() {
            // var URL = Constants.recommendUrl + '?username=' + this.$store.state.userData.username
            // return GET(URL, {}, (data) => {
            //   console.log(data)
            //   this.entries = data.extraData;
            // });
            var URL = '/recommend?username=' + this.$store.state.userData.username
              return axios.get(URL)
                      .then(response => {
                        console.log(response);
                        this.entries = response.data.extraData;
                      });
          }
          // getRecommend() {
          //   var params = new URLSearchParams();
          //   params.append("username", this.$store.state.userData.username);
          //   return axios.get("/recommend", params)
          //           .then(response => {
          //             console.log(response);
          //             this.entries = response.extraData;
          //           })
          // }

        },
        mounted() {
            return this.getRecommend();
        }
    }
</script>

<style scoped>
  .recommend {
    margin-top: 10px;
    margin-bottom: 10px;
  }
</style>

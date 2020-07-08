<template>
  <div id="recommend">
    <el-row>
      <el-col :span="4">猜你想搜：</el-col>
      <el-col :span="(20 - 20 % entries.length) / entries.length" v-for="entry in entries" :key="entry.K">
        <el-link type="primary" :href="entry.V">{{entry.K}}</el-link>
      </el-col>
    </el-row>
  </div>
</template>

<script>
    export default {
        name: "Recommend",
        data: function () {
            return {
                entries: [
                    {
                        K: '老大NB',
                        V: ''
                    },
                    {
                        K: '教务处SB',
                        V: ''
                    }
                ]
            };
        },
        methods: {
            getRecommend() {
                // setData: entries
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

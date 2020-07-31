<template>
  <el-container>
    <el-header>
      <Header />
    </el-header>
    <el-container>
      <el-main>
        <SearchBar :keyword="keyword" />
        <el-button type="main" @click="dialogVisible = true">显示推荐</el-button>
        <el-dialog title="推荐词条" :visible.sync="dialogVisible">
          <KnowledgeGraph :central-word="searchResults[0].title" />
        </el-dialog>
        <div class="not-found" v-if="notFound">
          没有找到相应词条"{{keyword}}"。您可能想要的是以下结果：
        </div>
        <div class="found">
          <SearchResult v-for="(result, i) in searchResults" :key="i" :result="result" />
        </div>
        <div v-if="notFound">
          <span>没有想要的结果？试试</span>
          <el-button type="text" @click="createEntry">创建词条</el-button>
        </div>
      </el-main>
      <el-aside><Ranking :columns="1" /></el-aside>
    </el-container>
  </el-container>
</template>

<script>
    import Header from "@/components/Header";
    import Ranking from "@/components/Ranking";
    import SearchBar from "@/components/SearchBar";
    import SearchResult from "@/components/SearchResult";
    import {Constants} from "@/utils/constants";
    import {GET,POST} from "@/ajax";
    import KnowledgeGraph from "@/components/KnowledgeGraph";
    export default {
        name: "ResultPage",
        components: {KnowledgeGraph, SearchResult, SearchBar, Ranking, Header},
        data: function () {
            return {
                keyword: this.$route.params.keyword,
                searchResults: [],
                notFound: false,
                dialogVisible: false
            };
        },
        methods: {
            search() {
                return GET(Constants.searchUrl, {
                    keyword: this.keyword
                }, (data) => {
                  console.log(data)
                  this.searchResults = data.extraData;
                    this.notFound = (data.status === -1);
                });
            },
            createEntry() {
              return POST(Constants.createUrl, {
                keyword: this.keyword
              }, () => {
                this.$router.push('/edit/' + this.keyword);
              });
            }
        },
        mounted() {
            return this.search();
        },
        watch: {
            '$route' () {
                this.keyword = this.$route.params.keyword
                this.search();
            }
        },
    }
</script>

<style scoped>

</style>

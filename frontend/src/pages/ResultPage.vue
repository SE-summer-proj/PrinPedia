<template>
  <el-container>
    <el-header>
      <Header />
    </el-header>
    <el-container>
      <el-main>
        <SearchBar :keyword="keyword" />
        <div class="not-found" v-if="notFound">
          <div>
            没有找到相应词条"{{keyword}}"。
            <el-button type="text" @click="createEntry">创建词条</el-button>
          </div>
          <div>您可能想要的是以下结果：</div>
        </div>
        <div class="found">
          <SearchResult v-for="(result, i) in searchResults" :key="i" :result="result" />
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
    import {createUrl, searchUrl} from "@/utils/constants";
    import {GET, POST} from "@/ajax";
    export default {
        name: "ResultPage",
        components: {SearchResult, SearchBar, Ranking, Header},
        data: function () {
            return {
                keyword: this.$route.params.keyword,
                searchResults: [],
                notFound: false
            };
        },
        methods: {
            search() {
                GET(searchUrl, {
                    keyword: this.keyword
                }, (data) => {
                    this.searchResults = data.extraData;
                    this.notFound = data.status === -1;
                });
            },
            createEntry() {
                POST(createUrl, {
                    keyword: this.keyword
                }, () => {
                    this.$router.push('/edit/' + this.keyword);
                });
            }
        },
        mounted() {
            this.search();
        }
    }
</script>

<style scoped>

</style>

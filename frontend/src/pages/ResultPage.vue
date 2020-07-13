<template>
  <el-container>
    <el-header>
      <Header />
    </el-header>
    <el-container>
      <el-main>
        <SearchBar :keyword="keyword" />
        <div v-if="notFound">没有找到相应词条"{{keyword}}"。您可能想要的是以下结果：</div>
        <SearchResult v-for="result in searchResults" :key="result.title" :result="result" />
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
    // import {searchUrl} from "@/utils/Constants";
    // import {GET} from "@/utils/Utils";
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
                // GET(searchUrl, {
                //     keyword: this.keyword
                // }, (data) => {
                //     this.searchResults = data.extraData;
                //     if (data.status === -1) {
                //         this.notFound = true;
                //     } else {
                //         this.notFound = false;
                //     }
                // });
                this.searchResults = [{title: 'sample title', description: 'sample description'}];
            }
        },
        mounted() {
            this.search();
        }
    }
</script>

<style scoped>

</style>

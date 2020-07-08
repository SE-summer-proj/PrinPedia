<template>
  <el-container>
    <el-header>
      <Header />
    </el-header>
    <el-aside><Ranking :columns="1" /></el-aside>
    <el-main>
      <SearchBar :keyword="keyWord" />
      <SearchResult v-for="result in searchResults" :key="result" :result="result" />
    </el-main>
  </el-container>
</template>

<script>
    import Header from "@/components/Header";
    import Ranking from "@/components/Ranking";
    import SearchBar from "@/components/SearchBar";
    import SearchResult from "@/components/SearchResult";
    export default {
        name: "ResultPage",
        components: {SearchResult, SearchBar, Ranking, Header},
        data: function () {
            return {
                keyWord: this.$route.params.keyword,
                searchResults: []
            };
        },
        methods: {
            search() {
                // setData: searchResults
            }
        },
        mounted() {
            this.search();
        }
    }
</script>

<style scoped>

</style>

<template>
  <el-container class="search-page">
    <el-header>
      <Header id="header-main" />
    </el-header>
    <el-main>
      <el-row type="flex" justify="center">
        <el-col :span="10">
          <SearchBar id="searchbar-main" style="text-align: center" keyword="" />
        </el-col>
      </el-row>
      <el-row type="flex" justify="center">
        <el-col :span="10">
          <Recommend id="recommend" />
        </el-col>
      </el-row>
      <el-row type="flex" justify="center">
        <el-col :span="10">
          <Ranking :columns="2" />
        </el-col>
      </el-row>
    </el-main>
    <el-footer>
      <Footer />
    </el-footer>
  </el-container>
</template>

<script>
    import SearchBar from "@/components/SearchBar";
    import Recommend from "@/components/Recommend";
    import Header from "@/components/Header";
    import Footer from "@/components/Footer";
    import Ranking from "@/components/Ranking";
    export default {
        name: "SearchPage",
        components: {Ranking, Footer, Header, Recommend, SearchBar}
    }
</script>

<style scoped>
  .search-page {
    display: block;
    word-break: keep-all;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
</style>

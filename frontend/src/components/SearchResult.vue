<template>
  <div id="search-result">
    <el-row>
      <el-col>
        <router-link :to="'/entry/' + result">{{result}}</router-link>
      </el-col>
    </el-row>
  </div>
</template>

<script>
    export default {
        name: "SearchResult",
        props: ['result']
    }
</script>

<style scoped>
  #search-result {
    text-align: left;
  }
</style>

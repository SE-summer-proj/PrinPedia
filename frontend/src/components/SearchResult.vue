<template>
  <div id="search-result">
    <el-row>
      <el-col>
        <router-link :to="'/result/' + result.title"></router-link>
      </el-col>
      <el-col>{{result.description}}</el-col>
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

</style>

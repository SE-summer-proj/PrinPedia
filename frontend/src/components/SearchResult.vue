<template>
  <div id="search-result">
    <router-link :to="'/entry/' + result.title">{{result.title}}</router-link>
    <div style="font-size: small">{{result.summary}}</div>
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
    margin-top: 10px;
    margin-bottom: 10px;
}
</style>

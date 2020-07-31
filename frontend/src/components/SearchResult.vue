<template>
  <div id="search-result">
    <router-link :to="'/entry/' + result.title">{{result.title}}</router-link>
    <div style="font-size: small">{{result.summary}}</div>
  </div>
</template>

<script>
    export default {
        name: "SearchResult",
        props: ['result'],
        // computed: {
        //     summary: function () {
        //         const maxLength = 100;
        //         let summary = this.result.summary;
        //         if (summary.length >= maxLength) {
        //             summary.splice(maxLength);
        //             return summary;
        //         } else return summary;
        //     }
        // }
    }
</script>

<style scoped>
  #search-result {
    text-align: left;
    margin-top: 10px;
    margin-bottom: 10px;
  }
</style>

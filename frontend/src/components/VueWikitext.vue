<template>
  <div class="vue-wikitext" v-html="html" />
</template>

<script>
import wtf from 'wtf_wikipedia';
import htmlPlugin from 'wtf-plugin-html';
wtf.extend(htmlPlugin);

export default {
    name: 'VueWikitext',
    props: {
        source: {
            type: String,
            default: '',
        }
    },
    computed: {
        html() {
            return wtf(this.source).html();
        }
    }
};
</script>

<style scoped>
.vue-wikitext {
}
.vue-wikitext p {
}
</style>

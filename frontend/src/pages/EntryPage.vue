<template>
  <div id="entry-page">
    <el-container>
      <el-header>
        <Header />
      </el-header>
      <el-container>
        <el-aside>
          <el-button @click="$router.back()">返回</el-button>
          <el-button @click="$router.push('/edit/' + entryName)">编辑词条</el-button>
          <Catalog :catalog="wikiData.content" />
        </el-aside>
        <el-main>
          <div class="entry-title">{{wikiData.title}}</div>
          <VueWikitext :source="wikiData.wikiText" />
        </el-main>
      </el-container>
      <el-footer>
        <Footer />
      </el-footer>
    </el-container>
  </div>
</template>

<script>
    import Header from "@/components/Header";
    import Footer from "@/components/Footer";
    import Catalog from "@/components/Catalog";
    import {GET} from "@/ajax";
    import {Constants} from "@/utils/constants";
    import VueWikitext from "@/components/VueWikitext";
    export default {
        name: "EntryPage",
        components: {VueWikitext, Catalog, Footer, Header},
        data: function () {
            return {
                wikiData: null,
                entryName: this.$route.params.entryName
            }
        },
        methods: {
            // getContents() {
            //     return GET(Constants.entryUrl, {
            //         entryName: this.entryName
            //     }, (data) => {
            //         this.wikiData = data.extraData
            //     });
            // }
          getContents() {
            return GET(Constants.entryUrl, {
              entryName: this.entryName
            }, (data) => {
              this.wikiData = data.extraData;
              this.wikiData.wikiText = this.fixWikitext(this.wikiData.wikiText);
            });
          },
          fixWikitext(text) {
            let unfinished = true;
            let start = 0;
            while (unfinished) {
              const index = text.indexOf('a href="./', start) + 8;
              if (index === -1) {
                unfinished = false;
              } else {
                text.splice(index, 1, '#/entry');
                start = index;
              }
            }
            return text;
          }
        },
        mounted() {
            return this.getContents();
        }
    }
</script>

<style scoped>
  .entry-title {
    font-size: large;
    font-weight: bold;
  }
</style>

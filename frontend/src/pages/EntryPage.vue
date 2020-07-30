<template>
  <div id="entry-page">
    <el-container>
      <el-header>
        <Header />
      </el-header>
      <el-container>
        <el-aside>
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
            getContents() {
                return GET(Constants.entryUrl, {
                    entryName: this.entryName
                }, (data) => {
                    this.wikiData = data.extraData
                });
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

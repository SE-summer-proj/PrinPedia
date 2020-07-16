<template>
  <div id="entry-page">
    <el-container>
      <el-header>
        <Header />
      </el-header>
      <el-container>
        <el-aside>
          <Catalog :catalog="contents.catalog" />
        </el-aside>
        <el-main>
          <MainText :contents="contents" />
        </el-main>
      </el-container>
      <el-footer>
        <Footer />
      </el-footer>
    </el-container>
  </div>
</template>

<script>
    import MainText from "@/components/MainText";
    import Header from "@/components/Header";
    import Footer from "@/components/Footer";
    import Catalog from "@/components/Catalog";
    import {GET} from "@/ajax";
    import {entryUrl} from "@/utils/constants";
    export default {
        name: "EntryPage",
        components: {Catalog, Footer, Header, MainText},
        data: function () {
            return {
                contents: [],
                entryName: this.$route.params.entryName
            }
        },
        methods: {
            getContents() {
                GET(entryUrl, {
                    entryName: this.entryName
                }, (data) => {
                    this.contents = data.extraData
                });
            }
        },
        mounted() {
            this.getContents();
        }
    }
</script>

<style scoped>

</style>

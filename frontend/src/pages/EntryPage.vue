<template>
  <el-container>
    <el-header>
      <Header />
    </el-header>
    <el-aside>
      <Catalog :catalog-data="catalog" />
    </el-aside>
    <el-main>
      <MainText :catalog-data="catalog" :contents="text" />
    </el-main>
    <el-footer>
      <Footer />
    </el-footer>
  </el-container>
</template>

<script>
    import Catalog from "@/components/Catalog";
    import MainText from "@/components/MainText";
    import Header from "@/components/Header";
    import Footer from "@/components/Footer";
    export default {
        name: "EntryPage",
        components: {Footer, Header, MainText, Catalog},
        data: function () {
            return {
                catalog: [],
                text: [],
                entryName: this.$route.params.entryName
            }
        },
        methods: {
            getContents() {
                console.log(this.entryName);
                // setData: contents, catalogData
            }
        },
        mounted() {
            this.getContents();
        }
    }
</script>

<style scoped>

</style>

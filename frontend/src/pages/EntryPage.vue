<template>
  <div id="entry-page">
    <el-container>
      <el-header>
        <Header />
      </el-header>
      <el-container>
        <el-aside>
          <el-button @click="dialogVisible = true" type="success">新增段落</el-button>
          <Catalog :catalog-data="contents.catalog" />
        </el-aside>
        <el-main>
          <MainText :contents="contents" />
        </el-main>
      </el-container>
      <el-footer>
        <Footer />
      </el-footer>
    </el-container>
    <el-dialog title="新增段落" :visible.sync="dialogVisible">
      <el-container>
        <el-aside><Catalog :catalog-data="contents.catalog" /></el-aside>
      </el-container>
    </el-dialog>
  </div>
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
                contents: [],
                entryName: this.$route.params.entryName,
                dialogVisible: false
            }
        },
        methods: {
            getContents() {
                console.log(this.entryName);
                // setData: contents
                this.contents = {
                    summary: 'Summary text of ' + this.entryName,
                    catalog: [
                        {
                            label: '1 History',
                            text: 'text...',
                            children: [
                                {
                                    label: '1.1 Ancient History',
                                    text: 'text...'
                                },
                                {
                                    label: '1.2 Modern History',
                                    text: 'text...',
                                }
                            ]
                        },
                        {
                            label: '2 Features',
                            text: 'text...',
                            children: [
                                {
                                    label: '2.1 Advantages',
                                    text: 'text...',
                                    children: [
                                        {
                                            label: '2.1.1 Good water-absorbing quality',
                                            text: 'text...'
                                        },
                                        {
                                            label: '2.1.2 Good air-permability',
                                            text: 'text...'
                                        }
                                    ]
                                },
                                {
                                    label: '2.2 Disadvantages',
                                    text: 'text...'
                                }
                            ]
                        },
                        {
                            label: '3 Principles',
                            text: 'text...'
                        }
                    ]
                };
            }
        },
        mounted() {
            this.getContents();
        }
    }
</script>

<style scoped>

</style>

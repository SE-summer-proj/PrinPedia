<template>
  <div id="entry-page">
    <el-container>
      <el-header>
        <Header />
      </el-header>
      <el-container>
        <el-aside>
          <el-button @click="dialogVisible = true" type="success">开始编辑</el-button>
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
    <el-dialog title="编辑" :visible.sync="dialogVisible">
      Coming Soon...
    </el-dialog>
  </div>
</template>

<script>
    import MainText from "@/components/MainText";
    import Header from "@/components/Header";
    import Footer from "@/components/Footer";
    import Catalog from "@/components/Catalog";
    export default {
        name: "EntryPage",
        components: {Catalog, Footer, Header, MainText},
        data: function () {
            return {
                contents: [],
                entryName: this.$route.params.entryName,
                dialogVisible: false
            }
        },
        methods: {
            getContents() {
                // console.log(this.entryName);
                // setData: contents = extraData
                this.contents = {
                    title: this.entryName,
                    summary: 'Summary text of ' + this.entryName,
                    catalog: [
                        {
                            label: 'History',
                            text: 'text...',
                            children: [
                                {
                                    label: 'Ancient History',
                                    text: 'text...'
                                },
                                {
                                    label: 'Modern History',
                                    text: 'text...',
                                }
                            ]
                        },
                        {
                            label: 'Features',
                            text: 'text...',
                            children: [
                                {
                                    label: 'Advantages',
                                    text: 'text...',
                                    children: [
                                        {
                                            label: 'Good water-absorbing quality',
                                            text: 'text...'
                                        },
                                        {
                                            label: 'Good air-permability',
                                            text: 'text...'
                                        }
                                    ]
                                },
                                {
                                    label: 'Disadvantages',
                                    text: 'text...'
                                }
                            ]
                        },
                        {
                            label: 'Principles',
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

<template>
  <el-container>
    <el-header><Header /></el-header>
    <el-main>
      <el-tabs v-model="activeName">
        <el-tab-pane label="预览" name="preview">
          <VueWikitext :source="wikiData.wikiText" />
        </el-tab-pane>
        <el-tab-pane label="编辑" name="editing">
          <el-input type="textarea" v-model="wikiData.wikiText" :rows="15" />
        </el-tab-pane>
      </el-tabs>
      <el-button @click="submit">提交修改</el-button>
    </el-main>
  </el-container>
</template>

<script>
    import Header from "@/components/Header";
    import {GET, POST} from "@/ajax";
    import {Constants} from "@/utils/constants";
    import VueWikitext from "@/components/VueWikitext";
    export default {
        name: "EditPage",
        components: {VueWikitext, Header},
        data: function () {
            return {
                activeName: 'preview',
                wikiData: null
            }
        },
        methods: {
            getContents() {
                return GET(Constants.entryUrl, {
                    keyword: this.$route.params.entryName
                }, (data) => {
                    this.wikiData = data.extraData;
                })
            },
            submit() {
                return POST(Constants.editUrl, {
                    title: this.wikiData.title,
                    wikiText: this.wikiData.wikiText
                }, (data) => {
                    this.$message.info(data.message);
                });
            }
        },
        mounted() {
            return this.getContents();
        }
    }
</script>

<style scoped>

</style>

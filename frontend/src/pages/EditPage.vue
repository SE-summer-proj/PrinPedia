<template>
  <el-container>
    <el-header><Header /></el-header>
    <el-main>
      <el-tabs v-model="activeName">
        <el-tab-pane label="预览" name="preview">
          <VueWikitext :source="wikiData" />
        </el-tab-pane>
        <el-tab-pane label="编辑" name="editing">
          <el-input type="textarea" v-model="wikiData" :rows="15" />
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>

<script>
    import Header from "@/components/Header";
    import {GET} from "@/ajax";
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
            }
        }
    }
</script>

<style scoped>

</style>

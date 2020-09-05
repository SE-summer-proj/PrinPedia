<template>
  <el-container>
    <el-header><Header /></el-header>
    <el-main>
      <TagSheet :col="8" :editing="false" :tags="allTags" />
    </el-main>
    <el-footer><Footer /></el-footer>
  </el-container>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import TagSheet from "@/components/TagSheet";
import {GET} from "@/ajax";
import {Constants} from "@/utils/constants";
export default {
    name: "TagPage",
    components: {TagSheet, Footer, Header},
    data() {
        return {
            allTags: [],
            selectedTag: ''
        };
    },
    methods: {
        getAllTags() {
            return GET(Constants.allTagUrl, null, (data) => {
                this.allTags = data.extraData;
            });
        }
    }
}
</script>

<style scoped>

</style>

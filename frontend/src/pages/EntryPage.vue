<template>
  <div id="entry-page">
    <el-container>
      <el-header>
        <Header />
      </el-header>
      <el-container>
        <el-aside>
          <el-button-group>
            <el-button @click="$router.back()">返回</el-button>
            <el-button @click="$router.push('/edit/' + entryName)">
              <i class="el-icon-edit" />
              <span>编辑</span>
            </el-button>
            <el-button @click="switchCollection">
              <i class="el-icon-star-on" v-if="isInCollection" />
              <i class="el-icon-star-off" v-else />
              <span>{{isInCollection ? '取消' : '收藏'}}</span>
            </el-button>
          </el-button-group>
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
import {GET, POST} from "@/ajax";
import {Constants} from "@/utils/constants";
import VueWikitext from "@/components/VueWikitext";
export default {
    name: "EntryPage",
    components: {VueWikitext, Catalog, Footer, Header},
    data: function () {
        return {
            wikiData: null,
            entryName: this.$route.params.entryName,
            isInCollection: false
        }
    },
    methods: {
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
        },
        getCollectionStat() {
            return GET(Constants.checkCollectionUrl, {
                username: this.$store.state.userData.username,
                title: this.wikiData.title
            }, (data) => {
                this.isInCollection = data.extraData;
            });
        },
        switchCollection() {
            if (this.$store.state.userData.username === '') {
                this.$message.info('您未登录');
                this.$router.push('/login');
            }
            const url = this.isInCollection ? Constants.removeCollectionUrl : Constants.addCollectionUrl;
            return POST(url, {
                username: this.$store.state.userData.username,
                title: this.wikiData.title
            }, () => {
                this.isInCollection = !(this.isInCollection);
            });
        }
    },
    async mounted() {
        await this.getContents();
        return this.getCollectionStat();
    }
}
</script>

<style scoped>
  .entry-title {
    font-size: large;
    font-weight: bold;
  }
</style>

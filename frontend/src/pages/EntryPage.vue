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
            <el-button @click="switchCollection">
              <i class="el-icon-star-on" v-if="isInCollection" />
              <i class="el-icon-star-off" v-else />
              <span>{{isInCollection ? '取消' : '收藏'}}</span>
            </el-button>
            <el-button @click="$router.push('/edit/' + entryName)" v-if="!wikiData.locked">
              <i class="el-icon-edit" />
              <span>编辑</span>
            </el-button>
            <el-popover v-else placement="right" width="200" trigger="hover">
              <div>
                <span>该词条已被锁定，无法编辑。详情请咨询管理员。去</span>
                <el-button type="text" @click="$router.push('/feedback')">意见箱</el-button>
              </div>
              <el-button slot="reference" disabled>
                <i class="el-icon-edit" />
                <span>编辑</span>
              </el-button>
            </el-popover>
            <el-button v-if="$store.state.userType.indexOf('ROLE_ADMIN') !== -1" @click="lockEntry">
              {{wikiData.locked ? '解锁' : '锁定'}}
            </el-button>
          </el-button-group>
          <Catalog :catalog="wikiData.content" />
        </el-aside>
        <el-main>
          <div class="entry-title">{{wikiData.title}}</div>
          <div class="entry-tags">
            <TagSheet :tags="tags" :col="4" :editing="false" />
          </div>
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
import TagSheet from "@/components/TagSheet";
export default {
    name: "EntryPage",
    components: {TagSheet, VueWikitext, Catalog, Footer, Header},
    data: function () {
        return {
            wikiData: {},
            entryName: this.$route.params.entryName,
            isInCollection: false,
            tags: []
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
                username: this.$store.state.username,
                title: this.wikiData.title
            }, (data) => {
                this.isInCollection = data.extraData;
            });
        },
        switchCollection() {
            if (this.$store.state.username === '') {
                this.$message.info('您未登录');
                this.$router.push('/login');
            }
            const url = this.isInCollection ? Constants.removeCollectionUrl : Constants.addCollectionUrl;
            return POST(url, {
                username: this.$store.state.username,
                title: this.wikiData.title
            }, () => {
                this.isInCollection = !(this.isInCollection);
            });
        },
        getTags() {
            return GET(Constants.tagOfEntryUrl, {
                title: this.entryName
            }, (data) => {
                this.tags = data.extraData;
            });
        },
        lockEntry() {
            return POST(Constants.lockUrl, {
                title: this.wikiData.title,
                lock: !this.wikiData.locked
            }, (data) => {
                if (data.status === 0) {
                    this.$message.success(data.message);
                    this.wikiData.locked = !this.wikiData.locked;
                }
            });
        }
    },
    async mounted() {
        await this.getContents();
        await this.getTags();
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

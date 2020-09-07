<template>
  <el-container>
    <el-header>
      <Header />
    </el-header>
    <el-container>
      <el-main>
        <SearchBar :keyword="keyword" />
        <div class="found" v-if="!notFound">
          <el-button-group>
            <el-button @click="dialogVisible = true">显示推荐</el-button>
<!--            <el-button @click="$router.push('/tags')">全部标签</el-button>-->
          </el-button-group>
          <el-dialog title="推荐词条" :visible.sync="dialogVisible">
            <el-row>
              <el-col :span="18">
                <KnowledgeGraph :central-word="searchResults[0].title" />
              </el-col>
              <el-col :span="6">
                <div>
                  <i class="el-icon-sort-down" />
                  <span>按联系降序排序</span>
                </div>
                <div v-if="relation.children.length !== 0">子词条</div>
                <div v-for="(child, c) in relation.children" :key="c">{{child}}</div>
                <br />
                <div v-if="relation.parents.length !== 0">父词条</div>
                <div v-for="(parent, p) in relation.parents" :key="p">{{parent}}</div>
              </el-col>
            </el-row>
          </el-dialog>
        </div>
        <div class="not-found" v-else-if="notFound">
          没有找到相应词条"{{keyword}}"。您可能想要的是以下结果：
        </div>
        <div class="found">
          <SearchResult v-for="(result, i) in searchResults" :key="i" :result="result" />
        </div>
        <div v-if="notFound">
          <span>没有想要的结果？试试</span>
          <el-button type="text" @click="createEntry">创建词条</el-button>
        </div>
      </el-main>
      <el-aside><Ranking :columns="1" /></el-aside>
    </el-container>
  </el-container>
</template>

<script>
import Header from "@/components/Header";
import Ranking from "@/components/Ranking";
import SearchBar from "@/components/SearchBar";
import SearchResult from "@/components/SearchResult";
import {Constants} from "@/utils/constants";
import {GET,POST} from "@/ajax";
import KnowledgeGraph from "@/components/KnowledgeGraph";
export default {
    name: "ResultPage",
    components: {KnowledgeGraph, SearchResult, SearchBar, Ranking, Header},
    data: function () {
        return {
            keyword: this.$route.params.keyword,
            searchResults: [],
            notFound: true,
            dialogVisible: false,
            relation: {
                current: '',
                children: [],
                parents: []
            }
        };
    },
    methods: {
        search() {
            return GET(Constants.searchUrl, {
                keyword: this.keyword
            }, (data) => {
                // console.log(data)
                this.notFound = (data.status === -1);
                if (this.notFound) this.searchResults = [];
                else this.searchResults = data.extraData;
            });
        },
        createEntry() {
            return POST(Constants.createUrl, {
                keyword: this.keyword
            }, () => {
                this.$router.push('/edit/' + this.keyword);
            });
        },
        getRelation() {
            return GET(Constants.relationUrl, {
                title: this.searchResults[0].title
            }, (data) => {
                this.relation = data;
            });
        },
        async searchAndGetRelation() {
            await this.search();
            return this.getRelation();
        }
    },
    mounted() {
        return this.searchAndGetRelation();
    },
    watch: {
        '$route' () {
            this.keyword = this.$route.params.keyword
            this.searchAndGetRelation();
        }
    },
}
</script>

<style scoped>

</style>

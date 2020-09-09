<template>
  <el-container>
    <el-header><Header /></el-header>
    <el-main>
      <el-row>
        <div id="advanced-search">
          <el-form :model="advancedDetail">
            <el-form-item label="必须全部关键字">
              <el-input v-model="advancedDetail.must" />
            </el-form-item>
            <el-form-item label="包含任意关键字">
              <el-input v-model="advancedDetail.should" />
            </el-form-item>
            <el-form-item label="不可包含关键字">
              <el-input v-model="advancedDetail.mustNot" />
            </el-form-item>
            <el-form-item label="包含完整关键字">
              <el-input v-model="advancedDetail.mustTotal" />
            </el-form-item>
            <el-form-item label="词条名包含关键字">
              <el-input v-model="advancedDetail.mustTitle" />
            </el-form-item>
            <el-form-item>
              <el-button-group>
                <el-button type="primary" @click="advancedSearch">高级搜索</el-button>
                <el-button type="danger" @click="clearCondition">清空条件</el-button>
              </el-button-group>
            </el-form-item>
          </el-form>
        </div>
      </el-row>
      <el-row>
        <SearchResult v-for="(result, i) in searchResults" :key="i" :result="result" />
      </el-row>
    </el-main>
    <el-footer><Footer /></el-footer>
  </el-container>
</template>

<script>
import {POST} from "@/ajax";
import {Constants} from "@/utils/constants";
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import SearchResult from "@/components/SearchResult";

export default {
    name: "AdvancedSearchPage",
    components: {SearchResult, Footer, Header},
    data() {
        return {
            advancedDetail: {
                must: '',
                should: '',
                mustNot: '',
                mustTotal: '',
                mustTitle: ''
            },
            searchResults: []
        };
    },
    methods: {
        clearCondition() {
            this.advancedDetail = {
                must: '',
                should: '',
                mustNot: '',
                mustTotal: '',
                mustTitle: ''
            };
        },
        advancedSearch() {
            return POST(Constants.advancedSearchUrl, this.advancedDetail, (data) => {
                this.searchResults = data.extraData;
            });
        }
    }
}
</script>

<style scoped>

</style>

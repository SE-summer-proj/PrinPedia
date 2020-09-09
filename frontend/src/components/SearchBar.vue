<template>
  <div class="searchbar">
    <el-input
        id="search-input"
        v-model="searchKeyword"
        placeholder="搜点什么……"
        clearable>
      <el-button
          id="search-button"
          type="primary"
          icon="el-icon-search"
          @click="search"
          slot="append"
      >搜索</el-button>
    </el-input>
  </div>
</template>

<script>
export default {
    name: "SearchBar",
    data: function () {
        return {
            searchKeyword: ''
        };
    },
    props: {
        keyword: String
    },
    methods: {
        search() {
            if (this.searchKeyword !== '')
                this.$router.push('/result/' + this.searchKeyword);
        }
    },
    mounted() {
        this.searchKeyword = this.keyword;
    }
}
</script>

<style scoped>
#search-button {
    background-color: aqua;
    color: darkblue;
}
.searchbar {
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    margin-top: 10px;
    margin-bottom: 10px;
}
</style>

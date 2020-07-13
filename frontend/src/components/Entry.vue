<template>
  <el-row>
    <el-col :span="2">{{index}}</el-col>
    <el-col :span="17">
      <span>
        <router-link :to="'/entry/' + entry.word">{{entry.word}}</router-link>
      </span>
    </el-col>
    <el-col :span="2">
      <span class="hot" v-if="entry.type === '热'">{{entry.type}}</span>
      <span class="new" v-else-if="entry.type === '新'">{{entry.type}}</span>
      <span class="recommend" v-else-if="entry.type === '荐'">{{entry.type}}</span>
      <span v-else style="color: transparent">--</span>
    </el-col>
    <el-col :span="3">
      <span v-if="entry.change > 0" class="up">
        <i class="el-icon-caret-top" />
        <span>{{entry.change}}</span>
      </span>
      <span v-else-if="entry.change < 0" class="down">
        <i class="el-icon-caret-bottom" />
        <span>{{entry.change}}</span>
      </span>
      <span v-else>
        <span>--</span>
      </span>
    </el-col>
  </el-row>
</template>

<script>
    export default {
        name: "Entry",
        props: ['index', 'entry']
    }
</script>

<style scoped>
  .hot {
    background-color: red;
    margin-left: 5px;
  }
  .recommend {
    background-color: aqua;
    margin-left: 5px;
  }
  .new {
    background-color: limegreen;
    margin-left: 5px;
  }
  .up {
    color: red;
  }
  .down {
    color: green;
  }
</style>

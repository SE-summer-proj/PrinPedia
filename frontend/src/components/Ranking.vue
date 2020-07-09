<template>
  <div class="ranking">
    <el-row>
      <el-col>百科热榜</el-col>
    </el-row>
    <el-row v-if="columns === 2">
      <el-col :span="12" class="rk-col">
        <el-row>
          <el-col v-for="i in (hotWords.length / 2)" :key="i">
            <Entry :index="i" :entry="hotWords[i - 1]" />
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="12" class="rk-col">
        <el-row>
          <el-col v-for="i in (hotWords.length / 2)" :key="i">
            <Entry :index="i + (hotWords.length / 2)" :entry="hotWords[i - 1 + (hotWords.length / 2)]" />
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-row v-if="columns === 1">
      <el-col :span="24" class="rk-col">
        <el-row>
          <el-col v-for="i in hotWords.length" :key="i">
            <Entry :index="i" :entry="hotWords[i - 1]" />
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
    import Entry from "@/components/Entry";
    export default {
        name: "Ranking",
        components: {Entry},
        data: function () {
            return {
                hotWords: []
            };
        },
        props: ['columns'],
        methods: {
            getRanking() {
                // setData: hotWords
                this.hotWords = [
                    {
                        word: '上海交大',
                        type: '新',
                        change: 1
                    },
                    {
                        word: '软件学院',
                        type: '热',
                        change: -1
                    },
                    {
                        word: '学费',
                        type: '',
                        change: 2
                    },
                    {
                        word: '大减价',
                        type: '荐',
                        change: 0
                    }
                ];
            }
        },
        mounted() {
            this.getRanking();
        }
    }
</script>

<style scoped>
  .rk-col {
    border: 1px solid darkblue;
    border-radius: 4px;
  }
</style>

<template>
  <div>
    <div id="tags">
      <el-row v-for="i in numOfRows" :key="i">
        <el-col :span="24 / col" v-for="j in col" :key="j">
          <el-tag :closable="editing" @close="handleClose(i, j)" @click="handleClick(i, j)">
            {{tags[getIndex(i, j)]}}
          </el-tag>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24 / col" v-for="k in (tags.length % col)" :key="k">
          <el-tag
              :closable="editing"
              @close="handleClose(numOfRows + 1, k)"
              @click="handleClick(numOfRows + 1, k)">
            {{tags[getIndex(numOfRows + 1, k)]}}
          </el-tag>
        </el-col>
      </el-row>
    </div>
    <div v-if="editing">
      <el-input
          class="input-new-tag"
          v-if="inputVisible"
          v-model="inputValue"
          ref="saveTagInput"
          size="small"
          @keyup.enter.native="handleInputConfirm"
          @blur="handleInputConfirm"/>
      <el-button v-else class="button-new-tag" size="small" @click="showInput">+添加标签</el-button>
    </div>
    <div v-else>
      <el-dialog title="词条一览">
        <span v-for="(result, i) in results" style="margin-right: 10px" :key="i">
          <router-link :to="'/entry/' + result">{{result}}</router-link>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import {GET, POST} from "@/ajax";
import {Constants} from "@/utils/constants";

export default {
    name: "TagSheet",
    props: {
        tags: Array,
        col: {
            type: Number,
            default: 4
        },
        editing: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            inputVisible: false,
            inputValue: '',
            dialogVisible: false,
            results: []
        };
    },
    methods: {
        getIndex(i, j) {
            return 4 * (i - 1) + (j - 1);
        },
        handleClose(i, j) {
            const index = this.getIndex(i, j);
            this.tags.splice(index, 1);
        },
        handleClick(i, j) {
            const index = this.getIndex(i, j);
            return GET(Constants.entryOfTagUrl, {
                tagName: this.tags[index]
            }, (data) => {
                this.results = data.extraData;
                this.dialogVisible = true;
            });
        },
        showInput() {
            this.inputVisible = true;
            this.$nextTick(() => {
                this.$refs.saveTagInput.$refs.input.focus();
            });
        },
        async handleInputConfirm() {
            let inputValue = this.inputValue;
            this.inputVisible = false;
            this.inputValue = '';
            if (inputValue) {
                this.tags.push(inputValue);
                await POST(Constants.createTagUrl, {
                    tagName: inputValue
                }, () => {});
            }
        }
    },
    computed: {
        numOfRows() {
            const mod = this.tags.length % this.col;
            return (this.tags.length - mod) / this.col;
        }
    }
}
</script>

<style scoped>

</style>

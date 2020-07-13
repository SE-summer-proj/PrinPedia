<template>
  <div id="main-text">
    <div>
      <div>
        <span class="title">{{contents.title}}</span>
        <span>
          <el-button type="text" size="mini" icon="el-icon-edit">编辑</el-button>
        </span>
      </div>
      <div class="summary">{{contents.summary}}</div>
    </div>
    <el-collapse v-model="activeNames">
      <el-collapse-item
          v-for="(item, i) in contents.catalog" :key="i"
          :title="getTitle(item.label, i)">
        <div class="paragraph">{{item.text}}</div>
        <div v-for="(subItem, j) in item.children" :key="j">
          <div class="sub-title">
            {{getTitle(subItem.label, i, j)}}
          </div>
          <div class="paragraph">{{subItem.text}}</div>
          <div v-for="(subSubItem, k) in subItem.children" :key="k">
            <div class="sub-sub-title">
              {{getTitle(subSubItem.label, i, j, k)}}
            </div>
            <div class="paragraph">{{subSubItem.text}}</div>
          </div>
        </div>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
    export default {
        name: "MainText",
        data: function () {
            return {
                activeNames: [],
            }
        },
        props: ['contents'],
        methods: {
            getTitle(label, i, j, k) {
                let index = (i+1).toString(10);
                if (j != null) index += ('.' + (j+1).toString(10));
                if (k != null) index += ('.' + (k+1).toString(10));
                return index + ' ' + label;
            }
        }
    }
</script>

<style scoped>
  #main-text {
    text-align: left;
  }
</style>

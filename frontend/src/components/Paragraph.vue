<template>
  <div id="paragraph">
    <el-row>
      <el-col :span="20">
        <div class="paragraph-title">{{title}}</div>
        <div class="paragraph-body" v-if="!isEditing">{{text}}</div>
        <div v-else>
          <el-input type="textarea" v-model="buffer" />
        </div>
      </el-col>
      <el-col :span="4">
        <div v-if="isEditMode">
          <el-button-group v-if="!isEditing">
            <el-button @click="triggerEdit" size="mini" type="primary">编辑</el-button>
          </el-button-group>
          <el-button-group v-else>
            <el-button type="primary" @click="submitEdit" size="mini">提交</el-button>
            <el-button @click="cancelEdit" size="mini" type="danger">取消</el-button>
          </el-button-group>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
    export default {
        name: "Paragraph",
        data: function () {
            return {
                isEditing: false,
                buffer: ''
            };
        },
        props: {
            title: String,
            text: String,
            isEditMode: Boolean,
            index: String
        },
        methods: {
            triggerEdit() {
                this.buffer = this.text;
                this.isEditing = true;
            },
            cancelEdit() {
                this.buffer = '';
                this.isEditing = false;
            },
            submitEdit() {
                this.text = this.buffer;
                console.log(this.index);
            }
        }
    }
</script>

<style scoped>
  #paragraph {
    text-align: left;
    font-size: medium;
    margin: 10px 10px 10px 10px;
  }
  .paragraph-title {
    font-weight: bold;
    font-size: larger;
  }
</style>

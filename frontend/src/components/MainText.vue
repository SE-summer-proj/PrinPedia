<template>
  <div id="main-text">
    <el-row>
      <el-col :span="21">
        <Paragraph
            index="summary"
            :is-edit-mode="isEditing"
            :text="contents.summary"
            title="Summary" />
      </el-col>
      <el-col :span="3">
        <el-button @click="changeEditState" type="primary">{{isEditing ? '结束编辑' : '编辑正文'}}</el-button>
      </el-col>
    </el-row>
    <el-collapse v-model="activeNames">
      <el-collapse-item
          v-for="catalogItem in contents.catalog"
          :key="catalogItem.label"
          :title="catalogItem.label">
        <Paragraph
            :index="catalogItem.label"
            :is-edit-mode="isEditing"
            :text="catalogItem.text"
            :title="''" />
        <div v-for="subItem in catalogItem.children" :key="subItem.label">
          <Paragraph
              :index="subItem.label"
              :is-edit-mode="isEditing"
              :text="subItem.text"
              :title="subItem.label" />
          <div v-for="subSubItem in subItem.children" :key="subSubItem.label">
            <SubParagraph
                :index="subSubItem.label"
                :is-edit-mode="isEditing"
                :text="subSubItem.text"
                :title="subSubItem.label" />
          </div>
        </div>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
    import Paragraph from "@/components/Paragraph";
    import SubParagraph from "@/components/SubParagraph";
    export default {
        name: "MainText",
        components: {SubParagraph, Paragraph},
        data: function () {
            return {
                activeNames: [],
                isEditing: false
            }
        },
        props: ['contents'],
        methods: {
            changeEditState() {
                this.isEditing = !this.isEditing;
            }
        }
    }
</script>

<style scoped>
  #main-text {
    text-align: left;
  }
</style>

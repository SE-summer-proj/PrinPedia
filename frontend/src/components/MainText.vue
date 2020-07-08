<template>
  <div>
    <el-button @click="changeEditState">{{isEditing ? '结束编辑' : '开始编辑'}}</el-button>
    <Paragraph :is-edit-mode="isEditing" :text="contents.summary" />
    <el-collapse v-model="activeNames">
      <el-collapse-item
          v-for="catalogItem in contents.catalog"
          :key="catalogItem.label"
          :title="catalogItem.label">
        <Paragraph :is-edit-mode="isEditing" :text="catalogItem.text" />
        <div v-for="subItem in catalogItem.children" :key="subItem.label">
          <Paragraph :is-edit-mode="isEditing" :text="subItem.text" />
          <div v-for="subSubItem in subItem.children" :key="subSubItem.label">
            <Paragraph :is-edit-mode="isEditing" :text="subSubItem.text" />
          </div>
        </div>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
    import Paragraph from "@/components/Paragraph";
    export default {
        name: "MainText",
        components: {Paragraph},
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

</style>

<template>
  <div>
    <div v-if="isEditing">{{text}}</div>
    <div v-else>
      <el-input type="textarea" v-model="text" />
    </div>
    <div v-if="isEditMode"></div>
    <el-button v-if="isEditing" @click="triggerEdit">编辑</el-button>
    <el-button-group v-else>
      <el-button type="primary" @click="submitEdit">提交</el-button>
      <el-button @click="cancelEdit">取消</el-button>
    </el-button-group>
  </div>
</template>

<script>
    export default {
        name: "Paragraph",
        data: function () {
            return {
                isEditing: false
            };
        },
        props: ['text', 'isEditMode'],
        methods: {
            triggerEdit() {
                this.isEditing = true;
            },
            cancelEdit() {
                this.isEditing = false;
            },
            submitEdit() {
                //
            }
        }
    }
</script>

<style scoped>

</style>

<template>
  <el-tree :data="catalogData" :props="defaultProps" @node-click="handleNodeClick" />
</template>

<script>
    export default {
        name: "Catalog",
        data: function () {
            return {
                catalog: [],
                defaultProps: {
                    children: 'children',
                    label: 'label'
                }
            };
        },
        props: ['catalogData'],
        methods: {
            handleNodeClick(data) {
                console.log(data);
            }
        }
    }
</script>

<style scoped>

</style>

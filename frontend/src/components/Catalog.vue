<template>
  <el-tree :data="catalog" :props="defaultProps" default-expand-all />
</template>

<script>
    export default {
        name: "Catalog",
        props: {
            catalog: Array
        },
        data: function () {
            return {
                defaultProps: {
                    children: 'children',
                    label: 'label'
                }
            };
        },
        methods: {
            getTitle(label, i, j, k) {
                let index = (i+1).toString(10);
                if (j != null) index += ('.' + (j+1).toString(10));
                if (k != null) index += ('.' + (k+1).toString(10));
                return index + ' ' + label;
            },
            formatTitle(catalog) {
                let formattedCatalog = catalog;
                for (let i = 0; i < catalog.length; ++i) {
                    formattedCatalog[i].label = this.getTitle(catalog[i].label, i);
                    let subItems = formattedCatalog[i].children;
                    for (let j = 0; j < subItems.length; ++j) {
                        subItems[j].label = this.getTitle(subItems[j].label, i, j);
                        let subSubItems = subItems[j].children;
                        for (let k = 0; k < subSubItems.length; ++k) {
                            subSubItems[k].label = this.getTitle(subSubItems[k].label, i, j, k);
                        }
                        subItems[j].children = subSubItems;
                    }
                    formattedCatalog[i].children = subItems;
                }
                return formattedCatalog;
            }
        }
    }
</script>

<style scoped>

</style>

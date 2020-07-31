<template>
    <el-tabs :active-name="activeName" @tab-click="getData">
        <el-tab-pane name="unchecked" label="未读">
            <el-table :data="unchecked">
                <el-table-column prop="id" label="申请编号" />
                <el-table-column prop="date" label="申请时间" />
                <el-table-column label="词条详情">
                    <template slot-scope="scope">
                        <router-link :to="'/compare/' + scope.row.id">{{scope.row.title}}</router-link>
                    </template>
                </el-table-column>
            </el-table>
        </el-tab-pane>
        <el-tab-pane name="checked" label="已读">
            <el-table :data="checked">
                <el-table-column prop="id" label="申请编号" />
                <el-table-column prop="date" label="申请时间" />
                <el-table-column label="词条详情">
                    <template slot-scope="scope">
                        <router-link :to="'/userLogDetail/' + scope.row.id">{{scope.row.title}}</router-link>
                    </template>
                </el-table-column>
                <el-table-column label="状态">
                    <template slot-scope="scope">{{getStatusDesc(scope.row.status)}}</template>
                </el-table-column>
            </el-table>
        </el-tab-pane>
    </el-tabs>
</template>

<script>
    import {GET} from "@/ajax";
    import {Constants} from "@/utils/constants";

    export default {
        name: "EditAdmin",
        data: function () {
            return {
                activeName: 'unchecked',
                checked: [],
                unchecked: []
            };
        },
        methods: {
            getChecked() {
                return GET(Constants.adminLogUrl, {
                    examined: true
                }, (data) => {
                    this.checked = data.extraData;
                });
            },
            getUnchecked() {
                return GET(Constants.adminLogUrl, {
                    examined: false
                }, (data) => {
                    this.unchecked = data.extraData;
                });
            },
            getStatusDesc(status) {
                switch (status) {
                    case 0: return '未审核';
                    case 1: return '已通过';
                    case 2: return '未通过';
                    default: return '';
                }
            },
            async getData() {
                await this.getChecked();
                return this.getUnchecked();
            }
        },
        mounted() {
            return this.getData();
        }
    }
</script>

<style scoped>

</style>

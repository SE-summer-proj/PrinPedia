<template>
  <div id="graph" />
</template>

<script>
    import echarts from "echarts/lib/echarts";
    import "echarts/lib/chart/graph";
    import {Constants} from "@/utils/constants";
    import {GET} from "@/ajax";

    export default {
        name: 'KnowledgeGraph',
        data() {
            return {
                graph: "",
                pointData: [],
                linkData: [],
                categoryData: [],
                options: {
                    title: {
                        text: "KnowledgeGraph"
                    },
                    series: [
                        {
                            name: "KnowledgeGraph",
                            type: "graph",
                            layout: "force",
                            force: {
                                repulsion: 60,
                                gravity: 0.1,
                                edgeLength: 15,
                                layoutAnimation: true
                            },
                            data: [],
                            links: [],
                            categories: [],
                            roam: false,
                            symbol: 'circle',
                            symbolSize: 40,
                            cursor: 'pointer',
                            label: {
                                normal: {
                                    show: true,
                                    position: "inside",
                                    formatter: "{b}",
                                    fontSize: 16,
                                    fontStyle: "600"
                                }
                            },
                            lineStyle: {
                                normal: {
                                    opacity: 0.9,
                                    width: 1.5,
                                    curveness: 0
                                }
                            }
                        }
                    ]
                }
            };
        },
        props: {
            centralWord: String
        },
        methods: {
            setPointData(list, category) {
                list.forEach((name, index) => {
                    this.pointData.push({
                        x: index * 50,
                        y: 20 + index * 50,
                        name,
                        category,
                        draggable: true
                    });
                });
            },
            setLinkData(list, target) {
                list.forEach(source => {
                    this.linkData.push({
                        source,
                        target,
                        lineStyle: {
                            normal: {
                                color: "source"
                            }
                        }
                    });
                });
            },
            setCategoryData(list) {
                list.forEach(name => {
                    this.categoryData.push({ name });
                });
            },
            getSourceData: function () {
                // this.keyword = "当前词条";
                // this.pointList1 = ["父亲", "关联1", "关联2", "关联3", "关联4"];
                // this.pointList2 = ["孩子", "关联5", "关联6", "关联7", "关联8"];
                // this.pointList3 = [this.keyword];

              console.log(this.centralWord);
              return GET(Constants.graphUrl, {
                    title: this.centralWord
                }, (data) => {
                console.log(data)
                    this.pointList1 = data.parents;
                    this.pointList2 = data.children;
                    this.pointList3 = [this.centralWord];
                });
            }
        },
        async mounted() {

            await this.getSourceData();
            this.graph = echarts.init(document.getElementById("graph"));
            this.setPointData(this.pointList1, this.pointList1[0]);
            this.setPointData(this.pointList2, this.pointList2[0]);
            this.setPointData(this.pointList3, this.pointList3[0]);
            this.setLinkData(this.pointList1, this.pointList1[0]);
            this.setLinkData(this.pointList2, this.pointList2[0]);
            this.setLinkData([this.pointList1[0], this.pointList2[0]], this.pointList3[0]);
            this.setCategoryData([this.pointList1[0], this.pointList2[0],  this.pointList3[0]]);
            this.options.series[0].data = this.pointData;
            this.options.series[0].links = this.linkData;
            this.options.series[0].categories = this.categoryData;
            // this.options.legend.data = this.categoryData;
            this.$nextTick(() => {
                window.console.log(this.options);
                this.graph.setOption(this.options);
            });
        }
    };
</script>

<style scoped>
  #graph {
    width: 100%;
    height: 500px;
  }
</style>

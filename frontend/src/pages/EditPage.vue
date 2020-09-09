<template>
  <el-container>
    <el-header><Header /></el-header>
    <el-main>
      <div id="locked" v-if="wikiData.locked">
        <span>本词条已被锁定，无法编辑。详情请咨询管理员。</span>
        <el-button-group>
          <el-button type="text" @click="$router.push('/feedback')">意见箱</el-button>
          <el-button type="text" @click="$router.back()">返回</el-button>
        </el-button-group>
      </div>
      <div id="editable" v-else>
        <div id="edit-tags">
          <div>编辑标签</div>
          <TagSheet :editing="true" :col="4" :tags="tags" />
          <el-button-group>
            <el-button type="primary" @click="editTags">提交更改</el-button>
            <el-button @click="cancelEdit">取消更改</el-button>
          </el-button-group>
        </div>
        <el-tabs v-model="activeName">
          <el-tab-pane label="预览" name="preview">
            <VueWikitext :source="wikiData.wikiText" />
          </el-tab-pane>
          <el-tab-pane label="编辑" name="editing">
            <el-input type="textarea" v-model="wikiData.wikiText" :rows="25" />
          </el-tab-pane>
        </el-tabs>
        <el-button-group>
          <el-button @click="submit">提交修改</el-button>
          <el-button @click="$router.back()">取消并返回</el-button>
        </el-button-group>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import Header from "@/components/Header";
import {GET, POST} from "@/ajax";
import {Constants} from "@/utils/constants";
import VueWikitext from "@/components/VueWikitext";
import axios from "axios";
import TagSheet from "@/components/TagSheet";
export default {
    name: "EditPage",
    components: {TagSheet, VueWikitext, Header},
    data: function () {
        return {
            activeName: 'preview',
            wikiData: {},
            tags: []
        }
    },
    methods: {
        getTags() {
            return GET(Constants.tagOfEntryUrl, {
                title: this.$route.params.entryName
            }, (data) => {
                this.tags = data.extraData;
            });
        },
        getContents() {
            return GET(Constants.entryUrl, {
                entryName: this.$route.params.entryName
            }, (data) => {
                // console.log(data)
                this.wikiData = data.extraData;
            })
        },
        submit() {
            return axios
                .post(Constants.editUrl,{
                        title: this.wikiData.title,
                        wikiText: this.wikiData.wikiText},
                    {
                        headers: {
                            // 'X-CSRFToken':this.getCookie('csrftoken'),
                            'Content-type':"application/json",
                            'Cookie':'JSESSIONID=9B2A0A4CE4D5B4F4FBA5F38C950F3BB0'
                        }
                    },
                )
                .then((res) => {
                    if (res.status === 200) {
                        this.$message.info(res.data.message);
                    } else {
                        console.log(res.status, res.statusText);
                    }
                })
                .catch((error) => {
                    console.log(error);
                });
        },
        editTags() {
            return POST(Constants.editTagUrl, {
                title: this.$route.params.entryName,
                tagList: this.tags
            }, (data) => {
                if (data.status === 0) {
                    this.$message.success(data.message);
                }
            });
        },
        cancelEdit() {
            return this.getTags();
        }
    },
    async mounted() {
        await this.getContents();
        return this.getTags();
    }
}
</script>

<style scoped>

</style>

<template>
  <el-container>
    <el-header><Header /></el-header>
    <el-main>
      <el-tabs v-model="activeName">
        <el-tab-pane label="预览" name="preview">
          <VueWikitext :source="wikiData.wikiText" />
        </el-tab-pane>
        <el-tab-pane label="编辑" name="editing">
          <el-input type="textarea" v-model="wikiData.wikiText" :rows="25" />
        </el-tab-pane>
      </el-tabs>
      <el-button @click="submit">提交修改</el-button>
    </el-main>
  </el-container>
</template>

<script>
    import Header from "@/components/Header";
    import {GET} from "@/ajax";
    // import {POST} from "@/ajax";
    import {Constants} from "@/utils/constants";
    import VueWikitext from "@/components/VueWikitext";
    import axios from "axios";
    export default {
        name: "EditPage",
        components: {VueWikitext, Header},
        data: function () {
            return {
                activeName: 'preview',
                wikiData: null
            }
        },
        methods: {
            getContents() {
                return GET(Constants.entryUrl, {
                    entryName: this.$route.params.entryName
                }, (data) => {
                    // console.log(data)
                    this.wikiData = data.extraData;
                })
            },
            submit() {
              // return POST(Constants.editUrl, {
              //   title: this.wikiData.title,
              //   wikiText: this.wikiData.wikiText,
              // }, (data) => {
              //   console.log(data)
              //   this.$message.info(data.message);
              // });

              return axios
                      .post(Constants.editUrl,{
                        title: this.wikiData.title,
                        wikiText: this.wikiData.wikiText},
                  {headers: {
                          // 'X-CSRFToken':this.getCookie('csrftoken'),
                          'Content-type':"application/json",
                          'Cookie':'JSESSIONID=9B2A0A4CE4D5B4F4FBA5F38C950F3BB0'}
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
            }
            // submit() {
            //   var params = new URLSearchParams();
            //   params.append("title", this.wikiData.title);
            //   params.append("wikiText", this.wikiData.wikiText);
            //   return axios.post("localhost:9090/entry/edit/request", params)
            //   .then(data => {
            //     console.log(data)
            //     this.$message.info(data.message);
            //   });
            // }
        },
        mounted() {
          this.getContents()
        }
    }
</script>

<style scoped>

</style>

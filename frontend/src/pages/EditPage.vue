<template>
  <el-container>
    <el-header>
      <Header />
    </el-header>
    <el-container>
      <el-aside>
        <el-button-group>
          <el-button type="success" @click="showAppendDialog" :disabled="!appendOK || selected.data === null">
            添加段落
          </el-button>
          <el-button type="danger" @click="remove" :disabled="selected.data === null">
            删除段落
          </el-button>
        </el-button-group>
        <el-dialog title="添加子段" :visible.sync="appendDialogVisible">
          <div>
            <el-input v-model="newChild.label" aria-placeholder="编辑标题" @input="checkDup" />
            <span v-if="newChild.label === ''" style="color: red">
              <i class="el-icon-error" />段落标题不能为空
            </span>
            <span v-else-if="dup" style="color: red">
              <i class="el-icon-error" />段落标题重复
            </span>
          </div>
          <el-input v-model="newChild.text" type="textarea" aria-placeholder="编辑段落" />
          <el-button-group>
            <el-button type="primary" @click="append" :disabled="dup || (newChild.label === '')">确定</el-button>
            <el-button @click="appendDialogVisible = false">取消</el-button>
          </el-button-group>
        </el-dialog>
        <el-tree
            draggable default-expand-all
            :expand-on-click-node="false"
            :data="contents.catalog"
            @node-click="handleNodeClick" />
        <el-button-group>
          <el-button type="primary" @click="submit">提交修改</el-button>
          <el-button @click="cancel">取消修改</el-button>
        </el-button-group>
      </el-aside>
      <el-main>
        <el-tabs v-model="activeName">
          <el-tab-pane label="预览" name="preview">
            <MainText :contents="contents" />
          </el-tab-pane>
          <el-tab-pane label="编辑" name="editing">
            <div v-if="selected.data !== null">
              <el-input v-model="selected.data.label" aria-placeholder="编辑标题" />
              <el-input v-model="selected.data.text" type="textarea" :rows="10" aria-placeholder="编辑段落" />
            </div>
            <div v-else>
              <div style="color: gray; font-size: smaller">请选择段落</div>
            </div>
          </el-tab-pane>
        </el-tabs>
      </el-main>
    </el-container>
    <el-footer>
      <Footer />
    </el-footer>
  </el-container>
</template>

<script>
    import {contents} from "../../tests/test_data";
    import Header from "@/components/Header";
    import Footer from "@/components/Footer";
    import MainText from "@/components/MainText";
    import {GET, POST} from "@/ajax";
    import {editUrl, entryUrl} from "@/utils/constants";

    export default {
        name: "EditPage",
        components: {MainText, Footer, Header},
        data: function () {
            return {
                entryName: this.$route.params.entryName,
                contents: null,
                activeName: 'preview',
                selected: {
                    data: null,
                    node: null
                },
                appendDialogVisible: false,
                dup: true,
                newChild: {
                    label: '',
                    text: '',
                    children: []
                },
                appendOK: false
            }
        },
        methods: {
            getContents() {
                GET(entryUrl, {
                    entryName: this.entryName
                }, (data) => {
                    this.contents = data.extraData
                });
            },
            handleNodeClick(data, node) {
                this.activeName = 'editing';
                this.appendOK = (node.level !== 3);
                this.selected.data = data;
                this.selected.node = node;
            },
            showAppendDialog() {
                this.newChild = {
                    label: '',
                    text: '',
                    children: []
                };
                this.appendDialogVisible = true;
            },
            checkDup() {
                if (!this.selected.data.children) {
                    this.dup = false;
                } else {
                    const index = this.selected.data.children.findIndex(
                        (d) => d.label === this.newChild.label
                    );
                    this.dup = (index !== -1);
                }
            },
            append() {
                if (!this.selected.children) {
                    this.$set(this.selected.data, 'children', []);
                }
                this.selected.data.children.push(this.newChild);
                this.appendDialogVisible = false;
            },
            remove() {
                const parent = this.selected.node.parent;
                const children = parent.data.children || parent.data;
                const index = children.findIndex((d) => d.label === this.selected.data.label);
                children.splice(index, 1);
                this.activeName = 'preview';
                this.selected.node = null;
                this.selected.data = null;
            },
            submit() {
                POST(editUrl, { contents: this.contents }, (data) => {
                    this.contents = data.extraData;
                    this.$router.push('/entry/' + this.entryName);
                })
            },
            cancel() {
                const msg = '是否提交修改？';
                const title = '确认';
                const config = {
                    distinguishCancelAndClose: true,
                    confirmButtonText: '提交修改',
                    cancelButtonText: '放弃修改'
                }
                this.$confirm(msg, title, config)
                    .then(() => {
                        this.submit();
                    })
                    .catch((action) => {
                        if (action === 'cancel')
                            this.$router.push('/entry/' + this.entryName);
                    });
            }
        },
        mounted() {
            this.getContents();
        }
    }
</script>

<style scoped>

</style>

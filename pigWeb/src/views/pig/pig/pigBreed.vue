<template>
  <!-- 配种页面 -->
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公猪代号" prop="pigId">
        <el-input
          v-model="queryParams.pigId"
          placeholder="请输入公猪代号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
     <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['pig:pig:add']"
        >新增</el-button>
      </el-col>
<!--      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['pig:pig:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['pig:pig:remove']"
        >删除</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pig:pig:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="pigList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="家猪id" align="center" prop="pigId" /> -->
      <el-table-column label="配种公猪代号" align="center" prop="pigId" />
      <el-table-column label="公猪性别" align="center" prop="pigSex" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.pigSex == 1">母</el-tag>
          <el-tag v-else>公</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="公猪年龄" align="center" prop="pigAge" />
    <!--  <el-table-column label="家猪状态" align="center" prop="pigStatus" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.pigStatus ==0 ">空闲中</el-tag>
          <el-tag v-else-if="scope.row.pigStatus==1">配种中</el-tag>
          <el-tag v-else>分娩中</el-tag>
        </template>
      </el-table-column> -->
      <el-table-column label="配种母猪代号" align="center" prop="pigPigid" />
      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
          v-if="scope.row.pigSex==1"
            size="medium"
            type="text"
            icon="el-icon-add"
            @click="pigPeizhong(scope.row)"
            v-hasPermi="['pig:pig:remove']"
          >分娩</el-button>
          <el-button
          v-if="scope.row.pigSex==2"
            size="medium"
            type="text"
            icon="el-icon-add"
            @click="pigPeizhong(scope.row)"
            v-hasPermi="['pig:pig:remove']"
          >休息</el-button>
        </template>
      </el-table-column> -->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改pig对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="公猪代号" prop="pigId">
              <el-select v-model="form.pigId" placeholder="请选择公猪" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in manPigList" :key="index" :label="item.pigId"
                  :value="item.pigId" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
         <el-col :span="24">
           <el-form-item label="母猪代号" prop="pigPigid">
             <el-select v-model="form.pigPigid" placeholder="请选择母猪" clearable :style="{width: '100%'}">
               <el-option v-for="(item, index) in womanPigList" :key="index" :label="item.pigId"
                 :value="item.pigId" :disabled="item.disabled"></el-option>
             </el-select>
           </el-form-item>
         </el-col>
        <!--  <el-col :span="24">
            <el-form-item label="性别" prop="pigSex">
              <el-select v-model="form.pigSex" placeholder="请选择性别" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in sexList" :key="index" :label="item.label"
                  :value="item.value" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col> -->
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPig, getPig, delPig, addPig, updatePig } from "@/api/pig/pig"
import { pigChangeStatus } from "../../../api/pig/pig"

export default {
  name: "Pig",
  data() {
    return {
      sexList:[
        {
          "label":"母",
          "value":"1"
        },
        {
          "label":"公",
          "value":"2"
        }
      ],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // pig表格数据
      pigList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      manPigList:[],
      womanPigList:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pigId:null,
        pigName: null,
        pigSex: 2,
        pigAge: null,
        pigStatus: 1,
        pigPigid: null
      },
      // 查询公猪
      queryManPigParams: {
        // pageNum: 1,
        // pageSize: 10,
        pigName: null,
        pigSex: 2,
        pigAge: null,
        pigStatus: 0,
        pigPigid: null
      },
      // 查询母猪
      queryWomanPigParams: {
        // pageNum: 1,
        // pageSize: 10,
        pigName: null,
        pigSex: 1,
        pigAge: null,
        pigStatus: 0,
        pigPigid: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    }
  },
  created() {
    this.init()
  },
  watch: {
    '$route'(to, from) {
      this.init()
    }
  },
  methods: {
    pigPeizhong(row){

    },
    init(){
      this.getList()
      this.getManList()
      this.getWomanList()
    },
    getManList(){
      listPig(this.queryManPigParams).then(res=>{
        this.manPigList=res.rows
        console.log(this.manPigList);
      })
    },
    getWomanList(){
      listPig(this.queryWomanPigParams).then(res=>{
        this.womanPigList=res.rows
        console.log(this.womanPigList);
      })
    },
    /** 查询pig列表 */
    getList() {
      this.loading = true
      listPig(this.queryParams).then(response => {
        this.pigList = response.rows
        this.total = response.total
        this.loading = false
        // 页面刷新
        // location.reload()
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        pigId: null,
        pigName: null,
        pigSex: null,
        pigAge: null,
        pigStatus: null,
        pigPigid: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.init()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.pigId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加配种记录"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const pigId = row.pigId || this.ids
      getPig(pigId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改pig"
      })
    },
    /** 提交按钮 */
    submitForm() {
      console.log(this.form);
      this.form.pigStatus="1"
      pigChangeStatus(this.form).then(res=>{
        pigChangeStatus({
        pigId: this.form.pigPigid,
        pigName: null,
        pigSex: null,
        pigAge: null,
        pigStatus: "1",
        pigPigid: null
      }).then(res=>{
                this.$modal.msgSuccess("新增成功")
                this.open = false
                this.init()
                
      })
      })
      // this.$refs["form"].validate(valid => {
      //   if (valid) {
      //     if (this.form.pigId != null) {
      //       updatePig(this.form).then(response => {
      //         this.$modal.msgSuccess("修改成功")
      //         this.open = false
      //         this.getList()
      //       })
      //     } else {
      //       console.log(this.form);
      //       // addPig(this.form).then(response => {
      //       //   this.$modal.msgSuccess("新增成功")
      //       //   this.open = false
      //       //   this.getList()
      //       // })
      //     }
      //   }
      // })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const pigIds = row.pigId || this.ids
      this.$modal.confirm('是否确认删除pig编号为"' + pigIds + '"的数据项？').then(function() {
        return delPig(pigIds)
      }).then(() => {
        this.init()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pig/pig/export', {
        ...this.queryParams
      }, `pig_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

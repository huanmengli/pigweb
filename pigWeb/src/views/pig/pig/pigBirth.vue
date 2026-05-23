<template>
  <!-- 分娩页面 -->
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="母猪代号" prop="pigId">
        <el-input
          v-model="queryParams.pigId"
          placeholder="请输入母猪代号"
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
          @click="handlePigAdd"
          v-hasPermi="['pig:pig:add']"
        >新增</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['pig:pig:edit']"
        >修改</el-button>
      </el-col> -->
      <!-- <el-col :span="1.5">
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
      <el-table-column label="母猪代号" align="center" prop="pigId" />
      <el-table-column label="母猪性别" align="center" prop="pigSex" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.pigSex == 1">母</el-tag>
          <el-tag v-else>公</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="母猪日龄" align="center" prop="pigAge" />
      <el-table-column label="母猪共生育的窝数" align="center" prop="pigBirthnum" />
      <el-table-column label="母猪共生育的总数" align="center" prop="pigSonNum" />
      <el-table-column label="母猪这一次需要生育的数量" align="center" prop="pigOneNum" />
      <el-table-column label="母猪状态" align="center" prop="pigStatus" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.pigStatus ==0 ">空闲中</el-tag>
          <el-tag v-else-if="scope.row.pigStatus==1">配种中</el-tag>
          <el-tag v-else-if="scope.row.pigStatus==3">分娩中</el-tag>
          <el-tag v-else-if="scope.row.pigStatus==6">带仔中</el-tag>
        </template>
      </el-table-column>
      <!-- <el-table-column label="需要配种的家猪id" align="center" prop="pigPigid" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
         <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pig:pig:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pig:pig:remove']"
          >删除</el-button> -->
          <el-button v-if="scope.row.pigStatus==3"
            size="medium"
            type="text"
            icon="el-icon-add"
            @click="handleAdd(scope.row)"
            v-hasPermi="['pig:pig:remove']"
          >确认分娩</el-button>
          <el-button
            size="medium"
            type="text"
            icon="el-icon-add"
            @click="handleSellp(scope.row)"
            v-hasPermi="['pig:pig:remove']"
          >休息</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!--添加需要分娩的母猪  -->
    <el-dialog :title="addtitle" :visible.sync="addOpen" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>

         <!-- <el-col :span="24">
            <el-form-item label="仔猪日龄" prop="pigAge">
              <el-input v-model="form.pigAge" placeholder="请输入仔猪年龄" />
            </el-form-item>
          </el-col> -->
          <el-col :span="24">
            <el-form-item label="请选择母猪" prop="pigId">
              <el-select v-model="form.pigId" placeholder="请选择母猪代号" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in womanList" :key="index" :label="item.pigId"
                  :value="item.pigId" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="母猪生育数量" prop="pigOneNum">
              <el-input v-model="form.pigOneNum" placeholder="请输入母猪生育数量" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addPig">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改pig对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="smallPigForm" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="仔猪代号" prop="pigName">
              <el-input v-model="smallPigForm.smallPigId" placeholder="请输入仔猪代号" />
            </el-form-item>
          </el-col>
         <el-col :span="24">
            <el-form-item label="仔猪日龄" prop="pigAge">
              <el-input v-model="smallPigForm.smallPigAge" placeholder="请输入仔猪年龄" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="性别" prop="pigSex">
              <el-select v-model="smallPigForm.smallPigSex" placeholder="请选择性别" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in sexList" :key="index" :label="item.label"
                  :value="item.value" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
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
import { addSmallPig } from "../../../api/smallPig/pig"

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
      addtitle:"新增分娩母猪",
      addOpen:false,
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
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pigId:null,
        pigName: null,
        pigSex: 1,
        pigSonNum:null,
        pigBirthnum:null,
        pigAge: null,
        // pigStatus: 3,
        pigPigid: null
      },
      // 母猪查询参数
      womanQueryParams: {
        // pageNum: 1,
        // pageSize: 10,
        pigName: null,
        pigSex: 1,
        pigAge: null,
        pigSonNum:null,
        pigOneNum:null,
        pigBirthnum:null,
        pigStatus: 0,
        pigPigid: null
      },
      // 仔猪查询参数
      smallPigForm: {
        // pageNum: 1,
        // pageSize: 10,
        smallPigId: null,
        smallPigSex: null,
        smallPigAge: null,
        smallPigBirthnum: null,
        smallPigStatus: null,
        smallPigPigId: null,
        smallPigCreatetime: null,
        smallPigUpdatetime: null
      },
      womanList:[],
      womanPig:{

      },
      // 表单参数
      form: {
       pigId: '',
       pigName: '',
       pigSex: '',
       pigAge: '',
       pigSonNum:'',
       pigOneNum:'',
       pigBirthnum:'',
       pigStatus: '',
       pigPigid: ''
      },
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
    addPig(){
      if(this.form.pigId==""||this.form.pigOneNum==""
      ||this.form.pigId==null||this.form.pigOneNum==null
      ){
        return
      }
      this.form.pigStatus="3"
      console.log(this.form);
      pigChangeStatus(this.form).then(res=>{
        this.init()
        this.addOpen=false
        // location.reload()
      })
    },
    handleSellp(row){
      row.pigStatus=0
      pigChangeStatus(row).then(res=>{
        this.init()
        // location.reload()
      })
      console.log(row);
    },
    pigPeizhong(row){

    },
    init(){
      this.getList()
      this.getWomanList()
    },
    // 查询母猪列表
    getWomanList(){
      listPig(this.womanQueryParams).then(res=>{
        this.womanList=res.rows
      })
    },

    /** 查询pig列表 */
    getList() {
      this.loading = true
      listPig(this.queryParams).then(response => {

        this.pigList = response.rows
        var list=this.pigList
        this.pigList= list.filter(item=> {
          if(item.pigStatus==3||item.pigStatus==6){
            return item
          }
        })
        this.total = response.total
        this.loading = false
        // 页面刷新
        // location.reload()
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.addOpen=false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        pigId: null,
        pigName: null,
        pigSex: null,
        pigAge: null,
        pigStatus: 2,
        pigPigid: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
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

    // 新增母猪
    handlePigAdd(){
      this.addOpen=true
      this.reset()
    },

    /** 新增按钮操作 */
    handleAdd(row) {
      this.smallPigForm.smallPigPigId=row.pigId
      this.smallPigForm.smallPigBirthnum=row.pigBirthnum
      this.womanPig=row
      this.open=true
      console.log(this.womanPig);
      // pigChangeStatus(row).then(res=>{
      //   console.log(res);
      //   this.reset()
      //   this.title = "添加仔猪"
      // })

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
      this.form.pigStatus='4'
      this.smallPigForm.smallPigStatus="1"
      addSmallPig(this.smallPigForm).then(response => {
        this.$modal.msgSuccess("新增成功")
        this.open = false
        this.womanPig.pigSonNum++
        this.womanPig.pigOneNum--
        if(this.womanPig.pigOneNum==0){
          this.womanPig.pigStatus="6"
        }
        pigChangeStatus(this.womanPig).then(res=>{
          this.init()
        })

      })
      // this.$refs["form"].validate(valid => {
      //   if (valid) {
      //     if (this.form.pigId != null) {

      //       // updatePig(this.form).then(response => {
      //       //   this.$modal.msgSuccess("修改成功")
      //       //   this.open = false
      //       //   this.getList()
      //       // })
      //     } else {
      //       this.form.pigStatus='4'
      //       this.smallPigForm.smallPigStatus="1"
      //       addSmallPig(this.smallPigForm).then(response => {
      //         this.$modal.msgSuccess("新增成功")
      //         this.open = false
      //         this.womanPig.pigSonNum++
      //         this.womanPig.pigStatus="6"
      //         pigChangeStatus(this.womanPig).then(res=>{
      //           this.init()
      //           // location.reload()
      //         })

      //       })
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
        this.getList()
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

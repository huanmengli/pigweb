<template>
  <!-- 家猪管理 -->
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="家猪日龄" prop="pigAge">
        <el-input
          v-model="queryParams.pigId"
          placeholder="请输入家猪代号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
     <!-- <el-form-item label="需要配种的家猪id" prop="pigPigid">
        <el-input
          v-model="queryParams.pigPigid"
          placeholder="请输入需要配种的家猪id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
    <!--  <el-form-item label="家猪创建时间" prop="pigCreatetime">
        <el-date-picker clearable
          v-model="queryParams.pigCreatetime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择家猪创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="家猪修改时间" prop="pigUpdatetime">
        <el-date-picker clearable
          v-model="queryParams.pigUpdatetime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择家猪修改时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="家猪创建人" prop="pigCreateby">
        <el-input
          v-model="queryParams.pigCreateby"
          placeholder="请输入家猪创建人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="家猪修改人" prop="pigUpdateby">
        <el-input
          v-model="queryParams.pigUpdateby"
          placeholder="请输入家猪修改人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
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
      <el-col :span="1.5">
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
      </el-col>
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
      <el-table-column label="家猪代码" align="center" prop="pigId" />
      <!-- <el-table-column label="${comment}" align="center" prop="pigName" /> -->
      <el-table-column label="家猪性别" align="center" prop="pigSex" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.pigSex == 1">母</el-tag>
          <el-tag v-else>公</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="家猪日龄" align="center" prop="pigAge" />
      <el-table-column label="家猪状态" align="center" prop="pigStatus" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.pigStatus == 0">空闲中</el-tag>
          <el-tag v-else-if="scope.row.pigStatus==1">配种中</el-tag>
          <el-tag v-else-if="scope.row.pigStatus==2">妊娠中</el-tag>
          <el-tag v-else-if="scope.row.pigStatus==3">分娩中</el-tag>
          <el-tag v-else-if="scope.row.pigStatus==4">未断奶</el-tag>
          <el-tag v-else-if="scope.row.pigStatus==5">已断奶</el-tag>
          <el-tag v-else-if="scope.row.pigStatus==6">带仔中</el-tag>
        </template>
      </el-table-column>
      <!-- <el-table-column label="需要配种的家猪id" align="center" prop="pigPigid" /> -->
      <el-table-column label="家猪创建时间" align="center" prop="pigCreatetime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.pigCreatetime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="家猪修改时间" align="center" prop="pigUpdatetime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.pigUpdatetime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="家猪创建人" align="center" prop="pigCreateby" /> -->
      <!-- <el-table-column label="家猪修改人" align="center" prop="pigUpdateby" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
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
          >删除</el-button>
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

    <!-- 添加pig对话框 -->
   <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
     <el-form ref="form" :model="form" :rules="rules" label-width="100px">
       <el-row>
         <el-col :span="24">
           <el-form-item label="家猪代号" prop="pigName">
             <el-input v-model="form.pigId" placeholder="请输入家猪代号" />
           </el-form-item>
         </el-col>
         <el-col :span="24">
           <el-form-item label="家猪日龄" prop="pigAge">
             <el-input v-model="form.pigAge" placeholder="请输入家猪年龄" />
           </el-form-item>
         </el-col>
         <el-col :span="24">
           <el-form-item label="性别" prop="pigSex">
             <el-select v-model="form.pigSex" placeholder="请选择性别" clearable :style="{width: '100%'}">
               <el-option v-for="(item, index) in sexList" :key="index" :label="item.label"
                 :value="item.value" :disabled="item.disabled"></el-option>
             </el-select>
           </el-form-item>
         </el-col>
       </el-row>
     </el-form>
     <div slot="footer" class="dialog-footer">
       <el-button type="primary" @click="submitFormAdd">确 定</el-button>
       <el-button @click="cancel">取 消</el-button>
     </div>
   </el-dialog>
   <el-dialog :title="title" :visible.sync="openUpdate" width="500px" append-to-body>
     <el-form ref="form" :model="form" :rules="rules" label-width="100px">
       <el-row>
         <el-col :span="24">
           <el-form-item label="家猪代号" prop="pigName">
             <el-input v-model="form.pigId" placeholder="请输入家猪代号" />
           </el-form-item>
         </el-col>
         <el-col :span="24">
           <el-form-item label="家猪日龄" prop="pigAge">
             <el-input v-model="form.pigAge" placeholder="请输入家猪日龄" />
           </el-form-item>
         </el-col>
         <el-col :span="24">
           <el-form-item label="性别" prop="pigSex">
             <el-select v-model="form.pigSex" placeholder="请选择性别" clearable :style="{width: '100%'}">
               <el-option v-for="(item, index) in sexList" :key="index" :label="item.label"
                 :value="item.value" :disabled="item.disabled"></el-option>
             </el-select>
           </el-form-item>
         </el-col>
         <el-col :span="24">
           <el-form-item label="状态" prop="pigSex">
             <el-select v-if="form.pigSex==1" v-model="form.pigStatus" placeholder="请选择状态" clearable :style="{width: '100%'}">
               <el-option v-for="(item, index) in womanStatusList" :key="index" :label="item.label"
                 :value="item.value" :disabled="item.disabled"></el-option>
             </el-select>
             <el-select v-else v-model="form.pigStatus" placeholder="请选择状态" clearable :style="{width: '100%'}">
               <el-option v-for="(item, index) in manStatusList" :key="index" :label="item.label"
                 :value="item.value" :disabled="item.disabled"></el-option>
             </el-select>
           </el-form-item>
         </el-col>
       </el-row>
     </el-form>
     <div slot="footer" class="dialog-footer">
       <el-button type="primary" @click="submitFormUpdate">确 定</el-button>
       <el-button @click="cancel">取 消</el-button>
     </div>
   </el-dialog>
  </div>
</template>

<script>
import { listPig, getPig, delPig, addPig  } from "@/api/pig/pig"
import { updatePig } from "../../../api/pig/pig"

export default {
  name: "Pig",
  data() {
    return {
      pigId:"",
      manStatusList:[
        {
          "label":"空闲中",
          "value":"0"
        },
        {
          "label":"配种中",
          "value":"1"
        },
        {
          "label":"未断奶",
          "value":"4"
        },
        {
          "label":"已断奶",
          "value":"5"
        }
      ],
      womanStatusList:[
        {
          "label":"空闲中",
          "value":"0"
        },
        {
          "label":"配种中",
          "value":"1"
        },
        {
          "label":"妊娠中",
          "value":"2"
        },
        {
          "label":"分娩中",
          "value":"3"
        },
        {
          "label":"未断奶",
          "value":"4"
        },
        {
          "label":"已断奶",
          "value":"5"
        },
        {
          "label":"带仔中",
          "value":"6"
        }
      ],
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
      openUpdate:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pigId: null,
        pigName: null,
        pigSex: null,
        pigAge: null,
        pigStatus: null,
        pigPigid: null,
        pigCreatetime: null,
        pigUpdatetime: null,
        pigCreateby: null,
        pigUpdateby: null
      },
      // 表单参数
      form: {
        pigId: null,
        pigName: null,
        pigSex: null,
        pigAge: null,
        pigSonNum:null,
        pigBirthnum:null,
        pigStatus: null,
        pigPigid: null,
        pigCreatetime: null,
        pigUpdatetime: null,
        pigCreateby: null,
        pigUpdateby: null
      },
      // 表单校验
      rules: {
      }
    }
  },
  created() {
    this.getList()
  },
  watch: {
    '$route'(to, from) {
      this.getList()
    }
  },
  methods: {
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
      this.openUpdate=false
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
        pigPigid: null,
        pigCreatetime: null,
        pigUpdatetime: null,
        pigCreateby: null,
        pigUpdateby: null
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加家猪信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      this.pigId = row.pigId || this.ids
      getPig(this.pigId).then(response => {
        this.form = response.data
        this.openUpdate = true
        this.title = "修改家猪信息"
      })
    },
    submitFormAdd(){
      this.form.pigSonNum=0
      this.form.pigBirthnum=0
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.pigId != null) {
            this.form.pigStatus="0"
            addPig(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()

            })
          } else {

          }
        }
      })
    },
    /** 提交按钮 */
    submitFormUpdate() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.pigId != null) {
            updatePig(this.form,this.pigId).then(response => {
              console.log(response);
              this.$modal.msgSuccess("修改成功")
              this.openUpdate = false
              this.getList()
            })
          } else {

          }
        }
      })
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

<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="仔猪年龄" prop="smallPigId">
        <el-input
          v-model="queryParams.smallPigId"
          placeholder="请输入仔猪年龄"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
    <!--  <el-form-item label="仔猪出生时那一窝的数量" prop="smallPigBirthnum">
        <el-input
          v-model="queryParams.smallPigBirthnum"
          placeholder="请输入仔猪出生时那一窝的数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="仔猪的母猪" prop="smallPigPigId">
        <el-input
          v-model="queryParams.smallPigPigId"
          placeholder="请输入仔猪的母猪"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="${comment}" prop="smallPigCreatetime">
        <el-date-picker clearable
          v-model="queryParams.smallPigCreatetime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择${comment}">
        </el-date-picker>
      </el-form-item> -->
      <!-- <el-form-item label="${comment}" prop="smallPigUpdatetime">
        <el-date-picker clearable
          v-model="queryParams.smallPigUpdatetime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择${comment}">
        </el-date-picker>
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
          v-hasPermi="['smallPig:pig:add']"
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
          v-hasPermi="['smallPig:pig:edit']"
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
          v-hasPermi="['smallPig:pig:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['smallPig:pig:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="pigList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="仔猪代号" align="center" prop="smallPigId" />
      <el-table-column label="家猪性别" align="center" prop="smallPigSex" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.smallPigSex == 1">母</el-tag>
          <el-tag v-else>公</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="仔猪年龄" align="center" prop="smallPigAge" />
      <el-table-column label="仔猪第几窝出生" align="center" prop="smallPigBirthnum" />
      <el-table-column label="仔猪状态" align="center" prop="smallPigStatus" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.smallPigStatus==1">未断奶</el-tag>
          <el-tag v-else-if="scope.row.smallPigStatus==2">已断奶</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="仔猪的母猪代号" align="center" prop="smallPigPigId" />
      <!-- <el-table-column label="${comment}" align="center" prop="smallPigCreatetime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.smallPigCreatetime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column> -->
      <!-- <el-table-column label="${comment}" align="center" prop="smallPigUpdatetime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.smallPigUpdatetime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['smallPig:pig:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['smallPig:pig:remove']"
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

    <!-- 添加mallPig对话框 -->
    <el-dialog title="添加仔猪" :visible.sync="openAdd" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="仔猪代号" prop="smallPigId">
              <el-input v-model="form.smallPigId" placeholder="请输入仔猪代号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="仔猪日龄" prop="smallPigAge">
              <el-input v-model="form.smallPigAge" placeholder="请输入仔猪日龄" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="请选择状态" prop="pigSex">
              <el-select  v-model="form.smallPigSex" placeholder="请选择性别" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in sexList" :key="index" :label="item.label"
                  :value="item.value" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="24">
            <el-form-item label="仔猪第几窝出生" prop="smallPigBirthnum">
              <el-input v-model="form.smallPigBirthnum" placeholder="请输入仔猪出生时那一窝的数量" />
            </el-form-item>
          </el-col> -->
          <el-col :span="24">
            <el-form-item label="请选择状态" prop="smallPigStatus">
              <el-select  v-model="form.smallPigStatus" placeholder="请选择状态" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in womanStatusList" :key="index" :label="item.label"
                  :value="item.value" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="请选择母猪" prop="pigSex">
              <el-select  v-model="form.smallPigPigId" placeholder="请选择母猪" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in womanList" :key="index" :label="item.pigName"
                  :value="item.pigId" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="24">
            <el-form-item label="${comment}" prop="smallPigCreatetime">
              <el-date-picker clearable
                v-model="form.smallPigCreatetime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择${comment}">
              </el-date-picker>
            </el-form-item>
          </el-col> -->
          <!-- <el-col :span="24">
            <el-form-item label="${comment}" prop="smallPigUpdatetime">
              <el-date-picker clearable
                v-model="form.smallPigUpdatetime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择${comment}">
              </el-date-picker>
            </el-form-item>
          </el-col> -->
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormAdd">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="修改信息" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="仔猪代号" prop="smallPigId">
              <el-input v-model="form.smallPigId" placeholder="请输入仔猪代号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="仔猪日龄" prop="smallPigAge">
              <el-input v-model="form.smallPigAge" placeholder="请输入仔猪日龄" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="请选择状态" prop="pigSex">
              <el-select  v-model="form.smallPigSex" placeholder="请选择性别" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in sexList" :key="index" :label="item.label"
                  :value="item.value" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="24">
            <el-form-item label="仔猪第几窝出生" prop="smallPigBirthnum">
              <el-input v-model="form.smallPigBirthnum" placeholder="请输入仔猪出生时那一窝的数量" />
            </el-form-item>
          </el-col> -->
          <el-col :span="24">
            <el-form-item label="请选择状态" prop="smallPigStatus">
              <el-select  v-model="form.smallPigStatus" placeholder="请选择状态" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in womanStatusList" :key="index" :label="item.label"
                  :value="item.value" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="请选择母猪" prop="pigSex">
              <el-select  v-model="form.smallPigPigId" placeholder="请选择母猪" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in womanList" :key="index" :label="item.pigName"
                  :value="item.pigId" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="24">
            <el-form-item label="${comment}" prop="smallPigCreatetime">
              <el-date-picker clearable
                v-model="form.smallPigCreatetime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择${comment}">
              </el-date-picker>
            </el-form-item>
          </el-col> -->
          <!-- <el-col :span="24">
            <el-form-item label="${comment}" prop="smallPigUpdatetime">
              <el-date-picker clearable
                v-model="form.smallPigUpdatetime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择${comment}">
              </el-date-picker>
            </el-form-item>
          </el-col> -->
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormPut">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getPig, listPig, pigChangeStatus } from "../../../api/pig/pig"
import { addSmallPig, delSmallPig, getSmallPig, listSmallPig, updateSmallPig } from "../../../api/smallPig/pig"

export default {
  name: "Pig",
  data() {
    return {
      womanStatusList:[
        {
          "label":"未断奶",
          "value":"1"
        },
        {
          "label":"已断奶",
          "value":"2"
        },
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
      // smallPig表格数据
      pigList: [],
      womanList:[],
      // 弹出层标题
      title: "",
      openAdd:false,
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        smallPigId: null,
        smallPigSex: null,
        smallPigAge: null,
        smallPigBirthnum: null,
        smallPigStatus: null,
        smallPigPigId: null,
        smallPigCreatetime: null,
        smallPigUpdatetime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
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
    }
  },
  created() {
    this.init()
  },
  watch: {
    '$route'() {
      this.init()
    }
  },
  methods: {
    init(){
      this.getList()
      this.getWomanList()
    },
    /** 查询smallPig列表 */
    getList() {
      this.loading = true
      listSmallPig(this.queryParams).then(response => {
        this.pigList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    getWomanList(){
      listPig({
        pigName: null,
        pigSex: 1,
        pigAge: null,
        pigStatus: null,
        pigPigid: null
      },).then(res=>{
        this.womanList=res.rows
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
        smallPigId: null,
        smallPigSex: null,
        smallPigAge: null,
        smallPigBirthnum: null,
        smallPigStatus: null,
        smallPigPigId: null,
        smallPigCreatetime: null,
        smallPigUpdatetime: null
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
      this.ids = selection.map(item => item.smallPigId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.openAdd = true
      this.title = "添加smallPig"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const smallPigId = row.smallPigId || this.ids
      getSmallPig(smallPigId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改smallPig"
      })
    },
    submitFormPut(){
      updateSmallPig(this.form).then(response => {
        this.$modal.msgSuccess("修改成功")
        this.open = false
        this.init()
      })
    },
    /** 提交按钮 */
    submitFormAdd() {
      getPig(this.form.smallPigPigId).then(res=>{
        console.log(res.data);
        this.form.smallPigBirthnum=res.data.pigBirthnum
        addSmallPig(this.form).then(response => {
          this.$modal.msgSuccess("新增成功")
          this.openAdd = false
          this.init()
          res.data.pigSonNum++
          pigChangeStatus(res.data)
        })
      })

      // this.$refs["form"].validate(valid => {
      //   if (valid) {
      //     if (this.form.smallPigId != null) {
      //       updateSmallPig(this.form).then(response => {
      //         this.$modal.msgSuccess("修改成功")
      //         this.openAdd = false
      //         this.init()
      //       })
      //     } else {

      //     }
      //   }
      // })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const smallPigIds = row.smallPigId || this.ids
      this.$modal.confirm('是否确认删除smallPig编号为"' + smallPigIds + '"的数据项？').then(function() {
        return delSmallPig(smallPigIds)
      }).then(() => {
        this.init()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('smallPig/pig/export', {
        ...this.queryParams
      }, `pig_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

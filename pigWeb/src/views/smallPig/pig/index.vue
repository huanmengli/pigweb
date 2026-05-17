<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="小猪年龄" prop="smallPigId">
        <el-input
          v-model="queryParams.smallPigId"
          placeholder="请输入小猪年龄"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
    <!--  <el-form-item label="小猪出生时那一窝的数量" prop="smallPigBirthnum">
        <el-input
          v-model="queryParams.smallPigBirthnum"
          placeholder="请输入小猪出生时那一窝的数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="小猪的母猪" prop="smallPigPigId">
        <el-input
          v-model="queryParams.smallPigPigId"
          placeholder="请输入小猪的母猪"
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
      <el-table-column label="小猪代号" align="center" prop="smallPigId" />
      <el-table-column label="家猪性别" align="center" prop="smallPigSex" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.smallPigSex == 1">母</el-tag>
          <el-tag v-else>公</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="小猪年龄" align="center" prop="smallPigAge" />
      <el-table-column label="小猪出生时那一窝的数量" align="center" prop="smallPigBirthnum" />
      <el-table-column label="小猪状态" align="center" prop="smallPigStatus" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.smallPigStatus==1">未断奶</el-tag>
          <el-tag v-else-if="scope.row.smallPigStatus==2">已断奶</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="小猪的母猪" align="center" prop="smallPigPigId" />
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

    <!-- 添加或修改smallPig对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="小猪年龄" prop="smallPigAge">
              <el-input v-model="form.smallPigAge" placeholder="请输入小猪年龄" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="小猪出生时那一窝的数量" prop="smallPigBirthnum">
              <el-input v-model="form.smallPigBirthnum" placeholder="请输入小猪出生时那一窝的数量" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="小猪的母猪" prop="smallPigPigId">
              <el-input v-model="form.smallPigPigId" placeholder="请输入小猪的母猪" />
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
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPig, getPig, delPig, addPig, updatePig } from "@/api/smallPig/pig"

export default {
  name: "Pig",
  data() {
    return {
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
      // 弹出层标题
      title: "",
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
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询smallPig列表 */
    getList() {
      this.loading = true
      listPig(this.queryParams).then(response => {
        this.pigList = response.rows
        this.total = response.total
        this.loading = false
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
      this.open = true
      this.title = "添加smallPig"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const smallPigId = row.smallPigId || this.ids
      getPig(smallPigId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改smallPig"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.smallPigId != null) {
            updatePig(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addPig(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const smallPigIds = row.smallPigId || this.ids
      this.$modal.confirm('是否确认删除smallPig编号为"' + smallPigIds + '"的数据项？').then(function() {
        return delPig(smallPigIds)
      }).then(() => {
        this.getList()
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

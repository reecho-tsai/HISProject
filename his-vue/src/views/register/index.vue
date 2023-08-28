<template>
  <div>
    <Breadcrumb msg1="挂号收费" msg2="添加挂号" />
    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 提示区域 -->
      <el-alert title="添加挂号信息" type="info" center show-icon :closable="false"></el-alert>
      <el-steps :active="parseInt(activeIndex)" finish-status="success">
        <el-step title="用户信息"></el-step>
        <el-step title="病例情况"></el-step>
        <el-step title="挂号信息"></el-step>
      </el-steps>

      <el-form :model="form" :rules="rules" ref="formRef" label-width="110px" label-position="left">
        <el-tabs
          v-model="activeIndex"
          :tab-position="'left'"
        >
          <el-tab-pane label="用户信息" name="0">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="身份证号" prop="idno">
              <el-input v-model="form.idno" @change="date_change"></el-input>
            </el-form-item>
            <el-form-item label="性别">
              <el-radio-group v-model="form.gender">
                <el-radio :label="0">男</el-radio>
                <el-radio :label="1">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="出生日期" prop="birth">
              <el-date-picker
                v-model="form.birthday"
                type="date"
                placeholder="选择日期"
                value-format="yyyy-MM-dd"
              ></el-date-picker>
            </el-form-item>
            <el-form-item label="年龄"  @focus="date_change">
              <el-input v-model="form.age" type="number"></el-input>
            </el-form-item>
            <el-form-item label="地址" prop="address">
              <el-input v-model="form.address"></el-input>
            </el-form-item>
          </el-tab-pane>

          <el-tab-pane label="病例情况" name="1">
            <el-form-item label="健康描述" prop="readme">
              <el-input v-model="form.readme" type="textarea"></el-input>
            </el-form-item>
            <el-form-item label="现病史">
              <el-input v-model="form.present" type="textarea"></el-input>
            </el-form-item>
            <el-form-item label="现病史治疗情况">
              <el-input v-model="form.presentTreat" type="textarea"></el-input>
            </el-form-item>
            <el-form-item label="既往史">
              <el-input v-model="form.history" type="textarea"></el-input>
            </el-form-item>
            <el-form-item label="过敏史">
              <el-input v-model="form.allergy" type="textarea"></el-input>
            </el-form-item>
          </el-tab-pane>

          <el-tab-pane label="挂号信息" name="2">
            <el-form-item label="挂号级别">
              <el-select v-model="form.regsitLevelId" placeholder="请选择" :popper-append-to-body="false" @change="fee">
                <el-option
                  v-for="(value, id) in register"
                  :key="id"
                  :label="value.name"
                  :value="id">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="挂号科室" >
              <el-select v-model="form.deptId" placeholder="请选择" :popper-append-to-body="false" @change="doctor={}">
                <el-option
                  v-for="(value, id) in department"
                  :key="id"
                  :label="value.name"
                  :value="id">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="时间" prop="retime2">
            <el-date-picker
            　　v-model="form.retime2"
            　　type="date"
            　　value-format="yyyy-MM-dd"
            　　:picker-options="pickerOptions"
                @change="retime2"
            　　placeholder="选择交货开始日期"  :popper-append-to-body="false" >
            </el-date-picker>
            </el-form-item>

            <el-form-item label="午别" prop="dtime">
              <el-radio-group v-model="form.dtime" @change="dtime">
                <el-radio-button :label="0">上午</el-radio-button>
                <el-radio-button :label="1">下午</el-radio-button>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="看诊医生">
              <el-select v-model="form.doctorId" placeholder="请选择" :popper-append-to-body="false" @focus="getDoctor">
                <el-option
                  v-for="(value, id) in doctor"
                  :key="id"
                  :label="value.name"
                  :value="id">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="是否要病历本" prop="book">
              <el-radio-group v-model="form.book" @change="fee">
                <el-radio-button :label="0">否</el-radio-button>
                <el-radio-button :label="1">是</el-radio-button>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="应收金额" prop="fee">
              <el-input v-model="form.fee" disabled></el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="save">立即创建</el-button>
            </el-form-item>
          </el-tab-pane>
        </el-tabs>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
export default {
  components: {
    Breadcrumb
  },
  data () {
    var checkId = (rule, value, cb) => {
      const reg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|31)|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}([0-9]|x|X)$/
      if (reg.test(value)) {
        return cb()
      }
      cb(new Error('请输入合法的身份证号'))
    }
    return {
      pickerOptions: {
        disabledDate: time => {//获取时间为一个月之内
            return (
            time.getTime() < Date.now() - 24 * 60 * 60 * 1000 ||
            time.getTime() > Date.now() + 24 * 60 * 60 * 1000 * 6
           );
        },
        timeOne: "",
      },
      activeIndex: 0,
      form: {
        name: '',//姓名
        gender: 0,//性别
        idno: '',//身份证号
        birthday: '',//生日
        age: null,//年龄
        address: '北京市-东城区',//地址
        regsitLevelId: null,//挂号级别
        deptId: null,//科室编号
        doctorId: null,//医生编号
        book: 0,//是否需要病历本
        fee: 0,//费用
        readme: '体检',//体检
        present: '',
        presentTreat: '',
        history: '',//历史
        allergy: '',
        status: 1,//状态是否合法
        retime: 0,//时间，用于筛选查询医生和获取当前时间
        retime2: '2022-012-01',//时间，用于保存时间表的时间
        dtime:'a',//午别，用于初始化和传值
        daytime:'a',//午别，用于筛选查询医生
        workTime0:'',//最终挂号时间
        doctorname:'',//医生名字
      },
      rules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        idno: [
          { required: true, message: '请输入身份证号码', trigger: 'blur' },
          { validator: checkId, trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        readme: [
          { required: true, message: '请输入身份证号码', trigger: 'blur' }
        ]
      },
      doctor: {}
    }
  },
  mounted () {
    this.$store.dispatch('getRegisterLevel')//挂号等级
    this.$store.dispatch('getDepartment')//部门
    this.retime()//获取当前日期
  },
  computed: {
    ...mapState(['register', 'department']),
      timeDefault() {//计算属性计算前一天的日期
        var date = new Date();
        var month = date.getMonth() + 1;
        const newmonth = month >= 10 ? month : "0" + month;
        var s1 = date.getFullYear() + "-" + newmonth + "-" + (date.getDate() - 1);
        return s1;
      },
  },
  methods: {
    retime() {//页面加载获取时间
        const nowDate = new Date();//获取当前时间
        let year = nowDate.getFullYear();
        let month =nowDate.getMonth() + 1 < 10? "0" + (nowDate.getMonth() + 1): nowDate.getMonth() + 1;
        let date =nowDate.getDate() < 10? "0" + nowDate.getDate(): nowDate.getDate();
        let hh =nowDate.getHours() < 10? "0" + nowDate.getHours(): nowDate.getHours();
        let mm =nowDate.getMinutes() < 10? "0" + nowDate.getMinutes(): nowDate.getMinutes();
        let ss =nowDate.getSeconds() < 10? "0" + nowDate.getSeconds(): nowDate.getSeconds();
        let week = nowDate.getDay();//得到星期
        let weeks = ["7","1","2","3","4","5","6"];
        let getWeek = weeks[week];
        this.form.retime2 = year + "-" + month + "-" + date;//修改日期初始值
        this.form.retime = getWeek;//星期传给retime,获取当前日期
    },
    retime2() {//获取时间
        const nowDate = new Date(this.form.retime2);//获取时间表时间
        let week = nowDate.getDay();//得到星期
        let weeks = ["7","1","2","3","4","5","6"];
        let getWeek = weeks[week];
        this.form.retime = getWeek;//星期传给retime
        this.doctor = {}
    },
    fee () {//费用信息
      if (this.form.regsitLevelId) {
        this.form.fee = this.register[this.form.regsitLevelId].fee
        if (this.form.book === 1) {//需要病历本加5
          this.form.fee += 5
        }
        this.doctor = {}
      }
    },
    applicationTypes(value){
    this.doctor.forEach(item => {
    if(id === value){
    this.form.doctorname = value.name}
    })
    },
    date_change() {//自动填写生日,年龄,性别
      let idno=this.form.idno;
      this.form.birthday=idno.substr(6,4)+'-'+idno.substr(10,2)+'-'+idno.substr(12,2);//生日
      this.form.age=2022-idno.substr(6,4);//年龄
      if(idno.substr(16,1)%2==1){//性别
        this.form.gender=0;
      }
      else{
        this.form.gender=1;
      }
    },
    dtime () {//选择午别
      if (this.form.dtime === 1) {//下午
        this.form.daytime = 'b';
      }
      else{//上午
        this.form.daytime = 'a';
      }
      this.doctor = {}
    },
    save () {//保存挂号信息
      this.$refs.formRef.validate(valid => {
        this.$axios.post('/registers', () => {}, this.form)
        let ut=this.register[this.form.regsitLevelId].roleId;
        let di=this.form.deptId;
        let wt=this.form.workTime0;
        let addid=this.form.doctorId;
        this.$axios.http.put(`works/add/${addid}`, response => {})
       }
       )
    },
    getDoctor () {//获取医生信息
      let resultTemp = this.form.retime + this.form.daytime;//日期与午别标志
      this.form.workTime0=resultTemp;
      if (Object.keys(this.doctor).length !== 0) return
      if (this.form.regsitLevelId && this.form.deptId) {
        this.$axios.http.get('/works/list', {
          params: {
            state: 1,
            user_type: this.register[this.form.regsitLevelId].roleId,
            dept_id: this.form.deptId,
            work_time0: resultTemp,
          }
        }).then(response => {
          this.doctor = response.data.data
        })
      } else {
        this.$message.error('请先选择挂号级别和科室')
      }
    },

      getDate() {//获取当前年月日时分秒
        const nowDate = new Date();
        var y = nowDate.getFullYear();
        var m = nowDate.getMonth() + 1;
        var d = nowDate.getDate();
        //  当前日期
        let nowDates =
          y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d);
        //   十二月的时候年份要加一
        if (m == 12) {
          y++;
          m = 1;
        } else if (m == 3 && d > 28) {
          //三月要考虑是否为闰年
          m++;
          if ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) {
            d = 29;
          } else {
            d = 28;
          }
        } else if ((m != 12 || m != 8) && d == 31) {
          //31号的月份要考虑下个月是否有31号
          m++;
          d = 30;
        } else {
          m++;
        }
        // 一个月后的日期
        this.timeOne =
          y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d);
      },


  }
}
</script>
<style scoped>
.el-select-dropdown {
  top: 38px !important;
  left: 0 !important;
}
</style>
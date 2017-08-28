<template>
    <div id="box" class="container" style="padding-top: 15px" >
        <div style="margin: 10px;"></div>
        <el-form :label-position="left" label-width="80px">

            <el-input type="text" id="exp"  v-model="exp"   placeholder="输入公式，例如：a+b=c"  @change="getVars" icon="edit" >
                    <template slot="prepend">公式：</template>
            </el-input>

            <ol>
                <li v-for="(item,index) in expVars">
                    <el-input type="text"  v-model="expVars[index].varValue"  :name="item.varValue" icon="edit" >
                        <template slot="prepend">{{item.varName}}: 取值范围：</template>
                    </el-input>
                </li>
                <div v-show="expVars.length!=0" style="margin: 15px">
                    <el-input type="text"  v-model="otherExp"  placeholder="其他条件如：a>b && a+b==10 || a+b==100"  icon="edit" >
                        <template slot="prepend">其他条件:</template>
                    </el-input>
                    <el-input type="text"   v-model="sumRow"   @change="setResultTableData" icon="edit" >
                        <template slot="prepend">总行数:&nbsp;&nbsp;&nbsp;</template>
                    </el-input>
                    <el-input type="text"   v-model="sumCol"  @change="setResultTableData"  icon="edit" >
                        <template slot="prepend">总列数:&nbsp;&nbsp;&nbsp;</template>
                    </el-input>
                </div>
            </ol>

            <el-button type="primary"  v-show="expVars.length!=0"  @click="getResult"  icon="upload">提交</el-button>
            <el-button type="warning"  v-show="result.length!=0"   @click="hiddenAns"  :icon="hidden?'star-on':'star-off'">{{hidden?'显示答案':'隐藏答案'}}</el-button>
        </el-form>
        <p v-show="expVars.length==0">暂无数据...</p>

        <div v-show="result.length!=0" >
            <hr>
            <el-table ref="singleTable" border :data="resultTableData" highlight-current-row  @current-change="setCurrentClickRow" style="width: 100%">
               <el-table-column v-for="colNum in parseInt(sumCol)"    :property="resultTableDataColomName[colNum-1]">
                   <template scope="scope">
                       <span style="margin-left: 10px">
                           <!--{{(scope.$index)*sumCol+colNum}}： --> {{scope.row[scope.column.property].split('=')[0]+"="}}
                            <span v-show="hidden">{{scope.row[scope.column.property].split('=')[1]}}</span>
                       </span>
                   </template>
               </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
    var genRexUrl  = '/genRex/';
    var getVariableUrl = '/genRex/getVariable';

    function mapToJson(map) {
        return JSON.stringify([...map]);
    }

    function jsonToMap(jsonStr) {
        return new Map(JSON.parse(jsonStr));
    }

    function strMapToObj(strMap) {
        let obj = Object.create(null);
        for (let [k,v] of strMap) {
            // We don’t escape the key '__proto__'
            // which can cause problems on older engines
            obj[k] = v;
        }
        return obj;
    }

    function objToStrMap(obj) {
        let strMap = new Map();
        for (let k of Object.keys(obj)) {
            strMap.set(k, obj[k]);
        }
        return strMap;
    }

    function strMapToJson(strMap) {
        return JSON.stringify(strMapToObj(strMap));
    }

    function jsonToStrMap(jsonStr) {
        return objToStrMap(JSON.parse(jsonStr));
    }


    export default {
        name: 'math',
        data () {
            return {
                expVars:[],
                exp:'',
                otherExp:'',
                sumRow:10,
                sumCol:5,
                result:[],
                now:-1,
                hidden:false,
                resultTableData:[],
                resultTableDataColomName:[]
            }
        },
        methods:{
            hiddenAns:function () {
                this.hidden = !this.hidden;
            },
            getVars:function(event){
                var $this = this;
                if(event.keyCode==38 || event.keyCode==40) return;
                this.axios({
                    method:'post',
                    url:getVariableUrl,
                    data:{
                        exp:this.exp,
                    },
                    timeout: 1000,
                }).then(function(res){
                    $this.expVars = [];
                    for (var i = 0; i < res.data.length; i ++) {
                        var item = {varName: res.data[i],varValue: '0<='+res.data[i]+'<=10'};
                        $this.expVars.push(item);
                    }
                }).catch(function (error) {
                    console.log(error);
                });


            },
            setResultTableData: function () {
                this.resultTableData =[];
                this.resultTableDataColomName =[];
                for (var i = 0; i < this.sumCol; i++) {
                    this.resultTableDataColomName[i] = "C" + i;
                }


                for (var i = 0; i < this.sumRow; i++) {
                    var newRow = {};
                    for (var j = 0; j < this.resultTableDataColomName.length; j++) {
                        let name = this.resultTableDataColomName[j];
                        newRow["" + name + ""] = this.result[(i + 1 - 1) * this.sumCol + j];
                    }
                    this.resultTableData[i] = newRow;

                }
            },
            getResult:function () {
                var $this = this;
                let  data = new Map();
                this.expVars.forEach((item,key)=>{
                    data.set(item.varName,item.varValue);
                });
                data.set("exp",this.exp);
                data.set("otherExp",this.otherExp);
                data.set("sumRow",this.sumRow);
                data.set("sumCol",this.sumCol);
                this.axios.post(genRexUrl,JSON.parse(strMapToJson(data))).then(function(res){
                    $this.result = res.data;
                    $this.setResultTableData();
                },function(){
                });
            },
            setCurrentClickRow(val) {
                this.currentRow = val;
            }
        }
    }
</script>


<style>
    body {
        font-family: Helvetica, sans-serif;
    }
</style>

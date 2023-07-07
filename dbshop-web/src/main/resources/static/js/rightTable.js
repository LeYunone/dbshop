// //右库树点击事件
// function rightTableHandler(data) {
//     console.log(data);
//     if (this.rightTableName === data.tableName) return;
//     this.rightTableName = data.tableName;
//     if (data.columns!=null) {
//         //点击表节点
//         this.rightColumns = data.columns
//     } else {
//         //点击字段
//         axios({
//             url: "/dbquery/columns",
//             params: {
//                 url: this.rightDbForm.url,
//                 tableName: data.tableName,
//                 dbName: this.rightDbForm.dbName
//             }
//         }).then((res) => {
//             var data = res.data;
//             if (data.status) {
//                 this.rightColumns = data.data;
//             } else {
//                 this.$message.error(data.message);
//             }
//         })
//     }
// }
// //刷新左表树
// function loadRightTree() {
//     axios({
//         url: "/dbquery/tables",
//         method: "GET",
//         params: {
//             url: this.rightDbForm.url,
//             dbName: this.rightDbForm.dbName
//         }
//     }).then((res) => {
//         var data = res.data;
//         if (data.status) {
//             this.rightTableTree = data.data;
//         } else {
//             this.$message.error(data.message);
//         }
//     })
// }
// //加载左库
// function loadRightDb() {
//     axios({
//         url: "/config/loadConnection",
//         method: "GET",
//         params: this.rightDbForm
//     }).then((res) => {
//         var data = res.data;
//         if (data.status) {
//             this.rightDb = data.data;
//             //属性左表树
//             this.loadRightTree();
//         } else {
//             this.$message.error(data.message);
//         }
//     })
// }
//

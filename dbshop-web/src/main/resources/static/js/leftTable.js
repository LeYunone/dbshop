// //左库树点击事件
// function leftTableHandler(data) {
//     console.log(data);
//     if (this.leftTableName === data.tableName) return;
//     this.leftTableName = data.tableName;
//     if (data.columns != null) {
//         //点击表节点
//         this.leftColumns = data.columns
//     } else {
//         //点击字段
//         axios({
//             url: "/dbquery/columns",
//             params: {
//                 url: this.leftDbForm.url,
//                 tableName: data.tableName,
//                 dbName: this.leftDbForm.dbName
//             }
//         }).then((res) => {
//             var data = res.data;
//             if (data.status) {
//                 this.leftColumns = data.data;
//             } else {
//                 this.$message.error(data.message);
//             }
//         })
//     }
// }
//
// //刷新左表树
// function loadLeftTree() {
//     axios({
//         url: "/dbquery/tables",
//         method: "GET",
//         params: {
//             url: this.leftDbForm.url,
//             dbName: this.leftDbForm.dbName
//         }
//     }).then((res) => {
//         var data = res.data;
//         if (data.status) {
//             this.leftTableTree = data.data;
//         } else {
//             this.$message.error(data.message);
//         }
//     })
// }
//
// //加载左库
// function loadLeftDb() {
//     axios({
//         url: "/config/loadConnection",
//         method: "GET",
//         params: this.leftDbForm
//     }).then((res) => {
//         var data = res.data;
//         if (data.status) {
//             this.leftDb = data.data;
//             //属性左表树
//             this.loadLeftTree();
//         } else {
//             this.$message.error(data.message);
//         }
//     })
// }

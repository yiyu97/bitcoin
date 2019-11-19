var app = new Vue({
    el:'#app',
    data:{
        companys:[],
    },
    methods: {
        getcompanys(){
            axiso.get("/list").then(res=>{
                console.log(res);
                this.companys=res.data;
            });
        }
       
    },
    mounted() {
        this.getcompanys();
    }
    

});
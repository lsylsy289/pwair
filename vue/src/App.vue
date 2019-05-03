<template>
  <div id="app" v-bind:style="appStyle">
    <router-view v-bind:appStyle='appStyle'></router-view>
  </div>
</template>

<script>
  import Main from './components/Main.vue';


  export default {
    components:{
      'Main' : Main
    },
    data: function(){
      return {
        items: [],
        appStyle : {
          height: '100%'
        }

      }
    },
    methods: {

    },
    created: function(){

    }
  }
</script>

<style>
  body{
    height: 100%;
    text-align: center;
    background-color: #50C1E9;
    font-family: 'Roboto';
    font-weight: 400;
  }
  #app{
    height: 100%;
  }
  input{
    border-style: groove;
    width: 200px;
  }
  button{
    border-style: groove;
  }
  .shadow{
    box-shadow: 5px 10px 10px rgba(0, 0, 0, 0.05)
  }
</style>

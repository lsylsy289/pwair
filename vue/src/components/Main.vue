<template>
  <div class="main-wrap">
    <!-- <md-button class="md-icon-button">
         <span class="fas fa-redo"></span>
     </md-button>
     <md-button class="md-raised md-accent">Logout</md-button> -->
    <!-- .air-container : S -->
    <section class="air-container">
      <div>
        <div class="far fa-grin-hearts"></div>
        <div class="air-score">{{ data.IDEX_MVL }}</div>
        <div class="air-state">{{ data.MSRSTE_NM }}의 대기는 지금 {{ data.IDEX_NM }} !</div>
        <div class="date">기준 : {{ data.MSRDT }}</div>
      </div>
    </section>
    <!-- //.air-container : E -->
    <!-- .air-info-wrap : S -->
    <ul class="air-info-wrap">
      <li class="info-box">
        <span class="tit">미세먼지</span>
        <span class="far fa-grin-hearts"></span>
        <span class="state">좋음</span>
        <span>{{ data.PM10 }} ㎍/㎥</span>
      </li>
      <li class="info-box">
        <span class="tit">오존</span>
        <span class="far fa-grin"></span>
        <span class="state">보통</span>
        <span>{{ data.O3 }} ppm</span>
      </li>
      <li class="info-box">
        <span class="tit">초미세먼지</span>
        <span class="far fa-dizzy"></span>
        <span class="state">나쁨</span>
        <span>{{ data.PM25 }} ㎍/㎥</span>
      </li>
    </ul>
    <!-- //.air-info-wrap : E -->
  </div>
</template>

<script>
  export default {
    props: {
      'appStyle' : {
        type: Object
      }
    },
    data: function() {
      return {
        data: {},
        items: null,
        currentDistrict: null
      }
    },
    computed: {
      hasResult: function () {
        return this.data.length > 0
      }
    },
    methods: {

      getLocation: function (){
        this.$location.getLocation()
          .then(coordinates => {
            //console.log(coordinates);
            // 좌표값 조회가 존재하면
            if( coordinates ) {
              // 실시간 미세먼지 조회
              this.geoInfoToDistrictName( coordinates )
            }
          });
        navigator.geolocation.getCurrentPosition(function(position) {
          this.geoInfoToDistrictName(position.coords.latitude, position.coords.longitude);
        });
      },

      getApi: function () {
        var self = this;

        const baseURI = 'http://openAPI.seoul.go.kr:8088/516b5870446c6f7537374c45767153/json/RealtimeCityAir/1/25';

        this.$http.get(`${baseURI}`)
          .then((result) => {


            let arrRows = result.data.RealtimeCityAir.row;

            //console.log(arrRows);
            //console.log(self.currentDistrict);

            console.log(self._data)
            this.data = arrRows.filter(function (v){
              //return v.MSRSTE_NM === self.geoLocation.currentDistrict
              //return v.MSRSTE_NM === self._data.currentDistrict
              return v.MSRSTE_NM === "강남구"
            })[0];
            self.setAirStatus(data);
          })
      },


      // getUserLocation() {
      //     return navigator.geolocation.getCurrentPosition(function(position) {
      //         this.geoLocation.currentLocation = position.coords;
      //         this.geoLocation.currentLocationLon = position.coords.longitude;
      //         this.geoLocation.currentLocationLat = position.coords.latitude;
      //         this.geoInfoToDistrictName(position.coords.latitude, position.coords.longitude);
      //     }.bind(this), function (error) { // getCurrentPosition 비동기 실행 결과 값을 컴포넌트에 매핑
      //         console.log('Error occurred. Error code: ' + error.code);
      //         switch (error.code) {
      //         case 0:
      //             console.log("Geolocation unknown error");
      //             break;
      //         case 1:
      //             console.log("Geolocation permission denied");
      //             break;
      //         case 2:
      //             console.log("Geolocation position unavailble");
      //             break;
      //         case 3:
      //             console.log("Getting location info timed out");
      //             break;
      //         default:
      //             console.log("Track the error");
      //         }
      //     }, { maximumAge: 5 * 60 * 1000 });
      // },
      geoInfoToDistrictName(lat, lng) {
        var self = this;
        naver.maps.Service.reverseGeocode({
          location: new naver.maps.LatLng(lat, lng),
        }, function(status, response) {
          if (status !== naver.maps.Service.Status.OK) {
            return alert('Something wrong!');
          }

          var result = response.result, // 검색 결과의 컨테이너
            items = result.items; // 검색 결과의 배열
          //console.log(items[0].addrdetail.sigugun);

          self.currentDistrict = items[0].addrdetail.sigugun;
          //console.log(self.currentDistrict);
        });
      },
      updateAppColor(status) {
        switch (status) {
          case '나쁨':
            this.appStyle.background = '#FA4659';
            break;
          case '보통':
            this.appStyle.background = '#ffa020';
            break;
          case '좋음':
            this.appStyle.background = '#53dcbf';
            break;
          default:
        }
      }
    },
    beforeCreate: function() {

    },
    created: function() {
      var self = this;

      setTimeout(function() {
        self.getApi();
      }, 500);
    }
  }
</script>

<style scoped>
  .main-wrap{
    height: 100%;
    text-align: center;
  }
  .main-wrap .air-container{
    display: flex;
    width: 100%;
    height: 72%;
    align-items: center;
    justify-content: center;
    color: #fff;
  }
  .main-wrap .air-container .far{
    font-size: 7.5em;
  }
  .main-wrap .air-container .air-score{
    font-size: 2.8em;
    font-weight: 300;
    margin-top: 20px;
  }
  .main-wrap .air-container .air-state{
    font-size: 1.5em;
    font-weight: 700;
    margin-top: 35px;
  }
  .main-wrap .air-container .date{
    font-size: 1.15em;
    font-weight: 300;
    margin-top: 15px;
    opacity: .8;
  }
  .main-wrap .air-info-wrap{
    position: absolute;
    bottom: 0;
    display: flex;
    width: 100%;
    padding: 30px 0;
    margin: 0;
    color: #fff;
    background: rgba(255, 255, 255, .2);
  }
  .main-wrap .air-info-wrap li{
    list-style: none;
    flex: 1;
  }
  .main-wrap .air-info-wrap li span{
    display: block;
  }
  .main-wrap .air-info-wrap li span.tit{
    font-size: 1.15em;
    margin-bottom: 8px;
  }
  .main-wrap .air-info-wrap li span.far{
    font-size: 2em;
    margin-bottom: 2px;
  }
</style>

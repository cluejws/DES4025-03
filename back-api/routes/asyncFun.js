const { default: axios} = require("axios");

//x:경도
//y:위도
const GetNavigator = async(startX,startY,endX,endY)=>{
    try{
        let result = await axios({
            withCredentials: true,
            method : 'POST',
            url: "https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1",
            async: false,
            params : {
                "appKey": "l7xxcfefdc48e5484976b1712b4452c69462",
                "startX" : startX,
                "startY" : startY,
                "endX": endX,
                "endY": endY,
                "startName": "출발지",
                "endName": "목적지",
                "reqCoordType": "WGS84GEO",
                "resCoordType": "EPSG3857"     
            }
        });
        let json = {
            navigator:null
        }
        json.navigator = result.data.features[0].properties.description;
        
        return json;
        
    }catch(error){
        console.log(error);
    }

}

const GetPOIsearch = async(endPoint)=>{
    try{
        searchKeyword = encodeURIComponent(endPoint);
        appKey = "l7xxcfefdc48e5484976b1712b4452c69462";
        resCoordType = "WGS84GEO";
        url = `https://apis.openapi.sk.com/tmap/pois?version=1&appKey=${appKey}&searchKeyword=${searchKeyword}&resCoordType=${resCoordType}`;
        let result = await axios.get(`${url}`);
        
        let json = {
            endPointX : null,
            endPointY : null
        }
        json.endPointX = result.data.searchPoiInfo.pois.poi[0].frontLon; // 목적지 경도
        json.endPointY = result.data.searchPoiInfo.pois.poi[0].frontLat; // 목적지 위도
        
        return json;

    }catch(error){
        console.log(error)
    }
}

module.exports.GetNavigator = GetNavigator;
module.exports.GetPOIsearch = GetPOIsearch;

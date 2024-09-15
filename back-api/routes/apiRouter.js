const express = require("express");
const data = require("./asyncFun.js");
const router = express.Router();

router.get('/navigation',async(req, res, next)=>{

    try{
        // 1. 요청 쿼리 파라미터
        let start_longitude = req.query.longitude;
        let start_latitude = req.query.latitude;
        let endPoint = req.query.endPoint;

        // 2. T-MAP API 요청
        // T-MAP API 요청(명칭 통합 검색)
        // T-MAP API 요청(경로 안내)
        let json = {
            description: null
        }

        // 2-1: (명칭 통합 검색)
        let end_longitude = (await data.GetPOIsearch(endPoint)).endPointX;
        let end_latitude = (await data.GetPOIsearch(endPoint)).endPointY;

        // 2-2: (경로 안내)
        json.description = await data.GetNavigator(
            start_longitude,
            start_latitude,
            end_longitude,
            end_latitude
        );
        
        // 3. RETURN
        res.json(json);

    } catch(error){
        next(error);
    }
})

module.exports = router;

package com.zkyf.address.service;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddressLngLatExchange {

    @Autowired
    private RestTemplate restTemplate;

    //写到配置文件中
    private static final String KEY = "ee6ec43f6a98f09c7c977a0413237c5b";
    private static final String OUTPUT = "JSON";
    private static final String GET_LNG_LAT_URL = "https://restapi.amap.com/v3/geocode/geo";
    private static final String GET_ADDR_FROM_LNG_LAT = "https://restapi.amap.com/v3/geocode/regeo";
    private static final String EXTENSIONS_ALL = "all";

    private static final Logger LOGGER = Logger.getLogger(AddressLngLatExchange.class);
    /**
     *
     * @description 根据传进来的一个地址，查询对应的经纬度
     * @param
     * @return
     */
    public  Map<String, Object> getLngLatFromOneAddr(String address) {

        if (StringUtils.isBlank(address)) {
            LOGGER.error("地址（" + address + "）为null或者空");
            return null;
        }

//        Map<String, String> params = new HashMap<String, String>();
//        params.put("address", address);
//        params.put("output", OUTPUT);
//        params.put("key", KEY);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(GET_LNG_LAT_URL).
                append("?key=").append(KEY).
                append("&output=").append(OUTPUT).
                append("&address=").append(address);

//        String result = HttpclientUtil.post(params, GET_LNG_LAT_URL);
//        String result = restTemplate.postForObject(GET_LNG_LAT_URL, params, String.class);
        String result = restTemplate.getForObject(stringBuffer.toString(), String.class);

        Pair<BigDecimal, BigDecimal> pair = null;
        Map<String, Object> map = new HashMap<>();

        // 解析返回的xml格式的字符串result，从中拿到经纬度
        // 调用高德API，拿到json格式的字符串结果
        JSONObject jsonObject = JSONObject.fromObject(result);
        // 拿到返回报文的status值，高德的该接口返回值有两个：0-请求失败，1-请求成功；
        int status = Integer.valueOf(jsonObject.getString("status"));

        if (status == 1) {
            JSONArray jsonArray = jsonObject.getJSONArray("geocodes");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                String lngLat = json.getString("location");
                String[] lngLatArr = lngLat.split(",");
                // 经度
                String longitude = lngLatArr[0];
                // 纬度
                String latitude = lngLatArr[1];

                map.put("longitude", longitude);
                map.put("latitude", latitude);
//                BigDecimal longitudeD = new BigDecimal(longitude);
//                // System.out.println("经度" + longitude);
//                BigDecimal latitudeD = new BigDecimal(latitude);
//                // System.out.println("纬度" + latitude);
//                pair = new MutablePair<BigDecimal, BigDecimal>(longitudeD, latitudeD);
            }

        } else {
            String errorMsg = jsonObject.getString("info");
            LOGGER.error("地址（" + address + "）" + errorMsg);
        }

        return map;
    }


    /**
     *
     * @description 根据经纬度查地址
     * @param lng：经度，lat：纬度
     * @return 地址
     */
    public  String getAddrFromLngLat(String lng, String lat) {
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("location", lng + "," + lat);
//        params.put("output", OUTPUT);
//        params.put("key", KEY);
//        params.put("extensions", EXTENSIONS_ALL);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(GET_ADDR_FROM_LNG_LAT).
                append("?key=").append(KEY).
                append("&location=").append(lng).append(",").append(lat).
                append("&output=").append(OUTPUT)
//                append("&extensions=").append(EXTENSIONS_ALL)
        ;
//        String result = restTemplate.postForObject(GET_ADDR_FROM_LNG_LAT, params, String.class);

        String result = restTemplate.getForObject(stringBuffer.toString(), String.class);

        JSONObject json = JSONObject.fromObject(result);
        String status = json.getString("status");
        String address = null;
        if ("1".equals(status)) {
            JSONObject regeocode = JSONObject.fromObject(json.get("regeocode"));
            address = regeocode.getString("formatted_address");
        }
        return address;
    }
}
package com.zkyf.address.controller;

import com.zkyf.address.service.AddressLngLatExchange;
import com.zkyf.address.utils.ApiCode;
import com.zkyf.address.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController()
@RequestMapping("com/api/")
public class AddressController {

    @Autowired
    private AddressLngLatExchange addressLngLatExchange;


    /**
     *
     * @param lng
     * @param lat
     * @param kind 1.GPS 2.高德
     * @return
     */
    @PostMapping("getAddrFromLngLat")
    public Result<String> getAddrFromLngLat(@RequestParam("lng") String lng, @RequestParam("lat") String lat, @RequestParam("kind") Integer kind) {
        if (kind == 1) {//GPS
            // TODO: 2019/2/28  GPS
            String addrFromLngLat = addressLngLatExchange.getAddrFromLngLat(lng, lat);
            return Result.ok(addrFromLngLat);
        } else if (kind == 2) {//高德
            String addrFromLngLat = addressLngLatExchange.getAddrFromLngLat(lng, lat);
            return Result.ok(addrFromLngLat);
        }
        return Result.error(ApiCode.BAD_REQUEST, "kind 目前只可 1.GPS 2.高德");
    }

    @PostMapping("getLngLatFromOneAddr")
    public Result<Map<String, Object>> getLngLatFromOneAddr(@RequestParam("address")String address){
        Map<String, Object> lngLatFromOneAddr = addressLngLatExchange.getLngLatFromOneAddr(address);
        return Result.ok(lngLatFromOneAddr);
    }





}

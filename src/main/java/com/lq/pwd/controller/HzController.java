package com.lq.pwd.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.lq.pwd.mapper.HzMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@Slf4j
public class HzController {

    static int pageSize = 30;
    static Map<String,String> ipList = new HashMap<>();
    @Autowired
    HzMapper hzMapper;
    @RequestMapping("/av/hz/checkSave/{hid}")
    public R checkSave(@PathVariable String hid){
        if (!hid.matches("\\d{4}")){
            return R.failed(hid+"不符合格式要求");
        }
        HashMap map = new HashMap();
        map.put("hid", StrUtil.padPre(hid,4,"0"));
        List<JSONObject> res = hzMapper.selectHz(map   );
        if (res==null||res.size()==0)return R.ok(0).setMsg(hid+"未保存");
        JSONObject obj = res.get(0);
        if ("1".equals(obj.getString("save")))return R.ok(1).setMsg(hid+"已保存");
        return R.ok(0).setMsg(hid+"未保存");
    }

    @RequestMapping("/av/hz/getAllHid/{page}")
    public R getData(@PathVariable Integer page){
        int start = page*100+1;
        int end = page*100+100;
        JSONObject req = new JSONObject();
        req.put("starthid", StrUtil.padPre(""+start,4,"0"));
        req.put("endhid", StrUtil.padPre(""+end,4,"0"));
        req.put("save","1");
        List<JSONObject> res = hzMapper.selectHz(req);
        Map m = new HashMap();
        Map loveMap = new HashMap();
        for (JSONObject r : res) {
            m.put(r.getInteger("hid")+"",r.getString("save"));
            loveMap.put(r.getInteger("hid")+"",r.getString("love"));
        }

        for (int i = start; i <= end ; i++) {
            String hid = i+"";
            if (!m.containsKey(hid)) {
                m.put(hid,"0");
                loveMap.put(hid,"0");
            }
        }
        Map resMap = new HashMap();
        resMap.put("saveHid",m);
        resMap.put("loveHid",loveMap);
        return R.ok(resMap);
    }

    @PostMapping("/av/hz/saveHid/{hid}")
    public R saveHid(@PathVariable String hid){
        Map map = new HashMap<>();
        hid = StrUtil.padPre(hid,4,"0");
        map.put("hid",hid);
        List<JSONObject> res = hzMapper.selectHz(map);

        map.put("save","1");

        Map resData = new HashMap();
        String saveStatus = "";// 0未保存，1更新，2已经保存
        String loveStatus = "0";
        if (res==null||res.size()==0) {
            hzMapper.insertHz(map);
            saveStatus = "0";
        }else {
            loveStatus = res.get(0).getString("love");
            if ("1".equals(res.get(0).getString("save"))){
                saveStatus = "2";
                List<String> paths = new ArrayList<>();
                for (int i = 1; i <= 22; i++) {
                    paths.add(hid+"/"+hid+"("+i+").jpg");
                }
                resData.put("savedImg", paths);
            }else {
                hzMapper.updateHz(map);
                saveStatus = "1";
            }
        }

        resData.put("saveStatus", saveStatus);
        resData.put("loveStatus", loveStatus);
        return R.ok(resData );
    }

    @PostMapping("/av/hz/unSaveHid/{hid}")
    public R unSaveHid(@PathVariable String hid){
        Map<String ,Object> map = new HashMap<>();
        map.put("hid", StrUtil.padPre(hid,4,"0"));
        map.put("save","0");
        hzMapper.updateHz(map);
        return R.ok("0");
    }

    @PostMapping("/av/hz/saveBatch/{hids}")
    public R saveBatch(@PathVariable String hids){
        if (StrUtil.isEmpty(hids)){
            return R.failed("hid为空");
        }else {
            String[] hidArr = hids.split(",");
            for (String s : hidArr) {
                if (StrUtil.isNotEmpty(s)&&s.matches("\\d{4}")){
                    saveHid(s);
                }
            }
        }
        return R.ok("处理完毕");
    }

    @PostMapping("/av/hz/loveHid/{idLove}")
    public R saveLove(@PathVariable String idLove){
        try{
            String[]idAndLove = idLove.split("-");
            Map updateMap = new HashMap();
            updateMap.put("hid",idAndLove[0]);
            updateMap.put("love",idAndLove[1]);
            hzMapper.updateHz(updateMap);
            return R.ok(idAndLove[1]).setMsg("操作成功");
        }catch (Exception e){
            return R.failed("操作失败");
        }
    }

    @PostMapping("/av/hz/latest/{page}")
    public R saveLove(@PathVariable Integer page){
        try{
            Map<String,Object> reqParam = new HashMap<>();
            reqParam.put("startNum",(page-1)*pageSize);
            reqParam.put("pageSize",pageSize);
            List<Map<String,Object>> res =getSearchRes(reqParam);

            Map updateMap = new HashMap();
            updateMap.put("list",res);
            updateMap.put("ip",getIpList().getData());
            return R.ok(updateMap).setMsg("操作成功");
        }catch (Exception e){
            e.printStackTrace();
            return R.failed("操作失败");
        }
    }
    @GetMapping("/av/hz/getIp")
    public R getIpList(){
        if (ipList.size()>0)return R.ok(ipList);
        Map map = new HashMap();
        map.put("hid","pc");
        List<JSONObject> res1 = hzMapper.selectHz(map);
        if (res1.size()>0) ipList.put("pc",res1.get(0).getString("save"));
        map.put("hid","mb");
        List<JSONObject> res2 = hzMapper.selectHz(map);
        if (res2.size()>0) ipList.put("mb",res2.get(0).getString("save"));
        return R.ok(ipList);
    }

    @PostMapping("av/hz/setIp/{type}/{ip}")
    public R setIp(@PathVariable String type, @PathVariable String ip){
        if (StrUtil.isEmpty(ip)) return R.failed("未送ip");
        Map reqParam = new HashMap();
        reqParam.put("save",ip);
        if (StrUtil.isEmpty(type)||"pc,mb".indexOf(type)<0)  {
            return R.failed("ip类型错误，pc设置pcIp,mb设置手机ip");
        }
        reqParam.put("hid", type);
        hzMapper.updateHz(reqParam);
        ipList.put(type,ip);
        return R.ok("ip设置成功");
    }

    @PostMapping("/av/hz/saveGood")
    public R saveGoodBatch(@RequestBody Map req){
        String batchHid = (String) req.get("batchHid");
        if (StrUtil.isEmpty(batchHid)){
            return R.failed("hid为空");
        }else {
            String[] hidArr = batchHid.split(",");
            for (String s : hidArr) {
                if (StrUtil.isEmpty(s))continue;
                Map map = new HashMap<>();
                map.put("hid",s);
                List res = hzMapper.selectHz(map);
                if (res!=null&&res.size()>0) continue;
                map.put("save","1");
                map.put("love","2");
                hzMapper.insertHz(map);
            }
        }
        return R.ok("处理完毕");
    }

    @PostMapping("/av/hz/checkGoodSave/{ID}")
    public R searchGood(@PathVariable String ID){
        if (StrUtil.isEmpty(ID)) return R.failed("参数为空");
        Map reqParam = new HashMap();
        reqParam.put("likeId","%"+ID+"%");
        reqParam.put("love","2");
        return R.ok(getSearchRes(reqParam));
    }

    @PostMapping("/av/hz/getRecentGood/{page}")
    public R getRent(@PathVariable Integer page) {
        Map<String,Object> reqParam = new HashMap<>();
        reqParam.put("startNum",(page-1)*pageSize);
        reqParam.put("pageSize",pageSize);
        reqParam.put("love","2");
        return R.ok(getSearchRes(reqParam));
    }

    /**
     * 搜索并转换时间格式
     * @param reqParam
     * @return
     */
    private List<Map<String, Object>> getSearchRes(Map<String, Object> reqParam) {
        List<Map<String, Object>> res = hzMapper.selectHzOrderByTime(reqParam);
        res.forEach(r->{
            Date date = (Date) r.get("update_time");
            if (date!=null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                r.put("update_time", simpleDateFormat.format(date));
            }
        });
        return res;
    }

    public static void main(String[] args) {
        FileUtil.listFileNames("D:\\temp\\phone").forEach(c->{

            System.out.print(c.substring(0,c.lastIndexOf("."))+",");
        });
    }
}

package me.dbpj.utils;

import me.dbpj.VO.ResultVO;

/**
 * @Author: Jeremy
 * @Date: 2018/11/7 15:22
 */
public class ResultVOUtil {
    public static ResultVO success(Object object,String time) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setTime(time);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}

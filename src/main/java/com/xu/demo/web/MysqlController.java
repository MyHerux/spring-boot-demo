package com.xu.demo.web;

import com.xu.demo.common.bean.ExceptionType;
import com.xu.demo.common.exception.BusinessException;
import com.xu.demo.entity.Info;
import com.xu.demo.mapper.InfoMapper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 Mysql-Mybatis 的功能
 */
@RestController
@RequestMapping("/sql")
public class MysqlController {
    private final InfoMapper infoMapper;

    @Autowired
    public MysqlController(InfoMapper infoMapper) {
        this.infoMapper = infoMapper;
    }



    @ApiOperation(value = "获取信息", response = Info.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "Integer")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success")
    })
    @GetMapping(value = "/get_user")
    public Info GetUser() {
        return infoMapper.findById(1);
    }

    @ApiOperation(value = "异常抛出", response = Info.class)
    @ApiImplicitParam(name = "id", value = "用户id", dataType = "Integer")
    @ApiResponse(code = 200, message = "success")
    @GetMapping(value = "/throw_exception")
    public Info ThrowException(@RequestParam(value = "id") Integer id) throws BusinessException {
        if (id == 1)
            throw new BusinessException(ExceptionType.DATA_ERROR);
        return infoMapper.findById(1);
    }
}

package com.example.user.controller;

import com.example.user.entity.Admin;
import com.example.user.entity.Student;
import com.example.user.entity.Teacher;
import com.example.user.entity.UserDto;
import com.example.user.service.*;
import com.example.user.utils.JwtUtils;
import com.example.user.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api("用户服务")
@RestController
public class UserController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private JWTService jwtService;

    @ApiOperation(value="判断用户是否存在 (201 : 用户存在 | 202 : 用户不存在) 24ms")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "用户已存在"),
            @ApiResponse(code = 202, message = "用户不存在")
    })
    @GetMapping("/exist")
    public Result exist(@ApiParam("用户名") @RequestParam("userName") String userName,
                        @ApiParam("类型（0 学生 | 1 老师 | 2 管理员）") @RequestParam("type") String type){
        try{
            if (userName == null || userName.equals("")) {return Result.fail("用户名不能为空");}
            if (type == null || type.equals("")) {return Result.fail("类型不能为空");}
            if (type.equals("0")) // 学生
            {
                Student student = studentService.selectByName(userName);
                if (student == null) return Result.isExist(202);
                else return Result.isExist(201);
            }
            else if (type.equals("1")) // 教师
            {
                Teacher teacher = teacherService.selectByName(userName);
                if (teacher == null) return Result.isExist(202);
                else return Result.isExist(201);
            }
            else if (type.equals("2")) // 管理员
            {
                Admin admin = adminService.selectByName(userName);
                if (admin == null) return Result.isExist(202);
                else return Result.isExist(201);
            }
            return Result.fail("类型数据错误");
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail("查询失败");
        }
    }

    @ApiOperation(value="注册 (200: 注册成功 |400 : 注册失败) 40ms")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "注册成功"),
            @ApiResponse(code = 400, message = "注册失败"),
    })
    @PostMapping("/register")
    public Result register(@ApiParam("传入json用户数据") @RequestBody UserDto user){
        try{
            String userName = user.getUserName();
            String userPassword = user.getUserPassword();
            //String userPhone = user.getUserPhone();
            String type = user.getType();
            if (userName == null || userName.equals("") || userPassword == null || userPassword.equals(""))
            {return Result.fail("用户名或密码不能为空");}
            //if (userPhone == null || userPhone.equals("")) {return Result.fail("手机号不能为空");}
            if (type == null || type.equals("")) {return Result.fail("类型不能为空");}
            //String storedCode = verificationCodeService.getVerificationCode(userPhone);
            //if (user.getCode() == null || !storedCode.equals(user.getCode())) {return Result.fail("验证码错误");}
            int s;
            if (type.equals("0"))
            {
                Student student = new Student(user);
                s = studentService.insert(student);
            }
            else if (type.equals("1"))
            {
                Teacher teacher = new Teacher(user);
                s = teacherService.insert(teacher);
            }
            else if (type.equals("2"))
            {
                Admin admin = new Admin(user);
                s = adminService.insert(admin);
            }
            else
            {
                return Result.fail("类型数据错误");
            }
            if (s == 1) return Result.suc("注册成功");
            else return Result.fail("注册失败");
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail("注册失败");
        }
    }

    @ApiOperation(value="登录 (200: 登陆成功 |400 : 登陆失败) 第一次 802ms 第二次20ms")
    @PostMapping("/login")
    public Result login(@ApiParam("传入用户名和密码 返回token字符串") @RequestBody UserDto user){
        try {
            String userName = user.getUserName();
            String userPassword = user.getUserPassword();
            String type = user.getType();
            if (userName == null || userName.equals("") || userPassword == null || userPassword.equals(""))
            {return Result.fail("用户名或密码不能为空");}
            if (type == null || type.equals("")) {return Result.fail("类型不能为空");}
            String userId = null;
            if (type.equals("0"))
            {
                Student right = studentService.selectByName(userName);
                if (right != null && right.getUserPassword().equals(userPassword))
                    userId = right.getId().toString();
            }
            else if (type.equals("1"))
            {
                Teacher right = teacherService.selectByName(userName);
                if (right != null && right.getUserPassword().equals(userPassword))
                    userId = right.getId().toString();
            }
            else if (type.equals("2"))
            {
                Admin right = adminService.selectByName(userName);
                if (right != null && right.getUserPassword().equals(userPassword))
                    userId = right.getId().toString();
            }
            else
            {
                return Result.fail("类型数据错误");
            }
            if (userId == null) return Result.fail("用户名或密码错误");
            String token = jwtService.getJWT(userId);
            if (token == null)
            {
                token = JwtUtils.createToken(userId, type);
                jwtService.storeJWT(userId, token, 6);
            }
            return Result.suc(token);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail("登陆失败");
        }
    }

    @ApiOperation(value="手机号登录 (200: 登陆成功 |400 : 登陆失败) 第一次 750ms 第二次33ms")
    @PostMapping("/loginByPhone")
    public Result loginByPhone(@ApiParam("传入手机号和code和type 返回token字符串") @RequestBody UserDto user){
        try{
            String phone = user.getUserPhone();
            String code = user.getCode();
            String type = user.getType();
            if (phone == null || code == null || type == null) return Result.fail("未传入所需数据");
            String storedCode = verificationCodeService.getVerificationCode(phone);
            if (user.getCode() == null || !storedCode.equals(code)) {return Result.fail("验证码错误");}
            String userId = null;
            if (type.equals("0"))
            {
                Student right = studentService.selectByPhone(phone);
                if(right != null)
                    userId = right.getId().toString();
            }
            else if (type.equals("1"))
            {
                Teacher right = teacherService.selectByPhone(phone);
                if(right != null)
                    userId = right.getId().toString();
            }
            else if (type.equals("2"))
            {
                Admin right = adminService.selectByPhone(phone);
                if(right != null)
                    userId = right.getId().toString();
            }
            else
            {
                return Result.fail("类型数据错误");
            }
            if (userId == null) return Result.fail("手机未绑定账号");
            String token = jwtService.getJWT(userId);
            if (token == null)
            {
                token = JwtUtils.createToken(userId, type);
                jwtService.storeJWT(userId, token, 7);
            }
            return Result.suc(token);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail("登陆失败");
        }
    }

    @ApiOperation(value="jwt状态验证 19ms")
    @GetMapping("/check")
    public Result check()
    {
        return Result.suc("验证通过");
    }

    @ApiOperation(value="获取用户信息 (200: 获取成功 |400 : 获取失败) 18ms")
    @GetMapping("/getUser")
    public Result getUser(@RequestHeader("userId") String sub,
                          @RequestHeader("userType") String type){
        if (sub == null || type == null || sub.equals("") || type.equals(""))
            {return Result.fail("系统内部错误");}
        if (sub.equals("error_token")) {return Result.fail("获取用户信息失败");}
        Long id = Long.parseLong(sub);
        UserDto user = null;
        if (type.equals("0"))
        {
            Student student = studentService.selectById(id);
            if (student != null) user = new UserDto(student);
        }
        else if (type.equals("1"))
        {
            Teacher teacher = teacherService.selectById(id);
            if (teacher != null) user = new UserDto(teacher);
        }
        else if (type.equals("2"))
        {
            Admin admin = adminService.selectById(id);
            if (admin != null) user = new UserDto(admin);
        }
        else
        {
            return Result.fail("类型信息错误");
        }

        if (user != null) return Result.suc(user);
        else return Result.fail("获取用户信息失败");
    }

    @ApiOperation(value="发送验证码 5min后过期 15ms")
    @GetMapping("/sendCode")
    public Result sendCode(@RequestParam("phone") String phoneNumber) {
        if (!phoneNumber.equals("17398213132") &&!phoneNumber.equals("13736578295")
                && !phoneNumber.equals("18822562630")) return Result.fail("电话未绑定");
        String code = "123456";  // 生成验证码的逻辑
        verificationCodeService.storeVerificationCode(phoneNumber, code, 300);
        return Result.suc("验证码已发送");
    }

    // 新增一个功能 selectAllUser
    @ApiOperation(value="获取所有学生信息(管理员端) 17ms")
    @GetMapping("/selectAllStudent")
    public Result selectAllStudent(@RequestHeader("userId") String sub,
                                   @RequestHeader("userType") String type)
    {
        if (sub == null || type == null || sub.equals("") || type.equals(""))
        {return Result.fail("系统内部错误");}
        if (sub.equals("error_token")) {return Result.fail("获取用户信息失败");}
        Long id = Long.parseLong(sub);
        if (type.equals("2"))
        {
            Admin admin = adminService.selectById(id);
            if (admin != null)
            {
                List<Student> students = studentService.selectAll();
                return Result.suc(students);
            }
        }
        return Result.fail("无管理员权限");
    }

    @ApiOperation(value="获取所有教师信息(管理员端) 18ms")
    @GetMapping("/selectAllTeacher")
    public Result selectAllTeacher(@RequestHeader("userId") String sub,
                                   @RequestHeader("userType") String type)
    {
        if (sub == null || type == null || sub.equals("") || type.equals(""))
        {return Result.fail("系统内部错误");}
        if (sub.equals("error_token")) {return Result.fail("获取用户信息失败");}
        Long id = Long.parseLong(sub);
        if (type.equals("2"))
        {
            Admin admin = adminService.selectById(id);
            if (admin != null)
            {
                List<Teacher> teachers = teacherService.selectAll();
                return Result.suc(teachers);
            }
        }
        return Result.fail("无管理员权限");
    }
}

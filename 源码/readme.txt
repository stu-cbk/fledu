75012991源码结构

back 后端
  |
  --fledu 父项目
        |
        --gateway 网关 10010
        --user-service 用户服务 8081
        --helper-service 教育机器人服务 8091
        --doc-service 心理测试服务 8101
        --virtual-service 实时英文对话服务 8131
        --ppt-service 教学ppt生成服务 8111
        --video-service 教学视频合成服务 8121

front 前端
  |
  -- educationWeb 主项目 8080
  -- pptist 子项目 5173

mysql 相关数据库
  |
  --fledu_user.sql 用户服务相关数据库代码
  --fledu_chat.sql 教育机器人服务相关数据库代码
  --fledu_doc.sql 心理测试服务相关数据库代码
  --fledu_ppt.sql 教学ppt合成服务相关数据库代码
  --fledu_video.sql 教学视频合成服务相关数据库代码 

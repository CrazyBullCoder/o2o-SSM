# 加密连接数据库明文密码
## 利用PropertyPlaceholderConfigurer实现对称加密
## Base64Encoder和Base64Decoder无法使用解决办法
原方法

BASE64Encoder encoder = new BASE64Encoder();

String imagestr =  encoder.encode(captcha);

BASE64Decoder decoder = new BASE64Decoder();

byte[] bytes = decoder.decodeBuffer(imagestr);

现方法

import java.util.Base64.Encoder
import java.util.Base64.Decoder
 
Encoder encoder = Base64.getEncoder();
String result = encoder.encodeToString(byteArray);
 
Decoder decoder = Base64.getDecoder();
byte[] result = decoder.decode(str);
原因是/lib/tool.jar和/lib/rt.jar已经从Java SE 9中删除
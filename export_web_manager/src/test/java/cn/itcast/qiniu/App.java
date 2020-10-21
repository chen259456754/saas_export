package cn.itcast.qiniu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * 空间名称：zaku00306
 * 临时域名：qijmxd3vm.hd-bkt.clouddn.com   （上传时候不用，访问时候用）
 * AK： QxLPVrGYBO7m6xQG2HuB6TSvaPWYIfiNBRZtw38q
 * SK：aD8Y6USuriz2k8VTjLhixc9uA5Z1A2QQmrbhiPWN
 */
public class App {
    public static void main(String[] args) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "QxLPVrGYBO7m6xQG2HuB6TSvaPWYIfiNBRZtw38q";
        String secretKey = "aD8Y6USuriz2k8VTjLhixc9uA5Z1A2QQmrbhiPWN";
        String bucket = "zaku00306";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:\\Users\\chjkt\\Pictures\\微信图片_20201019102823.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "pic0001";

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException e) {
            Response response = e.response;
            System.err.println(response.toString());
            try {
                System.err.println(response.bodyString());
            } catch (QiniuException qiniuException) {
                qiniuException.printStackTrace();
            }
        }
    }
}

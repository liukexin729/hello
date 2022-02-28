package com.example.hello.controller;

import com.example.hello.config.SftpConfig;
import com.example.hello.entity.MswebBanner;
import com.example.hello.server.impl.BannerServiceImpl;
import com.example.hello.util.FileUtil;
import com.example.hello.util.IdUtil;
import com.example.hello.util.SFTP;
import com.jcraft.jsch.SftpException;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

@RestController
public class BannerController {

    /**
     * @Description: 上传路径
     */
    @Value("${upload.upload-Path}")
    private String uplaodPath;

    /**
     * @Description: // 访问路径
     */
    @Value("${upload.uploadFolder}")
    private String uploadFolder;

    @Value("${upload.domain-name}")
    private String domainName;

    @Value("${upload.height}")
    private Integer height;

    @Value("${upload.width}")
    private Integer width;

    @Value("${upload.size}")
    private long size;

    @Value("${sftp.hostname}")
    private String hostname;
    @Value("${sftp.port}")
    private Integer port;
    @Value("${sftp.username}")
    private String username;
    @Value("${sftp.password}")
    private String password;
    @Value("${sftp.timeout}")
    private Integer timeout;
    @Value("${sftp.remoteRootPath}")
    private String remoteRootPath;

    @Autowired
    private BannerServiceImpl bannerService;

    @PostMapping(value = "/getAll")
    public String getAll() {
        List<MswebBanner> all = bannerService.getAll();
        return all.toString();
    }

    @ApiOperation("上传banner")
    @PostMapping(value = "/upload.do")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file == null) {
            return "图片不能为空";
        }
        long size1 = file.getSize();
        if (size1 > size) {
            return "图片在" + size + "以内";
        }
        // 通过MultipartFile得到InputStream，从而得到BufferedImage
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        if (bufferedImage == null) {
            return "您上传的不是图片";
        }
        Integer width1 = bufferedImage.getWidth();
        Integer height1 = bufferedImage.getHeight();
        if (width1 >= width && height1 >= height) {
            return "图片长:" + width + ",宽:" + height + ",请规范上传";
        }
        // 获取文件原名
        String fileName = file.getOriginalFilename();
        // 获取文件后缀
        String fileSuffix = FileUtil.fileSuffix(fileName);
        String createName = IdUtil.createOrderNumber("", 1, 0) + fileSuffix;
        File targetFile = new File(uplaodPath, createName);
        /**
         *  http://127.0.0.1:8080/upload/createName
         */
//        String bannerUrlYaoyao = domainName + uploadFolder + createName;
        /**
         *  /upload/createName
         */
//        String bannerUrlWeb = remotestaticAccessPath + createName;
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdir();
        }

        file.transferTo(targetFile);

        bannerService.updateloadTp(file,createName);
        return "success";
    }

    @ApiOperation("更新banner")
    @PostMapping(value = "/updateBanner")
    public String update(MswebBanner mswebBanner) {
        if(mswebBanner == null){
            return "传递参数错误";
        }
        if (mswebBanner.getBannerUrlYaoyao() != "" && mswebBanner.getBannerUrlYaoyao() != null) {
//            MswebBanner bannerById = bannerService.getBannerById(mswebBanner.getBannerId());
//            String bannerUrlYaoyao = bannerById.getBannerUrlYaoyao();
            String bannerUrlYaoyao = " http://127.0.0.1:8080/upload/1021123083374626.jpg";
            String[] split = bannerUrlYaoyao.split("/");
            /**
             * get url createName
             */
            String s1 = split[split.length - 1];
            SFTP ftp = new SFTP(3, 6000);
            SftpConfig sftpConfig = new SftpConfig(hostname, port, username, password, timeout, remoteRootPath);
//            SftpConfig sftpConfig1 = new SftpConfig(hostname1, port1, username1, password1, timeout1, remoteRootPath1);
            try {
                ftp.delete(remoteRootPath, s1, sftpConfig);
//                ftp.delete(remoteRootPath1, s1, sftpConfig1);
            } catch (SftpException e) {
                e.printStackTrace();
                return "修改失败";
            }

            /**
             * delete /upload exist createName
             */
            FileUtil.existsDelete(s1, uplaodPath);
        }
//        boolean insert = bannerService.updateBanner(mswebBanner);
//        if (insert) {
//            return "修改成功";
//        }
//        return "修改失败";
        return "success";
    }
}

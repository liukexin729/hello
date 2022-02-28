package com.example.hello.server.impl;

import com.example.hello.config.SftpConfig;
import com.example.hello.entity.MswebBanner;
import com.example.hello.mapper.BannerMapper;
import com.example.hello.server.BannerService;
import com.example.hello.util.SFTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Value("${upload.upload-Path}")
    private String uplaodPath;

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

    @Value("${sftp1.hostname}")
    private String hostname1;
    @Value("${sftp1.port}")
    private Integer port1;
    @Value("${sftp1.username}")
    private String username1;
    @Value("${sftp1.password}")
    private String password1;
    @Value("${sftp1.timeout}")
    private Integer timeout1;
    @Value("${sftp1.remoteRootPath}")
    private String remoteRootPath1;
    @Value("${sftp1.remotestaticAccessPath}")
    private String  remotestaticAccessPath;

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<MswebBanner> getAll() {
        return bannerMapper.getAll();
    }

    @Override
    public void updateloadTp(MultipartFile file, String createName) throws Exception {
        SFTP ftp = new SFTP(3, 6000);
        String uplaodFile = uplaodPath + createName;
        SftpConfig sftpConfig = new SftpConfig(hostname, port, username, password, timeout, remoteRootPath);
        ftp.upload(remoteRootPath, uplaodFile, sftpConfig);
//        SftpConfig sftpConfig1 = new SftpConfig(hostname1, port1, username1, password1, timeout1, remoteRootPath1);
//        ftp.upload(remoteRootPath1, uplaodFile, sftpConfig1);
    }

    @Override
    public MswebBanner getBannerById(int bannerId) {
        return bannerMapper.getBannerById(bannerId);
    }



}

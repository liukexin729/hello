package com.example.hello.server;

import com.example.hello.entity.MswebBanner;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BannerService {

    /**
     * 获取所有banner
     * @return
     */
    public List<MswebBanner> getAll();

    /**
     * 上传文件
     * @param file
     * @param createName
     * @throws Exception
     */
    public void updateloadTp(MultipartFile file, String createName) throws Exception;

    /**
     * by id get banner
     * @param bannerId
     * @return
     */
    public MswebBanner getBannerById(int bannerId);
}

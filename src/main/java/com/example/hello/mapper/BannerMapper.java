package com.example.hello.mapper;

import com.example.hello.entity.MswebBanner;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BannerMapper {

    /**
     * 获取所有banner
     * @return
     */
    public List<MswebBanner> getAll();

    /**
     * by id get banner
     * @param bannerId
     * @return
     */
    public MswebBanner getBannerById(int bannerId);

    /**
     * update banner
     * @param mswebBanner
     * @return
     */
    public boolean updateBanner(MswebBanner mswebBanner);
}

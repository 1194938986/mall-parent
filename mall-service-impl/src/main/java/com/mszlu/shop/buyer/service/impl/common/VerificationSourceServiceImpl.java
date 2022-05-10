package com.mszlu.shop.buyer.service.impl.common;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.shop.buyer.mapper.VerificationSourceMapper;
import com.mszlu.shop.buyer.service.VerificationSourceService;
import com.mszlu.shop.model.buyer.eums.VerificationSourceEnum;
import com.mszlu.shop.model.buyer.pojo.VerificationSource;
import com.mszlu.shop.model.buyer.vo.commons.VerificationSourceVo;
import com.mszlu.shop.model.buyer.vo.commons.slider.VerificationVO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@DubboService(version = "1.0.0",interfaceClass = VerificationSourceService.class)
public class VerificationSourceServiceImpl implements VerificationSourceService {

    @Resource
    private VerificationSourceMapper verificationSourceMapper;

    public VerificationVO findVerificationSource() {
        LambdaQueryWrapper<VerificationSource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VerificationSource::getDeleteFlag,false);
        List<VerificationSource> dbList = verificationSourceMapper.selectList(queryWrapper);
        List<VerificationSource> resourceList = new ArrayList<>();
        List<VerificationSource> sliderList = new ArrayList<>();
        for (VerificationSource item : dbList) {
            if (item.getType().equals(VerificationSourceEnum.RESOURCE.name())) {
                resourceList.add(item);
            } else if (item.getType().equals(VerificationSourceEnum.SLIDER.name())) {
                sliderList.add(item);
            }
        }
        VerificationVO verificationVO = new VerificationVO();
        verificationVO.setVerificationResources(copyList(resourceList));
        verificationVO.setVerificationSlider(copyList(sliderList));
        return verificationVO;
    }

    public VerificationSourceVo copy(VerificationSource verificationSource){
        if (verificationSource == null){
            return null;
        }
        VerificationSourceVo verificationSourceVo = new VerificationSourceVo();
        BeanUtils.copyProperties(verificationSource,verificationSourceVo);
        return verificationSourceVo;
    }

    public List<VerificationSourceVo> copyList(List<VerificationSource> verificationSourceList){
        List<VerificationSourceVo> verificationSourceVoList = new ArrayList<>();
        for (VerificationSource verificationSource : verificationSourceList) {
            verificationSourceVoList.add(copy(verificationSource));
        }
        return verificationSourceVoList;
    }
}

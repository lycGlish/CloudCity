package com.lyc.city.info.app;

import java.util.Arrays;
import java.util.Map;

import com.lyc.city.info.feign.ImageFeignService;
import com.lyc.city.utils.PageUtils;
import com.lyc.city.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lyc.city.entity.ImageEntity;
import com.lyc.city.info.service.ImageService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 *
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-08-20 15:15:31
 */
@RestController
@RequestMapping("info/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageFeignService imageFeignService;

    @PostMapping("/upload/image")
    public R uploadImage(@RequestParam("multipartFile") MultipartFile multipartFile){
        return imageFeignService.policy(multipartFile);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("info:image:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = imageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{imageId}")
    //@RequiresPermissions("info:image:info")
    public R info(@PathVariable("imageId") Long imageId){
		ImageEntity image = imageService.getById(imageId);

        return R.ok().put("image", image);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("info:image:save")
    public R save(@RequestBody ImageEntity image){
		imageService.save(image);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("info:image:update")
    public R update(@RequestBody ImageEntity image){
		imageService.updateById(image);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("info:image:delete")
    public R delete(@RequestBody Long[] imageIds){
		imageService.removeByIds(Arrays.asList(imageIds));

        return R.ok();
    }

}

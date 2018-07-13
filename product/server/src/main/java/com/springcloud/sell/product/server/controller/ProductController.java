package com.springcloud.sell.product.server.controller;

import com.google.common.collect.Lists;
import com.springcloud.sell.product.server.VO.ProductInfoVO;
import com.springcloud.sell.product.server.VO.ProductVO;
import com.springcloud.sell.product.server.domian.ProductCategory;
import com.springcloud.sell.product.server.domian.ProductInfo;
import com.springcloud.sell.product.server.service.CategoryService;
import com.springcloud.sell.product.server.service.ProductService;
import com.springcloud.sell.product.server.utils.ServerResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * 1.查询所有在架的商品
     * 2.获取类目type列表
     * 3.查询类目
     * 4.构造数据
     */
    @GetMapping("/list")
    public ServerResponse<List<ProductVO>> list() {
        //1.查询 所有在架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2.获取类目type列表
        List<Integer> categoryListType = productInfoList.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());

        //3.查询类目
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryListType);

        //4.构造数据
        List<ProductVO> list = Lists.newArrayList();
        productCategoryList.forEach(productCategory -> {
            ProductVO vo = modelMapper.map(productCategory, ProductVO.class);
            //根据categoryType查询ProductInfoVOList
            List<ProductInfoVO> productInfoVOList = Lists.newArrayList();
            productInfoList.forEach(productInfo -> {
                if (productCategory.getCategoryType() == productInfo.getCategoryType()) {
                    ProductInfoVO productInfoVO = modelMapper.map(productInfo, ProductInfoVO.class);
                    productInfoVOList.add(productInfoVO);
                }
            });
            vo.setProductInfoVOList(productInfoVOList);
            list.add(vo);
        });

        return ServerResponse.createBySuccess(list);
    }

}

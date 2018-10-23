package com.example.demo.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.logging.log4j.util.Strings;
import org.hibernate.validator.cfg.context.ParameterConstraintMappingContext;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.FilterParam;
import com.example.demo.entity.Phone;
import com.example.demo.entity.PhoneDisplay;
import com.example.demo.entity.PhoneListInfor;
import com.example.demo.entity.Prices;
import com.example.demo.entity.Prices2;
import com.example.demo.entity.SalePromotion;
import com.example.demo.entity.SalePromotionInfor;
import com.example.demo.repository.PhoneRepository;
import com.example.demo.repository.PriceRepository;
import com.example.demo.repository.SalePromotionRepository;
import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;
import com.mongodb.MongoClient;
import com.mongodb.client.DistinctIterable;

@Controller
public class phoneController {
    
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private SalePromotionRepository salePromotionRepository;
    @Autowired
    private PriceRepository priceRepository;
    
    private MongoOperations mongoOperations = new MongoTemplate(new MongoClient(), "jd3");
    
    private final static List<String> sensitiveVocabulary = new ArrayList<String>(
                Arrays.asList("其他","暂无信息","其他品牌")
            );
    
    
    @GetMapping("/")
    @ResponseBody
    public Map<String, Object> listInformation() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        ArrayList<Prices> dataList = new ArrayList<Prices>();
        //新的需求，如果在前端传进来一些ram,rom,还有brand,还有keyword
        MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "jd3");               //regex后面加个点就可以匹配全部
       
        Query query = new Query(Criteria.where("prices").elemMatch(Criteria.where("price_value").regex("有货").and("date").is("2018-09-27")));//还支持shop.shop_name，不支持数组操作
        query.fields().include("prices").slice("prices", -1);
        query.skip(0);
        query.limit(20);
        query.with(Sort.by(Direction.ASC, "id"));
        List<Prices> PricesList = mongoOps.find(query,Prices.class);
        for(Prices price : PricesList) {
            PhoneDisplay phone = phoneRepository.findByIdUnity(price.get_id());
            price.getPrices()[0].setPriceValue(price.getPrices()[0].getPriceValue().replace(".00(有货)", ""));
            price.setPhoneDisplay(phone);
            SalePromotion salePromotion = salePromotionRepository.findByIdUnity(price.get_id());
            price.setSalePromotionInfors(salePromotion.getSalePromotion());
            dataList.add(price);
        }
        map.put("data", dataList);
        
        return map;
    }
    
    @GetMapping("/phoneToPrice")//翻页查询后端代码，完成
    @ResponseBody
    public ModelAndView listInformation2(FilterParam param ,@PageableDefault(page=0, size=20) Pageable pageable) {//@RequestParam(defaultValue = "")
        ModelAndView modelAndView = new ModelAndView("list");
        System.out.println(param);
        Page<PhoneListInfor> phones = phoneRepository.findByIdUnity2(param.getBrand(),param.getRam(),param.getRom(),param.getHotSpot(),param.getKeyword(),pageable);//new PageRequest(1, 20)
        //phone.getContent().get(0).setRam("10086");//可以修改！！！
        Iterator<PhoneListInfor> iterator = phones.getContent().iterator();
        while (iterator.hasNext()) {
            PhoneListInfor phone =  iterator.next();
            //System.out.println(phone);
            SalePromotion salePromotion = salePromotionRepository.findByIdUnity(phone.get_id());
            phone.setSalePromotionInfors(salePromotion.getSalePromotion());
            Prices2 prices = priceRepository.findByIdUnity(phone.get_id());
            phone.setPrices(prices.getPrices());
            
        }
        modelAndView.addObject("data", phones);
        Map<String, List<String>> brands = getSelectorLine("phone","brand",15);
        modelAndView.addObject("brands", brands);
        Map<String, List<String>> rams = getSelectorLine("phone","ram",15);
        modelAndView.addObject("rams", rams);
        Map<String, List<String>> roms = getSelectorLine("phone","rom",15);
        modelAndView.addObject("roms", roms);
        Map<String, List<String>> hotSpots = getSelectorLine("phone","hot_spot",15);
        modelAndView.addObject("hot_spots", hotSpots);
        return modelAndView;
    }
    
    private Map<String,List<String>> getSelectorLine(String collectionName, String columnName,int limit){
       
      //获得brands
        int i = 1;
        Map map = new HashMap<String,List<String>>(); 
        ArrayList<String> results = new ArrayList<String>();
        ArrayList<String> extraResults = new ArrayList<String>();
        Iterator<String> iterator = mongoOperations.getCollection(collectionName).distinct(columnName,String.class).iterator();       
        while(iterator.hasNext()) {
            String result = iterator.next();
            if(!sensitiveVocabulary.contains(result) && i<limit) {
                i++;
                results.add(result);
            }
            else {
                extraResults.add(result);
            }
        }
        map.put("result", results);
        map.put("extraResults", extraResults);
        return map;
    }
    
    @GetMapping("/phoneToPriceApi")//翻页查询后端代码，完成
    @ResponseBody
    public Map<String, Object> listInformation3(FilterParam param ,@PageableDefault(page=0, size=20) Pageable pageable) {//@RequestParam(defaultValue = "")       
        HashMap<String, Object> map = new HashMap<String, Object>();
        System.out.println(param);
        Page<PhoneListInfor> phones = phoneRepository.findByIdUnity2(param.getBrand(),param.getRam(),param.getRom(),param.getHotSpot(),param.getKeyword(), pageable);//new PageRequest(1, 20)
        //phone.getContent().get(0).setRam("10086");//可以修改！！！
        Iterator<PhoneListInfor> iterator = phones.getContent().iterator();
        while (iterator.hasNext()) {
            PhoneListInfor phone =  iterator.next();
            SalePromotion salePromotion = salePromotionRepository.findByIdUnity(phone.get_id());
            phone.setSalePromotionInfors(salePromotion.getSalePromotion());
            Prices2 prices = priceRepository.findByIdUnity(phone.get_id());
            phone.setPrices(prices.getPrices());
        }
        map.put("data", phones);
        Map<String, List<String>> brands = getSelectorLine("phone","brand",15);
        map.put("brands", brands);
        Map<String, List<String>> rams = getSelectorLine("phone","ram",15);
        map.put("rams", rams);
        Map<String, List<String>> roms = getSelectorLine("phone","rom",15);
        map.put("roms", roms);
        Map<String, List<String>> hotSpots = getSelectorLine("phone","hot_spot",15);
        map.put("hot_spots", hotSpots);
        List<PhoneDisplay> hot_result =  phoneRepository.findByHotSpot("人工智能");
        //System.out.println(hot_result);
        return map;
    }
    
    @GetMapping("/list")
    public ModelAndView list(String keyword, String brand, String ram, String rom) {//查询参数，还要回显。
        ModelAndView modelAndView = new ModelAndView("list");
        if (brand != null)
            System.out.println(brand);
        ArrayList<Prices> dataList = new ArrayList<Prices>();
        //新的需求，如果在前端传进来一些ram,rom,还有brand,还有keyword
        MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "jd3");               //regex后面加个点就可以匹配全部
        Query query = new Query(Criteria.where("prices").elemMatch(Criteria.where("price_value").regex("有货").and("date").is("2018-09-27")));//还支持shop.shop_name，不支持数组操作
        query.fields().include("prices").slice("prices", -1);
        query.skip(0);
        query.limit(100);
        query.with(Sort.by(Direction.ASC, "id"));
        List<Prices> PricesList = mongoOps.find(query,Prices.class);
        for(Prices price : PricesList) {
            PhoneDisplay phone = phoneRepository.findByIdUnity(price.get_id());
            price.getPrices()[0].setPriceValue(price.getPrices()[0].getPriceValue().replace(".00(有货)", ""));
            price.setPhoneDisplay(phone);
            SalePromotion salePromotion = salePromotionRepository.findByIdUnity(price.get_id());
            price.setSalePromotionInfors(salePromotion.getSalePromotion());
            dataList.add(price);
        }
        modelAndView.addObject("data", dataList);
        return modelAndView;
    }
}

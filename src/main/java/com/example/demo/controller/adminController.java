package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Impresses;
import com.example.demo.entity.Opinions;
import com.example.demo.entity.Phone;
import com.example.demo.entity.PhoneDisplay;
import com.example.demo.entity.Prices2;
import com.example.demo.entity.SalePromotion;
import com.example.demo.repository.ImpressesRepository;
import com.example.demo.repository.OpinionRepository;
import com.example.demo.repository.PhoneRepository;
import com.example.demo.repository.PriceRepository;
import com.example.demo.repository.SalePromotionRepository;

//@RequestMapping("/admin")
@Controller
public class adminController {
    
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private SalePromotionRepository salePromotionRepository;
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private OpinionRepository opinionRepository;
    @Autowired
    private ImpressesRepository impressesRepository;
    
    //查找一个手机的所有信息
    @GetMapping("admin/test")
    @ResponseBody
    public HashMap<String, Object> test() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Phone phone = phoneRepository.findById("jd8302186").get();
        Prices2 prices = priceRepository.findByIdUnity("jd8302186");
        SalePromotion salePromotion = salePromotionRepository.findByIdUnity("jd8302186");
        Opinions opinions = opinionRepository.findByIdUnity("jd8302186");
        Impresses impresses = impressesRepository.findByIdUnity("jd8302186");
        System.out.println(phone);
        System.out.println(prices);
        System.out.println(salePromotion);
        System.out.println(opinions);
        System.out.println(impresses);
        
        map.put("phone", phone);
        map.put("prices", prices);
        map.put("salePromotion", salePromotion);
        map.put("opinions", opinions);
        map.put("impresses", impresses);
        return map;
    }
    
    @GetMapping("admin/phoneListResp")
    @ResponseBody
    public HashMap<String, Object> phoneListResp(@PageableDefault(page=0, size=20) Pageable pageable) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        ArrayList<Prices2> priceList = new ArrayList<Prices2>();
        ArrayList<SalePromotion> salePromotionList = new ArrayList<SalePromotion>();
        ArrayList<Opinions> opinionsList = new ArrayList<Opinions>();
        ArrayList<Impresses> impressesList = new ArrayList<Impresses>();
        
        Page<Phone> phonePage = phoneRepository.findAll(pageable);
        
        List<Phone> phones = phonePage.getContent();
        List<String> ids = phones.stream().map(Phone::get_id).collect(Collectors.toList());
        
        List<Prices2> priceses = priceRepository.findByIds(ids);
        List<SalePromotion> salePromotions = salePromotionRepository.findByIds(ids);
        List<Opinions> opinions = opinionRepository.findByIds(ids);
        List<Impresses> impresses = impressesRepository.findByIds(ids);
        
        List<String> priceIds =  priceses.stream().map(Prices2::get_id).collect(Collectors.toList());
        List<String> saleIds =  salePromotions.stream().map(SalePromotion::get_id).collect(Collectors.toList());
        List<String> opinionsIds =  opinions.stream().map(Opinions::get_id).collect(Collectors.toList());
        List<String> impressesIds =  impresses.stream().map(Impresses::get_id).collect(Collectors.toList());
        
        for(int i=0 ; i<ids.size(); i++) {
            priceList.add(priceses.get(priceIds.indexOf(ids.get(i))));
            salePromotionList.add(salePromotions.get(saleIds.indexOf(ids.get(i))));
            opinionsList.add(opinions.get(opinionsIds.indexOf(ids.get(i))));
            impressesList.add(impresses.get(impressesIds.indexOf(ids.get(i))));
        }
        
        map.put("phonePage", phonePage);
        map.put("priceList", priceList);
        map.put("salePromotionList", salePromotionList);
        map.put("opinionsList", opinionsList);
        map.put("impressesList", impressesList);
        return map;
    }
    
    @GetMapping("admin/phoneList")
    public String phoneList(Model model,@PageableDefault(page=0, size=20) Pageable pageable) {
        //HashMap<String, Object> map = new HashMap<String, Object>();
        ArrayList<Prices2> priceList = new ArrayList<Prices2>();
        ArrayList<SalePromotion> salePromotionList = new ArrayList<SalePromotion>();
        ArrayList<Opinions> opinionsList = new ArrayList<Opinions>();
        ArrayList<Impresses> impressesList = new ArrayList<Impresses>();
        
        Page<Phone> phonePage = phoneRepository.findAll(pageable);
        
        List<Phone> phones = phonePage.getContent();
        List<String> ids = phones.stream().map(Phone::get_id).collect(Collectors.toList());
        
        List<Prices2> priceses = priceRepository.findByIds(ids);
        List<SalePromotion> salePromotions = salePromotionRepository.findByIds(ids);
        List<Opinions> opinions = opinionRepository.findByIds(ids);
        List<Impresses> impresses = impressesRepository.findByIds(ids);
        
        List<String> priceIds =  priceses.stream().map(Prices2::get_id).collect(Collectors.toList());
        List<String> saleIds =  salePromotions.stream().map(SalePromotion::get_id).collect(Collectors.toList());
        List<String> opinionsIds =  opinions.stream().map(Opinions::get_id).collect(Collectors.toList());
        List<String> impressesIds =  impresses.stream().map(Impresses::get_id).collect(Collectors.toList());
        
        for(int i=0 ; i<ids.size(); i++) {
            priceList.add(priceses.get(priceIds.indexOf(ids.get(i))));
            salePromotionList.add(salePromotions.get(saleIds.indexOf(ids.get(i))));
            opinionsList.add(opinions.get(opinionsIds.indexOf(ids.get(i))));
            impressesList.add(impresses.get(impressesIds.indexOf(ids.get(i))));
        }
        
        model.addAttribute("phonePage", phonePage);
        model.addAttribute("priceList", priceList);
        model.addAttribute("salePromotionList", salePromotionList);
        model.addAttribute("opinionsList", opinionsList);
        model.addAttribute("impressesList", impressesList);
        return "admin/phoneList";
    }
}

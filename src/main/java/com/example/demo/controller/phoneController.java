package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpSession;
import javax.sound.midi.Receiver;
import javax.validation.Valid;

import org.apache.logging.log4j.util.Strings;
import org.hibernate.validator.cfg.context.ParameterConstraintMappingContext;
import org.json.JSONException;
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
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Article;
import com.example.demo.entity.Author;
import com.example.demo.entity.Collection;
import com.example.demo.entity.Comment2;
import com.example.demo.entity.FilterParam;
import com.example.demo.entity.Impresses;
import com.example.demo.entity.Opinions;
import com.example.demo.entity.Phone;
import com.example.demo.entity.PhoneDisplay;
import com.example.demo.entity.PhoneListInfor;
import com.example.demo.entity.Prices;
import com.example.demo.entity.Prices2;
import com.example.demo.entity.SalePromotion;
import com.example.demo.entity.SalePromotionInfor;
import com.example.demo.entity.Topic;
import com.example.demo.entity.Trace;
import com.example.demo.entity.User;
import com.example.demo.entity.Wallet;
import com.example.demo.repository.AritcleRepository;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.CollectionRepository;
import com.example.demo.repository.Comment2Repository;
import com.example.demo.repository.ImpressesRepository;
import com.example.demo.repository.OpinionRepository;
import com.example.demo.repository.PhoneRepository;
import com.example.demo.repository.PriceRepository;
import com.example.demo.repository.SalePromotionRepository;
import com.example.demo.repository.TopicRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WalletRepository;
import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.mongodb.MongoClient;
import com.mongodb.client.DistinctIterable;

import junit.framework.Test;

@Controller
public class phoneController {
    
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private CollectionRepository collectionRepository;
    
    private MongoOperations mongoOperations = new MongoTemplate(new MongoClient(), "jd3");
    
    private final static List<String> sensitiveVocabulary = new ArrayList<String>(
                Arrays.asList("其他","暂无信息","其他品牌")
            );
    
    @Resource
    private AuthorRepository authorRepository;
    
    @Resource
    private WalletRepository walletRepository;
    
    @Resource
    private AritcleRepository aritcleRepository;
    
    @Resource
    private Comment2Repository comment2Repository;
    
    @Resource
    private TopicRepository topicRepository;
    

    @GetMapping("/footer/collCount")
    public String newblogs(Model model,HttpSession session) {
        if(session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            user = userRepository.findById(user.getId()).get();
            model.addAttribute("collCount",user.getCollections().size());
        }
        else {
            model.addAttribute("collCount","请登录");
        }
        return "header_footer :: collCount";
    }
    
    @PostMapping("/priceGraph")
    @ResponseBody
    public Object priceGraph(String id) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        SalePromotion salePromotion = salePromotionRepository.findById(id).get();
        System.out.println(salePromotion);
        Prices2 prices = priceRepository.findById(id).get();
        System.out.println(prices);
        
        map.put("salePromotion", salePromotion);
        map.put("prices", prices);
        
        return map;
    }
    
    @GetMapping("/test")
    @ResponseBody
    public Object Test() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        SalePromotion salePromotion = salePromotionRepository.findById("jd6322713").get();
        System.out.println(salePromotion);
        Prices2 prices = priceRepository.findById("jd6322713").get();
        System.out.println(prices);
        
        map.put("salePromotion", salePromotion);
        map.put("prices", prices);
        
        return map;
        
//        if(session.getAttribute("user") != null) {
//            User user = (User) session.getAttribute("user");
//            System.out.println(user);
//        }
//        User user = userRepository.findById(33L).get();
//        Collection collection = new Collection();
//        collection.setCreateTime(new Date());
//        collection.setPhoneId("123");
//        user.addColl(collection);
//        user = userRepository.save(user);
//        System.out.println(user.getCollections());//这个只适用于 查看我的收藏 页面展示
//        List<String> ids =  user.getCollections().stream().map(Collection::getPhoneId).collect(Collectors.toList());
        //System.out.println(ids);  //返回一个数组[123,123,123,123,123]
        //System.out.println(ids.contains("123")); 返回true
        //return user;
        //多对多查找
//        Article article = aritcleRepository.findById(83L).get();
//        System.out.println(article.getTopics());
//        return article;
        
        //级联新建
//        Article article = new Article();
//        article.setTitle("我们的爱");
//        article.setContent("一去不复返");
//        
//        Comment2 comment2 = new Comment2();
//        comment2.setContent("可以可以");
//        article.addComment2(comment2);
//        
//        Comment2 comment3 = new Comment2();
//        comment3.setContent("我们这一代");
//        article.addComment2(comment3);
//        
//        Topic topic = new Topic();
//        topic.setName("我们这个屯");
//        article.addTopic(topic);
//        
//        System.out.println(article);
//        aritcleRepository.save(article);
//        return article;
        
/*        Wallet wallet = walletRepository.findById((long) 62).get();
        System.out.println(wallet);
        System.out.println(wallet.getAuthor()); //不能这么用
*/        //查找
//        Author author = authorRepository.findById((long) 61).get();
//        System.out.println(author);
//        return author;
        
        //级联新建
//        Author author = new Author();
//        author.setNickName("池章立");
//        author.setPhone("13538628500");
//        author.setSignDate(new Date());
//        Wallet wallet = new Wallet();
//        wallet.setBalance(BigDecimal.valueOf(123));
//        author.setWallet(wallet);
//        authorRepository.save(author);
        
        //级联更新
//        Author author = authorRepository.findById((long) 59).get();
//        author.setNickName("科比");
//        author.getWallet().setBalance(BigDecimal.valueOf(10086));
//        authorRepository.save(author);
        
        //级联删除
        //authorRepository.deleteById((long) 59);
        //return "good";
    }
    
    @GetMapping("/listTrace")
    public String listTraceInformation(Model model, HttpSession session){
        ArrayList<PhoneDisplay> phoneList = new ArrayList<PhoneDisplay>();
        
        User user = null;
        if(session.getAttribute("user") != null) {
            user = (User) session.getAttribute("user");
            user = userRepository.findById(user.getId()).get();
            System.out.println(user);
            List<Trace> traces = user.getTraces();
            
            List<String> ids =  traces.stream().map(Trace::getPhoneId).collect(Collectors.toList());
            //这种方法查询到结果是乱序的
            List<PhoneDisplay> phones = phoneRepository.findByIds(ids);     
            //排序
            List<String> phoneIds =  phones.stream().map(PhoneDisplay::get_id).collect(Collectors.toList());         
            for(int i=0 ; i<ids.size(); i++) {
                phoneList.add(phones.get(phoneIds.indexOf(ids.get(i))));
            }
            model.addAttribute("traces", traces);
            model.addAttribute("phoneList", phoneList);
            return "trace";
        }
        else {
            return null;
        }
    }
    
    
    @GetMapping("/listTraceResp")
    @ResponseBody
    public Map<String, Object> listTraceInformationResp(Model model, HttpSession session){
        HashMap<String, Object> map = new HashMap<String, Object>();
        ArrayList<PhoneDisplay> phoneList = new ArrayList<PhoneDisplay>();
        
        User user = null;
        if(session.getAttribute("user") != null) {
            user = (User) session.getAttribute("user");
            user = userRepository.findById(user.getId()).get();
            System.out.println(user);
            List<Trace> traces = user.getTraces();
            
            List<String> ids =  traces.stream().map(Trace::getPhoneId).collect(Collectors.toList());
            //这种方法查询到结果是乱序的
            List<PhoneDisplay> phones = phoneRepository.findByIds(ids);     
            //排序
            List<String> phoneIds =  phones.stream().map(PhoneDisplay::get_id).collect(Collectors.toList());         
            for(int i=0 ; i<ids.size(); i++) {
                phoneList.add(phones.get(phoneIds.indexOf(ids.get(i))));
            }
            map.put("traces", traces);
            map.put("phoneList", phoneList);
            return map;
        }
        else {
            return null;
        }
    }
    
    @GetMapping("/listCollection")
    public String listCollInformation(Model model, HttpSession session){
        //ModelAndView modelAndView = new ModelAndView("collection");
        ArrayList<Prices2> priceList = new ArrayList<Prices2>();
        ArrayList<PhoneDisplay> phoneList = new ArrayList<PhoneDisplay>();
        
        User user = null;
        user = (User) session.getAttribute("user");
        user = userRepository.findById(user.getId()).get();
        System.out.println(user);
        List<Collection> collections = user.getCollections();
        
        List<String> ids =  collections.stream().map(Collection::getPhoneId).collect(Collectors.toList());
        //System.out.println(ids);
        
        //这种方法查询到结果是乱序的
        List<PhoneDisplay> phones = phoneRepository.findByIds(ids);
        //System.out.println(phones);
        
        List<Prices2> priceses = priceRepository.findByIds(ids);
        //System.out.println(priceses);
        
        //排序
        List<String> phoneIds =  phones.stream().map(PhoneDisplay::get_id).collect(Collectors.toList());
        //System.out.println(phoneIds);
        List<String> priceIds =  priceses.stream().map(Prices2::get_id).collect(Collectors.toList());
        //System.out.println(priceIds);
        
        for(int i=0 ; i<ids.size(); i++) {
            priceList.add(priceses.get(priceIds.indexOf(ids.get(i))));
            phoneList.add(phones.get(phoneIds.indexOf(ids.get(i))));
        }
        System.out.println(priceList);
        System.out.println(phoneList);
        
        model.addAttribute("collections", collections);
        model.addAttribute("phoneList", phoneList);
        model.addAttribute("priceList", priceList);
        return "collection";
    }
    
    
    @GetMapping("/listCollectionResp")
    @ResponseBody
    public Map<String, Object> listCollInformationResp(HttpSession session){
        HashMap<String, Object> map = new HashMap<String, Object>();
        ArrayList<Prices2> priceList = new ArrayList<Prices2>();
        ArrayList<PhoneDisplay> phoneList = new ArrayList<PhoneDisplay>();
        
        User user = null;
        if(session.getAttribute("user") != null) {
            user = (User) session.getAttribute("user");
            user = userRepository.findById(user.getId()).get();
            System.out.println(user);
            List<Collection> collections = user.getCollections();
            
            List<String> ids =  collections.stream().map(Collection::getPhoneId).collect(Collectors.toList());
            //System.out.println(ids);
            
            //这种方法查询到结果是乱序的
            List<PhoneDisplay> phones = phoneRepository.findByIds(ids);
            //System.out.println(phones);
            
            List<Prices2> priceses = priceRepository.findByIds(ids);
            //System.out.println(priceses);
            
            //排序
            List<String> phoneIds =  phones.stream().map(PhoneDisplay::get_id).collect(Collectors.toList());
            //System.out.println(phoneIds);
            List<String> priceIds =  priceses.stream().map(Prices2::get_id).collect(Collectors.toList());
            //System.out.println(priceIds);
            
            for(int i=0 ; i<ids.size(); i++) {
                priceList.add(priceses.get(priceIds.indexOf(ids.get(i))));
                phoneList.add(phones.get(phoneIds.indexOf(ids.get(i))));
            }
            System.out.println(priceList);
            System.out.println(phoneList);
            
            map.put("collections", collections);
            map.put("phoneList", phoneList);
            map.put("priceList", priceList);
            return map;
        }
        else {
            return null;
        }
    }
    
    
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
    
    @GetMapping("/toLogin")
    public ModelAndView toLogin() {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }
    
    @GetMapping("/toEditPassword")
    public ModelAndView toEditPassword() {
        ModelAndView modelAndView = new ModelAndView("edit_password");
        return modelAndView;
    }
    
    @PostMapping("/editPassword")
    //前台设置 phone 为disabled后，表单中的phone字段不会传过来，也不会发生后台验证错误
    public String editPassword(@Valid User userTemp , String newPassword , HttpSession session, RedirectAttributes attributes, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
        }
        System.out.println(userTemp.getPhoneNumber() + " " + userTemp.getPassword() + " " + newPassword + " ");
        User user = (User) session.getAttribute("user");
        user = userRepository.findById(user.getId()).get();
        user.setPassword(newPassword);
        userRepository.save(user);
        attributes.addFlashAttribute("message", "更新密码成功，亲爱的用户 ：" + user.getPhoneNumber());
        return "redirect:/phoneToPrice";
    }
    
    @PostMapping("/login")
    //public String Login(String phone, String password ,HttpSession session, RedirectAttributes attributes) {
    public String Login(@Valid User userTemp,HttpSession session, RedirectAttributes attributes) {
        System.out.println(userTemp.getPhoneNumber() + " " + userTemp.getPassword());
        String phone = userTemp.getPhoneNumber();
        String password = userTemp.getPassword();
        //如果密码等于mysql表中的密码，说明是旧密码，不需要修改密码。
        //如果jpa在数据库中找不到的会就会返回null
        User user = userRepository.findByPhoneNumber(phone);
        //System.out.println(user);
        //如果密码等于redis内存中的验证码，说明是新密码，需要修改mysql数据库中密码。也可能是新用户，直接注册的。
        //如果redis在内存中找不到的会就会返回null
        String redisValue = stringRedisTemplate.opsForValue().get(phone);
        System.out.println(redisValue);
        if (user != null && password.equals(user.getPassword())) {
            //如果是旧密码,不修改密码，直接登录成功。
            beSession(user,session);
            attributes.addFlashAttribute("message", "欢迎回来，尊敬的用户 : " + phone);
            return "redirect:/phoneToPrice";
        }
        else if(user == null) {
            //新用户，创建新账号。
            user = new User();
            user.setPhoneNumber(phone);
            user.setPassword(redisValue);
            user.setCreateTime(new Date());
            userRepository.save(user);
            beSession(user,session);
            attributes.addFlashAttribute("message", "尊敬的新用户 : " + phone + "，您好！");
            return "redirect:/phoneToPrice";
        }
        else if (password.equals(redisValue)) {
            //是验证码，需要修改密码
            //user = new User();
            user.setPhoneNumber(phone);
            user.setPassword(redisValue);
            //user.setCreateTime(new Date());
            user = userRepository.saveAndFlush(user);
            beSession(user,session);
            attributes.addFlashAttribute("message", "欢迎回来，尊敬的用户 : " + phone);
            return "redirect:/phoneToPrice";
        }
        attributes.addFlashAttribute("message", "手机号或密码错误");
        return "redirect:/toLogin";
    }
    
    private void beSession(User user, HttpSession session) {
        //user.setPassword(null);
        session.setAttribute("user", user);
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/toLogin";
    }
    
    @PostMapping("/addToColl")
    @ResponseBody
    public String addToColl(String id, HttpSession session) {
        System.out.println(id);//商品的ID
        if(session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            user = userRepository.findById(user.getId()).get(); //由于此用户和彼用户不一致，所以要重新查找
            Collection collection = new Collection();
            if(phoneRepository.existsById(id))
                collection.setPhoneId(id);
            else 
                return "fail";
            
            collection.setCreateTime(new Date());
            user.addColl(collection);
            user = userRepository.save(user);
            System.out.println(user);
            System.out.println(user.getCollections());
        }
        else {
            return "fail";
        }
        return "success";
    }
    
    public String collOrNot(String phoneId, HttpSession session) {
        System.out.println(phoneId);//商品的ID
        if(session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            user = userRepository.findById(user.getId()).get(); //由于此用户和彼用户不一致，所以要重新查找
            List<String> ids =  user.getCollections().stream().map(Collection::getPhoneId).collect(Collectors.toList());
            System.out.println(ids);
            if(ids.contains(phoneId)) {
                System.out.println("已经收藏了");
                return "colled"; //已经收藏了
            }
            else
                return "notColl"; //未收藏
        }
        else
            return "toLogin"; //未登录
    }
    
    
    @PostMapping("/cancelColl")
    @ResponseBody
    public String cancelColl(String id, HttpSession session) {
        System.out.println(id);
        if(session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            user = userRepository.getOne(user.getId());
            System.out.println(user.getCollections());
            user.getCollections().removeIf(coll -> coll.getPhoneId().equals(id)); //按条件删除所有
            System.out.println(user.getCollections());
            userRepository.save(user);
        }
        else {
            return "fail";
        }
        return "success";
    }
    
    @PostMapping("/receiverSMS")
    @ResponseBody
    public String ReceiverSMS(String phone) {
        System.out.println(phone);
        int appid = 1400155268; 
        String appkey = "9e7873c0a1b3bc1fb8976cfcafb1599e";
        // 需要发送短信的手机号码
        String[] phoneNumbers = {"13538628500"};
        // 短信模板ID，需要在短信应用中申请
        int templateId = 219697; 
        // 签名
        String smsSign = "池章立个人技术经验分享"; 
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 9).toLowerCase();
        System.out.println(uuid);

        //User user = userRepository.findByPhoneNumber(phone);
        //System.out.println(user);
        
        stringRedisTemplate.opsForValue().set(phone, uuid,1,TimeUnit.MINUTES);
        //发送短信
/*        try {
            String[] params = {uuid,"1"};
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            //send 的参数 1. 0 : 代表普通参数， 2. countrycode 国家代码， 3. 短信的手机号  4. 短信发送的内容， 5和6  扩展内容
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
                templateId, params, smsSign, "", "");  
            System.out.println(result);
        } catch (Exception e) {
            return "fail";
        }*/
        //用于回调
        return phone;
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
    
    @GetMapping("/phoneDetail/{id}")
    public ModelAndView listInformation3(@PathVariable String id,HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("detail");
        System.out.println(id);
        
        Phone phone = phoneRepository.findById(id).get();
        System.out.println(phone);
        modelAndView.addObject("phone", phone);
        
        SalePromotion salePromotion = salePromotionRepository.findByIdUnity(id);
        System.out.println(salePromotion);
        modelAndView.addObject("salePromotion", salePromotion);
        
        Prices2 prices = priceRepository.findByIdUnity(id);
        System.out.println(prices);
        modelAndView.addObject("prices", prices);
        
        Opinions opinions = opinionRepository.findByIdUnity(id);
        System.out.println(opinions);
        modelAndView.addObject("opinions", opinions);
        
        Impresses impresses = impressesRepository.findByIdUnity(id);
        System.out.println(impresses);
        modelAndView.addObject("impresses", impresses);
        
        String collStatus = collOrNot(id, session);
        modelAndView.addObject("collStatus", collStatus);
        
        return modelAndView;
    }
    
    @GetMapping("/phoneDetailResp/{id}")
    @ResponseBody
    public Map<String, Object> listInformation4(@PathVariable String id,HttpSession session) {
        Map<String, Object> map = new HashMap<String,Object>(); 
        System.out.println(id);
        
        Phone phone = phoneRepository.findById(id).get();
        System.out.println(phone);
        map.put("phone", phone);
        
        SalePromotion salePromotion = salePromotionRepository.findByIdUnity(id);
        System.out.println(salePromotion);
        map.put("salePromotion", salePromotion);
        
        Prices2 prices = priceRepository.findByIdUnity(id);
        System.out.println(prices);
        map.put("prices", prices);
        
        Opinions opinions = opinionRepository.findByIdUnity(id);
        System.out.println(opinions);
        map.put("opinions", opinions);
        
        Impresses impresses = impressesRepository.findByIdUnity(id);
        System.out.println(impresses);
        map.put("impresses", impresses);
        
        String collStatus = collOrNot(id, session);
        map.put("collStatus", collStatus);
        
        return map;
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

package com.SmartContactManager.Controller;

import com.SmartContactManager.dao.UserRepo;
import com.SmartContactManager.entities.User;
import com.SmartContactManager.helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo useRepo;


    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("title","Home - Smart Contact Manager");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("title", "About - Smart Contact Manager");
        return "about";
    }

    @RequestMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title","Register - Smart Contact Manger");
        model.addAttribute("user",new User());
        return "signup";
    }


    @RequestMapping(value = "/do_register",method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam(value = "agreement",defaultValue = "false") boolean agreement, Model model, HttpSession session){
        try{
            if(!agreement){
                System.out.println("you have not agreed to the terms and condition");
                throw new Exception("you have not agreed to the terms and condition");
            }
            System.out.println("RESUTL "+result.toString());
            if(result.hasErrors()){
                System.out.println("ERROR "+result.toString());
                model.addAttribute("user",user);
//                model.addAttribute("result",result);
                return "signup";
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User usr=useRepo.save(user);
            System.out.println("Agreement "+agreement);
            System.out.println("USER "+usr.toString());
            model.addAttribute("user",new User());
            session.setAttribute("message", new Message("Registration Successful","alert-success"));
            return "signup";
        }
        catch(Exception e){
            e.printStackTrace();
            model.addAttribute("user",user);
            session.setAttribute("message", new Message("something went wrong"+e.getMessage(),"alert-error"));
            return "signup";
        }
    }


    @GetMapping("/signin")
    public String customLogin(Model model){
        model.addAttribute("title","Login Page - Smart Contact Manager");
        return "login";
    }

//    @Autowired
//    private UserRepo userRepo;
//    @GetMapping("/test")
//    @ResponseBody
//    public String test(){
//        System.out.println("function call");
//        User usr=new User();
//        usr.setName("Arnav");
//        usr.setEmail("arnavtiwari10");
//        userRepo.save(usr);
//
//        return "working";
//    }
//    @GetMapping("/")
//    @ResponseBody
//    public String Do(){
//        System.out.println("There is some");
//        return "Hello";
//    }
}

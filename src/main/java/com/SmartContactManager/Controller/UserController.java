package com.SmartContactManager.Controller;

import com.SmartContactManager.dao.UserRepo;
import com.SmartContactManager.entities.Contact;
import com.SmartContactManager.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.server.ExportException;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepo userRepo;

    @ModelAttribute
    public void addCommonData(Model model, Principal principal){
        String userName=principal.getName();
        System.out.println(userName);
//        System.out.println("Username "+userName);
//        System.out.println("Entering dashboard");
        //get the user using username
        User user=userRepo.getUserByUserName(userName);
//        System.out.println(user.toString());
        model.addAttribute("user",user);

    }
    @RequestMapping("/index")
    public String index(Model model, Principal principal){


        return "normal/user_dashboard";
    }

    @GetMapping("/add-contact")
    public String openAddContactForm(Model model){
        System.out.println("Inside Function ");
        model.addAttribute("title","Add Contact");
        model.addAttribute("contact", new Contact());
        return "normal/add_contact_form";
    }

    //processing add contact form

    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file, Principal principal){
        try {
            System.out.println("DATA" + contact.toString());
            String name = principal.getName();
            User user = userRepo.getUserByUserName(name);

            //processing and uploading file
            if(file.isEmpty()){
                System.out.println("File is empty");
            }
            else{
                //upload fle to folder and update name to contact
                contact.setImageString(file.getOriginalFilename());
                File saveFile=new ClassPathResource("static/img").getFile();
                Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is UPloaded");
            }
            contact.setUser(user);
            user.getContacts().add(contact);
            userRepo.save(user);
            System.out.println("ADDED to data base");
        }
        catch(Exception e){
            System.out.println("ERROR "+e.getMessage());
            e.printStackTrace();

        }

        return "normal/add_contact_form";
    }
}

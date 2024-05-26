package com.example.practice1.web.validation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/validation/items")
public class ValidationItemController {

    @GetMapping
    public String itemList(){
        return "validation/items";
    }

    @GetMapping("/add")
    public String addForm(){
        return "validation/addForm";
    }

    @PostMapping("/add")
    public String addItem(){
        return "validation/item";
    }

    @GetMapping("/edit")
    public String editForm(){
        return "validation/editForm";
    }

    @PostMapping("/edit")
    public String edit(){
        return "validation/items";
    }

}

package com.example.practice1.web.validation;

import com.example.practice1.domain.item.Item;
import com.example.practice1.domain.item.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/validation/items")
public class ValidationItemController {

    private final ItemRepository itemRepository;


    public ValidationItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public String itemList(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "validation/items";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("item",new Item());
        return "validation/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status",true);
        return "redirect:/validation/items/{itemId}";
    }
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "validation/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId,Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "validation/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,@ModelAttribute Item item){
        itemRepository.update(itemId,item);
        return "redirect:/validation/items/{itemId}";
    }

}

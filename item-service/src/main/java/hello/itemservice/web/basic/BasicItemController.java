package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;
/*
    @Autowired // 생성자 하나일 때 생략 가능
    public BasicItemController(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }
    -> @RequiredArgsConstructor 을 쓰면 final붙은 생성자 만들어주어서 생략이 가능
 */
    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId,Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item",item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item/*, Model model*/){
        itemRepository.save(item);
//        model.addAttribute("item",item); // @ModelAttribute로 생략 가능(자동추가)
        return "basic/item";
    }


//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){
        // @ModelAttribute에 넘기는 이름 생략 시
        // 객체의 첫글자를 소문자로 바꾼 이름이 @ModelAttribute에 담기게 됨
        // ex) Item -> item , HelloData -> helloData // @ModelAtrribute("item"), @ModelAttribute("helloData")
        itemRepository.save(item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV4(Item item){
// @ModelAttribute 생략 가능 // String 같은 단순 타입은 @RequestParam 적용, 임의의 객체는 @ModelAttribute 적용(자동)
        itemRepository.save(item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV5(Item item){
        itemRepository.save(item);
        return "redirect:/basic/items/"+item.getId();
        // PRG => redirect 를 쓰지 않고 상품 상세를 호출할 경우 상세화면을 새로고침 하면 중복등록이 됨(새로고침시 마지막 동작(?) 실행)
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status",true);
        return "redirect:/basic/items/{itemId}";
        // PRG => redirect 를 쓰지 않고 상품 상세를 호출할 경우 상세화면을 새로고침 하면 중복등록이 됨(새로고침시 마지막 동작(?) 실행)
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId,Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,@ModelAttribute Item item){
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 -> @PostConstruct
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
    }
}

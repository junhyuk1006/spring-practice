package com.example.practice1.web.validation;

import com.example.practice1.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

// 스프링이 제공하는 검증로직(?)
@Component
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
        // item 이 clazz와 타입이 같나
        // item 이 자식클래스여도 통과
        // ==으로 비교보다 isAssignableFrom이 자식클래스까지 포함할 수 있기 때문에 더 좋음.
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item)target;

        if (!StringUtils.hasText(item.getItemName())){
            errors.rejectValue("itemName","required");
        }
        if(item.getPrice() == null || item.getPrice() <= 1000 || item.getPrice() >= 1000000){
            errors.rejectValue("price","range",new Object[]{1000,1000000},null);
        }
        if(item.getQuantity() == null || item.getQuantity() >= 9999){
            errors.rejectValue("quantity","max",new Object[]{9999},null);
        }
    }
}

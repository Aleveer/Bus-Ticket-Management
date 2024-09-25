package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Discount;
import com.myproject.busticket.repositories.DiscountRepository;

@Service
public class DiscountService {
    @Autowired
    private DiscountRepository discountRepository;

    public List<Discount> getAll() {
        return discountRepository.findAll();
    }

    public Discount getById(int id) {
        return discountRepository.findById(id).orElse(null);
    }

    // Note: This method uses for both add and update
    public Discount save(Discount discount) {
        return discountRepository.save(discount);
    }

    public Discount deleteById(int id) {
        Discount discount = getById(id);
        if (discount != null) {
            discountRepository.deleteById(id);
        }
        return discount;
    }

    public Discount getByCode(String code) {
        return discountRepository.findByCode(code);
    }

    // TODO: Implement more methods / query if needed.
}

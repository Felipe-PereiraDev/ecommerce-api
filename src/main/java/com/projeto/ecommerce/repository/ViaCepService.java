package com.projeto.ecommerce.repository;

import com.projeto.ecommerce.dto.ViaCepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://viacep.com.br/ws", name = "viacep")
public interface ViaCepService {

    @GetMapping("/{cep}/json/")
    ViaCepDTO getAddress(@PathVariable("cep") String cep);
}
